package com.autopai.support.view;

/* loaded from: classes.dex */
public class TopLinearLayout extends android.widget.FrameLayout implements com.autopai.support.utils.WifiView.IWifiChange, com.autopai.support.proxy.SingalImplProxy.ISignalChangeListener {
    private static final java.lang.String TAG = "TopLinearLayout";
    private com.autopai.support.utils.SignalView mSignalView;
    private com.autopai.support.utils.WifiView mWifiView;

    public TopLinearLayout(android.content.Context context) {
        this(context, null);
    }

    public TopLinearLayout(android.content.Context context, android.util.AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopLinearLayout(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        android.view.LayoutInflater.from(context).inflate(com.autopai.support.utils.AutoPaiResource.getIdentifierByLayout(context, "jg_wifi4g_layout"), this);
        this.mSignalView = (com.autopai.support.utils.SignalView) findViewById(com.autopai.support.utils.AutoPaiResource.getIdentifierById(context, "iv_signal"));
        this.mWifiView = (com.autopai.support.utils.WifiView) findViewById(com.autopai.support.utils.AutoPaiResource.getIdentifierById(context, "toprightview_wifi"));
        this.mWifiView.setWifiChangeListener(this);
        setVisibility(8);
    }

    public void changeTheme() {
        if (this.mSignalView != null) {
            this.mSignalView.changeTheme();
        }
        if (this.mWifiView != null) {
            this.mWifiView.changeTheme();
        }
    }

    public void destroy() {
        if (this.mSignalView != null) {
            this.mSignalView.changeTheme();
        }
        if (this.mWifiView != null) {
            this.mWifiView.changeTheme();
        }
    }

    @Override // com.autopai.support.utils.WifiView.IWifiChange
    public void wifiChanged(boolean status) {
        com.autopai.support.utils.SLog.d(TAG, "wifiChanged status = " + status);
        if (status) {
            this.mSignalView.setVisibility(8);
        } else {
            this.mSignalView.setVisibility(0);
        }
    }

    @Override // com.autopai.support.proxy.SingalImplProxy.ISignalChangeListener
    public void onTboxMobileSignalChanged(int type, int level, int value) {
        if (this.mWifiView != null) {
            com.autopai.support.utils.SLog.d(TAG, "onTboxMobileSignalChanged type = " + type + "; level = " + level);
            if (level <= 0) {
                post(new java.lang.Runnable() { // from class: com.autopai.support.view.TopLinearLayout.1
                    @Override // java.lang.Runnable
                    public void run() {
                        com.autopai.support.view.TopLinearLayout.this.mWifiView.updateWifiIcons();
                    }
                });
            }
        }
    }
}
