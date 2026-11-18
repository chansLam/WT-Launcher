package com.incall.apps.service.userservice.beans;

/* loaded from: classes.dex */
public class UserLogin extends com.incall.apps.service.userservice.beans.BaseBean implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.incall.apps.service.userservice.beans.UserLogin> CREATOR = new android.os.Parcelable.Creator<com.incall.apps.service.userservice.beans.UserLogin>() { // from class: com.incall.apps.service.userservice.beans.UserLogin.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.incall.apps.service.userservice.beans.UserLogin[] newArray(int size) {
            return new com.incall.apps.service.userservice.beans.UserLogin[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.incall.apps.service.userservice.beans.UserLogin createFromParcel(android.os.Parcel in) {
            return new com.incall.apps.service.userservice.beans.UserLogin(in);
        }
    };
    private java.lang.String imgUrl;
    private java.lang.String name;
    private java.lang.String signature;
    private java.lang.String time;
    private java.lang.String userId;
    private java.lang.String userIdentity;
    private java.lang.String userToken;

    public UserLogin() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected UserLogin(android.os.Parcel in) {
        this.userId = in.readString();
        this.name = in.readString();
        this.signature = in.readString();
        this.imgUrl = in.readString();
        this.time = in.readString();
        this.userToken = in.readString();
        this.userIdentity = in.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel dest, int arg1) {
        dest.writeString(this.userId);
        dest.writeString(this.name);
        dest.writeString(this.signature);
        dest.writeString(this.imgUrl);
        dest.writeString(this.time);
        dest.writeString(this.userToken);
        dest.writeString(this.userIdentity);
    }

    @Override // com.incall.apps.service.userservice.beans.BaseBean
    public java.lang.String[] beanValues() {
        return new java.lang.String[]{this.userId, this.userToken, this.userIdentity, this.name, this.signature, this.imgUrl, this.time};
    }

    @Override // com.incall.apps.service.userservice.beans.BaseBean
    public java.lang.String[] queryValue() {
        return new java.lang.String[]{this.userId};
    }

    public java.lang.String toString() {
        return " userId=" + this.userId + ",userToken=" + this.userToken + ",userIdentity=" + this.userIdentity + ", name=" + this.name + ", signature=" + this.signature + ", imgUrl=" + this.imgUrl + ",time = " + this.time;
    }

    public java.lang.String getUserToken() {
        return this.userToken;
    }

    public void setUserToken(java.lang.String userToken) {
        this.userToken = userToken;
    }

    public java.lang.String getUserIdentity() {
        return this.userIdentity;
    }

    public void setUserIdentity(java.lang.String userIdentity) {
        this.userIdentity = userIdentity;
    }

    public java.lang.String getTime() {
        return this.time;
    }

    public void setTime(java.lang.String time) {
        this.time = time;
    }

    public java.lang.String getUserId() {
        return this.userId;
    }

    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }

    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public java.lang.String getSignature() {
        return this.signature;
    }

    public void setSignature(java.lang.String signature) {
        this.signature = signature;
    }

    public java.lang.String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(java.lang.String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
