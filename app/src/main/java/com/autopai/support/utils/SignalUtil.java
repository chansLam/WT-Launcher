package com.autopai.support.utils;

/* loaded from: classes.dex */
public class SignalUtil {
    public static final java.lang.String TAG = "SignalUtil";
    public int[][] JG_SIGNAL_STRENGTH;
    private com.autopai.support.proxy.SingalImplProxy mSingalProxy;

    public SignalUtil(android.content.Context cxt, com.autopai.support.proxy.SingalImplProxy.ISignalChangeListener listener) {
        if (cxt != null) {
            initArray(cxt);
            this.mSingalProxy = new com.autopai.support.proxy.SingalImplProxy(cxt, listener);
        }
    }

    private void initArray(android.content.Context cxt) {
        com.autopai.support.utils.SLog.d(TAG, "initArray");
        this.JG_SIGNAL_STRENGTH = com.autopai.support.utils.SignalIcons.getInstance(cxt).JG_SIGNAL_STRENGTH;
    }

    public void onWindowVisibilityChanged(int visibility) {
        com.autopai.support.utils.SLog.i(TAG, "onWindowVisibilityChanged visibility =" + visibility);
    }

    public void setSignalChangeListener(com.autopai.support.proxy.SingalImplProxy.ISignalChangeListener listener) {
        this.mSingalProxy.setSignalChangeListener(listener);
    }

    public void destroy() {
        this.mSingalProxy.destroy();
        this.JG_SIGNAL_STRENGTH = (int[][]) null;
    }
}
