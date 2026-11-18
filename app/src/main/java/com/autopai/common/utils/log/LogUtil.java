package com.autopai.common.utils.log;

/* loaded from: classes.dex */
public class LogUtil {
    public static boolean DEBUG_LOG = true;
    private static java.lang.String className;
    private static int lineNumber;
    private static java.lang.String methodName;

    private static java.lang.String createLog(java.lang.String log) {
        java.lang.StringBuffer buffer = new java.lang.StringBuffer();
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append(")");
        buffer.append(log);
        return buffer.toString();
    }

    private static void getMethodNames(java.lang.StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    public static void v(java.lang.String msg) {
        if (DEBUG_LOG) {
            getMethodNames(new java.lang.Throwable().getStackTrace());
            android.util.Log.v(className, createLog(msg));
        }
    }

    public static void v(android.content.Context context, int resId) {
        v(context.getApplicationContext().getString(resId));
    }

    public static void d(java.lang.String msg) {
        if (DEBUG_LOG) {
            getMethodNames(new java.lang.Throwable().getStackTrace());
            android.util.Log.d(className, createLog(msg));
        }
    }

    public static void d(android.content.Context context, int resId) {
        d(context.getApplicationContext().getString(resId));
    }

    public static void i(java.lang.String msg) {
        if (DEBUG_LOG) {
            getMethodNames(new java.lang.Throwable().getStackTrace());
            android.util.Log.i(className, createLog(msg));
        }
    }

    public static void i(android.content.Context context, int resId) {
        i(context.getApplicationContext().getString(resId));
    }

    public static void w(java.lang.String msg) {
        if (DEBUG_LOG) {
            getMethodNames(new java.lang.Throwable().getStackTrace());
            android.util.Log.w(className, createLog(msg));
        }
    }

    public static void w(android.content.Context context, int resId) {
        w(context.getApplicationContext().getString(resId));
    }

    public static void e(java.lang.String msg) {
        if (DEBUG_LOG) {
            getMethodNames(new java.lang.Throwable().getStackTrace());
            android.util.Log.e(className, createLog(msg));
        }
    }

    public static void e(android.content.Context context, int resId) {
        e(context.getApplicationContext().getString(resId));
    }

    public static void wtf(java.lang.String message) {
        if (DEBUG_LOG) {
            getMethodNames(new java.lang.Throwable().getStackTrace());
            android.util.Log.wtf(className, createLog(message));
        }
    }

    public static void wtf(android.content.Context context, int resId) {
        wtf(context.getApplicationContext().getString(resId));
    }

    public static void log(java.lang.String tag, java.lang.String message, java.lang.Object... args) {
        java.lang.String log = message + java.util.Arrays.deepToString(args);
        android.util.Log.i(tag, log);
    }

    public static void logTrace(java.lang.String tag, java.lang.String message) {
        java.lang.StringBuffer traceLog = new java.lang.StringBuffer("");
        java.lang.StackTraceElement[] elements = java.lang.Thread.currentThread().getStackTrace();
        int length = elements.length;
        for (int i = 3; i < length; i++) {
            traceLog.append(elements[i].toString()).append("\n");
        }
        android.util.Log.i(tag, message + "\n" + ((java.lang.Object) traceLog));
    }
}
