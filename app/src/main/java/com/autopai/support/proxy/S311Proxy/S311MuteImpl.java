package com.autopai.support.proxy.S311Proxy;

/* loaded from: classes.dex */
public class S311MuteImpl implements com.autopai.support.proxy.Interface.IMuteProxy {
    private static final java.lang.String LOGTAG = "S311MuteProxy";
    private boolean isMute;
    private com.autopai.support.proxy.MuteImplProxy.IMuteChangeListener mChangeListener;
    private com.incall.proxy.setting.SettingManager.SettingChangedListener mSettingChangeListener = new com.incall.proxy.setting.SettingManager.SettingChangedListener() { // from class: com.autopai.support.proxy.S311Proxy.S311MuteImpl.1
        public void onSettingsInfoChange(com.incall.proxy.setting.SettingsInfo settingsInfo) {
            com.autopai.support.utils.SLog.i(com.autopai.support.proxy.S311Proxy.S311MuteImpl.LOGTAG, "onSettingsInfoChange settingsInfo =" + settingsInfo);
            if (settingsInfo != null) {
                com.autopai.support.utils.SLog.i(com.autopai.support.proxy.S311Proxy.S311MuteImpl.LOGTAG, "onSettingsInfoChange isMute=" + settingsInfo.isMute);
                com.autopai.support.proxy.S311Proxy.S311MuteImpl.this.isMute = settingsInfo.isMute;
                if (com.autopai.support.proxy.S311Proxy.S311MuteImpl.this.mChangeListener != null) {
                    com.autopai.support.proxy.S311Proxy.S311MuteImpl.this.mChangeListener.onMuteChanged(com.autopai.support.proxy.S311Proxy.S311MuteImpl.this.isMute);
                }
            }
        }

        public void onAudioSettingsInfoChange(com.incall.proxy.setting.AudioSettingsInfo audioSettingsInfo) {
        }
    };

    public S311MuteImpl() {
        com.incall.proxy.setting.SettingsInfo info = com.incall.proxy.setting.SettingManager.getInstance().getSettingsInfo();
        if (info != null) {
            this.isMute = info.isMute;
        }
        com.incall.proxy.setting.SettingManager.getInstance().addSettingsChangedListener(this.mSettingChangeListener);
    }

    @Override // com.autopai.support.proxy.Interface.IMuteProxy
    public boolean getMuteStatus() {
        return this.isMute;
    }

    @Override // com.autopai.support.proxy.Interface.IMuteProxy
    public void setMuteChangeListener(com.autopai.support.proxy.MuteImplProxy.IMuteChangeListener listener) {
        this.mChangeListener = listener;
    }

    @Override // com.autopai.support.proxy.Interface.IMuteProxy
    public void destroy() {
        com.incall.proxy.setting.SettingManager.getInstance().removeSettingsChangedListener(this.mSettingChangeListener);
    }
}
