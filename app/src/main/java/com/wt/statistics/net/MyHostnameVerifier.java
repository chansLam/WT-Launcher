package com.wt.statistics.net;

/* loaded from: classes.dex */
public class MyHostnameVerifier implements javax.net.ssl.HostnameVerifier {
    private static final java.lang.String LOGTAG = "MyHostnameVerifier";

    @Override // javax.net.ssl.HostnameVerifier
    public boolean verify(java.lang.String hostname, javax.net.ssl.SSLSession session) {
        com.wt.statistics.utils.SLog.i(LOGTAG, "verify hostname=" + hostname);
        return "daq.api.auto-pai.com".equals(hostname);
    }
}
