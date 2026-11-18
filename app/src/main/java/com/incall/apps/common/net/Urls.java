package com.incall.apps.common.net;

/* loaded from: classes.dex */
public class Urls {
    public static final java.lang.String AUTHORIZE_BY_STATE_CODE = "/oauth/uc/authorizeByStateCode";
    public static final java.lang.String GET_AD = "/appserver/api/hu/2.0/getAdsInfo";
    public static final java.lang.String GET_BALANCE_INFO = "/appserver/api/hu/2.0/getBalanceInfo";
    public static final java.lang.String GET_DRIVING_PLAN = "/huapi/api/hu/2.0/getDrivingPlan";
    public static final java.lang.String GET_FEEDBACK = "/api/hu/2.0/feedback";
    public static final java.lang.String GET_LOCATION = "http://api.map.baidu.com/location/ip?";
    public static final java.lang.String GET_MILEAGE = "/appserver/api/hu/2.0/getMaintainMileageInfo";
    public static final java.lang.String GET_QR_CORD = "/huapi/v1.0/getQRCode";
    public static final java.lang.String GET_QR_CORD_SACN_RESULT = "/huapi/v1.0/getQRCodeScanResult";
    public static final java.lang.String GET_SHAM_DOWNLOAD_URL = "/api/hu/2.0/getShamDownlodUrl";
    public static final java.lang.String GET_TEST_TOKEN = "https://tspdemo.changan.com.cn/api/hu/2.0/getAccessToken";
    public static final java.lang.String GET_TEST_UPDATE = "https://tspdemo.changan.com.cn/api/hu/2.0/getAppPackages";
    public static final java.lang.String GET_THEME = "/appserver/api/hu/2.0/getSystemTheme";
    public static final java.lang.String GET_TOKEN = "/api/hu/2.0/getAccessToken";
    public static final java.lang.String GET_UPDATE = "/api/hu/2.0/getAppPackages";
    public static final java.lang.String GET_USER_INFO = "/huapi/v1.0/getUserInfo";
    public static final java.lang.String GET_USER_TOKEN = "/huapi/v1.0/getUserToken";
    public static final java.lang.String GET_WALLPAPER = "/appserver/api/hu/2.0/getWallpaper";
    public static final java.lang.String GET_WEATHER = "/api/hu/2.0/getWeather";
    public static final java.lang.String LOGOUT = "/huapi/v1.0/logout";
    public static final java.lang.String NET_WORK = "/appserver/api/network/test";
    public static final java.lang.String SERVER_URL = "/";
    public static final java.lang.String UPLOAD_USER_BEHAVIOR = "/appserver/api/hu/2.0/userBehavior";
    public static final java.lang.String UPLOAD_USER_DATA = "/api/hu/2.0/behavior";

    public static java.lang.String getUrl(java.lang.String host, java.lang.String end) {
        return "https://" + host + end;
    }
}
