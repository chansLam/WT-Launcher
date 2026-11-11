package com.android.launcherWT.wallpaper;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.android.launcherWT.core.BingDailyImage;
import com.android.launcherWT.core.WallpaperMode;
import com.android.launcherWT.core.WallpaperRules;
import com.android.launcherWT.store.DesktopDb;

import java.io.File;
import java.io.IOException;

public final class WallpaperApplier {
    private static final Object FILE_LOCK = new Object();
    private static final Object STATE_LOCK = new Object();
    private static final String NOT_UPDATED = "今天的壁纸没有更新";

    private WallpaperApplier() {
    }

    public static void apply(Context context, File file) throws IOException {
        synchronized (STATE_LOCK) {
            setBitmap(context, file);
        }
    }

    public static void guard(Runnable action) {
        synchronized (STATE_LOCK) {
            action.run();
        }
    }

    public static void refreshHome(Context context, DesktopDb database) {
        refresh(context, database, false);
    }

    public static boolean refreshSettings(Context context, DesktopDb database) {
        return refresh(context, database, true);
    }

    private static boolean refresh(Context context, DesktopDb database, boolean applyCached) {
        synchronized (FILE_LOCK) {
            if (database.wallpaperMode() != WallpaperMode.DAILY) {
                return false;
            }
            BingDailyImage image = BingDailyImage.parse(BingClient.fetchJson(BingClient.DAILY_URL));
            String remote = image == null ? null : image.startdate;
            boolean download = WallpaperRules.shouldDownload(
                    database.wallpaperMode(), database.dailyStartdate(), remote);
            File temp = null;
            boolean written = false;
            if (download && image != null) {
                File dir = wallpaperDir(context);
                if (dir == null) {
                    notifyNotUpdated(context);
                    return false;
                }
                temp = new File(dir, "daily.jpg.tmp");
                written = BingClient.download(image.imageUrl, temp);
            }
            synchronized (STATE_LOCK) {
                if (database.wallpaperMode() != WallpaperMode.DAILY) {
                    deleteQuietly(temp);
                    return false;
                }
                if (image == null) {
                    deleteQuietly(temp);
                    if (!applyCached) {
                        return false;
                    }
                    return applyCachedDaily(context);
                }
                boolean stillDownload = WallpaperRules.shouldDownload(
                        database.wallpaperMode(), database.dailyStartdate(), image.startdate);
                if (!stillDownload) {
                    deleteQuietly(temp);
                    if (!applyCached) {
                        return false;
                    }
                    return applyCachedDaily(context);
                }
                File dest = dailyFile(context);
                if (!written || !publish(temp, dest)) {
                    deleteQuietly(temp);
                    notifyNotUpdated(context);
                    return false;
                }
                try {
                    setBitmap(context, dest);
                } catch (Exception ex) {
                    restoreBackup(dest);
                    notifyNotUpdated(context);
                    return false;
                } catch (OutOfMemoryError error) {
                    restoreBackup(dest);
                    notifyNotUpdated(context);
                    return false;
                }
                deleteBackup(dest);
                if (database.wallpaperMode() == WallpaperMode.DAILY) {
                    database.setDailyStartdate(image.startdate);
                }
                return true;
            }
        }
    }

    private static boolean applyCachedDaily(Context context) {
        try {
            setBitmap(context, dailyFile(context));
            return true;
        } catch (Exception ex) {
            notifyNotUpdated(context);
            return false;
        } catch (OutOfMemoryError error) {
            notifyNotUpdated(context);
            return false;
        }
    }

    private static void setBitmap(Context context, File file) throws IOException {
        if (file == null || !file.isFile() || file.length() <= 0L) {
            throw new IOException("missing wallpaper");
        }
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), bounds);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize(
                bounds.outWidth, bounds.outHeight, metrics.widthPixels, metrics.heightPixels);
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        if (bitmap == null) {
            throw new IOException("undecodable wallpaper");
        }
        try {
            WallpaperManager.getInstance(context).setBitmap(bitmap);
        } finally {
            bitmap.recycle();
        }
    }

    private static boolean publish(File temp, File dest) {
        File dir = dest.getParentFile();
        if (dir == null) {
            deleteQuietly(temp);
            return false;
        }
        File backup = new File(dir, "daily.jpg.bak");
        if (backup.exists() && !backup.delete()) {
            deleteQuietly(temp);
            return false;
        }
        if (dest.exists() && !dest.renameTo(backup)) {
            deleteQuietly(temp);
            return false;
        }
        if (!temp.renameTo(dest)) {
            if (backup.exists()) {
                backup.renameTo(dest);
            }
            deleteQuietly(temp);
            return false;
        }
        if (!dest.isFile() || dest.length() <= 0L) {
            deleteQuietly(dest);
            if (backup.exists()) {
                backup.renameTo(dest);
            }
            return false;
        }
        return true;
    }

    private static void deleteBackup(File dest) {
        File dir = dest.getParentFile();
        if (dir == null) {
            return;
        }
        deleteQuietly(new File(dir, "daily.jpg.bak"));
    }

    private static void restoreBackup(File dest) {
        File dir = dest.getParentFile();
        if (dir == null) {
            return;
        }
        File backup = new File(dir, "daily.jpg.bak");
        if (!backup.exists()) {
            return;
        }
        File failed = new File(dir, "daily.jpg.failed");
        deleteQuietly(failed);
        if (dest.exists() && !dest.renameTo(failed)) {
            return;
        }
        if (!backup.renameTo(dest)) {
            if (!dest.exists()) {
                failed.renameTo(dest);
            }
            return;
        }
        deleteQuietly(failed);
    }

    private static int sampleSize(int width, int height, int targetWidth, int targetHeight) {
        int sample = 1;
        if (width <= 0 || height <= 0 || targetWidth <= 0 || targetHeight <= 0) {
            return sample;
        }
        while (sample <= Integer.MAX_VALUE / 2
                && (width > (long) targetWidth * sample || height > (long) targetHeight * sample)) {
            sample *= 2;
        }
        return sample;
    }

    private static File wallpaperDir(Context context) {
        File dir = new File(context.getFilesDir(), "wallpaper");
        if (dir.isDirectory() || dir.mkdirs()) {
            return dir;
        }
        return null;
    }

    private static File dailyFile(Context context) {
        return new File(context.getFilesDir(), "wallpaper/daily.jpg");
    }

    private static void deleteQuietly(File file) {
        if (file != null && file.exists()) {
            file.delete();
        }
    }

    private static void notifyNotUpdated(Context context) {
        final Context host = context.getApplicationContext() == null
                ? context
                : context.getApplicationContext();
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(host, NOT_UPDATED, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
