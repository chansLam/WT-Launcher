package com.wt.statistics;

/* loaded from: classes.dex */
public class WTClickAgent {
    private static final java.lang.String LOGTAG = "WTClickAgent";

    private void init(android.content.Context cxt) {
        com.wt.statistics.StatisticsManager.getInstance(cxt);
    }

    public static void getToken(android.content.Context cxt) {
        com.wt.statistics.StatisticsManager.getInstance(cxt).getToken();
    }

    public static void getServerConfig(android.content.Context cxt) {
        com.wt.statistics.StatisticsManager.getInstance(cxt).getServerConfig();
    }

    public static void refreshToken(android.content.Context cxt) {
        com.wt.statistics.StatisticsManager.getInstance(cxt).refreshToken();
    }

    public static void onResume(android.content.Context cxt) {
        if (com.wt.common.WTConfigure.isSdkDisabled()) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "onResume sdk is diabled");
        } else if (cxt == null) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "onResume context is null");
        } else {
            com.wt.statistics.utils.SLog.i(LOGTAG, "onResume start name=" + cxt.getClass().getCanonicalName());
        }
    }

    public static void onPause(android.content.Context cxt) {
        if (com.wt.common.WTConfigure.isSdkDisabled()) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "onResume sdk is diabled");
        } else if (cxt == null) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "onPause context is null");
        } else {
            com.wt.statistics.utils.SLog.i(LOGTAG, "onPause start name=" + cxt.getClass().getCanonicalName());
        }
    }

    public static void onPageStart(java.lang.String viewName) {
        if (com.wt.common.WTConfigure.isSdkDisabled()) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "onResume sdk is diabled");
        } else {
            com.wt.statistics.utils.SLog.i(LOGTAG, "onPageStart start viewName = " + viewName);
        }
    }

    public static void onPageEnd(java.lang.String viewName) {
        if (com.wt.common.WTConfigure.isSdkDisabled()) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "onResume sdk is diabled");
        } else {
            com.wt.statistics.utils.SLog.i(LOGTAG, "onPageEnd start viewName = " + viewName);
        }
    }

    public static void onEvent(android.content.Context cxt, java.lang.String eventId) {
        if (cxt == null) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "onEvent context is null");
        } else {
            onEvent(cxt, eventId, "");
        }
    }

    public static void onEvent(android.content.Context cxt, java.lang.String eventId, java.lang.String eventLable) {
        if (cxt == null) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "onEvent context is null");
        } else {
            onEvent(cxt, eventId, eventLable, (java.util.Map<java.lang.String, java.lang.String>) null);
        }
    }

    public static void onEvent(android.content.Context cxt, java.lang.String eventId, java.util.Map<java.lang.String, java.lang.String> mapEvent) {
        if (cxt == null) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "onEvent context is null");
        } else {
            onEvent(cxt, eventId, "", mapEvent);
        }
    }

    public static void onEvent(android.content.Context cxt, java.lang.String eventId, java.lang.String eventLable, java.util.Map<java.lang.String, java.lang.String> mapEvent) {
        if (com.wt.common.WTConfigure.isSdkDisabled()) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "onResume sdk is diabled");
        } else if (cxt == null) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "onEvent context is null");
        } else {
            com.wt.statistics.StatisticsManager.getInstance(cxt).sendNormalEvent(eventId, eventLable, mapEvent);
        }
    }

    public static void onReportError(android.content.Context cxt, java.lang.Throwable e) {
        if (com.wt.common.WTConfigure.isSdkDisabled()) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "onResume sdk is diabled");
        } else if (cxt == null) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "onReportError context is null");
        } else {
            com.wt.statistics.utils.SLog.i(LOGTAG, "onReportError start e = " + e.getMessage());
        }
    }

    public static void onReportError(android.content.Context cxt, java.lang.String error) {
        if (com.wt.common.WTConfigure.isSdkDisabled()) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "onResume sdk is diabled");
        } else if (cxt == null) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "onReportError context is null");
        } else {
            com.wt.statistics.utils.SLog.i(LOGTAG, "onReportError start error = " + error);
        }
    }

    public static void setReportUncaughtExceptions(boolean enable) {
        if (com.wt.common.WTConfigure.isSdkDisabled()) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "onResume sdk is diabled");
        } else {
            com.wt.statistics.utils.SLog.i(LOGTAG, "setReportUncaughtExceptions start enable = " + enable);
        }
    }

    public static void onKillProcess(android.content.Context cxt) {
        if (cxt == null) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "onKillProcess context is null");
        }
    }

    private static void onEvent(android.content.Context cxt, java.lang.String eventId, java.util.Map<java.lang.String, java.lang.String> mapEvent, int duration) {
    }

    private static void setUploadEnableWhenWifi(boolean enable) {
    }

    private static boolean isUploadEnableWhenWifi() {
        return true;
    }

    private static void setUploadEnableWhenDataConnection(boolean enable) {
    }

    private static boolean isUploadEnableWhenDataConnection() {
        return false;
    }
}
