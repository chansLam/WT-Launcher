package com.autopai.support.proxy;

/* loaded from: classes.dex */
public class AirConditionImplProxy implements com.autopai.support.proxy.Interface.IAirConditionProxy {
    private com.autopai.support.proxy.Interface.IAirConditionProxy mProxy;

    public interface IAirConditionStatusChangeListener {
        void onAirConditionChanged(com.autopai.support.proxy.AirConditionImplProxy.AirConditionValue airConditionValue);
    }

    public AirConditionImplProxy(android.content.Context cxt) {
        if ("releaseS311".equals("releaseS311") || "releaseS311".equals(com.autopai.support.proxy.ProxyConstants.S311_DEBUG)) {
            this.mProxy = new com.autopai.support.proxy.S311Proxy.S311AirConditionImpl(cxt);
        } else {
            this.mProxy = new com.autopai.support.proxy.ZBHProxy.ZBHAirConditionImpl(cxt);
        }
    }

    @Override // com.autopai.support.proxy.Interface.IAirConditionProxy
    public void setAirConditionStatusChangeListener(com.autopai.support.proxy.AirConditionImplProxy.IAirConditionStatusChangeListener listener) {
        this.mProxy.setAirConditionStatusChangeListener(listener);
    }

    @Override // com.autopai.support.proxy.Interface.IAirConditionProxy
    public com.autopai.support.proxy.AirConditionImplProxy.AirConditionValue getAirConditionValue() {
        return this.mProxy.getAirConditionValue();
    }

    @Override // com.autopai.support.proxy.Interface.IAirConditionProxy
    public void destroy() {
        this.mProxy.destroy();
    }

    public static class AirConditionValue {
        public static float DEFAULT_TEMP = 99.0f;
        public boolean isOpen = false;
        public float leftTmp = DEFAULT_TEMP;
        public float rightTmp = DEFAULT_TEMP;
        public int fan = 0;
        public boolean isPurifierOpen = false;
        public int level = 0;

        public java.lang.String toString() {
            return "AirConditionValue isOpen = " + this.isOpen + ";leftTmp = " + this.leftTmp + ";rightTmp = " + this.rightTmp + ";fan = " + this.fan + ";isPurifierOpen = " + this.isPurifierOpen + ";level = " + this.level;
        }
    }
}
