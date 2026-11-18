package com.incall.apps.service.util;

/* loaded from: classes.dex */
public class LogUtil {
    private static final java.lang.String TAG_HEAD = "IncallService_B.2.6.6_";
    private static final boolean isDebug = false;

    public static void v(java.lang.String tag, java.lang.Object... msgs) {
    }

    public static void d(java.lang.String tag, java.lang.Object... msgs) {
    }

    public static void i(java.lang.String tag, java.lang.Object... msgs) {
        android.util.Log.i(buildTag(tag), buildMsgs(msgs));
    }

    public static void w(java.lang.String tag, java.lang.Object... msgs) {
        android.util.Log.w(buildTag(tag), buildMsgs(msgs));
    }

    public static void e(java.lang.String tag, java.lang.Object... msgs) {
        android.util.Log.e(buildTag(tag), buildMsgs(msgs));
    }

    private static java.lang.String buildMsgs(java.lang.Object... msgs) {
        if (msgs == null) {
            return null;
        }
        java.lang.StringBuffer sb = new java.lang.StringBuffer();
        for (java.lang.Object msg : msgs) {
            sb.append(msg);
        }
        return sb.toString();
    }

    private static java.lang.String buildTag(java.lang.String tag) {
        return TAG_HEAD + tag;
    }
}
