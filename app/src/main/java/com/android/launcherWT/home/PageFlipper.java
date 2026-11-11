package com.android.launcherWT.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ViewFlipper;

public class PageFlipper extends ViewFlipper {
    public interface OnPageFling {
        void onMoveTowardCards();

        void onMoveTowardApps();
    }

    private final GestureDetector detector;
    private final int minDistance;
    private final int minVelocity;
    private OnPageFling listener;
    private float downX;
    private float downY;
    private boolean intercept;

    public PageFlipper(Context context) {
        this(context, null);
    }

    public PageFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAutoStart(false);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        minDistance = configuration.getScaledPagingTouchSlop() * 2;
        minVelocity = configuration.getScaledMinimumFlingVelocity();
        detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e1 == null || e2 == null || listener == null) {
                    return false;
                }
                float dx = e2.getX() - e1.getX();
                float dy = e2.getY() - e1.getY();
                if (Math.abs(dx) < minDistance || Math.abs(dx) < Math.abs(dy)) {
                    return false;
                }
                if (Math.abs(velocityX) < minVelocity) {
                    return false;
                }
                if (dx < 0f) {
                    listener.onMoveTowardCards();
                } else {
                    listener.onMoveTowardApps();
                }
                return true;
            }
        });
        detector.setIsLongpressEnabled(false);
    }

    public void setOnPageFling(OnPageFling listener) {
        this.listener = listener;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        stopFlipping();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        detector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {
            downX = ev.getX();
            downY = ev.getY();
            intercept = false;
        } else if (action == MotionEvent.ACTION_MOVE) {
            float dx = ev.getX() - downX;
            float dy = ev.getY() - downY;
            if (Math.abs(dx) >= minDistance && Math.abs(dx) > Math.abs(dy)) {
                intercept = true;
            }
        }
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            intercept = false;
        }
        return true;
    }
}
