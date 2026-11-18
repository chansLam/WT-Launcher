package com.android.launcherWT.home;

import com.android.launcherWT.DesktopSettingsActivity;

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
import android.graphics.Typeface;
import android.util.Log;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.launcherWT.R;
import com.android.launcherWT.core.DesktopState;
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
    private int columns = DesktopState.DEFAULT_COLUMNS;
    private int rows = DesktopState.DEFAULT_ROWS;
    private int iconSizeDesign = DesktopState.DEFAULT_ICON_SIZE;
    private static final int COLOR_ACTIVE = 0xFFFFFFFF;
    private static final int COLOR_INACTIVE = 0x6FFFFFFF;
    private static final String SPEECH_TYPE = "type";
    private static final String SPEECH_SETTING = "SETTING";
    private static final long DRAG_HOLD_MS = 380L;

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
    private final List<DesktopEntries.Item> items = new ArrayList<DesktopEntries.Item>();
    private int pressedIndex = -1;
    private int draggedIndex = -1;
    private final Runnable dragArm = new Runnable() {
        @Override
        public void run() {
            beginItemDrag();
        }
    };
    private final OnTouchListener pressFeedback = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    view.animate().cancel();
                    view.animate().alpha(0.65f).setDuration(80).start();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    view.animate().cancel();
                    view.animate().alpha(1f).setDuration(120).start();
                    break;
                default:
                    break;
            }
            return false;
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
        columns = database.gridColumns();
        rows = database.gridRows();
        iconSizeDesign = database.iconSize();
        icons.clear();
        labels.clear();
        List<DesktopEntries.Item> source = DesktopEntries.source(getContext(), theme, database.displayOrder());
        Set<String> hidden = new HashSet<String>();
        for (int i = 0; i < source.size(); i++) {
            if (database.isHidden(source.get(i).key)) {
                hidden.add(source.get(i).key);
            }
        }
        InstalledApps installed = InstalledApps.load(getContext().getPackageManager());
        List<DesktopEntries.Item> visible = DesktopEntries.arrange(
                source, database.displayOrder(), hidden, false);
        items.clear();
        items.addAll(visible);
        for (int i = 0; i < visible.size(); i++) {
            final DesktopEntries.Item item = visible.get(i);
            addCell(item.title, iconOf(item, installed), new OnClickListener() {
                @Override
                public void onClick(View v) {
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
        if (item.settings) {
            getContext().startActivity(new Intent(getContext(), DesktopSettingsActivity.class));
            return;
        }
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
        label.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        label.setTypeface(Typeface.create("sans-serif-condensed", Typeface.NORMAL));
        cell.addView(label);
        cell.setOnTouchListener(pressFeedback);
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

    private int pageSize() {
        return Math.max(1, columns * rows);
    }

    private int viewHeight() {
        return getHeight() > 0 ? getHeight() : DESIGN_H;
    }

    private void layoutCells(int width, float sx, float sy) {
        int padLeft = Math.round(80 * sx);
        int padRight = Math.round(80 * sx);
        int padTop = Math.round(64 * sy);
        int padBottom = Math.round(84 * sy);
        int gridWidth = Math.max(1, width - padLeft - padRight);
        int cellWidth = gridWidth / Math.max(1, columns);
        int gridHeight = Math.max(1, viewHeight() - padTop - padBottom);
        int cellHeight = gridHeight / Math.max(1, rows);
        float iconScale = Math.max(0.5f, iconSizeDesign / (float) DesktopState.DEFAULT_ICON_SIZE);
        int iconSize = Math.round(iconSizeDesign * sx);
        int labelText = Math.round(28f * sy * Math.min(1.5f, iconScale));
        int labelGap = Math.round(8 * sy);
        for (int i = 0; i < cells.size(); i++) {
            int pageIndex = i / pageSize();
            int slot = i % pageSize();
            int column = slot % columns;
            int row = slot / columns;
            View cell = cells.get(i);
            int cellLeft = pageIndex * width + padLeft + column * cellWidth;
            int cellTop = padTop + row * cellHeight;
            cell.layout(cellLeft, cellTop, cellLeft + cellWidth, cellTop + cellHeight);

            TextView label = labels.get(i);
            label.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, labelText);
            label.measure(
                    MeasureSpec.makeMeasureSpec(cellWidth, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(cellHeight, MeasureSpec.AT_MOST));
            int labelHeight = label.getMeasuredHeight();
            int contentHeight = iconSize + labelGap + labelHeight;
            int contentTop = cellTop + Math.max(0, (cellHeight - contentHeight) / 2);

            ImageView icon = icons.get(i);
            int iconLeft = cellLeft + (cellWidth - iconSize) / 2;
            icon.layout(iconLeft, contentTop, iconLeft + iconSize, contentTop + iconSize);

            int labelTop = contentTop + iconSize + labelGap;
            int labelLeft = cellLeft + Math.max(0, (cellWidth - label.getMeasuredWidth()) / 2);
            label.layout(labelLeft, labelTop, labelLeft + label.getMeasuredWidth(),
                    labelTop + labelHeight);
        }
    }

    private int pageCount() {
        int count = (cells.size() + pageSize() - 1) / pageSize();
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
                pressedIndex = cellIndexAt(event.getX(), event.getY());
                armItemDrag();
                if (animator != null) {
                    animator.cancel();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (draggedIndex >= 0) {
                    trackDrag(event.getX(), event.getY());
                    return true;
                }
                float dx = event.getX() - downX;
                float dy = event.getY() - downY;
                if (movedPastSlop(dx, dy)) {
                    cancelItemDragArm();
                }
                if (Math.abs(dx) > touchSlop && Math.abs(dx) > Math.abs(dy)) {
                    dragging = true;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                cancelItemDragArm();
                if (draggedIndex >= 0) {
                    endItemDrag();
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }

    private void armItemDrag() {
        cancelItemDragArm();
        if (pressedIndex >= 0 && items.size() > pressedIndex && items.get(pressedIndex).settings) {
            return;
        }
        if (pressedIndex >= 0) {
            postDelayed(dragArm, DRAG_HOLD_MS);
        }
    }

    private void cancelItemDragArm() {
        removeCallbacks(dragArm);
    }

    private boolean movedPastSlop(float dx, float dy) {
        return Math.abs(dx) > touchSlop || Math.abs(dy) > touchSlop;
    }

    /** Insert style reorder: the dragged icon lands on a slot and the rest shift. */
    private void beginItemDrag() {
        if (pressedIndex < 0 || pressedIndex >= cells.size()) {
            return;
        }
        draggedIndex = pressedIndex;
        dragging = false;
        if (animator != null) {
            animator.cancel();
        }
        View cell = cells.get(draggedIndex);
        cell.animate().cancel();
        cell.animate().scaleX(1.12f).scaleY(1.12f).alpha(0.85f).setDuration(120).start();
    }

    private void trackDrag(float x, float y) {
        int target = cellIndexAt(x, y);
        if (target < 0 || target == draggedIndex) {
            return;
        }
        if (target < items.size() && items.get(target).settings) {
            target = Math.max(0, target - 1);
        }
        moveItem(draggedIndex, target);
        draggedIndex = target;
    }

    private void endItemDrag() {
        if (draggedIndex >= 0 && draggedIndex < cells.size()) {
            View cell = cells.get(draggedIndex);
            cell.animate().cancel();
            cell.animate().scaleX(1f).scaleY(1f).alpha(1f).setDuration(120).start();
        }
        draggedIndex = -1;
        pressedIndex = -1;
        saveOrder();
    }

    private void moveItem(int from, int to) {
        if (from == to || from < 0 || to < 0 || from >= items.size() || to >= items.size()) {
            return;
        }
        DesktopEntries.Item moved = items.remove(from);
        items.add(to, moved);
        View cell = cells.remove(from);
        cells.add(to, cell);
        icons.add(to, icons.remove(from));
        labels.add(to, labels.remove(from));
        requestLayout();
        invalidate();
    }

    private void saveOrder() {
        List<String> keys = new ArrayList<String>();
        for (int i = 0; i < items.size(); i++) {
            DesktopEntries.Item item = items.get(i);
            if (!item.settings) {
                keys.add(item.key);
            }
        }
        database.setDisplayOrder(keys);
    }

    private int cellIndexAt(float x, float y) {
        float boardX = x - board.getTranslationX();
        for (int i = 0; i < cells.size(); i++) {
            View cell = cells.get(i);
            if (boardX >= cell.getLeft() && boardX < cell.getRight()
                    && y >= cell.getTop() && y < cell.getBottom()) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                dragStart = board.getTranslationX();
                return true;
            case MotionEvent.ACTION_MOVE:
                if (draggedIndex >= 0) {
                    trackDrag(event.getX(), event.getY());
                    return true;
                }
                float dx = event.getX() - downX;
                float dy = event.getY() - downY;
                if (movedPastSlop(dx, dy)) {
                    cancelItemDragArm();
                    dragging = true;
                }
                board.setTranslationX(clamp(dragStart + dx));
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                cancelItemDragArm();
                if (draggedIndex >= 0) {
                    endItemDrag();
                    return true;
                }
                settle(event.getX() - downX);
                dragging = false;
                pressedIndex = -1;
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
