package com.wt.statistics.net;

/* loaded from: classes.dex */
public class UrlConnectUtils {
    private static final java.lang.String LOGTAG = "UrlConnectUtils";

    public static java.lang.String doGet(java.lang.String url, java.util.Map<java.lang.String, java.lang.String> params, java.lang.String body) {
        java.net.URLConnection connection;
        java.lang.String response = "";
        com.wt.statistics.utils.SLog.i(LOGTAG, "doGet start body=" + body + "; url=" + url);
        try {
            if (url.startsWith("https")) {
                connection = getHttpsConnection(url);
            } else {
                connection = new java.net.URL(url).openConnection();
            }
            java.net.HttpURLConnection urlConnection = (java.net.HttpURLConnection) connection;
            urlConnection.setConnectTimeout(20000);
            urlConnection.setReadTimeout(20000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setRequestProperty("Accept", "application/json");
            if (params != null && params.size() > 0) {
                for (java.lang.String key : params.keySet()) {
                    java.lang.String value = params.get(key);
                    urlConnection.setRequestProperty(key, value);
                }
            }
            urlConnection.connect();
            if (!android.text.TextUtils.isEmpty(body)) {
                java.io.DataOutputStream outputStream = new java.io.DataOutputStream(urlConnection.getOutputStream());
                outputStream.write(body.getBytes());
                outputStream.flush();
                outputStream.close();
            }
            com.wt.statistics.utils.SLog.i(LOGTAG, "doGet response code =" + urlConnection.getResponseCode());
            if (urlConnection.getResponseCode() == 200) {
                response = streamToString(urlConnection.getInputStream());
                com.wt.statistics.utils.SLog.i(LOGTAG, "doGet response=" + response);
            }
            urlConnection.disconnect();
        } catch (java.net.MalformedURLException e) {
            e.printStackTrace();
        } catch (java.io.IOException e2) {
            e2.printStackTrace();
        } catch (java.security.KeyManagementException e3) {
            e3.printStackTrace();
        } catch (java.security.NoSuchAlgorithmException e4) {
            e4.printStackTrace();
        }
        return response;
    }

    public static java.lang.String doPost(java.lang.String url, java.util.Map<java.lang.String, java.lang.String> params, java.lang.String body) {
        java.net.URLConnection connection;
        java.lang.String response = "";
        com.wt.statistics.utils.SLog.i(LOGTAG, "doPost start body=" + body + ";url=" + url);
        try {
            if (url.startsWith("https")) {
                connection = getHttpsConnection(url);
            } else {
                connection = new java.net.URL(url).openConnection();
            }
            java.net.HttpURLConnection urlConnection = (java.net.HttpURLConnection) connection;
            urlConnection.setConnectTimeout(20000);
            urlConnection.setReadTimeout(20000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            if (params != null && params.size() > 0) {
                for (java.lang.String key : params.keySet()) {
                    java.lang.String value = params.get(key);
                    urlConnection.setRequestProperty(key, value);
                }
            }
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.connect();
            if (!android.text.TextUtils.isEmpty(body)) {
                java.io.DataOutputStream outputStream = new java.io.DataOutputStream(urlConnection.getOutputStream());
                outputStream.write(body.getBytes());
                outputStream.flush();
                outputStream.close();
            }
            com.wt.statistics.utils.SLog.i(LOGTAG, "doPost response code =" + urlConnection.getResponseCode());
            if (urlConnection.getResponseCode() == 200) {
                response = streamToString(urlConnection.getInputStream());
                com.wt.statistics.utils.SLog.i(LOGTAG, "doPost response=" + response);
            }
            urlConnection.disconnect();
        } catch (java.net.MalformedURLException e) {
            e.printStackTrace();
        } catch (java.io.IOException e2) {
            e2.printStackTrace();
        } catch (java.security.KeyManagementException e3) {
            e3.printStackTrace();
        } catch (java.security.NoSuchAlgorithmException e4) {
            e4.printStackTrace();
        }
        return response;
    }

    public static java.lang.String streamToString(java.io.InputStream is) {
        try {
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            while (true) {
                int len = is.read(buffer);
                if (len != -1) {
                    baos.write(buffer, 0, len);
                } else {
                    baos.close();
                    is.close();
                    byte[] byteArray = baos.toByteArray();
                    return new java.lang.String(byteArray);
                }
            }
        } catch (java.lang.Exception e) {
            return null;
        }
    }

    private static java.net.URLConnection getHttpsConnection(java.lang.String url) throws java.security.NoSuchAlgorithmException, java.security.KeyManagementException, java.io.IOException, java.net.MalformedURLException {
        java.net.URL desUrl = new java.net.URL(url);
        javax.net.ssl.SSLContext sslContext = javax.net.ssl.SSLContext.getInstance("TLS");
        com.wt.statistics.net.MyX509TrustManager trustManager = new com.wt.statistics.net.MyX509TrustManager(false);
        javax.net.ssl.TrustManager[] trustAllCerts = {trustManager};
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        java.net.URLConnection connection = desUrl.openConnection();
        ((javax.net.ssl.HttpsURLConnection) connection).setSSLSocketFactory(sslContext.getSocketFactory());
        ((javax.net.ssl.HttpsURLConnection) connection).setHostnameVerifier(new com.wt.statistics.net.MyHostnameVerifier());
        return connection;
    }
}
