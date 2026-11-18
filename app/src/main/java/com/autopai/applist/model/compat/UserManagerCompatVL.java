package com.autopai.applist.model.compat;

@android.annotation.TargetApi(21)
/* loaded from: classes.dex */
public class UserManagerCompatVL extends com.autopai.applist.model.compat.UserManagerCompatV17 {
    private static final java.lang.String USER_CREATION_TIME_KEY = "user_creation_time_";
    private final android.content.Context mContext;
    private final android.content.pm.PackageManager mPm;

    UserManagerCompatVL(android.content.Context context) {
        super(context);
        this.mPm = context.getPackageManager();
        this.mContext = context;
    }

    @Override // com.autopai.applist.model.compat.UserManagerCompatV17, com.autopai.applist.model.compat.UserManagerCompatV16, com.autopai.applist.model.compat.UserManagerCompat
    public void enableAndResetCache() {
        synchronized (this) {
            this.mUsers = new com.autopai.common.utils.common.LongArrayMap<>();
            this.mUserToSerialMap = new java.util.HashMap<>();
            java.util.List<android.os.UserHandle> users = this.mUserManager.getUserProfiles();
            if (users != null) {
                for (android.os.UserHandle user : users) {
                    long serial = this.mUserManager.getSerialNumberForUser(user);
                    com.autopai.applist.model.compat.UserHandleCompat userCompat = com.autopai.applist.model.compat.UserHandleCompat.fromUser(user);
                    this.mUsers.put(serial, userCompat);
                    this.mUserToSerialMap.put(userCompat, java.lang.Long.valueOf(serial));
                }
            }
        }
    }

    @Override // com.autopai.applist.model.compat.UserManagerCompatV16, com.autopai.applist.model.compat.UserManagerCompat
    public java.util.List<com.autopai.applist.model.compat.UserHandleCompat> getUserProfiles() {
        synchronized (this) {
            if (this.mUsers != null) {
                return new java.util.ArrayList(this.mUserToSerialMap.keySet());
            }
            java.util.List<android.os.UserHandle> users = this.mUserManager.getUserProfiles();
            if (users == null) {
                return java.util.Collections.emptyList();
            }
            java.util.ArrayList<com.autopai.applist.model.compat.UserHandleCompat> compatUsers = new java.util.ArrayList<>(users.size());
            for (android.os.UserHandle user : users) {
                compatUsers.add(com.autopai.applist.model.compat.UserHandleCompat.fromUser(user));
            }
            return compatUsers;
        }
    }

    @Override // com.autopai.applist.model.compat.UserManagerCompatV16, com.autopai.applist.model.compat.UserManagerCompat
    public android.graphics.drawable.Drawable getBadgedDrawableForUser(android.graphics.drawable.Drawable unbadged, com.autopai.applist.model.compat.UserHandleCompat user) {
        return this.mPm.getUserBadgedIcon(unbadged, user.getUser());
    }

    @Override // com.autopai.applist.model.compat.UserManagerCompatV16, com.autopai.applist.model.compat.UserManagerCompat
    public java.lang.CharSequence getBadgedLabelForUser(java.lang.CharSequence label, com.autopai.applist.model.compat.UserHandleCompat user) {
        return user == null ? label : this.mPm.getUserBadgedLabel(label, user.getUser());
    }

    @Override // com.autopai.applist.model.compat.UserManagerCompatV16, com.autopai.applist.model.compat.UserManagerCompat
    @android.support.annotation.RequiresApi(api = 23)
    public long getUserCreationTime(com.autopai.applist.model.compat.UserHandleCompat user) {
        if (com.autopai.common.utils.common.Utilities.ATLEAST_MARSHMALLOW) {
            return this.mUserManager.getUserCreationTime(user.getUser());
        }
        android.content.SharedPreferences prefs = this.mContext.getSharedPreferences(com.autopai.applist.model.config.AppsConfig.SHARED_PREFERENCES_KEY, 0);
        java.lang.String key = USER_CREATION_TIME_KEY + getSerialNumberForUser(user);
        if (!prefs.contains(key)) {
            prefs.edit().putLong(key, java.lang.System.currentTimeMillis()).apply();
        }
        return prefs.getLong(key, 0L);
    }
}
