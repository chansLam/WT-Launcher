package com.android.launcherWT.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.launcherWT.core.LaunchableApp;

import java.util.List;

public class AppGridView extends GridLayout {
    private static final int COLUMNS = 5;
    private static final int ROWS = 2;
    public static final int PAGE_SIZE = COLUMNS * ROWS;

    public AppGridView(Context context) {
        super(context);
        setColumnCount(COLUMNS);
        setRowCount(ROWS);
    }

    public void bind(List<LaunchableApp> apps, InstalledApps source) {
        removeAllViews();
        int shown = Math.min(apps.size(), PAGE_SIZE);
        for (int i = 0; i < PAGE_SIZE; i++) {
            View cell;
            if (i < shown) {
                final LaunchableApp app = apps.get(i);
                cell = createCell(app, source.icon(app));
            } else {
                cell = new View(getContext());
            }
            addView(cell, cellParams(i));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (width > 0 && height > 0 && getChildCount() > 0) {
            int cellWidth = width / COLUMNS;
            int cellHeight = height / ROWS;
            for (int i = 0; i < getChildCount(); i++) {
                LayoutParams params = (LayoutParams) getChildAt(i).getLayoutParams();
                params.width = cellWidth;
                params.height = cellHeight;
                params.columnSpec = GridLayout.spec(i % COLUMNS);
                params.rowSpec = GridLayout.spec(i / COLUMNS);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private LayoutParams cellParams(int index) {
        LayoutParams params = new LayoutParams(
                GridLayout.spec(index / COLUMNS),
                GridLayout.spec(index % COLUMNS));
        params.width = 0;
        params.height = 0;
        params.setGravity(Gravity.FILL);
        return params;
    }

    private View createCell(final LaunchableApp app, Drawable icon) {
        LinearLayout cell = new LinearLayout(getContext());
        cell.setOrientation(LinearLayout.VERTICAL);
        cell.setGravity(Gravity.CENTER);
        int pad = dp(4);
        cell.setPadding(pad, pad, pad, pad);
        cell.setContentDescription(app.label);

        ImageView image = new ImageView(getContext());
        image.setImageDrawable(icon);
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        int iconSize = dp(56);
        cell.addView(image, new LinearLayout.LayoutParams(iconSize, iconSize));

        TextView label = new TextView(getContext());
        label.setText(app.label);
        label.setTextColor(0xFFFFFFFF);
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        label.setGravity(Gravity.CENTER_HORIZONTAL);
        label.setSingleLine(true);
        label.setEllipsize(TextUtils.TruncateAt.END);
        label.setShadowLayer(3f, 0f, 1f, 0xCC000000);
        LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        labelParams.topMargin = dp(4);
        cell.addView(label, labelParams);

        cell.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeLaunch.start(getContext(), app.packageName, app.className);
            }
        });
        return cell;
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
