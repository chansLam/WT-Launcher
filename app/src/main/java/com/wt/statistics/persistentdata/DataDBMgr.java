package com.wt.statistics.persistentdata;

/* loaded from: classes.dex */
public class DataDBMgr {
    private static final java.lang.String LOGTAG = "DataDBMgr";
    private static com.wt.statistics.persistentdata.DataDBMgr sInstance;
    private int MAX_ITEM_READ = 15;
    private android.database.sqlite.SQLiteDatabase db;
    private com.wt.statistics.persistentdata.DataDBHelper mDbHelper;

    public interface IQueryDbCallback {
        java.lang.Object parseDbData(android.database.Cursor cursor);
    }

    private DataDBMgr(android.content.Context cxt) {
        this.db = null;
        this.mDbHelper = new com.wt.statistics.persistentdata.DataDBHelper(cxt);
        this.db = this.mDbHelper.getWritableDatabase();
    }

    public static com.wt.statistics.persistentdata.DataDBMgr getInstance(android.content.Context cxt) {
        if (sInstance == null) {
            synchronized (com.wt.statistics.persistentdata.DataDBMgr.class) {
                if (sInstance == null) {
                    sInstance = new com.wt.statistics.persistentdata.DataDBMgr(cxt);
                }
            }
        }
        return sInstance;
    }

    public boolean insertEventData(java.util.ArrayList<com.wt.statistics.persistentdata.EventBeans.Events> normal_events) {
        android.content.ContentValues value = new android.content.ContentValues();
        long timestamp = java.lang.System.currentTimeMillis();
        com.wt.statistics.utils.SLog.i(LOGTAG, "insertEventData normal_events.size = " + normal_events.size() + ";timestamp=" + timestamp);
        try {
            this.db.beginTransaction();
            for (int i = 0; i < normal_events.size(); i++) {
                value.put(com.wt.statistics.persistentdata.DataDBHelper.TIMESTAMP, java.lang.Long.valueOf(timestamp));
                value.put(com.wt.statistics.persistentdata.DataDBHelper.EVENT_JSON, normal_events.get(i).toJsonObject().toString());
                this.db.insert("events", null, value);
            }
            this.db.setTransactionSuccessful();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        } finally {
            this.db.endTransaction();
        }
        return false;
    }

    public void insertExceptionData(com.wt.statistics.persistentdata.ExceptionBean.ExceptionInfo bean) {
        android.content.ContentValues value = new android.content.ContentValues();
        long timestamp = java.lang.System.currentTimeMillis();
        try {
            this.db.beginTransaction();
            value.put(com.wt.statistics.persistentdata.DataDBHelper.TIMESTAMP, java.lang.Long.valueOf(timestamp));
            value.put(com.wt.statistics.persistentdata.DataDBHelper.EXCEP_TYPE, bean.type);
            value.put(com.wt.statistics.persistentdata.DataDBHelper.EXCEP_TRACE, bean.stacktrace);
            value.put(com.wt.statistics.persistentdata.DataDBHelper.EXCEP_CPUINFO, bean.cpuInfo);
            value.put(com.wt.statistics.persistentdata.DataDBHelper.EXCEP_ANRINFO, bean.anrinfo);
            this.db.insert(com.wt.statistics.persistentdata.DataDBHelper.EXCEP_TABLE_NAME, null, value);
            this.db.setTransactionSuccessful();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        } finally {
            this.db.endTransaction();
        }
    }

