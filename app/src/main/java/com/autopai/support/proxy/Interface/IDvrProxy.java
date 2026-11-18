package com.autopai.support.proxy.Interface;

/* loaded from: classes.dex */
public interface IDvrProxy {
    public static final int DVR_CLOSE = 0;
    public static final int DVR_NO_EXIST = -1;
    public static final int DVR_OPEN = 1;

    void destroy();

    int getDvrStatus();

    void setDvrChangeListener(com.autopai.support.proxy.DvrImplProxy.IDvrChangeListener iDvrChangeListener);
}
