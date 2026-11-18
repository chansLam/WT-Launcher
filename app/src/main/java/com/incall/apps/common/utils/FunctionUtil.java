package com.incall.apps.common.utils;

/* loaded from: classes.dex */
public class FunctionUtil {
    private static final int acTypeAddr = 1;
    private static final int commonDisplayAddr = 3;
    private static final int frontAirAddr = 15;
    private static final int hasACState = 6;
    private static final int hasAQSMode = 10;
    private static final int hasAcType = 1;
    private static final int hasAnimation = 28;
    private static final int hasAuto = 12;
    private static final int hasCircleMode = 9;
    private static final int hasCircleModeL = 35;
    private static final int hasCopilotSeatHeat = 17;
    private static final int hasCopilotSeatVent = 19;
    private static final int hasCopilotTemperature = 5;
    private static final int hasCopilotTemperatureL = 32;
    private static final int hasDefrost = 7;
    private static final int hasDriverSeatHeat = 16;
    private static final int hasDriverSeatVent = 18;
    private static final int hasDriverTemperature = 4;
    private static final int hasDriverTemperatureL = 31;
    private static final int hasIntelligentOptimize = 27;
    private static final int hasIonizerL = 36;
    private static final int hasLifeRecommend = 25;
    private static final int hasMaxAc = 11;
    private static final int hasNearbyAQIDisplay = 21;
    private static final int hasOFF = 15;
    private static final int hasOutAQIDisplay = 20;
    private static final int hasPM25Display = 26;
    private static final int hasPollution = 29;
    private static final int hasRearDefrost = 8;
    private static final int hasRearLock = 13;
    private static final int hasSBHI = 30;
    private static final int hasSetAutoDry = 42;
    private static final int hasSetCircle = 39;
    private static final int hasSetIntelligent = 38;
    private static final int hasSetNewWind = 45;
    private static final int hasSetUnlock = 40;
    private static final int hasSetVibrate = 44;
    private static final int hasSetVoiceRemind = 37;
    private static final int hasSetWeather = 43;
    private static final int hasSetWindow = 41;
    private static final int hasSync = 14;
    private static final int hasWeather24H = 23;
    private static final int hasWeather7D = 24;
    private static final int hasWeatherReport = 22;
    private static final int hasWindLevel = 2;
    private static final int hasWindLevelL = 33;
    private static final int hasWindMode = 3;
    private static final int hasWindModeL = 34;
    private static final int healthAirAddr = 25;
    private static final int healthMgrAddr = 30;
    private static final int seatAddr = 19;
    private static final int settingAddr = 45;
    private byte[] functionActs;
    private int type;

    private static class Holder {
        private static final com.incall.apps.common.utils.FunctionUtil instance = new com.incall.apps.common.utils.FunctionUtil(null);

        private Holder() {
        }
    }

    public static com.incall.apps.common.utils.FunctionUtil getInstance() {
        return com.incall.apps.common.utils.FunctionUtil.Holder.instance;
    }

    private FunctionUtil() {
        this.type = 1;
        this.functionActs = com.incall.proxy.ac.AirConditionManager.getInstance().getACConfigs();
        this.type = com.incall.proxy.ac.AirConditionManager.getInstance().getAcType();
        com.incall.apps.service.util.LogUtil.i("FunctionUtil", "type = " + this.type + " functionActs " + java.util.Arrays.toString(this.functionActs));
    }

    /* synthetic */ FunctionUtil(com.incall.apps.common.utils.FunctionUtil functionUtil) {
        this();
    }

    public boolean isFunctionActive() {
        return isElecAC() ? isFunctionActive(15) : isFunctionActive(15);
    }

    private boolean isFunctionActive(int index) {
        return (this.functionActs == null || this.functionActs.length == 0 || !isNotOutOfIndex(index)) ? false : true;
    }

    public boolean isElecAC() {
        return this.type != 1;
    }

    private boolean isNotOutOfIndex(int index) {
        return this.functionActs.length >= index;
    }

    public boolean isHaveAirCleaner() {
        return false;
    }

