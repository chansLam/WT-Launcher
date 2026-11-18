package com.autopai.support.proxy;

/* loaded from: classes.dex */
public class MuteImplProxy implements com.autopai.support.proxy.Interface.IMuteProxy {
    private com.autopai.support.proxy.Interface.IMuteProxy mProxy;

    public interface IMuteChangeListener {
        void onMuteChanged(boolean z);
    }

    public MuteImplProxy(android.content.Context cxt) {
        if ("releaseS311".equals("releaseS311") || "releaseS311".equals(com.autopai.support.proxy.ProxyConstants.S311_DEBUG)) {
            this.mProxy = new com.autopai.support.proxy.S311Proxy.S311MuteImpl();
        } else {
            this.mProxy = new com.autopai.support.proxy.ZBHProxy.ZBHMuteImpl(cxt);
        }
    }

    @Override // com.autopai.support.proxy.Interface.IMuteProxy
    public void setMuteChangeListener(com.autopai.support.proxy.MuteImplProxy.IMuteChangeListener listener) {
        this.mProxy.setMuteChangeListener(listener);
    }

    @Override // com.autopai.support.proxy.Interface.IMuteProxy
    public void destroy() {
        this.mProxy.destroy();
    }

    @Override // com.autopai.support.proxy.Interface.IMuteProxy
    public boolean getMuteStatus() {
        return this.mProxy.getMuteStatus();
    }
}
