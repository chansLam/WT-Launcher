package com.incall.proxy.can;

public class AirConditionInfo {
    public AirConditionInfo() {}
    public static AirConditionInfo getInstance() { return null; }
    public static class Switch {
        public Switch() {}
        public static Switch getInstance() { return null; }
        public int index;
        public static class ON {
            public ON() {}
            public static ON getInstance() { return null; }
            public static int index;
        }
    }
    public int windLevel;
    public Switch acIonizerState;
    public float leftTemperature;
    public float rightTemperature;
    public int electricTemperature;
}
