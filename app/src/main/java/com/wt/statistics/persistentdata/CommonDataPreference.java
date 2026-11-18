package com.wt.statistics.persistentdata;

/* loaded from: classes.dex */
public class CommonDataPreference {
    private static final java.lang.String LOGTAG = "CommonDataPreference";
    private static com.wt.statistics.persistentdata.CommonDataPreference sInstance;
    private java.lang.Object lock = new java.lang.Object();
    private android.content.SharedPreferences mAppSharePref;
    private android.content.Context mCxt;
    private java.util.HashMap<java.lang.String, java.lang.Object> mLocalData;

    public static com.wt.statistics.persistentdata.CommonDataPreference getInstance(android.content.Context cxt) {
        if (sInstance == null) {
            synchronized (com.wt.statistics.persistentdata.CommonDataPreference.class) {
                if (sInstance == null) {
                    sInstance = new com.wt.statistics.persistentdata.CommonDataPreference(cxt);
                }
            }
        }
        return sInstance;
    }

    private CommonDataPreference(android.content.Context cxt) {
        if (cxt != null) {
            this.mCxt = cxt.getApplicationContext();
            this.mAppSharePref = android.preference.PreferenceManager.getDefaultSharedPreferences(this.mCxt);
            synchronized (this.lock) {
                if (this.mLocalData == null) {
                    this.mLocalData = new java.util.HashMap<>();
                }
            }
        }
    }

    public synchronized void addToLocalData(com.wt.statistics.persistentdata.CommonBean obj) {
        if (obj != null) {
            this.mLocalData.put(obj.getClass().getCanonicalName(), obj);
        }
    }

    public synchronized com.wt.statistics.persistentdata.CommonBean getLocalData(java.lang.Class obj) {
        com.wt.statistics.persistentdata.CommonBean result;
        result = null;
        if (obj != null) {
            result = (com.wt.statistics.persistentdata.CommonBean) this.mLocalData.get(obj.getCanonicalName());
            if (result == null && (result = pullFromPref(obj)) != null) {
                addToLocalData(result);
            }
        }
        return result;
    }

    public synchronized void pushToPref(com.wt.statistics.persistentdata.CommonBean obj) {
        android.content.SharedPreferences.Editor editor = this.mAppSharePref.edit();
        if (obj instanceof com.wt.statistics.persistentdata.IJsonObject) {
            editor.putString(obj.getClass().getCanonicalName(), obj.toJsonObject().toString());
            editor.commit();
        }
    }

    public synchronized com.wt.statistics.persistentdata.CommonBean pullFromPref(java.lang.Class obj) {
        com.wt.statistics.persistentdata.CommonBean bean;
        java.lang.String json = this.mAppSharePref.getString(obj.getCanonicalName(), "");
        if (android.text.TextUtils.isEmpty(json)) {
            bean = null;
        } else {
            bean = new com.wt.statistics.persistentdata.CommonBean();
            boolean ret = bean.toObject(json);
            if (!ret) {
                com.wt.statistics.utils.SLog.d(LOGTAG, "pullFromPref CommonBean has exception");
                bean = null;
            }
        }
        return bean;
    }
}
