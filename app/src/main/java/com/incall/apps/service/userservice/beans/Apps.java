package com.incall.apps.service.userservice.beans;

/* loaded from: classes.dex */
public class Apps extends com.incall.apps.service.userservice.beans.BaseBean implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.incall.apps.service.userservice.beans.Apps> CREATOR = new android.os.Parcelable.Creator<com.incall.apps.service.userservice.beans.Apps>() { // from class: com.incall.apps.service.userservice.beans.Apps.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.incall.apps.service.userservice.beans.Apps[] newArray(int size) {
            return new com.incall.apps.service.userservice.beans.Apps[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.incall.apps.service.userservice.beans.Apps createFromParcel(android.os.Parcel in) {
            return new com.incall.apps.service.userservice.beans.Apps(in);
        }
    };
    public java.lang.String gd;
    public java.lang.String kw;
    public java.lang.String qqMusic;
    public java.lang.String tencentMap;
    public java.lang.String tencentRadio;
    public java.lang.String time;
    public java.lang.String userId;
    public java.lang.String xmly;

    public Apps() {
    }

    public java.lang.String getTencentRadio() {
        return this.tencentRadio;
    }

    public void setTencentRadio(java.lang.String tencentRadio) {
        this.tencentRadio = tencentRadio;
    }

    public java.lang.String getTencentMap() {
        return this.tencentMap;
    }

    public void setTencentMap(java.lang.String tencentMap) {
        this.tencentMap = tencentMap;
    }

    public java.lang.String getQqMusic() {
        return this.qqMusic;
    }

    public void setQqMusic(java.lang.String qqMusic) {
        this.qqMusic = qqMusic;
    }

    @Override // com.incall.apps.service.userservice.beans.BaseBean
    public java.lang.String[] beanValues() {
        return new java.lang.String[]{this.userId, this.xmly, this.kw, this.gd, this.tencentRadio, this.tencentMap, this.qqMusic, this.time};
    }

    @Override // com.incall.apps.service.userservice.beans.BaseBean
    public java.lang.String[] queryValue() {
        return new java.lang.String[]{this.userId};
    }

    public java.lang.String getUser() {
        return this.userId;
    }

    public void setUser(java.lang.String user) {
        this.userId = user;
    }

    public java.lang.String getXmly() {
        return this.xmly;
    }

    public void setXmly(java.lang.String xmly) {
        this.xmly = xmly;
    }

    public java.lang.String getKw() {
        return this.kw;
    }

    public void setKw(java.lang.String kw) {
        this.kw = kw;
    }

    public java.lang.String getGd() {
        return this.gd;
    }

    public void setGd(java.lang.String gd) {
        this.gd = gd;
    }

    public java.lang.String getTime() {
        return this.time;
    }

    public void setTime(java.lang.String time) {
        this.time = time;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected Apps(android.os.Parcel in) {
        this.userId = in.readString();
        this.xmly = in.readString();
        this.kw = in.readString();
        this.gd = in.readString();
        this.tencentRadio = in.readString();
        this.tencentMap = in.readString();
        this.qqMusic = in.readString();
        this.time = in.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel dest, int arg1) {
        dest.writeString(this.userId);
        dest.writeString(this.xmly);
        dest.writeString(this.kw);
        dest.writeString(this.gd);
        dest.writeString(this.tencentRadio);
        dest.writeString(this.tencentMap);
        dest.writeString(this.qqMusic);
        dest.writeString(this.time);
    }

    public java.lang.String toString() {
        return "userId=" + this.userId + ",xmly=" + this.xmly + ",kw=" + this.kw + ",gd=" + this.gd + ",tencentRadio=" + this.tencentRadio + ",tencentMap=" + this.tencentMap + ",qqMusic=" + this.qqMusic + ",time=" + this.time;
    }
}
