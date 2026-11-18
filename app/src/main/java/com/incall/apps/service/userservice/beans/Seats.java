package com.incall.apps.service.userservice.beans;

/* loaded from: classes.dex */
public class Seats extends com.incall.apps.service.userservice.beans.BaseBean implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.incall.apps.service.userservice.beans.Seats> CREATOR = new android.os.Parcelable.Creator<com.incall.apps.service.userservice.beans.Seats>() { // from class: com.incall.apps.service.userservice.beans.Seats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.incall.apps.service.userservice.beans.Seats[] newArray(int size) {
            return new com.incall.apps.service.userservice.beans.Seats[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.incall.apps.service.userservice.beans.Seats createFromParcel(android.os.Parcel in) {
            return new com.incall.apps.service.userservice.beans.Seats(in);
        }
    };
    private java.lang.String date;
    private java.lang.String patternCheck;
    private java.lang.String patternInfoI;
    private java.lang.String patternInfoII;
    private java.lang.String patternNameI;
    private java.lang.String patternNameII;
    private java.lang.String seatId;
    private java.lang.String userId;

    public Seats() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.incall.apps.service.userservice.beans.BaseBean
    public java.lang.String[] beanValues() {
        return new java.lang.String[]{this.userId, this.seatId, this.patternNameI, this.patternInfoI, this.patternNameII, this.patternInfoII, this.patternCheck, this.date};
    }

    @Override // com.incall.apps.service.userservice.beans.BaseBean
    public java.lang.String[] queryValue() {
        return new java.lang.String[]{this.userId};
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel dest, int arg1) {
        dest.writeString(this.userId);
        dest.writeString(this.seatId);
        dest.writeString(this.patternNameI);
        dest.writeString(this.patternInfoI);
        dest.writeString(this.patternNameII);
        dest.writeString(this.patternInfoII);
        dest.writeString(this.patternCheck);
        dest.writeString(this.date);
    }

    public java.lang.String toString() {
        return "userId =" + this.userId + ",seatId =" + this.seatId + ",patternNameI =" + this.patternNameI + ",patternInfoI =" + this.patternInfoI + ",patternNameII =" + this.patternNameII + ",patternInfoII =" + this.patternInfoII + ",patternCheck = " + this.patternCheck + ",date =" + this.date;
    }

    protected Seats(android.os.Parcel in) {
        this.userId = in.readString();
        this.seatId = in.readString();
        this.patternNameI = in.readString();
        this.patternInfoI = in.readString();
        this.patternNameII = in.readString();
        this.patternInfoII = in.readString();
        this.patternCheck = in.readString();
        this.date = in.readString();
    }

    public java.lang.String getUserId() {
        return this.userId;
    }

    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }

    public java.lang.String getSeatId() {
        return this.seatId;
    }

    public void setSeatId(java.lang.String seatId) {
        this.seatId = seatId;
    }

    public java.lang.String getPatternNameI() {
        return this.patternNameI;
    }

    public void setPatternNameI(java.lang.String patternNameI) {
        this.patternNameI = patternNameI;
    }

    public java.lang.String getPatternInfoI() {
        return this.patternInfoI;
    }

    public void setPatternInfoI(java.lang.String patternInfoI) {
        this.patternInfoI = patternInfoI;
    }

    public java.lang.String getPatternNameII() {
        return this.patternNameII;
    }

    public void setPatternNameII(java.lang.String patternNameII) {
        this.patternNameII = patternNameII;
    }

    public java.lang.String getPatternInfoII() {
        return this.patternInfoII;
    }

    public void setPatternInfoII(java.lang.String patternInfoII) {
        this.patternInfoII = patternInfoII;
    }

    public java.lang.String getPatternCheck() {
        return this.patternCheck;
    }

    public void setPatternCheck(java.lang.String patternCheck) {
        this.patternCheck = patternCheck;
    }

    public java.lang.String getDate() {
        return this.date;
    }

    public void setDate(java.lang.String date) {
        this.date = date;
    }
}
