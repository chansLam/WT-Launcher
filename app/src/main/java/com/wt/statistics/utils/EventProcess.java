package com.wt.statistics.utils;

/* loaded from: classes.dex */
public class EventProcess {
    private static final java.lang.String LOGTAG = "EventProcess";
    private android.content.Context mContext;
    private com.wt.statistics.net.NetThread mNetThread;

    public EventProcess(android.content.Context cxt, com.wt.statistics.net.NetThread netthread) {
        this.mContext = cxt.getApplicationContext();
        this.mNetThread = netthread;
    }

    public void makeNormalEvent(com.wt.statistics.persistentdata.EventBeans.Events events) {
        handleNormalEvent(events);
    }

    private void handleNormalEvent(com.wt.statistics.persistentdata.EventBeans.Events events) {
        try {
            if (needSaveEvent(this.mContext)) {
                this.mNetThread.makeEventCacheMsg(0L, events);
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    private boolean needSaveEvent(android.content.Context cxt) {
        boolean ret = true;
        com.wt.statistics.persistentdata.ServerConfigs.getInstance();
        com.wt.statistics.persistentdata.ConfigBean bean = com.wt.statistics.persistentdata.ServerConfigs.readServerConfig(cxt);
        if (bean == null) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "needUploadEvent bean is null");
            return true;
        }
        if (bean.getReportStrategy() == -1) {
            ret = false;
        }
        return ret;
    }
}
