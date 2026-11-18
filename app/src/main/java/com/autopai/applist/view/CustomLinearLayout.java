package com.autopai.applist.view;

/* loaded from: classes.dex */
public class CustomLinearLayout extends android.widget.LinearLayout {
    private android.widget.ImageView mImageView;
    private android.widget.TextView mTextView;

    public CustomLinearLayout(android.content.Context context) {
        this(context, null, 0);
    }

    public CustomLinearLayout(android.content.Context context, @android.support.annotation.Nullable android.util.AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomLinearLayout(android.content.Context context, @android.support.annotation.Nullable android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setOrientation(1);
        android.widget.LinearLayout.LayoutParams imgParams = new android.widget.LinearLayout.LayoutParams(-1, -1);
        android.widget.LinearLayout.LayoutParams textParams = new android.widget.LinearLayout.LayoutParams(-2, -2);
        imgParams.gravity = 1;
        textParams.gravity = 1;
        this.mImageView = new android.widget.ImageView(getContext());
        imgParams.topMargin = 34;
        this.mImageView.setLayoutParams(imgParams);
        imgParams.width = com.autopai.applist.model.config.AppsConfig.APP_ICON_SIZE;
        imgParams.height = com.autopai.applist.model.config.AppsConfig.APP_ICON_SIZE;
        this.mTextView = new android.widget.TextView(getContext());
        this.mTextView.setTextSize(0, 32.0f);
        this.mTextView.setTextColor(-1);
        this.mTextView.setGravity(1);
        textParams.topMargin = 32;
        this.mTextView.setLayoutParams(textParams);
        addView(this.mImageView);
        addView(this.mTextView);
    }

    public void setShowBadge(boolean showBadge) {
    }

    public android.widget.ImageView getImageView() {
        return this.mImageView;
    }

    public android.widget.TextView getTextView() {
        return this.mTextView;
    }
}
