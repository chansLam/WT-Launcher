package com.android.launcherWT.wallpaper;

import android.content.Context;
import android.util.Log;

import com.android.launcherWT.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public final class BingClient {
    public static final String DAILY_URL =
            "https://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=zh-CN";
    public static final String DAILY_URL_EN =
            "https://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=en-US";
    public static final String DAILY_WALLHAVEN =
            "https://wallhaven.cc/api/v1/search?sorting=toplist&topRange=1d&purity=100"
                    + "&atleast=1920x720&ratios=16x9,21x9";
    private static final int TIMEOUT_MS = 10000;

    private BingClient() {
    }

    public static String fetchJson(Context context, String url) {
        return fetchText(context, url);
    }

    public static String fetchText(Context context, String url) {
        HttpURLConnection connection = null;
        InputStream input = null;
        try {
            connection = open(context, url);
            if (connection == null || connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.w("BingClient", "fetch " + (connection == null ? "null" : connection.getResponseCode()) + " " + url);
                return null;
            }
            input = connection.getInputStream();
            byte[] body = readAll(input);
            if (body.length == 0) {
                return null;
            }
            return new String(body, Charset.forName("UTF-8"));
        } catch (Exception ex) {
            Log.w("BingClient", "fetch failed " + url, ex);
            return null;
        } finally {
            close(input);
            disconnect(connection);
        }
    }

    public static boolean download(Context context, String url, File destination) {
        HttpURLConnection connection = null;
        InputStream input = null;
        FileOutputStream output = null;
        try {
            connection = open(context, url);
            if (connection == null || connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.w("BingClient", "download " + (connection == null ? "null" : String.valueOf(connection.getResponseCode())) + " " + url);
                return false;
            }
            input = connection.getInputStream();
            output = new FileOutputStream(destination);
            byte[] buffer = new byte[8192];
            int count;
            long total = 0L;
            while ((count = input.read(buffer)) != -1) {
                output.write(buffer, 0, count);
                total += count;
            }
            output.flush();
            return total > 0L;
        } catch (Exception ex) {
            Log.w("BingClient", "download failed " + url + " " + ex);
            return false;
        } finally {
            close(output);
            close(input);
            disconnect(connection);
        }
    }

    private static HttpURLConnection open(Context context, String url) throws IOException {
        try {
            preferIpv4(new URL(url).getHost());
        } catch (IOException ignored) {
        }
        URLConnection connection = new URL(url).openConnection();
        if (!(connection instanceof HttpURLConnection)) {
            return null;
        }
        HttpURLConnection http = (HttpURLConnection) connection;
        http.setConnectTimeout(TIMEOUT_MS);
        http.setReadTimeout(TIMEOUT_MS);
        http.setInstanceFollowRedirects(true);
        http.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Linux; Android 4.4.4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        http.setRequestProperty("Referer", referer(url));
        http.setRequestProperty("Accept", "image/jpeg,image/png,image/*,*/*;q=0.8");
        if (http instanceof HttpsURLConnection) {
            ((HttpsURLConnection) http).setSSLSocketFactory(trustFactory(context));
        }
        return http;
    }

    private static void preferIpv4(String host) {
        if (host == null) {
            return;
        }
        try {
            InetAddress[] all = InetAddress.getAllByName(host);
            java.util.ArrayList<InetAddress> v4 = new java.util.ArrayList<InetAddress>();
            for (int i = 0; i < all.length; i++) {
                if (all[i] instanceof Inet4Address) {
                    v4.add(all[i]);
                }
            }
            if (v4.isEmpty()) {
                return;
            }
            java.lang.reflect.Field field = InetAddress.class.getDeclaredField("addressCache");
            field.setAccessible(true);
            Object cache = field.get(null);
            java.lang.reflect.Method put = cache.getClass().getDeclaredMethod(
                    "put", String.class, InetAddress[].class);
            put.setAccessible(true);
            put.invoke(cache, host, v4.toArray(new InetAddress[v4.size()]));
        } catch (Exception ex) {
            Log.w("BingClient", "preferIpv4 " + host + " " + ex);
        }
    }

    private static String referer(String url) {
        if (url == null) {
            return "https://www.bing.com/";
        }
        if (url.indexOf("wallhaven.cc") >= 0) {
            return "https://wallhaven.cc/";
        }
        if (url.indexOf("zzzmh.cn") >= 0) {
            return "https://bz.zzzmh.cn/";
        }
        if (url.indexOf("cn.bing.com") >= 0) {
            return "https://cn.bing.com/";
        }
        return "https://www.bing.com/";
    }

    private static volatile SSLSocketFactory trusted;

    private static int loadPem(Context context, CertificateFactory certificates, KeyStore store, int resource, int index)
            throws GeneralSecurityException, IOException {
        InputStream input = context.getApplicationContext().getResources().openRawResource(resource);
        String pem;
        try {
            pem = new String(readAll(input), Charset.forName("US-ASCII"));
        } finally {
            input.close();
        }
        String begin = "-----BEGIN CERTIFICATE-----";
        String endMark = "-----END CERTIFICATE-----";
        int from = 0;
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
                    new ByteArrayInputStream(block.getBytes(Charset.forName("US-ASCII"))));
            store.setCertificateEntry("ca-" + index, certificate);
            index++;
            from = end + endMark.length();
        }
        return index;
    }

    private static SSLSocketFactory trustFactory(Context context) throws IOException {
        if (trusted != null) {
            return trusted;
        }
        synchronized (BingClient.class) {
            if (trusted != null) {
                return trusted;
            }
            try {
                CertificateFactory certificates = CertificateFactory.getInstance("X.509");
                KeyStore store = KeyStore.getInstance(KeyStore.getDefaultType());
                store.load(null, null);
                int index = 0;
                index = loadPem(context, certificates, store, R.raw.mozilla_cacert, index);
                index = loadPem(context, certificates, store, R.raw.bing_chain, index);
                TrustManagerFactory managers = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                managers.init(store);
                SSLContext ssl = SSLContext.getInstance("TLS");
                ssl.init(null, managers.getTrustManagers(), null);
                trusted = new Tls12SocketFactory(ssl.getSocketFactory());
                return trusted;
            } catch (GeneralSecurityException ex) {
                throw new IOException(ex);
            }
        }
    }

    private static byte[] readAll(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int count;
        while ((count = input.read(buffer)) != -1) {
            output.write(buffer, 0, count);
        }
        return output.toByteArray();
    }

    private static void disconnect(HttpURLConnection connection) {
        if (connection != null) {
            connection.disconnect();
        }
    }

    private static final class Tls12SocketFactory extends SSLSocketFactory {
        private static final String TLS_12 = "TLSv1.2";
        private final SSLSocketFactory delegate;

        Tls12SocketFactory(SSLSocketFactory delegate) {
            this.delegate = delegate;
        }

        @Override
        public String[] getDefaultCipherSuites() {
            return delegate.getDefaultCipherSuites();
        }

        @Override
        public String[] getSupportedCipherSuites() {
            return delegate.getSupportedCipherSuites();
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
            return enable(delegate.createSocket(socket, host, port, autoClose), host);
        }

        @Override
        public Socket createSocket(String host, int port) throws IOException {
            return enable(delegate.createSocket(host, port), host);
        }

        @Override
        public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
            return enable(delegate.createSocket(host, port, localHost, localPort), host);
        }

        @Override
        public Socket createSocket(InetAddress host, int port) throws IOException {
            return enable(delegate.createSocket(host, port), null);
        }

        @Override
        public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort)
                throws IOException {
            return enable(delegate.createSocket(address, port, localAddress, localPort), null);
        }

        private Socket enable(Socket socket, String host) {
            if (!(socket instanceof SSLSocket)) {
                return socket;
            }
            SSLSocket ssl = (SSLSocket) socket;
            enableModernTls(ssl);
            String name = host;
            if (name != null) {
                try {
                    java.lang.reflect.Method method = ssl.getClass().getMethod("setHostname", String.class);
                    method.invoke(ssl, name);
                } catch (Exception ignored) {
                }
            }
            return ssl;
        }

        private void enableModernTls(SSLSocket socket) {
            String[] protocols = pick(socket.getSupportedProtocols(), new String[] {TLS_12, "TLSv1.1"});
            if (protocols.length > 0) {
                socket.setEnabledProtocols(protocols);
            }
            String[] ciphers = socket.getSupportedCipherSuites();
            if (ciphers != null && ciphers.length > 0) {
                socket.setEnabledCipherSuites(ciphers);
            }
        }

        private String[] pick(String[] supported, String[] wanted) {
            java.util.ArrayList<String> found = new java.util.ArrayList<String>();
            if (supported == null) {
                return new String[0];
            }
            for (int i = 0; i < wanted.length; i++) {
                for (int j = 0; j < supported.length; j++) {
                    if (wanted[i].equals(supported[j])) {
                        found.add(wanted[i]);
                        break;
                    }
                }
            }
            return found.toArray(new String[found.size()]);
        }
    }

    private static void close(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException ex) {
        }
    }
}
