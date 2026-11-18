package com.autopai.applist.model.infos;

/* loaded from: classes.dex */
public class AllAppsList {
    private static final int DEFAULT_APPLICATIONS_NUMBER = 42;
    private static final java.lang.String TAG = "AllAppsList";
    public java.util.ArrayList<com.autopai.applist.model.infos.AppInfo> data = new java.util.ArrayList<>(42);
    public java.util.ArrayList<com.autopai.applist.model.infos.AppInfo> modified = new java.util.ArrayList<>();
    public java.util.ArrayList<java.lang.Long> mListAppIds = new java.util.ArrayList<>();

    public void add(com.autopai.applist.model.infos.AppInfo info) {
        if (com.autopai.applist.model.config.AppsConfig.FILTER_APP_LIST || !findActivity(this.data, info.componentName, info.user)) {
            this.data.add(info);
        }
    }

    public void clear() {
        this.data.clear();
        this.modified.clear();
        this.mListAppIds.clear();
    }

    public int size() {
        return this.data.size();
    }

    public com.autopai.applist.model.infos.AppInfo get(int index) {
        return this.data.get(index);
    }

    public void addPackage(android.content.Context context, java.lang.String packageName, com.autopai.applist.model.compat.UserHandleCompat user) {
        android.util.Log.e(TAG, "IconCache -addPackage: packageName " + packageName);
        com.autopai.applist.model.compat.LauncherAppsCompat launcherApps = com.autopai.applist.model.compat.LauncherAppsCompat.getInstance(context);
        java.util.List<com.autopai.applist.model.compat.LauncherActivityInfoCompat> matches = launcherApps.getActivityList(packageName, user);
        android.util.Log.e(TAG, "addPackage: matches.size()== " + matches.size());
        for (com.autopai.applist.model.compat.LauncherActivityInfoCompat info : matches) {
            if (com.autopai.applist.model.config.AppsConfig.FILTER_APP_LIST) {
                for (int i = 0; i < com.autopai.applist.model.config.AppsConfig.favoritesAppList.size(); i++) {
                    if (com.autopai.applist.model.config.AppsConfig.favoritesAppList.get(i).contains(info.getComponentName().getPackageName()) || com.autopai.applist.model.config.AppsConfig.favoritesAppList.contains(info.getComponentName().getPackageName() + com.incall.apps.common.net.Urls.SERVER_URL + info.getComponentName().getShortClassName())) {
                        com.autopai.applist.model.infos.AppInfo appInfo = new com.autopai.applist.model.infos.AppInfo(context, info, user);
                        appInfo.id = com.autopai.applist.model.config.AppsConfig.favoritesAppList.indexOf(info.getComponentName().getPackageName());
                        if (appInfo.id == -1) {
                            appInfo.id = com.autopai.applist.model.config.AppsConfig.favoritesAppList.indexOf(info.getComponentName().getPackageName() + com.incall.apps.common.net.Urls.SERVER_URL + info.getComponentName().getShortClassName());
                        }
                        if (appInfo.id != -1 && !this.mListAppIds.contains(java.lang.Long.valueOf(appInfo.id))) {
                            this.mListAppIds.add(java.lang.Long.valueOf(appInfo.id));
                            add(appInfo);
                        }
                        android.util.Log.e(TAG, "addPackage: appInfo.id== " + appInfo.id + "-- addPackage: appInfo.id== " + ((java.lang.Object) appInfo.title));
                    }
                }
            } else {
                add(new com.autopai.applist.model.infos.AppInfo(context, info, user));
            }
        }
    }

    public void removePackage(java.lang.String packageName, com.autopai.applist.model.compat.UserHandleCompat user) {
        android.util.Log.e(TAG, "removePackage: packageName = " + packageName);
        for (int i = this.data.size() - 1; i >= 0; i--) {
            com.autopai.applist.model.infos.AppInfo info = this.data.get(i);
            android.content.ComponentName component = info.intent.getComponent();
            if (component != null && info.user.equals(user) && packageName.equals(component.getPackageName())) {
                this.data.remove(i);
                int id = com.autopai.applist.model.config.AppsConfig.favoritesAppList.indexOf(packageName);
                if (id == -1) {
                    id = com.autopai.applist.model.config.AppsConfig.favoritesAppList.indexOf(packageName + com.incall.apps.common.net.Urls.SERVER_URL + component.getShortClassName());
                }
                int idIndex = this.mListAppIds.indexOf(java.lang.Long.valueOf(id));
                if (idIndex != -1) {
                    this.mListAppIds.remove(idIndex);
                }
            }
        }
    }

