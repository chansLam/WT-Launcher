package com.incall.proxy.setting;

public class SettingManager {
    public SettingManager() {}
    public static SettingManager getInstance() { return null; }
    public static class SettingChangedListener {
        public SettingChangedListener() {}
        public static SettingChangedListener getInstance() { return null; }
    }
    public String getCurCarType() { return null;}
    public SettingsInfo getSettingsInfo() { return null;}
    public com.incall.proxy.constant.SettingsConstantsDef.CustomerId getCustomerId() { return null;}
    public void addSettingsChangedListener(SettingChangedListener a0) { }
    public void removeSettingsChangedListener(SettingChangedListener a0) { }
    public boolean isHaveTboxConfig() { return false; }
    public boolean isHave4GConfig() { return false; }
    public boolean isTencentUi() { return false; }
    public Object getACConfigs() { return null; }
    public int getAcType() { return 0; }
}
