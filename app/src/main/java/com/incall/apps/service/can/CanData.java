package com.incall.apps.service.can;

/* loaded from: classes.dex */
public class CanData implements java.io.Serializable {
    public static final double DEFAULT_VAL = -777.0d;
    private static final long serialVersionUID = 1;

    @com.google.gson.annotations.SerializedName("factor")
    private double factor;

    @com.google.gson.annotations.SerializedName("index")
    private int index;

    @com.google.gson.annotations.SerializedName("length")
    private int length;

    @com.google.gson.annotations.SerializedName("name")
    private java.lang.String name;

    @com.google.gson.annotations.SerializedName("value")
    private int value = com.incall.apps.service.can.CarStatusEntity.DEFAULT_VALUE;

    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public double getFactor() {
        return this.factor;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public java.lang.String toString() {
        return "CanData{name='" + this.name + "', factor=" + this.factor + ", index=" + this.index + ", length=" + this.length + ", value=" + this.value + '}';
    }
}
