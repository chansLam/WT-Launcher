package com.android.launcherWT.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;

import com.android.launcherWT.core.BitmapSample;
import com.android.launcherWT.core.WallpaperMode;
import com.android.launcherWT.store.DesktopDb;
import com.android.launcherWT.wallpaper.WallpaperApplier;

import java.io.File;

public final class HomeBackground {
    private HomeBackground() {
    }

    public static void show(View view, DesktopDb database, int theme) {
        WallpaperMode mode = database.wallpaperMode();
        if (mode == WallpaperMode.CUSTOM) {
            if (showFile(view, file(database.customPath()))) {
                return;
            }
        } else if (mode == WallpaperMode.DAILY) {
            if (showFile(view, WallpaperApplier.dailyFile(view.getContext()))) {
                return;
            }
        }
        view.setBackgroundResource(FavoriteCatalog.background(theme));
    }

    public static File customFile(Context context) {
        File dir = new File(context.getFilesDir(), "wallpaper");
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
        return new File(dir, "custom.jpg");
    }

    private static boolean showFile(View view, File file) {
        Bitmap bitmap = decode(view.getContext(), file);
        if (bitmap == null) {
            return false;
        }
        BitmapDrawable drawable = new BitmapDrawable(view.getResources(), bitmap);
        drawable.setGravity(Gravity.FILL);
        view.setBackgroundDrawable(drawable);
        return true;
    }

    private static File file(String path) {
        if (path == null || path.length() == 0) {
            return null;
        }
        return new File(path);
    }

    private static Bitmap decode(Context context, File file) {
        if (file == null || !file.isFile() || file.length() <= 0L) {
            return null;
        }
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), bounds);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = BitmapSample.of(
                bounds.outWidth, bounds.outHeight, metrics.widthPixels, metrics.heightPixels);
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        return crop(bitmap, metrics.widthPixels, metrics.heightPixels);
    }

    /** Fill the 1920×720 board by cropping instead of stretching. */
    private static Bitmap crop(Bitmap source, int targetWidth, int targetHeight) {
        if (source == null) {
            return null;
        }
        int[] rect = com.android.launcherWT.core.AspectCrop.rect(
                source.getWidth(), source.getHeight(), targetWidth, targetHeight);
        if (rect[0] == 0 && rect[1] == 0
                && rect[2] == source.getWidth() && rect[3] == source.getHeight()) {
            return source;
        }
        Bitmap cropped = Bitmap.createBitmap(source, rect[0], rect[1], rect[2], rect[3]);
        if (cropped != source) {
            source.recycle();
        }
        return cropped;
    }
}
