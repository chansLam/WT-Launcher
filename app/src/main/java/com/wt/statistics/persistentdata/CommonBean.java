package com.wt.statistics.persistentdata;

/* loaded from: classes.dex */
public class CommonBean implements com.wt.statistics.persistentdata.IJsonObject {
    public static final java.lang.String APP_ID = "app_id";
    public static final java.lang.String APP_VERSION = "app_version";
    public static final java.lang.String CAR_TYPE = "car_type";
    public static final java.lang.String CHANNEL_ID = "channel_id";
    public static final java.lang.String OS_VERSION = "os_version";
    public static final java.lang.String SESSION_TIME = "session_time";
    public static final java.lang.String TOKEN_EXPIRATIONDATE = "token_expirationdate";
    public static final java.lang.String TOKEN_TOKEN = "token_token";
    public static final java.lang.String UNKNOW = "unknow";
    public static final java.lang.String VIN = "vin";
    public java.lang.String app_id;
    public java.lang.String app_version;
    public java.lang.String car_type;
    public java.lang.String channel_id;
    public java.lang.String os_verison;
    public java.lang.String vin;
    public com.wt.statistics.net.TokenBean tokenBean = new com.wt.statistics.net.TokenBean();
    public long session_time = 30000;

    @Override // com.wt.statistics.persistentdata.IJsonObject
    public org.json.JSONObject toJsonObject() {
        org.json.JSONObject obj = new org.json.JSONObject();
        try {
            obj.put("channel_id", this.channel_id);
            obj.put("app_id", this.app_id);
            obj.put(APP_VERSION, this.app_version);
            obj.put(SESSION_TIME, this.session_time);
            obj.put(OS_VERSION, this.os_verison);
            obj.put(CAR_TYPE, this.car_type);
            obj.put("vin", this.vin);
            obj.put(TOKEN_TOKEN, this.tokenBean.token);
            obj.put(TOKEN_EXPIRATIONDATE, this.tokenBean.expirationDate);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override // com.wt.statistics.persistentdata.IJsonObject
    public boolean toObject(java.lang.String jsonString) {
        try {
            org.json.JSONObject jsonObj = new org.json.JSONObject(jsonString);
            this.app_id = jsonObj.getString("app_id");
            this.vin = jsonObj.getString("vin");
            this.tokenBean.token = jsonObj.getString(TOKEN_TOKEN);
            this.tokenBean.expirationDate = jsonObj.getString(TOKEN_EXPIRATIONDATE);
            this.channel_id = jsonObj.getString("channel_id");
            this.session_time = jsonObj.getLong(SESSION_TIME);
            this.app_version = jsonObj.getString(APP_VERSION);
            this.car_type = jsonObj.getString(CAR_TYPE);
            this.os_verison = jsonObj.getString(OS_VERSION);
            return true;
        } catch (org.json.JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}
