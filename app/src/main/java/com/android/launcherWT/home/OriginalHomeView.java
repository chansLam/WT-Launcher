package com.android.launcherWT.home;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.launcherWT.AppManageActivity;
import com.android.launcherWT.R;
import com.android.launcherWT.WallpaperActivity;
import com.android.launcherWT.core.LaunchableApp;
import com.android.launcherWT.core.WallpaperMode;
import com.android.launcherWT.store.DesktopDb;
import com.autopai.applist.manager.DataStatisticsManager;
import com.autopai.applist.model.config.AppsConfig;
import com.incall.apps.service.setting.IOnThemeChange;
import com.incall.apps.service.setting.SettingServiceManager;
import com.android.launcherWT.status.StatusCluster;
import com.android.launcherWT.wallpaper.WallpaperApplier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Home screen on a 1920x720 page: five columns by three rows,
 * with the day-theme status cluster at the top right.
 */
public class OriginalHomeView extends FrameLayout {
    private static final int DESIGN_W = 1920;
    private static final int DESIGN_H = 720;
    private static final int COLUMNS = 5;
    private static final int ROWS = 3;
    private static final int PAGE_SIZE = COLUMNS * ROWS;
    private static final int COLOR_ACTIVE = 0xFFFFFFFF;
    private static final int COLOR_INACTIVE = 0x6FFFFFFF;
    private static final String SPEECH_TYPE = "type";
    private static final String SPEECH_SETTING = "SETTING";

    private final List<View> cells = new ArrayList<View>();
    private final List<ImageView> icons = new ArrayList<ImageView>();
    private final List<TextView> labels = new ArrayList<TextView>();
    private final Paint indicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final int touchSlop;

    private Board board;
    private StatusCluster status;
    private int theme = FavoriteCatalog.THEME_CLASSIC;
    private int page;
    private float downX;
    private float downY;
    private float dragStart;
    private boolean dragging;
    private ValueAnimator animator;
    private DesktopDb database;
    private int wallpaperTicket;
    private boolean ignoreClick;
    private boolean longPressed;
    private final Runnable longPressRunnable = new Runnable() {
        @Override
        public void run() {
            if (dragging) {
                return;
            }
            longPressed = true;
            showMenu();
        }
    };

    private boolean packagesRegistered;
    private IOnThemeChange themeListener;
    private boolean themeRegistered;

