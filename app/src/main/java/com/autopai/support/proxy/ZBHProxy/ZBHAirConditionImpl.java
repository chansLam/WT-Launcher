package com.autopai.support.proxy.ZBHProxy;

/* loaded from: classes.dex */
public class ZBHAirConditionImpl implements com.autopai.support.proxy.Interface.IAirConditionProxy {
    private static final java.lang.String LOGTAG = "ZBHAirConditionImpl";
    private static int[] sPropIdList = {358614275, 356517120, 354419984, 557845763};
    private android.car.Car mCar;
    private android.content.Context mContext;
    private android.car.hardware.hvac.CarHvacManager mHvacManager;
    private com.autopai.support.proxy.AirConditionImplProxy.IAirConditionStatusChangeListener mListener;
    private com.autopai.support.proxy.AirConditionImplProxy.AirConditionValue mValues = new com.autopai.support.proxy.AirConditionImplProxy.AirConditionValue();
    private com.autopai.support.proxy.ZBHProxy.ZBHAirConditionImpl.HvacEventCallback mHvacCallBack = new com.autopai.support.proxy.ZBHProxy.ZBHAirConditionImpl.HvacEventCallback();
    private final android.content.ServiceConnection mServiceConnectionListener = new android.content.ServiceConnection() { // from class: com.autopai.support.proxy.ZBHProxy.ZBHAirConditionImpl.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName name, android.os.IBinder service) {
            try {
                com.autopai.support.proxy.ZBHProxy.ZBHAirConditionImpl.this.mHvacManager = (android.car.hardware.hvac.CarHvacManager) com.autopai.support.proxy.ZBHProxy.ZBHAirConditionImpl.this.mCar.getCarManager("hvac");
                com.autopai.support.proxy.ZBHProxy.ZBHAirConditionImpl.this.mHvacManager.registerCallback(com.autopai.support.proxy.ZBHProxy.ZBHAirConditionImpl.this.mHvacCallBack);
                com.autopai.support.proxy.ZBHProxy.ZBHAirConditionImpl.this.getAirConditionValue();
                if (com.autopai.support.proxy.ZBHProxy.ZBHAirConditionImpl.this.mListener != null) {
                    com.autopai.support.proxy.ZBHProxy.ZBHAirConditionImpl.this.mListener.onAirConditionChanged(com.autopai.support.proxy.ZBHProxy.ZBHAirConditionImpl.this.mValues);
                }
            } catch (android.car.CarNotConnectedException e) {
                com.autopai.support.utils.SLog.i(com.autopai.support.proxy.ZBHProxy.ZBHAirConditionImpl.LOGTAG, "mServiceConnectionListener e.msg = " + e.getMessage());
            }
            com.autopai.support.utils.SLog.i(com.autopai.support.proxy.ZBHProxy.ZBHAirConditionImpl.LOGTAG, "onServiceConnected end ");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName name) {
            com.autopai.support.proxy.ZBHProxy.ZBHAirConditionImpl.this.mHvacManager = null;
            com.autopai.support.utils.SLog.i(com.autopai.support.proxy.ZBHProxy.ZBHAirConditionImpl.LOGTAG, "onServiceDisconnected end ");
        }
    };

    public ZBHAirConditionImpl(android.content.Context cxt) {
        this.mContext = cxt.getApplicationContext();
        try {
            this.mCar = android.car.Car.createCar(cxt, this.mServiceConnectionListener, null);
            if (this.mCar != null) {
                this.mCar.connect();
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.autopai.support.proxy.Interface.IAirConditionProxy
    public void setAirConditionStatusChangeListener(com.autopai.support.proxy.AirConditionImplProxy.IAirConditionStatusChangeListener listener) {
        this.mListener = listener;
    }

    @Override // com.autopai.support.proxy.Interface.IAirConditionProxy
    public com.autopai.support.proxy.AirConditionImplProxy.AirConditionValue getAirConditionValue() {
        com.autopai.support.utils.SLog.i(LOGTAG, "getAirConditionValue mHvacManager = " + this.mHvacManager);
        if (this.mHvacManager != null) {
            try {
                float leftTemp = this.mHvacManager.getFloatProperty(358614275, 1);
                this.mValues.leftTmp = leftTemp;
                float rightTemp = this.mHvacManager.getFloatProperty(358614275, 4);
                this.mValues.rightTmp = rightTemp;
                int fan = this.mHvacManager.getIntProperty(356517120, 1);
                this.mValues.fan = fan;
                boolean open = this.mHvacManager.getBooleanProperty(354419984, 1);
                this.mValues.isOpen = open;
                int isPurifierOpen = this.mHvacManager.getIntProperty(557845763, 1);
                this.mValues.isPurifierOpen = isPurifierOpen == 2;
                com.autopai.support.utils.SLog.i(LOGTAG, "getAirConditionValue mValues = " + this.mValues.toString());
            } catch (java.lang.Exception e) {
                com.autopai.support.utils.SLog.i(LOGTAG, "getAirConditionValue exception");
                e.printStackTrace();
            }
        }
        return this.mValues;
    }

    @Override // com.autopai.support.proxy.Interface.IAirConditionProxy
    public void destroy() {
        try {
            if (this.mHvacManager != null) {
                this.mHvacManager.unregisterCallback(this.mHvacCallBack);
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        if (this.mCar != null) {
            this.mCar.disconnect();
        }
    }

    private class HvacEventCallback implements android.car.hardware.hvac.CarHvacManager.CarHvacEventCallback {
        private HvacEventCallback() {
        }

        public void onChangeEvent(android.car.hardware.CarPropertyValue value) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.ZBHProxy.ZBHAirConditionImpl.LOGTAG, "HvacEventCallback onChangeEvent:" + value.toString());
            com.autopai.support.proxy.ZBHProxy.ZBHAirConditionImpl.this.updateHvacValues(value);
        }

        public void onErrorEvent(int propertyId, int area) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.ZBHProxy.ZBHAirConditionImpl.LOGTAG, "HvacEventCallback onErrorEvent propertyId, area:" + propertyId + " ：" + area);
            com.autopai.support.proxy.ZBHProxy.ZBHAirConditionImpl.this.updateHvacError(propertyId, area);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to find 'out' block for switch in B:6:0x001d. Please report as an issue. */
    public void updateHvacError(int propertyId, int area) {
        if (this.mListener != null) {
            com.autopai.support.utils.SLog.d(LOGTAG, "updateHvacError propertyId = " + propertyId);
            switch (propertyId) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateHvacValues(android.car.hardware.CarPropertyValue value) {
        if (value == null) {
            com.autopai.support.utils.SLog.d(LOGTAG, "updateHvacValues value is null");
            return;
        }
        if (this.mListener == null) {
            com.autopai.support.utils.SLog.d(LOGTAG, "updateHvacValues mListener is null");
            return;
        }
        try {
            switch (value.getPropertyId()) {
                case 354419984:
                    doPower(value);
                    break;
                case 356517120:
                    doFan(value);
                    break;
                case 358614275:
                    doTemp(value);
                    break;
                case 557845763:
                    doAQS(value);
                    break;
            }
        } catch (java.lang.Exception e) {
            com.autopai.support.utils.SLog.i(LOGTAG, "updateHvacValues exception");
        }
        com.autopai.support.utils.SLog.d(LOGTAG, "updateHvacValues mValues = " + this.mValues.toString());
    }

    private void doAQS(android.car.hardware.CarPropertyValue value) {
        this.mValues.isPurifierOpen = ((java.lang.Boolean) value.getValue()).booleanValue();
        com.autopai.support.utils.SLog.d(LOGTAG, "doAQS mListener = " + this.mListener + "; mValues = " + this.mValues.toString());
        if (this.mListener != null) {
            this.mListener.onAirConditionChanged(this.mValues);
        }
    }

    private void doPower(android.car.hardware.CarPropertyValue value) {
        this.mValues.isOpen = ((java.lang.Boolean) value.getValue()).booleanValue();
        com.autopai.support.utils.SLog.d(LOGTAG, "doPower mListener = " + this.mListener + "; mValues = " + this.mValues.toString());
        if (this.mListener != null) {
            this.mListener.onAirConditionChanged(this.mValues);
        }
    }

    private void doFan(android.car.hardware.CarPropertyValue value) {
        this.mValues.fan = ((java.lang.Integer) value.getValue()).intValue();
        com.autopai.support.utils.SLog.d(LOGTAG, "doFan mListener = " + this.mListener + "; mValues = " + this.mValues.toString());
        if (this.mListener != null) {
            this.mListener.onAirConditionChanged(this.mValues);
        }
    }

    private void doTemp(android.car.hardware.CarPropertyValue value) {
        int areId = value.getAreaId();
        if (areId == 1) {
            this.mValues.leftTmp = ((java.lang.Float) value.getValue()).floatValue();
        } else if (areId == 4) {
            this.mValues.rightTmp = ((java.lang.Float) value.getValue()).floatValue();
        }
        com.autopai.support.utils.SLog.d(LOGTAG, "doTemp areId = " + areId + "; mListener = " + this.mListener + "; mValues = " + this.mValues.toString());
        if (this.mListener != null) {
            this.mListener.onAirConditionChanged(this.mValues);
        }
    }
}
