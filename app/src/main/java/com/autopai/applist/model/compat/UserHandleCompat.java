package com.autopai.applist.model.compat;

/* loaded from: classes.dex */
public class UserHandleCompat {
    private android.os.UserHandle mUser;

    private UserHandleCompat(android.os.UserHandle user) {
        this.mUser = user;
    }

    private UserHandleCompat() {
    }

    @android.annotation.TargetApi(17)
    public static com.autopai.applist.model.compat.UserHandleCompat myUserHandle() {
        return com.autopai.common.utils.common.Utilities.ATLEAST_JB_MR1 ? new com.autopai.applist.model.compat.UserHandleCompat(android.os.Process.myUserHandle()) : new com.autopai.applist.model.compat.UserHandleCompat();
    }

    public static com.autopai.applist.model.compat.UserHandleCompat fromUser(android.os.UserHandle user) {
        if (user == null) {
            return null;
        }
        return new com.autopai.applist.model.compat.UserHandleCompat(user);
    }

    public android.os.UserHandle getUser() {
        return this.mUser;
    }

    public java.lang.String toString() {
        return com.autopai.common.utils.common.Utilities.ATLEAST_JB_MR1 ? this.mUser.toString() : "";
    }

    public boolean equals(java.lang.Object other) {
        return (other instanceof com.autopai.applist.model.compat.UserHandleCompat) && (!com.autopai.common.utils.common.Utilities.ATLEAST_JB_MR1 || this.mUser.equals(((com.autopai.applist.model.compat.UserHandleCompat) other).mUser));
    }

    public int hashCode() {
        if (com.autopai.common.utils.common.Utilities.ATLEAST_JB_MR1) {
            return this.mUser.hashCode();
        }
        return 0;
    }

    public void addToIntent(android.content.Intent intent, java.lang.String name) {
        if (com.autopai.common.utils.common.Utilities.ATLEAST_LOLLIPOP && this.mUser != null) {
            intent.putExtra(name, this.mUser);
        }
    }
}
