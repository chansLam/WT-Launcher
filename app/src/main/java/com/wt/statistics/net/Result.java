package com.wt.statistics.net;

/* loaded from: classes.dex */
public class Result {
    public java.lang.String code;
    public java.lang.Object data;
    public java.lang.String message;
    public java.lang.String sign;

    public void toObject(java.lang.String jsonString) {
        try {
            org.json.JSONObject jsonObj = new org.json.JSONObject(jsonString);
            try {
                this.code = jsonObj.getString("code");
                this.message = jsonObj.getString("message");
                this.sign = jsonObj.getString("sign");
                this.data = jsonObj.getString("data");
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        } catch (java.lang.Exception e2) {
            e2.printStackTrace();
        }
    }
}
