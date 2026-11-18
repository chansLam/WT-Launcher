package com.autopai.support.proxy;

/* loaded from: classes.dex */
public class SingalImplProxy implements com.autopai.support.proxy.Interface.ISingalProxy {
    private com.autopai.support.proxy.Interface.ISingalProxy mProxy;

    public interface ISignalChangeListener {
        void onTboxMobileSignalChanged(int i, int i2, int i3);
    }

    public SingalImplProxy(android.content.Context cxt, com.autopai.support.proxy.SingalImplProxy.ISignalChangeListener listener) {
        if ("releaseS311".equals("releaseS311") || "releaseS311".equals(com.autopai.support.proxy.ProxyConstants.S311_DEBUG)) {
            this.mProxy = new com.autopai.support.proxy.S311Proxy.S311SignalImpl(listener);
        } else {
            this.mProxy = new com.autopai.support.proxy.ZBHProxy.ZBHSignalImpl(cxt, listener);
        }
    }

    @Override // com.autopai.support.proxy.Interface.ISingalProxy
    public void setSignalChangeListener(com.autopai.support.proxy.SingalImplProxy.ISignalChangeListener listener) {
        this.mProxy.setSignalChangeListener(listener);
    }

    @Override // com.autopai.support.proxy.Interface.ISingalProxy
    public void destroy() {
        this.mProxy.destroy();
    }
}
