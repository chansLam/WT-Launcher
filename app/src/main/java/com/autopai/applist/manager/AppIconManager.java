package com.autopai.applist.manager;

/* loaded from: classes.dex */
public class AppIconManager {
    private static final java.lang.String TAG = "AppIconManager";

    public static java.lang.String getAppIconResourcesId(java.lang.String packageName, java.lang.String className) {
        java.util.LinkedHashMap<java.lang.String, java.lang.String> appsIconResIdMap = com.autopai.applist.model.config.AppsConfig.getIcons();
        if (com.autopai.applist.model.config.AppsConfig.favoritesAppList.contains(packageName) || com.autopai.applist.model.config.AppsConfig.favoritesAppList.contains(packageName + com.incall.apps.common.net.Urls.SERVER_URL + className)) {
            android.util.Log.i(TAG, "getAppIconResourcesId aaaaaa: --------> \n packageName = " + packageName);
            for (int i = 0; i < com.autopai.applist.model.config.AppsConfig.favoritesAppList.size(); i++) {
                java.lang.String appPackageName = com.autopai.applist.model.config.AppsConfig.favoritesAppList.get(i);
                if (appPackageName.equals(packageName) || appPackageName.equals(packageName + com.incall.apps.common.net.Urls.SERVER_URL + className)) {
                    java.lang.String appIconResId = appsIconResIdMap.get(appPackageName);
                    android.util.Log.i(TAG, "getAppIconResourcesId aaaaaa: " + appPackageName + ", " + appIconResId);
                    return appIconResId;
                }
            }
        }
        return "";
    }

    public static java.lang.String getAppListBGResourceId() {
        return com.autopai.applist.model.config.AppsConfig.getBackground();
    }

    public static java.lang.String[] getMarkerResourcedIds() {
        return com.autopai.applist.model.config.AppsConfig.getMarkerType();
    }
}
