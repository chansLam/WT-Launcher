package com.autopai.applist.model.compat;

/* loaded from: classes.dex */
public abstract class LauncherActivityInfoCompat {
    public abstract android.content.pm.ApplicationInfo getApplicationInfo();

    public abstract android.graphics.drawable.Drawable getBadgedIcon(int i);

    public abstract android.content.ComponentName getComponentName();

    public abstract long getFirstInstallTime();

    public abstract android.graphics.drawable.Drawable getIcon(int i);

    public abstract java.lang.CharSequence getLabel();

    public abstract com.autopai.applist.model.compat.UserHandleCompat getUser();

    LauncherActivityInfoCompat() {
    }

    public static com.autopai.applist.model.compat.LauncherActivityInfoCompat fromResolveInfo(android.content.pm.ResolveInfo info, android.content.Context context) {
        return new com.autopai.applist.model.compat.LauncherActivityInfoCompatV16(context, info);
    }
}
