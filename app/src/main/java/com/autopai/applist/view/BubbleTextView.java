package com.autopai.applist.view;

@android.annotation.SuppressLint({"AppCompatCustomView"})
/* loaded from: classes.dex */
public class BubbleTextView extends android.widget.TextView {
    private android.graphics.drawable.Drawable mIcon;
    private final int mIconSize;

    public BubbleTextView(android.content.Context context) {
        this(context, null, 0);
    }

    public BubbleTextView(android.content.Context context, android.util.AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleTextView(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setGravity(1);
        setTextSize(0, 32.0f);
        setTextColor(android.graphics.Color.parseColor("#FFFFFFFF"));
        this.mIconSize = com.autopai.applist.model.config.AppsConfig.APP_ICON_SIZE;
        setLines(1);
    }

    public android.graphics.drawable.Drawable getIcon() {
        return this.mIcon;
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setPadding(0, 34, 0, 0);
    }

    public android.graphics.drawable.Drawable setIcon(android.graphics.drawable.Drawable icon) {
        this.mIcon = icon;
        if (this.mIconSize != -1) {
            this.mIcon.setBounds(0, 0, this.mIconSize, this.mIconSize);
        }
        setCompoundDrawables(null, this.mIcon, null, null);
        setCompoundDrawablePadding(32);
        return icon;
    }
}
