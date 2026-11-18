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
    private static final long DRAG_HOLD_MS = 300L;

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
    private int dragTarget = -1;

    private float grabDx;
    private float grabDy;
    private boolean itemDrag;
    private final Paint slotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint ghostTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Drawable ghostIcon;
    private String ghostLabel;
    private int ghostIconWidth;
    private int ghostIconHeight;
    private int ghostCellWidth;
    private float ghostTextSize;
    private float ghostX;
    private float ghostY;
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
        private int pageWidth;
        private int columns = DesktopState.DEFAULT_COLUMNS;
        private int rows = DesktopState.DEFAULT_ROWS;
        private int padLeft;
        private int padRight;
        private int padTop;
        private int padBottom;
        private int pages = 1;

        Board(Context context) {
            super(context);
        }

        void configure(int pageWidth, int columns, int rows, int pages,
                int padLeft, int padRight, int padTop, int padBottom) {
            this.pageWidth = pageWidth;
            this.columns = Math.max(1, columns);
            this.rows = Math.max(1, rows);
            this.pages = Math.max(1, pages);
            this.padLeft = padLeft;
            this.padRight = padRight;
            this.padTop = padTop;
            this.padBottom = padBottom;
        }

        int cellWidth() {
            return Math.max(1, (pageWidth - padLeft - padRight) / columns);
        }

        int cellHeight() {
            return Math.max(1, (getHeight() - padTop - padBottom) / rows);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            setMeasuredDimension(width, height);
            int cellWidth = cellWidth();
            int cellHeight = Math.max(1, (height - padTop - padBottom) / rows);
            int specW = MeasureSpec.makeMeasureSpec(cellWidth, MeasureSpec.EXACTLY);
            int specH = MeasureSpec.makeMeasureSpec(cellHeight, MeasureSpec.EXACTLY);
            for (int i = 0; i < getChildCount(); i++) {
                getChildAt(i).measure(specW, specH);
            }
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            int cellWidth = cellWidth();
            int perPage = columns * rows;
            int cellHeight = Math.max(1, (bottom - top - padTop - padBottom) / rows);
            for (int i = 0; i < getChildCount(); i++) {
                int page = i / perPage;
                int slot = i % perPage;
                int column = slot % columns;
                int row = slot / columns;
                int cellLeft = page * pageWidth + padLeft + column * cellWidth;
                int cellTop = padTop + row * cellHeight;
                getChildAt(i).layout(cellLeft, cellTop, cellLeft + cellWidth, cellTop + cellHeight);
            }
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
        int viewWidth = getWidth() > 0 ? getWidth() : DESIGN_W;
        int viewHeight = getHeight() > 0 ? getHeight() : DESIGN_H;
        float scaleX = viewWidth / (float) DESIGN_W;
        float scaleY = viewHeight / (float) DESIGN_H;
        int iconSizePx = Math.round(iconSizeDesign * scaleX);
        int labelPx = Math.round(28f * scaleY);
        int cellH = Math.round((DESIGN_H - 148f) * scaleY / Math.max(1, rows));
        int maxIcon = cellH - labelPx - Math.round(8f * scaleY);
        int iconPx = Math.min(iconSizePx, Math.max(Math.round(48f * scaleX), maxIcon));

        LinearLayout cell = new LinearLayout(getContext());
        cell.setOrientation(LinearLayout.VERTICAL);
        cell.setGravity(Gravity.CENTER);

        ImageView image = new ImageView(getContext());
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        image.setImageDrawable(icon);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(iconPx, iconPx);
        imageParams.gravity = Gravity.CENTER_HORIZONTAL;
        image.setLayoutParams(imageParams);
        cell.addView(image);

        TextView label = new TextView(getContext());
        label.setText(title);
        label.setTextColor(Color.WHITE);
        label.setGravity(Gravity.CENTER_HORIZONTAL);
        label.setSingleLine(true);
        label.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        label.setTypeface(Typeface.create("sans-serif-condensed", Typeface.NORMAL));
        label.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, labelPx);
        LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        labelParams.gravity = Gravity.CENTER_HORIZONTAL;
        labelParams.topMargin = Math.round(8 * scaleY);
        label.setLayoutParams(labelParams);
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
        int padLeft = Math.round(80 * (width / (float) DESIGN_W));
        int padRight = padLeft;
        int padTop = Math.round(64 * (height / (float) DESIGN_H));
        int padBottom = Math.round(84 * (height / (float) DESIGN_H));
        board.configure(width, columns, rows, pageCount(), padLeft, padRight, padTop, padBottom);
        int boardWidth = MeasureSpec.makeMeasureSpec(width * pageCount(), MeasureSpec.EXACTLY);
        int boardHeight = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        board.measure(boardWidth, boardHeight);
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
        View cluster = status.view();
        int clusterTop = status.topMarginPx();
        cluster.layout(width - cluster.getMeasuredWidth(), clusterTop,
                width, clusterTop + cluster.getMeasuredHeight());
        if (!dragging && !itemDrag && (animator == null || !animator.isRunning())) {
            board.setTranslationX(-page * width);
        }
    }

    private int pageSize() {
        return Math.max(1, columns * rows);
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
                if (!movedPastSlop(dx, dy)) {
                    break;
                }
                // A vertical drag that starts on an icon reorders it (insert style) instead of
                // paging; only a clearly horizontal gesture is treated as a page swipe.
                if (Math.abs(dy) > Math.abs(dx) && pressedIndex >= 0) {
                    cancelItemDragArm();
                    beginItemDrag();
                    trackDrag(event.getX(), event.getY());
                    return true;
                }
                cancelItemDragArm();
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
        dragTarget = pressedIndex;
        itemDrag = true;
        dragging = false;
        if (animator != null) {
            animator.cancel();
        }
        View cell = cells.get(draggedIndex);
        cell.animate().cancel();
        cell.setAlpha(0.22f);
        // Lift a copy of the icon and let it follow the finger; the original slot is left
        // as a dimmed placeholder until the finger is released.
        View iconView = icons.get(draggedIndex);
        ghostIcon = ((ImageView) iconView).getDrawable();
        ghostIconWidth = Math.max(1, iconView.getWidth());
        ghostIconHeight = Math.max(1, iconView.getHeight());
        ghostCellWidth = Math.max(1, cell.getWidth());
        ghostLabel = labels.get(draggedIndex).getText().toString();
        ghostTextSize = labels.get(draggedIndex).getTextSize();
        ghostTextPaint.setTextSize(ghostTextSize);
        ghostTextPaint.setColor(Color.WHITE);
        ghostTextPaint.setTypeface(Typeface.create("sans-serif-condensed", Typeface.NORMAL));
        ghostTextPaint.setTextAlign(Paint.Align.CENTER);
        float cellLeft = cell.getLeft() + board.getTranslationX();
        float cellTop = cell.getTop();
        grabDx = downX - cellLeft;
        grabDy = downY - cellTop;
        ghostX = cellLeft;
        ghostY = cellTop;
        invalidate();
    }

    private void trackDrag(float x, float y) {
        ghostX = x - grabDx;
        ghostY = y - grabDy;
        invalidate();
        int target = cellIndexAt(x, y);
        if (target < 0) {
            return;
        }
        if (target >= items.size()) {
            target = items.size() - 1;
        }
        if (items.size() > target && items.get(target).settings) {
            target = Math.max(0, target - 1);
        }
        if (target != dragTarget) {
            dragTarget = target;
            invalidate();
        }
    }

    private void endItemDrag() {
        int from = draggedIndex;
        int to = dragTarget;
        ghostIcon = null;
        ghostLabel = null;
        if (from >= 0 && from < cells.size()) {
            View cell = cells.get(from);
            cell.animate().cancel();
            cell.animate().alpha(1f).setDuration(120).start();
        }
        itemDrag = false;
        dragTarget = -1;
        draggedIndex = -1;
        pressedIndex = -1;
        invalidate();
        if (from >= 0 && to >= 0 && from != to) {
            moveItem(from, to);
            saveOrder();
        }
    }

    private void drawDragOverlay(Canvas canvas) {
        if (dragTarget >= 0 && dragTarget < cells.size()) {
            View cell = cells.get(dragTarget);
            float left = cell.getLeft() + board.getTranslationX();
            float top = cell.getTop();
            // drawRoundRect with a radius needs API 21, so keep the target slot a plain rect.
            slotPaint.setStyle(Paint.Style.FILL);
            slotPaint.setColor(0x33FFFFFF);
            canvas.drawRect(left + 4, top + 4, left + cell.getWidth() - 4,
                    top + cell.getHeight() - 4, slotPaint);
            slotPaint.setStyle(Paint.Style.STROKE);
            slotPaint.setStrokeWidth(Math.max(1f, 2f * (getHeight() / (float) DESIGN_H)));
            slotPaint.setColor(0x66FFFFFF);
            canvas.drawRect(left + 4, top + 4, left + cell.getWidth() - 4,
                    top + cell.getHeight() - 4, slotPaint);
        }
        if (ghostIcon != null) {
            int save = canvas.save();
            float cx = ghostX + ghostCellWidth / 2f;
            float cy = ghostY + ghostIconHeight / 2f;
            canvas.scale(1.12f, 1.12f, cx, cy);
            float iconLeft = cx - ghostIconWidth / 2f;
            float iconTop = ghostY;
            ghostIcon.setBounds(0, 0, ghostIconWidth, ghostIconHeight);
            canvas.translate(iconLeft, iconTop);
            ghostIcon.draw(canvas);
            canvas.restoreToCount(save);
            if (!TextUtils.isEmpty(ghostLabel)) {
                canvas.drawText(ghostLabel, cx, ghostY + ghostIconHeight + ghostTextSize + 10f,
                        ghostTextPaint);
            }
        }
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
        // Board places children by their index in the ViewGroup, so the view order has to
        // follow the item order or the drawn slot and the stored item drift apart.
        board.removeView(cell);
        board.addView(cell, Math.min(to, board.getChildCount()));
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
        if (itemDrag) {
            drawDragOverlay(canvas);
        }
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
