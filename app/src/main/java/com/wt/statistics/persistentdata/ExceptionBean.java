package com.wt.statistics.persistentdata;

/* loaded from: classes.dex */
public class ExceptionBean implements com.wt.statistics.persistentdata.IJsonObject {
    private static final java.lang.String EXCEPTIONS = "data";
    public java.util.ArrayList<com.wt.statistics.persistentdata.ExceptionBean.ExceptionInfo> infos = new java.util.ArrayList<>();
    public com.wt.statistics.persistentdata.BaseRequestBean baseBean = new com.wt.statistics.persistentdata.BaseRequestBean();

    @Override // com.wt.statistics.persistentdata.IJsonObject
    public org.json.JSONObject toJsonObject() {
        org.json.JSONObject obj = new org.json.JSONObject();
        try {
            obj.put("app_id", this.baseBean.app_id);
            obj.put("vin", this.baseBean.vin);
            obj.put("channel_id", this.baseBean.channel_id);
            obj.put(com.wt.statistics.persistentdata.BaseRequestBean.CARSERIES, this.baseBean.carSeries);
            obj.put(com.wt.statistics.persistentdata.BaseRequestBean.USER_ID, this.baseBean.user_id);
            org.json.JSONArray exceptionArray = new org.json.JSONArray();
            for (int i = 0; i < this.infos.size(); i++) {
                com.wt.statistics.persistentdata.ExceptionBean.ExceptionInfo events = this.infos.get(i);
                exceptionArray.put(i, events.toJsonObject());
            }
            obj.put(EXCEPTIONS, exceptionArray);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override // com.wt.statistics.persistentdata.IJsonObject
    public boolean toObject(java.lang.String jsonString) {
        org.json.JSONObject jsonObj = null;
        try {
            jsonObj = new org.json.JSONObject(jsonString);
        } catch (java.lang.Exception e) {
        }
        try {
            this.baseBean.app_id = jsonObj.getString("app_id");
            this.baseBean.channel_id = jsonObj.getString("channel_id");
            this.baseBean.vin = jsonObj.getString("vin");
            this.baseBean.carSeries = jsonObj.getString(com.wt.statistics.persistentdata.BaseRequestBean.CARSERIES);
            this.baseBean.user_id = jsonObj.getString(com.wt.statistics.persistentdata.BaseRequestBean.USER_ID);
            org.json.JSONArray exceptionArray = jsonObj.getJSONArray(EXCEPTIONS);
            for (int i = 0; i < exceptionArray.length(); i++) {
                com.wt.statistics.persistentdata.ExceptionBean.ExceptionInfo exceptionInfo = new com.wt.statistics.persistentdata.ExceptionBean.ExceptionInfo();
                org.json.JSONObject obj = exceptionArray.getJSONObject(i);
                exceptionInfo.toObject(obj.toString());
                this.infos.add(exceptionInfo);
            }
            return true;
        } catch (java.lang.Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static class ExceptionInfo implements com.wt.statistics.persistentdata.IJsonObject {
        public static final java.lang.String ANRINFO = "anrinfo";
        public static final java.lang.String CPUINFO = "cpuinfo";
        public static final java.lang.String STACKTRACE = "stacktrace";
        public static final java.lang.String TYPE = "type";
        public java.lang.String stacktrace;
        public java.lang.String type;
        public java.lang.String cpuInfo = "";
        public java.lang.String anrinfo = "";

        @Override // com.wt.statistics.persistentdata.IJsonObject
        public org.json.JSONObject toJsonObject() {
            org.json.JSONObject eventsObj = new org.json.JSONObject();
            try {
                eventsObj.put(TYPE, this.type);
                eventsObj.put(STACKTRACE, this.stacktrace);
                eventsObj.put(CPUINFO, this.cpuInfo);
                eventsObj.put(ANRINFO, this.anrinfo);
            } catch (org.json.JSONException e) {
                e.printStackTrace();
            }
            return eventsObj;
        }

        @Override // com.wt.statistics.persistentdata.IJsonObject
        public boolean toObject(java.lang.String jsonString) {
            boolean ret = true;
            if (jsonString == null) {
                return false;
            }
            try {
                org.json.JSONObject obj = new org.json.JSONObject(jsonString);
                this.type = obj.getString(TYPE);
                this.stacktrace = obj.getString(STACKTRACE);
                this.cpuInfo = obj.getString(CPUINFO);
                this.anrinfo = obj.getString(ANRINFO);
            } catch (org.json.JSONException e) {
                e.printStackTrace();
                ret = false;
            }
            return ret;
        }
    }
}
