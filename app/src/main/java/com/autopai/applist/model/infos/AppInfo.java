package com.autopai.applist.model.infos;

/* loaded from: classes.dex */
public class AppInfo implements java.io.Serializable {
    private static final int DOWNLOADED_FLAG = 1;
    public static final java.lang.String EXTRA_PROFILE = "profile";
    public static final int NO_ID = -1;
    private static final java.lang.String TAG = "AppInfo";
    private static final int UPDATED_SYSTEM_APP_FLAG = 2;
    private static final java.util.regex.Pattern sTrimPattern = java.util.regex.Pattern.compile("^[\\s|\\p{javaSpaceChar}]*(.*)[\\s|\\p{javaSpaceChar}]*$");
    public android.content.ComponentName componentName;
    public long container;
    public java.lang.CharSequence contentDescription;
    private long firstInstallTime;
    private int flags;
    public android.graphics.drawable.Drawable iconDrawable;
    public long id;
    public android.content.Intent intent;
    public int itemType;
    private com.autopai.applist.model.compat.LauncherActivityInfoCompat mInfo;
    public java.lang.String packageName;
    public int rank;
    public boolean requiresDbUpdate;
    public long screenId;
    public java.lang.CharSequence title;
    public com.autopai.applist.model.compat.UserHandleCompat user;
    boolean usingLowResIcon;

    AppInfo() {
        this.id = -1L;
        this.container = -1L;
        this.screenId = -1L;
        this.rank = 0;
        this.requiresDbUpdate = false;
        this.flags = 0;
        this.user = com.autopai.applist.model.compat.UserHandleCompat.myUserHandle();
    }

    public android.content.Intent getIntent() {
        return this.intent;
    }

    protected android.content.Intent getRestoredIntent() {
        return null;
    }

    public AppInfo(android.content.Context context, com.autopai.applist.model.compat.LauncherActivityInfoCompat info, com.autopai.applist.model.compat.UserHandleCompat user) {
        this.id = -1L;
        this.container = -1L;
        this.screenId = -1L;
        this.rank = 0;
        this.requiresDbUpdate = false;
        this.flags = 0;
        if (info.getComponentName().getPackageName().equals("com.tencent.wecarspeech")) {
            java.util.Locale locale = java.util.Locale.getDefault();
            if (locale.getLanguage().equals("en")) {
                this.title = "Voice Assistant";
            } else {
                this.title = "语音设置";
            }
        } else {
            this.title = info.getLabel();
        }
        this.mInfo = info;
        this.componentName = info.getComponentName();
        this.packageName = this.componentName.getPackageName();
        this.container = -1L;
        this.user = user;
        updateIcon(context);
        this.flags = initFlags(info);
        this.firstInstallTime = info.getFirstInstallTime();
        if (!this.packageName.equals("com.tencent.wecarspeech")) {
            this.intent = makeLaunchIntent(context, info, user);
        } else {
            this.intent = makeIntent(context, info, user);
        }
        android.util.Log.i(TAG, "IconCache - AppInfo: --------------\n ActivityInfo.getLabel == " + ((java.lang.Object) this.title) + "\n ActivityInfo.getClassName == " + info.getComponentName().getClassName());
    }

    private static int initFlags(com.autopai.applist.model.compat.LauncherActivityInfoCompat info) {
        int appFlags = info.getApplicationInfo().flags;
        if ((appFlags & 1) != 0) {
            return 0;
        }
        int flags = 0 | 1;
        if ((appFlags & 128) != 0) {
            return flags | 2;
        }
        return flags;
    }

    public AppInfo(android.content.Context context, long id, java.lang.String label, java.lang.String packageName) {
        this.id = -1L;
        this.container = -1L;
        this.screenId = -1L;
        this.rank = 0;
        this.requiresDbUpdate = false;
        this.flags = 0;
        this.id = id;
        this.title = trim(label);
        java.lang.String[] strings = packageName.split(com.incall.apps.common.net.Urls.SERVER_URL);
        this.packageName = strings[0];
        java.lang.String name = com.autopai.applist.manager.AppIconManager.getAppIconResourcesId(packageName, null);
        int resourcesID = com.autopai.common.utils.common.Utilities.getResourcesID(context, name, "drawable");
        if (resourcesID > 0) {
            this.iconDrawable = context.getResources().getDrawable(resourcesID);
        } else {
            this.iconDrawable = this.mInfo.getBadgedIcon(240);
        }
    }

