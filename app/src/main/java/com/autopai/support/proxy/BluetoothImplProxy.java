package com.autopai.support.proxy;

/* loaded from: classes.dex */
public class BluetoothImplProxy implements com.autopai.support.proxy.Interface.IBluetoothProxy {
    private com.autopai.support.proxy.Interface.IBluetoothProxy mProxy;

    public interface IBluetoothStatusChangeListener {
        void onBluetoothChanged(boolean z, boolean z2);
    }

    public BluetoothImplProxy(android.content.Context cxt) {
        if ("releaseS311".equals("releaseS311") || "releaseS311".equals(com.autopai.support.proxy.ProxyConstants.S311_DEBUG)) {
            this.mProxy = new com.autopai.support.proxy.S311Proxy.S311BluetoothImpl(cxt);
        } else {
            this.mProxy = new com.autopai.support.proxy.ZBHProxy.ZBHBluetoothImpl(cxt);
        }
    }

    @Override // com.autopai.support.proxy.Interface.IBluetoothProxy
    public void setBluetoothStatusChangeListener(com.autopai.support.proxy.BluetoothImplProxy.IBluetoothStatusChangeListener listener) {
        this.mProxy.setBluetoothStatusChangeListener(listener);
    }

    @Override // com.autopai.support.proxy.Interface.IBluetoothProxy
    public boolean getBluetoothPowerOnStatus() {
        return this.mProxy.getBluetoothPowerOnStatus();
    }

    @Override // com.autopai.support.proxy.Interface.IBluetoothProxy
    public boolean getBluetoothConnectStatus() {
        return this.mProxy.getBluetoothConnectStatus();
    }

    @Override // com.autopai.support.proxy.Interface.IBluetoothProxy
    public void destroy() {
        this.mProxy.destroy();
    }
}
