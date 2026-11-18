package com.wt.statistics.persistentdata;

/* loaded from: classes.dex */
public class DBPersistent implements com.wt.statistics.persistentdata.IPersistent {
    private static final java.lang.String LOGTAG = "DBPersistent";
    private static final int MAX_DB_SIZE = 10485760;
    private static final int MAX_EVENT_CACHE = 30;
    private static final int MAX_ITEM_COUNT = 100;
    private android.content.Context mContext;

    public DBPersistent(android.content.Context cxt) {
        this.mContext = cxt;
    }

    @Override // com.wt.statistics.persistentdata.IPersistent
    public void removeData() {
    }

    @Override // com.wt.statistics.persistentdata.IPersistent
    public void saveExceptionEvent(com.wt.statistics.persistentdata.ExceptionBean.ExceptionInfo bean) {
        if (checkDatabaseSize(this.mContext)) {
            deleteDatas();
        }
        com.wt.statistics.persistentdata.DataDBMgr.getInstance(this.mContext).insertExceptionData(bean);
    }

    @Override // com.wt.statistics.persistentdata.IPersistent
    public com.wt.statistics.persistentdata.ExceptionBean readExceptionEvent(long timestamp) {
        com.wt.statistics.persistentdata.ExceptionBean beans = com.wt.statistics.persistentdata.DataDBMgr.getInstance(this.mContext).queryExceptionData(timestamp);
        return beans;
    }

    @Override // com.wt.statistics.persistentdata.IPersistent
    public void saveNormalEvent(java.util.ArrayList<com.wt.statistics.persistentdata.EventBeans.Events> events) {
        if (checkDatabaseSize(this.mContext)) {
            deleteDatas();
        }
        if (events != null) {
            mBean.normal_events = events;
            mBean.toJsonObject().toString();
            saveNormalEvent();
            resetNormalEventCache();
        }
    }

    @Override // com.wt.statistics.persistentdata.IPersistent
    public com.wt.statistics.persistentdata.EventBeans readEvent(long curTime) {
        com.wt.statistics.persistentdata.EventBeans sendDatas = com.wt.statistics.persistentdata.DataDBMgr.getInstance(this.mContext).queryData(curTime);
        return sendDatas;
    }

    @Override // com.wt.statistics.persistentdata.IPersistent
    public void deleteExceptionData(long timestamp) {
        com.wt.statistics.persistentdata.DataDBMgr.getInstance(this.mContext).deleteExceptionData(timestamp);
    }

    @Override // com.wt.statistics.persistentdata.IPersistent
    public void deleteEventData(long timestamp) {
        com.wt.statistics.persistentdata.DataDBMgr.getInstance(this.mContext).deleteEventData(timestamp);
    }

    @Override // com.wt.statistics.persistentdata.IPersistent
    public boolean needUploadEvent() {
        int itemCnt = com.wt.statistics.persistentdata.DataDBMgr.getInstance(this.mContext).queryDbItemCnt();
        return itemCnt > 100;
    }

    private void saveNormalEvent() {
        com.wt.statistics.persistentdata.DataDBMgr.getInstance(this.mContext).insertEventData(mBean.normal_events);
    }

    private void savePageEvent() {
        com.wt.statistics.persistentdata.DataDBMgr.getInstance(this.mContext).insertPageData(mBean.activity_events, mBean.fragment_events);
    }

    private void resetNormalEventCache() {
        mBean.normal_events.clear();
    }

    private void resetPageEventCache() {
        mBean.activity_events.clear();
        mBean.fragment_events.clear();
    }

    private void resetAllCache() {
        resetNormalEventCache();
        resetPageEventCache();
    }

    @Override // com.wt.statistics.persistentdata.IPersistent
    public void saveActivityEvent(java.util.ArrayList<com.wt.statistics.persistentdata.EventBeans.ActivityEvents> events) {
        if (checkDatabaseSize(this.mContext)) {
            deleteDatas();
        }
        if (events != null) {
            mBean.activity_events = events;
            java.lang.String jsonString = mBean.toJsonObject().toString();
            com.wt.statistics.utils.SLog.i(LOGTAG, "saveActivityEvent data sendJson=" + jsonString);
            savePageEvent();
            resetPageEventCache();
        }
    }

    @Override // com.wt.statistics.persistentdata.IPersistent
    public void saveFragmentEvent(java.util.ArrayList<com.wt.statistics.persistentdata.EventBeans.FragmentEvents> events) {
        if (events != null) {
            mBean.fragment_events = events;
            java.lang.String jsonString = mBean.toJsonObject().toString();
            com.wt.statistics.utils.SLog.i(LOGTAG, "saveFragmentEvent data sendJson=" + jsonString);
            savePageEvent();
            resetPageEventCache();
        }
    }

    private void deleteDatas() {
        long timestamp = java.lang.System.currentTimeMillis();
        deleteEventData(timestamp);
        deleteExceptionData(timestamp);
    }

    private boolean checkDatabaseSize(android.content.Context cxt) {
        java.io.File dataFile = cxt.getDatabasePath(com.wt.statistics.persistentdata.DataDBHelper.DB_NAME);
        com.wt.statistics.utils.SLog.d(LOGTAG, "checkDatabaseSize dataFile.length() =" + dataFile.length());
        if (dataFile.length() <= 10485760) {
            return false;
        }
        return true;
    }
}
