package com.autopai.support.utils;

/* loaded from: classes.dex */
public class ResourceUtils {
    private static final java.lang.String LOGTAG = "ResourceUtils";
    private static final java.lang.String PRE_DAYLIGHT = "day_";
    private static volatile com.autopai.support.utils.ResourceUtils mInstance;
    private android.content.Context mContext;
    private int mCurTheme = 0;
    private android.content.res.Resources mResources;

    private ResourceUtils(android.content.Context cxt) {
        this.mContext = cxt.getApplicationContext();
        this.mResources = this.mContext.getResources();
    }

    public static com.autopai.support.utils.ResourceUtils getInstance(android.content.Context cxt) {
        if (mInstance == null) {
            synchronized (com.autopai.support.utils.ResourceUtils.class) {
                if (mInstance == null) {
                    mInstance = new com.autopai.support.utils.ResourceUtils(cxt);
                }
            }
        }
        return mInstance;
    }

    public void setCurTheme(int theme) {
        this.mCurTheme = theme;
    }

    public android.graphics.drawable.Drawable getDrawable(int resId) {
        com.autopai.support.utils.SLog.d(LOGTAG, "getDrawable resId = " + resId);
        android.graphics.drawable.Drawable originDrawable = this.mResources.getDrawable(resId);
        int targetResId = getTargetResId(resId);
        if (targetResId == 0) {
            return originDrawable;
        }
        android.graphics.drawable.Drawable originDrawable2 = this.mResources.getDrawable(targetResId);
        return originDrawable2;
    }

    public android.content.res.ColorStateList getColorStateList(int resId) {
        android.content.res.ColorStateList colorStateList = this.mResources.getColorStateList(resId);
        int targetResId = getTargetResId(resId);
        if (targetResId == 0) {
            return colorStateList;
        }
        android.content.res.ColorStateList colorStateList2 = this.mResources.getColorStateList(targetResId);
        return colorStateList2;
    }

    private int getTargetResId(int resId) {
        try {
            java.lang.String resName = this.mResources.getResourceEntryName(resId);
            java.lang.String type = this.mResources.getResourceTypeName(resId);
            com.autopai.support.utils.SLog.d(LOGTAG, "getTargetResId resName " + resName + "; resId = " + resId);
            if (this.mCurTheme == 1) {
                if (!resName.startsWith(PRE_DAYLIGHT)) {
                    resName = PRE_DAYLIGHT + resName;
                } else {
                    return resId;
                }
            } else if (this.mCurTheme == 0) {
                if (resName.startsWith(PRE_DAYLIGHT)) {
                    resName = resName.substring(PRE_DAYLIGHT.length(), resName.length());
                } else {
                    return resId;
                }
            }
            return this.mResources.getIdentifier(resName, type, this.mContext.getPackageName());
        } catch (java.lang.Exception e) {
            return 0;
        }
    }
}