    public boolean isHaveFrontAir() {
        boolean isHave = false;
        if (isElecAC()) {
            if (isFunctionActive(15)) {
                int i = 1;
                while (true) {
                    if (i >= 15) {
                        break;
                    }
                    if (this.functionActs[i] != 1) {
                        i++;
                    } else {
                        isHave = true;
                        break;
                    }
                }
            }
        } else if (isFunctionActive(15)) {
            int i2 = 1;
            while (true) {
                if (i2 >= 15) {
                    break;
                }
                if (this.functionActs[i2] != 1) {
                    i2++;
                } else {
                    isHave = true;
                    break;
                }
            }
        }
        return isHave || isHaveChair();
    }

    public boolean isHaveRearAir() {
        return false;
    }

    public boolean isHaveChair() {
        if (isElecAC()) {
            if (!isFunctionActive(19)) {
                return false;
            }
            for (int i = 15; i < 19; i++) {
                if (this.functionActs[i] == 1) {
                    return true;
                }
            }
            return false;
        }
        if (!isFunctionActive(19)) {
            return false;
        }
        for (int i2 = 15; i2 < 19; i2++) {
            if (this.functionActs[i2] == 1) {
                return true;
            }
        }
        return false;
    }

    public boolean isHaveHealthAir() {
        if (isElecAC()) {
            if (!isFunctionActive(25)) {
                return false;
            }
            for (int i = 19; i < 25; i++) {
                if (this.functionActs[i] == 1) {
                    return true;
                }
            }
            return false;
        }
        if (!isFunctionActive(25)) {
            return false;
        }
        for (int i2 = 19; i2 < 25; i2++) {
            if (this.functionActs[i2] == 1) {
                return true;
            }
        }
        return false;
    }

    public boolean isHaveHealthMgr() {
        if (isElecAC()) {
            if (!isFunctionActive(19)) {
                return false;
            }
            for (int i = 25; i < 19; i++) {
                if (this.functionActs[i] == 1) {
                    return true;
                }
            }
            return false;
        }
        if (!isFunctionActive(30)) {
            return false;
        }
        for (int i2 = 25; i2 < 30; i2++) {
            if (this.functionActs[i2] == 1) {
                return true;
            }
        }
        return false;
    }

    public boolean isHaveLongDisplay() {
        if (isElecAC()) {
            if (!isFunctionActive(3)) {
                return false;
            }
            for (int i = 30; i < 3; i++) {
                if (this.functionActs[i] == 1) {
                    return true;
                }
            }
            return false;
        }
        if (!isFunctionActive(3)) {
            return false;
        }
        for (int i2 = 30; i2 < 3; i2++) {
            if (this.functionActs[i2] == 1) {
                return true;
            }
        }
        return false;
    }

    public boolean isHaveSetting() {
        if (isElecAC()) {
            if (!isFunctionActive(45)) {
                return false;
            }
            for (int i = 3; i < 45; i++) {
                if (this.functionActs[i] == 1) {
                    return true;
                }
            }
            return false;
        }
        if (!isFunctionActive(45)) {
            return false;
        }
        for (int i2 = 3; i2 < 45; i2++) {
            if (this.functionActs[i2] == 1) {
                return true;
            }
        }
        return false;
    }

    public boolean isHavePM25Inside() {
        return false;
    }

    public boolean isHavePM25Outside() {
        return false;
    }

    public boolean isHaveAirCleanSwitch() {
        return false;
    }

    public boolean isHaveFrontAirVolume() {
        if (isElecAC()) {
            if (isFunctionActive(2) && this.functionActs[1] == 1) {
                return true;
            }
            return false;
        }
        if (isFunctionActive(2) && this.functionActs[1] == 1) {
            return true;
        }
        return false;
    }

    public boolean isHaveFrontWindMode() {
        if (isElecAC()) {
            if (isFunctionActive(3) && this.functionActs[2] == 1) {
                return true;
            }
            return false;
        }
        if (isFunctionActive(3) && this.functionActs[2] == 1) {
            return true;
        }
        return false;
    }

    public boolean isHaveFrontDriverTemperature() {
        if (isElecAC()) {
            if (isFunctionActive(4) && this.functionActs[3] == 1) {
                return true;
            }
            return false;
        }
        if (isFunctionActive(4) && this.functionActs[3] == 1) {
            return true;
        }
        return false;
    }

