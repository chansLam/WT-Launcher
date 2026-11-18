package com.incall.proxy.can;

public class AirConditionInfo {
    public int windLevel;
    public Switch acIonizerState;
    public float leftTemperature;
    public float rightTemperature;
    public int electricTemperature;

    /** Switch 是一个枚举式的值对象：index 为 byte，ON 是它的静态实例。 */
    public static class Switch {
        public byte index;
        public static Switch ON;
    }
}
