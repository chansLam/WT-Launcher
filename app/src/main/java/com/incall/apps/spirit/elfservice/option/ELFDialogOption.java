package com.incall.apps.spirit.elfservice.option;

/* loaded from: classes.dex */
public class ELFDialogOption extends com.incall.apps.spirit.elfservice.option.BaseDialogOption {
    public static final java.lang.String EXTRA_DETAIL = "detail";
    private static final java.lang.String EXTRA_DIALOG_BG = "dialogBg";
    public static final java.lang.String EXTRA_ICON_BITMAP = "iconBitmap";
    public static final java.lang.String EXTRA_IMAGE_BITMAP = "imageBitmap";
    public static final java.lang.String EXTRA_LEFT_BTN_BG = "leftBtnBG";
    public static final java.lang.String EXTRA_LEFT_BTN_STATE = "leftBtnState";
    public static final java.lang.String EXTRA_LEFT_BTN_TEXT = "leftBtnText";
    public static final java.lang.String EXTRA_RIGHT_BTN_BG = "rightBtnBG";
    public static final java.lang.String EXTRA_RIGHT_BTN_STATE = "rightBtnState";
    public static final java.lang.String EXTRA_RIGHT_BTN_TEXT = "rightBtnText";
    private static final java.lang.String EXTRA_SHOW_TIME = "showTime";
    public static final java.lang.String EXTRA_TITLE = "title";
    private static final java.lang.String EXTRA_VOICE_KEY = "voiceKey";

    public ELFDialogOption() {
    }

    public ELFDialogOption(android.os.Bundle bundle, com.incall.apps.spirit.elfservice.callback.IELFDialogCallback callback) {
        super(bundle, callback);
    }

    public ELFDialogOption(java.lang.String title, java.lang.String leftBtnText, java.lang.String rightBtnText, android.graphics.Bitmap iconBitmap) {
        setTitle(title);
        setLeftBtnText(leftBtnText);
        setRightBtnText(rightBtnText);
        setIconBitmap(iconBitmap);
    }

    public ELFDialogOption(java.lang.String title, java.lang.String detail, java.lang.String leftBtnText, java.lang.String rightBtnText) {
        setTitle(title);
        setDetail(detail);
        setLeftBtnText(leftBtnText);
        setRightBtnText(rightBtnText);
    }

    public ELFDialogOption(java.lang.String title, java.lang.String detail, java.lang.String leftBtnText, java.lang.String rightBtnText, android.graphics.Bitmap iconBitmap, int showTime) {
        setTitle(title);
        setDetail(detail);
        setLeftBtnText(leftBtnText);
        setRightBtnText(rightBtnText);
        setIconBitmap(iconBitmap);
        setShowTime(showTime);
    }

    public ELFDialogOption(java.lang.String title, java.lang.String detail, java.lang.String leftBtnText, java.lang.String rightBtnText, android.graphics.Bitmap iconbitmap, int showTime, java.lang.String speech) {
        setTitle(title);
        setDetail(detail);
        setLeftBtnText(leftBtnText);
        setRightBtnText(rightBtnText);
        setIconBitmap(iconbitmap);
        setShowTime(showTime);
        setSpeech(speech);
    }

    public java.lang.String getDetail() {
        return this.bundle.getString(EXTRA_DETAIL);
    }

    public void setDetail(java.lang.String detail) {
        this.bundle.putString(EXTRA_DETAIL, detail);
    }

    public java.lang.String getLeftBtnText() {
        return this.bundle.getString(EXTRA_LEFT_BTN_TEXT);
    }

    public void setLeftBtnText(java.lang.String leftBtnText) {
        this.bundle.putString(EXTRA_LEFT_BTN_TEXT, leftBtnText);
    }

    public void setLeftBtnBg(android.graphics.Bitmap leftBtnBg) {
        this.bundle.putParcelable(EXTRA_LEFT_BTN_BG, leftBtnBg);
    }

    public android.graphics.Bitmap getLeftBtnBg() {
        return getBitmap(EXTRA_LEFT_BTN_BG);
    }

    public java.lang.String getRightBtnText() {
        return this.bundle.getString(EXTRA_RIGHT_BTN_TEXT);
    }

    public void setRightBtnText(java.lang.String rightBtnText) {
        this.bundle.putString(EXTRA_RIGHT_BTN_TEXT, rightBtnText);
    }

    public void setRightBtnBg(android.graphics.Bitmap rightBtnBg) {
        this.bundle.putParcelable(EXTRA_RIGHT_BTN_BG, rightBtnBg);
    }

    public android.graphics.Bitmap getRightBtnBg() {
        return getBitmap(EXTRA_RIGHT_BTN_BG);
    }

    public android.graphics.Bitmap getIconBitmap() {
        return getBitmap(EXTRA_ICON_BITMAP);
    }

    public void setIconBitmap(android.graphics.Bitmap iconBitmap) {
        this.bundle.putParcelable(EXTRA_ICON_BITMAP, iconBitmap);
    }

    public android.graphics.Bitmap getImageBitmap() {
        return getBitmap(EXTRA_IMAGE_BITMAP);
    }

    public void setImageBitmap(android.graphics.Bitmap imageBitmap) {
        this.bundle.putParcelable(EXTRA_IMAGE_BITMAP, imageBitmap);
    }

    public long getShowTime() {
        return this.bundle.getLong(EXTRA_SHOW_TIME);
    }

    public void setShowTime(long showTime) {
        this.bundle.putLong(EXTRA_SHOW_TIME, showTime);
    }

    public void setDialogBg(java.lang.String dialogBg) {
        this.bundle.putString(EXTRA_DIALOG_BG, dialogBg);
    }

    public java.lang.String getDialogBg() {
        return this.bundle.getString(EXTRA_DIALOG_BG);
    }

    public void setLeftBtnState(int state) {
        this.bundle.putInt(EXTRA_LEFT_BTN_STATE, state);
    }

    public int getLeftBtnState() {
        return this.bundle.getInt(EXTRA_LEFT_BTN_STATE, -1);
    }

    public void setRightBtnState(int state) {
        this.bundle.putInt(EXTRA_RIGHT_BTN_STATE, state);
    }

    public int getRightBtnState() {
        return this.bundle.getInt(EXTRA_RIGHT_BTN_STATE, -1);
    }

    public void setVoiceKeys(java.lang.String[] voiceKeys) {
        org.json.JSONArray array = new org.json.JSONArray();
        if (voiceKeys != null) {
            for (java.lang.String str : voiceKeys) {
                array.put(str);
            }
        }
        org.json.JSONObject item = new org.json.JSONObject();
        try {
            item.put("cmd", array);
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
        this.bundle.putString(EXTRA_VOICE_KEY, item.toString());
    }

    public java.lang.String getVoiceKeyJson() {
        return this.bundle.getString(EXTRA_VOICE_KEY);
    }

    private android.graphics.Bitmap getBitmap(java.lang.String extra) {
        android.os.Parcelable smallParcel = this.bundle.getParcelable(extra);
        if (smallParcel instanceof android.graphics.Bitmap) {
            return (android.graphics.Bitmap) smallParcel;
        }
        return null;
    }
}
