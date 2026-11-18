package com.autopai.support.utils;

/* loaded from: classes.dex */
public class SLog {
    private static final java.lang.String SLOG = "SupportWidget_";
    private static final boolean isLogEnabled = true;

    public static void d(java.lang.String tag, java.lang.String msg) {
        try {
            android.util.Log.d(SLOG + tag, msg);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    public static void d(java.lang.String tag, java.lang.String msg, java.lang.Throwable tr) {
        try {
            android.util.Log.d(SLOG + tag, msg, tr);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    public static void i(java.lang.String tag, java.lang.String msg) {
        try {
            android.util.Log.i(SLOG + tag, msg);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    public static void i(java.lang.String tag, java.lang.Throwable tr) {
        try {
            android.util.Log.i(SLOG + tag, "", tr);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    public static void i(java.lang.String tag, java.lang.String msg, java.lang.Throwable tr) {
        try {
            android.util.Log.i(SLOG + tag, msg, tr);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
}
