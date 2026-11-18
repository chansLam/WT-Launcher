package com.incall.apps.service.aircleaner;

/* loaded from: classes.dex */
public class RecycleType {
    public static final int AUTO_FRESH = 3;
    public static final int AUTO_RECYCLE = 2;
    public static final int FRESH = 1;
    public static final int INVALID = 4;
    public static final int RECYCLE = 0;
    public static final int RESERVED = -2;

    public static boolean isRecycle(int type) {
        return type == 0 || type == 2;
    }
}
