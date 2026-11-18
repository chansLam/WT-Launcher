package com.incall.apps.common.view;

/* loaded from: classes.dex */
public class CommonRadioGroup extends android.widget.RadioGroup {
    private android.widget.RadioGroup.OnCheckedChangeListener mListener;

    public CommonRadioGroup(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    public CommonRadioGroup(android.content.Context context) {
        super(context);
    }

    @Override // android.widget.RadioGroup
    public void setOnCheckedChangeListener(android.widget.RadioGroup.OnCheckedChangeListener listener) {
        this.mListener = listener;
        super.setOnCheckedChangeListener(listener);
    }

    public void checkSafty(int checkId) {
        super.setOnCheckedChangeListener(null);
        super.check(checkId);
        super.setOnCheckedChangeListener(this.mListener);
    }
}
