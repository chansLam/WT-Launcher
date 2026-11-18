package com.autopai.applist.model.compat;

/* loaded from: classes.dex */
public class UserManagerCompatV16 extends com.autopai.applist.model.compat.UserManagerCompat {
    UserManagerCompatV16() {
    }

    @Override // com.autopai.applist.model.compat.UserManagerCompat
    public java.util.List<com.autopai.applist.model.compat.UserHandleCompat> getUserProfiles() {
        java.util.List<com.autopai.applist.model.compat.UserHandleCompat> profiles = new java.util.ArrayList<>(1);
        profiles.add(com.autopai.applist.model.compat.UserHandleCompat.myUserHandle());
        return profiles;
    }

    @Override // com.autopai.applist.model.compat.UserManagerCompat
    public com.autopai.applist.model.compat.UserHandleCompat getUserForSerialNumber(long serialNumber) {
        return com.autopai.applist.model.compat.UserHandleCompat.myUserHandle();
    }

    @Override // com.autopai.applist.model.compat.UserManagerCompat
    public android.graphics.drawable.Drawable getBadgedDrawableForUser(android.graphics.drawable.Drawable unbadged, com.autopai.applist.model.compat.UserHandleCompat user) {
        return unbadged;
    }

    @Override // com.autopai.applist.model.compat.UserManagerCompat
    public long getSerialNumberForUser(com.autopai.applist.model.compat.UserHandleCompat user) {
        return 0L;
    }

    @Override // com.autopai.applist.model.compat.UserManagerCompat
    public java.lang.CharSequence getBadgedLabelForUser(java.lang.CharSequence label, com.autopai.applist.model.compat.UserHandleCompat user) {
        return label;
    }

    @Override // com.autopai.applist.model.compat.UserManagerCompat
    public long getUserCreationTime(com.autopai.applist.model.compat.UserHandleCompat user) {
        return 0L;
    }

    @Override // com.autopai.applist.model.compat.UserManagerCompat
    public void enableAndResetCache() {
    }
}
