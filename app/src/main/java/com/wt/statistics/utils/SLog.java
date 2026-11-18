package com.wt.statistics.utils;

/* loaded from: classes.dex */
public class SLog {
    private static final java.lang.String SLOG = "SLog_";

    public static void d(java.lang.String tag, java.lang.String msg) {
        try {
            if (com.wt.common.WTConfigure.isLogEnabled()) {
                android.util.Log.d(SLOG + tag, msg);
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    public static void d(java.lang.String tag, java.lang.String msg, java.lang.Throwable tr) {
        try {
            if (com.wt.common.WTConfigure.isLogEnabled()) {
                android.util.Log.d(SLOG + tag, msg, tr);
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    public static void i(java.lang.String tag, java.lang.String msg) {
        try {
            if (com.wt.common.WTConfigure.isLogEnabled()) {
                android.util.Log.i(SLOG + tag, msg);
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    public static void i(java.lang.String tag, java.lang.Throwable tr) {
        try {
            if (com.wt.common.WTConfigure.isLogEnabled()) {
                android.util.Log.i(SLOG + tag, "", tr);
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    public static void i(java.lang.String tag, java.lang.String msg, java.lang.Throwable tr) {
        try {
            if (com.wt.common.WTConfigure.isLogEnabled()) {
                android.util.Log.i(SLOG + tag, msg, tr);
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
}
