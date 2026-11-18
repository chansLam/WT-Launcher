package com.incall.apps.service.userservice.beans;

/* loaded from: classes.dex */
public class Keys extends com.incall.apps.service.userservice.beans.BaseBean implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.incall.apps.service.userservice.beans.Keys> CREATOR = new android.os.Parcelable.Creator<com.incall.apps.service.userservice.beans.Keys>() { // from class: com.incall.apps.service.userservice.beans.Keys.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.incall.apps.service.userservice.beans.Keys[] newArray(int size) {
            return new com.incall.apps.service.userservice.beans.Keys[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.incall.apps.service.userservice.beans.Keys createFromParcel(android.os.Parcel in) {
            return new com.incall.apps.service.userservice.beans.Keys(in);
        }
    };
    private java.lang.String bluetooth;
    private java.lang.String car;
    private java.lang.String face;
    private java.lang.String time;
    private java.lang.String userId;
    private java.lang.String voice;

    public Keys() {
    }

    @Override // com.incall.apps.service.userservice.beans.BaseBean
    public java.lang.String[] beanValues() {
        return new java.lang.String[]{this.userId, this.face, this.voice, this.bluetooth, this.car, this.time};
    }

    @Override // com.incall.apps.service.userservice.beans.BaseBean
    public java.lang.String[] queryValue() {
        return new java.lang.String[]{this.userId};
    }

    public java.lang.String toString() {
        return "user = " + this.userId + ",face = " + this.face + ",voice = " + this.voice + ",bluetooth = " + this.bluetooth + ",car =" + this.car + ",time = " + this.time;
    }

    public java.lang.String getUser() {
        return this.userId;
    }

    public void setUser(java.lang.String user) {
        this.userId = user;
    }

    public java.lang.String getFace() {
        return this.face;
    }

    public void setFace(java.lang.String face) {
        this.face = face;
    }

    public java.lang.String getVoice() {
        return this.voice;
    }

    public void setVoice(java.lang.String voice) {
        this.voice = voice;
    }

    public java.lang.String getBluetooth() {
        return this.bluetooth;
    }

    public void setBluetooth(java.lang.String bluetooth) {
        this.bluetooth = bluetooth;
    }

    public java.lang.String getCar() {
        return this.car;
    }

    public void setCar(java.lang.String car) {
        this.car = car;
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

    protected Keys(android.os.Parcel in) {
        this.userId = in.readString();
        this.face = in.readString();
        this.voice = in.readString();
        this.bluetooth = in.readString();
        this.car = in.readString();
        this.time = in.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel dest, int arg1) {
        dest.writeString(this.userId);
        dest.writeString(this.face);
        dest.writeString(this.voice);
        dest.writeString(this.bluetooth);
        dest.writeString(this.car);
        dest.writeString(this.time);
    }
}
