package com.incall.apps.common.view;

/* loaded from: classes.dex */
public class AnalogClockCustomDrawable extends android.view.View {
    private static final java.lang.String TAG = "AnalogClockCustomDrawable";
    private boolean mAttached;
    private java.util.Calendar mCalendar;
    private boolean mChanged;
    private final java.lang.Runnable mClockTick;
    private android.content.Context mContext;
    private android.graphics.drawable.Drawable mDial;
    private int mDialHeight;
    private int mDialWidth;
    private android.graphics.drawable.Drawable mDot;
    private final float mDotOffset;
    private android.graphics.Paint mDotPaint;
    private final float mDotRadius;
    private final android.os.Handler mHandler;
    private float mHour;
    private android.graphics.drawable.Drawable mHourHand;
    private final android.content.BroadcastReceiver mIntentReceiver;
    private android.graphics.drawable.Drawable mMinuteHand;
    private float mMinutes;
    private boolean mNoSeconds;
    private android.graphics.drawable.Drawable mSecondHand;
    private float mSeconds;
    private java.lang.String mTimeZoneId;

    public AnalogClockCustomDrawable(android.content.Context context) {
        this(context, null);
    }

    public AnalogClockCustomDrawable(android.content.Context context, android.util.AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnalogClockCustomDrawable(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mHandler = new android.os.Handler();
        this.mNoSeconds = false;
        this.mIntentReceiver = new android.content.BroadcastReceiver() { // from class: com.incall.apps.common.view.AnalogClockCustomDrawable.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                com.incall.apps.common.view.AnalogClockCustomDrawable.this.mCalendar = java.util.Calendar.getInstance();
                if (intent.getAction().equals("android.intent.action.TIMEZONE_CHANGED")) {
                    java.lang.String tz = intent.getStringExtra("time-zone");
                    com.incall.apps.common.view.AnalogClockCustomDrawable.this.mCalendar.setTimeZone(java.util.TimeZone.getTimeZone(tz));
                }
            }
        };
        this.mClockTick = new java.lang.Runnable() { // from class: com.incall.apps.common.view.AnalogClockCustomDrawable.2
            @Override // java.lang.Runnable
            public void run() {
                com.incall.apps.common.view.AnalogClockCustomDrawable.this.mCalendar.add(13, 1);
                com.incall.apps.common.view.AnalogClockCustomDrawable.this.onTimeChanged();
                com.incall.apps.common.view.AnalogClockCustomDrawable.this.invalidate();
                com.incall.apps.common.view.AnalogClockCustomDrawable.this.postDelayed(com.incall.apps.common.view.AnalogClockCustomDrawable.this.mClockTick, 1000L);
            }
        };
        this.mContext = context.getApplicationContext();
        android.content.res.Resources r = this.mContext.getResources();
        this.mDial = r.getDrawable(com.android.launcherWT.R.drawable.ic_clock1);
        this.mHourHand = r.getDrawable(com.android.launcherWT.R.drawable.ic_clock_hour1);
        this.mMinuteHand = r.getDrawable(com.android.launcherWT.R.drawable.ic_clock_minute1);
        this.mSecondHand = ThemeChanged();
        android.content.res.TypedArray a = context.obtainStyledAttributes(attrs, com.android.launcherWT.R.styleable.AnalogClockCustomDrawable);
        this.mDotRadius = a.getDimension(com.android.launcherWT.R.styleable.AnalogClockCustomDrawable_dialRadius, 0.0f);
        this.mDotOffset = a.getDimension(com.android.launcherWT.R.styleable.AnalogClockCustomDrawable_needleOffset, 0.0f);
        int dotColor = a.getColor(com.android.launcherWT.R.styleable.AnalogClockCustomDrawable_needleColor, -1);
        if (dotColor != 0) {
            this.mDotPaint = new android.graphics.Paint(1);
            this.mDotPaint.setColor(dotColor);
        }
        this.mCalendar = java.util.Calendar.getInstance();
        this.mDialWidth = this.mDial.getIntrinsicWidth();
        this.mDialHeight = this.mDial.getIntrinsicHeight();
    }

