package com.android.launcherWT.home;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.android.launcherWT.R;
import com.android.launcherWT.core.DisplayOrder;
import com.android.launcherWT.core.LaunchableApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class DesktopEntries {
    public static final class Item {
        public final String key;
        public final String packageName;
        public final String className;
        public final String title;
        public final int iconRes;
        public final boolean speech;
        public final boolean settings;

        Item(String key, String packageName, String className, String title, int iconRes, boolean speech) {
            this(key, packageName, className, title, iconRes, speech, false);
        }

        Item(String key, String packageName, String className, String title, int iconRes,
                boolean speech, boolean settings) {
            this.key = key;
            this.packageName = packageName;
            this.className = className;
            this.title = title;
            this.iconRes = iconRes;
            this.speech = speech;
            this.settings = settings;
        }
    }

    private DesktopEntries() {
    }

    /** Desktop settings tile, always appended last. */
    public static final String SETTINGS_KEY = "desktop-settings";

    public static List<Item> source(Context context, int theme) {
        return source(context, theme, new ArrayList<String>());
    }

    /** Favorite whitelist plus apps the user added through the settings page. */
    public static List<Item> source(Context context, int theme, List<String> addedKeys) {
        List<Item> items = new ArrayList<Item>();
        Set<String> reserved = new HashSet<String>();
        reserved.add(context.getPackageName());
        List<FavoriteCatalog.Entry> favorites = FavoriteCatalog.entries();
        for (int i = 0; i < favorites.size(); i++) {
            FavoriteCatalog.Entry entry = favorites.get(i);
            reserved.add(entry.packageName);
            if (CarFilters.hidden(entry.packageName)) {
                continue;
            }
            if (!launchable(context.getPackageManager(), entry.packageName, entry.className)) {
                continue;
            }
            items.add(new Item(
                    entry.key(),
                    entry.packageName,
                    entry.className,
                    entry.title(),
                    entry.icon(theme),
                    "com.tencent.wecarspeech".equals(entry.packageName)));
        }
        InstalledApps installed = InstalledApps.load(context.getPackageManager());
        if (items.isEmpty()) {
            // No car app of the whitelist is installed (emulator/dev board): show what is there
            // instead of an empty board.
            List<LaunchableApp> all = installed.apps();
            for (int i = 0; i < all.size(); i++) {
                LaunchableApp app = all.get(i);
                if (reserved.contains(app.packageName) || CarFilters.hidden(app.packageName)) {
                    continue;
                }
                items.add(new Item(app.key(), app.packageName, app.className, app.label, 0, false));
            }
        }
        if (addedKeys != null && !addedKeys.isEmpty()) {
            List<LaunchableApp> apps = installed.apps();
            for (int i = 0; i < apps.size(); i++) {
                LaunchableApp app = apps.get(i);
                if (!addedKeys.contains(app.key()) || reserved.contains(app.packageName)) {
                    continue;
                }
                if (CarFilters.hidden(app.packageName)) {
                    continue;
                }
                items.add(new Item(app.key(), app.packageName, app.className, app.label, 0, false));
            }
        }
        items.add(new Item(SETTINGS_KEY, context.getPackageName(),
                "com.android.launcherWT.DesktopSettingsActivity", context.getString(R.string.desktop_settings),
                R.drawable.classic_icon_system_settings, false, true));
        return items;
    }

    public static List<Item> arrange(List<Item> source, List<String> savedOrder, Set<String> hidden, boolean includeHidden) {
        Map<String, Item> byKey = new HashMap<String, Item>();
        List<String> keys = new ArrayList<String>();
        Set<String> seen = new HashSet<String>();
        for (int i = 0; i < source.size(); i++) {
            Item item = source.get(i);
            if (item == null || !seen.add(item.key)) {
                continue;
            }
            if (!includeHidden && hidden.contains(item.key)) {
                continue;
            }
            byKey.put(item.key, item);
            keys.add(item.key);
        }
        List<String> ordered = DisplayOrder.merge(keys, savedOrder);
        List<Item> result = new ArrayList<Item>();
        for (int i = 0; i < ordered.size(); i++) {
            result.add(byKey.get(ordered.get(i)));
        }
        return result;
    }

    /** The original lists only installed apps that expose a launcher activity. */
    private static boolean launchable(PackageManager manager, String packageName, String className) {
        Intent probe = new Intent(Intent.ACTION_MAIN);
        probe.addCategory(Intent.CATEGORY_LAUNCHER);
        probe.setPackage(packageName);
        List<ResolveInfo> found = manager.queryIntentActivities(probe, 0);
        if (found != null && !found.isEmpty()) {
            return true;
        }
        if (className == null) {
            return false;
        }
        try {
            return manager.getActivityInfo(new ComponentName(packageName, className), 0) != null;
        } catch (PackageManager.NameNotFoundException missing) {
            return false;
        }
    }
}
