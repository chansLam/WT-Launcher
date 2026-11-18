package com.autopai.support.utils;

/* loaded from: classes.dex */
public class WifiView extends android.widget.RelativeLayout implements com.autopai.support.IChangeTheme {
    private static java.lang.String LOG = "WifiView";
    private boolean isRegister;
    android.content.BroadcastReceiver mBroadcastReceiver;
    private int mCurResId;
    private int mInetCondition;
    private int mJGIconFlag;
    private com.autopai.support.utils.WifiView.IWifiChange mListener;
    boolean mWifiConnected;
    boolean mWifiEnabled;
    private int mWifiIconId;
    private int mWifiLevel;
    private android.net.wifi.WifiManager mWifiManager;
    private int mWifiRssi;
    private java.lang.String mWifiSsid;
    private com.autopai.support.view.TopImageView wifiImg;

    public interface IWifiChange {
        void wifiChanged(boolean z);
    }

    public WifiView(android.content.Context context) {
        this(context, null);
    }

    public WifiView(android.content.Context context, android.util.AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WifiView(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mWifiLevel = -1;
        this.mInetCondition = 0;
        this.mWifiIconId = 0;
        this.isRegister = false;
        this.mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.autopai.support.utils.WifiView.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                java.lang.String action = intent.getAction();
                if (action.equals("android.net.wifi.RSSI_CHANGED") || action.equals("android.net.wifi.WIFI_STATE_CHANGED") || action.equals("android.net.wifi.STATE_CHANGE")) {
                    com.autopai.support.utils.SLog.i(com.autopai.support.utils.WifiView.LOG, "WifiView broadcast action=" + action);
                    com.autopai.support.utils.WifiView.this.updateWifiState(intent);
                }
            }
        };
        initLayout(context);
        android.content.IntentFilter filter = new android.content.IntentFilter();
        filter.addAction("android.net.wifi.RSSI_CHANGED");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        context.registerReceiver(this.mBroadcastReceiver, filter);
        this.isRegister = true;
    }

    public void setJGIconFlag(int flag) {
        this.mJGIconFlag = flag;
        if (!shouldDisplayJGWifiIcon()) {
            setVisibility(8);
        }
    }

    public void destroy() {
        if (this.isRegister) {
            this.isRegister = false;
            getContext().unregisterReceiver(this.mBroadcastReceiver);
        }
    }

    private void initLayout(android.content.Context context) {
        android.view.LayoutInflater.from(context).inflate(com.autopai.support.utils.AutoPaiResource.getIdentifierByLayout(context, "imgview_layout"), this);
        this.wifiImg = (com.autopai.support.view.TopImageView) findViewById(com.autopai.support.utils.AutoPaiResource.getIdentifierById(context, "imgview_img"));
        this.mWifiManager = (android.net.wifi.WifiManager) context.getSystemService("wifi");
        int wifiState = 1;
        if (this.mWifiManager != null) {
            wifiState = this.mWifiManager.getWifiState();
            com.autopai.support.utils.SLog.d(LOG, "initLayout mWifiManager.getWifiState() = " + wifiState);
            if (wifiState == 3) {
                this.mWifiLevel = getConnectedWifiRSSI();
                com.autopai.support.utils.SLog.d(LOG, "initLayout wifiLevel = " + this.mWifiLevel);
                int[][] strength = com.autopai.support.utils.WifiIcons.getInstance(getContext()).JG_WIFI_SIGNAL_STRENGTH;
                if (this.mWifiLevel >= 0 && this.mWifiLevel < strength[0].length) {
                    this.mWifiIconId = strength[this.mInetCondition][this.mWifiLevel];
                    changeTheme();
                } else {
                    return;
                }
            }
        }
        setWifiViewVisible(wifiState == 3 ? 0 : 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWifiState(android.content.Intent intent) {
        java.lang.String action = intent.getAction();
        if (action.equals("android.net.wifi.WIFI_STATE_CHANGED")) {
            this.mWifiEnabled = intent.getIntExtra("wifi_state", 4) == 3;
        } else if (action.equals("android.net.wifi.STATE_CHANGE")) {
            android.net.NetworkInfo networkInfo = (android.net.NetworkInfo) intent.getParcelableExtra("networkInfo");
            boolean wasConnected = this.mWifiConnected;
            this.mWifiConnected = networkInfo != null && networkInfo.isConnected();
            if (this.mWifiConnected && !wasConnected) {
                android.net.wifi.WifiInfo info = (android.net.wifi.WifiInfo) intent.getParcelableExtra("wifiInfo");
                if (info == null) {
                    this.mWifiManager.getConnectionInfo();
                }
            } else if (!this.mWifiConnected) {
                this.mWifiSsid = null;
            }
        } else if (action.equals("android.net.wifi.RSSI_CHANGED")) {
            int oldWifiLevel = this.mWifiLevel;
            this.mWifiLevel = getConnectedWifiRSSI();
            if (oldWifiLevel != this.mWifiLevel) {
                com.autopai.support.utils.SLog.i(LOG, "WifiView get wifi mWifiLevel=" + this.mWifiLevel);
            } else {
                return;
            }
        }
        updateWifiIcons();
    }

    private int getConnectedWifiRSSI() {
        android.net.wifi.WifiInfo mWifiinfo = this.mWifiManager.getConnectionInfo();
        if (mWifiinfo == null) {
            return 0;
        }
        java.lang.String ssid = mWifiinfo.getSSID();
        int wifiLevel = android.net.wifi.WifiManager.calculateSignalLevel(mWifiinfo.getRssi(), com.autopai.support.utils.WifiIcons.getInstance(getContext()).WIFI_LEVEL_COUNT);
        com.autopai.support.utils.SLog.d(LOG, "WifiView wifiLevel : " + wifiLevel + ",ssid=" + ssid);
        return wifiLevel;
    }

    public void updateWifiIcons() {
        com.autopai.support.utils.SLog.d(LOG, "updateWifiIcons mWifiEnabled =" + this.mWifiEnabled + ";mWifiConnected=" + this.mWifiConnected);
        if (!this.mWifiEnabled) {
            setWifiViewVisible(8);
            return;
        }
        setWifiViewVisible(0);
        if (this.mWifiConnected) {
            int[][] strength = com.autopai.support.utils.WifiIcons.getInstance(getContext()).JG_WIFI_SIGNAL_STRENGTH;
            if (this.mWifiLevel < 0 || this.mWifiLevel >= strength[0].length) {
                com.autopai.support.utils.SLog.d(LOG, "updateWifiIcons level outofbounds mWifiLevel = " + this.mWifiLevel);
                return;
            }
            this.mWifiIconId = strength[this.mInetCondition][this.mWifiLevel];
            setWifiViewVisible(0);
            changeTheme();
            return;
        }
        setWifiViewVisible(8);
    }

    private void doWifiChanged() {
        if (this.mListener != null) {
            boolean status = this.mWifiEnabled;
            if (status) {
                if (this.mWifiConnected) {
                    status = true;
                } else {
                    status = false;
                }
            }
            this.mListener.wifiChanged(status);
        }
    }

    private void setWifiViewVisible(int visible) {
        if (shouldDisplayJGWifiIcon()) {
            this.wifiImg.setVisibility(visible);
            setVisibility(visible);
        } else {
            setVisibility(8);
        }
        doWifiChanged();
    }

    private boolean shouldDisplayJGWifiIcon() {
        return this.mJGIconFlag == 0;
    }

    @Override // com.autopai.support.IChangeTheme
    public void changeTheme() {
        if (this.mWifiIconId != 0) {
            android.graphics.drawable.Drawable drawable = com.autopai.support.utils.ResourceUtils.getInstance(getContext()).getDrawable(this.mWifiIconId);
            this.wifiImg.setImageDrawable(drawable);
        }
    }

    public void setWifiChangeListener(com.autopai.support.utils.WifiView.IWifiChange listener) {
        this.mListener = listener;
    }
}
