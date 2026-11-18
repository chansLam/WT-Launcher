package com.wt.statistics.net;

/* loaded from: classes.dex */
public class TokenBean {
    public static final java.lang.String EXPIRATIONDATE = "expirationDate";
    public static final java.lang.String KEY = "TokenBean";
    private static final java.lang.String LOGTAG = "TokenBean";
    public static final java.lang.String TOKEN = "token";
    public java.lang.String expirationDate;
    public java.lang.String token;

    public static void saveInPreference(android.content.Context cxt, com.wt.statistics.net.TokenBean data) {
        org.json.JSONObject obj = null;
        org.json.JSONObject obj2 = null;
        try {
            obj = new org.json.JSONObject();
        } catch (java.lang.Exception e) {
        }
        try {
            obj.put(TOKEN, data.token);
            obj.put(EXPIRATIONDATE, data.expirationDate);
            obj2 = obj;
        } catch (java.lang.Exception e2) {
            e2.printStackTrace();
            obj2 = obj;
            com.wt.statistics.utils.SLog.i("TokenBean", "saveInPreference token error");
            android.content.SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(cxt.getApplicationContext());
            preferences.edit().putString("TokenBean", obj2.toString()).commit();
        }
        android.content.SharedPreferences preferences2 = android.preference.PreferenceManager.getDefaultSharedPreferences(cxt.getApplicationContext());
        preferences2.edit().putString("TokenBean", obj2.toString()).commit();
    }

    public static com.wt.statistics.net.TokenBean readInPreference(android.content.Context cxt) {
        android.content.SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(cxt.getApplicationContext());
        java.lang.String jsonString = preferences.getString("TokenBean", "");
        com.wt.statistics.net.TokenBean bean = new com.wt.statistics.net.TokenBean();
        try {
            org.json.JSONObject object = new org.json.JSONObject(jsonString);
            bean.token = object.getString(TOKEN);
            bean.expirationDate = object.getString(EXPIRATIONDATE);
        } catch (java.lang.Exception e) {
            com.wt.statistics.utils.SLog.i("TokenBean", "readInPreference token error");
            e.printStackTrace();
        }
        return bean;
    }
}
