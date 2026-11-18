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

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private final StringBuilder report = new StringBuilder();
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
        try {
            ResourceUtils.getInstance(this.context).setCurTheme(1);
        } catch (Throwable error) {
            Log.i(TAG, "status theme unavailable: " + error);
        }

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
        writeReport();
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
        // Several children hide themselves in their constructor when the head
        // unit has no such device (DvrView does it when hasDvrDevice() is false,
        // the stock launcher never shows the recording icon on a car without a
        // DVR). Keep that decision instead of forcing the layout's default back.
        if (visibility == View.VISIBLE && child.getVisibility() == View.GONE) {
            visibility = View.GONE;
        }
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
            View view = new AirConditionView(ctx);
            report.append("air=").append("ok\n");
            return view;
        } catch (Throwable error) {
            Log.i(TAG, "air condition service unavailable: " + error);
            report.append("air=").append(error).append('\n');
            return null;
        }
    }

    private View dvr(Context ctx) {
        try {
            View view = new DvrView(ctx);
            report.append("dvr=ok,selfVisibility=").append(view.getVisibility()).append('\n');
            return view;
        } catch (Throwable error) {
            Log.i(TAG, "dvr service unavailable: " + error);
            report.append("dvr=").append(error).append('\n');
            return null;
        }
    }

    private View usb(Context ctx) {
        try {
            View view = new UsbView(ctx);
            report.append("usb=").append("ok\n");
            return view;
        } catch (Throwable error) {
            Log.i(TAG, "usb service unavailable: " + error);
            report.append("usb=").append(error).append('\n');
            return null;
        }
    }

    private View wifi(Context ctx) {
        try {
            View view = new WifiView(ctx);
            report.append("wifi=").append("ok\n");
            return view;
        } catch (Throwable error) {
            Log.i(TAG, "wifi service unavailable: " + error);
            report.append("wifi=").append(error).append('\n');
            return null;
        }
    }

    private View bluetooth(Context ctx) {
        try {
            View view = new BluetoothView(ctx);
            report.append("bt=").append("ok\n");
            return view;
        } catch (Throwable error) {
            Log.i(TAG, "bluetooth service unavailable: " + error);
            report.append("bt=").append(error).append('\n');
            return null;
        }
    }

    private View mute(Context ctx) {
        try {
            View view = new MuteView(ctx);
            report.append("mute=").append("ok\n");
            return view;
        } catch (Throwable error) {
            Log.i(TAG, "mute service unavailable: " + error);
            report.append("mute=").append(error).append('\n');
            return null;
        }
    }

    private View signal(Context ctx) {
        try {
            View view = new SignalView(ctx);
            report.append("signal=").append("ok\n");
            return view;
        } catch (Throwable error) {
            Log.i(TAG, "signal service unavailable: " + error);
            report.append("signal=").append(error).append('\n');
            return null;
        }
    }

    /** Which vendor entry points are actually reachable on this head unit. */
    private void probeVendorLibrary() {
        probe("com.incall.proxy.ac.AirConditionManager");
        probe("com.incall.proxy.can.CanManager");
        probe("com.incall.proxy.tbox.TboxManager");
        probe("com.incall.proxy.bluetooth.BluetoothManager");
    }

    private void probe(String className) {
        try {
            Class.forName(className);
            report.append("lib ").append(className).append("=ok\n");
        } catch (Throwable error) {
            report.append("lib ").append(className).append('=').append(error).append('\n');
        }
    }

    /**
     * The stock cluster lives on vendor services that may not resolve outside
     * the factory app. Dump what was built and why to the SD card, so a head
     * unit without logcat can still be inspected.
     */
    private void writeReport() {
        probeVendorLibrary();
        String when = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        StringBuilder text = new StringBuilder();
        text.append("LauncherWT status cluster ").append(when).append('\n');
        text.append(report);
        try {
            File file = new File("/sdcard/launcherWT-status.log");
            FileWriter writer = new FileWriter(file, false);
            writer.write(text.toString());
            writer.close();
            Log.i(TAG, "status report written");
        } catch (Throwable error) {
            Log.i(TAG, "status report not written: " + error);
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
