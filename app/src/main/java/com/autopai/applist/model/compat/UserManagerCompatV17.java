package com.autopai.applist.model.compat;

@android.annotation.TargetApi(17)
/* loaded from: classes.dex */
public class UserManagerCompatV17 extends com.autopai.applist.model.compat.UserManagerCompatV16 {
    protected android.os.UserManager mUserManager;
    java.util.HashMap<com.autopai.applist.model.compat.UserHandleCompat, java.lang.Long> mUserToSerialMap;
    com.autopai.common.utils.common.LongArrayMap<com.autopai.applist.model.compat.UserHandleCompat> mUsers;

    UserManagerCompatV17(android.content.Context context) {
        this.mUserManager = (android.os.UserManager) context.getSystemService("user");
    }

    @Override // com.autopai.applist.model.compat.UserManagerCompatV16, com.autopai.applist.model.compat.UserManagerCompat
    public long getSerialNumberForUser(com.autopai.applist.model.compat.UserHandleCompat user) {
        synchronized (this) {
            if (this.mUserToSerialMap != null) {
                java.lang.Long serial = this.mUserToSerialMap.get(user);
                return serial == null ? 0L : serial.longValue();
            }
            return this.mUserManager.getSerialNumberForUser(user.getUser());
        }
    }

    @Override // com.autopai.applist.model.compat.UserManagerCompatV16, com.autopai.applist.model.compat.UserManagerCompat
    public com.autopai.applist.model.compat.UserHandleCompat getUserForSerialNumber(long serialNumber) {
        synchronized (this) {
            if (this.mUsers != null) {
                return this.mUsers.get(serialNumber);
            }
            return com.autopai.applist.model.compat.UserHandleCompat.fromUser(this.mUserManager.getUserForSerialNumber(serialNumber));
        }
    }

    @Override // com.autopai.applist.model.compat.UserManagerCompatV16, com.autopai.applist.model.compat.UserManagerCompat
    public void enableAndResetCache() {
        synchronized (this) {
            this.mUsers = new com.autopai.common.utils.common.LongArrayMap<>();
            this.mUserToSerialMap = new java.util.HashMap<>();
            com.autopai.applist.model.compat.UserHandleCompat myUser = com.autopai.applist.model.compat.UserHandleCompat.myUserHandle();
            long serial = this.mUserManager.getSerialNumberForUser(myUser.getUser());
            this.mUsers.put(serial, myUser);
            this.mUserToSerialMap.put(myUser, java.lang.Long.valueOf(serial));
        }
    }
}
