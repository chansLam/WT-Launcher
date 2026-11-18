package com.incall.proxy.tbox;

public interface INetworkCallBack {
    void onException(int error, String response);
    void onCompleted(int type, String response);
}
