package com.autopai.support.proxy;

/* loaded from: classes.dex */
public class DvrImplProxy implements com.autopai.support.proxy.Interface.IDvrProxy {
    private com.autopai.support.proxy.Interface.IDvrProxy mProxy;

    public interface IDvrChangeListener {
        void onDvrChanged(int i);
    }

    public DvrImplProxy(android.content.Context cxt) {
        if ("releaseS311".equals("releaseS311") || "releaseS311".equals(com.autopai.support.proxy.ProxyConstants.S311_DEBUG)) {
            this.mProxy = new com.autopai.support.proxy.S311Proxy.S311DvrImpl(cxt);
        } else {
            this.mProxy = new com.autopai.support.proxy.ZBHProxy.ZBHDvrImpl(cxt);
        }
    }

    @Override // com.autopai.support.proxy.Interface.IDvrProxy
    public void setDvrChangeListener(com.autopai.support.proxy.DvrImplProxy.IDvrChangeListener listener) {
        this.mProxy.setDvrChangeListener(listener);
    }

    @Override // com.autopai.support.proxy.Interface.IDvrProxy
    public int getDvrStatus() {
        return this.mProxy.getDvrStatus();
    }

    @Override // com.autopai.support.proxy.Interface.IDvrProxy
    public void destroy() {
        this.mProxy.destroy();
    }
}
