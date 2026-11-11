package com.android.launcherWT;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.launcherWT.core.DesktopCatalog;
import com.android.launcherWT.core.LaunchableApp;
import com.android.launcherWT.core.WallpaperMode;
import com.android.launcherWT.home.AppGridView;
import com.android.launcherWT.home.CardPageView;
import com.android.launcherWT.home.HotseatView;
import com.android.launcherWT.home.InstalledApps;
import com.android.launcherWT.home.PageFlipper;
import com.android.launcherWT.store.DesktopDb;
import com.android.launcherWT.wallpaper.WallpaperApplier;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends Activity {
    private static final long CLOCK_PERIOD_MS = 30000L;

    private final Handler clockHandler = new Handler(Looper.getMainLooper());
    private final SimpleDateFormat clockFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private final Runnable clockTick = new Runnable() {
        @Override
        public void run() {
            updateClock();
            clockHandler.postDelayed(this, CLOCK_PERIOD_MS);
        }
    };

    private final BroadcastReceiver timeChanges = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            String action = intent.getAction();
            if (Intent.ACTION_DATE_CHANGED.equals(action)
                    || Intent.ACTION_TIME_CHANGED.equals(action)) {
                refreshDailyWallpaper();
            }
        }
    };

    private DesktopDb database;
    private PageFlipper pages;
    private CardPageView cardPage;
    private HotseatView hotseat;
    private LinearLayout dots;
    private TextView clock;
    private boolean timeChangesRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER);
        setContentView(R.layout.activity_main);

        database = new DesktopDb(this);
        pages = (PageFlipper) findViewById(R.id.pages);
        hotseat = (HotseatView) findViewById(R.id.hotseat);
        dots = (LinearLayout) findViewById(R.id.page_dots);
        clock = (TextView) findViewById(R.id.clock);
        cardPage = new CardPageView(this);
        cardPage.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        pages.addView(cardPage);

        findViewById(R.id.settings_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DesktopSettingsActivity.class));
            }
        });
        pages.setOnPageFling(new PageFlipper.OnPageFling() {
            @Override
            public void onMoveTowardCards() {
                showPage(pages.getDisplayedChild() + 1);
            }

            @Override
            public void onMoveTowardApps() {
                showPage(pages.getDisplayedChild() - 1);
            }
        });
        updateClock();
    }

    @Override
    protected void onStart() {
        super.onStart();
        cardPage.startListening();
        clockHandler.removeCallbacks(clockTick);
        clockTick.run();
        registerTimeChanges();
        applySelectedWallpaper();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadApps();
        hotseat.reload(database);
    }

    @Override
    protected void onStop() {
        unregisterTimeChanges();
        clockHandler.removeCallbacks(clockTick);
        cardPage.stopListening();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        clockHandler.removeCallbacks(clockTick);
        super.onDestroy();
    }

    private void reloadApps() {
        int previous = pages.getDisplayedChild();
        boolean onCards = pages.indexOfChild(cardPage) > 0
                && previous == pages.indexOfChild(cardPage);
        pages.setInAnimation(null);
        pages.setOutAnimation(null);
        pages.removeView(cardPage);
        pages.removeAllViews();

        InstalledApps installed = InstalledApps.load(getPackageManager());
        Set<String> hidden = new HashSet<String>();
        List<LaunchableApp> installedApps = installed.apps();
        for (int i = 0; i < installedApps.size(); i++) {
            LaunchableApp app = installedApps.get(i);
            if (database.isHidden(app.key())) {
                hidden.add(app.key());
            }
        }
        List<LaunchableApp> visible = DesktopCatalog.visible(installedApps, hidden);
        if (visible.isEmpty()) {
            pages.addView(gridFor(new ArrayList<LaunchableApp>(), installed));
        } else {
            for (int start = 0; start < visible.size(); start += AppGridView.PAGE_SIZE) {
                int end = Math.min(start + AppGridView.PAGE_SIZE, visible.size());
                List<LaunchableApp> page = new ArrayList<LaunchableApp>(visible.subList(start, end));
                pages.addView(gridFor(page, installed));
            }
        }
        pages.addView(cardPage);

        int last = pages.getChildCount() - 1;
        int target = onCards ? last : Math.min(previous, Math.max(0, last - 1));
        pages.setDisplayedChild(target);
        updateDots();
    }

    private AppGridView gridFor(List<LaunchableApp> apps, InstalledApps installed) {
        AppGridView grid = new AppGridView(this);
        grid.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        grid.bind(apps, installed);
        return grid;
    }

    private void showPage(int index) {
        int count = pages.getChildCount();
        if (index < 0 || index >= count) {
            return;
        }
        int current = pages.getDisplayedChild();
        if (index == current) {
            updateDots();
            return;
        }
        if (index > current) {
            pages.setInAnimation(slide(1f, 0f));
            pages.setOutAnimation(slide(0f, -1f));
        } else {
            pages.setInAnimation(slide(-1f, 0f));
            pages.setOutAnimation(slide(0f, 1f));
        }
        pages.setDisplayedChild(index);
        updateDots();
    }

    private void updateDots() {
        int count = pages.getChildCount();
        int current = pages.getDisplayedChild();
        if (dots.getChildCount() != count) {
            dots.removeAllViews();
            for (int i = 0; i < count; i++) {
                View dot = new View(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dp(8), dp(8));
                params.leftMargin = dp(4);
                params.rightMargin = dp(4);
                dot.setLayoutParams(params);
                dots.addView(dot);
            }
        }
        for (int i = 0; i < count; i++) {
            dots.getChildAt(i).setBackground(dotDrawable(i == current));
        }
    }

    private GradientDrawable dotDrawable(boolean active) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(active ? 0xFFFFFFFF : 0x80FFFFFF);
        drawable.setStroke(dp(1), 0x66000000);
        return drawable;
    }

    private void updateClock() {
        clock.setText(clockFormat.format(new Date()));
    }

    private void registerTimeChanges() {
        if (timeChangesRegistered) {
            return;
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_DATE_CHANGED);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        registerReceiver(timeChanges, filter);
        timeChangesRegistered = true;
    }

    private void unregisterTimeChanges() {
        if (!timeChangesRegistered) {
            return;
        }
        unregisterReceiver(timeChanges);
        timeChangesRegistered = false;
    }

    private void applySelectedWallpaper() {
        if (database.wallpaperMode() == WallpaperMode.CUSTOM) {
            applyCustomWallpaper();
            return;
        }
        refreshDailyWallpaper();
    }

    private void applyCustomWallpaper() {
        final File file = new File(getFilesDir(), "wallpaper/custom.jpg");
        new Thread(new Runnable() {
            @Override
            public void run() {
                WallpaperApplier.guard(new Runnable() {
                    @Override
                    public void run() {
                        if (database.wallpaperMode() != WallpaperMode.CUSTOM) {
                            return;
                        }
                        try {
                            WallpaperApplier.apply(MainActivity.this, file);
                        } catch (Exception ex) {
                            toastUnusable();
                        } catch (OutOfMemoryError error) {
                            toastUnusable();
                        }
                    }
                });
            }
        }, "custom-wallpaper").start();
    }

    private void toastUnusable() {
        final Context host = getApplicationContext() == null
                ? MainActivity.this
                : getApplicationContext();
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(host, R.string.wallpaper_unusable, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshDailyWallpaper() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WallpaperApplier.refreshHome(MainActivity.this, database);
            }
        }, "daily-wallpaper").start();
    }

    private Animation slide(float from, float to) {
        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, from,
                Animation.RELATIVE_TO_PARENT, to,
                Animation.RELATIVE_TO_PARENT, 0f,
                Animation.RELATIVE_TO_PARENT, 0f);
        animation.setDuration(180);
        return animation;
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
