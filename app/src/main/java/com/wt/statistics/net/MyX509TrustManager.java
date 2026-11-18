package com.wt.statistics.net;

/* loaded from: classes.dex */
public class MyX509TrustManager implements javax.net.ssl.X509TrustManager {
    private static final java.lang.String LOGTAG = "MyX509TrustManager";
    private boolean checkCertOk;
    javax.net.ssl.X509TrustManager tm;

    public MyX509TrustManager(boolean checkCertOk) {
        java.security.KeyStore keyStore;
        this.checkCertOk = false;
        this.checkCertOk = checkCertOk;
        try {
            if (android.os.Build.VERSION.SDK_INT >= 14) {
                keyStore = java.security.KeyStore.getInstance("AndroidCAStore");
            } else {
                keyStore = java.security.KeyStore.getInstance("BKS");
            }
            keyStore.load(null, null);
            javax.net.ssl.TrustManagerFactory tmf = javax.net.ssl.TrustManagerFactory.getInstance(javax.net.ssl.KeyManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);
            javax.net.ssl.TrustManager[] tms = tmf.getTrustManagers();
            for (int i = 0; i < tms.length; i++) {
                if (tms[i] instanceof javax.net.ssl.X509TrustManager) {
                    this.tm = (javax.net.ssl.X509TrustManager) tms[i];
                    return;
                }
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, java.lang.String authType) throws java.security.cert.CertificateException {
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, java.lang.String authType) throws java.security.cert.CertificateException {
        if (this.checkCertOk) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "证书已经验证通过，但ssl握手异常，下次不再验证");
            return;
        }
        com.wt.statistics.utils.SLog.i(LOGTAG, "checkServerTrusted authType=" + authType);
        this.tm.checkServerTrusted(chain, authType);
        this.checkCertOk = true;
    }

    @Override // javax.net.ssl.X509TrustManager
    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    public boolean isCheckCertOk() {
        return this.checkCertOk;
    }
}
