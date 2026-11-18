package com.autopai.applist;

/* loaded from: classes.dex */
public class ToastUtils {
    private static java.lang.ref.WeakReference<com.autopai.applist.view.CustomToast> mToast;

    public static void showToast(android.content.Context context, java.lang.String content, int time) {
        if (mToast == null) {
            mToast = new java.lang.ref.WeakReference<>(new com.autopai.applist.view.CustomToast(context));
            mToast.get().initToast(content);
        } else if (mToast.get() != null) {
            mToast.get().setText(content);
        } else {
            mToast = new java.lang.ref.WeakReference<>(new com.autopai.applist.view.CustomToast(context));
            mToast.get().initToast(content);
        }
        mToast.get().showToast(time);
    }
}