    public AppInfo(com.autopai.applist.model.infos.AppInfo info) {
        this.id = -1L;
        this.container = -1L;
        this.screenId = -1L;
        this.rank = 0;
        this.requiresDbUpdate = false;
        this.flags = 0;
        this.id = info.id;
        this.rank = info.rank;
        this.screenId = info.screenId;
        this.itemType = info.itemType;
        this.container = info.container;
        this.contentDescription = info.contentDescription;
        this.componentName = info.componentName;
        this.title = trim(info.title);
        this.intent = new android.content.Intent(info.intent);
        this.flags = info.flags;
        this.firstInstallTime = info.firstInstallTime;
        this.iconDrawable = info.iconDrawable;
    }

    public static java.lang.String trim(java.lang.CharSequence s) {
        if (s == null) {
            return null;
        }
        java.util.regex.Matcher m = sTrimPattern.matcher(s);
        return m.replaceAll("$1");
    }

    public java.lang.String toString() {
        return "ApplicationInfo(title=" + ((java.lang.Object) this.title) + " id=" + this.id + " intent=" + this.intent + " type=" + this.itemType + " container=" + this.container + " screen=" + this.screenId + " +  flags=" + this.flags + " firstInstallTime=" + this.firstInstallTime + ")";
    }

    public static void dumpApplicationInfoList(java.lang.String tag, java.lang.String label, java.util.ArrayList<com.autopai.applist.model.infos.AppInfo> list) {
        android.util.Log.d(tag, label + " size=" + list.size());
        java.util.Iterator<com.autopai.applist.model.infos.AppInfo> it = list.iterator();
        while (it.hasNext()) {
            com.autopai.applist.model.infos.AppInfo info = it.next();
            android.util.Log.d(tag, "   title=\"" + ((java.lang.Object) info.title) + "\" iconBitmap=" + info.iconDrawable + " firstInstallTime=" + info.firstInstallTime + " componentName=" + info.componentName.getPackageName());
        }
    }

    private static android.content.Intent makeLaunchIntent(android.content.Context context, com.autopai.applist.model.compat.LauncherActivityInfoCompat info, com.autopai.applist.model.compat.UserHandleCompat user) {
        long serialNumber = com.autopai.applist.model.compat.UserManagerCompat.getInstance(context).getSerialNumberForUser(user);
        return new android.content.Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setComponent(info.getComponentName()).setFlags(270532608).putExtra(EXTRA_PROFILE, serialNumber);
    }

    private static android.content.Intent makeIntent(android.content.Context context, com.autopai.applist.model.compat.LauncherActivityInfoCompat info, com.autopai.applist.model.compat.UserHandleCompat user) {
        long serialNumber = com.autopai.applist.model.compat.UserManagerCompat.getInstance(context).getSerialNumberForUser(user);
        android.content.Intent intent = new android.content.Intent().setComponent(info.getComponentName()).setFlags(270532608).putExtra(EXTRA_PROFILE, serialNumber);
        intent.putExtra(com.wt.statistics.persistentdata.ExceptionBean.ExceptionInfo.TYPE, "SETTING");
        return intent;
    }

    public void updateIcon(android.content.Context context) {
        if (this.mInfo != null) {
            java.lang.String name = com.autopai.applist.manager.AppIconManager.getAppIconResourcesId(this.mInfo.getComponentName().getPackageName(), this.mInfo.getComponentName().getClassName());
            int id = com.autopai.common.utils.common.Utilities.getResourcesID(context, name, "drawable");
            android.util.Log.e(TAG, "getAppIconResourcesId: " + id);
            if (id > 0) {
                this.iconDrawable = context.getResources().getDrawable(id);
                return;
            } else {
                this.iconDrawable = this.mInfo.getBadgedIcon(240);
                return;
            }
        }
        java.lang.String name2 = com.autopai.applist.manager.AppIconManager.getAppIconResourcesId(this.packageName, null);
        this.iconDrawable = context.getResources().getDrawable(com.autopai.common.utils.common.Utilities.getResourcesID(context, name2, "drawable"));
    }
}
