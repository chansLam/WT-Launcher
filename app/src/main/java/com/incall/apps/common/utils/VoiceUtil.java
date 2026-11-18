package com.incall.apps.common.utils;

/* loaded from: classes.dex */
public class VoiceUtil {
    private static com.incall.apps.common.utils.VoiceUtil mInstance;
    private java.lang.String TAG = "VoiceUtil";

    public static synchronized com.incall.apps.common.utils.VoiceUtil getInstance() {
        com.incall.apps.common.utils.VoiceUtil voiceUtil;
        synchronized (com.incall.apps.common.utils.VoiceUtil.class) {
            if (mInstance == null) {
                mInstance = new com.incall.apps.common.utils.VoiceUtil();
            }
            voiceUtil = mInstance;
        }
        return voiceUtil;
    }

    private VoiceUtil() {
    }

    private java.lang.String generateJson(java.lang.String focus, java.lang.String operation, java.lang.String msg) {
        org.json.JSONObject item = new org.json.JSONObject();
        try {
            item.put("focus", focus);
            item.put("operation", operation);
            item.put(android.support.v4.app.NotificationCompat.CATEGORY_MESSAGE, msg);
        } catch (org.json.JSONException e) {
            android.util.Log.i(this.TAG, "generateJson -- JSONException");
            e.printStackTrace();
        }
        return item.toString();
    }

    public void playTTSVoice(java.lang.String focus, java.lang.String operation, java.lang.String msg) {
        java.lang.String data = generateJson(focus, operation, msg);
        android.util.Log.i(this.TAG, "playTTSVoice = " + data);
        com.incall.proxy.voice.VoiceManager.getInstance().sendElfData(0, data);
    }

    public void playOtherTTS(java.lang.String tts) {
        android.util.Log.i(this.TAG, "playOtherTTS = " + tts);
        com.incall.proxy.voice.VoiceManager.getInstance().playOtherTTS(tts);
    }
}
