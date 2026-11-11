package com.android.launcherWT.home;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.launcherWT.R;

final class HomeLaunch {
    private HomeLaunch() {
    }

    static void start(Context context, String packageName, String className) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(new ComponentName(packageName, className));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(context, R.string.app_open_failed, Toast.LENGTH_SHORT).show();
        } catch (SecurityException ex) {
            Toast.makeText(context, R.string.app_open_failed, Toast.LENGTH_SHORT).show();
        }
    }
}
