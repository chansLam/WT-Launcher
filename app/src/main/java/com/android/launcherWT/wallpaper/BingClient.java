package com.android.launcherWT.wallpaper;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.net.ssl.HttpsURLConnection;

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
        return https;
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
