package com.incall.apps.service.userservice.beans;

/* loaded from: classes.dex */
public class RadioCollect extends com.incall.apps.service.userservice.beans.BaseBean implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.incall.apps.service.userservice.beans.RadioCollect> CREATOR = new android.os.Parcelable.Creator<com.incall.apps.service.userservice.beans.RadioCollect>() { // from class: com.incall.apps.service.userservice.beans.RadioCollect.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.incall.apps.service.userservice.beans.RadioCollect[] newArray(int size) {
            return new com.incall.apps.service.userservice.beans.RadioCollect[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.incall.apps.service.userservice.beans.RadioCollect createFromParcel(android.os.Parcel in) {
            return new com.incall.apps.service.userservice.beans.RadioCollect(in);
        }
    };
    public java.lang.String albumId;
    public java.lang.String albumName;
    private java.lang.String collect;
    private java.lang.String collectTime;
    private java.lang.String coverUrl;
    private java.lang.String frequency;
    public java.lang.String radioDesc;
    private java.lang.String radioName;
    public java.lang.String trackId;
    public java.lang.String trackName;
    private java.lang.String type;
    private java.lang.String userId;

    public RadioCollect() {
        this.userId = "";
        this.radioName = "";
        this.coverUrl = "";
        this.frequency = "";
        this.type = "";
        this.collectTime = "";
        this.collect = "1";
        this.trackId = "";
        this.trackName = "";
        this.albumId = "";
        this.albumName = "";
        this.radioDesc = "";
    }

    public java.lang.String getCoverUrl() {
        return this.coverUrl;
    }

    public void setCoverUrl(java.lang.String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public RadioCollect(android.os.Parcel in) {
        this.userId = "";
        this.radioName = "";
        this.coverUrl = "";
        this.frequency = "";
        this.type = "";
        this.collectTime = "";
        this.collect = "1";
        this.trackId = "";
        this.trackName = "";
        this.albumId = "";
        this.albumName = "";
        this.radioDesc = "";
        this.userId = in.readString();
        this.radioName = in.readString();
        this.trackId = in.readString();
        this.trackName = in.readString();
        this.albumId = in.readString();
        this.albumName = in.readString();
        this.radioDesc = in.readString();
        this.coverUrl = in.readString();
        this.frequency = in.readString();
        this.type = in.readString();
        this.collectTime = in.readString();
        this.collect = in.readString();
    }

    public java.lang.String getUserId() {
        return this.userId;
    }

    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }

    public java.lang.String getRadioName() {
        return this.radioName;
    }

    public void setRadioName(java.lang.String radioName) {
        this.radioName = radioName;
    }

    public java.lang.String getFrequency() {
        return this.frequency;
    }

    public void setFrequency(java.lang.String frequency) {
        this.frequency = frequency;
    }

    public java.lang.String getType() {
        return this.type;
    }

    public void setType(java.lang.String type) {
        this.type = type;
    }

    public java.lang.String getCollectTime() {
        return this.collectTime;
    }

    public void setCollectTime(java.lang.String collectTime) {
        this.collectTime = collectTime;
    }

    public java.lang.String getCollect() {
        return this.collect;
    }

    public void setCollect(java.lang.String collect) {
        this.collect = collect;
    }

    public java.lang.String getTrackId() {
        return this.trackId;
    }

    public void setTrackId(java.lang.String trackId) {
        this.trackId = trackId;
    }

    public java.lang.String getTrackName() {
        return this.trackName;
    }

    public void setTrackName(java.lang.String trackName) {
        this.trackName = trackName;
    }

    public java.lang.String getAlbumId() {
        return this.albumId;
    }

    public void setAlbumId(java.lang.String albumId) {
        this.albumId = albumId;
    }

    public java.lang.String getAlbumName() {
        return this.albumName;
    }

    public void setAlbumName(java.lang.String albumName) {
        this.albumName = albumName;
    }

    public java.lang.String getRadioDesc() {
        return this.radioDesc;
    }

    public void setRadioDesc(java.lang.String radioDesc) {
        this.radioDesc = radioDesc;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel dest, int arg1) {
        dest.writeString(this.userId);
        dest.writeString(this.radioName);
        dest.writeString(this.trackId);
        dest.writeString(this.trackName);
        dest.writeString(this.albumId);
        dest.writeString(this.albumName);
        dest.writeString(this.radioDesc);
        dest.writeString(this.coverUrl);
        dest.writeString(this.frequency);
        dest.writeString(this.type);
        dest.writeString(this.collectTime);
        dest.writeString(this.collect);
    }

    @Override // com.incall.apps.service.userservice.beans.BaseBean
    public java.lang.String[] beanValues() {
        return new java.lang.String[]{this.userId, this.radioName, this.trackId, this.trackName, this.albumId, this.albumName, this.radioDesc, this.coverUrl, this.frequency, this.type, this.collectTime, this.collect};
    }

    @Override // com.incall.apps.service.userservice.beans.BaseBean
    public java.lang.String[] queryValue() {
        return null;
    }

    public java.lang.String toString() {
        return "userId = " + this.userId + ",radioName = " + this.radioName + ",coverUrl = " + this.coverUrl + ",frequency = " + this.frequency + ",type = " + this.type + ",collectTime = " + this.collectTime + ",collect= " + this.collect + ",trackId = " + this.trackId + ",trackName= " + this.trackName + ",albumId = " + this.albumId + ",albumName = " + this.albumName + ",radioDesc= " + this.radioDesc;
    }
}
