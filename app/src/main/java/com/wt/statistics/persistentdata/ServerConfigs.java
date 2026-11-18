package com.wt.statistics.persistentdata;

/* loaded from: classes.dex */
public class ServerConfigs {
    public static final int ACCOFF = 1;
    public static final java.lang.String APPKEY = "appKey";
    public static final java.lang.String CATCHEXCEPTION = "catchException";
    public static final int NO_SEND = -1;
    public static final int REALTIME = 0;
    public static final java.lang.String REPORTSTRATEGY = "reportStrategy";
    public static final java.lang.String SERVERCONFIGBEAN = "ServerConfigs";
    private static com.wt.statistics.persistentdata.ServerConfigs mInstance;
    private java.util.ArrayList<com.wt.statistics.persistentdata.ConfigBean> mConfigBeans;

    private ServerConfigs() {
    }

    public static com.wt.statistics.persistentdata.ServerConfigs getInstance() {
        if (mInstance == null) {
            synchronized (com.wt.statistics.persistentdata.ServerConfigs.class) {
                if (mInstance == null) {
                    mInstance = new com.wt.statistics.persistentdata.ServerConfigs();
                }
            }
        }
        return mInstance;
    }

    public synchronized java.util.ArrayList<com.wt.statistics.persistentdata.ConfigBean> getConfigBeans(android.content.Context cxt) {
        if (this.mConfigBeans == null) {
            this.mConfigBeans = readInPreference(cxt);
        }
        return this.mConfigBeans;
    }

    public synchronized void setConfigBeans(android.content.Context cxt, java.util.ArrayList<com.wt.statistics.persistentdata.ConfigBean> data) {
        saveInPreference(cxt, data);
    }

    private void saveInPreference(android.content.Context cxt, java.util.ArrayList<com.wt.statistics.persistentdata.ConfigBean> data) {
        if (data != null) {
            org.json.JSONObject jSONObject = new org.json.JSONObject();
            org.json.JSONArray array = new org.json.JSONArray();
            for (int i = 0; i < data.size(); i++) {
                try {
                    org.json.JSONObject obj = new org.json.JSONObject();
                    obj.put(APPKEY, data.get(i).getAppKey());
                    obj.put(REPORTSTRATEGY, data.get(i).getReportStrategy());
                    obj.put(CATCHEXCEPTION, data.get(i).getCatchExceptionEnable());
                    array.put(i, obj);
                    addBeans(data.get(i));
                } catch (java.lang.Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                jSONObject.put(SERVERCONFIGBEAN, array);
            } catch (org.json.JSONException e) {
                e.printStackTrace();
            }
            android.content.SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(cxt.getApplicationContext());
            preferences.edit().putString(SERVERCONFIGBEAN, jSONObject.toString()).commit();
        }
    }

    private synchronized void addBeans(com.wt.statistics.persistentdata.ConfigBean data) {
        com.wt.statistics.persistentdata.ConfigBean bean = new com.wt.statistics.persistentdata.ConfigBean();
        bean.setAppKey(data.getAppKey());
        bean.setReportStrategy(data.getReportStrategy());
        bean.setCatchExceptionEnable(data.getCatchExceptionEnable());
        this.mConfigBeans.add(bean);
    }

    private java.util.ArrayList<com.wt.statistics.persistentdata.ConfigBean> readInPreference(android.content.Context cxt) {
        java.util.ArrayList<com.wt.statistics.persistentdata.ConfigBean> beans = new java.util.ArrayList<>();
        android.content.SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(cxt.getApplicationContext());
        java.lang.String jsonString = preferences.getString(SERVERCONFIGBEAN, "");
        try {
            org.json.JSONObject object = new org.json.JSONObject(jsonString);
            org.json.JSONArray arr = object.getJSONArray(SERVERCONFIGBEAN);
            for (int i = 0; i < arr.length(); i++) {
                org.json.JSONObject obj = arr.getJSONObject(i);
                com.wt.statistics.persistentdata.ConfigBean bean = new com.wt.statistics.persistentdata.ConfigBean();
                bean.setAppKey(obj.getString(APPKEY));
                bean.setReportStrategy(obj.getInt(REPORTSTRATEGY));
                bean.setCatchExceptionEnable(obj.getBoolean(CATCHEXCEPTION));
                beans.add(bean);
            }
        } catch (java.lang.Exception e) {
        }
        return beans;
    }

    public static com.wt.statistics.persistentdata.ConfigBean readServerConfig(android.content.Context cxt) {
        java.util.ArrayList<com.wt.statistics.persistentdata.ConfigBean> beans = getInstance().getConfigBeans(cxt);
        if (beans != null && beans.size() > 0) {
            com.wt.statistics.persistentdata.ConfigBean bean = beans.get(0);
            int strategy = bean.getReportStrategy();
            if (strategy != 2 && strategy == 1) {
            }
            return bean;
        }
        return null;
    }
}
