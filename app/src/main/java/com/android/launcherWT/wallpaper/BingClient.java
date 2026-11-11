package com.android.launcherWT.wallpaper;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public final class BingClient {
    public static final String DAILY_URL =
            "https://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=zh-CN";
    private static final int TIMEOUT_MS = 10000;

    private BingClient() {
    }

    public static String fetchJson(String url) {
        HttpsURLConnection connection = null;
        InputStream input = null;
        try {
            connection = open(url);
            if (connection == null || connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
            input = connection.getInputStream();
            byte[] body = readAll(input);
            if (body.length == 0) {
                return null;
            }
            return new String(body, Charset.forName("UTF-8"));
        } catch (Exception ex) {
            return null;
        } finally {
            close(input);
            disconnect(connection);
        }
    }

    public static boolean download(String url, File destination) {
        HttpsURLConnection connection = null;
        InputStream input = null;
        FileOutputStream output = null;
        try {
            connection = open(url);
            if (connection == null || connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
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
            return false;
        } finally {
            close(output);
            close(input);
            disconnect(connection);
        }
    }

    private static HttpsURLConnection open(String url) throws IOException {
        URLConnection connection = new URL(url).openConnection();
        if (!(connection instanceof HttpsURLConnection)) {
            if (connection instanceof HttpURLConnection) {
                ((HttpURLConnection) connection).disconnect();
            }
            return null;
        }
        HttpsURLConnection https = (HttpsURLConnection) connection;
        https.setConnectTimeout(TIMEOUT_MS);
        https.setReadTimeout(TIMEOUT_MS);
        enableTls12(https);
        return https;
    }

    private static void enableTls12(HttpsURLConnection https) throws IOException {
        try {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, null, null);
            https.setSSLSocketFactory(new Tls12SocketFactory(context.getSocketFactory()));
        } catch (GeneralSecurityException ex) {
            throw new IOException(ex);
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

    private static void disconnect(HttpsURLConnection connection) {
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
            return enable(delegate.createSocket(socket, host, port, autoClose));
        }

        @Override
        public Socket createSocket(String host, int port) throws IOException {
            return enable(delegate.createSocket(host, port));
        }

        @Override
        public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
            return enable(delegate.createSocket(host, port, localHost, localPort));
        }

        @Override
        public Socket createSocket(InetAddress host, int port) throws IOException {
            return enable(delegate.createSocket(host, port));
        }

        @Override
        public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort)
                throws IOException {
            return enable(delegate.createSocket(address, port, localAddress, localPort));
        }

        private Socket enable(Socket socket) {
            if (socket instanceof SSLSocket) {
                enableProtocol((SSLSocket) socket);
            }
            return socket;
        }

        private void enableProtocol(SSLSocket socket) {
            String[] supported = socket.getSupportedProtocols();
            boolean available = false;
            for (int i = 0; i < supported.length; i++) {
                if (TLS_12.equals(supported[i])) {
                    available = true;
                    break;
                }
            }
            if (!available) {
                return;
            }
            String[] enabled = socket.getEnabledProtocols();
            for (int i = 0; i < enabled.length; i++) {
                if (TLS_12.equals(enabled[i])) {
                    return;
                }
            }
            String[] next = new String[enabled.length + 1];
            System.arraycopy(enabled, 0, next, 0, enabled.length);
            next[enabled.length] = TLS_12;
            socket.setEnabledProtocols(next);
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
