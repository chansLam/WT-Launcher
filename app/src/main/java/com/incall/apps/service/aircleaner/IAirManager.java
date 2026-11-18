package com.incall.apps.service.aircleaner;

/* loaded from: classes.dex */
interface IAirManager {
    int getInLevel();

    int getInPM();

    int getOutLevel();

    int getOutPM();

    int getRecycleType();

    boolean isPlasmaOn();

    boolean isPowerOn();

    boolean isWindOn();

    void removeCallback();

    void setCallback(com.incall.apps.service.aircleaner.IDataChange iDataChange);

    void setIonizerEnable(boolean z, boolean z2);
}
