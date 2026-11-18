package com.android.launcherWT.home;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.widget.Toast;

import com.android.launcherWT.R;
import com.autopai.common.utils.common.Utilities;

import java.util.List;

final class HomeLaunch {
    private HomeLaunch() {
    }

    static void startResolved(Context context, String packageName, String className) {
        if (className != null) {
            start(context, packageName, className);
            return;
        }
        PackageManager packages = context.getPackageManager();
        Intent probe = new Intent(Intent.ACTION_MAIN);
        probe.addCategory(Intent.CATEGORY_LAUNCHER);
        probe.setPackage(packageName);
        List<ResolveInfo> found = packages.queryIntentActivities(probe, 0);
        if (found == null || found.isEmpty() || found.get(0).activityInfo == null) {
            Toast.makeText(context, R.string.activity_not_found, Toast.LENGTH_SHORT).show();
            return;
        }
        start(context, found.get(0).activityInfo.packageName, found.get(0).activityInfo.name);
    }

    static void startSpeech(Context context, String packageName, String className, String typeKey, String typeValue) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, className));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        intent.putExtra(typeKey, typeValue);
        if (blockedBySafeMode(context, intent)) {
            return;
        }
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(context, R.string.activity_not_found, Toast.LENGTH_SHORT).show();
        } catch (SecurityException ex) {
            Toast.makeText(context, R.string.activity_not_found, Toast.LENGTH_SHORT).show();
        }
    }

    static void start(Context context, String packageName, String className) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(new ComponentName(packageName, className));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        if (blockedBySafeMode(context, intent)) {
            return;
        }
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(context, R.string.activity_not_found, Toast.LENGTH_SHORT).show();
        } catch (SecurityException ex) {
            Toast.makeText(context, R.string.activity_not_found, Toast.LENGTH_SHORT).show();
        }
    }

    /** The original refuses non system apps while the car runs in safe mode. */
    private static boolean blockedBySafeMode(Context context, Intent intent) {
        if (!context.getPackageManager().isSafeMode()) {
            return false;
        }
        if (Utilities.isSystemApp(context, intent)) {
            return false;
        }
        Toast.makeText(context, R.string.safe_mode_error, Toast.LENGTH_SHORT).show();
        return true;
    }
}
