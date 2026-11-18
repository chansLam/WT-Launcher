package com.incall.proxy.tbox;

public class TboxManager {
    public TboxManager() {}
    public static TboxManager getInstance() { return null; }
    public static class TboxChangedListener {
        public TboxChangedListener() {}
        public static TboxChangedListener getInstance() { return null; }
    }
    public static class TboxCallListener extends TboxChangedListener {
        public TboxCallListener() {}
        public static TboxCallListener getInstance() { return null; }
    }
    public Object getMobileState(INetworkCallBack a0) { return null;}
    public Object getWanInfo(INetworkCallBack a0) { return null;}
    public Object getMobileNetworkState(INetworkCallBack a0) { return null;}
    public void addTBoxChangedListener(TboxChangedListener a0) { }
    public void removeTBoxChangedListener(TboxChangedListener a0) { }
}
