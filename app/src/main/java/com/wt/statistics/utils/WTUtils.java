package com.wt.statistics.utils;

/* loaded from: classes.dex */
public class WTUtils {
    private static final java.lang.String APP_ID = "WT_APP_ID";
    private static final java.lang.String CHANNEL_ID = "WT_CHANNEL_ID";
    private static final java.lang.String LOGTAG = "WTUtils";
    private static final java.lang.String SAVE_APP_ID = "appID";
    private static final java.lang.String SAVE_CHANNEL_ID = "channelID";
    private static final java.lang.String WT_COMMON_CONFIG = "wt_common_config";

    public static java.lang.String getAppIDByXml(android.content.Context cxt) {
        return getStringMetaData(cxt, APP_ID);
    }

    public static java.lang.String getChannelIDByXml(android.content.Context cxt) {
        return getStringMetaData(cxt, CHANNEL_ID);
    }

    public static void setAppID(android.content.Context cxt, java.lang.String appKey) {
        if (cxt != null && !android.text.TextUtils.isEmpty(appKey)) {
            try {
                android.content.SharedPreferences preferences = cxt.getApplicationContext().getSharedPreferences(WT_COMMON_CONFIG, 0);
                if (preferences != null) {
                    preferences.edit().putString(SAVE_APP_ID, appKey).commit();
                }
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void setChannelID(android.content.Context cxt, java.lang.String channel) {
        if (cxt != null && !android.text.TextUtils.isEmpty(channel)) {
            try {
                android.content.SharedPreferences preferences = cxt.getApplicationContext().getSharedPreferences(WT_COMMON_CONFIG, 0);
                if (preferences != null) {
                    preferences.edit().putString(SAVE_CHANNEL_ID, channel).commit();
                }
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static java.lang.String getAppID(android.content.Context cxt) {
        java.lang.String value = "";
        if (cxt == null) {
            return "";
        }
        try {
            android.content.SharedPreferences preferences = cxt.getApplicationContext().getSharedPreferences(WT_COMMON_CONFIG, 0);
            value = preferences.getString(SAVE_APP_ID, null);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static java.lang.String getChannelID(android.content.Context cxt) {
        java.lang.String value = "";
        if (cxt == null) {
            return "";
        }
        try {
            android.content.SharedPreferences preferences = cxt.getApplicationContext().getSharedPreferences(WT_COMMON_CONFIG, 0);
            value = preferences.getString(SAVE_CHANNEL_ID, null);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static java.lang.String getAppVersionCode(android.content.Context cxt) {
        if (cxt == null) {
            return "";
        }
        try {
            android.content.pm.PackageInfo info = cxt.getPackageManager().getPackageInfo(cxt.getPackageName(), 0);
            int code = info.versionCode;
            return java.lang.String.valueOf(code);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static java.lang.String getAppVersionName(android.content.Context cxt) {
        if (cxt == null) {
            return "";
        }
        try {
            android.content.pm.PackageInfo info = cxt.getPackageManager().getPackageInfo(cxt.getPackageName(), 0);
            return info.versionName;
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static java.lang.String getCurCarType() {
        try {
            java.lang.Class clazz = java.lang.Class.forName("com.incall.proxy.setting.SettingManager");
            java.lang.reflect.Method method = clazz.getMethod("getInstance", null);
            java.lang.Object obj = method.invoke(clazz, null);
            java.lang.reflect.Method m = clazz.getMethod("getCurCarType", null);
            java.lang.Object carType = m.invoke(obj, null);
            com.wt.statistics.utils.SLog.i(LOGTAG, "getCurCarType carType = " + carType);
            return (java.lang.String) carType;
        } catch (java.lang.Exception e) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "getCurCarType exception");
            e.printStackTrace();
            return "";
        }
    }

    public static java.lang.String getOSVersion() {
        try {
            java.lang.Class clazz = java.lang.Class.forName("com.incall.proxy.setting.SettingManager");
            java.lang.reflect.Method method = clazz.getMethod("getInstance", null);
            java.lang.Object obj = method.invoke(clazz, null);
            java.lang.reflect.Method m = clazz.getMethod("getOSVersion", null);
            java.lang.Object osVersion = m.invoke(obj, null);
            return (java.lang.String) osVersion;
        } catch (java.lang.Exception e) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "getOSVersion exception");
            e.printStackTrace();
            return "";
        }
    }

    public static boolean checkPermission(android.content.Context cxt, java.lang.String permission) {
        if (cxt == null) {
            return false;
        }
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            try {
                java.lang.Class clazz = java.lang.Class.forName("android.content.Context");
                java.lang.reflect.Method method = clazz.getMethod("checkSelfPermission", java.lang.String.class);
                int i = ((java.lang.Integer) method.invoke(cxt, permission)).intValue();
                return i == 0;
            } catch (java.lang.Exception e) {
                return false;
            }
        }
        android.content.pm.PackageManager mgr = cxt.getPackageManager();
        return mgr.checkPermission(permission, cxt.getPackageName()) == 0;
    }

    public static java.lang.String getMainProcessName(android.content.Context cxt) {
        if (cxt == null) {
            return "";
        }
        try {
            java.lang.String mainProcessName = cxt.getApplicationContext().getApplicationInfo().processName;
            return mainProcessName;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static void writeFileData(android.content.Context cxt, java.lang.String fileName, java.lang.String content) {
        if (cxt != null) {
            java.lang.String path = cxt.getFilesDir().getPath();
            java.io.File file = new java.io.File(path + fileName);
            com.wt.statistics.utils.SLog.i(LOGTAG, "writeFileData path=" + path + ";file=" + file.exists());
            try {
                java.io.FileOutputStream ostream = new java.io.FileOutputStream(file);
                try {
                    byte[] buffer = content.getBytes();
                    ostream.write(buffer);
                    ostream.close();
                } catch (java.lang.Exception e) {
                    e.printStackTrace();
                }
            } catch (java.lang.Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private static java.lang.String getStringMetaData(android.content.Context cxt, java.lang.String metaName) {
        try {
            android.content.pm.ApplicationInfo info = cxt.getPackageManager().getApplicationInfo(cxt.getPackageName(), 128);
            java.lang.String value = info.metaData.getString(metaName);
            return value;
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static java.lang.String getProp(java.lang.String key) {
        try {
            java.lang.Class<?> clazz = java.lang.Class.forName("android.os.SystemProperties");
            java.lang.reflect.Method get = clazz.getMethod("get", java.lang.String.class);
            java.lang.String value = (java.lang.String) get.invoke(clazz, key);
            return value;
        } catch (java.lang.Exception e) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "getProp exception");
            e.printStackTrace();
            return null;
        }
    }

    public static java.lang.String getVin() {
        return getProp("persist.sys.mcutoapp.vin");
    }

    public static boolean isNetworkAvailable(android.content.Context context) {
        if (context == null) {
            return false;
        }
        try {
            android.net.ConnectivityManager connectivity = (android.net.ConnectivityManager) context.getSystemService("connectivity");
            if (connectivity == null) {
                com.wt.statistics.utils.SLog.i(LOGTAG, "couldn't get connectivity manager");
            } else {
                android.net.NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (android.net.NetworkInfo networkInfo : info) {
                        if (networkInfo.getState() == android.net.NetworkInfo.State.CONNECTED) {
                            com.wt.statistics.utils.SLog.d(LOGTAG, "network is available");
                            return true;
                        }
                    }
                }
            }
        } catch (java.lang.Throwable var6) {
            com.wt.statistics.utils.SLog.d(LOGTAG, "Exception:" + var6.getStackTrace());
        }
        com.wt.statistics.utils.SLog.d(LOGTAG, "network is not available");
        return false;
    }

    public static java.lang.String getMac(android.content.Context cxt) {
        java.lang.String macString = getMacAddress();
        com.wt.statistics.utils.SLog.i(LOGTAG, "getMac macString = " + macString);
        return macString;
    }

    private static java.lang.String getLocalMacAddressFromWifiInfo(android.content.Context cxt) {
        android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) cxt.getSystemService("wifi");
        android.net.wifi.WifiInfo wifiInfo = wifi.getConnectionInfo();
        java.lang.String mac = wifiInfo.getMacAddress();
        return mac;
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0023, code lost:
    
        r3 = r5.trim();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.lang.String getMacAddress() {
        /*
            r3 = 0
            java.lang.String r5 = ""
            java.lang.Runtime r6 = java.lang.Runtime.getRuntime()     // Catch: java.io.IOException -> L40
            java.lang.String r7 = "cat /sys/class/net/wlan0/address"
            java.lang.Process r4 = r6.exec(r7)     // Catch: java.io.IOException -> L40
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch: java.io.IOException -> L40
            java.io.InputStream r6 = r4.getInputStream()     // Catch: java.io.IOException -> L40
            r2.<init>(r6)     // Catch: java.io.IOException -> L40
            java.io.LineNumberReader r1 = new java.io.LineNumberReader     // Catch: java.io.IOException -> L40
            r1.<init>(r2)     // Catch: java.io.IOException -> L40
        L1b:
            if (r5 == 0) goto L27
            java.lang.String r5 = r1.readLine()     // Catch: java.io.IOException -> L40
            if (r5 == 0) goto L1b
            java.lang.String r3 = r5.trim()     // Catch: java.io.IOException -> L40
        L27:
            java.lang.String r6 = "WTUtils"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "getMacAddress macSerial ="
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.StringBuilder r7 = r7.append(r3)
            java.lang.String r7 = r7.toString()
            com.wt.statistics.utils.SLog.d(r6, r7)
            return r3
        L40:
            r0 = move-exception
            r0.printStackTrace()
            goto L27
        */
        throw new UnsupportedOperationException("Method not decompiled: com.wt.statistics.utils.WTUtils.getMacAddress():java.lang.String");
    }
}
