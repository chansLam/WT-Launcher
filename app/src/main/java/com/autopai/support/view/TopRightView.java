package com.autopai.support.view;

/* loaded from: classes.dex */
public class TopRightView extends android.widget.RelativeLayout {
    private static final java.lang.String COM_INCALL_APPS_SETTING_CAMERA = "com.incall.apps.setting.camera";
    private static final java.lang.String TAG = "TopRightView";
    private android.os.Handler handler;
    boolean isRegster;
    private com.autopai.support.utils.AirConditionView mAirView;
    private com.autopai.support.utils.BluetoothView mBluetoothView;
    private com.autopai.support.utils.Clock mClockView;
    private com.autopai.support.view.TopLinearLayout mContainer;
    private android.content.Context mContext;
    private com.autopai.support.utils.DvrView mDvrView;
    private int mJGIconFlag;
    private com.autopai.support.utils.MediaStatusView mMediaStatusView;
    private com.autopai.support.utils.MuteView mMuteView;
    private com.autopai.support.utils.SignalView mSignalView;
    private int mThemeType;
    private com.autopai.support.utils.UsbView mUsbView;
    private com.autopai.support.utils.WifiView mWifiView;

    public TopRightView(android.content.Context context) {
        this(context, null);
    }

    public TopRightView(android.content.Context context, android.util.AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopRightView(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.handler = new com.autopai.support.view.TopRightView.MyHandler(this);
        this.mThemeType = 0;
        this.isRegster = false;
        com.autopai.support.utils.SLog.d(TAG, "TopRightView construct start");
        com.autopai.support.AppGlobal.AppInfo info = com.autopai.support.AppGlobal.getInstance(context).getAppInfo();
        if (info != null) {
            com.autopai.support.utils.SLog.d(TAG, "TopRightView construct start info.sdkversion = " + info.sdkVersion + "; info.packageName = " + info.packageName + "; info.appVersionName = " + info.appVersionName);
        }
        this.mContext = context;
        initJGLayout(context);
        setThemeType(1);
    }

    public void setJGIconFlag(int flag) {
        this.mJGIconFlag = flag;
        this.mJGIconFlag = 0;
        if (this.mBluetoothView != null) {
            this.mBluetoothView.setJGIconFlag(this.mJGIconFlag);
        }
        if (this.mWifiView != null) {
            this.mWifiView.setJGIconFlag(this.mJGIconFlag);
        }
        if (this.mDvrView != null && !shouldDisplayJGDvrIcon()) {
            this.mDvrView.setVisibility(8);
        }
        if (this.mMuteView != null && !shouldDisplayJGHornIcon()) {
            this.mMuteView.setVisibility(8);
        }
        if (this.mClockView != null && !shouldDisplayJGClockIcon()) {
            this.mClockView.setVisibility(8);
        }
    }

    public int getCurTheme() {
        return this.mThemeType;
    }

    public boolean isWhiteTheme() {
        return this.mThemeType == 0;
    }

    public void setThemeType(int type) {
        com.autopai.support.utils.ResourceUtils.getInstance(this.mContext).setCurTheme(type);
        if (this.mThemeType != type) {
            this.mThemeType = type;
            updatViews();
        }
    }

    private void updatViews() {
        com.autopai.support.utils.SLog.d(TAG, "updatViews this = " + this);
        if (this.mBluetoothView != null) {
            this.mBluetoothView.changeTheme();
        }
        if (this.mMuteView != null) {
            this.mMuteView.changeTheme();
        }
        if (this.mContainer != null) {
            this.mContainer.changeTheme();
        }
        if (this.mSignalView != null) {
            this.mSignalView.changeTheme();
        }
        if (this.mWifiView != null) {
            this.mWifiView.changeTheme();
        }
        if (this.mClockView != null) {
            this.mClockView.changeTheme();
        }
        if (this.mMediaStatusView != null) {
            this.mMediaStatusView.changeTheme();
        }
        if (this.mDvrView != null) {
            this.mDvrView.changeTheme();
        }
        if (this.mUsbView != null) {
            this.mUsbView.changeTheme();
        }
        if (this.mAirView != null) {
            this.mAirView.changeTheme();
        }
    }

    private void initJGLayout(android.content.Context context) {
        android.view.LayoutInflater.from(context).inflate(com.autopai.support.utils.AutoPaiResource.getIdentifierByLayout(this.mContext, "jg_toprightview_layout"), this);
        initBluetoothView(context);
        initWifiView(context);
        initSignalView(context);
        initContainerView(context);
        initDvrView(context);
        initMuteView(context);
        initClockView(context);
        initMediaStatusView(context);
        initUsbView(context);
        initAirConditionView(context);
        initJGReceiver();
    }

    private void initAirConditionView(android.content.Context context) {
        this.mAirView = (com.autopai.support.utils.AirConditionView) findViewById(com.autopai.support.utils.AutoPaiResource.getIdentifierById(this.mContext, "toprightview_aircondition"));
    }

    private void initUsbView(android.content.Context context) {
        this.mUsbView = (com.autopai.support.utils.UsbView) findViewById(com.autopai.support.utils.AutoPaiResource.getIdentifierById(this.mContext, "toprightview_usb"));
    }

    private void initMediaStatusView(android.content.Context context) {
    }

    private void initClockView(android.content.Context context) {
        this.mClockView = (com.autopai.support.utils.Clock) findViewById(com.autopai.support.utils.AutoPaiResource.getIdentifierById(this.mContext, "toprightview_clock"));
    }

    private void initBluetoothView(android.content.Context context) {
        this.mBluetoothView = (com.autopai.support.utils.BluetoothView) findViewById(com.autopai.support.utils.AutoPaiResource.getIdentifierById(this.mContext, "toprightview_bluetooth"));
    }

    private void initJGReceiver() {
    }

    private void initWifiView(android.content.Context context) {
        this.mWifiView = (com.autopai.support.utils.WifiView) findViewById(com.autopai.support.utils.AutoPaiResource.getIdentifierById(this.mContext, "toprightview_wifi"));
        if (com.autopai.support.AppGlobal.getInstance(getContext()).hasDoubleAirCondition()) {
            android.util.Log.d(TAG, "initWifiView mWifiView GONE");
            this.mWifiView.setJGIconFlag(1);
        }
    }

    private void initDvrView(android.content.Context context) {
        this.mDvrView = (com.autopai.support.utils.DvrView) findViewById(com.autopai.support.utils.AutoPaiResource.getIdentifierById(this.mContext, "toprightview_dvr"));
    }

    private void initSignalView(android.content.Context context) {
        this.mSignalView = (com.autopai.support.utils.SignalView) findViewById(com.autopai.support.utils.AutoPaiResource.getIdentifierById(this.mContext, "iv_signal"));
        if (com.autopai.support.AppGlobal.getInstance(getContext()).hasDoubleAirCondition()) {
            android.util.Log.d(TAG, "initSignalView mSignalView GONE");
            this.mSignalView.setVisibility(8);
        }
    }

    private void initContainerView(android.content.Context context) {
        if (com.autopai.support.AppGlobal.getInstance(getContext()).hasDoubleAirCondition()) {
            android.view.ViewStub stub = (android.view.ViewStub) findViewById(com.autopai.support.utils.AutoPaiResource.getIdentifierById(this.mContext, "iv_container"));
            android.util.Log.d(TAG, "initContainerView stub = " + stub);
            if (stub != null) {
                this.mContainer = (com.autopai.support.view.TopLinearLayout) stub.inflate();
                this.mContainer.setVisibility(0);
            }
        }
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
    }

    private void initMuteView(android.content.Context context) {
        this.mMuteView = (com.autopai.support.utils.MuteView) findViewById(com.autopai.support.utils.AutoPaiResource.getIdentifierById(this.mContext, "toprightview_horn"));
    }

    public void destroy() {
        if (this.mWifiView != null) {
            this.mWifiView.destroy();
            this.mWifiView = null;
        }
        if (this.mBluetoothView != null) {
            this.mBluetoothView.destroy();
            this.mBluetoothView = null;
        }
        if (this.mMuteView != null) {
            this.mMuteView.destroy();
            this.mMuteView = null;
        }
        if (this.mContainer != null) {
            this.mContainer.destroy();
            this.mContainer = null;
        }
        if (this.mSignalView != null) {
            this.mSignalView.destroy();
            this.mSignalView = null;
        }
        if (this.mDvrView != null) {
            this.mDvrView.destroy();
            this.mDvrView = null;
        }
        if (this.mMediaStatusView != null) {
            this.mMediaStatusView.destroy();
            this.mMediaStatusView = null;
        }
        if (this.mUsbView != null) {
            this.mUsbView.destroy();
            this.mUsbView = null;
        }
        if (this.mAirView != null) {
            this.mAirView.destroy();
            this.mAirView = null;
        }
        com.autopai.support.utils.SLog.i(TAG, "destroy start");
    }

    private static class MyHandler extends android.os.Handler {
        private java.lang.ref.WeakReference<com.autopai.support.view.TopRightView> wView;

        public MyHandler(com.autopai.support.view.TopRightView view) {
            this.wView = new java.lang.ref.WeakReference<>(view);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            com.autopai.support.view.TopRightView view = this.wView.get();
            if (view == null) {
            }
        }
    }

    private boolean shouldDisplayJGDvrIcon() {
        if (this.mJGIconFlag == 0) {
        }
        return true;
    }

    private boolean shouldDisplayJGHornIcon() {
        if (this.mJGIconFlag == 0) {
        }
        return true;
    }

    private boolean shouldDisplayJGSignalIcon() {
        if (this.mJGIconFlag == 0) {
        }
        return true;
    }

    private boolean shouldDisplayJGClockIcon() {
        if (this.mJGIconFlag == 0) {
        }
        return true;
    }
}
