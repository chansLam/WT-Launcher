package com.incall.apps.common.modal;

/* loaded from: classes.dex */
public class QualityLevel {
    public static final int EXCELLENT = 0;
    public static final int FINE = 1;
    public static final int HIGH = 4;
    public static final int INVALID = -1;
    public static final int LIGHT = 2;
    public static final int MODERATE = 3;
    public static final int REVERSED = -1;
    public static final int SERIOUS = 5;

    public static boolean isLevelValid(int level) {
        switch (level) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return true;
            default:
                return false;
        }
    }

    public static boolean isPolution(int level) {
        switch (level) {
            case 2:
            case 3:
            case 4:
            case 5:
                return true;
            default:
                return false;
        }
    }

    public static boolean isClean(int level) {
        return level == 0;
    }
}
