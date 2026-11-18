package com.wt.statistics;

/* loaded from: classes.dex */
public final class StatisticsManager {
    private static final java.lang.String LOGTAG = "StatisticsManager";
    private static com.wt.statistics.StatisticsManager sManager;
    private android.content.Context mContext;
    private com.wt.statistics.utils.EventProcess mEventProcess;
    private com.wt.statistics.net.NetThread mNetThread;
    private com.wt.statistics.persistentdata.IPersistent mPersistent;

    private StatisticsManager(android.content.Context cxt) {
        this.mContext = cxt.getApplicationContext();
        this.mPersistent = new com.wt.statistics.persistentdata.DBPersistent(cxt);
        initNetThread();
        initEventProcess();
        com.wt.statistics.sendstrategy.CommonStrategy.getInstance(this.mContext).registerACCReceiver(this.mNetThread);
    }

    private void initEventProcess() {
        this.mEventProcess = new com.wt.statistics.utils.EventProcess(this.mContext, this.mNetThread);
    }

    private void initNetThread() {
        this.mNetThread = new com.wt.statistics.net.NetThread(this.mContext, this.mPersistent);
    }

    public void getToken() {
        this.mNetThread.makeTokenRequest(0L);
    }

    public void refreshToken() {
        this.mNetThread.makeRefreshTokenRequest(0L);
    }

    public void getServerConfig() {
        this.mNetThread.makeConfigRequest(0L);
    }

    public static com.wt.statistics.StatisticsManager getInstance(android.content.Context cxt) {
        if (sManager == null) {
            synchronized (com.wt.statistics.StatisticsManager.class) {
                if (sManager == null) {
                    sManager = new com.wt.statistics.StatisticsManager(cxt);
                }
            }
        }
        return sManager;
    }

    public void sendNormalEvent(java.lang.String eventId, java.lang.String eventLabel, java.util.Map<java.lang.String, java.lang.String> map) {
        com.wt.statistics.persistentdata.EventBeans.Events events = new com.wt.statistics.persistentdata.EventBeans.Events();
        try {
            events.eventId = eventId;
            events.eventLabel = eventLabel;
            events.eventTime = java.lang.String.valueOf(java.lang.System.currentTimeMillis());
            if (map != null) {
                events.properties = new org.json.JSONObject(map).toString();
            }
            com.wt.statistics.utils.SLog.i("", "sendNormalEvent events.properties=" + events.properties);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        this.mEventProcess.makeNormalEvent(events);
    }
}
