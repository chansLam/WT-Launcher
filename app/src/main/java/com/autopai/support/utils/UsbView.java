package com.autopai.support.utils;

/* loaded from: classes.dex */
public class UsbView extends android.widget.ImageView implements com.autopai.support.IChangeTheme {
    private android.content.Context mContext;
    private int mCurResId;
    private com.autopai.support.proxy.UsbImplProxy.IUsbChangeListener mListener;
    private com.autopai.support.proxy.UsbImplProxy mUsbImplProxy;

    public UsbView(android.content.Context context) {
        this(context, null);
    }

    public UsbView(android.content.Context context, android.util.AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UsbView(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mListener = new com.autopai.support.proxy.UsbImplProxy.IUsbChangeListener() { // from class: com.autopai.support.utils.UsbView.1
            @Override // com.autopai.support.proxy.UsbImplProxy.IUsbChangeListener
            public void onUsbChanged(boolean usbStatus) {
                com.autopai.support.utils.UsbView.this.showUsb(usbStatus);
            }
        };
        this.mContext = context.getApplicationContext();
        this.mUsbImplProxy = new com.autopai.support.proxy.UsbImplProxy(this.mContext);
        initLayout();
    }

    private void initLayout() {
        this.mCurResId = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(this.mContext, "sw_ic_usb");
        showUsb(this.mUsbImplProxy.getUsbStatus());
        this.mUsbImplProxy.setUsbChangeListener(this.mListener);
        changeTheme();
    }

    public void destroy() {
        this.mUsbImplProxy.destroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showUsb(boolean show) {
        if (show) {
            setVisibility(0);
        } else {
            setVisibility(8);
        }
    }

    @Override // com.autopai.support.IChangeTheme
    public void changeTheme() {
        android.graphics.drawable.Drawable drawable = com.autopai.support.utils.ResourceUtils.getInstance(this.mContext).getDrawable(this.mCurResId);
        setImageDrawable(drawable);
    }
}
