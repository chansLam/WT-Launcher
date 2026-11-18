package com.autopai.support;

/* loaded from: classes.dex */
public class AppGlobal {
    private static final java.lang.String LOGTAG = "AppGlobal";
    private static com.autopai.support.AppGlobal mInstance;
    private android.content.Context mAppCxt;
    private java.lang.String mCarType = "";
    private android.content.pm.PackageInfo mInfo;

    public static class AppInfo {
        public long appVersionCode;
        public java.lang.String appVersionName;
        public java.lang.String packageName;
        public java.lang.String sdkVersion;
    }

    private AppGlobal(android.content.Context cxt) {
        if (cxt != null) {
            this.mAppCxt = cxt.getApplicationContext();
            android.content.pm.PackageManager pkgMgr = this.mAppCxt.getPackageManager();
            try {
                this.mInfo = pkgMgr.getPackageInfo(this.mAppCxt.getPackageName(), 0);
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
        getCarType();
    }

    public static com.autopai.support.AppGlobal getInstance(android.content.Context cxt) {
        if (mInstance == null) {
            synchronized (com.autopai.support.AppGlobal.class) {
                if (mInstance == null) {
                    mInstance = new com.autopai.support.AppGlobal(cxt);
                }
            }
        }
        return mInstance;
    }

    public java.lang.String getCarType() {
        if (android.text.TextUtils.isEmpty(this.mCarType)) {
            this.mCarType = com.autopai.support.utils.Utils.getCurCarType();
        }
        com.autopai.support.utils.SLog.d(LOGTAG, "getCarType mCarType = " + this.mCarType);
        return this.mCarType;
    }

    public boolean hasDoubleAirCondition() {
        boolean ret = true;
        if (!"releaseS311".equals("releaseS311") && !"releaseS311".equals(com.autopai.support.proxy.ProxyConstants.S311_DEBUG)) {
            com.autopai.support.utils.SLog.d(LOGTAG, "hasDoubleAirCondition S311Z return");
            return true;
        }
        getCarType();
        if (!"CHANGAN_S311_CARTYPE_10".equals(this.mCarType)) {
            ret = false;
        }
        return ret;
    }

    public boolean isAutoAirCondition() {
        getCarType();
        if (!"CHANGAN_S311_CARTYPE_1".equals(this.mCarType) && !"CHANGAN_S311_CARTYPE_2".equals(this.mCarType) && !"CHANGAN_S311_CARTYPE_3".equals(this.mCarType) && !"CHANGAN_S311_CARTYPE_6".equals(this.mCarType)) {
            return true;
        }
        return false;
    }

    public boolean hasDvrDevice() {
        boolean ret = true;
        if (!"releaseS311".equals("releaseS311") && !"releaseS311".equals(com.autopai.support.proxy.ProxyConstants.S311_DEBUG)) {
            com.autopai.support.utils.SLog.d(LOGTAG, "hasDvrDevice S311Z return");
            return true;
        }
        getCarType();
        if (!"CHANGAN_S311_CARTYPE_10".equals(this.mCarType)) {
            ret = false;
        }
        return ret;
    }

    public com.autopai.support.AppGlobal.AppInfo getAppInfo() {
        com.autopai.support.AppGlobal.AppInfo info = new com.autopai.support.AppGlobal.AppInfo();
        info.sdkVersion = com.autopai.support.BuildConfig.VERSION_NAME;
        if (this.mInfo != null) {
            info.appVersionCode = this.mInfo.versionCode;
            info.appVersionName = this.mInfo.versionName;
            info.packageName = this.mInfo.packageName;
        }
        return info;
    }
}
