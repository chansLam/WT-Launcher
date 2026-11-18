package com.autopai.support.utils;

/* loaded from: classes.dex */
public class BluetoothView extends android.widget.RelativeLayout implements com.autopai.support.IChangeTheme {
    private static java.lang.String LOGTAG = "BluetoothView";
    private com.autopai.support.proxy.BluetoothImplProxy mBluetoothImplProxy;
    private com.autopai.support.view.TopImageView mBtImageView;
    private com.autopai.support.proxy.BluetoothImplProxy.IBluetoothStatusChangeListener mChangeListener;
    private android.content.Context mContext;
    private int mCurResId;
    private int mJGIconFlag;

    public BluetoothView(android.content.Context context) {
        this(context, null);
    }

    public BluetoothView(android.content.Context context, android.util.AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BluetoothView(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mChangeListener = new com.autopai.support.proxy.BluetoothImplProxy.IBluetoothStatusChangeListener() { // from class: com.autopai.support.utils.BluetoothView.1
            @Override // com.autopai.support.proxy.BluetoothImplProxy.IBluetoothStatusChangeListener
            public void onBluetoothChanged(boolean powerOn, boolean connect) {
                com.autopai.support.utils.BluetoothView.this.setImageResource(com.autopai.support.utils.BluetoothView.this.mBtImageView, com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(com.autopai.support.utils.BluetoothView.this.mContext, "jg_ic_bt_noconn"));
                com.autopai.support.utils.SLog.d(com.autopai.support.utils.BluetoothView.LOGTAG, "onBluetoothChanged powerOn = " + powerOn + "; connect = " + connect);
                if (connect) {
                    com.autopai.support.utils.BluetoothView.this.setImageResource(com.autopai.support.utils.BluetoothView.this.mBtImageView, com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(com.autopai.support.utils.BluetoothView.this.mContext, "jg_ic_bt"));
                    if (com.autopai.support.utils.BluetoothView.this.shouldDisplayJGBluetoothIcon()) {
                        com.autopai.support.utils.BluetoothView.this.setVisibility(0);
                    } else {
                        com.autopai.support.utils.BluetoothView.this.setVisibility(8);
                    }
                } else if (com.autopai.support.utils.BluetoothView.this.shouldDisplayJGBluetoothIcon()) {
                    com.autopai.support.utils.BluetoothView.this.setVisibility(0);
                } else {
                    com.autopai.support.utils.BluetoothView.this.setVisibility(8);
                }
                if (powerOn) {
                    if (com.autopai.support.utils.BluetoothView.this.shouldDisplayJGBluetoothIcon()) {
                        com.autopai.support.utils.BluetoothView.this.setVisibility(0);
                    } else {
                        com.autopai.support.utils.BluetoothView.this.setVisibility(8);
                    }
                    if (connect) {
                        com.autopai.support.utils.BluetoothView.this.setImageResource(com.autopai.support.utils.BluetoothView.this.mBtImageView, com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(com.autopai.support.utils.BluetoothView.this.mContext, "jg_ic_bt"));
                        return;
                    }
                    return;
                }
                com.autopai.support.utils.BluetoothView.this.setVisibility(8);
            }
        };
        this.mContext = context.getApplicationContext();
        this.mBluetoothImplProxy = new com.autopai.support.proxy.BluetoothImplProxy(this.mContext);
        this.mBluetoothImplProxy.setBluetoothStatusChangeListener(this.mChangeListener);
        initLayout(context);
    }

    public void destroy() {
        this.mBluetoothImplProxy.destroy();
    }

    public void setJGIconFlag(int flag) {
        this.mJGIconFlag = flag;
        this.mJGIconFlag = 0;
        if (!shouldDisplayJGBluetoothIcon()) {
            setVisibility(8);
        }
    }

    private void initLayout(android.content.Context context) {
        android.view.LayoutInflater.from(context).inflate(com.autopai.support.utils.AutoPaiResource.getIdentifierByLayout(this.mContext, "jg_bluetooth_layout"), this);
        this.mBtImageView = (com.autopai.support.view.TopImageView) findViewById(com.autopai.support.utils.AutoPaiResource.getIdentifierById(this.mContext, "bluetooth_img"));
        if (this.mBluetoothImplProxy.getBluetoothPowerOnStatus()) {
            setVisibility(0);
            if (this.mBluetoothImplProxy.getBluetoothConnectStatus()) {
                setImageResource(this.mBtImageView, com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(this.mContext, "jg_ic_bt"));
                return;
            } else {
                setImageResource(this.mBtImageView, com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(this.mContext, "jg_ic_bt_noconn"));
                return;
            }
        }
        setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldDisplayJGBluetoothIcon() {
        if (this.mJGIconFlag == 0) {
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setImageResource(android.widget.ImageView imgView, int resId) {
        this.mCurResId = resId;
        changeTheme();
    }

    @Override // com.autopai.support.IChangeTheme
    public void changeTheme() {
        com.autopai.support.utils.SLog.d(LOGTAG, "changeTheme this = " + this + "; mCurResId = " + this.mCurResId);
        if (this.mCurResId != 0) {
            android.graphics.drawable.Drawable drawable = com.autopai.support.utils.ResourceUtils.getInstance(this.mContext).getDrawable(this.mCurResId);
            this.mBtImageView.setImageDrawable(drawable);
        }
    }
}
