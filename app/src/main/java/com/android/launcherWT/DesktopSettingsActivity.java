package com.android.launcherWT;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.launcherWT.core.DailyFeeds;
import com.android.launcherWT.core.ImageSearch;
import com.android.launcherWT.core.LaunchableApp;
import com.android.launcherWT.core.WallpaperMode;
import com.android.launcherWT.home.CarFilters;
import com.android.launcherWT.home.DesktopEntries;
import com.android.launcherWT.home.FavoriteCatalog;
import com.android.launcherWT.home.HomeBackground;
import com.android.launcherWT.home.InstalledApps;
import com.android.launcherWT.store.DesktopDb;
import com.android.launcherWT.wallpaper.BingClient;
import com.android.launcherWT.wallpaper.WallpaperApplier;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * One settings page with two halves: wallpaper on the left, app management on the right.
 */
public class DesktopSettingsActivity extends Activity {
    private static final int PICK_IMAGE = 41;

    private final Handler main = new Handler(Looper.getMainLooper());
    private final AtomicInteger searchGeneration = new AtomicInteger();

    private DesktopDb database;
    private InstalledApps installed;

    private RadioGroup wallpaperMode;
    private LinearLayout localPanel;
    private LinearLayout searchPanel;
    private LinearLayout dailyPanel;
    private ImageView preview;
    private EditText query;
    private TextView status;
    private TextView dailyStatus;
    private LinearLayout results;
    private Spinner sourceSpinner;
    private boolean adjustingRadios;
    private boolean adjustingSource;

    private LinearLayout desktopApps;
    private ScrollView desktopScroll;
    private LinearLayout availableApps;

    private View panelLayout;
    private View panelApps;
    private TextView tabLayout;
    private TextView tabApps;

    private LinearLayout previewGrid;
    private View previewArea;
    private TextView previewCaption;

    private final List<DesktopEntries.Item> desktopItems = new ArrayList<DesktopEntries.Item>();
    private final List<String> pendingOrder = new ArrayList<String>();
    private final Set<String> pendingHidden = new HashSet<String>();
    private int pendingColumns;
    private int pendingRows;
    private int pendingIconSize;
    private int currentTab = -1;
    private int dragIndex = -1;

    private static final String[] SOURCE_IDS = {
            "", DailyFeeds.BING_ZH, DailyFeeds.BING_EN, DailyFeeds.WALLHAVEN, DailyFeeds.ZZZMH,
            DailyFeeds.PEAPIX, DailyFeeds.TODAYBING, DailyFeeds.UPX8, DailyFeeds.YUMUS };

    private static final int[] SOURCE_LABELS = {
            R.string.wallpaper_source_auto, R.string.wallpaper_source_bing_zh,
            R.string.wallpaper_source_bing_en, R.string.wallpaper_source_wallhaven,
            R.string.wallpaper_source_zzzmh, R.string.wallpaper_source_peapix,
            R.string.wallpaper_source_todaybing, R.string.wallpaper_source_upx8,
            R.string.wallpaper_source_yumus };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        database = new DesktopDb(this);
        installed = InstalledApps.load(getPackageManager());

        wallpaperMode = (RadioGroup) findViewById(R.id.wallpaper_mode);
        localPanel = (LinearLayout) findViewById(R.id.local_panel);
        searchPanel = (LinearLayout) findViewById(R.id.search_panel);
        dailyPanel = (LinearLayout) findViewById(R.id.daily_panel);
        preview = (ImageView) findViewById(R.id.wallpaper_preview);
        query = (EditText) findViewById(R.id.search_query);
        status = (TextView) findViewById(R.id.search_status);
        dailyStatus = (TextView) findViewById(R.id.daily_status);
        results = (LinearLayout) findViewById(R.id.search_results);
        sourceSpinner = (Spinner) findViewById(R.id.daily_source);
        desktopApps = (LinearLayout) findViewById(R.id.desktop_apps);
        desktopScroll = (ScrollView) findViewById(R.id.desktop_apps_scroll);
        availableApps = (LinearLayout) findViewById(R.id.available_apps);

        panelLayout = (View) findViewById(R.id.panel_layout);
        panelApps = (View) findViewById(R.id.panel_apps);
        tabLayout = (TextView) findViewById(R.id.tab_layout);
        tabApps = (TextView) findViewById(R.id.tab_apps);
        previewArea = findViewById(R.id.preview_area);
        previewGrid = (LinearLayout) findViewById(R.id.preview_grid);
        previewCaption = (TextView) findViewById(R.id.preview_caption);

        tabLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTab(0);
            }
        });
        tabApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTab(1);
            }
        });
        findViewById(R.id.settings_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndReturn();
            }
        });

        bindDesktopLayout();
        bindWallpaper();
        bindApps();
        showMode();
        selectTab(0);
    }

    private void selectTab(int index) {
        if (currentTab == index) {
            return;
        }
        currentTab = index;
        TextView[] tabs = { tabLayout, tabApps };
        View[] panels = { panelLayout, panelApps };
        for (int i = 0; i < tabs.length; i++) {
            boolean selected = i == index;
            tabs[i].setBackgroundColor(selected ? 0x22FFFFFF : 0x00000000);
            tabs[i].setTextColor(selected ? 0xFFFFFFFF : 0x88FFFFFF);
            tabs[i].setTypeface(tabs[i].getTypeface(), selected
                    ? android.graphics.Typeface.BOLD : android.graphics.Typeface.NORMAL);
            panels[i].setVisibility(selected ? View.VISIBLE : View.GONE);
        }
        if (index == 0) {
            showPreview();
            updatePreview();
        }
    }

    // ------------------------------------------------------------ desktop grid

    private void bindDesktopLayout() {
        Spinner columns = (Spinner) findViewById(R.id.grid_columns);
        Spinner rows = (Spinner) findViewById(R.id.grid_rows);
        SeekBar iconSize = (SeekBar) findViewById(R.id.icon_size);
        TextView iconValue = (TextView) findViewById(R.id.icon_size_value);

        final int minColumns = DesktopDb.MIN_COLUMNS;
        final int maxColumns = DesktopDb.MAX_COLUMNS;
        String[] columnLabels = new String[maxColumns - minColumns + 1];
        for (int i = 0; i < columnLabels.length; i++) {
            columnLabels[i] = Integer.toString(minColumns + i);
        }
        ArrayAdapter<String> columnAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, columnLabels);
        columnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        columns.setAdapter(columnAdapter);
        pendingColumns = database.gridColumns();
        columns.setSelection(clampIndex(pendingColumns - minColumns, columnLabels.length));

        final int minRows = DesktopDb.MIN_ROWS;
        final int maxRows = DesktopDb.MAX_ROWS;
        String[] rowLabels = new String[maxRows - minRows + 1];
        for (int i = 0; i < rowLabels.length; i++) {
            rowLabels[i] = Integer.toString(minRows + i);
        }
        ArrayAdapter<String> rowAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, rowLabels);
        rowAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rows.setAdapter(rowAdapter);
        pendingRows = database.gridRows();
        rows.setSelection(clampIndex(pendingRows - minRows, rowLabels.length));

        final int minIcon = DesktopDb.MIN_ICON;
        pendingIconSize = database.iconSize();
        iconSize.setMax(DesktopDb.MAX_ICON - minIcon);
        iconSize.setProgress(pendingIconSize - minIcon);
        iconValue.setText(Integer.toString(pendingIconSize));

        columns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pendingColumns = minColumns + position;
                updatePreview();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        rows.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pendingRows = minRows + position;
                updatePreview();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        iconSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pendingIconSize = minIcon + progress;
                iconValue.setText(Integer.toString(pendingIconSize));
                updatePreview();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        updatePreview();
    }

    /** Miniature of the whole board: wallpaper underneath, columns x rows cells on top. */
    private void updatePreview() {
        if (previewGrid == null || previewCaption == null) {
            return;
        }
        View box = previewGrid;
        int boxWidth = box.getWidth();
        int boxHeight = box.getHeight();
        if (boxWidth <= 0 || boxHeight <= 0) {
            box.post(new Runnable() {
                @Override
                public void run() {
                    updatePreview();
                }
            });
            return;
        }
        int columns = Math.max(1, pendingColumns);
        int rows = Math.max(1, pendingRows);
        float scaleX = boxWidth / 1920f;
        float scaleY = boxHeight / 720f;

        // Same margins as the real board, so the preview shows where icons can sit.
        int padLeft = Math.round(80f * scaleX);
        int padRight = padLeft;
        int padTop = Math.round(64f * scaleY);
        int padBottom = Math.round(84f * scaleY);
        previewGrid.setPadding(padLeft, padTop, padRight, padBottom);

        int innerWidth = Math.max(1, boxWidth - padLeft - padRight);
        int innerHeight = Math.max(1, boxHeight - padTop - padBottom);
        int cellWidth = innerWidth / columns;
        int cellHeight = innerHeight / rows;
        int labelPx = Math.max(9, Math.round(28f * scaleY));
        int gap = Math.max(1, Math.round(6f * scaleY));
        int iconPx = Math.round(pendingIconSize * scaleX);
        int maxIcon = cellHeight - labelPx - gap;
        iconPx = Math.min(iconPx, Math.max(10, maxIcon));
        iconPx = Math.min(iconPx, cellWidth - gap);

        previewGrid.removeAllViews();
        for (int r = 0; r < rows; r++) {
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER);
            row.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f));
            for (int c = 0; c < columns; c++) {
                LinearLayout cell = new LinearLayout(this);
                cell.setOrientation(LinearLayout.VERTICAL);
                cell.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
                cell.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT, 1f));

                ImageView sample = new ImageView(this);
                sample.setScaleType(ImageView.ScaleType.FIT_CENTER);
                sample.setImageDrawable(sampleIcon(r * columns + c));
                sample.setLayoutParams(new LinearLayout.LayoutParams(iconPx, iconPx));

                TextView name = new TextView(this);
                name.setText(R.string.preview_label);
                name.setTextColor(0xFFFFFFFF);
                name.setSingleLine(true);
                name.setTypeface(android.graphics.Typeface.create("sans-serif-condensed",
                        android.graphics.Typeface.NORMAL));
                name.setTextSize(TypedValue.COMPLEX_UNIT_PX, labelPx);
                LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                nameParams.topMargin = gap;
                name.setLayoutParams(nameParams);

                cell.addView(sample);
                cell.addView(name);
                row.addView(cell);
            }
            previewGrid.addView(row);
        }
        previewCaption.setText(getString(R.string.preview_caption_format,
                pendingColumns, pendingRows, pendingIconSize));
    }

    /** Classic-theme icons, cycled, so the preview looks like the real desktop. */
    private static final int[] SAMPLE_ICONS = {
            R.drawable.classic_icon_map, R.drawable.classic_icon_radio,
            R.drawable.classic_icon_airconditioner, R.drawable.classic_icon_phone,
            R.drawable.classic_icon_eryanet_link, R.drawable.classic_icon_vehicle_setting,
            R.drawable.classic_icon_car_dvr_360, R.drawable.classic_icon_system_settings,
            R.drawable.classic_icon_comfort_cockpit, R.drawable.classic_icon_file_manager,
            R.drawable.ic_apps_icon_call_center, R.drawable.classic_icon_speech
    };

    @SuppressWarnings("deprecation")
    private Drawable sampleIcon(int index) {
        try {
            int res = SAMPLE_ICONS[Math.abs(index) % SAMPLE_ICONS.length];
            return getResources().getDrawable(res);
        } catch (Exception ex) {
            return getResources().getDrawable(android.R.drawable.sym_def_app_icon);
        }
    }

    private static int clampIndex(int value, int size) {
        if (value < 0) {
            return 0;
        }
        return value >= size ? size - 1 : value;
    }

    // ---------------------------------------------------------------- wallpaper

    private void bindWallpaper() {
        wallpaperMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (adjustingRadios) {
                    return;
                }
                if (checkedId == R.id.mode_default) {
                    setMode(WallpaperMode.DEFAULT);
                } else if (checkedId == R.id.mode_daily) {
                    setMode(WallpaperMode.DAILY);
                    refreshDaily();
                } else if (checkedId == R.id.mode_local) {
                    showPanels(WallpaperMode.CUSTOM);
                } else if (checkedId == R.id.mode_search) {
                    showPanels(WallpaperMode.CUSTOM);
                }
                showPreview();
            }
        });
        findViewById(R.id.pick_local).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickLocalImage();
            }
        });
        findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        ArrayAdapter<String> sources = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, labels());
        sources.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceSpinner.setAdapter(sources);
        sourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (adjustingSource || position < 0 || position >= SOURCE_IDS.length) {
                    return;
                }
                database.setDailySource(SOURCE_IDS[position]);
                if (database.wallpaperMode() == WallpaperMode.DAILY) {
                    refreshDaily();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private String[] labels() {
        String[] values = new String[SOURCE_LABELS.length];
        for (int i = 0; i < SOURCE_LABELS.length; i++) {
            values[i] = getString(SOURCE_LABELS[i]);
        }
        return values;
    }

    private void setMode(WallpaperMode mode) {
        database.setWallpaperMode(mode);
        showPanels(mode);
    }

    private void showMode() {
        WallpaperMode mode = database.wallpaperMode();
        adjustingRadios = true;
        if (mode == WallpaperMode.DAILY) {
            wallpaperMode.check(R.id.mode_daily);
        } else if (mode == WallpaperMode.CUSTOM) {
            wallpaperMode.check(R.id.mode_local);
        } else {
            wallpaperMode.check(R.id.mode_default);
        }
        adjustingRadios = false;
        showPanels(mode);
        showSource();
        showPreview();
    }

    private void showPanels(WallpaperMode mode) {
        localPanel.setVisibility(mode == WallpaperMode.CUSTOM && !searchSelected()
                ? View.VISIBLE : View.GONE);
        dailyPanel.setVisibility(mode == WallpaperMode.DAILY ? View.VISIBLE : View.GONE);
        searchPanel.setVisibility(mode == WallpaperMode.CUSTOM && searchSelected()
                ? View.VISIBLE : View.GONE);
    }

    private boolean searchSelected() {
        return wallpaperMode.getCheckedRadioButtonId() == R.id.mode_search;
    }

    private void showSource() {
        String source = database.dailySource();
        int index = 0;
        for (int i = 0; i < SOURCE_IDS.length; i++) {
            if (SOURCE_IDS[i].equals(source == null ? "" : source)) {
                index = i;
                break;
            }
        }
        adjustingSource = true;
        sourceSpinner.setSelection(index);
        adjustingSource = false;
    }

    private void showPreview() {
        HomeBackground.show(preview, database, FavoriteCatalog.theme(this));
    }

    private void refreshDaily() {
        dailyStatus.setText(R.string.wallpaper_daily_loading);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final boolean applied = WallpaperApplier.refreshSettings(DesktopSettingsActivity.this, database);
                main.post(new Runnable() {
                    @Override
                    public void run() {
                        if (isFinishing()) {
                            return;
                        }
                        if (database.wallpaperMode() == WallpaperMode.DAILY) {
                            if (applied) {
                                dailyStatus.setText("");
                            } else {
                                dailyStatus.setText(R.string.wallpaper_unusable);
                            }
                            showPreview();
                        }
                    }
                });
            }
        }, "daily-wallpaper").start();
    }

    private void pickLocalImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(intent, PICK_IMAGE);
        } catch (Exception ex) {
            Toast.makeText(this, R.string.wallpaper_unusable, Toast.LENGTH_SHORT).show();
        }
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

    private boolean copyLocal(Uri uri) {
        File dest = HomeBackground.customFile(this);
        if (dest.getParentFile() != null && !dest.getParentFile().isDirectory()) {
            dest.getParentFile().mkdirs();
        }
        File temp = new File(dest.getParentFile(), "custom.part");
        InputStream input = null;
        FileOutputStream output = null;
        try {
            input = getContentResolver().openInputStream(uri);
            if (input == null) {
                return false;
            }
            output = new FileOutputStream(temp);
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
        } catch (Exception ex) {
            return false;
        } finally {
            close(output);
            close(input);
        }
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(temp.getAbsolutePath(), bounds);
        if (bounds.outWidth <= 0 || bounds.outHeight <= 0) {
            temp.delete();
            return false;
        }
        if (dest.exists()) {
            dest.delete();
        }
        return temp.renameTo(dest);
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
                String bingHtml = BingClient.fetchText(DesktopSettingsActivity.this,
                        "https://www.bing.com/images/async?q=" + encoded
                                + "&first=0&count=35&mmasync=1&adlt=strict"
                                + "&qft=%2Bfilterui:aspect-wide%2Bfilterui:imagesize-large");
                String wallhaven = BingClient.fetchText(DesktopSettingsActivity.this,
                        "https://wallhaven.cc/api/v1/search?q=" + encoded
                                + "&purity=100&atleast=1920x720&ratios=16x9,21x9&sorting=relevance");
                String zzzHtml = BingClient.fetchText(DesktopSettingsActivity.this,
                        "https://bz.zzzmh.cn/search?q=" + encoded + "&r=ultra");
                String upxJson = BingClient.fetchText(DesktopSettingsActivity.this,
                        "https://wp.upx8.com/api.php?format=json&content=" + encoded + "&count=3");
                List<ImageSearch.Hit> bingHits = ImageSearch.parseBing(bingHtml);
                List<ImageSearch.Hit> havenHits = ImageSearch.parseWallhaven(wallhaven);
                List<ImageSearch.Hit> zzzHits = zzzmhHits(zzzHtml);
                List<ImageSearch.Hit> upxHits = ImageSearch.parseUpx8(upxJson);
                List<List<ImageSearch.Hit>> groups = new ArrayList<List<ImageSearch.Hit>>();
                groups.add(bingHits);
                groups.add(havenHits);
                groups.add(zzzHits);
                groups.add(upxHits);
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
                if (!file.isFile() && !BingClient.download(DesktopSettingsActivity.this, url, file)) {
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
                File dest = HomeBackground.customFile(DesktopSettingsActivity.this);
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
                        adjustingRadios = true;
                        wallpaperMode.check(R.id.mode_local);
                        adjustingRadios = false;
                        showPanels(WallpaperMode.CUSTOM);
                    }
                });
            }
        }).start();
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
        return part.renameTo(dest);
    }

    // ------------------------------------------------------------- app manager

    private void bindApps() {
        pendingOrder.clear();
        List<String> stored = database.displayOrder();
        if (stored != null) {
            pendingOrder.addAll(stored);
        }
        pendingHidden.clear();
        List<DesktopEntries.Item> source = DesktopEntries.source(this, FavoriteCatalog.theme(this),
                pendingOrder);
        for (int i = 0; i < source.size(); i++) {
            if (database.isHidden(source.get(i).key)) {
                pendingHidden.add(source.get(i).key);
            }
        }
        rebuildItems();
    }

    private void rebuildItems() {
        desktopItems.clear();
        List<DesktopEntries.Item> source = DesktopEntries.source(this, FavoriteCatalog.theme(this),
                pendingOrder);
        List<DesktopEntries.Item> visible = DesktopEntries.arrange(source, pendingOrder,
                pendingHidden, false);
        for (int i = 0; i < visible.size(); i++) {
            if (!visible.get(i).settings) {
                desktopItems.add(visible.get(i));
            }
        }
        renderDesktopItems();
        bindAvailable();
    }

    private void renderDesktopItems() {
        desktopApps.removeAllViews();
        for (int i = 0; i < desktopItems.size(); i++) {
            desktopApps.addView(desktopRow(i));
        }
    }

    private View desktopRow(final int index) {
        final DesktopEntries.Item item = desktopItems.get(index);
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        int pad = dp(6);
        row.setPadding(pad, pad, pad, pad);

        ImageView icon = new ImageView(this);
        icon.setImageDrawable(iconOf(item));
        row.addView(icon, new LinearLayout.LayoutParams(dp(36), dp(36)));

        TextView name = new TextView(this);
        name.setText(item.title);
        name.setTextColor(0xFFFFFFFF);
        name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        name.setSingleLine(true);
        name.setEllipsize(TextUtils.TruncateAt.END);
        name.setPadding(dp(10), 0, dp(10), 0);
        row.addView(name, new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

        TextView handle = new TextView(this);
        handle.setText("≡");
        handle.setTextColor(0x99FFFFFF);
        handle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        handle.setPadding(dp(10), 0, dp(10), 0);
        row.addView(handle, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        handle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return drag(row, event);
            }
        });

        TextView remove = new TextView(this);
        remove.setText(R.string.app_remove);
        remove.setTextColor(0xCCFF6B6B);
        remove.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        remove.setPadding(dp(10), dp(4), dp(10), dp(4));
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pendingHidden.add(item.key);
                rebuildItems();
            }
        });
        row.addView(remove, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return row;
    }

    private boolean drag(View row, MotionEvent event) {
        int index = desktopApps.indexOfChild(row);
        if (index < 0) {
            return false;
        }
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                dragIndex = index;
                row.setBackgroundColor(0x33FFFFFF);
                desktopScroll.requestDisallowInterceptTouchEvent(true);
                return true;
            case MotionEvent.ACTION_MOVE:
                if (dragIndex < 0) {
                    return true;
                }
                int target = indexAt(event.getRawY());
                if (target >= 0 && target != dragIndex) {
                    moveRow(dragIndex, target);
                    dragIndex = target;
                }
                autoScroll(event.getRawY());
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                row.setBackgroundColor(0x00000000);
                dragIndex = -1;
                syncPendingOrder();
                return true;
            default:
                return false;
        }
    }

    private void moveRow(int from, int to) {
        DesktopEntries.Item item = desktopItems.remove(from);
        desktopItems.add(to, item);
        View row = desktopApps.getChildAt(from);
        desktopApps.removeViewAt(from);
        desktopApps.addView(row, to);
        desktopApps.invalidate();
    }

    private int indexAt(float rawY) {
        int closest = dragIndex;
        float best = Float.MAX_VALUE;
        int[] location = new int[2];
        for (int i = 0; i < desktopApps.getChildCount(); i++) {
            View child = desktopApps.getChildAt(i);
            child.getLocationOnScreen(location);
            float center = location[1] + child.getHeight() / 2f;
            float distance = Math.abs(rawY - center);
            if (distance < best) {
                best = distance;
                closest = i;
            }
        }
        return closest;
    }

    private void autoScroll(float rawY) {
        int[] location = new int[2];
        desktopScroll.getLocationOnScreen(location);
        if (rawY < location[1] + dp(60)) {
            desktopScroll.scrollBy(0, -dp(12));
        } else if (rawY > location[1] + desktopScroll.getHeight() - dp(60)) {
            desktopScroll.scrollBy(0, dp(12));
        }
    }

    private void syncPendingOrder() {
        pendingOrder.clear();
        for (int i = 0; i < desktopItems.size(); i++) {
            pendingOrder.add(desktopItems.get(i).key);
        }
    }

    private void bindAvailable() {
        availableApps.removeAllViews();
        Set<String> onDesktop = new HashSet<String>();
        for (int i = 0; i < desktopItems.size(); i++) {
            onDesktop.add(desktopItems.get(i).key);
        }
        List<LaunchableApp> apps = installed.apps();
        for (int i = 0; i < apps.size(); i++) {
            final LaunchableApp app = apps.get(i);
            if (onDesktop.contains(app.key())
                    || getPackageName().equals(app.packageName)
                    || CarFilters.hidden(app.packageName)) {
                continue;
            }
            availableApps.addView(availableRow(app));
        }
    }

    private View availableRow(final LaunchableApp app) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        int pad = dp(6);
        row.setPadding(pad, pad, pad, pad);

        ImageView icon = new ImageView(this);
        icon.setImageDrawable(installed.icon(app));
        row.addView(icon, new LinearLayout.LayoutParams(dp(36), dp(36)));

        TextView name = new TextView(this);
        name.setText(app.label);
        name.setTextColor(0xFFFFFFFF);
        name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        name.setSingleLine(true);
        name.setEllipsize(TextUtils.TruncateAt.END);
        name.setPadding(dp(10), 0, dp(10), 0);
        row.addView(name, new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

        TextView add = new TextView(this);
        add.setText(R.string.app_add);
        add.setTextColor(0xCC7FD4FF);
        add.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        add.setPadding(dp(10), dp(4), dp(10), dp(4));
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToDesktop(app);
            }
        });
        row.addView(add, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return row;
    }

    private void addToDesktop(LaunchableApp app) {
        pendingHidden.remove(app.key());
        pendingOrder.remove(app.key());
        pendingOrder.add(app.key());
        rebuildItems();
    }

    @SuppressWarnings("deprecation")
    private Drawable iconOf(DesktopEntries.Item item) {
        if (item.iconRes != 0) {
            return getResources().getDrawable(item.iconRes);
        }
        return installed.icon(new LaunchableApp(item.packageName, item.className, item.title));
    }

    private void saveAndReturn() {
        database.setGridColumns(pendingColumns);
        database.setGridRows(pendingRows);
        database.setIconSize(pendingIconSize);
        syncPendingOrder();
        database.setDisplayOrder(pendingOrder);
        Set<String> hidden = new HashSet<String>(pendingHidden);
        for (int i = 0; i < desktopItems.size(); i++) {
            hidden.remove(desktopItems.get(i).key);
        }
        for (String key : pendingHidden) {
            database.setHidden(key, true);
        }
        for (String key : hidden) {
            database.setHidden(key, false);
        }
        for (int i = 0; i < desktopItems.size(); i++) {
            database.setHidden(desktopItems.get(i).key, false);
        }
        Toast.makeText(this, R.string.settings_saved, Toast.LENGTH_SHORT).show();
        Intent home = new Intent(this, MainActivity.class);
        home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        try {
            startActivity(home);
        } catch (Exception ex) {
            // LauncherWT is not the current home task; still leave settings.
        }
        finish();
    }

    private static void close(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException ex) {
        }
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
