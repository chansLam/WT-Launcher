package com.autopai.support.proxy;

/* loaded from: classes.dex */
public class UsbImplProxy implements com.autopai.support.proxy.Interface.IUsbProxy {
    private com.autopai.support.proxy.Interface.IUsbProxy mProxy;

    public interface IUsbChangeListener {
        void onUsbChanged(boolean z);
    }

    public UsbImplProxy(android.content.Context cxt) {
        this.mProxy = new com.autopai.support.proxy.S311Proxy.S311UsbImpl(cxt);
    }

    @Override // com.autopai.support.proxy.Interface.IUsbProxy
    public void setUsbChangeListener(com.autopai.support.proxy.UsbImplProxy.IUsbChangeListener listener) {
        this.mProxy.setUsbChangeListener(listener);
    }

    @Override // com.autopai.support.proxy.Interface.IUsbProxy
    public boolean getUsbStatus() {
        return this.mProxy.getUsbStatus();
    }

    @Override // com.autopai.support.proxy.Interface.IUsbProxy
    public void destroy() {
        this.mProxy.destroy();
    }
}
