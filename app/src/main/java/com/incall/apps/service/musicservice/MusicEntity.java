package com.incall.apps.service.musicservice;

/* loaded from: classes.dex */
public class MusicEntity implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.incall.apps.service.musicservice.MusicEntity> CREATOR = new android.os.Parcelable.Creator<com.incall.apps.service.musicservice.MusicEntity>() { // from class: com.incall.apps.service.musicservice.MusicEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.incall.apps.service.musicservice.MusicEntity createFromParcel(android.os.Parcel source) {
            return new com.incall.apps.service.musicservice.MusicEntity(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.incall.apps.service.musicservice.MusicEntity[] newArray(int size) {
            return new com.incall.apps.service.musicservice.MusicEntity[size];
        }
    };

    @com.google.gson.annotations.SerializedName("musicName")
    private java.lang.String musicName;

    @com.google.gson.annotations.SerializedName("musicType")
    private int musicType;

    @com.google.gson.annotations.SerializedName("path")
    private java.lang.String path;

    public java.lang.String getMusicName() {
        return this.musicName;
    }

    public void setMusicName(java.lang.String musicName) {
        this.musicName = musicName;
    }

    public java.lang.String getPath() {
        return this.path;
    }

    public void setPath(java.lang.String path) {
        this.path = path;
    }

    public int getMusicType() {
        return this.musicType;
    }

    public void setMusicType(int musicType) {
        this.musicType = musicType;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeString(this.musicName);
        dest.writeString(this.path);
        dest.writeInt(this.musicType);
    }

    public MusicEntity() {
    }

    protected MusicEntity(android.os.Parcel in) {
        this.musicName = in.readString();
        this.path = in.readString();
        this.musicType = in.readInt();
    }

    public java.lang.String toString() {
        return "MusicEntity{musicName='" + this.musicName + "', path='" + this.path + "', musicType=" + this.musicType + '}';
    }
}
