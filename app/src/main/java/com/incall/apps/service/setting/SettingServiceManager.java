package com.incall.apps.service.setting;

/* loaded from: classes.dex */
public class SettingServiceManager {
    private static final java.lang.String TAG = "SettingServiceManager";
    private static com.incall.apps.service.setting.SettingServiceManager mSettingServiceManager;
    private com.incall.apps.service.setting.ICommonViewShow mCommonViewShow;
    private com.incall.apps.service.setting.ISettingService mISettingService;
    private java.lang.Object lock = new java.lang.Object();
    private java.util.concurrent.CopyOnWriteArrayList<com.incall.apps.service.setting.IOnThemeChange> mOnThemeChangeList = new java.util.concurrent.CopyOnWriteArrayList<>();
    private java.lang.Thread mMotion = new java.lang.Thread() { // from class: com.incall.apps.service.setting.SettingServiceManager.1
        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (true) {
                try {
                    if (com.incall.apps.service.setting.SettingServiceManager.this.mISettingService == null) {
                        com.incall.apps.service.util.LogUtil.d(com.incall.apps.service.setting.SettingServiceManager.TAG, "run init getService");
                        com.incall.apps.service.setting.SettingServiceManager.this.init();
                    }
                    if (com.incall.apps.service.setting.SettingServiceManager.this.mISettingService == null) {
                        sleep(1000L);
                    } else {
                        com.incall.apps.service.setting.SettingServiceManager.this.checkService();
                        synchronized (com.incall.apps.service.setting.SettingServiceManager.this.lock) {
                            com.incall.apps.service.setting.SettingServiceManager.this.lock.wait();
                        }
                    }
                } catch (java.lang.InterruptedException e) {
                    com.incall.apps.service.util.LogUtil.e(com.incall.apps.service.setting.SettingServiceManager.TAG, "mMotion sleep InterruptedException = ", e.getMessage());
                    return;
                }
            }
        }
    };

    public static com.incall.apps.service.setting.SettingServiceManager getInstance() {
        if (mSettingServiceManager == null) {
            synchronized (com.incall.apps.service.setting.SettingServiceManager.class) {
                if (mSettingServiceManager == null) {
                    mSettingServiceManager = new com.incall.apps.service.setting.SettingServiceManager();
                }
            }
        }
        return mSettingServiceManager;
    }

    private SettingServiceManager() {
        init();
        this.mMotion.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void init() {
        android.os.IBinder settingService = android.os.ServiceManager.getService("incall.setting.service");
        if (settingService != null) {
            if (this.mISettingService == null) {
                this.mISettingService = com.incall.apps.service.setting.ISettingService.Stub.asInterface(settingService);
                return;
            }
            return;
        }
        com.incall.apps.service.util.LogUtil.d(TAG, " settingService = null");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkService() {
        com.incall.apps.service.util.LogUtil.i(TAG, "checkService success");
        try {
            this.mISettingService.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.incall.apps.service.setting.SettingServiceManager.2
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    com.incall.apps.service.util.LogUtil.e(com.incall.apps.service.setting.SettingServiceManager.TAG, "checkService mCallService binderDied");
                    com.incall.apps.service.setting.SettingServiceManager.this.mISettingService = null;
                    synchronized (com.incall.apps.service.setting.SettingServiceManager.this.lock) {
                        com.incall.apps.service.setting.SettingServiceManager.this.lock.notifyAll();
                    }
                }
            }, 0);
            com.incall.apps.service.util.LogUtil.i(TAG, "checkService registerThemeChangeListener size = " + this.mOnThemeChangeList.size());
            java.util.Iterator<com.incall.apps.service.setting.IOnThemeChange> it = this.mOnThemeChangeList.iterator();
            while (it.hasNext()) {
                com.incall.apps.service.setting.IOnThemeChange listener = it.next();
                com.incall.apps.service.util.LogUtil.i(TAG, "checkService registerThemeChangeListener");
                register(listener);
            }
            if (this.mCommonViewShow != null) {
                setOnCommonViewShowListener(this.mCommonViewShow);
            }
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(TAG, "checkService RemoteException = ", e.getMessage());
        }
    }

    public void registerThemeChangeListener(com.incall.apps.service.setting.IOnThemeChange onThemeChange) {
        this.mOnThemeChangeList.add(onThemeChange);
        register(onThemeChange);
    }

    private void register(com.incall.apps.service.setting.IOnThemeChange onThemeChange) {
        if (this.mISettingService == null) {
            com.incall.apps.service.util.LogUtil.e(TAG, "mSettingService registerThemeChangeListener null");
            return;
        }
        try {
            com.incall.apps.service.util.LogUtil.d(TAG, "mSettingService registerThemeChangeListener");
            this.mISettingService.registerThemeChangeListener(onThemeChange);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    public void unRegisterThemeChangeListener(com.incall.apps.service.setting.IOnThemeChange onThemeChange) {
        if (this.mOnThemeChangeList.contains(onThemeChange)) {
            this.mOnThemeChangeList.remove(onThemeChange);
        }
        unregister(onThemeChange);
    }

    private void unregister(com.incall.apps.service.setting.IOnThemeChange onThemeChange) {
        if (this.mISettingService == null) {
            com.incall.apps.service.util.LogUtil.e(TAG, "mSettingService unRegisterThemeChangeListener null");
            return;
        }
        try {
            com.incall.apps.service.util.LogUtil.d(TAG, "mSettingService unRegisterThemeChangeListener");
            this.mISettingService.unRegisterThemeChangeListener(onThemeChange);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    public boolean setTheme(int theme) {
        if (this.mISettingService == null) {
            com.incall.apps.service.util.LogUtil.e(TAG, "mSettingService setTheme null");
            return false;
        }
        try {
            com.incall.apps.service.util.LogUtil.d(TAG, "mSettingService setTheme=" + theme);
            return this.mISettingService.setTheme(theme);
        } catch (java.lang.Exception e) {
            com.incall.apps.service.util.LogUtil.e(TAG, "mSettingService setTheme Exception =" + e.toString());
            e.printStackTrace();
            return false;
        }
    }

    public int getTheme() {
        if (this.mISettingService == null) {
            com.incall.apps.service.util.LogUtil.e(TAG, "mSettingService getTheme null");
        } else {
            try {
                com.incall.apps.service.util.LogUtil.d(TAG, "mSettingService getTheme");
                return this.mISettingService.getTheme();
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
        com.incall.apps.service.util.LogUtil.e(TAG, "mSettingService getTheme error");
        return 0;
    }

    public int getTheme(android.content.Context context) {
        int theme = android.provider.Settings.System.getInt(context.getContentResolver(), com.incall.apps.service.setting.ThemeType.KEY_THEME, 1);
        com.incall.apps.service.util.LogUtil.e(TAG, "getTheme theme = " + theme);
        return theme;
    }

    public void isShowTopRightView(boolean isShow) {
        if (this.mISettingService != null) {
            try {
                this.mISettingService.isShowTopRightView(isShow);
                return;
            } catch (android.os.RemoteException e) {
                e.printStackTrace();
                return;
            }
        }
        com.incall.apps.service.util.LogUtil.e(TAG, "mSettingService isShowTopRightView null");
    }

    public void isShowAirCleanView(boolean isShow) {
        if (this.mISettingService != null) {
            try {
                this.mISettingService.isShowAirCleanView(isShow);
                return;
            } catch (android.os.RemoteException e) {
                e.printStackTrace();
                return;
            }
        }
        com.incall.apps.service.util.LogUtil.e(TAG, "mSettingService isShowAirCleanView null");
    }

    public void isShowPlayContentView(boolean isShow) {
        if (this.mISettingService != null) {
            try {
                this.mISettingService.isShowPlayContentView(isShow);
                return;
            } catch (android.os.RemoteException e) {
                e.printStackTrace();
                return;
            }
        }
        com.incall.apps.service.util.LogUtil.e(TAG, "mSettingService isShowPlayContentView null");
    }

    public void setOnCommonViewShowListener(com.incall.apps.service.setting.ICommonViewShow iCommonViewShow) {
        this.mCommonViewShow = iCommonViewShow;
        if (this.mISettingService != null) {
            try {
                this.mISettingService.setOnCommonViewShowListener(iCommonViewShow);
                return;
            } catch (android.os.RemoteException e) {
                e.printStackTrace();
                return;
            }
        }
        com.incall.apps.service.util.LogUtil.e(TAG, "mSettingService addOnCommonViewShowListener null");
    }

    public void setSlideViewVisible(boolean isVisible) {
        if (this.mISettingService != null) {
            try {
                com.incall.apps.service.util.LogUtil.i(TAG, "setSlideViewVisible isVisible = " + isVisible);
                this.mISettingService.setSlideViewVisible(isVisible);
                return;
            } catch (android.os.RemoteException e) {
                e.printStackTrace();
                return;
            }
        }
        com.incall.apps.service.util.LogUtil.e(TAG, "setSlideViewVisible isShowPlayContentView null");
    }

    public boolean isSideViewTop(android.content.Context context) {
        int isTop = android.provider.Settings.System.getInt(context.getContentResolver(), "is_slide_view_top", 0);
        com.incall.apps.service.util.LogUtil.i(TAG, "isSideViewTop isTop = " + isTop);
        return isTop == 1;
    }

    public void addSlideViewVisibleObserver(android.content.Context contex, android.database.ContentObserver observer) {
        contex.getContentResolver().registerContentObserver(android.provider.Settings.System.getUriFor("is_slide_view_top"), true, observer);
    }

    public void removeSlideViewVisibleObserver(android.content.Context contex, android.database.ContentObserver observer) {
        contex.getContentResolver().unregisterContentObserver(observer);
    }

    public void closeHUScreen() {
        com.incall.apps.service.util.LogUtil.i(TAG, "closeHUScreen");
        java.lang.String type = com.incall.apps.service.userservice.PersonlCenterManager.getInstance().getOtherAppInfo(2, com.incall.apps.service.setting.ScreenCloseType.SCREEN_CLOSE_TYPE);
        if (type == null) {
            type = com.incall.apps.service.setting.ScreenCloseType.SCREEN_OFF_BLACK;
        }
        closeHUScreen(type);
    }

    public void closeHUScreen(java.lang.String type) {
        com.incall.apps.service.util.LogUtil.i(TAG, "closeHUScreen =" + type);
        if (this.mISettingService != null) {
            try {
                this.mISettingService.colseHUScreen(type);
                return;
            } catch (android.os.RemoteException e) {
                e.printStackTrace();
                return;
            }
        }
        com.incall.apps.service.util.LogUtil.e(TAG, "mSettingService closeHUScreen null");
    }

    public void openHUScreen() {
        com.incall.apps.service.util.LogUtil.i(TAG, "openHUScreen");
        if (this.mISettingService != null) {
            try {
                this.mISettingService.openHUScreen();
                return;
            } catch (android.os.RemoteException e) {
                e.printStackTrace();
                return;
            }
        }
        com.incall.apps.service.util.LogUtil.e(TAG, "mSettingService closeHUScreen null");
    }

    public void registerSkinChangeListener(com.incall.apps.service.setting.ISkinChanageListener skinChange) {
        com.incall.apps.service.util.LogUtil.d(TAG, "registerSkinChangeListener -- register ");
        registerSkin(skinChange);
    }

    private void registerSkin(com.incall.apps.service.setting.ISkinChanageListener skinChange) {
        if (this.mISettingService == null) {
            com.incall.apps.service.util.LogUtil.e(TAG, "mSettingService registerSkinChangeListener null");
            return;
        }
        try {
            com.incall.apps.service.util.LogUtil.d(TAG, "mSettingService registerSkinChangeListener");
            this.mISettingService.registerSkinChangeListener(skinChange);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            com.incall.apps.service.util.LogUtil.e(TAG, "Exception = " + e.toString());
        }
    }

    public void unregisterSkinListener(com.incall.apps.service.setting.ISkinChanageListener skinChange) {
        unregisterSkin(skinChange);
    }

    private void unregisterSkin(com.incall.apps.service.setting.ISkinChanageListener skinChange) {
        if (this.mISettingService == null) {
            com.incall.apps.service.util.LogUtil.e(TAG, "mSettingService unRegisterSkinChangeListener null");
            return;
        }
        try {
            com.incall.apps.service.util.LogUtil.d(TAG, "mSettingService unRegisterSkinChangeListener");
            this.mISettingService.unRegisterSkinChangeListener(skinChange);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            com.incall.apps.service.util.LogUtil.e(TAG, "Exception = " + e.toString());
        }
    }

    public void setSkinChange(java.lang.String path, java.lang.String actions) {
        com.incall.apps.service.util.LogUtil.i(TAG, "registerSkinChange = path=" + path + ",actions = " + actions);
        if (this.mISettingService != null) {
            try {
                this.mISettingService.dispatchSkinBroadcast(path, actions);
                com.incall.apps.service.util.LogUtil.d(TAG, "listner.onSkinChanage");
                return;
            } catch (android.os.RemoteException e) {
                e.printStackTrace();
                return;
            }
        }
        com.incall.apps.service.util.LogUtil.e(TAG, "mSettingService registerSkinChange null");
    }
}
