package com.wt.statistics.persistentdata;

/* loaded from: classes.dex */
public class ConfigBean {
    private java.lang.String appKey;
    private boolean catchExceptionEnable = true;
    private int reportStrategy;

    public java.lang.String getAppKey() {
        return this.appKey;
    }

    public void setAppKey(java.lang.String appKey) {
        this.appKey = appKey;
    }

    public int getReportStrategy() {
        return this.reportStrategy;
    }

    public void setReportStrategy(int reportStrategy) {
        this.reportStrategy = reportStrategy;
    }

    public boolean getCatchExceptionEnable() {
        return this.catchExceptionEnable;
    }

    public void setCatchExceptionEnable(boolean catchExceptionEnable) {
        this.catchExceptionEnable = catchExceptionEnable;
    }
}
