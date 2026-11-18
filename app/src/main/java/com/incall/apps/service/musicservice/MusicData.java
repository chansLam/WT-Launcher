package com.incall.apps.service.musicservice;

/* loaded from: classes.dex */
public class MusicData implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.incall.apps.service.musicservice.MusicData> CREATOR = new android.os.Parcelable.Creator<com.incall.apps.service.musicservice.MusicData>() { // from class: com.incall.apps.service.musicservice.MusicData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.incall.apps.service.musicservice.MusicData createFromParcel(android.os.Parcel source) {
            return new com.incall.apps.service.musicservice.MusicData(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.incall.apps.service.musicservice.MusicData[] newArray(int size) {
            return new com.incall.apps.service.musicservice.MusicData[size];
        }
    };

    @com.google.gson.annotations.SerializedName("mode")
    private int mode;

    @com.google.gson.annotations.SerializedName("musicName")
    private java.lang.String musicName;

    @com.google.gson.annotations.SerializedName("musicType")
    private int musicType;

    @com.google.gson.annotations.SerializedName("path")
    private java.lang.String path;

    @com.google.gson.annotations.SerializedName("playState")
    private int playState;

    @com.google.gson.annotations.SerializedName(android.support.v4.app.NotificationCompat.CATEGORY_PROGRESS)
    private int progress;

    public boolean equals(java.lang.Object obj) {
        return super.equals(obj);
    }

    public int hashCode() {
        return super.hashCode();
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
        dest.writeInt(this.progress);
        dest.writeInt(this.mode);
        dest.writeInt(this.playState);
    }

    public MusicData() {
    }

    protected MusicData(android.os.Parcel in) {
        this.musicName = in.readString();
        this.path = in.readString();
        this.musicType = in.readInt();
        this.progress = in.readInt();
        this.mode = in.readInt();
        this.playState = in.readInt();
    }

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

    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getPlayState() {
        return this.playState;
    }

    public void setPlayState(int playState) {
        this.playState = playState;
    }

    public java.lang.String toString() {
        return "MusicData [musicName=" + this.musicName + ", path=" + this.path + ", musicType=" + this.musicType + ", progress=" + this.progress + ", mode=" + this.mode + ", playState=" + this.playState + "]";
    }
}
