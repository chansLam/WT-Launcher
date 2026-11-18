package com.autopai.support.utils;

/* loaded from: classes.dex */
public interface DemoMode {
    public static final java.lang.String ACTION_DEMO = "com.android.systemui.demo";
    public static final java.lang.String COMMAND_BARS = "bars";
    public static final java.lang.String COMMAND_BATTERY = "battery";
    public static final java.lang.String COMMAND_CLOCK = "clock";
    public static final java.lang.String COMMAND_ENTER = "enter";
    public static final java.lang.String COMMAND_EXIT = "exit";
    public static final java.lang.String COMMAND_NETWORK = "network";
    public static final java.lang.String COMMAND_STATUS = "status";

    void dispatchDemoCommand(java.lang.String str, android.os.Bundle bundle);
}