    public boolean isHaveFrontCopilotTemperature() {
        return !isElecAC() && isFunctionActive(5) && this.functionActs[4] == 1;
    }

    public boolean isHaveACState() {
        if (isElecAC()) {
            if (isFunctionActive(6) && this.functionActs[5] == 1) {
                return true;
            }
            return false;
        }
        if (isFunctionActive(6) && this.functionActs[5] == 1) {
            return true;
        }
        return false;
    }

    public boolean isHaveFrontDefrost() {
        if (isElecAC()) {
            if (isFunctionActive(7) && this.functionActs[6] == 1) {
                return true;
            }
            return false;
        }
        if (isFunctionActive(7) && this.functionActs[6] == 1) {
            return true;
        }
        return false;
    }

    public boolean isHaveBehindDefrost() {
        if (isElecAC()) {
            if (isFunctionActive(8) && this.functionActs[7] == 1) {
                return true;
            }
            return false;
        }
        if (isFunctionActive(8) && this.functionActs[7] == 1) {
            return true;
        }
        return false;
    }

    public boolean isHaveRecycleMode() {
        if (isElecAC()) {
            if (isFunctionActive(9) && this.functionActs[8] == 1) {
                return true;
            }
            return false;
        }
        if (isFunctionActive(9) && this.functionActs[8] == 1) {
            return true;
        }
        return false;
    }

    public boolean isHaveAQS() {
        return isFunctionActive(10) && this.functionActs[9] == 1;
    }

    public boolean isHaveMaxAC() {
        return isFunctionActive(11) && this.functionActs[10] == 1;
    }

    public boolean isHaveFrontAuto() {
        if (isElecAC()) {
            if (isFunctionActive(12) && this.functionActs[11] == 1) {
                return true;
            }
            return false;
        }
        if (isFunctionActive(12) && this.functionActs[11] == 1) {
            return true;
        }
        return false;
    }

    public boolean isHaveRearLock() {
        return isFunctionActive(13) && this.functionActs[12] == 1;
    }

    public boolean isHaveDUAL() {
        return false;
    }

    public boolean isHaveSYNC() {
        return !isElecAC() && isFunctionActive(14) && this.functionActs[13] == 1;
    }

    public boolean isHaveFrontOff() {
        if (isElecAC()) {
            if (isFunctionActive(15) && this.functionActs[14] == 1) {
                return true;
            }
            return false;
        }
        if (isFunctionActive(15) && this.functionActs[14] == 1) {
            return true;
        }
        return false;
    }

    public boolean isHaveBehindAirVolume() {
        return false;
    }

    public boolean isHaveBehindTemperature() {
        return false;
    }

    public boolean isHaveBehindAuto() {
        return false;
    }

    public boolean isHaveBehindWindMode() {
        return false;
    }

    public boolean isHaveBehindOff() {
        return false;
    }

    public boolean isHaveDriverHeat() {
        return !isElecAC() && isFunctionActive(16) && this.functionActs[15] == 1;
    }

    public boolean isHaveCopilotHeat() {
        return !isElecAC() && isFunctionActive(17) && this.functionActs[16] == 1;
    }

    public boolean isHaveDriverAreatation() {
        return !isElecAC() && isFunctionActive(18) && this.functionActs[17] == 1;
    }

    public boolean isHaveCopilotAreatation() {
        return !isElecAC() && isFunctionActive(19) && this.functionActs[18] == 1;
    }

    public boolean isHaveFrontDriverTemperatureL() {
        if (isElecAC()) {
            if (isFunctionActive(31) && this.functionActs[30] == 1) {
                return true;
            }
            return false;
        }
        if (isFunctionActive(31) && this.functionActs[30] == 1) {
            return true;
        }
        return false;
    }

    public boolean isHaveFrontCopilotTemperatureL() {
        return !isElecAC() && isFunctionActive(32) && this.functionActs[32] == 1;
    }

    public boolean isHaveFrontAirVolumeL() {
        if (isElecAC()) {
            if (isFunctionActive(33) && this.functionActs[32] == 1) {
                return true;
            }
            return false;
        }
        if (isFunctionActive(33) && this.functionActs[32] == 1) {
            return true;
        }
        return false;
    }

