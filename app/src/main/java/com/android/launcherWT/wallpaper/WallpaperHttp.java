package com.android.launcherWT.wallpaper;

import android.content.Context;
import android.util.Log;

import com.android.launcherWT.R;

import org.conscrypt.Conscrypt;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.ConnectionSpec;
import okhttp3.Dns;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.TlsVersion;

/**
 * 壁纸下载唯一出口。
 *
 * <p>直接搬 75Play 在现场验证过的做法：Android 4.4 系统自带的 OpenSSL + 2013 年的系统根证书
 * 撑不住现在这些壁纸站点（TLS1.2 未默认开启、缺少新根、没有 SNI 处理、DNS 先给 IPv6），
 * 所以这里用 conscrypt 的 BoringSSL 做 TLS，再把一份现代 CA 包与系统根合并做信任链。</p>
 */
public final class WallpaperHttp {

    private static final String TAG = "WallpaperHttp";
    private static final int CONNECT_TIMEOUT_S = 12;
    private static final int READ_TIMEOUT_S = 20;
    /** 单次响应上限，防止异常站点把内存吃干。 */
    public static final int MAX_BODY = 12 * 1024 * 1024;

    private static volatile OkHttpClient shared;
    private static volatile String userAgent;

    private WallpaperHttp() {
    }

    public static String userAgent(Context context) {
        String agent = userAgent;
        if (agent != null) {
            return agent;
        }
        String version = "1.8";
        try {
            version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception ignored) {
        }
        agent = "LauncherWT/" + version + " (wallpaper)";
        userAgent = agent;
        return agent;
    }

    public static OkHttpClient client(Context context) {
        OkHttpClient current = shared;
        if (current != null) {
            return current;
        }
        synchronized (WallpaperHttp.class) {
            if (shared == null) {
                shared = build(context.getApplicationContext());
            }
            return shared;
        }
    }

    /** 取文本（JSON / HTML）。失败返回 null，不抛。 */
    public static String text(Context context, String url) {
        byte[] body = bytes(context, url);
        if (body == null) {
            return null;
        }
        return new String(body, java.nio.charset.Charset.forName("UTF-8"));
    }

    public static byte[] bytes(Context context, String url) {
        if (url == null || url.isEmpty()) {
            return null;
        }
        Response response = null;
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .header("User-Agent", userAgent(context))
                    .header("Accept", "image/jpeg,image/png,image/*,*/*;q=0.8")
                    .build();
            response = client(context).newCall(request).execute();
            if (!response.isSuccessful()) {
                Log.w(TAG, "http " + response.code() + " " + url);
                return null;
            }
            okhttp3.ResponseBody payload = response.body();
            if (payload == null) {
                return null;
            }
            long declared = payload.contentLength();
            if (declared > MAX_BODY) {
                Log.w(TAG, "too large " + declared + " " + url);
                return null;
            }
            return readCapped(payload.byteStream(), MAX_BODY);
        } catch (Exception ex) {
            logFailure("get", url, ex);
            return null;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    /** 下载到文件；先写 .part 再改名，失败不留半张图。 */
    public static boolean download(Context context, String url, File destination) {
        if (url == null || url.isEmpty()) {
            return false;
        }
        Response response = null;
        InputStream input = null;
        FileOutputStream output = null;
        File part = new File(destination.getAbsolutePath() + ".part");
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .header("User-Agent", userAgent(context))
                    .header("Accept", "image/*,*/*;q=0.8")
                    .build();
            response = client(context).newCall(request).execute();
            if (!response.isSuccessful() || response.body() == null) {
                Log.w(TAG, "download " + (response == null ? "null" : String.valueOf(response.code())) + " " + url);
                return false;
            }
            input = response.body().byteStream();
            output = new FileOutputStream(part);
            byte[] buffer = new byte[8192];
            int count;
            long total = 0L;
            while ((count = input.read(buffer)) != -1) {
                total += count;
                if (total > MAX_BODY) {
                    Log.w(TAG, "download exceeds cap " + url);
                    return false;
                }
                output.write(buffer, 0, count);
            }
            output.flush();
            output.close();
            output = null;
            input.close();
            input = null;
            if (total <= 0L) {
                return false;
            }
            if (destination.exists() && !destination.delete()) {
                Log.w(TAG, "cannot replace " + destination);
            }
            return part.renameTo(destination);
        } catch (Exception ex) {
            logFailure("download", url, ex);
            return false;
        } finally {
            closeQuietly(output);
            closeQuietly(input);
            if (response != null) {
                try {
                    response.close();
                } catch (Exception ignored) {
                }
            }
            if (part.exists() && !destination.exists()) {
                //noinspection ResultOfMethodCallIgnored
                part.delete();
            }
        }
    }