    public void updatePackage(android.content.Context context, java.lang.String packageName, com.autopai.applist.model.compat.UserHandleCompat user) {
        android.util.Log.e(TAG, "updatePackage: packageName == " + packageName);
        com.autopai.applist.model.compat.LauncherAppsCompat launcherApps = com.autopai.applist.model.compat.LauncherAppsCompat.getInstance(context);
        java.util.List<com.autopai.applist.model.compat.LauncherActivityInfoCompat> matches = launcherApps.getActivityList(packageName, user);
        android.util.Log.e(TAG, "updatePackage: matches.size() == " + matches.size());
        if (matches.size() > 0) {
            for (int i = this.data.size() - 1; i >= 0; i--) {
                com.autopai.applist.model.infos.AppInfo applicationInfo = this.data.get(i);
                android.content.ComponentName component = applicationInfo.intent.getComponent();
                if (component != null && user.equals(applicationInfo.user) && packageName.equals(component.getPackageName()) && !findActivity(matches, component)) {
                    this.data.remove(i);
                    int id = com.autopai.applist.model.config.AppsConfig.favoritesAppList.indexOf(packageName);
                    if (id == -1) {
                        id = com.autopai.applist.model.config.AppsConfig.favoritesAppList.indexOf(packageName + com.incall.apps.common.net.Urls.SERVER_URL + component.getShortClassName());
                    }
                    int idIndex = this.mListAppIds.indexOf(java.lang.Long.valueOf(id));
                    if (idIndex != -1) {
                        this.mListAppIds.remove(idIndex);
                    }
                }
            }
            for (com.autopai.applist.model.compat.LauncherActivityInfoCompat info : matches) {
                com.autopai.applist.model.infos.AppInfo applicationInfo2 = findApplicationInfoLocked(info.getComponentName().getPackageName(), user, info.getComponentName().getClassName());
                if (applicationInfo2 == null) {
                    if (com.autopai.applist.model.config.AppsConfig.FILTER_APP_LIST) {
                        for (int i2 = 0; i2 < com.autopai.applist.model.config.AppsConfig.favoritesAppList.size(); i2++) {
                            if (com.autopai.applist.model.config.AppsConfig.favoritesAppList.get(i2).contains(info.getComponentName().getPackageName()) || com.autopai.applist.model.config.AppsConfig.favoritesAppList.contains(info.getComponentName().getPackageName() + com.incall.apps.common.net.Urls.SERVER_URL + info.getComponentName().getShortClassName())) {
                                com.autopai.applist.model.infos.AppInfo appInfo = new com.autopai.applist.model.infos.AppInfo(context, info, user);
                                appInfo.id = com.autopai.applist.model.config.AppsConfig.favoritesAppList.indexOf(info.getComponentName().getPackageName());
                                if (appInfo.id == -1) {
                                    appInfo.id = com.autopai.applist.model.config.AppsConfig.favoritesAppList.indexOf(info.getComponentName().getPackageName() + com.incall.apps.common.net.Urls.SERVER_URL + info.getComponentName().getShortClassName());
                                }
                                if (!this.mListAppIds.contains(java.lang.Long.valueOf(appInfo.id))) {
                                    this.mListAppIds.add(java.lang.Long.valueOf(appInfo.id));
                                    add(appInfo);
                                }
                                android.util.Log.e(TAG, "updatePackage: appInfo.id == " + appInfo.id);
                            }
                        }
                    } else {
                        add(new com.autopai.applist.model.infos.AppInfo(context, info, user));
                    }
                } else {
                    this.modified.add(applicationInfo2);
                }
            }
        } else {
            for (int i3 = this.data.size() - 1; i3 >= 0; i3--) {
                com.autopai.applist.model.infos.AppInfo applicationInfo3 = this.data.get(i3);
                android.content.ComponentName component2 = applicationInfo3.intent.getComponent();
                if (component2 != null && user.equals(applicationInfo3.user) && packageName.equals(component2.getPackageName())) {
                    this.data.remove(i3);
                    int id2 = com.autopai.applist.model.config.AppsConfig.favoritesAppList.indexOf(packageName);
                    if (id2 == -1) {
                        id2 = com.autopai.applist.model.config.AppsConfig.favoritesAppList.indexOf(packageName + com.incall.apps.common.net.Urls.SERVER_URL + component2.getShortClassName());
                    }
                    int idIndex2 = this.mListAppIds.indexOf(java.lang.Long.valueOf(id2));
                    if (idIndex2 != -1) {
                        this.mListAppIds.remove(idIndex2);
                    }
                }
            }
        }
        modifyDataList();
    }

    private void modifyDataList() {
        java.util.Iterator<com.autopai.applist.model.infos.AppInfo> it = this.modified.iterator();
        while (it.hasNext()) {
            com.autopai.applist.model.infos.AppInfo app = it.next();
            int index = this.data.indexOf(app);
            if (index != -1) {
                this.data.remove(index);
                this.data.add(index, app);
            }
        }
        this.modified.clear();
    }

    private static boolean findActivity(java.util.List<com.autopai.applist.model.compat.LauncherActivityInfoCompat> apps, android.content.ComponentName component) {
        for (com.autopai.applist.model.compat.LauncherActivityInfoCompat info : apps) {
            if (info.getComponentName().equals(component)) {
                return true;
            }
        }
        return false;
    }

    static boolean packageHasActivities(android.content.Context context, java.lang.String packageName, com.autopai.applist.model.compat.UserHandleCompat user) {
        com.autopai.applist.model.compat.LauncherAppsCompat launcherApps = com.autopai.applist.model.compat.LauncherAppsCompat.getInstance(context);
        return launcherApps.getActivityList(packageName, user).size() > 0;
    }

    private static boolean findActivity(java.util.ArrayList<com.autopai.applist.model.infos.AppInfo> apps, android.content.ComponentName component, com.autopai.applist.model.compat.UserHandleCompat user) {
        for (int i = 0; i < apps.size(); i++) {
            com.autopai.applist.model.infos.AppInfo info = apps.get(i);
            if (info.user.equals(user) && info.componentName.equals(component)) {
                return true;
            }
        }
        return false;
    }

    private com.autopai.applist.model.infos.AppInfo findApplicationInfoLocked(java.lang.String packageName, com.autopai.applist.model.compat.UserHandleCompat user, java.lang.String className) {
        java.util.Iterator<com.autopai.applist.model.infos.AppInfo> it = this.data.iterator();
        while (it.hasNext()) {
            com.autopai.applist.model.infos.AppInfo info = it.next();
            android.content.ComponentName component = info.intent.getComponent();
            if (component != null && user.equals(info.user) && packageName.equals(component.getPackageName()) && className.equals(component.getClassName())) {
                return info;
            }
        }
        return null;
    }
}
