package com.wt.statistics.net;

/* loaded from: classes.dex */
public class UrlConstants {
    public static final java.lang.String E0000 = "E0000";
    public static final java.lang.String E0001 = "E0001";
    public static final java.lang.String E0002 = "E0002";
    public static final java.lang.String E0003 = "E0003";
    public static final java.lang.String E0004 = "E0004";
    public static final java.lang.String E0005 = "E0005";
    public static final java.lang.String RET_OK = "0000";
    public static final java.lang.String S_SERVER_CONFIG_URL = "log/config";
    public static final java.lang.String S_SERVER_EVENT_URL = "log/collect";
    public static final java.lang.String S_SERVER_EXCEPTION_EVENT_URL = "error/log";
    public static final java.lang.String S_SERVER_REFRESH_TOKEN_URL = "refresh";
    public static final java.lang.String S_SERVER_TOKEN_URL = "authorization";
    public static final java.lang.String S_SERVER_URL = "https://daq.api.auto-pai.com/daq/v1/";

    public static java.lang.String getExceptionEventUrl() {
        return "https://daq.api.auto-pai.com/daq/v1/error/log";
    }

    public static java.lang.String getEventUrl() {
        return "https://daq.api.auto-pai.com/daq/v1/log/collect";
    }

    public static java.lang.String getConfigUrl() {
        return "https://daq.api.auto-pai.com/daq/v1/log/config";
    }

    public static java.lang.String getTokenUrl() {
        return "https://daq.api.auto-pai.com/daq/v1/authorization";
    }

    public static java.lang.String getRefreshTokenUrl() {
        return "https://daq.api.auto-pai.com/daq/v1/refresh";
    }
}
