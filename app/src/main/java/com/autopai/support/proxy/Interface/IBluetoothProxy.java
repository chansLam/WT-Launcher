package com.autopai.support.proxy.Interface;

/* loaded from: classes.dex */
public interface IBluetoothProxy {
    void destroy();

    boolean getBluetoothConnectStatus();

    boolean getBluetoothPowerOnStatus();

    void setBluetoothStatusChangeListener(com.autopai.support.proxy.BluetoothImplProxy.IBluetoothStatusChangeListener iBluetoothStatusChangeListener);
}
