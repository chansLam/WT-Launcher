package com.incall.proxy.tbox;

public class TboxManager {
    public TboxManager() {}
    public static TboxManager getInstance() { return null; }
    public void addTBoxChangedListener(TboxChangedListener listener) { }
    public void removeTBoxChangedListener(TboxChangedListener listener) { }
    public Object getMobileState(INetworkCallBack callback) { return null; }
    public Object getWanInfo(INetworkCallBack callback) { return null; }
    public Object getMobileNetworkState(INetworkCallBack callback) { return null; }

    public interface TboxChangedListener {
    }

    public interface TboxCallListener extends TboxChangedListener {
        void onCallStatusChanged(int status, int callType);
        void onTboxCallStateReport(TboxCommonDefine.TboxCallReportCMD cmd, String data);
        void onTboxMobileSignalChanged(int type, int level, int value);
        void onTboxMobileSwitchStateChanged(int status);
        void onTboxWifiStationConnectStatusChanged(int status, String ssid);
    }
}
