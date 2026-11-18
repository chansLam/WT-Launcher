package com.autopai.support.proxy.S311Proxy;

/* loaded from: classes.dex */
public class S311BluetoothImpl implements com.autopai.support.proxy.Interface.IBluetoothProxy {
    private static final java.lang.String LOGTAG = "S311BluetoothImpl";
    private boolean isConnect;
    private boolean isPowerOn;
    private com.autopai.support.proxy.BluetoothImplProxy.IBluetoothStatusChangeListener mChangeListener;
    com.incall.proxy.bt.BtPhoneManager.OnPowerChangedListener mOnPowerChangedListener = new com.incall.proxy.bt.BtPhoneManager.OnPowerChangedListener() { // from class: com.autopai.support.proxy.S311Proxy.S311BluetoothImpl.1
        public void onPowerChanged(int powerState) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311BluetoothImpl.LOGTAG, "onPowerChanged powerState = " + powerState);
            com.autopai.support.proxy.S311Proxy.S311BluetoothImpl.this.isPowerOn = powerState == 12;
            if (com.autopai.support.proxy.S311Proxy.S311BluetoothImpl.this.mChangeListener != null) {
                com.autopai.support.proxy.S311Proxy.S311BluetoothImpl.this.mChangeListener.onBluetoothChanged(com.autopai.support.proxy.S311Proxy.S311BluetoothImpl.this.isPowerOn, com.autopai.support.proxy.S311Proxy.S311BluetoothImpl.this.isConnect);
            }
        }
    };
    com.incall.proxy.bt.BtPhoneManager.OnHfpConnectedListener mHfpConnectedListener = new com.incall.proxy.bt.BtPhoneManager.OnHfpConnectedListener() { // from class: com.autopai.support.proxy.S311Proxy.S311BluetoothImpl.2
        public void onHfpConnectionChanged(int status) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311BluetoothImpl.LOGTAG, "onHfpConnectionChanged status = " + status);
            com.autopai.support.proxy.S311Proxy.S311BluetoothImpl.this.isConnect = status == 2;
            if (com.autopai.support.proxy.S311Proxy.S311BluetoothImpl.this.mChangeListener != null) {
                com.autopai.support.proxy.S311Proxy.S311BluetoothImpl.this.mChangeListener.onBluetoothChanged(com.autopai.support.proxy.S311Proxy.S311BluetoothImpl.this.isPowerOn, com.autopai.support.proxy.S311Proxy.S311BluetoothImpl.this.isConnect);
            }
        }
    };
    private com.incall.proxy.bt.BtPhoneManager mBtPhoneManager = com.incall.proxy.bt.BtPhoneManager.getInstance();

    public S311BluetoothImpl(android.content.Context cxt) {
        this.mBtPhoneManager.registerOnPowerChangedListener(this.mOnPowerChangedListener);
        this.mBtPhoneManager.registerOnHfpConnectedListener(this.mHfpConnectedListener);
        this.isPowerOn = this.mBtPhoneManager.getBtPowerStatus() == 12;
        this.isConnect = this.mBtPhoneManager.getHfpConnectStatus() == 2;
        com.autopai.support.utils.SLog.d(LOGTAG, "isPowerOn is " + this.isPowerOn + " isConnect is " + this.isConnect);
    }

    @Override // com.autopai.support.proxy.Interface.IBluetoothProxy
    public void setBluetoothStatusChangeListener(com.autopai.support.proxy.BluetoothImplProxy.IBluetoothStatusChangeListener listener) {
        this.mChangeListener = listener;
    }

    @Override // com.autopai.support.proxy.Interface.IBluetoothProxy
    public boolean getBluetoothPowerOnStatus() {
        return this.isPowerOn;
    }

    @Override // com.autopai.support.proxy.Interface.IBluetoothProxy
    public boolean getBluetoothConnectStatus() {
        return this.isConnect;
    }

    @Override // com.autopai.support.proxy.Interface.IBluetoothProxy
    public void destroy() {
        if (this.mBtPhoneManager != null) {
            this.mBtPhoneManager.unregisterOnPowerChangedListener(this.mOnPowerChangedListener);
            this.mBtPhoneManager.unregisterOnHfpConnectedListener(this.mHfpConnectedListener);
        }
    }
}
