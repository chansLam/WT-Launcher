package com.autopai.support.proxy.Interface;

/* loaded from: classes.dex */
public interface IUsbProxy {
    void destroy();

    boolean getUsbStatus();

    void setUsbChangeListener(com.autopai.support.proxy.UsbImplProxy.IUsbChangeListener iUsbChangeListener);
}
