package com.autopai.applist.model.compat;

/* loaded from: classes.dex */
public abstract class UserManagerCompat {
    private static com.autopai.applist.model.compat.UserManagerCompat sInstance;
    private static final java.lang.Object sInstanceLock = new java.lang.Object();

    public abstract void enableAndResetCache();

    public abstract android.graphics.drawable.Drawable getBadgedDrawableForUser(android.graphics.drawable.Drawable drawable, com.autopai.applist.model.compat.UserHandleCompat userHandleCompat);

    public abstract java.lang.CharSequence getBadgedLabelForUser(java.lang.CharSequence charSequence, com.autopai.applist.model.compat.UserHandleCompat userHandleCompat);

    public abstract long getSerialNumberForUser(com.autopai.applist.model.compat.UserHandleCompat userHandleCompat);

    public abstract long getUserCreationTime(com.autopai.applist.model.compat.UserHandleCompat userHandleCompat);

    public abstract com.autopai.applist.model.compat.UserHandleCompat getUserForSerialNumber(long j);

    public abstract java.util.List<com.autopai.applist.model.compat.UserHandleCompat> getUserProfiles();

    protected UserManagerCompat() {
    }

    public static com.autopai.applist.model.compat.UserManagerCompat getInstance(android.content.Context context) {
        com.autopai.applist.model.compat.UserManagerCompat userManagerCompat;
        synchronized (sInstanceLock) {
            if (sInstance == null) {
                if (com.autopai.common.utils.common.Utilities.ATLEAST_LOLLIPOP) {
                    sInstance = new com.autopai.applist.model.compat.UserManagerCompatVL(context.getApplicationContext());
                } else if (com.autopai.common.utils.common.Utilities.ATLEAST_JB_MR1) {
                    sInstance = new com.autopai.applist.model.compat.UserManagerCompatV17(context.getApplicationContext());
                } else {
                    sInstance = new com.autopai.applist.model.compat.UserManagerCompatV16();
                }
            }
            userManagerCompat = sInstance;
        }
        return userManagerCompat;
    }
}
