package com.android.launcherWT.home;

import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.List;

public class CardPageView extends LinearLayout {
    private static final int HOST_ID = 0x575401;
    private static final String PREFS = "card_widgets";

    private final AppWidgetHost host;
    private final AppWidgetManager widgets;
    private final FrameLayout[] slots = new FrameLayout[ShortcutTargets.ALL.length];
    private boolean listening;

    public CardPageView(Context context) {
        super(context);
        setOrientation(HORIZONTAL);
        AppWidgetHost createdHost = null;
        AppWidgetManager createdWidgets = null;
        try {
            createdHost = new AppWidgetHost(context.getApplicationContext(), HOST_ID);
            createdWidgets = AppWidgetManager.getInstance(context);
        } catch (RuntimeException ex) {
            createdHost = null;
            createdWidgets = null;
        }
        host = createdHost;
        widgets = createdWidgets;
        for (int i = 0; i < ShortcutTargets.ALL.length; i++) {
            FrameLayout slot = new FrameLayout(context);
            LayoutParams params = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f);
            int margin = dp(8);
            params.setMargins(margin, margin, margin, margin);
            slots[i] = slot;
            addView(slot, params);
            showName(i);
        }
        if (host != null && widgets != null) {
            tryBindAll();
        }
    }

    public void startListening() {
        if (host == null || listening) {
            return;
        }
        try {
            host.startListening();
            listening = true;
        } catch (RuntimeException ex) {
            listening = false;
        }
    }

    public void stopListening() {
        if (host == null || !listening) {
            return;
        }
        try {
            host.stopListening();
        } catch (RuntimeException ex) {
            // The name buttons stay usable if the host is already gone.
        } finally {
            listening = false;
        }
    }

    private void tryBindAll() {
        for (int i = 0; i < ShortcutTargets.ALL.length; i++) {
            try {
                tryBind(i);
            } catch (RuntimeException ex) {
                showName(i);
            }
        }
    }

    private void tryBind(int index) {
        ShortcutTargets target = ShortcutTargets.ALL[index];
        ComponentName provider = new ComponentName(target.widgetPackage, target.widgetClass);
        if (!providerInstalled(provider)) {
            forget(provider);
            showName(index);
            return;
        }
        int appWidgetId = boundId(provider);
        if (appWidgetId == -1) {
            showName(index);
            return;
        }
        AppWidgetProviderInfo info = widgets.getAppWidgetInfo(appWidgetId);
        if (info == null) {
            showName(index);
            return;
        }
        AppWidgetHostView hostView = host.createView(getContext(), appWidgetId, info);
        hostView.setAppWidget(appWidgetId, info);
        slots[index].removeAllViews();
        slots[index].addView(hostView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
    }

    private int boundId(ComponentName provider) {
        SharedPreferences prefs = prefs();
        String key = provider.flattenToShortString();
        int saved = prefs.getInt(key, -1);
        if (saved != -1) {
            AppWidgetProviderInfo info = widgets.getAppWidgetInfo(saved);
            if (info != null && provider.equals(info.provider)) {
                return saved;
            }
            deleteId(saved);
            prefs.edit().remove(key).apply();
        }
        int id = host.allocateAppWidgetId();
        try {
            if (!widgets.bindAppWidgetIdIfAllowed(id, provider)) {
                deleteId(id);
                return -1;
            }
            AppWidgetProviderInfo info = widgets.getAppWidgetInfo(id);
            if (info == null || !provider.equals(info.provider)) {
                deleteId(id);
                return -1;
            }
        } catch (RuntimeException ex) {
            deleteId(id);
            throw ex;
        }
        prefs.edit().putInt(key, id).apply();
        return id;
    }

    private boolean providerInstalled(ComponentName provider) {
        List<AppWidgetProviderInfo> installed = widgets.getInstalledProviders();
        if (installed == null) {
            return false;
        }
        for (int i = 0; i < installed.size(); i++) {
            AppWidgetProviderInfo info = installed.get(i);
            if (info != null && provider.equals(info.provider)) {
                return true;
            }
        }
        return false;
    }

    private void forget(ComponentName provider) {
        SharedPreferences prefs = prefs();
        String key = provider.flattenToShortString();
        int saved = prefs.getInt(key, -1);
        if (saved != -1) {
            deleteId(saved);
            prefs.edit().remove(key).apply();
        }
    }

    private void deleteId(int appWidgetId) {
        try {
            host.deleteAppWidgetId(appWidgetId);
        } catch (RuntimeException ex) {
            // Dropping a stale id should not take down the home screen.
        }
    }

    private SharedPreferences prefs() {
        return getContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    private void showName(final int index) {
        final ShortcutTargets target = ShortcutTargets.ALL[index];
        Button button = new Button(getContext());
        button.setText(target.labelRes);
        button.setContentDescription(button.getText());
        button.setTextColor(0xFFFFFFFF);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        button.setAllCaps(false);
        button.setBackground(buttonBackground(dp(12)));
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeLaunch.start(getContext(), target.packageName, target.className);
            }
        });
        slots[index].removeAllViews();
        slots[index].addView(button, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
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
