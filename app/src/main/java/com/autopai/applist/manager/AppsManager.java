package com.autopai.applist.manager;

/* loaded from: classes.dex */
public class AppsManager implements com.autopai.applist.model.compat.LauncherAppsCompat.OnAppsChangedCallbackCompat {
    private static final boolean DEBUG_LOADERS = true;
    private static final java.lang.String TAG = "AppsManager";
    private static com.autopai.applist.model.infos.AllAppsList mAllAppsList;
    private com.autopai.applist.callback.AppsManagerCallback mCallback;
    private android.content.Context mContext;
    private com.autopai.applist.model.compat.LauncherAppsCompat mLauncherApps;
    private com.autopai.applist.manager.AppsManager.LocaleBroadcastReceiver mLocaleReceiver;
    private final com.autopai.applist.model.compat.UserManagerCompat mUserManager;
    private final java.lang.Object sBgLock = new java.lang.Object();
    private android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper());
    private com.incall.apps.service.setting.IOnThemeChange mThemeChangeListener = new com.incall.apps.service.setting.IOnThemeChange.Stub() { // from class: com.autopai.applist.manager.AppsManager.3
        @Override // com.incall.apps.service.setting.IOnThemeChange
        public void onThemeChange(int theme) throws android.os.RemoteException {
            android.util.Log.e(com.autopai.applist.manager.AppsManager.TAG, "onThemeChange: " + theme);
            com.autopai.applist.manager.AppsManager.this.updateTheme(theme);
        }
    };

    public AppsManager(android.content.Context context) {
        if (mAllAppsList == null) {
            mAllAppsList = new com.autopai.applist.model.infos.AllAppsList();
        }
        this.mContext = context;
        this.mLauncherApps = com.autopai.applist.model.compat.LauncherAppsCompat.getInstance(context);
        this.mUserManager = com.autopai.applist.model.compat.UserManagerCompat.getInstance(context);
        android.content.IntentFilter mIntentFilter = new android.content.IntentFilter();
        mIntentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        this.mLocaleReceiver = new com.autopai.applist.manager.AppsManager.LocaleBroadcastReceiver();
        context.registerReceiver(this.mLocaleReceiver, mIntentFilter);
        this.mLauncherApps.addOnAppsChangedCallback(this);
        loadAndBindAllAppsList(context);
        com.incall.apps.service.setting.SettingServiceManager.getInstance().registerThemeChangeListener(this.mThemeChangeListener);
    }

    public void setCallback(com.autopai.applist.callback.AppsManagerCallback callback) {
        this.mCallback = callback;
    }

    @Override // com.autopai.applist.model.compat.LauncherAppsCompat.OnAppsChangedCallbackCompat
    public void onPackageRemoved(java.lang.String packageName, com.autopai.applist.model.compat.UserHandleCompat user) {
        enqueuePackageUpdated(new com.autopai.applist.manager.AppsManager.PackageUpdatedTask(3, new java.lang.String[]{packageName}, user));
    }

    @Override // com.autopai.applist.model.compat.LauncherAppsCompat.OnAppsChangedCallbackCompat
    public void onPackageAdded(java.lang.String packageName, com.autopai.applist.model.compat.UserHandleCompat user) {
        enqueuePackageUpdated(new com.autopai.applist.manager.AppsManager.PackageUpdatedTask(1, new java.lang.String[]{packageName}, user));
    }

    @Override // com.autopai.applist.model.compat.LauncherAppsCompat.OnAppsChangedCallbackCompat
    public void onPackageChanged(java.lang.String packageName, com.autopai.applist.model.compat.UserHandleCompat user) {
        enqueuePackageUpdated(new com.autopai.applist.manager.AppsManager.PackageUpdatedTask(2, new java.lang.String[]{packageName}, user));
    }

    @Override // com.autopai.applist.model.compat.LauncherAppsCompat.OnAppsChangedCallbackCompat
    public void onPackagesAvailable(java.lang.String[] packageNames, com.autopai.applist.model.compat.UserHandleCompat user, boolean replacing) {
        if (!replacing) {
            enqueuePackageUpdated(new com.autopai.applist.manager.AppsManager.PackageUpdatedTask(1, packageNames, user));
        } else {
            enqueuePackageUpdated(new com.autopai.applist.manager.AppsManager.PackageUpdatedTask(2, packageNames, user));
        }
    }

    @Override // com.autopai.applist.model.compat.LauncherAppsCompat.OnAppsChangedCallbackCompat
    public void onPackagesUnavailable(java.lang.String[] packageNames, com.autopai.applist.model.compat.UserHandleCompat user, boolean replacing) {
        if (!replacing) {
            enqueuePackageUpdated(new com.autopai.applist.manager.AppsManager.PackageUpdatedTask(4, packageNames, user));
        }
    }

    private void enqueuePackageUpdated(com.autopai.applist.manager.AppsManager.PackageUpdatedTask task) {
        com.autopai.common.utils.thread.HandlerThreadUtil.runOnWorkerThread(task);
    }

    private class PackageUpdatedTask implements java.lang.Runnable {
        static final int OP_ADD = 1;
        public static final int OP_NONE = 0;
        static final int OP_REMOVE = 3;
        static final int OP_UNAVAILABLE = 4;
        static final int OP_UPDATE = 2;
        int mOp;
        java.lang.String[] mPackages;
        com.autopai.applist.model.compat.UserHandleCompat mUser;

        PackageUpdatedTask(int op, java.lang.String[] packages, com.autopai.applist.model.compat.UserHandleCompat user) {
            this.mOp = op;
            this.mPackages = packages;
            this.mUser = user;
        }

        @Override // java.lang.Runnable
        public void run() {
            int i = 0;
            android.content.Context context = com.autopai.applist.manager.AppsManager.this.mContext;
            java.lang.String[] packages = this.mPackages;
            switch (this.mOp) {
                case 1:
                    int length = packages.length;
                    while (i < length) {
                        java.lang.String aPackage = packages[i];
                        android.util.Log.d(com.autopai.applist.manager.AppsManager.TAG, "mAllAppsList.addPackage " + aPackage);
                        com.autopai.applist.manager.AppsManager.mAllAppsList.addPackage(context, aPackage, this.mUser);
                        i++;
                    }
                    break;
                case 2:
                    int length2 = packages.length;
                    while (i < length2) {
                        java.lang.String aPackage2 = packages[i];
                        android.util.Log.d(com.autopai.applist.manager.AppsManager.TAG, "mAllAppsList.updatePackage " + aPackage2);
                        if (com.autopai.applist.manager.AppsManager.this.isPackageDisabled(context, aPackage2, this.mUser)) {
                            android.util.Log.i(com.autopai.applist.manager.AppsManager.TAG, "OP_UPDATE --removePackage");
                            com.autopai.applist.manager.AppsManager.mAllAppsList.removePackage(aPackage2, this.mUser);
                        } else {
                            android.util.Log.i(com.autopai.applist.manager.AppsManager.TAG, "OP_UPDATE --updatePackage");
                            com.autopai.applist.manager.AppsManager.mAllAppsList.updatePackage(context, aPackage2, this.mUser);
                        }
                        i++;
                    }
                    break;
                case 3:
                case 4:
                    int length3 = packages.length;
                    while (i < length3) {
                        java.lang.String aPackage3 = packages[i];
                        android.util.Log.d(com.autopai.applist.manager.AppsManager.TAG, "mAllAppsList.removePackage " + aPackage3);
                        com.autopai.applist.manager.AppsManager.mAllAppsList.removePackage(aPackage3, this.mUser);
                        i++;
                    }
                    break;
            }
            com.autopai.applist.manager.AppsManager.this.mHandler.post(new java.lang.Runnable() { // from class: com.autopai.applist.manager.AppsManager.PackageUpdatedTask.1
                @Override // java.lang.Runnable
                public void run() {
                    if (com.autopai.applist.manager.AppsManager.this.mCallback != null) {
                        com.autopai.applist.manager.AppsManager.this.mCallback.onAppDataChange(com.autopai.applist.manager.AppsManager.mAllAppsList.data);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPackageDisabled(android.content.Context context, java.lang.String packageName, com.autopai.applist.model.compat.UserHandleCompat user) {
        com.autopai.applist.model.compat.LauncherAppsCompat launcherApps = com.autopai.applist.model.compat.LauncherAppsCompat.getInstance(context);
        return !launcherApps.isPackageEnabledForProfile(packageName, user);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadCustomIcon(java.util.List<com.autopai.applist.model.compat.LauncherActivityInfoCompat> apps, com.autopai.applist.model.compat.UserHandleCompat user) {
        android.content.Intent intent = new android.content.Intent();
        intent.setComponent(new android.content.ComponentName("com.tencent.wecarspeech", com.autopai.applist.model.config.AppsConfig.APPS_TENCENT_WECARSPEECH_MORE_CLASSNAME));
        com.autopai.applist.model.compat.LauncherActivityInfoCompat infoCompats = this.mLauncherApps.resolveActivity(intent, user);
        if (infoCompats != null) {
            apps.add(infoCompats);
            android.util.Log.i(TAG, "loadCustomIcon: " + infoCompats.getComponentName().getClassName());
        } else {
            android.util.Log.i(TAG, "loadCustomIcon is null ");
        }
    }

    private void loadAndBindAllAppsList(final android.content.Context context) {
        android.util.Log.d("hurley", "loadAndBindAllAppsList time-end = " + java.lang.System.currentTimeMillis());
        java.lang.Runnable runnable = new java.lang.Runnable() { // from class: com.autopai.applist.manager.AppsManager.1
            @Override // java.lang.Runnable
            public void run() {
                java.util.List<com.autopai.applist.model.compat.UserHandleCompat> profiles = com.autopai.applist.manager.AppsManager.this.mUserManager.getUserProfiles();
                android.util.Log.d(com.autopai.applist.manager.AppsManager.TAG, "loadAndBindAllAppsList mUserManager.getUserProfiles()=" + profiles.size());
                com.autopai.applist.manager.AppsManager.mAllAppsList.clear();
                for (com.autopai.applist.model.compat.UserHandleCompat user : profiles) {
                    long qiaTime = android.os.SystemClock.uptimeMillis();
                    java.util.List<com.autopai.applist.model.compat.LauncherActivityInfoCompat> apps = com.autopai.applist.manager.AppsManager.this.mLauncherApps.getActivityList(null, user);
                    android.util.Log.d(com.autopai.applist.manager.AppsManager.TAG, "getActivityList took " + (android.os.SystemClock.uptimeMillis() - qiaTime) + "ms for user " + user);
                    android.util.Log.d(com.autopai.applist.manager.AppsManager.TAG, "getActivityList got " + apps.size() + " apps for user " + user);
                    if (apps != null && !apps.isEmpty()) {
                        com.autopai.applist.manager.AppsManager.this.loadCustomIcon(apps, user);
                        for (int i = 0; i < apps.size(); i++) {
                            com.autopai.applist.model.compat.LauncherActivityInfoCompat app = apps.get(i);
                            com.autopai.applist.model.infos.AppInfo appInfo = new com.autopai.applist.model.infos.AppInfo(context, app, user);
                            if (com.autopai.applist.model.config.AppsConfig.FILTER_APP_LIST) {
                                if ((!com.autopai.applist.model.config.AppsConfig.mDisableCarTypeList.contains(com.autopai.applist.model.config.AppsConfig.mCarType) || !com.autopai.applist.model.config.AppsConfig.mDisableAppsList.contains(app.getComponentName().getPackageName())) && (com.autopai.applist.model.config.AppsConfig.favoritesAppList.contains(app.getComponentName().getPackageName()) || com.autopai.applist.model.config.AppsConfig.favoritesAppList.contains(app.getComponentName().getPackageName() + com.incall.apps.common.net.Urls.SERVER_URL + app.getComponentName().getClassName()))) {
                                    appInfo.id = com.autopai.applist.model.config.AppsConfig.favoritesAppList.indexOf(app.getComponentName().getPackageName());
                                    android.util.Log.i(com.autopai.applist.manager.AppsManager.TAG, "loadAndBindAllAppsList: appLabel --- " + ((java.lang.Object) app.getLabel()) + ", packageName: " + app.getComponentName().getPackageName() + ", className: " + app.getComponentName().getShortClassName());
                                    if (appInfo.id == -1) {
                                        appInfo.id = com.autopai.applist.model.config.AppsConfig.favoritesAppList.indexOf(app.getComponentName().getPackageName() + com.incall.apps.common.net.Urls.SERVER_URL + app.getComponentName().getClassName());
                                    }
                                    if (!com.autopai.applist.manager.AppsManager.mAllAppsList.mListAppIds.contains(java.lang.Long.valueOf(appInfo.id))) {
                                        com.autopai.applist.manager.AppsManager.mAllAppsList.mListAppIds.add(java.lang.Long.valueOf(appInfo.id));
                                        com.autopai.applist.manager.AppsManager.mAllAppsList.add(appInfo);
                                    }
                                    android.util.Log.i(com.autopai.applist.manager.AppsManager.TAG, "loadAndBindAllAppsList: appInfo.id== " + appInfo.id);
                                }
                            } else {
                                android.util.Log.i(com.autopai.applist.manager.AppsManager.TAG, "loadAndBindAllAppsList: appLabel --- " + ((java.lang.Object) app.getLabel()) + ", packageName: " + app.getComponentName().getPackageName());
                                com.autopai.applist.manager.AppsManager.mAllAppsList.add(appInfo);
                            }
                        }
                    } else {
                        android.util.Log.e(com.autopai.applist.manager.AppsManager.TAG, "load shortcut Failed if we don't have any apps");
                    }
                }
                android.util.Log.i(com.autopai.applist.manager.AppsManager.TAG, "loadAndBindAllAppsList: mAllAppsList.size() == " + com.autopai.applist.manager.AppsManager.mAllAppsList.size());
                com.autopai.applist.manager.AppsManager.this.verifyApplications();
            }
        };
        com.autopai.common.utils.thread.HandlerThreadUtil.runOnWorkerThread(runnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void verifyApplications() {
        com.autopai.common.utils.thread.HandlerThreadUtil.runOnMainThread(this.mHandler, new java.lang.Runnable() { // from class: com.autopai.applist.manager.AppsManager.2
            @Override // java.lang.Runnable
            public void run() {
                com.autopai.applist.manager.AppsManager.this.setAppListAdapter();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAppListAdapter() {
        if (this.mCallback != null) {
            this.mCallback.setAppListAdapter(mAllAppsList.data);
        }
    }

    private class LocaleBroadcastReceiver extends android.content.BroadcastReceiver {
        private LocaleBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if ("android.intent.action.LOCALE_CHANGED".equals(intent.getAction())) {
                java.lang.Runnable updateR = new java.lang.Runnable() { // from class: com.autopai.applist.manager.AppsManager.LocaleBroadcastReceiver.1
                    @Override // java.lang.Runnable
                    public void run() {
                        com.autopai.applist.manager.AppsManager.this.updateShortcutLable();
                    }
                };
                com.autopai.common.utils.thread.HandlerThreadUtil.runOnWorkerThread(updateR);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTheme(int theme) {
        switch (theme) {
            case 0:
                android.util.Log.e(TAG, "onThemeChange: Theme change fail");
                break;
            case 1:
                android.util.Log.e(TAG, "onThemeChange: Theme Classic");
                com.autopai.applist.model.config.AppsConfig.setCurrentTheme(0);
                break;
            case 2:
                android.util.Log.e(TAG, "onThemeChange: Theme Sport");
                com.autopai.applist.model.config.AppsConfig.setCurrentTheme(1);
                break;
            case 3:
                android.util.Log.e(TAG, "onThemeChange: Theme Simple");
                com.autopai.applist.model.config.AppsConfig.setCurrentTheme(2);
                break;
        }
        this.mCallback.changeTheme();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateShortcutLable() {
        android.util.Log.e(TAG, "updateShortcutLable:  ------->");
        synchronized (this.sBgLock) {
            java.util.Iterator<com.autopai.applist.model.infos.AppInfo> it = mAllAppsList.data.iterator();
            while (it.hasNext()) {
                com.autopai.applist.model.infos.AppInfo info = it.next();
                java.lang.CharSequence lable = null;
                android.content.ComponentName cn = info.componentName;
                java.util.List<com.autopai.applist.model.compat.LauncherActivityInfoCompat> newAppInfo = this.mLauncherApps.getActivityList(cn.getPackageName(), info.user);
                if (newAppInfo != null && !newAppInfo.isEmpty()) {
                    for (int i = 0; i < newAppInfo.size(); i++) {
                        com.autopai.applist.model.compat.LauncherActivityInfoCompat newInfo = newAppInfo.get(i);
                        lable = newInfo.getLabel();
                    }
                    if (lable != null) {
                        info.title = com.autopai.common.utils.common.Utilities.trim(lable);
                    }
                    if (cn.getPackageName().equals("com.tencent.wecarspeech")) {
                        java.util.Locale locale = java.util.Locale.getDefault();
                        if (locale.getLanguage().equals("en")) {
                            info.title = "Voice Assistant";
                        } else {
                            info.title = "语音设置";
                        }
                    }
                }
                android.util.Log.e(TAG, "updateShortcutLable: " + ((java.lang.Object) lable));
            }
            if (this.mCallback != null) {
                this.mCallback.localeChanged();
            }
        }
    }

    public void unInit() {
        com.incall.apps.service.setting.SettingServiceManager.getInstance().unRegisterThemeChangeListener(this.mThemeChangeListener);
        this.mContext.unregisterReceiver(this.mLocaleReceiver);
    }
}
