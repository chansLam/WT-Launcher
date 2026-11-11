package com.android.launcherWT.home;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class HotseatView extends LinearLayout {
    public HotseatView(Context context) {
        this(context, null);
    }

    public HotseatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        int pad = dp(8);
        setPadding(pad, pad, pad, pad);
        for (int i = 0; i < ShortcutTargets.ALL.length; i++) {
            addView(createButton(ShortcutTargets.ALL[i]), buttonParams(i));
        }
    }

    private Button createButton(final ShortcutTargets target) {
        Button button = new Button(getContext());
        button.setText(target.labelRes);
        button.setContentDescription(button.getText());
        button.setTextColor(0xFFFFFFFF);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        button.setAllCaps(false);
        button.setBackground(buttonBackground(dp(8)));
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeLaunch.start(getContext(), target.packageName, target.className);
            }
        });
        return button;
    }

    private LayoutParams buttonParams(int index) {
        LayoutParams params = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f);
        if (index > 0) {
            params.leftMargin = dp(8);
        }
        return params;
    }

    private StateListDrawable buttonBackground(int radius) {
        GradientDrawable normal = new GradientDrawable();
        normal.setColor(0xCC1B2430);
        normal.setCornerRadius(radius);
        GradientDrawable pressed = new GradientDrawable();
        pressed.setColor(0xE6455A64);
        pressed.setCornerRadius(radius);
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[] {android.R.attr.state_pressed}, pressed);
        states.addState(new int[] {}, normal);
        return states;
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
