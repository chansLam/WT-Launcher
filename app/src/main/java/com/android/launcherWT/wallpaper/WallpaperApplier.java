package com.android.launcherWT.wallpaper;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.android.launcherWT.core.DailyFeeds;
import com.android.launcherWT.core.DailyPick;
import com.android.launcherWT.core.ImageSearch;
import com.android.launcherWT.core.WallpaperMode;
import com.android.launcherWT.core.WallpaperRules;
import com.android.launcherWT.store.DesktopDb;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
            String requested = database.dailySource();
            boolean explicit = requested != null && requested.length() > 0;
            List<DailyPick> picks = explicit
                    ? java.util.Collections.singletonList(loadSource(context, requested))
                    : loadDaily(context);
            DailyPick chosen = DailyFeeds.select(picks, explicit ? requested : null, dayOfYear());
            boolean needsDownload = chosen != null && WallpaperRules.shouldDownload(
                    database.wallpaperMode(), database.dailyStartdate(), chosen.key);
            File temp = null;
            DailyPick saved = null;
            if (needsDownload) {
                File dir = wallpaperDir(context);
                if (dir == null) {
                    notifyNotUpdated(context);
                    return false;
                }
                int start = indexOf(picks, chosen);
                for (int n = 0; n < picks.size(); n++) {
                    if (explicit && n > 0) {
                        break;
                    }
                    DailyPick pick = picks.get((start + n) % picks.size());
                    if (pick == null || !WallpaperRules.shouldDownload(
                            WallpaperMode.DAILY, database.dailyStartdate(), pick.key)) {
                        continue;
                    }
                    File attempt = new File(dir, "daily.jpg.tmp");
                    if (BingClient.download(context, pick.imageUrl, attempt) && decodable(attempt)) {
                        temp = attempt;
                        saved = pick;
                        break;
                    }
                    deleteQuietly(attempt);
                }
            }
            synchronized (STATE_LOCK) {
                if (database.wallpaperMode() != WallpaperMode.DAILY) {
                    deleteQuietly(temp);
                    return false;
                }
                if (!needsDownload || chosen == null) {
                    deleteQuietly(temp);
                    if (explicit && chosen == null) {
                        notifyNotUpdated(context);
                        return false;
                    }
                    if (!applyCached) {
                        return false;
                    }
                    return applyCachedDaily(context);
                }
                if (!WallpaperRules.shouldDownload(
                        database.wallpaperMode(), database.dailyStartdate(), chosen.key)) {
                    deleteQuietly(temp);
                    if (!applyCached) {
                        return false;
                    }
                    return applyCachedDaily(context);
                }
                String current = database.dailySource();
                if (explicit && !requested.equals(current)) {
                    deleteQuietly(temp);
                    return false;
                }
                if (!explicit && current != null && current.length() > 0 && saved != null
                        && !current.equals(saved.source)) {
                    deleteQuietly(temp);
                    return false;
                }
                File dest = dailyFile(context);
                if (saved == null || !publish(temp, dest)) {
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
                    if (database.dailySource() == null || database.dailySource().length() == 0) {
                        database.setDailySource(saved.source);
                    }
                    database.setDailyStartdate(saved.key);
                    android.util.Log.i("WallpaperDaily", "saved " + saved.key);
                }
                return true;
            }
        }
    }

    private static int indexOf(List<DailyPick> picks, DailyPick chosen) {
        for (int i = 0; i < picks.size(); i++) {
            if (picks.get(i) == chosen) {
                return i;
            }
        }
        return 0;
    }

    private static DailyPick loadSource(Context context, String source) {
        if (DailyFeeds.BING_ZH.equals(source)) {
            return DailyFeeds.fromBing(BingClient.fetchJson(context, BingClient.DAILY_URL), DailyFeeds.BING_ZH);
        }
        if (DailyFeeds.BING_EN.equals(source)) {
            return DailyFeeds.fromBing(BingClient.fetchJson(context, BingClient.DAILY_URL_EN), DailyFeeds.BING_EN);
        }
        if (DailyFeeds.WALLHAVEN.equals(source)) {
            return DailyFeeds.fromWallhaven(BingClient.fetchJson(context, BingClient.DAILY_WALLHAVEN));
        }
        if (DailyFeeds.ZZZMH.equals(source)) {
            return zzzmhDaily(context);
        }
        if (DailyFeeds.PEAPIX.equals(source)) {
            return DailyFeeds.fromPeapix(BingClient.fetchJson(context, BingClient.PEAPIX_FEED));
        }
        if (DailyFeeds.TODAYBING.equals(source)) {
            return DailyFeeds.fromTodayBing(BingClient.fetchJson(context, BingClient.TODAYBING_TODAY));
        }
        if (DailyFeeds.UPX8.equals(source)) {
            return DailyFeeds.fromUpx8(BingClient.fetchJson(context, BingClient.UPX8_RANDOM), dayStamp());
        }
        if (DailyFeeds.YUMUS.equals(source)) {
            return DailyFeeds.fromYumus(BingClient.YUMUS_BING, dayStamp());
        }
        if (DailyFeeds.VSAA.equals(source)) {
            return DailyFeeds.fromVsaa(BingClient.fetchJson(context, BingClient.VSAA_DAILY), dayStamp());
        }
        return null;
    }

    private static List<DailyPick> loadDaily(Context context) {
        List<DailyPick> picks = new ArrayList<DailyPick>();
        // 顺序照 75Play 现场可用的兜底链：Peapix -> VSAA -> 必应 -> 语幕，再补国内可用源。
        addPick(picks, trySource(context, DailyFeeds.PEAPIX));
        addPick(picks, trySource(context, DailyFeeds.VSAA));
        addPick(picks, trySource(context, DailyFeeds.BING_ZH));
        addPick(picks, trySource(context, DailyFeeds.YUMUS));
        addPick(picks, trySource(context, DailyFeeds.BING_EN));
        addPick(picks, trySource(context, DailyFeeds.ZZZMH));
        addPick(picks, trySource(context, DailyFeeds.UPX8));
        addPick(picks, trySource(context, DailyFeeds.TODAYBING));
        addPick(picks, trySource(context, DailyFeeds.WALLHAVEN));
        StringBuilder names = new StringBuilder();
        for (int i = 0; i < picks.size(); i++) {
            if (names.length() > 0) {
                names.append(',');
            }
            names.append(picks.get(i).key);
        }
        android.util.Log.i("WallpaperDaily", "picks " + names);
        return picks;
    }

    private static DailyPick zzzmhDaily(Context context) {
        List<ImageSearch.ZzzCard> cards = ImageSearch.parseZzzmh(
                BingClient.fetchText(context, "https://bz.zzzmh.cn/gallery?r=ultra"));
        for (int i = 0; i < cards.size() && i < 3; i++) {
            ImageSearch.ZzzCard card = cards.get(i);
            String page = BingClient.fetchText(context,
                    "https://bz.zzzmh.cn/wallpaper/" + card.id + "?r=ultra");
            ImageSearch.Hit hit = ImageSearch.parseZzzmhDetail(page, card.thumbUrl);
            if (hit != null) {
                return DailyFeeds.fromZzzmh(card.id, hit.fullUrl);
            }
        }
        return null;
    }

    private static DailyPick trySource(Context context, String source) {
        DailyPick pick = loadSource(context, source);
        android.util.Log.i("WallpaperDaily", source + (pick == null ? " fail" : " ok " + pick.key));
        return pick;
    }

    private static void addPick(List<DailyPick> picks, DailyPick pick) {
        if (pick != null) {
            picks.add(pick);
        }
    }

    private static int dayOfYear() {
        return Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
    }

    /** YYYYMMDD, so one random pick is cached per day. */
    private static String dayStamp() {
        return new java.text.SimpleDateFormat("yyyyMMdd", java.util.Locale.US).format(new java.util.Date());
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
        bitmap = crop(bitmap, metrics.widthPixels, metrics.heightPixels);
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

    public static File dailyFile(Context context) {
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

    /** The car screen is 1920×720; leave the source un-stretched by cropping first. */
    private static Bitmap crop(Bitmap source, int targetWidth, int targetHeight) {
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

    /** WebP from some CDNs is unreadable on 4.4, so reject instead of publishing it. */
    private static boolean decodable(File file) {
        if (file == null || !file.isFile() || file.length() <= 0L) {
            return false;
        }
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), bounds);
        return bounds.outWidth > 0 && bounds.outHeight > 0;
    }
}
