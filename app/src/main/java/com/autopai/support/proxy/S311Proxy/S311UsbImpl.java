package com.autopai.support.proxy.S311Proxy;

/* loaded from: classes.dex */
public class S311UsbImpl implements com.autopai.support.proxy.Interface.IUsbProxy {
    private android.content.Context mContext;

    public S311UsbImpl(android.content.Context cxt) {
        this.mContext = cxt.getApplicationContext();
    }

    @Override // com.autopai.support.proxy.Interface.IUsbProxy
    public void setUsbChangeListener(com.autopai.support.proxy.UsbImplProxy.IUsbChangeListener listener) {
    }

    @Override // com.autopai.support.proxy.Interface.IUsbProxy
    public boolean getUsbStatus() {
        return false;
    }

    @Override // com.autopai.support.proxy.Interface.IUsbProxy
    public void destroy() {
    }
}
