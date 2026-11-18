package com.incall.proxy.bt;

public class BtPhoneManager {
    public BtPhoneManager() {}
    public static BtPhoneManager getInstance() { return null; }
    public void registerOnPowerChangedListener(OnPowerChangedListener listener) { }
    public void unregisterOnPowerChangedListener(OnPowerChangedListener listener) { }
    public void registerOnHfpConnectedListener(OnHfpConnectedListener listener) { }
    public void unregisterOnHfpConnectedListener(OnHfpConnectedListener listener) { }
    public int getBtPowerStatus() { return 0; }
    public int getHfpConnectStatus() { return 0; }
    public java.util.ArrayList getOnGoingContacts() { return null; }

    public interface OnPowerChangedListener {
        void onPowerChanged(int powerState);
    }

    public interface OnHfpConnectedListener {
        void onHfpConnectionChanged(int status);
    }
}
