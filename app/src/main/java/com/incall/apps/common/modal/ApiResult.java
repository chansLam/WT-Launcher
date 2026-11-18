package com.incall.apps.common.modal;

/* loaded from: classes.dex */
public class ApiResult {
    private java.lang.String address;
    private java.lang.String error_msg;
    private int status_code;

    public java.lang.String getAddress() {
        return this.address;
    }

    public void setAddress(java.lang.String address) {
        this.address = address;
    }

    public int getStatus_code() {
        return this.status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public java.lang.String getError_msg() {
        return this.error_msg;
    }

    public void setError_msg(java.lang.String error_msg) {
        this.error_msg = error_msg;
    }

    public java.lang.String toString() {
        return "ApiResult [status_code=" + this.status_code + ", error_msg=" + this.error_msg + "]";
    }
}
