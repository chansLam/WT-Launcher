package com.wt.statistics.net;

/* loaded from: classes.dex */
public class ApiUtils {
    private static final java.lang.String LOGTAG = "ApiUtils";

    public static com.wt.statistics.net.Result getTokenRequest(com.wt.statistics.persistentdata.CommonBean bean) {
        com.wt.statistics.net.Result result = new com.wt.statistics.net.Result();
        org.json.JSONObject object = new org.json.JSONObject();
        try {
            object.put("vin", bean.vin);
            object.put(com.wt.statistics.persistentdata.ServerConfigs.APPKEY, java.lang.String.valueOf(bean.app_id));
            object.put("appSecret", "");
            object.put(com.wt.statistics.persistentdata.CommonBean.APP_VERSION, bean.app_version);
            if (android.text.TextUtils.isEmpty(bean.os_verison)) {
                bean.os_verison = com.wt.statistics.utils.WTUtils.getOSVersion();
            }
            object.put("os_verison", bean.os_verison);
            if (android.text.TextUtils.isEmpty(bean.car_type)) {
                bean.car_type = com.wt.statistics.utils.WTUtils.getCurCarType();
            }
            com.wt.statistics.utils.SLog.d(LOGTAG, "getTokenRequest bean.car_type =" + bean.car_type);
            object.put(com.wt.statistics.persistentdata.BaseRequestBean.CARSERIES, bean.car_type);
            if (android.text.TextUtils.isEmpty(bean.channel_id)) {
                bean.channel_id = com.wt.statistics.persistentdata.CommonBean.UNKNOW;
            }
            object.put("channel_id", bean.channel_id);
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
        com.wt.statistics.utils.SLog.i(LOGTAG, "getTokenRequest object.toString()=" + object.toString());
        java.lang.String responseValue = com.wt.statistics.net.UrlConnectUtils.doPost(com.wt.statistics.net.UrlConstants.getTokenUrl(), null, object.toString());
        com.wt.statistics.utils.SLog.i(LOGTAG, "getTokenRequest responseValue=" + responseValue);
        try {
            org.json.JSONObject obj = new org.json.JSONObject(responseValue);
            result.code = obj.getString("code");
            result.message = obj.getString("message");
            result.sign = obj.getString("sign");
            org.json.JSONObject dataObj = obj.getJSONObject("data");
            com.wt.statistics.net.TokenBean tokenBean = new com.wt.statistics.net.TokenBean();
            tokenBean.token = dataObj.getString(com.wt.statistics.net.TokenBean.TOKEN);
            tokenBean.expirationDate = dataObj.getString(com.wt.statistics.net.TokenBean.EXPIRATIONDATE);
            result.data = tokenBean;
        } catch (org.json.JSONException e2) {
            e2.printStackTrace();
        }
        return result;
    }

    public static com.wt.statistics.net.Result refreshTokenRequest(java.lang.String badToken) {
        com.wt.statistics.net.Result result = new com.wt.statistics.net.Result();
        java.util.HashMap<java.lang.String, java.lang.String> header = new java.util.HashMap<>();
        java.lang.String bad = "Bearer " + badToken;
        header.put("Authorization", bad);
        com.wt.statistics.utils.SLog.i(LOGTAG, "refreshTokenRequest badToken=" + bad);
        java.lang.String responseValue = com.wt.statistics.net.UrlConnectUtils.doGet(com.wt.statistics.net.UrlConstants.getRefreshTokenUrl(), header, null);
        com.wt.statistics.utils.SLog.i(LOGTAG, "refreshTokenRequest responseValue=" + responseValue);
        try {
            org.json.JSONObject obj = new org.json.JSONObject(responseValue);
            result.code = obj.getString("code");
            result.message = obj.getString("message");
            result.sign = obj.getString("sign");
            org.json.JSONObject dataObj = obj.getJSONObject("data");
            com.wt.statistics.net.TokenBean tokenBean = new com.wt.statistics.net.TokenBean();
            tokenBean.token = dataObj.getString(com.wt.statistics.net.TokenBean.TOKEN);
            tokenBean.expirationDate = dataObj.getString(com.wt.statistics.net.TokenBean.EXPIRATIONDATE);
            result.data = tokenBean;
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static com.wt.statistics.net.Result getConfigRequest(com.wt.statistics.persistentdata.CommonBean commBean, com.wt.statistics.net.TokenBean tokenBean) {
        com.wt.statistics.net.Result result = new com.wt.statistics.net.Result();
        org.json.JSONObject object = new org.json.JSONObject();
        java.lang.String carSeries = commBean.car_type;
        if (android.text.TextUtils.isEmpty(carSeries)) {
            carSeries = com.wt.statistics.utils.WTUtils.getCurCarType();
        }
        com.wt.statistics.utils.SLog.d(LOGTAG, "getConfigRequest carSeries is carSeries=" + carSeries);
        try {
            object.put("vin", commBean.vin);
            object.put("channel_id", commBean.channel_id);
            object.put(com.wt.statistics.persistentdata.BaseRequestBean.CARSERIES, commBean.channel_id);
            org.json.JSONArray keyArray = new org.json.JSONArray();
            keyArray.put(0, commBean.app_id);
            object.put("appKeys", keyArray);
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
        java.util.HashMap<java.lang.String, java.lang.String> header = new java.util.HashMap<>();
        java.lang.String headToken = "Bearer " + tokenBean.token;
        header.put("Authorization", headToken);
        com.wt.statistics.utils.SLog.i(LOGTAG, "getConfigRequest object.toString()=" + object.toString());
        java.lang.String responseValue = com.wt.statistics.net.UrlConnectUtils.doPost(com.wt.statistics.net.UrlConstants.getConfigUrl(), header, object.toString());
        com.wt.statistics.utils.SLog.i(LOGTAG, "getConfigRequest responseValue=" + responseValue);
        try {
            org.json.JSONObject obj = new org.json.JSONObject(responseValue);
            result.code = obj.getString("code");
            result.message = obj.getString("message");
            result.sign = obj.getString("sign");
            java.util.ArrayList<com.wt.statistics.persistentdata.ConfigBean> list = new java.util.ArrayList<>();
            org.json.JSONArray arrayObj = obj.getJSONArray("data");
            for (int i = 0; i < arrayObj.length(); i++) {
                org.json.JSONObject data = arrayObj.getJSONObject(i);
                com.wt.statistics.persistentdata.ConfigBean bean = new com.wt.statistics.persistentdata.ConfigBean();
                bean.setAppKey(data.getString(com.wt.statistics.persistentdata.ServerConfigs.APPKEY));
                bean.setReportStrategy(data.getInt(com.wt.statistics.persistentdata.ServerConfigs.REPORTSTRATEGY));
                list.add(bean);
            }
            result.data = list;
        } catch (org.json.JSONException e2) {
            e2.printStackTrace();
        }
        return result;
    }

    public static com.wt.statistics.net.Result collectRequest(java.lang.String sendBody, com.wt.statistics.net.TokenBean tokenBean) {
        com.wt.statistics.net.Result result = new com.wt.statistics.net.Result();
        com.wt.statistics.utils.SLog.i(LOGTAG, "collectRequest sendBody=" + sendBody);
        java.util.HashMap<java.lang.String, java.lang.String> header = new java.util.HashMap<>();
        java.lang.String headToken = "Bearer " + tokenBean.token;
        header.put("Authorization", headToken);
        java.lang.String responseValue = com.wt.statistics.net.UrlConnectUtils.doPost(com.wt.statistics.net.UrlConstants.getEventUrl(), header, sendBody);
        com.wt.statistics.utils.SLog.i(LOGTAG, "collectRequest responseValue=" + responseValue);
        result.toObject(responseValue);
        return result;
    }

    public static com.wt.statistics.net.Result collectExceptionRequest(java.lang.String sendBody, com.wt.statistics.net.TokenBean tokenBean) {
        com.wt.statistics.net.Result result = new com.wt.statistics.net.Result();
        com.wt.statistics.utils.SLog.i(LOGTAG, "collectExceptionRequest sendBody=" + sendBody);
        java.util.HashMap<java.lang.String, java.lang.String> header = new java.util.HashMap<>();
        java.lang.String headToken = "Bearer " + tokenBean.token;
        header.put("Authorization", headToken);
        java.lang.String responseValue = com.wt.statistics.net.UrlConnectUtils.doPost(com.wt.statistics.net.UrlConstants.getExceptionEventUrl(), header, sendBody);
        com.wt.statistics.utils.SLog.i(LOGTAG, "collectRequest responseValue=" + responseValue);
        result.toObject(responseValue);
        return result;
    }
}
