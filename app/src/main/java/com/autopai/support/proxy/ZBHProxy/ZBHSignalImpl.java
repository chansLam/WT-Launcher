package com.autopai.support.proxy.ZBHProxy;

/* loaded from: classes.dex */
public class ZBHSignalImpl implements com.autopai.support.proxy.Interface.ISingalProxy {
    private static final java.lang.String LOGTAG = "F202SignalImpl";
    private android.car.Car mCar;
    private com.autopai.support.proxy.SingalImplProxy.ISignalChangeListener mSignalChangedListener;
    private android.car.wt.tbox.WtTBoxManager mWtTBoxManager;
    private android.car.wt.tbox.WtTBoxManager.ISignalListener mTboxSignalCallback = new android.car.wt.tbox.WtTBoxManager.ISignalListener() { // from class: com.autopai.support.proxy.ZBHProxy.ZBHSignalImpl.1
        public void onSignalChanged(int type, int signalLevel) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.ZBHProxy.ZBHSignalImpl.LOGTAG, "onSignalChanged type = " + type + "; signalLevel = " + signalLevel);
            if (com.autopai.support.proxy.ZBHProxy.ZBHSignalImpl.this.mSignalChangedListener != null) {
                com.autopai.support.proxy.ZBHProxy.ZBHSignalImpl.this.mSignalChangedListener.onTboxMobileSignalChanged(type, signalLevel, 0);
            }
        }
    };
    private final android.content.ServiceConnection mServiceConnectionListener = new android.content.ServiceConnection() { // from class: com.autopai.support.proxy.ZBHProxy.ZBHSignalImpl.2
        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName name, android.os.IBinder service) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.ZBHProxy.ZBHSignalImpl.LOGTAG, "onServiceConnected name = " + name);
            try {
                com.autopai.support.proxy.ZBHProxy.ZBHSignalImpl.this.mWtTBoxManager = (android.car.wt.tbox.WtTBoxManager) com.autopai.support.proxy.ZBHProxy.ZBHSignalImpl.this.mCar.getCarManager("wt_tbox");
                try {
                    com.autopai.support.proxy.ZBHProxy.ZBHSignalImpl.this.mWtTBoxManager.registerSignalListener(com.autopai.support.proxy.ZBHProxy.ZBHSignalImpl.this.mTboxSignalCallback);
                } catch (java.lang.Exception e) {
                    e.printStackTrace();
                }
            } catch (android.car.CarNotConnectedException e2) {
                com.autopai.support.utils.SLog.i(com.autopai.support.proxy.ZBHProxy.ZBHSignalImpl.LOGTAG, "mServiceConnectionListener e.msg = " + e2.getMessage());
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName name) {
            com.autopai.support.proxy.ZBHProxy.ZBHSignalImpl.this.mWtTBoxManager = null;
        }
    };

    public ZBHSignalImpl(android.content.Context cxt, com.autopai.support.proxy.SingalImplProxy.ISignalChangeListener listener) {
        this.mSignalChangedListener = listener;
        android.content.Context appCxt = cxt.getApplicationContext();
        try {
            this.mCar = android.car.Car.createCar(appCxt, this.mServiceConnectionListener, null);
            com.autopai.support.utils.SLog.d(LOGTAG, "ZBHSignalImpl mCar = " + this.mCar);
            if (this.mCar != null) {
                this.mCar.connect();
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.autopai.support.proxy.Interface.ISingalProxy
    public void setSignalChangeListener(com.autopai.support.proxy.SingalImplProxy.ISignalChangeListener listener) {
        this.mSignalChangedListener = listener;
    }

    @Override // com.autopai.support.proxy.Interface.ISingalProxy
    public void destroy() {
        try {
            if (this.mWtTBoxManager != null) {
                this.mWtTBoxManager.unregisterSignalListener(this.mTboxSignalCallback);
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        if (this.mCar != null) {
            this.mCar.disconnect();
        }
    }
}