    public com.wt.statistics.persistentdata.ExceptionBean queryExceptionData(long timeStamp) {
        java.lang.String[] projectionPage = {com.wt.statistics.persistentdata.DataDBHelper.ID, com.wt.statistics.persistentdata.DataDBHelper.TIMESTAMP, com.wt.statistics.persistentdata.DataDBHelper.EXCEP_TYPE, com.wt.statistics.persistentdata.DataDBHelper.EXCEP_TRACE, com.wt.statistics.persistentdata.DataDBHelper.EXCEP_ANRINFO, com.wt.statistics.persistentdata.DataDBHelper.EXCEP_CPUINFO};
        java.lang.String[] selectionArgs = {java.lang.String.valueOf(timeStamp)};
        final com.wt.statistics.persistentdata.ExceptionBean beans = new com.wt.statistics.persistentdata.ExceptionBean();
        queryTableData(com.wt.statistics.persistentdata.DataDBHelper.EXCEP_TABLE_NAME, projectionPage, "timestamp <= ?", selectionArgs, new com.wt.statistics.persistentdata.DataDBMgr.IQueryDbCallback() { // from class: com.wt.statistics.persistentdata.DataDBMgr.1
            @Override // com.wt.statistics.persistentdata.DataDBMgr.IQueryDbCallback
            public java.lang.Object parseDbData(android.database.Cursor cursor) {
                com.wt.statistics.persistentdata.ExceptionBean.ExceptionInfo bean = new com.wt.statistics.persistentdata.ExceptionBean.ExceptionInfo();
                com.wt.statistics.persistentdata.DataDBMgr.this.parseExceptionData(cursor, bean);
                beans.infos.add(bean);
                return null;
            }
        });
        return beans;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parseExceptionData(android.database.Cursor cursor, com.wt.statistics.persistentdata.ExceptionBean.ExceptionInfo datas) {
        java.lang.String type = cursor.getString(cursor.getColumnIndex(com.wt.statistics.persistentdata.DataDBHelper.EXCEP_TYPE));
        java.lang.String trace = cursor.getString(cursor.getColumnIndex(com.wt.statistics.persistentdata.DataDBHelper.EXCEP_TRACE));
        java.lang.String cpuinfo = cursor.getString(cursor.getColumnIndex(com.wt.statistics.persistentdata.DataDBHelper.EXCEP_CPUINFO));
        java.lang.String anrinfo = cursor.getString(cursor.getColumnIndex(com.wt.statistics.persistentdata.DataDBHelper.EXCEP_ANRINFO));
        java.lang.String id = cursor.getString(cursor.getColumnIndex(com.wt.statistics.persistentdata.DataDBHelper.ID));
        com.wt.statistics.utils.SLog.i(LOGTAG, "parseExceptionData id =" + id);
        datas.type = type;
        datas.stacktrace = trace;
        datas.cpuInfo = cpuinfo;
        datas.anrinfo = anrinfo;
    }

    public void deleteExceptionData(long timestamp) {
        try {
            this.db.beginTransaction();
            java.lang.String[] selectionArgs = {java.lang.String.valueOf(timestamp)};
            this.db.delete(com.wt.statistics.persistentdata.DataDBHelper.EXCEP_TABLE_NAME, "timestamp <= ?", selectionArgs);
            this.db.setTransactionSuccessful();
            this.db.endTransaction();
        } catch (java.lang.Exception e) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "deleteData exception timestamp=" + timestamp);
            e.printStackTrace();
        }
    }

    public boolean insertPageData(java.util.ArrayList<com.wt.statistics.persistentdata.EventBeans.ActivityEvents> activityEvents, java.util.ArrayList<com.wt.statistics.persistentdata.EventBeans.FragmentEvents> fragmentEvents) {
        android.content.ContentValues value = new android.content.ContentValues();
        long timestamp = java.lang.System.currentTimeMillis();
        com.wt.statistics.utils.SLog.i(LOGTAG, "insertEventData normal_events.size = " + activityEvents.size() + ";timestamp=" + timestamp);
        try {
            this.db.beginTransaction();
            for (int i = 0; i < activityEvents.size(); i++) {
                value.put(com.wt.statistics.persistentdata.DataDBHelper.TIMESTAMP, java.lang.Long.valueOf(timestamp));
                value.put(com.wt.statistics.persistentdata.DataDBHelper.PAGE_JSON, activityEvents.get(i).toJsonObject().toString());
                value.put(com.wt.statistics.persistentdata.DataDBHelper.PAGE_TYPE, com.wt.statistics.persistentdata.DataDBHelper.ACTIVITY_PAGE);
                this.db.insert(com.wt.statistics.persistentdata.DataDBHelper.PAGE_TABLE_NAME, null, value);
            }
            for (int i2 = 0; i2 < fragmentEvents.size(); i2++) {
                value.put(com.wt.statistics.persistentdata.DataDBHelper.TIMESTAMP, java.lang.Long.valueOf(timestamp));
                value.put(com.wt.statistics.persistentdata.DataDBHelper.PAGE_JSON, fragmentEvents.get(i2).toJsonObject().toString());
                value.put(com.wt.statistics.persistentdata.DataDBHelper.PAGE_TYPE, com.wt.statistics.persistentdata.DataDBHelper.FRAGMENT_PAGE);
                this.db.insert(com.wt.statistics.persistentdata.DataDBHelper.PAGE_TABLE_NAME, null, value);
            }
            this.db.setTransactionSuccessful();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        } finally {
            this.db.endTransaction();
        }
        return false;
    }

    public com.wt.statistics.persistentdata.EventBeans queryData(long timestamp) {
        java.lang.String[] projectionPage = {com.wt.statistics.persistentdata.DataDBHelper.ID, com.wt.statistics.persistentdata.DataDBHelper.TIMESTAMP, com.wt.statistics.persistentdata.DataDBHelper.PAGE_TYPE, com.wt.statistics.persistentdata.DataDBHelper.PAGE_JSON};
        java.lang.String[] projectionEvent = {com.wt.statistics.persistentdata.DataDBHelper.ID, com.wt.statistics.persistentdata.DataDBHelper.TIMESTAMP, com.wt.statistics.persistentdata.DataDBHelper.EVENT_JSON};
        final com.wt.statistics.persistentdata.EventBeans mSendData = new com.wt.statistics.persistentdata.EventBeans();
        java.lang.String[] selectionArgs = {java.lang.String.valueOf(timestamp)};
        queryTableData(com.wt.statistics.persistentdata.DataDBHelper.PAGE_TABLE_NAME, projectionPage, "timestamp <= ?", selectionArgs, new com.wt.statistics.persistentdata.DataDBMgr.IQueryDbCallback() { // from class: com.wt.statistics.persistentdata.DataDBMgr.2
            @Override // com.wt.statistics.persistentdata.DataDBMgr.IQueryDbCallback
            public java.lang.Object parseDbData(android.database.Cursor cursor) {
                com.wt.statistics.persistentdata.DataDBMgr.this.parsePageData(cursor, mSendData);
                return null;
            }
        });
        queryTableData("events", projectionEvent, "timestamp <= ?", selectionArgs, new com.wt.statistics.persistentdata.DataDBMgr.IQueryDbCallback() { // from class: com.wt.statistics.persistentdata.DataDBMgr.3
            @Override // com.wt.statistics.persistentdata.DataDBMgr.IQueryDbCallback
            public java.lang.Object parseDbData(android.database.Cursor cursor) {
                com.wt.statistics.persistentdata.DataDBMgr.this.parseEventData(cursor, mSendData);
                return null;
            }
        });
        return mSendData;
    }

    public int queryDbItemCnt() {
        int cnt = queryTableItemCnt(com.wt.statistics.persistentdata.DataDBHelper.PAGE_TABLE_NAME) + queryTableItemCnt("events") + queryTableItemCnt(com.wt.statistics.persistentdata.DataDBHelper.EXCEP_TABLE_NAME);
        com.wt.statistics.utils.SLog.i(LOGTAG, "queryDbItemCnt cnt =" + cnt);
        return cnt;
    }

    private int queryTableItemCnt(java.lang.String tableName) {
        try {
            android.database.Cursor cursorEvent = this.db.query(tableName, null, null, null, null, null, null);
            com.wt.statistics.utils.SLog.i(LOGTAG, "queryTableItemCnt cursorEvent=" + cursorEvent);
            if (cursorEvent == null) {
                return 0;
            }
            int cnt = cursorEvent.getCount();
            return cnt;
        } catch (java.lang.Exception e) {
            return 0;
        }
    }

    private void queryTableData(java.lang.String tableName, java.lang.String[] columns, java.lang.String selection, java.lang.String[] selectionArgs, com.wt.statistics.persistentdata.DataDBMgr.IQueryDbCallback callback) {
        android.database.Cursor cursorEvent = null;
        try {
            try {
                cursorEvent = this.db.query(tableName, columns, selection, selectionArgs, null, null, "_id ASC");
                com.wt.statistics.utils.SLog.i(LOGTAG, "queryData cursorEvent=" + cursorEvent);
                if (cursorEvent != null) {
                    int cnt = cursorEvent.getCount();
                    com.wt.statistics.utils.SLog.i(LOGTAG, "queryData cnt=" + cnt);
                    int idx = 0;
                    if (cnt > 0) {
                        cursorEvent.moveToFirst();
                        do {
                            callback.parseDbData(cursorEvent);
                            idx++;
                            if (!cursorEvent.moveToNext()) {
                                break;
                            }
                        } while (idx < this.MAX_ITEM_READ);
                    }
                }
                if (cursorEvent != null) {
                    cursorEvent.close();
                }
            } catch (java.lang.Exception e) {
                e.printStackTrace();
                if (cursorEvent != null) {
                    cursorEvent.close();
                }
            }
        } catch (java.lang.Throwable th) {
            if (cursorEvent != null) {
                cursorEvent.close();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parseEventData(android.database.Cursor cursor, com.wt.statistics.persistentdata.EventBeans datas) {
        com.wt.statistics.persistentdata.EventBeans.Events events = new com.wt.statistics.persistentdata.EventBeans.Events();
        java.lang.String jsonString = cursor.getString(cursor.getColumnIndex(com.wt.statistics.persistentdata.DataDBHelper.EVENT_JSON));
        events.toObject(jsonString);
        datas.normal_events.add(events);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parsePageData(android.database.Cursor cursor, com.wt.statistics.persistentdata.EventBeans datas) {
        java.lang.String type = cursor.getString(cursor.getColumnIndex(com.wt.statistics.persistentdata.DataDBHelper.PAGE_TYPE));
        java.lang.String pageJson = cursor.getString(cursor.getColumnIndex(com.wt.statistics.persistentdata.DataDBHelper.PAGE_JSON));
        if (com.wt.statistics.persistentdata.DataDBHelper.ACTIVITY_PAGE.equals(type)) {
            com.wt.statistics.persistentdata.EventBeans.ActivityEvents events = new com.wt.statistics.persistentdata.EventBeans.ActivityEvents();
            events.toObject(pageJson);
            datas.activity_events.add(events);
        } else {
            com.wt.statistics.persistentdata.EventBeans.FragmentEvents events2 = new com.wt.statistics.persistentdata.EventBeans.FragmentEvents();
            events2.toObject(pageJson);
            datas.fragment_events.add(events2);
        }
    }

    public void deleteEventData(long timestamp) {
        try {
            this.db.beginTransaction();
            java.lang.String[] selectionArgs = {java.lang.String.valueOf(timestamp)};
            this.db.delete(com.wt.statistics.persistentdata.DataDBHelper.PAGE_TABLE_NAME, "timestamp <= ?", selectionArgs);
            this.db.delete("events", "timestamp <= ?", selectionArgs);
            this.db.setTransactionSuccessful();
            this.db.endTransaction();
        } catch (java.lang.Exception e) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "deleteData exception timestamp=" + timestamp);
            e.printStackTrace();
        }
    }
}
