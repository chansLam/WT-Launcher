package com.incall.apps.common.view;

/* loaded from: classes.dex */
public class SwitchButton extends android.widget.Switch {
    private android.widget.CompoundButton.OnCheckedChangeListener mListener;

    public SwitchButton(android.content.Context context) {
        super(context);
    }

    public SwitchButton(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    public SwitchButton(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override // android.widget.CompoundButton
    public void setOnCheckedChangeListener(android.widget.CompoundButton.OnCheckedChangeListener listener) {
        this.mListener = listener;
        super.setOnCheckedChangeListener(listener);
    }

    public void setCheckedSafty(boolean checked) {
        super.setOnCheckedChangeListener(null);
        super.setChecked(checked);
        super.setOnCheckedChangeListener(this.mListener);
    }
}
