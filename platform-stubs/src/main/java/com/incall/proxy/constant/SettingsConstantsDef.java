package com.incall.proxy.constant;

public class SettingsConstantsDef {
    public SettingsConstantsDef() {}
    public static SettingsConstantsDef getInstance() { return null; }

    public enum CustomerId {
        B17, CHANGAN_A301, CHANGAN_B211, CHANGAN_B311, CHANGAN_B511, CHANGAN_C211, CHANGAN_C211EV,
        CHANGAN_C301, CHANGAN_C301CTC, CHANGAN_JD301, CHANGAN_P201_CA6595, CHANGAN_P201_CA6596,
        CHANGAN_P201_CA6596_CARPLAY, CHANGAN_R103_H15T, CHANGAN_S111, CHANGAN_S201, CHANGAN_S301,
        CHANGAN_S302, CHANGAN_S311, CHANGAN_S401, CHANGAN_V301_S, CHANGAN_V302, GEELY_CV, GEELY_SV, S401;

        public static boolean isLikeB211(CustomerId id) { return false; }
        public static boolean isLikeC211(CustomerId id) { return false; }
        public static boolean isLikeC211EV(CustomerId id) { return false; }
        public static boolean isLikeC301(CustomerId id) { return false; }
        public static boolean isLikeS302(CustomerId id) { return false; }
        public static boolean isLikeB311(CustomerId id) { return false; }
        public static boolean isLikeCAS201(CustomerId id) { return false; }
        public static boolean isLikeS401(CustomerId id) { return false; }
        public static boolean isHaveTboxConfig() { return false; }
        public static boolean isHave4GConfig() { return false; }
        public static Object getACConfigs() { return null; }
    }
}
