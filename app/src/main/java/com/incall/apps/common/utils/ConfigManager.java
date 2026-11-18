package com.incall.apps.common.utils;

/* loaded from: classes.dex */
public class ConfigManager {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$incall$proxy$constant$SettingsConstantsDef$CustomerId = null;
    private static final java.lang.String IS_S201_6AT_L2 = "S201_6AT_LEV2";
    private static final java.lang.String IS_S201_6MT_L2 = "S201_6MT_LEV2";
    private static final java.lang.String IS_S201_7DCT_L2 = "S201_7DCT_LEV2";
    private static final java.lang.String TAG = "ConfigManager";
    private static com.incall.apps.common.utils.ConfigManager configManager;
    private java.lang.String curCarType;
    private com.incall.proxy.constant.SettingsConstantsDef.CustomerId customerId;
    private boolean have4GConfig;
    private boolean haveTboxConfig;

    static /* synthetic */ int[] $SWITCH_TABLE$com$incall$proxy$constant$SettingsConstantsDef$CustomerId() {
        int[] iArr = $SWITCH_TABLE$com$incall$proxy$constant$SettingsConstantsDef$CustomerId;
        if (iArr == null) {
            iArr = new int[com.incall.proxy.constant.SettingsConstantsDef.CustomerId.values().length];
            try {
                iArr[com.incall.proxy.constant.SettingsConstantsDef.CustomerId.B17.ordinal()] = 21;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                iArr[com.incall.proxy.constant.SettingsConstantsDef.CustomerId.CHANGAN_A301.ordinal()] = 9;
            } catch (java.lang.NoSuchFieldError e2) {
            }
            try {
                iArr[com.incall.proxy.constant.SettingsConstantsDef.CustomerId.CHANGAN_B211.ordinal()] = 8;
            } catch (java.lang.NoSuchFieldError e3) {
            }
            try {
                iArr[com.incall.proxy.constant.SettingsConstantsDef.CustomerId.CHANGAN_B311.ordinal()] = 14;
            } catch (java.lang.NoSuchFieldError e4) {
            }
            try {
                iArr[com.incall.proxy.constant.SettingsConstantsDef.CustomerId.CHANGAN_B511.ordinal()] = 7;
            } catch (java.lang.NoSuchFieldError e5) {
            }
            try {
                iArr[com.incall.proxy.constant.SettingsConstantsDef.CustomerId.CHANGAN_C211.ordinal()] = 5;
            } catch (java.lang.NoSuchFieldError e6) {
            }
            try {
                iArr[com.incall.proxy.constant.SettingsConstantsDef.CustomerId.CHANGAN_C211EV.ordinal()] = 6;
            } catch (java.lang.NoSuchFieldError e7) {
            }
            try {
                iArr[com.incall.proxy.constant.SettingsConstantsDef.CustomerId.CHANGAN_C301.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError e8) {
            }
            try {
                iArr[com.incall.proxy.constant.SettingsConstantsDef.CustomerId.CHANGAN_C301CTC.ordinal()] = 17;
            } catch (java.lang.NoSuchFieldError e9) {
            }
            try {
                iArr[com.incall.proxy.constant.SettingsConstantsDef.CustomerId.CHANGAN_JD301.ordinal()] = 19;
            } catch (java.lang.NoSuchFieldError e10) {
            }
            try {
                iArr[com.incall.proxy.constant.SettingsConstantsDef.CustomerId.CHANGAN_P201_CA6595.ordinal()] = 15;
            } catch (java.lang.NoSuchFieldError e11) {
            }
            try {
                iArr[com.incall.proxy.constant.SettingsConstantsDef.CustomerId.CHANGAN_P201_CA6596.ordinal()] = 16;
            } catch (java.lang.NoSuchFieldError e12) {
            }
            try {
                iArr[com.incall.proxy.constant.SettingsConstantsDef.CustomerId.CHANGAN_P201_CA6596_CARPLAY.ordinal()] = 20;
            } catch (java.lang.NoSuchFieldError e13) {
            }
            try {
                iArr[com.incall.proxy.constant.SettingsConstantsDef.CustomerId.CHANGAN_R103_H15T.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e14) {
            }
            try {
                iArr[com.incall.proxy.constant.SettingsConstantsDef.CustomerId.CHANGAN_S111.ordinal()] = 11;
            } catch (java.lang.NoSuchFieldError e15) {
            }
            try {
                iArr[com.incall.proxy.constant.SettingsConstantsDef.CustomerId.CHANGAN_S201.ordinal()] = 25;
            } catch (java.lang.NoSuchFieldError e16) {
            }
            try {
                iArr[com.incall.proxy.constant.SettingsConstantsDef.CustomerId.CHANGAN_S301.ordinal()] = 10;
            } catch (java.lang.NoSuchFieldError e17) {
            }
            try {
                iArr[com.incall.proxy.constant.SettingsConstantsDef.CustomerId.CHANGAN_S302.ordinal()] = 12;
            } catch (java.lang.NoSuchFieldError e18) {
            }
            try {
                iArr[com.incall.proxy.constant.SettingsConstantsDef.CustomerId.CHANGAN_S311.ordinal()] = 18;
            } catch (java.lang.NoSuchFieldError e19) {
            }
            try {
                iArr[com.incall.proxy.constant.SettingsConstantsDef.CustomerId.CHANGAN_S401.ordinal()] = 13;
            } catch (java.lang.NoSuchFieldError e20) {
            }
            try {
                iArr[com.incall.proxy.constant.SettingsConstantsDef.CustomerId.CHANGAN_V301_S.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e21) {
            }
            try {
                iArr[com.incall.proxy.constant.SettingsConstantsDef.CustomerId.CHANGAN_V302.ordinal()] = 24;
            } catch (java.lang.NoSuchFieldError e22) {
            }
            try {
                iArr[com.incall.proxy.constant.SettingsConstantsDef.CustomerId.GEELY_CV.ordinal()] = 22;
            } catch (java.lang.NoSuchFieldError e23) {
            }
            try {
                iArr[com.incall.proxy.constant.SettingsConstantsDef.CustomerId.GEELY_SV.ordinal()] = 23;
            } catch (java.lang.NoSuchFieldError e24) {
            }
            try {
                iArr[com.incall.proxy.constant.SettingsConstantsDef.CustomerId.S401.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e25) {
            }
            $SWITCH_TABLE$com$incall$proxy$constant$SettingsConstantsDef$CustomerId = iArr;
        }
        return iArr;
    }

    public static synchronized com.incall.apps.common.utils.ConfigManager getInstance() {
        com.incall.apps.common.utils.ConfigManager configManager2;
        synchronized (com.incall.apps.common.utils.ConfigManager.class) {
            if (configManager == null) {
                configManager = new com.incall.apps.common.utils.ConfigManager();
            }
            configManager2 = configManager;
        }
        return configManager2;
    }

    private ConfigManager() {
        com.incall.proxy.setting.SettingManager settingManager = com.incall.proxy.setting.SettingManager.getInstance();
        this.customerId = settingManager.getCustomerId();
        this.curCarType = settingManager.getCurCarType();
        this.haveTboxConfig = settingManager.isHaveTboxConfig();
        this.have4GConfig = settingManager.isHave4GConfig();
        android.util.Log.i(TAG, "getInstance customerId = " + this.customerId + ", curCarType = " + this.curCarType + ",haveTboxConfig = " + this.haveTboxConfig + ",have4GConfig = " + this.have4GConfig);
    }

    public boolean isShowOnlineMusic() {
        return isShowOnline();
    }

    public boolean isShowOnlineRadio() {
        return isShowOnline();
    }

    public boolean isShowOnlineService() {
        return isShowOnline();
    }

    public boolean isShowOnline() {
        boolean ret;
        switch ($SWITCH_TABLE$com$incall$proxy$constant$SettingsConstantsDef$CustomerId()[this.customerId.ordinal()]) {
            case 5:
                ret = this.have4GConfig;
                break;
            case 14:
                ret = false;
                break;
            default:
                ret = this.haveTboxConfig;
                break;
        }
        android.util.Log.i(TAG, "isShowOnline ret = " + ret);
        return ret;
    }

    public boolean isShowMap() {
        boolean ret;
        switch ($SWITCH_TABLE$com$incall$proxy$constant$SettingsConstantsDef$CustomerId()[this.customerId.ordinal()]) {
            case 4:
                if ("L2".equals(this.curCarType) || "L3-MT".equals(this.curCarType)) {
                    ret = true;
                    break;
                } else {
                    ret = false;
                    break;
                }
            case 15:
                if ("P201_L2".equals(this.curCarType) || "P201_L4".equals(this.curCarType)) {
                    ret = true;
                    break;
                } else {
                    ret = false;
                    break;
                }
            default:
                if ("L3-MT".equals(this.curCarType)) {
                    ret = true;
                    break;
                } else {
                    ret = false;
                    break;
                }
        }
        android.util.Log.i(TAG, "isShowMap ret = " + ret + ",customerId=" + this.customerId + ",curCarType=" + this.curCarType);
        return ret;
    }

    public boolean isShowMobileData() {
        boolean ret;
        switch ($SWITCH_TABLE$com$incall$proxy$constant$SettingsConstantsDef$CustomerId()[this.customerId.ordinal()]) {
            case 6:
                ret = false;
                break;
            case 14:
                ret = false;
                break;
            default:
                ret = this.have4GConfig;
                break;
        }
        android.util.Log.i(TAG, "isShowMobileData ret = " + ret);
        return ret;
    }

    public boolean isShow3DAudio() {
        boolean ret;
        switch ($SWITCH_TABLE$com$incall$proxy$constant$SettingsConstantsDef$CustomerId()[this.customerId.ordinal()]) {
            case 18:
                ret = true;
                break;
            default:
                ret = false;
                break;
        }
        android.util.Log.i(TAG, "isShow3DAudio customerId = " + this.customerId + ", ret = " + ret);
        return ret;
    }

    public boolean isShowIPTheme() {
        return false;
    }

    public boolean isShowFloat() {
        boolean ret = false;
        switch ($SWITCH_TABLE$com$incall$proxy$constant$SettingsConstantsDef$CustomerId()[this.customerId.ordinal()]) {
            case 5:
                if (!this.have4GConfig) {
                    ret = true;
                    break;
                }
                break;
            case 13:
                ret = false;
                break;
            default:
                if (!this.haveTboxConfig) {
                    ret = true;
                    break;
                }
                break;
        }
        if (this.curCarType != null && (this.curCarType.contains("P201_Overseas_L10") || this.curCarType.contains("P201_Overseas_L11") || this.curCarType.contains("P201_Overseas_L12"))) {
            ret = false;
        }
        android.util.Log.i(TAG, "isShowFloat customerId = " + this.customerId + ", ret = " + ret);
        return ret;
    }

    public boolean isShowIPUpdate() {
        return true;
    }

    public boolean isShowFeedBack() {
        boolean ret;
        switch ($SWITCH_TABLE$com$incall$proxy$constant$SettingsConstantsDef$CustomerId()[this.customerId.ordinal()]) {
            case 8:
                ret = false;
                break;
            default:
                ret = true;
                break;
        }
        if (this.curCarType != null && (this.curCarType.contains("P201_Overseas_L10") || this.curCarType.contains("P201_Overseas_L11") || this.curCarType.contains("P201_Overseas_L12"))) {
            ret = false;
        }
        android.util.Log.i(TAG, "isShowFeedBack customerId = " + this.customerId + ", ret = " + ret);
        return ret;
    }

    public boolean isShowDVR() {
        if (android.text.TextUtils.isEmpty(this.curCarType) || !"CHANGAN_S311_CARTYPE_10".equals(this.curCarType)) {
            return false;
        }
        return true;
    }

    public boolean isShowAmBient() {
        boolean ret = false;
        if (android.text.TextUtils.isEmpty(this.curCarType)) {
            ret = false;
        }
        if ("CHANGAN_S311_CARTYPE_10".equals(this.curCarType)) {
            return true;
        }
        return ret;
    }

    public boolean isNotShow3D() {
        boolean ret = false;
        if (android.text.TextUtils.isEmpty(this.curCarType)) {
            ret = false;
        }
        if ("CHANGAN_S311_CARTYPE_1".equals(this.curCarType) || "CHANGAN_S311_CARTYPE_2".equals(this.curCarType) || "CHANGAN_S311_CARTYPE_3".equals(this.curCarType) || "CHANGAN_S311_CARTYPE_6".equals(this.curCarType)) {
            return true;
        }
        return ret;
    }

    public boolean isNotShowMusicLight() {
        android.util.Log.d(TAG, "customerId = " + this.customerId.toString() + "curCarType = " + this.curCarType);
        if (!"CHANGAN_S311_CARTYPE_1".equals(this.curCarType) && !"CHANGAN_S311_CARTYPE_2".equals(this.curCarType) && !"CHANGAN_S311_CARTYPE_3".equals(this.curCarType) && !"CHANGAN_S311_CARTYPE_6".equals(this.curCarType)) {
            return false;
        }
        return true;
    }

    public boolean isNotShowTipVoice() {
        boolean ret = false;
        if (android.text.TextUtils.isEmpty(this.curCarType)) {
            ret = false;
        }
        if ("S201_6AT_LEV2".equals(this.curCarType) || "S201_6MT_LEV2".equals(this.curCarType) || "S201_7DCT_LEV2".equals(this.curCarType)) {
            return true;
        }
        return ret;
    }

    public boolean isShowFota() {
        boolean ret;
        switch ($SWITCH_TABLE$com$incall$proxy$constant$SettingsConstantsDef$CustomerId()[this.customerId.ordinal()]) {
            case 5:
                ret = true;
                break;
            case 12:
                ret = true;
                break;
            case 13:
                ret = true;
                break;
            default:
                ret = false;
                break;
        }
        android.util.Log.d(TAG, "isShowFota customerId:" + this.customerId + "   ret = " + ret);
        return ret;
    }

    public boolean isShowVoiceManage() {
        boolean ret;
        switch ($SWITCH_TABLE$com$incall$proxy$constant$SettingsConstantsDef$CustomerId()[this.customerId.ordinal()]) {
            case 5:
                if (this.curCarType.startsWith("C212")) {
                    ret = true;
                    break;
                } else {
                    ret = false;
                    break;
                }
            default:
                ret = false;
                break;
        }
        android.util.Log.d(TAG, "isShowVoiceManage customerId:" + this.customerId + "   ret = " + ret);
        return ret;
    }

    public boolean isShowMapInMulti() {
        boolean ret;
        switch ($SWITCH_TABLE$com$incall$proxy$constant$SettingsConstantsDef$CustomerId()[this.customerId.ordinal()]) {
            case 4:
            case 8:
            case 14:
                ret = true;
                break;
            default:
                ret = false;
                break;
        }
        android.util.Log.d(TAG, "isShowMapInMulti customerId:" + this.customerId + "   ret = " + ret);
        return ret;
    }

    public boolean isShowNaviInMulti() {
        boolean ret;
        switch ($SWITCH_TABLE$com$incall$proxy$constant$SettingsConstantsDef$CustomerId()[this.customerId.ordinal()]) {
            case 4:
            case 5:
            case 6:
            case 8:
            case 12:
            case 13:
            case 14:
            case 15:
                ret = true;
                break;
            case 7:
            case 9:
            case 10:
            case 11:
            default:
                ret = false;
                break;
        }
        android.util.Log.d(TAG, "isShowNaviInMulti customerId:" + this.customerId + "   ret = " + ret);
        return ret;
    }

    public boolean isShowAirStatuBar() {
        boolean ret;
        switch ($SWITCH_TABLE$com$incall$proxy$constant$SettingsConstantsDef$CustomerId()[this.customerId.ordinal()]) {
            case 4:
            case 5:
            case 6:
            case 8:
            case 12:
            case 15:
                ret = false;
                break;
            case 7:
            case 9:
            case 10:
            case 11:
            case 14:
            default:
                ret = false;
                break;
            case 13:
                ret = true;
                break;
        }
        android.util.Log.d(TAG, "isShowAirStatuBar customerId:" + this.customerId + "   ret = " + ret);
        return ret;
    }

    public boolean isShowAirClean() {
        boolean ret = false;
        switch ($SWITCH_TABLE$com$incall$proxy$constant$SettingsConstantsDef$CustomerId()[this.customerId.ordinal()]) {
            case 12:
            case 13:
            case 18:
                byte[] functionActs = com.incall.proxy.ac.AirConditionManager.getInstance().getACConfigs();
                if (functionActs != null && functionActs.length >= 4) {
                    int i = 0;
                    while (true) {
                        if (i >= 4) {
                            break;
                        } else if (functionActs[i] != 1) {
                            i++;
                        } else {
                            ret = true;
                            break;
                        }
                    }
                }
                break;
            default:
                ret = false;
                break;
        }
        android.util.Log.d(TAG, "isShowAirClean customerId:" + this.customerId + "   ret = " + ret);
        return ret;
    }

    public boolean isShowAirWindow() {
        boolean ret;
        switch ($SWITCH_TABLE$com$incall$proxy$constant$SettingsConstantsDef$CustomerId()[this.customerId.ordinal()]) {
            case 12:
            case 13:
                ret = true;
                break;
            default:
                ret = false;
                break;
        }
        android.util.Log.d(TAG, "isShowAirWindow customerId:" + this.customerId + "   ret = " + ret);
        return ret;
    }

    public boolean isC212CarType() {
        return this.curCarType.startsWith("C212");
    }

    public boolean isShowTencentMap() {
        boolean ret;
        switch ($SWITCH_TABLE$com$incall$proxy$constant$SettingsConstantsDef$CustomerId()[this.customerId.ordinal()]) {
            case 12:
                ret = true;
                break;
            default:
                ret = false;
                break;
        }
        android.util.Log.d(TAG, "isShowTencentMap customerId:" + this.customerId + "   ret = " + ret);
        return ret;
    }

    public boolean isCallAuthentication() {
        boolean ret;
        switch ($SWITCH_TABLE$com$incall$proxy$constant$SettingsConstantsDef$CustomerId()[this.customerId.ordinal()]) {
            case 5:
                ret = this.have4GConfig;
                break;
            case 6:
                ret = false;
                break;
            default:
                if (!this.haveTboxConfig && !this.have4GConfig) {
                    ret = false;
                    break;
                } else {
                    ret = true;
                    break;
                }
        }
        android.util.Log.d(TAG, "isCallAuthentication customerId:" + this.customerId + ",haveTboxConfig = " + this.haveTboxConfig + "   ret = " + ret);
        return ret;
    }

    public int isWhitchCallType() {
        int ret;
        switch ($SWITCH_TABLE$com$incall$proxy$constant$SettingsConstantsDef$CustomerId()[this.customerId.ordinal()]) {
            case 4:
            case 6:
            case 14:
            case 15:
            case 16:
                ret = 0;
                break;
            case 5:
            case 7:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            default:
                ret = 2;
                break;
            case 8:
                if (com.incall.apps.common.utils.CommonUtil.CA_B211_CAR_TYPE_6.equals(this.curCarType) || com.incall.apps.common.utils.CommonUtil.CA_B211_CAR_TYPE_7.equals(this.curCarType) || com.incall.apps.common.utils.CommonUtil.CA_B211_CAR_TYPE_8.equals(this.curCarType) || com.incall.apps.common.utils.CommonUtil.CA_B211_CAR_TYPE_9.equals(this.curCarType) || com.incall.apps.common.utils.CommonUtil.CA_B211_CAR_TYPE_10.equals(this.curCarType)) {
                    ret = 0;
                    break;
                } else {
                    ret = 2;
                    break;
                }
        }
        android.util.Log.d(TAG, "isWhitchCallType customerId:" + this.customerId + "   ret = " + ret);
        return ret;
    }

    public java.lang.String isShowAudioBackGround() {
        if (com.incall.proxy.constant.SettingsConstantsDef.CustomerId.isLikeB211(this.customerId)) {
            return "hbs_B211";
        }
        if (com.incall.proxy.constant.SettingsConstantsDef.CustomerId.isLikeC211(this.customerId) || com.incall.proxy.constant.SettingsConstantsDef.CustomerId.isLikeC211EV(this.customerId) || com.incall.proxy.constant.SettingsConstantsDef.CustomerId.isLikeC301(this.customerId) || com.incall.proxy.constant.SettingsConstantsDef.CustomerId.isLikeS302(this.customerId)) {
            return "hbs_C211";
        }
        if (com.incall.proxy.constant.SettingsConstantsDef.CustomerId.isLikeB311(this.customerId)) {
            return "hbs_B311";
        }
        return "none";
    }

    public boolean isSevenIPIcon() {
        if (!isShowAudioBackGround().contains("B311")) {
            return false;
        }
        if (!"L2".equals(this.curCarType) && !"L3".equals(this.curCarType)) {
            return false;
        }
        return true;
    }

    public java.lang.String isShowThemeText() {
        switch ($SWITCH_TABLE$com$incall$proxy$constant$SettingsConstantsDef$CustomerId()[this.customerId.ordinal()]) {
            case 4:
            case 5:
                return "hbs_C211";
            case 6:
                return "hbs_C211EV";
            case 7:
            case 9:
            case 10:
            case 11:
            default:
                return "none";
            case 8:
                return "hbs_B211";
            case 12:
                return "hbs_S302";
            case 13:
                return "hbs_S401";
            case 14:
                return "hbs_B311";
        }
    }

    public boolean isShowNightMode() {
        boolean ret = true;
        if (com.incall.proxy.constant.SettingsConstantsDef.CustomerId.isLikeC211(this.customerId) || com.incall.proxy.constant.SettingsConstantsDef.CustomerId.isLikeC211EV(this.customerId) || com.incall.proxy.constant.SettingsConstantsDef.CustomerId.isLikeB211(this.customerId)) {
            ret = false;
        }
        android.util.Log.i(TAG, "isShowNightMode ret=" + ret);
        return ret;
    }

    public boolean isShowTencent() {
        boolean ret = com.incall.proxy.setting.SettingManager.getInstance().isTencentUi();
        android.util.Log.i(TAG, "isShowTencent ret=" + ret);
        return ret;
    }

    public java.lang.String getNavType() {
        if (!com.incall.proxy.constant.SettingsConstantsDef.CustomerId.isLikeS401(this.customerId) && !com.incall.proxy.constant.SettingsConstantsDef.CustomerId.isLikeS302(this.customerId)) {
            return "GaoDe";
        }
        return "GaoDe";
    }

    public boolean isHaveMap() {
        boolean ret = true;
        if ("P201_L1".equals(this.curCarType) || "P201_L3".equals(this.curCarType)) {
            ret = false;
        }
        android.util.Log.i(TAG, "isHaveMap ret=" + ret + ",customerId=" + this.customerId);
        return ret;
    }

    public boolean callLongHangUpCarType() {
        com.incall.apps.service.util.LogUtil.i(TAG, "callLongHangUpCarType == " + this.curCarType);
        if (!"CHANGAN_S311_CARTYPE_4".equals(this.curCarType) && !"CHANGAN_S311_CARTYPE_5".equals(this.curCarType) && !"CHANGAN_S311_CARTYPE_6".equals(this.curCarType) && !"CHANGAN_S311_CARTYPE_7".equals(this.curCarType) && !"CHANGAN_S311_CARTYPE_8".equals(this.curCarType)) {
            return false;
        }
        return true;
    }

    public boolean isShowCallInTTsView() {
        if (!com.incall.proxy.constant.SettingsConstantsDef.CustomerId.isLikeCAS201(this.customerId) || "S201_6AT_LEV2".equals(this.curCarType) || "S201_6MT_LEV2".equals(this.curCarType) || "S201_7DCT_LEV2".equals(this.curCarType)) {
            return false;
        }
        return true;
    }

    public boolean isS201CarType() {
        return com.incall.proxy.constant.SettingsConstantsDef.CustomerId.isLikeCAS201(this.customerId);
    }
}
