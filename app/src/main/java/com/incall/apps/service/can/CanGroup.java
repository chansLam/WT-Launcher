package com.incall.apps.service.can;

/* loaded from: classes.dex */
public class CanGroup implements java.io.Serializable {
    private static final long serialVersionUID = 1;

    @com.google.gson.annotations.SerializedName("canId")
    private java.lang.String canId;
    private transient int canIdInt;

    @com.google.gson.annotations.SerializedName("datas")
    private java.util.List<com.incall.apps.service.can.CanData> datas;

    @com.google.gson.annotations.SerializedName("frequency")
    private int frequency;

    public void parseCanIdInt() {
        this.canIdInt = java.lang.Integer.parseInt(this.canId, 16);
    }

    public int getCanIdInt() {
        return this.canIdInt;
    }

    public void setCanIdInt(int canIdInt) {
        this.canIdInt = canIdInt;
    }

    public java.lang.String getCanId() {
        return this.canId;
    }

    public void setCanId(java.lang.String canId) {
        this.canId = canId;
    }

    public int getFrequency() {
        return this.frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public java.util.List<com.incall.apps.service.can.CanData> getDatas() {
        return this.datas;
    }

    public void setDatas(java.util.List<com.incall.apps.service.can.CanData> datas) {
        this.datas = datas;
    }

    public java.lang.String toString() {
        return "CanGroup{canId=" + this.canId + ", frequency=" + this.frequency + ", datas=" + this.datas.toString() + '}';
    }
}