    private final BroadcastReceiver packageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            reloadCells();
        }
    };

    public OriginalHomeView(Context context) {
        this(context, null);
    }

    public OriginalHomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        theme = FavoriteCatalog.theme(context);
        database = new DesktopDb(context);
        indicatorPaint.setStrokeCap(Paint.Cap.ROUND);
        indicatorPaint.setStyle(Paint.Style.STROKE);
        setWillNotDraw(false);
        try {
            DataStatisticsManager.initSdk(context);
        } catch (Throwable unavailable) {
            Log.i("OriginalHomeView", "statistics sdk unavailable: " + unavailable);
        }

        board = new Board(context);
        addView(board);
        reload();
        status = StatusCluster.create(getContext());
        addView(status.view());
    }

    public void reload() {
        theme = FavoriteCatalog.theme(getContext());
        reloadCells();
        applyBackground();
    }

    private static final class Board extends ViewGroup {
        Board(Context context) {
            super(context);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        }
    }

    private void reloadCells() {
        board.removeAllViews();
        cells.clear();
        icons.clear();
        labels.clear();
        List<DesktopEntries.Item> source = DesktopEntries.source(getContext(), theme);
        Set<String> hidden = new HashSet<String>();
        for (int i = 0; i < source.size(); i++) {
            if (database.isHidden(source.get(i).key)) {
                hidden.add(source.get(i).key);
            }
        }
        InstalledApps installed = InstalledApps.load(getContext().getPackageManager());
        List<DesktopEntries.Item> visible = DesktopEntries.arrange(
                source, database.displayOrder(), hidden, false);
        for (int i = 0; i < visible.size(); i++) {
            final DesktopEntries.Item item = visible.get(i);
            addCell(item.title, iconOf(item, installed), new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ignoreClick) {
                        return;
                    }
                    open(item);
                }
            });
        }
        if (page > pageCount() - 1) {
            page = Math.max(0, pageCount() - 1);
        }
        requestLayout();
        invalidate();
    }

    @SuppressWarnings("deprecation")
    private Drawable iconOf(DesktopEntries.Item item, InstalledApps installed) {
        if (item.iconRes != 0) {
            return getResources().getDrawable(item.iconRes);
        }
        return installed.icon(new LaunchableApp(item.packageName, item.className, item.title));
    }

    private void open(DesktopEntries.Item item) {
        DataStatisticsManager.sendEvents(getContext(), item.packageName, null);
        if (item.speech) {
            if (!speechReady()) {
                Toast.makeText(getContext(), R.string.speech_content, Toast.LENGTH_SHORT).show();
                return;
            }
            HomeLaunch.startSpeech(getContext(), item.packageName, item.className, SPEECH_TYPE, SPEECH_SETTING);
            return;
        }
        HomeLaunch.startResolved(getContext(), item.packageName, item.className);
    }

    /** wt_speech_ready is missing until the assistant is installed. */
    private boolean speechReady() {
        try {
            return Settings.System.getInt(getContext().getContentResolver(),
                    AppsConfig.WT_SPEECH_READY) == 1;
        } catch (Settings.SettingNotFoundException missing) {
            return true;
        }
    }

    private void applyBackground() {
        final int ticket = ++wallpaperTicket;
        final WallpaperMode mode = database.wallpaperMode();
        HomeBackground.show(this, database, theme);
        if (mode != WallpaperMode.DAILY) {
            return;
        }
        final Context context = getContext().getApplicationContext();
        new Thread(new Runnable() {
            @Override
            public void run() {
                WallpaperApplier.refreshHome(context, database);
                post(new Runnable() {
                    @Override
                    public void run() {
                        if (ticket == wallpaperTicket && database.wallpaperMode() == WallpaperMode.DAILY) {
                            HomeBackground.show(OriginalHomeView.this, database, theme);
                        }
                    }
                });
            }
        }).start();
    }

    private void showMenu() {
        ignoreClick = true;
        Context context = getContext();
        if (!(context instanceof android.app.Activity)) {
            return;
        }
        View content = LayoutInflater.from(context).inflate(R.layout.dialog_home_menu, null);
        final AlertDialog dialog = new AlertDialog.Builder(
                new ContextThemeWrapper(context, android.R.style.Theme_Holo_Dialog)).setView(content).create();
        content.findViewById(R.id.menu_apps).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                getContext().startActivity(new Intent(getContext(), AppManageActivity.class));
            }
        });
        content.findViewById(R.id.menu_wallpaper).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                getContext().startActivity(new Intent(getContext(), WallpaperActivity.class));
            }
        });
        dialog.setOnDismissListener(new android.content.DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(android.content.DialogInterface d) {
                post(new Runnable() {
                    @Override
                    public void run() {
                        ignoreClick = false;
                    }
                });
            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        if (window != null) {
            int width = Math.round(280 * context.getResources().getDisplayMetrics().density);
            window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
        }
    }

    @SuppressWarnings("deprecation")
    private void addCell(String title, Drawable icon, OnClickListener click) {
        LinearLayout cell = new LinearLayout(getContext());
        cell.setOrientation(LinearLayout.VERTICAL);
        cell.setGravity(Gravity.CENTER_HORIZONTAL);

        ImageView image = new ImageView(getContext());
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        image.setImageDrawable(icon);
        cell.addView(image);

        TextView label = new TextView(getContext());
        label.setText(title);
        label.setTextColor(Color.WHITE);
        label.setGravity(Gravity.CENTER_HORIZONTAL);
        label.setSingleLine(true);
        cell.addView(label);
        cell.setOnClickListener(click);

        cells.add(cell);
        icons.add(image);
        labels.add(label);
        board.addView(cell);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!packagesRegistered) {
            IntentFilter packages = new IntentFilter();
            packages.addAction(Intent.ACTION_PACKAGE_ADDED);
            packages.addAction(Intent.ACTION_PACKAGE_REMOVED);
            packages.addAction(Intent.ACTION_PACKAGE_CHANGED);
            packages.addDataScheme("package");
            getContext().registerReceiver(packageReceiver, packages);
            packagesRegistered = true;
        }
        if (!themeRegistered) {
            registerThemeChange();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if (packagesRegistered) {
            getContext().unregisterReceiver(packageReceiver);
            packagesRegistered = false;
        }
        cancelLongPressWatch();
        unregisterThemeChange();
        super.onDetachedFromWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int boardWidth = MeasureSpec.makeMeasureSpec(width * pageCount(), MeasureSpec.EXACTLY);
        int boardHeight = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        board.measure(boardWidth, boardHeight);
        for (int i = 0; i < cells.size(); i++) {
            cells.get(i).measure(boardWidth, boardHeight);
        }
        status.view().measure(
                MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST),
                MeasureSpec.makeMeasureSpec(status.heightPx(), MeasureSpec.EXACTLY));
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int width = right - left;
        int height = bottom - top;
        float sx = width / (float) DESIGN_W;
        float sy = height / (float) DESIGN_H;
        board.layout(0, 0, width * pageCount(), height);
        layoutCells(width, sx, sy);
        View cluster = status.view();
        int clusterTop = status.topMarginPx();
        cluster.layout(width - cluster.getMeasuredWidth(), clusterTop,
                width, clusterTop + cluster.getMeasuredHeight());
        if (!dragging && (animator == null || !animator.isRunning())) {
            board.setTranslationX(-page * width);
        }
    }

    private void layoutCells(int width, float sx, float sy) {
        int padLeft = Math.round(80 * sx);
        int padTop = Math.round(64 * sy);
        int itemWidth = Math.round(264 * sx);
        int itemHeight = Math.round(176 * sy);
        int insetLeft = Math.round(56 * sx);
        int stride = insetLeft + itemWidth + Math.round(32 * sx);
        int iconSize = Math.round(96 * sx);
        int iconTop = Math.round(16 * sy);
        int labelGap = Math.round(10 * sy);
        int rowGap = Math.round(20 * sy);
        for (int i = 0; i < cells.size(); i++) {
            int pageIndex = i / PAGE_SIZE;
            int slot = i % PAGE_SIZE;
            int column = slot % COLUMNS;
            int row = slot / COLUMNS;
            int cellLeft = pageIndex * width + padLeft + column * stride + insetLeft;
            int cellTop = padTop + row * (itemHeight + rowGap);
            View cell = cells.get(i);
            cell.layout(cellLeft, cellTop, cellLeft + itemWidth, cellTop + itemHeight);
            ImageView icon = icons.get(i);
            int iconLeft = (itemWidth - iconSize) / 2;
            icon.layout(iconLeft, iconTop, iconLeft + iconSize, iconTop + iconSize);
            TextView label = labels.get(i);
            label.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, 28f * sy);
            int labelTop = iconTop + iconSize + labelGap;
            label.measure(
                    MeasureSpec.makeMeasureSpec(itemWidth, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(itemHeight - labelTop, MeasureSpec.AT_MOST));
            int labelWidth = label.getMeasuredWidth();
            int labelLeft = Math.max(0, (itemWidth - labelWidth) / 2);
            label.layout(labelLeft, labelTop, labelLeft + labelWidth, labelTop + label.getMeasuredHeight());
        }
    }

    private int pageCount() {
        int count = (cells.size() + PAGE_SIZE - 1) / PAGE_SIZE;
        return Math.max(1, count);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                dragStart = board.getTranslationX();
                dragging = false;
                armLongPress();
                if (animator != null) {
                    animator.cancel();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = event.getX() - downX;
                float dy = event.getY() - downY;
                if (movedPastSlop(dx, dy)) {
                    cancelLongPressWatch();
                }
                if (Math.abs(dx) > touchSlop && Math.abs(dx) > Math.abs(dy)) {
                    dragging = true;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                cancelLongPressWatch();
                if (longPressed) {
                    longPressed = false;
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }

    private void armLongPress() {
        longPressed = false;
        cancelLongPressWatch();
        postDelayed(longPressRunnable, ViewConfiguration.getLongPressTimeout());
    }

    private void cancelLongPressWatch() {
        removeCallbacks(longPressRunnable);
    }

    private boolean movedPastSlop(float dx, float dy) {
        return Math.abs(dx) > touchSlop || Math.abs(dy) > touchSlop;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                dragStart = board.getTranslationX();
                return true;
            case MotionEvent.ACTION_MOVE:
                float dx = event.getX() - downX;
                float dy = event.getY() - downY;
                if (movedPastSlop(dx, dy)) {
                    cancelLongPressWatch();
                    dragging = true;
                }
                board.setTranslationX(clamp(dragStart + dx));
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                cancelLongPressWatch();
                if (!longPressed) {
                    settle(event.getX() - downX);
                }
                dragging = false;
                longPressed = false;
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    private void settle(float dx) {
        int width = getWidth();
        int target = page;
        if (width > 0 && dx <= -width * 0.2f) {
            target = Math.min(pageCount() - 1, page + 1);
        } else if (width > 0 && dx >= width * 0.2f) {
            target = Math.max(0, page - 1);
        }
        animateTo(target);
    }

    private void animateTo(final int target) {
        final float end = -target * getWidth();
        if (animator != null) {
            animator.cancel();
        }
        animator = ValueAnimator.ofFloat(board.getTranslationX(), end);
        animator.setDuration(200);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                board.setTranslationX((Float) animation.getAnimatedValue());
                invalidate();
            }
        });
        animator.start();
        page = target;
    }

    private float clamp(float translation) {
        float min = -(pageCount() - 1) * getWidth();
        if (translation < min) {
            return min;
        }
        if (translation > 0f) {
            return 0f;
        }
        return translation;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawIndicator(canvas);
    }

    private void drawIndicator(Canvas canvas) {
        int count = pageCount();
        if (count <= 1 || getWidth() == 0) {
            return;
        }
        float sx = getWidth() / (float) DESIGN_W;
        float sy = getHeight() / (float) DESIGN_H;
        float longLength = 100f * sx;
        float shortLength = 50f * sx;
        float gap = 50f * sx;
        float y = getHeight() - 48f * sy;
        indicatorPaint.setStrokeWidth(Math.max(1f, 4f * sy));
        float progress = 0f;
        int current = page;
        if (getWidth() > 0) {
            float offset = -board.getTranslationX() / getWidth();
            current = (int) Math.floor(offset);
            if (current < 0) {
                current = 0;
            }
            if (current > count - 1) {
                current = count - 1;
            }
            progress = offset - current;
            if (progress < 0f) {
                progress = 0f;
            }
        }
        float total = longLength + shortLength * (count - 1) + gap * Math.max(0, count - 1);
        float start = (getWidth() - total) / 2f;
        for (int i = 0; i < count; i++) {
            float length = shortLength;
            int color = COLOR_INACTIVE;
            if (progress == 0f) {
                if (i == current) {
                    length = longLength;
                    color = COLOR_ACTIVE;
                }
            } else if (i == current) {
                length = longLength - shortLength * progress;
                color = blend(COLOR_ACTIVE, COLOR_INACTIVE, progress);
            } else if (i == current + 1) {
                length = shortLength + (longLength - shortLength) * progress;
                color = blend(COLOR_INACTIVE, COLOR_ACTIVE, progress);
            }
            indicatorPaint.setColor(color);
            canvas.drawLine(start, y, start + length, y, indicatorPaint);
            start += length + gap;
        }
    }

    private static int blend(int from, int to, float amount) {
        float clamped = Math.max(0f, Math.min(1f, amount));
        int a = mix(Color.alpha(from), Color.alpha(to), clamped);
        int r = mix(Color.red(from), Color.red(to), clamped);
        int g = mix(Color.green(from), Color.green(to), clamped);
        int b = mix(Color.blue(from), Color.blue(to), clamped);
        return Color.argb(a, r, g, b);
    }

    private static int mix(int from, int to, float amount) {
        return Math.round(from + (to - from) * amount);
    }

    /** Theme switch is pushed by the setting service, not polled on resume. */
    private void registerThemeChange() {
        try {
            themeListener = new IOnThemeChange.Stub() {
                @Override
                public void onThemeChange(int type) {
                    post(new Runnable() {
                        @Override
                        public void run() {
                            reload();
                        }
                    });
                }
            };
            SettingServiceManager.getInstance().registerThemeChangeListener(themeListener);
            themeRegistered = true;
        } catch (Throwable unavailable) {
            themeListener = null;
            Log.i("OriginalHomeView", "theme service unavailable: " + unavailable);
        }
    }

    private void unregisterThemeChange() {
        if (!themeRegistered || themeListener == null) {
            return;
        }
        try {
            SettingServiceManager.getInstance().unRegisterThemeChangeListener(themeListener);
        } catch (Throwable unavailable) {
            Log.i("OriginalHomeView", "theme service unregister failed: " + unavailable);
        }
        themeRegistered = false;
        themeListener = null;
    }
}
