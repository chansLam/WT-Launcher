package com.incall.apps.common.view;

/* loaded from: classes.dex */
public class MyDigitalClock extends android.widget.TextView {
    private static final java.lang.String TAG = "MyDigitalClock";
    private static final java.lang.String m12 = "aah:mm";
    private static final java.lang.String m24 = "k:mm";
    private boolean mAttached;
    java.util.Calendar mCalendar;
    private android.content.Context mContext;
    java.lang.String mFormat;
    private com.incall.apps.common.view.MyDigitalClock.FormatChangeObserver mFormatChangeObserver;
    private android.content.BroadcastReceiver mIntentReceiver;

    public MyDigitalClock(android.content.Context context) {
        super(context);
        this.mIntentReceiver = new android.content.BroadcastReceiver() { // from class: com.incall.apps.common.view.MyDigitalClock.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context arg0, android.content.Intent arg1) {
                com.incall.apps.common.view.MyDigitalClock.this.mCalendar.setTimeInMillis(java.lang.System.currentTimeMillis());
                com.incall.apps.common.view.MyDigitalClock.this.setText(android.text.format.DateFormat.format(com.incall.apps.common.view.MyDigitalClock.this.mFormat, com.incall.apps.common.view.MyDigitalClock.this.mCalendar));
                com.incall.apps.common.view.MyDigitalClock.this.invalidate();
            }
        };
        this.mContext = context;
        initClock(context);
    }

    public MyDigitalClock(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        this.mIntentReceiver = new android.content.BroadcastReceiver() { // from class: com.incall.apps.common.view.MyDigitalClock.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context arg0, android.content.Intent arg1) {
                com.incall.apps.common.view.MyDigitalClock.this.mCalendar.setTimeInMillis(java.lang.System.currentTimeMillis());
                com.incall.apps.common.view.MyDigitalClock.this.setText(android.text.format.DateFormat.format(com.incall.apps.common.view.MyDigitalClock.this.mFormat, com.incall.apps.common.view.MyDigitalClock.this.mCalendar));
                com.incall.apps.common.view.MyDigitalClock.this.invalidate();
            }
        };
        this.mContext = context;
        initClock(context);
    }

    private void initClock(android.content.Context context) {
        if (this.mCalendar == null) {
            this.mCalendar = java.util.Calendar.getInstance();
        }
        this.mFormatChangeObserver = new com.incall.apps.common.view.MyDigitalClock.FormatChangeObserver();
        getContext().getContentResolver().registerContentObserver(android.provider.Settings.System.CONTENT_URI, true, this.mFormatChangeObserver);
        setFormat();
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        com.incall.apps.service.util.LogUtil.i(TAG, "onAttachedToWindow = " + this.mAttached);
        this.mCalendar.setTimeInMillis(java.lang.System.currentTimeMillis());
        setText(android.text.format.DateFormat.format(this.mFormat, this.mCalendar));
        invalidate();
        if (!this.mAttached) {
            this.mAttached = true;
            android.content.IntentFilter filter = new android.content.IntentFilter();
            filter.addAction("android.intent.action.TIME_TICK");
            this.mContext.registerReceiver(this.mIntentReceiver, filter);
        }
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
        }
    }

    private boolean get24HourMode() {
        return android.text.format.DateFormat.is24HourFormat(getContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFormat() {
        if (get24HourMode()) {
            this.mFormat = m24;
        } else {
            this.mFormat = m12;
        }
    }

    private class FormatChangeObserver extends android.database.ContentObserver {
        public FormatChangeObserver() {
            super(new android.os.Handler());
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            com.incall.apps.common.view.MyDigitalClock.this.setFormat();
        }
    }
}
