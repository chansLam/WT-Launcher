package com.autopai.support.proxy.ZBHProxy;

/* loaded from: classes.dex */
public class ZBHMuteImpl implements com.autopai.support.proxy.Interface.IMuteProxy {
    private static final java.lang.String LOGTAG = "ZBHMuteImpl";
    private static final java.lang.String MASTER_MUTE_ACTION = "android.media.MASTER_MUTE_CHANGED_ACTION";
    private boolean isMute;
    private android.content.Context mContext;
    private com.autopai.support.proxy.MuteImplProxy.IMuteChangeListener mMuteChangeListener;
    private android.content.BroadcastReceiver mMuteReceiver = new android.content.BroadcastReceiver() { // from class: com.autopai.support.proxy.ZBHProxy.ZBHMuteImpl.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            java.lang.String action = intent.getAction();
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.ZBHProxy.ZBHMuteImpl.LOGTAG, "onReceive action = " + action);
            switch (action) {
                case "android.media.MASTER_MUTE_CHANGED_ACTION":
                    com.autopai.support.proxy.ZBHProxy.ZBHMuteImpl.this.setMuteStatus();
                    break;
            }
        }
    };

    public ZBHMuteImpl(android.content.Context cxt) {
        this.mContext = cxt.getApplicationContext();
        android.content.IntentFilter filter = new android.content.IntentFilter();
        filter.addAction(MASTER_MUTE_ACTION);
        this.mContext.registerReceiver(this.mMuteReceiver, filter);
        setMuteStatus();
    }

    @Override // com.autopai.support.proxy.Interface.IMuteProxy
    public void setMuteChangeListener(com.autopai.support.proxy.MuteImplProxy.IMuteChangeListener listener) {
        this.mMuteChangeListener = listener;
    }

    @Override // com.autopai.support.proxy.Interface.IMuteProxy
    public boolean getMuteStatus() {
        return this.isMute;
    }

    @Override // com.autopai.support.proxy.Interface.IMuteProxy
    public void destroy() {
        if (this.mMuteReceiver != null) {
            this.mContext.unregisterReceiver(this.mMuteReceiver);
        }
    }

    private boolean getSystemMuteStatus() {
        android.media.AudioManager mgr = (android.media.AudioManager) this.mContext.getSystemService("audio");
        try {
            java.lang.Class clazz = java.lang.Class.forName("android.media.AudioManager");
            java.lang.reflect.Method m = clazz.getMethod("isMasterMute", (java.lang.Class[]) null);
            java.lang.Object muteStatus = m.invoke(mgr, (java.lang.Object[]) null);
            com.autopai.support.utils.SLog.d(LOGTAG, "getSystemMuteStatus muteStatus = " + muteStatus);
            return ((java.lang.Boolean) muteStatus).booleanValue();
        } catch (java.lang.Exception e) {
            com.autopai.support.utils.SLog.d(LOGTAG, "getSystemMuteStatus muteStatus exception ");
            e.printStackTrace();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMuteStatus() {
        this.isMute = getSystemMuteStatus();
        if (this.mMuteChangeListener != null) {
            this.mMuteChangeListener.onMuteChanged(this.isMute);
        }
    }
}