    /**
     * 自检用：短超时探一次接口，成功返回 "标签 code 长度B"，失败返回 "标签 FAIL 异常名"。
     * 车机上哪些站点连不上，用这个直接看。
     */
    public static String probe(Context context, String label, String url) {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .header("User-Agent", userAgent(context))
                    .build();
            OkHttpClient probeClient = client(context).newBuilder()
                    .connectTimeout(6, TimeUnit.SECONDS)
                    .readTimeout(8, TimeUnit.SECONDS)
                    .build();
            Response response = probeClient.newCall(request).execute();
            try {
                long length = response.body() == null ? 0 : response.body().contentLength();
                return label + " " + response.code() + (length > 0 ? " " + length + "B" : "");
            } finally {
                response.close();
            }
        } catch (Exception ex) {
            Throwable root = ex;
            int guard = 0;
            while (root.getCause() != null && root.getCause() != root && guard++ < 8) {
                root = root.getCause();
            }
            String name = root.getClass().getSimpleName();
            return label + " FAIL " + (name.length() == 0 ? root.getClass().getName() : name);
        }
    }

    private static OkHttpClient build(Context context) {
        X509TrustManager trust = compositeTrust(context);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .sslSocketFactory(tls12Factory(trust), trust)
                .connectionSpecs(buildConnectionSpecs())
                .protocols(buildProtocols())
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                // 车机走 4G 时 IPv6 常常是黑洞，优先 IPv4 再回落。
                .dns(new Dns() {
                    @Override
                    public List<java.net.InetAddress> lookup(String hostname) throws java.net.UnknownHostException {
                        List<java.net.InetAddress> all = Dns.SYSTEM.lookup(hostname);
                        List<java.net.InetAddress> v4 = new ArrayList<java.net.InetAddress>(all.size());
                        for (int i = 0; i < all.size(); i++) {
                            if (all.get(i) instanceof Inet4Address) {
                                v4.add(all.get(i));
                            }
                        }
                        if (v4.isEmpty()) {
                            return all;
                        }
                        for (int i = 0; i < all.size(); i++) {
                            if (!(all.get(i) instanceof Inet4Address)) {
                                v4.add(all.get(i));
                            }
                        }
                        return v4;
                    }
                })
                .connectTimeout(CONNECT_TIMEOUT_S, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_S, TimeUnit.SECONDS)
                .writeTimeout(READ_TIMEOUT_S, TimeUnit.SECONDS);
        return builder.build();
    }

    private static javax.net.ssl.SSLSocketFactory tls12Factory(X509TrustManager trust) {
        try {
            SSLContext ssl = SSLContext.getInstance("TLSv1.2", Conscrypt.newProvider());
            ssl.init(null, new javax.net.ssl.TrustManager[] {trust}, null);
            return ssl.getSocketFactory();
        } catch (Exception ex) {
            Log.w(TAG, "conscrypt unavailable, fall back to platform TLS", ex);
            try {
                SSLContext ssl = SSLContext.getInstance("TLS");
                ssl.init(null, new javax.net.ssl.TrustManager[] {trust}, null);
                return ssl.getSocketFactory();
            } catch (Exception inner) {
                throw new IllegalStateException(inner);
            }
        }
    }

    private static List<ConnectionSpec> buildConnectionSpecs() {
        List<ConnectionSpec> specs = new ArrayList<ConnectionSpec>(2);
        ConnectionSpec.Builder modern = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2);
        specs.add(modern.build());
        // 少数老站点仍只给 TLS1.0/1.1（个别图片 CDN），留一条兼容线路。
        specs.add(new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                .build());
        return specs;
    }

    private static List<Protocol> buildProtocols() {
        List<Protocol> protocols = new ArrayList<Protocol>(1);
        protocols.add(Protocol.HTTP_1_1);
        return protocols;
    }

    /**
     * 系统根 + 内置现代根（raw/update_ca_bundle.pem）取并集：
     * 先过系统，失败再过内置包，任一条链通过即放行。
     */
    private static X509TrustManager compositeTrust(Context context) {
        X509TrustManager system = systemTrust();
        X509TrustManager bundled = bundledTrust(context);
        if (bundled == null) {
            return system;
        }
        return new CompositeTrust(system, bundled);
    }

    private static X509TrustManager systemTrust() {
        try {
            TrustManagerFactory factory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            factory.init((KeyStore) null);
            return firstX509(factory);
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static X509TrustManager bundledTrust(Context context) {
        try {
            CertificateFactory certificates = CertificateFactory.getInstance("X.509");
            KeyStore store = KeyStore.getInstance(KeyStore.getDefaultType());
            store.load(null, null);
            InputStream input = context.getResources().openRawResource(R.raw.update_ca_bundle);
            String pem;
            try {
                pem = new String(readCapped(input, 1024 * 1024), java.nio.charset.Charset.forName("US-ASCII"));
            } finally {
                closeQuietly(input);
            }
            String begin = "-----BEGIN CERTIFICATE-----";
            String endMark = "-----END CERTIFICATE-----";
            int from = 0;
            int index = 0;
            while (from < pem.length()) {
                int start = pem.indexOf(begin, from);
                if (start < 0) {
                    break;
                }
                int end = pem.indexOf(endMark, start);
                if (end < 0) {
                    break;
                }
                String block = pem.substring(start, end + endMark.length());
                Certificate certificate = certificates.generateCertificate(
                        new java.io.ByteArrayInputStream(block.getBytes(java.nio.charset.Charset.forName("US-ASCII"))));
                store.setCertificateEntry("bundle-" + index, certificate);
                index++;
                from = end + endMark.length();
            }
            TrustManagerFactory factory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            factory.init(store);
            return firstX509(factory);
        } catch (Exception ex) {
            Log.w(TAG, "bundled CA load failed", ex);
            return null;
        }
    }

    private static X509TrustManager firstX509(TrustManagerFactory factory) {
        javax.net.ssl.TrustManager[] managers = factory.getTrustManagers();
        for (int i = 0; i < managers.length; i++) {
            if (managers[i] instanceof X509TrustManager) {
                return (X509TrustManager) managers[i];
            }
        }
        throw new IllegalStateException("no X509TrustManager");
    }

    private static final class CompositeTrust implements X509TrustManager {
        private final X509TrustManager system;
        private final X509TrustManager bundled;

        CompositeTrust(X509TrustManager system, X509TrustManager bundled) {
            this.system = system;
            this.bundled = bundled;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            try {
                system.checkClientTrusted(chain, authType);
            } catch (Exception ex) {
                bundled.checkClientTrusted(chain, authType);
            }
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            try {
                system.checkServerTrusted(chain, authType);
            } catch (Exception first) {
                try {
                    bundled.checkServerTrusted(chain, authType);
                } catch (Exception second) {
                    throw new CertificateException(first);
                }
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            X509Certificate[] a = system.getAcceptedIssuers();
            X509Certificate[] b = bundled.getAcceptedIssuers();
            X509Certificate[] merged = new X509Certificate[a.length + b.length];
            System.arraycopy(a, 0, merged, 0, a.length);
            System.arraycopy(b, 0, merged, a.length, b.length);
            return merged;
        }
    }

    public static byte[] readCapped(InputStream input, int cap) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int count;
        int total = 0;
        while ((count = input.read(buffer)) != -1) {
            total += count;
            if (total > cap) {
                return null;
            }
            output.write(buffer, 0, count);
        }
        return output.toByteArray();
    }

    private static void closeQuietly(java.io.Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (Exception ignored) {
        }
    }

    private static void logFailure(String what, String url, Exception failure) {
        StringBuilder line = new StringBuilder(what).append(" failed ").append(url);
        Throwable current = failure;
        for (int depth = 0; current != null && depth < 3; depth++) {
            line.append(" <- ").append(current.getClass().getSimpleName());
            if (current.getMessage() != null) {
                line.append(": ").append(current.getMessage());
            }
            current = current.getCause();
        }
        Log.w(TAG, line.toString());
    }
}
