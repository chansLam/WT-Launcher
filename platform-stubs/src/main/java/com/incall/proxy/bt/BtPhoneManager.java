package com.incall.proxy.bt;

public class BtPhoneManager {
    public BtPhoneManager() {}
    public static BtPhoneManager getInstance() { return null; }
    public static class OnHfpConnectedListener {
        public OnHfpConnectedListener() {}
        public static OnHfpConnectedListener getInstance() { return null; }
    }
    public static class OnPowerChangedListener {
        public OnPowerChangedListener() {}
        public static OnPowerChangedListener getInstance() { return null; }
    }
    public void registerOnPowerChangedListener(OnPowerChangedListener a0) { }
    public void registerOnHfpConnectedListener(OnHfpConnectedListener a0) { }
    public int getBtPowerStatus() { return 0;}
    public int getHfpConnectStatus() { return 0;}
    public java.util.List getOnGoingContacts() { return java.util.Collections.emptyList(); }
    public void unregisterOnPowerChangedListener(OnPowerChangedListener a0) { }
    public void unregisterOnHfpConnectedListener(OnHfpConnectedListener a0) { }
}
