package com.autopai.support.proxy.Interface;

/* loaded from: classes.dex */
public interface IAirConditionProxy {
    void destroy();

    com.autopai.support.proxy.AirConditionImplProxy.AirConditionValue getAirConditionValue();

    void setAirConditionStatusChangeListener(com.autopai.support.proxy.AirConditionImplProxy.IAirConditionStatusChangeListener iAirConditionStatusChangeListener);
}
