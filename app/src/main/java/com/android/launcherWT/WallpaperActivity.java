package com.android.launcherWT;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.launcherWT.core.DailyFeeds;
import com.android.launcherWT.core.ImageSearch;
import com.android.launcherWT.core.WallpaperMode;
import com.android.launcherWT.home.HomeBackground;
import com.android.launcherWT.store.DesktopDb;
import com.android.launcherWT.wallpaper.BingClient;
import com.android.launcherWT.wallpaper.WallpaperApplier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class WallpaperActivity extends Activity {
    private static final int PICK_IMAGE = 41;

    private final Handler main = new Handler(Looper.getMainLooper());
    private final AtomicInteger searchGeneration = new AtomicInteger();

    private DesktopDb database;
    private ImageView preview;
    private LinearLayout customPanel;
    private LinearLayout dailyPanel;
    private LinearLayout results;
    private TextView status;
    private TextView dailyStatus;
    private EditText query;
    private boolean updatingRadios;
    private boolean updatingSources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);
        database = new DesktopDb(this);
        preview = (ImageView) findViewById(R.id.wallpaper_preview);
        customPanel = (LinearLayout) findViewById(R.id.custom_panel);
        dailyPanel = (LinearLayout) findViewById(R.id.daily_panel);
        results = (LinearLayout) findViewById(R.id.search_results);
        status = (TextView) findViewById(R.id.search_status);
        dailyStatus = (TextView) findViewById(R.id.daily_status);
        query = (EditText) findViewById(R.id.search_query);
        RadioGroup modes = (RadioGroup) findViewById(R.id.wallpaper_mode);
        modes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (updatingRadios) {
                    return;
                }
                if (checkedId == R.id.mode_custom) {
                    database.setWallpaperMode(WallpaperMode.CUSTOM);
                } else if (checkedId == R.id.mode_daily) {
                    database.setWallpaperMode(WallpaperMode.DAILY);
                    showMode();
                    refreshDaily();
                    return;
                } else {
                    database.setWallpaperMode(WallpaperMode.DEFAULT);
                }
                showMode();
            }
        });
        RadioGroup sources = (RadioGroup) findViewById(R.id.daily_source);
        sources.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (updatingSources) {
                    return;
                }
                String source = sourceId(checkedId);
                if (source == null || source.equals(database.dailySource())) {
                    return;
                }
                database.setDailySource(source);
                if (database.wallpaperMode() == WallpaperMode.DAILY) {
                    refreshDaily();
                }
            }
        });
        findViewById(R.id.pick_local).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                try {
                    startActivityForResult(intent, PICK_IMAGE);
                } catch (Exception ex) {
                    Toast.makeText(WallpaperActivity.this, R.string.wallpaper_unusable, Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
        findViewById(R.id.wallpaper_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showMode();
    }

    private void showMode() {
        WallpaperMode mode = database.wallpaperMode();
        updatingRadios = true;
        RadioGroup modes = (RadioGroup) findViewById(R.id.wallpaper_mode);
        if (mode == WallpaperMode.CUSTOM) {
            modes.check(R.id.mode_custom);
        } else if (mode == WallpaperMode.DAILY) {
            modes.check(R.id.mode_daily);
        } else {
            modes.check(R.id.mode_default);
        }
        updatingRadios = false;
        customPanel.setVisibility(mode == WallpaperMode.CUSTOM ? View.VISIBLE : View.GONE);
        dailyPanel.setVisibility(mode == WallpaperMode.DAILY ? View.VISIBLE : View.GONE);
        if (mode == WallpaperMode.DAILY) {
            showDailySource();
        }
        HomeBackground.show(preview, database, com.android.launcherWT.home.FavoriteCatalog.theme(this));
    }

    private void showDailySource() {
        String source = database.dailySource();
        if (source == null || source.length() == 0) {
            source = DailyFeeds.sourceOf(database.dailyStartdate());
            if (radioFor(source) != -1) {
                database.setDailySource(source);
            }
        }
        updatingSources = true;
        RadioGroup sources = (RadioGroup) findViewById(R.id.daily_source);
        int radio = radioFor(source);
        if (radio == -1) {
            sources.clearCheck();
        } else {
            sources.check(radio);
        }
        updatingSources = false;
    }

    private static String sourceId(int checkedId) {
        if (checkedId == R.id.source_bing_zh) {
            return DailyFeeds.BING_ZH;
        }
        if (checkedId == R.id.source_bing_en) {
            return DailyFeeds.BING_EN;
        }
        if (checkedId == R.id.source_wallhaven) {
            return DailyFeeds.WALLHAVEN;
        }
        if (checkedId == R.id.source_zzzmh) {
            return DailyFeeds.ZZZMH;
        }
        return null;
    }

    private static int radioFor(String source) {
        if (DailyFeeds.BING_ZH.equals(source)) {
            return R.id.source_bing_zh;
        }
        if (DailyFeeds.BING_EN.equals(source)) {
            return R.id.source_bing_en;
        }
        if (DailyFeeds.WALLHAVEN.equals(source)) {
            return R.id.source_wallhaven;
        }
        if (DailyFeeds.ZZZMH.equals(source)) {
            return R.id.source_zzzmh;
        }
        return -1;
    }

    private void refreshDaily() {
        dailyStatus.setText(R.string.wallpaper_daily_loading);
        new Thread(new Runnable() {
            @Override
            public void run() {
                WallpaperApplier.refreshSettings(WallpaperActivity.this, database);
                main.post(new Runnable() {
                    @Override
                    public void run() {
                        if (database.wallpaperMode() == WallpaperMode.DAILY) {
                            dailyStatus.setText("");
                            showMode();
                        }
                    }
                });
            }
        }).start();
    }

    private void search() {
        final String text = query.getText().toString().trim();
        if (text.length() == 0) {
            Toast.makeText(this, R.string.wallpaper_search_hint, Toast.LENGTH_SHORT).show();
            return;
        }
        final int generation = searchGeneration.incrementAndGet();
        status.setText(R.string.wallpaper_searching);
        results.removeAllViews();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String encoded = text;
                try {
                    encoded = URLEncoder.encode(text, "UTF-8");
                } catch (Exception ex) {
                    encoded = text;
                }
                String bingHtml = BingClient.fetchText(WallpaperActivity.this,
                        "https://www.bing.com/images/async?q=" + encoded
                                + "&first=0&count=35&mmasync=1&adlt=strict"
                                + "&qft=%2Bfilterui:aspect-wide%2Bfilterui:imagesize-large");
                String wallhaven = BingClient.fetchText(WallpaperActivity.this,
                        "https://wallhaven.cc/api/v1/search?q=" + encoded
                                + "&purity=100&atleast=1920x720&ratios=16x9,21x9&sorting=relevance");
                String zzzHtml = BingClient.fetchText(WallpaperActivity.this,
                        "https://bz.zzzmh.cn/search?q=" + encoded + "&r=ultra");
                List<ImageSearch.Hit> bingHits = ImageSearch.parseBing(bingHtml);
                List<ImageSearch.Hit> havenHits = ImageSearch.parseWallhaven(wallhaven);
                List<ImageSearch.Hit> zzzHits = zzzmhHits(zzzHtml);
                android.util.Log.i("WallpaperSearch", "bing=" + bingHits.size()
                        + " wallhaven=" + havenHits.size() + " zzzmh=" + zzzHits.size());
                List<List<ImageSearch.Hit>> groups = new ArrayList<List<ImageSearch.Hit>>();
                groups.add(bingHits);
                groups.add(havenHits);
                groups.add(zzzHits);
                final List<ImageSearch.Hit> hits = ImageSearch.mergeAll(groups);
                main.post(new Runnable() {
                    @Override
                    public void run() {
                        if (generation != searchGeneration.get()) {
                            return;
                        }
                        if (hits.isEmpty()) {
                            status.setText(R.string.wallpaper_search_empty);
                            return;
                        }
                        status.setText("");
                        showHits(hits);
                    }
                });
            }
        }).start();
    }

    private List<ImageSearch.Hit> zzzmhHits(String html) {
        List<ImageSearch.Hit> hits = new ArrayList<ImageSearch.Hit>();
        List<ImageSearch.ZzzCard> cards = ImageSearch.parseZzzmh(html);
        for (int i = 0; i < cards.size() && hits.size() < 4; i++) {
            ImageSearch.ZzzCard card = cards.get(i);
            String page = BingClient.fetchText(this,
                    "https://bz.zzzmh.cn/wallpaper/" + card.id + "?r=ultra");
            ImageSearch.Hit hit = ImageSearch.parseZzzmhDetail(page, card.thumbUrl);
            if (hit != null) {
                hits.add(hit);
            }
        }
        return hits;
    }

    private void showHits(List<ImageSearch.Hit> hits) {
        results.removeAllViews();
        for (int i = 0; i < hits.size(); i++) {
            final ImageSearch.Hit hit = hits.get(i);
            final ImageView thumb = new ImageView(this);
            int size = Math.round(120 * getResources().getDisplayMetrics().density);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            params.rightMargin = Math.round(8 * getResources().getDisplayMetrics().density);
            thumb.setLayoutParams(params);
            thumb.setScaleType(ImageView.ScaleType.CENTER_CROP);
            thumb.setBackgroundColor(0x33FFFFFF);
            results.addView(thumb);
            thumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    useRemote(hit);
                }
            });
            String preview = ImageSearch.bingImage(hit.thumbUrl, 240, 240);
            loadThumb(thumb, preview != null ? preview : hit.thumbUrl);
        }
    }

    private void loadThumb(final ImageView view, final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(getCacheDir(), "thumb-" + Math.abs(url.hashCode()) + ".img");
                if (!file.isFile() && !BingClient.download(WallpaperActivity.this, url, file)) {
                    return;
                }
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                if (bitmap == null) {
                    file.delete();
                    return;
                }
                final Bitmap shown = bitmap;
                main.post(new Runnable() {
                    @Override
                    public void run() {
                        view.setImageBitmap(shown);
                    }
                });
            }
        }).start();
    }

    private void useRemote(final ImageSearch.Hit hit) {
        status.setText(R.string.wallpaper_saving);
        new Thread(new Runnable() {
            @Override
            public void run() {
                File dest = HomeBackground.customFile(WallpaperActivity.this);
                boolean saved = storeImage(hit.fullUrl, dest);
                if (!saved) {
                    saved = storeImage(ImageSearch.bingImage(hit.thumbUrl, 1920, 720), dest);
                }
                final boolean ok = saved && dest.isFile() && dest.length() > 0L;
                main.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!ok) {
                            status.setText(R.string.wallpaper_unusable);
                            return;
                        }
                        database.setCustomPath(dest.getAbsolutePath());
                        database.setWallpaperMode(WallpaperMode.CUSTOM);
                        status.setText("");
                        showMode();
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != PICK_IMAGE || resultCode != RESULT_OK || data == null || data.getData() == null) {
            return;
        }
        if (!copyLocal(data.getData())) {
            Toast.makeText(this, R.string.wallpaper_unusable, Toast.LENGTH_SHORT).show();
            return;
        }
        database.setWallpaperMode(WallpaperMode.CUSTOM);
        showMode();
    }

    private boolean storeImage(String url, File dest) {
        if (url == null || url.length() == 0) {
            return false;
        }
        File part = new File(dest.getParentFile(), "custom.part");
        if (part.exists()) {
            part.delete();
        }
        if (!BingClient.download(this, url, part)) {
            part.delete();
            return false;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(part.getAbsolutePath(), options);
        if (options.outWidth < 1280 || options.outHeight < 720) {
            part.delete();
            return false;
        }
        if (dest.exists()) {
            dest.delete();
        }
        if (!part.renameTo(dest) && !copyFile(part, dest)) {
            part.delete();
            return false;
        }
        if (part.exists()) {
            part.delete();
        }
        return dest.isFile() && dest.length() > 0L;
    }

    private boolean copyLocal(Uri uri) {
        InputStream input = null;
        FileOutputStream output = null;
        try {
            input = getContentResolver().openInputStream(uri);
            if (input == null) {
                return false;
            }
            File dest = HomeBackground.customFile(this);
            output = new FileOutputStream(dest);
            byte[] buffer = new byte[8192];
            int count;
            long total = 0L;
            while ((count = input.read(buffer)) != -1) {
                output.write(buffer, 0, count);
                total += count;
            }
            output.flush();
            if (total <= 0L) {
                return false;
            }
            database.setCustomPath(dest.getAbsolutePath());
            return true;
        } catch (Exception ex) {
            return false;
        } finally {
            close(input);
            close(output);
        }
    }

    private static boolean copyFile(File from, File to) {
        InputStream input = null;
        FileOutputStream output = null;
        try {
            input = new java.io.FileInputStream(from);
            output = new FileOutputStream(to);
            byte[] buffer = new byte[8192];
            int count;
            while ((count = input.read(buffer)) != -1) {
                output.write(buffer, 0, count);
            }
            output.flush();
            return to.isFile() && to.length() > 0L;
        } catch (Exception ex) {
            return false;
        } finally {
            close(input);
            close(output);
        }
    }

    private static void close(java.io.Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (Exception ex) {
        }
    }
}