    public android.graphics.drawable.Drawable ThemeChanged() {
        switch (com.incall.apps.common.utils.ThemeUtils.getThemeType(this.mContext)) {
            case 1:
                this.mSecondHand = this.mContext.getResources().getDrawable(com.android.launcherWT.R.drawable.ic_clock_second1_classic);
                break;
            case 2:
                this.mSecondHand = this.mContext.getResources().getDrawable(com.android.launcherWT.R.drawable.ic_clock_second1);
                break;
            case 3:
                this.mSecondHand = this.mContext.getResources().getDrawable(com.android.launcherWT.R.drawable.ic_clock_second1_simple);
                break;
        }
        return this.mSecondHand;
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        com.incall.apps.service.util.LogUtil.i(TAG, "onAttachedToWindow = " + this.mAttached);
        if (!this.mAttached) {
            this.mAttached = true;
            android.content.IntentFilter filter = new android.content.IntentFilter();
            filter.addAction("android.intent.action.TIME_TICK");
            filter.addAction("android.intent.action.TIME_SET");
            filter.addAction("android.intent.action.TIMEZONE_CHANGED");
            this.mContext.registerReceiver(this.mIntentReceiver, filter, null, this.mHandler);
        }
        this.mCalendar = java.util.Calendar.getInstance();
        onTimeChanged();
        post(this.mClockTick);
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mAttached) {
            try {
                this.mContext.unregisterReceiver(this.mIntentReceiver);
            } catch (java.lang.Exception e) {
                com.incall.apps.service.util.LogUtil.i(TAG, "recever没有注册");
            }
            removeCallbacks(this.mClockTick);
            this.mAttached = false;
        }
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = android.view.View.MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = android.view.View.MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = android.view.View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = android.view.View.MeasureSpec.getSize(heightMeasureSpec);
        float hScale = 1.0f;
        float vScale = 1.0f;
        if (widthMode != 0 && widthSize < this.mDialWidth) {
            hScale = widthSize / this.mDialWidth;
        }
        if (heightMode != 0 && heightSize < this.mDialHeight) {
            vScale = heightSize / this.mDialHeight;
        }
        float scale = java.lang.Math.min(hScale, vScale);
        setMeasuredDimension(resolveSizeAndState((int) (this.mDialWidth * scale), widthMeasureSpec, 0), resolveSizeAndState((int) (this.mDialHeight * scale), heightMeasureSpec, 0));
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mChanged = true;
    }

    @Override // android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        boolean changed = this.mChanged;
        if (changed) {
            this.mChanged = false;
        }
        int availableWidth = getWidth();
        int availableHeight = getHeight();
        int x = availableWidth / 2;
        int y = availableHeight / 2;
        android.graphics.drawable.Drawable dial = this.mDial;
        int w = dial.getIntrinsicWidth();
        int h = dial.getIntrinsicHeight();
        boolean scaled = false;
        if (availableWidth < w || availableHeight < h) {
            scaled = true;
            float scale = java.lang.Math.min(availableWidth / w, availableHeight / h);
            canvas.save();
            canvas.scale(scale, scale, x, y);
        }
        if (changed) {
            dial.setBounds(x - (w / 2), y - (h / 2), (w / 2) + x, (h / 2) + y);
        }
        dial.draw(canvas);
        if (this.mDotRadius > 0.0f && this.mDotPaint != null) {
            canvas.drawCircle(x, (y - (h / 2)) + this.mDotOffset, this.mDotRadius, this.mDotPaint);
        }
        drawHand(canvas, this.mHourHand, x, y, (this.mHour / 12.0f) * 360.0f, changed);
        drawHand(canvas, this.mMinuteHand, x, y, (this.mMinutes / 60.0f) * 360.0f, changed);
        if (!this.mNoSeconds) {
            drawHand(canvas, this.mSecondHand, x, y, (this.mSeconds / 60.0f) * 360.0f, changed);
        }
        if (scaled) {
            canvas.restore();
        }
    }

    private void drawHand(android.graphics.Canvas canvas, android.graphics.drawable.Drawable hand, int x, int y, float angle, boolean changed) {
        canvas.save();
        canvas.rotate(angle, x, y);
        if (changed) {
            int w = hand.getIntrinsicWidth();
            int h = hand.getIntrinsicHeight();
            hand.setBounds(x - (w / 2), y - (h / 2), (w / 2) + x, (h / 2) + y);
        }
        hand.draw(canvas);
        canvas.restore();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTimeChanged() {
        if (this.mTimeZoneId != null) {
            this.mCalendar.setTimeZone(java.util.TimeZone.getTimeZone(this.mTimeZoneId));
        }
        int hour = this.mCalendar.get(10);
        int minute = this.mCalendar.get(12);
        int second = this.mCalendar.get(13);
        this.mSeconds = second;
        this.mMinutes = minute + (second / 60.0f);
        this.mHour = hour + (this.mMinutes / 60.0f);
        this.mChanged = true;
        updateContentDescription(this.mCalendar);
    }

    private void updateContentDescription(java.util.Calendar calendar) {
        if (getContext() != null) {
            java.lang.String contentDescription = android.text.format.DateUtils.formatDateTime(this.mContext, calendar.getTimeInMillis(), 129);
            setContentDescription(contentDescription);
        }
    }

    public void setTimeZone(java.lang.String id) {
        this.mTimeZoneId = id;
        onTimeChanged();
    }

    public void enableSeconds(boolean enable) {
        this.mNoSeconds = !enable;
    }
}
