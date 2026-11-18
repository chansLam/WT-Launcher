package com.autopai.support.proxy.ZBHProxy;

/* loaded from: classes.dex */
public class ZBHDvrImpl implements com.autopai.support.proxy.Interface.IDvrProxy {
    private static final java.lang.String LOGTAG = "ZBHDvrImpl";
    private android.car.Car mCar;
    private com.autopai.support.proxy.DvrImplProxy.IDvrChangeListener mChangeListener;
    private android.content.Context mContext;
    private android.car.wt.WtCarDVRManager mDvrMgr;
    private int mDvrStatus = 0;
    private android.car.wt.WtCarDVRManager.WtCarDVREventCallback mDvrCallback = new android.car.wt.WtCarDVRManager.WtCarDVREventCallback() { // from class: com.autopai.support.proxy.ZBHProxy.ZBHDvrImpl.1
        public void onChangeEvent(android.car.hardware.CarPropertyValue carPropertyValue) {
            try {
                int status = ((java.lang.Integer) carPropertyValue.getValue()).intValue();
                com.autopai.support.utils.SLog.d(com.autopai.support.proxy.ZBHProxy.ZBHDvrImpl.LOGTAG, "onChangeEvent status = " + status);
                if (status == 2) {
                    com.autopai.support.proxy.ZBHProxy.ZBHDvrImpl.this.mDvrStatus = 1;
                } else {
                    com.autopai.support.proxy.ZBHProxy.ZBHDvrImpl.this.mDvrStatus = 0;
                }
                com.autopai.support.proxy.ZBHProxy.ZBHDvrImpl.this.mChangeListener.onDvrChanged(com.autopai.support.proxy.ZBHProxy.ZBHDvrImpl.this.mDvrStatus);
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }

        public void onErrorEvent(int i, int i1) {
        }
    };
    private final android.content.ServiceConnection mServiceConnectionListener = new android.content.ServiceConnection() { // from class: com.autopai.support.proxy.ZBHProxy.ZBHDvrImpl.2
        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName name, android.os.IBinder service) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.ZBHProxy.ZBHDvrImpl.LOGTAG, "onServiceConnected name = " + name);
            try {
                if (com.autopai.support.proxy.ZBHProxy.ZBHDvrImpl.this.mCar != null) {
                    com.autopai.support.proxy.ZBHProxy.ZBHDvrImpl.this.mDvrMgr = (android.car.wt.WtCarDVRManager) com.autopai.support.proxy.ZBHProxy.ZBHDvrImpl.this.mCar.getCarManager("wt_car_dvr");
                    try {
                        com.autopai.support.proxy.ZBHProxy.ZBHDvrImpl.this.mDvrMgr.registerCallback(com.autopai.support.proxy.ZBHProxy.ZBHDvrImpl.this.mDvrCallback);
                        int status = com.autopai.support.proxy.ZBHProxy.ZBHDvrImpl.this.mDvrMgr.getIntProperty(557903487, 0);
                        com.autopai.support.utils.SLog.d(com.autopai.support.proxy.ZBHProxy.ZBHDvrImpl.LOGTAG, "onServiceConnected status = " + status);
                        if (status == 2) {
                            com.autopai.support.proxy.ZBHProxy.ZBHDvrImpl.this.mDvrStatus = 1;
                        } else {
                            com.autopai.support.proxy.ZBHProxy.ZBHDvrImpl.this.mDvrStatus = 0;
                        }
                        if (com.autopai.support.proxy.ZBHProxy.ZBHDvrImpl.this.mChangeListener != null) {
                            com.autopai.support.proxy.ZBHProxy.ZBHDvrImpl.this.mChangeListener.onDvrChanged(com.autopai.support.proxy.ZBHProxy.ZBHDvrImpl.this.mDvrStatus);
                        }
                    } catch (java.lang.Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (android.car.CarNotConnectedException e2) {
                com.autopai.support.utils.SLog.i(com.autopai.support.proxy.ZBHProxy.ZBHDvrImpl.LOGTAG, "mServiceConnectionListener e.msg = " + e2.getMessage());
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName name) {
            com.autopai.support.proxy.ZBHProxy.ZBHDvrImpl.this.mDvrMgr = null;
        }
    };

    public ZBHDvrImpl(android.content.Context cxt) {
        this.mContext = cxt.getApplicationContext();
        initDvrStatus();
        initReceiver();
    }

    private void initDvrStatus() {
        try {
            this.mCar = android.car.Car.createCar(this.mContext, this.mServiceConnectionListener, null);
            com.autopai.support.utils.SLog.d(LOGTAG, "initDvrStatus mCar = " + this.mCar);
            if (this.mCar != null) {
                this.mCar.connect();
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    private void initReceiver() {
    }

    @Override // com.autopai.support.proxy.Interface.IDvrProxy
    public void setDvrChangeListener(com.autopai.support.proxy.DvrImplProxy.IDvrChangeListener listener) {
        this.mChangeListener = listener;
    }

    @Override // com.autopai.support.proxy.Interface.IDvrProxy
    public int getDvrStatus() {
        com.autopai.support.utils.SLog.d(LOGTAG, "getDvrStatus mDvrStatus = " + this.mDvrStatus);
        return this.mDvrStatus;
    }

    @Override // com.autopai.support.proxy.Interface.IDvrProxy
    public void destroy() {
        try {
            if (this.mDvrMgr != null) {
                this.mDvrMgr.unregisterCallback(this.mDvrCallback);
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        if (this.mCar != null) {
            this.mCar.disconnect();
            this.mCar = null;
        }
    }
}
