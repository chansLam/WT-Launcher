package com.incall.apps.service.aiservice;

/* loaded from: classes.dex */
public interface IAIManager {
    void addCallback(com.incall.apps.service.aiservice.callback.IAICallback iAICallback);

    void clickBournForecast();

    void clickCompany();

    void clickHome();

    java.lang.String getBournForecastData();

    void removeCallback(com.incall.apps.service.aiservice.callback.IAICallback iAICallback);

    void requestNetwork(java.lang.String str, java.util.Map<java.lang.String, java.lang.String> map, com.incall.apps.service.aiservice.callback.INetworkCallback iNetworkCallback);

    void uploadBehavior(java.lang.String str);
}
