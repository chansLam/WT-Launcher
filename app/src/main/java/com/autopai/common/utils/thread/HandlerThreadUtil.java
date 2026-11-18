package com.autopai.common.utils.thread;

/* loaded from: classes.dex */
public class HandlerThreadUtil {
    private static android.os.Handler mWorkerHandler;
    private static android.os.HandlerThread mWorkerThread = new android.os.HandlerThread("launcher_loader");
    private static android.os.Handler mMainHandler = new android.os.Handler(android.os.Looper.getMainLooper());
    private static final int mMainTid = android.os.Process.myTid();

    static {
        mWorkerThread.start();
        mWorkerHandler = new android.os.Handler(mWorkerThread.getLooper());
    }

    public static void runOnWorkerThread(java.lang.Runnable runnable) {
        if (mWorkerThread.getThreadId() == android.os.Process.myTid()) {
            runnable.run();
        } else {
            mWorkerHandler.post(runnable);
        }
    }

    public static void runOnMainThread(java.lang.Runnable runnable) {
        if (android.os.Process.myTid() != mMainTid) {
            mMainHandler.post(runnable);
        } else {
            runnable.run();
        }
    }

    public static void runOnMainThread(android.os.Handler handler, java.lang.Runnable runnable) {
        if (mWorkerThread.getThreadId() == android.os.Process.myTid()) {
            handler.post(runnable);
        } else {
            runnable.run();
        }
    }
}
