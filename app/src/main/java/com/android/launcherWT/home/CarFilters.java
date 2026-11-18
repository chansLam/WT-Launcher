package com.android.launcherWT.home;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Port of AppsConfig.mDisableCarTypeList / mDisableAppsList / showTest from
 * LauncherWT 1.8.17.23. On some car types the 360 DVR and the comfort cockpit
 * are not offered at all, and the third party installer flag drops the whole
 * whitelist filter.
 */
final class CarFilters {
    private static final String TAG = "CarFilters";
    private static final String PROP_THIRD_APK = "sys.thirdapk.caninstall";
    private static final String DEFAULT_CAR_TYPE = "CHANGAN_S311_CARTYPE_10";

    private static final Set<String> DISABLE_PACKAGES = new HashSet<String>(Arrays.asList(
            "com.coagent.dvr",
            "com.incall.apps.comfortcockpit"));

    private static final Set<String> DISABLE_CAR_TYPES = new HashSet<String>(Arrays.asList(
            "CHANGAN_S311_CARTYPE_1", "CHANGAN_S311_CARTYPE_2", "CHANGAN_S311_CARTYPE_3",
            "CHANGAN_S311_CARTYPE_4", "CHANGAN_S311_CARTYPE_5", "CHANGAN_S311_CARTYPE_6",
            "CHANGAN_S311_CARTYPE_7", "CHANGAN_S311_CARTYPE_8", "CHANGAN_S311_CARTYPE_9",
            "CHANGAN_S311_CARTYPE_11", "CHANGAN_S311_CARTYPE_12", "CHANGAN_S311_CARTYPE_13",
            "CHANGAN_S311_CARTYPE_14"));

    private static Boolean whitelistActive;
    private static Boolean carTypeDisabled;
    private static String cachedCarType;

    private CarFilters() {
    }

    /** sys.thirdapk.caninstall == 1 turns the whitelist off, exactly like showTest(). */
    static boolean whitelistActive() {
        if (whitelistActive == null) {
            whitelistActive = "1".equals(readProp(PROP_THIRD_APK)) ? Boolean.FALSE : Boolean.TRUE;
        }
        return whitelistActive.booleanValue();
    }

    static String carType() {
        if (cachedCarType == null) {
            String type = null;
            try {
                type = com.incall.proxy.setting.SettingManager.getInstance().getCurCarType();
            } catch (Throwable unavailable) {
                Log.i(TAG, "car type unavailable: " + unavailable);
            }
            cachedCarType = type == null ? DEFAULT_CAR_TYPE : type;
        }
        return cachedCarType;
    }

    /** Hidden only when the car type and the package are disabled at the same time. */
    static boolean hidden(String packageName) {
        if (packageName == null || !whitelistActive()) {
            return false;
        }
        if (!DISABLE_PACKAGES.contains(packageName)) {
            return false;
        }
        if (carTypeDisabled == null) {
            carTypeDisabled = DISABLE_CAR_TYPES.contains(carType()) ? Boolean.TRUE : Boolean.FALSE;
        }
        return carTypeDisabled.booleanValue();
    }

    private static String readProp(String property) {
        try {
            Process process = Runtime.getRuntime().exec("getprop " + property);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String value = reader.readLine();
            reader.close();
            return value;
        } catch (Exception unreadable) {
            Log.i(TAG, "readProp " + property + " failed: " + unreadable);
            return null;
        }
    }
}
