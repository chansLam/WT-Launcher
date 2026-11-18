package com.incall.proxy.ac;

public class AirConditionManager {
    public AirConditionManager() {}
    public static AirConditionManager getInstance() { return null; }
    public static interface IAirConditionListener {
    }
    public void addListener(IAirConditionListener a0) { }
    public com.incall.proxy.can.AirConditionInfo getAirConditionInfo() { return null;}
    public byte[] getACConfigs() { return new byte[0]; }
    public int getAcType() { return 0; }
    public void removeListener(IAirConditionListener a0) { }
}
