package com.autopai.support.proxy.ZBHProxy;

/* loaded from: classes.dex */
public class ZBHBluetoothImpl implements com.autopai.support.proxy.Interface.IBluetoothProxy {
    private static final java.lang.String LOGTAG = "ZBHBluetoothImpl";
    private boolean isConnect;
    private boolean isPowerOn;
    private com.autopai.support.proxy.BluetoothImplProxy.IBluetoothStatusChangeListener mChangeListener;
    private android.content.Context mContext;
    private com.autopai.support.proxy.ZBHProxy.ZBHBluetoothImpl.BluetoothReceiver mReceiver;

    public ZBHBluetoothImpl(android.content.Context cxt) {
        this.mContext = cxt.getApplicationContext();
        initState();
        registerReceiver();
    }

    private void initState() {
        android.bluetooth.BluetoothAdapter adapter = android.bluetooth.BluetoothAdapter.getDefaultAdapter();
        if (adapter != null) {
            this.isPowerOn = adapter.isEnabled();
            int connectStatus = reflectGetConnectionStateMethod(adapter);
            com.autopai.support.utils.SLog.d(LOGTAG, "initStatus isPowerOn = " + this.isPowerOn + " connectStatus = " + connectStatus);
            if (this.isPowerOn) {
                if (2 == connectStatus || 1 == connectStatus) {
                    this.isConnect = true;
                } else if (connectStatus == 0 || 3 == connectStatus) {
                    this.isConnect = false;
                }
            }
        }
    }

    private int reflectGetConnectionStateMethod(android.bluetooth.BluetoothAdapter adapter) {
        java.lang.Class cls = adapter.getClass();
        try {
            java.lang.reflect.Method method = cls.getDeclaredMethod("getConnectionState", (java.lang.Class[]) null);
            method.setAccessible(true);
            return ((java.lang.Integer) method.invoke(adapter, (java.lang.Object[]) null)).intValue();
        } catch (java.lang.IllegalAccessException e) {
            e.printStackTrace();
            return -200;
        } catch (java.lang.NoSuchMethodException e2) {
            e2.printStackTrace();
            return -200;
        } catch (java.lang.reflect.InvocationTargetException e3) {
            e3.printStackTrace();
            return -200;
        }
    }

    private void registerReceiver() {
        android.content.IntentFilter filter = new android.content.IntentFilter();
        filter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        filter.addAction("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED");
        if (this.mReceiver == null) {
            this.mReceiver = new com.autopai.support.proxy.ZBHProxy.ZBHBluetoothImpl.BluetoothReceiver();
        }
        this.mContext.registerReceiver(this.mReceiver, filter);
    }

    @Override // com.autopai.support.proxy.Interface.IBluetoothProxy
    public void setBluetoothStatusChangeListener(com.autopai.support.proxy.BluetoothImplProxy.IBluetoothStatusChangeListener listener) {
        this.mChangeListener = listener;
    }

    @Override // com.autopai.support.proxy.Interface.IBluetoothProxy
    public boolean getBluetoothPowerOnStatus() {
        return this.isPowerOn;
    }

    @Override // com.autopai.support.proxy.Interface.IBluetoothProxy
    public boolean getBluetoothConnectStatus() {
        return this.isConnect;
    }

    @Override // com.autopai.support.proxy.Interface.IBluetoothProxy
    public void destroy() {
        if (this.mReceiver != null) {
            this.mContext.unregisterReceiver(this.mReceiver);
            this.mReceiver = null;
        }
    }

    private class BluetoothReceiver extends android.content.BroadcastReceiver {
        private BluetoothReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            java.lang.String action = intent.getAction();
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.ZBHProxy.ZBHBluetoothImpl.LOGTAG, "BluetoothReceiver action = " + action);
            if (action.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                int state = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
                com.autopai.support.proxy.ZBHProxy.ZBHBluetoothImpl.this.isPowerOn = state == 12;
                com.autopai.support.utils.SLog.d(com.autopai.support.proxy.ZBHProxy.ZBHBluetoothImpl.LOGTAG, "BluetoothReceiver ACTION_STATE_CHANGED isPowerOn = " + com.autopai.support.proxy.ZBHProxy.ZBHBluetoothImpl.this.isPowerOn + "; isConnect =" + com.autopai.support.proxy.ZBHProxy.ZBHBluetoothImpl.this.isConnect);
                if (com.autopai.support.proxy.ZBHProxy.ZBHBluetoothImpl.this.mChangeListener != null) {
                    com.autopai.support.proxy.ZBHProxy.ZBHBluetoothImpl.this.mChangeListener.onBluetoothChanged(com.autopai.support.proxy.ZBHProxy.ZBHBluetoothImpl.this.isPowerOn, com.autopai.support.proxy.ZBHProxy.ZBHBluetoothImpl.this.isConnect);
                    return;
                }
                return;
            }
            if (action.equals("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED")) {
                int state2 = intent.getIntExtra("android.bluetooth.adapter.extra.CONNECTION_STATE", 0);
                com.autopai.support.proxy.ZBHProxy.ZBHBluetoothImpl.this.isConnect = state2 == 2;
                com.autopai.support.utils.SLog.d(com.autopai.support.proxy.ZBHProxy.ZBHBluetoothImpl.LOGTAG, "BluetoothReceiver ACTION_CONNECTION_STATE_CHANGED isPowerOn = " + com.autopai.support.proxy.ZBHProxy.ZBHBluetoothImpl.this.isPowerOn + "; isConnect =" + com.autopai.support.proxy.ZBHProxy.ZBHBluetoothImpl.this.isConnect);
                if (com.autopai.support.proxy.ZBHProxy.ZBHBluetoothImpl.this.mChangeListener != null) {
                    com.autopai.support.proxy.ZBHProxy.ZBHBluetoothImpl.this.mChangeListener.onBluetoothChanged(com.autopai.support.proxy.ZBHProxy.ZBHBluetoothImpl.this.isPowerOn, com.autopai.support.proxy.ZBHProxy.ZBHBluetoothImpl.this.isConnect);
                }
            }
        }
    }
}
