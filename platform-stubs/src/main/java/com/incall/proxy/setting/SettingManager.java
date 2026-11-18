package com.incall.proxy.setting;

public class SettingManager {
    public SettingManager() {}
    public static SettingManager getInstance() { return null; }
    public String getCurCarType() { return null; }
    public SettingsInfo getSettingsInfo() { return null; }
    public com.incall.proxy.constant.SettingsConstantsDef.CustomerId getCustomerId() { return null; }
    public void addSettingsChangedListener(SettingChangedListener listener) { }
    public void removeSettingsChangedListener(SettingChangedListener listener) { }
    public boolean isHaveTboxConfig() { return false; }
    public boolean isHave4GConfig() { return false; }
    public boolean isTencentUi() { return false; }

    public interface SettingChangedListener {
        void onSettingsInfoChange(SettingsInfo settingsInfo);
        void onAudioSettingsInfoChange(AudioSettingsInfo audioSettingsInfo);
    }
}
