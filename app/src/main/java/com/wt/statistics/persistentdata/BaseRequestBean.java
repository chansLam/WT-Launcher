package com.wt.statistics.persistentdata;

/* loaded from: classes.dex */
public class BaseRequestBean implements com.wt.statistics.persistentdata.IJsonObject {
    public static final java.lang.String APP_ID = "app_id";
    public static final java.lang.String CARSERIES = "carsSeries";
    public static final java.lang.String CHANNEL_ID = "channel_id";
    public static final java.lang.String USER_ID = "user_id";
    public static final java.lang.String VIN = "vin";
    public java.lang.String app_id;
    public java.lang.String carSeries;
    public java.lang.String channel_id;
    public java.lang.String user_id;
    public java.lang.String vin;

    @Override // com.wt.statistics.persistentdata.IJsonObject
    public org.json.JSONObject toJsonObject() {
        return null;
    }

    @Override // com.wt.statistics.persistentdata.IJsonObject
    public boolean toObject(java.lang.String jsonString) {
        return false;
    }
}