    public boolean isHaveFrontWindModeL() {
        if (isElecAC()) {
            if (isFunctionActive(34) && this.functionActs[33] == 1) {
                return true;
            }
            return false;
        }
        if (isFunctionActive(34) && this.functionActs[34] == 1) {
            return true;
        }
        return false;
    }

    public boolean isHaveRecycleModeL() {
        if (isElecAC()) {
            if (isFunctionActive(35) && this.functionActs[34] == 1) {
                return true;
            }
            return false;
        }
        if (isFunctionActive(35) && this.functionActs[35] == 1) {
            return true;
        }
        return false;
    }

    public boolean isHaveIonizerL() {
        return !isElecAC() && isFunctionActive(36) && this.functionActs[36] == 1;
    }

    public boolean isHaveOutAQIDisplay() {
        if (isElecAC()) {
            if (isFunctionActive(20) && this.functionActs[20] == 1) {
                return true;
            }
            return false;
        }
        if (isFunctionActive(20) && this.functionActs[20] == 1) {
            return true;
        }
        return false;
    }

    public boolean isHavePM25Display() {
        if (isElecAC()) {
            if (isFunctionActive(26) && this.functionActs[26] == 1) {
                return true;
            }
            return false;
        }
        if (isFunctionActive(26) && this.functionActs[26] == 1) {
            return true;
        }
        return false;
    }

    public boolean isHaveSetVoiceRemind() {
        if (isElecAC()) {
            if (isFunctionActive(37) && this.functionActs[37] == 1) {
                return true;
            }
            return false;
        }
        if (isFunctionActive(37) && this.functionActs[37] == 1) {
            return true;
        }
        return false;
    }

    public boolean isHaveSetIntelligent() {
        if (isElecAC()) {
            if (isFunctionActive(38) && this.functionActs[38] == 1) {
                return true;
            }
            return false;
        }
        if (isFunctionActive(38) && this.functionActs[38] == 1) {
            return true;
        }
        return false;
    }

    public boolean isHaveSetCircle() {
        if (isElecAC()) {
            if (isFunctionActive(39) && this.functionActs[39] == 1) {
                return true;
            }
            return false;
        }
        if (isFunctionActive(39) && this.functionActs[39] == 1) {
            return true;
        }
        return false;
    }

    public boolean isHaveSetUnlock() {
        if (isElecAC()) {
            if (isFunctionActive(40) && this.functionActs[40] == 1) {
                return true;
            }
            return false;
        }
        if (isFunctionActive(40) && this.functionActs[40] == 1) {
            return true;
        }
        return false;
    }

    public boolean isHaveSetWindow() {
        if (isElecAC()) {
            if (isFunctionActive(41) && this.functionActs[41] == 1) {
                return true;
            }
            return false;
        }
        if (isFunctionActive(41) && this.functionActs[41] == 1) {
            return true;
        }
        return false;
    }

    public boolean isHaveSetAutoDry() {
        if (isElecAC()) {
            if (isFunctionActive(42) && this.functionActs[42] == 1) {
                return true;
            }
            return false;
        }
        if (isFunctionActive(42) && this.functionActs[42] == 1) {
            return true;
        }
        return false;
    }

    public boolean isHaveSetWeather() {
        if (isElecAC()) {
            if (isFunctionActive(43) && this.functionActs[43] == 1) {
                return true;
            }
            return false;
        }
        if (isFunctionActive(43) && this.functionActs[43] == 1) {
            return true;
        }
        return false;
    }

    public boolean isHaveSetVibrate() {
        if (isElecAC()) {
            if (isFunctionActive(44) && this.functionActs[44] == 1) {
                return true;
            }
            return false;
        }
        if (isFunctionActive(44) && this.functionActs[44] == 1) {
            return true;
        }
        return false;
    }

    public boolean isHaveSetNewWind() {
        if (isElecAC()) {
            if (isFunctionActive(45) && this.functionActs[45] == 1) {
                return true;
            }
            return false;
        }
        if (isFunctionActive(45) && this.functionActs[45] == 1) {
            return true;
        }
        return false;
    }
}
