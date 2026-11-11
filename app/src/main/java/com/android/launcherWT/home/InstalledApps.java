package com.android.launcherWT.home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.android.launcherWT.core.LaunchableApp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class InstalledApps {
    private final List<LaunchableApp> apps;
    private final Map<String, Drawable> icons;

    private InstalledApps(List<LaunchableApp> apps, Map<String, Drawable> icons) {
        this.apps = apps;
        this.icons = icons;
    }

    public static InstalledApps load(PackageManager packageManager) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolved = packageManager.queryIntentActivities(intent, 0);
        List<LaunchableApp> apps = new ArrayList<LaunchableApp>();
        Map<String, Drawable> icons = new LinkedHashMap<String, Drawable>();
        if (resolved != null) {
            for (int i = 0; i < resolved.size(); i++) {
                ResolveInfo info = resolved.get(i);
                if (info == null || info.activityInfo == null) {
                    continue;
                }
                String packageName = info.activityInfo.packageName;
                String className = info.activityInfo.name;
                if (packageName == null || className == null) {
                    continue;
                }
                LaunchableApp app = new LaunchableApp(packageName, className, labelOf(packageManager, info));
                if (icons.containsKey(app.key())) {
                    continue;
                }
                icons.put(app.key(), iconOf(packageManager, info));
                apps.add(app);
            }
        }
        return new InstalledApps(apps, icons);
    }

    public List<LaunchableApp> apps() {
        return Collections.unmodifiableList(apps);
    }

    public Drawable icon(LaunchableApp app) {
        Drawable icon = icons.get(app.key());
        if (icon != null) {
            return icon;
        }
        return defaultIcon();
    }

    private static String labelOf(PackageManager packageManager, ResolveInfo info) {
        CharSequence label = info.loadLabel(packageManager);
        if (label != null) {
            String text = label.toString().trim();
            if (text.length() > 0) {
                return text;
            }
        }
        return info.activityInfo.name;
    }

    private static Drawable iconOf(PackageManager packageManager, ResolveInfo info) {
        Drawable icon = info.loadIcon(packageManager);
        if (icon != null) {
            return icon;
        }
        return defaultIcon();
    }

    @SuppressWarnings("deprecation")
    private static Drawable defaultIcon() {
        return Resources.getSystem().getDrawable(android.R.drawable.sym_def_app_icon);
    }
}
