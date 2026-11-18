package com.incall.apps.service.userservice;

/* loaded from: classes.dex */
public class UserCenterBroadcastUtils {
    private static final java.lang.String TAG = "BroadcastUtils";
    private static com.incall.apps.service.userservice.UserCenterBroadcastUtils instance;
    private android.content.Context mContext;

    public static synchronized com.incall.apps.service.userservice.UserCenterBroadcastUtils getInstance(android.content.Context context) {
        com.incall.apps.service.userservice.UserCenterBroadcastUtils userCenterBroadcastUtils;
        synchronized (com.incall.apps.service.userservice.UserCenterBroadcastUtils.class) {
            if (instance == null) {
                instance = new com.incall.apps.service.userservice.UserCenterBroadcastUtils(context);
            }
            userCenterBroadcastUtils = instance;
        }
        return userCenterBroadcastUtils;
    }

    private UserCenterBroadcastUtils(android.content.Context context) {
        this.mContext = context;
    }

    public void sendLoginSuccess() {
        android.content.Intent intent = new android.content.Intent();
        intent.setAction(com.incall.apps.service.userservice.UserCenterBroadcastConstants.USER_CENTER_LOGIN_SUCCESS);
        this.mContext.sendBroadcast(intent);
        com.incall.apps.service.util.LogUtil.i(TAG, "sendLoginSuccess");
    }

    public void sendLoginFailed() {
        android.content.Intent intent = new android.content.Intent();
        intent.setAction(com.incall.apps.service.userservice.UserCenterBroadcastConstants.USER_CENTER_LOGIN_FAILED);
        this.mContext.sendBroadcast(intent);
        com.incall.apps.service.util.LogUtil.i(TAG, "sendLoginFailed");
    }

    public void sendAuthorizeResult() {
        android.content.Intent intent = new android.content.Intent();
        intent.setAction(com.incall.apps.service.userservice.UserCenterBroadcastConstants.NOTIFICATION_TO_INQUIRE);
        this.mContext.sendBroadcast(intent);
        com.incall.apps.service.util.LogUtil.i(TAG, "sendAuthorizeResult");
    }
}
