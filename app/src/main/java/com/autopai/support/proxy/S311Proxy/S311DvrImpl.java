package com.autopai.support.proxy.S311Proxy;

/* loaded from: classes.dex */
public class S311DvrImpl implements com.autopai.support.proxy.Interface.IDvrProxy {
    private static final java.lang.String COM_INCALL_APPS_SETTING_CAMERA = "com.incall.apps.setting.camera";
    private static final int DVR_DEFAULT = -1;
    private static final java.lang.String LOGTAG = "S311DvrImpl";
    private com.autopai.support.proxy.DvrImplProxy.IDvrChangeListener mChangeListener;
    private android.content.Context mContext;
    private int mDvrStatus = -1;
    private com.autopai.support.proxy.S311Proxy.S311DvrImpl.MyReceiver mReceiver;

    public S311DvrImpl(android.content.Context cxt) {
        this.mContext = cxt.getApplicationContext();
        initDvrStatus();
        initReceiver();
    }

    private void initDvrStatus() {
        try {
            int camera_status = android.provider.Settings.System.getInt(this.mContext.getContentResolver(), "camera_status", 0);
            com.autopai.support.utils.SLog.i(LOGTAG, "initDvrView camera_status = " + camera_status);
            this.mDvrStatus = camera_status;
        } catch (java.lang.Exception e) {
            com.autopai.support.utils.SLog.i(LOGTAG, "initDvrView exception");
        }
    }

    private void initReceiver() {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("com.incall.apps.setting.camera");
        if (this.mReceiver == null) {
            this.mReceiver = new com.autopai.support.proxy.S311Proxy.S311DvrImpl.MyReceiver();
        }
        this.mContext.registerReceiver(this.mReceiver, intentFilter);
    }

    @Override // com.autopai.support.proxy.Interface.IDvrProxy
    public void setDvrChangeListener(com.autopai.support.proxy.DvrImplProxy.IDvrChangeListener listener) {
        this.mChangeListener = listener;
    }

    @Override // com.autopai.support.proxy.Interface.IDvrProxy
    public int getDvrStatus() {
        return this.mDvrStatus;
    }

    @Override // com.autopai.support.proxy.Interface.IDvrProxy
    public void destroy() {
        if (this.mReceiver != null) {
            this.mContext.unregisterReceiver(this.mReceiver);
            this.mReceiver = null;
        }
    }

    private class MyReceiver extends android.content.BroadcastReceiver {
        private MyReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if (intent != null) {
                com.autopai.support.utils.SLog.i(com.autopai.support.proxy.S311Proxy.S311DvrImpl.LOGTAG, "MyReceiver intent.getAction() = " + intent.getAction());
                if ("com.incall.apps.setting.camera".equals(intent.getAction())) {
                    int state = intent.getIntExtra("status", -1);
                    com.autopai.support.utils.SLog.i(com.autopai.support.proxy.S311Proxy.S311DvrImpl.LOGTAG, "MyReceiver state = " + state);
                    com.autopai.support.proxy.S311Proxy.S311DvrImpl.this.mDvrStatus = state;
                    com.autopai.support.proxy.S311Proxy.S311DvrImpl.this.mChangeListener.onDvrChanged(com.autopai.support.proxy.S311Proxy.S311DvrImpl.this.mDvrStatus);
                }
            }
        }
    }
}
