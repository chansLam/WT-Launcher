package com.autopai.support.proxy.Interface;

/* loaded from: classes.dex */
public interface IMuteProxy {
    void destroy();

    boolean getMuteStatus();

    void setMuteChangeListener(com.autopai.support.proxy.MuteImplProxy.IMuteChangeListener iMuteChangeListener);
}
