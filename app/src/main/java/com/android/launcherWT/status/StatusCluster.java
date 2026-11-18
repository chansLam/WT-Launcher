package com.android.launcherWT.status;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.launcherWT.R;
import com.autopai.support.utils.AirConditionView;
import com.autopai.support.utils.BluetoothView;
import com.autopai.support.utils.Clock;
import com.autopai.support.utils.DvrView;
import com.autopai.support.utils.MuteView;
import com.autopai.support.utils.ResourceUtils;
import com.autopai.support.utils.SignalView;
import com.autopai.support.utils.UsbView;
import com.autopai.support.utils.WifiView;

import java.util.ArrayList;
import java.util.List;

/**
 * The top-right cluster from LauncherWT 1.8.17.23
 * (res/layout/jg_toprightview_layout.xml), rebuilt child by child.
 *
 * Each child is the original view class, so it keeps the original art, states
 * and service bindings. A child whose platform service is missing throws while
 * being constructed; that child is dropped and the rest keep working.
 */
public final class StatusCluster {
    private static final String TAG = "LauncherWT";
    private static final String ACTION_SHOW = "com.incall.action.StatusBar.Show";
    private static final String ACTION_HIDE = "com.incall.action.StatusBar.Hide";

    private final Context context;
    private final LinearLayout root;
    private final List<View> vehicle = new ArrayList<View>();
    private final List<Integer> savedVisibility = new ArrayList<Integer>();
    private final int height;
    private final int topMargin;

    private boolean registered;
    private boolean hiddenBySystem;

    private final BroadcastReceiver statusBarReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctx, Intent intent) {
            String action = intent == null ? null : intent.getAction();
            if (ACTION_HIDE.equals(action)) {
                hideVehicle();
            } else if (ACTION_SHOW.equals(action)) {
                showVehicle();
            }
        }
    };

    public static StatusCluster create(Context ctx) {
        return new StatusCluster(ctx);
    }

    private StatusCluster(Context ctx) {
        this.context = ctx.getApplicationContext();
        Resources res = ctx.getResources();
        this.height = res.getDimensionPixelSize(R.dimen.jg_top_right_view_height);
        this.topMargin = res.getDimensionPixelSize(R.dimen.jg_top_right_view_top_margin);

        // The original pins the top bar to daylight (TopRightView.setThemeType(1)),
        // so day_ resources are used whatever the system night mode is.
        ResourceUtils.getInstance(this.context).setCurTheme(1);

        root = new LinearLayout(ctx);
        root.setOrientation(LinearLayout.HORIZONTAL);
        root.setGravity(Gravity.CENTER_VERTICAL);
        root.setPadding(0, 0, res.getDimensionPixelSize(R.dimen.jg_top_right_view_right_margin), 0);

        int iconHeight = res.getDimensionPixelSize(R.dimen.jg_top_right_view_icon_height);
        int iconGap = res.getDimensionPixelSize(R.dimen.jg_top_left_icon_margin);

        add(airCondition(ctx), iconHeight, iconGap, View.VISIBLE);
        add(usb(ctx), iconHeight, iconGap, View.GONE);
        add(dvr(ctx), iconHeight, iconGap, View.VISIBLE);
        add(wifi(ctx), iconHeight, iconGap, View.GONE);
        add(bluetooth(ctx), iconHeight, iconGap, View.GONE);
        add(mute(ctx), iconHeight, iconGap, View.GONE);
        addNavigatorStub(ctx, iconHeight);
        add(signal(ctx), iconHeight, iconGap, View.VISIBLE);
        addClock(ctx, iconGap);

        listenStatusBar();
    }

    public View view() {
        return root;
    }

    public int heightPx() {
        return height;
    }

    public int topMarginPx() {
        return topMargin;
    }

    private void add(View child, int iconHeight, int iconGap, int visibility) {
        if (child == null) {
            return;
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, iconHeight);
        params.gravity = Gravity.CENTER_VERTICAL;
        params.rightMargin = iconGap;
        child.setLayoutParams(params);
        child.setVisibility(visibility);
        root.addView(child);
        vehicle.add(child);
    }

    private void addClock(Context ctx, int iconGap) {
        try {
            Clock clock = new Clock(ctx);
            clock.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX,
                    ctx.getResources().getDimensionPixelSize(R.dimen.jg_top_right_view_text_size));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER_VERTICAL;
            params.leftMargin = iconGap;
            clock.setLayoutParams(params);
            root.addView(clock);
        } catch (Throwable error) {
            Log.i(TAG, "status clock unavailable: " + error);
        }
    }

    /** The navigation card stays collapsed exactly as in the original layout. */
    private void addNavigatorStub(Context ctx, int iconHeight) {
        try {
            View stub = new View(ctx);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, iconHeight);
            params.gravity = Gravity.CENTER_VERTICAL;
            stub.setLayoutParams(params);
            stub.setVisibility(View.GONE);
            root.addView(stub);
        } catch (Throwable error) {
            Log.i(TAG, "status navigator stub unavailable: " + error);
        }
    }

    private View airCondition(Context ctx) {
        try {
            return new AirConditionView(ctx);
        } catch (Throwable error) {
            Log.i(TAG, "air condition service unavailable: " + error);
            return null;
        }
    }

    private View usb(Context ctx) {
        try {
            return new UsbView(ctx);
        } catch (Throwable error) {
            Log.i(TAG, "usb service unavailable: " + error);
            return null;
        }
    }

    private View dvr(Context ctx) {
        try {
            return new DvrView(ctx);
        } catch (Throwable error) {
            Log.i(TAG, "dvr service unavailable: " + error);
            return null;
        }
    }

    private View wifi(Context ctx) {
        try {
            return new WifiView(ctx);
        } catch (Throwable error) {
            Log.i(TAG, "wifi service unavailable: " + error);
            return null;
        }
    }

    private View bluetooth(Context ctx) {
        try {
            return new BluetoothView(ctx);
        } catch (Throwable error) {
            Log.i(TAG, "bluetooth service unavailable: " + error);
            return null;
        }
    }

    private View mute(Context ctx) {
        try {
            return new MuteView(ctx);
        } catch (Throwable error) {
            Log.i(TAG, "mute service unavailable: " + error);
            return null;
        }
    }

    private View signal(Context ctx) {
        try {
            return new SignalView(ctx);
        } catch (Throwable error) {
            Log.i(TAG, "signal service unavailable: " + error);
            return null;
        }
    }

    private void listenStatusBar() {
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction(ACTION_SHOW);
            filter.addAction(ACTION_HIDE);
            context.registerReceiver(statusBarReceiver, filter);
            registered = true;
        } catch (Throwable error) {
            Log.i(TAG, "status bar broadcasts unavailable: " + error);
        }
    }

    private void hideVehicle() {
        if (hiddenBySystem) {
            return;
        }
        hiddenBySystem = true;
        savedVisibility.clear();
        for (int i = 0; i < vehicle.size(); i++) {
            View child = vehicle.get(i);
            savedVisibility.add(child.getVisibility());
            child.setVisibility(View.GONE);
        }
    }

    private void showVehicle() {
        if (!hiddenBySystem) {
            return;
        }
        hiddenBySystem = false;
        for (int i = 0; i < vehicle.size() && i < savedVisibility.size(); i++) {
            vehicle.get(i).setVisibility(savedVisibility.get(i));
        }
        savedVisibility.clear();
    }

    public void destroy() {
        if (registered) {
            try {
                context.unregisterReceiver(statusBarReceiver);
            } catch (Throwable error) {
                Log.i(TAG, "status bar unregister failed: " + error);
            }
            registered = false;
        }
    }
}
