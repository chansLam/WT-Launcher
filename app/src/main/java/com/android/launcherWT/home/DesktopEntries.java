package com.android.launcherWT.home;

import android.content.Context;

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

        Item(String key, String packageName, String className, String title, int iconRes, boolean speech) {
            this.key = key;
            this.packageName = packageName;
            this.className = className;
            this.title = title;
            this.iconRes = iconRes;
            this.speech = speech;
        }
    }

    private DesktopEntries() {
    }

    public static List<Item> source(Context context, int theme) {
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
            items.add(new Item(
                    entry.key(),
                    entry.packageName,
                    entry.className,
                    entry.title(),
                    entry.icon(theme),
                    "com.tencent.wecarspeech".equals(entry.packageName)));
        }
        InstalledApps installed = InstalledApps.load(context.getPackageManager());
        List<LaunchableApp> apps = installed.apps();
        for (int i = 0; i < apps.size(); i++) {
            LaunchableApp app = apps.get(i);
            if (reserved.contains(app.packageName)) {
                continue;
            }
            if (CarFilters.hidden(app.packageName)) {
                continue;
            }
            items.add(new Item(app.key(), app.packageName, app.className, app.label, 0, false));
        }
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
}
