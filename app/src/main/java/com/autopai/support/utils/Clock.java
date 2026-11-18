package com.autopai.support.utils;

/* loaded from: classes.dex */
public class Clock extends android.widget.TextView implements com.autopai.support.IChangeTheme {
    private static final java.lang.String LOGTAG = "Clock";
    private static final int MSG_UPDATE = 1;
    private boolean mAttached;
    private java.util.Calendar mCalendar;
    private int mCurColorResId;
    private final android.content.BroadcastReceiver mIntentReceiver;
    private java.util.Locale mLocale;
    private android.os.Handler mUiHandler;

    public Clock(android.content.Context context) {
        this(context, null);
    }

    public Clock(android.content.Context context, android.util.AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Clock(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mIntentReceiver = new android.content.BroadcastReceiver() { // from class: com.autopai.support.utils.Clock.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                java.lang.String action = intent.getAction();
                if (action.equals("android.intent.action.TIMEZONE_CHANGED")) {
                    java.lang.String tz = intent.getStringExtra("time-zone");
                    com.autopai.support.utils.Clock.this.mCalendar = java.util.Calendar.getInstance(java.util.TimeZone.getTimeZone(tz));
                } else if (action.equals("android.intent.action.CONFIGURATION_CHANGED")) {
                    java.util.Locale newLocale = com.autopai.support.utils.Clock.this.getResources().getConfiguration().locale;
                    if (!newLocale.equals(com.autopai.support.utils.Clock.this.mLocale)) {
                        com.autopai.support.utils.Clock.this.mLocale = newLocale;
                    }
                }
                com.autopai.support.utils.Clock.this.updateClock();
            }
        };
        this.mUiHandler = new android.os.Handler() { // from class: com.autopai.support.utils.Clock.2
            @Override // android.os.Handler
            public void handleMessage(android.os.Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        com.autopai.support.utils.Clock.this.mCalendar.setTimeInMillis(java.lang.System.currentTimeMillis());
                        com.autopai.support.utils.Clock.this.setText(com.autopai.support.utils.Clock.this.getSmallTime());
                        break;
                }
            }
        };
        this.mCurColorResId = com.autopai.support.utils.AutoPaiResource.getIdentifierByColor(context, "toprightview_clock_text_color");
        changeTheme();
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.mAttached) {
            this.mAttached = true;
            android.content.IntentFilter filter = new android.content.IntentFilter();
            filter.addAction("android.intent.action.TIME_TICK");
            filter.addAction("android.intent.action.TIME_SET");
            filter.addAction("android.intent.action.TIMEZONE_CHANGED");
            filter.addAction("android.intent.action.CONFIGURATION_CHANGED");
            getContext().registerReceiver(this.mIntentReceiver, filter, null, getHandler());
        }
        this.mCalendar = java.util.Calendar.getInstance(java.util.TimeZone.getDefault());
        updateClock();
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mAttached) {
            getContext().unregisterReceiver(this.mIntentReceiver);
            this.mAttached = false;
        }
    }

    final void updateClock() {
        com.autopai.support.utils.SLog.d(LOGTAG, "updateClock time = " + java.lang.System.currentTimeMillis());
        android.os.Message msg = this.mUiHandler.obtainMessage();
        msg.what = 1;
        this.mUiHandler.sendMessage(msg);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.SuppressLint({"SimpleDateFormat"})
    public final java.lang.CharSequence getSmallTime() {
        java.lang.String ampmValues;
        android.content.Context context = getContext();
        boolean is24 = android.text.format.DateFormat.is24HourFormat(context);
        java.text.SimpleDateFormat sdf = is24 ? new java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault()) : new java.text.SimpleDateFormat("hh:mm", java.util.Locale.getDefault());
        java.util.Date d = this.mCalendar.getTime();
        java.lang.String result = sdf.format(d);
        int hour = d.getHours();
        com.autopai.support.utils.SLog.d(LOGTAG, "updateClock d.getTime() = " + d.getTime() + "; d.toString = " + d.toString() + "; result = " + result + "; mCalendar.get(Calendar.AM_PM) = " + this.mCalendar.get(9) + "; hour =" + hour);
        if (hour < 12) {
            ampmValues = context.getString(com.autopai.support.utils.AutoPaiResource.getIdentifierByString(context, "am_time"));
        } else {
            ampmValues = context.getString(com.autopai.support.utils.AutoPaiResource.getIdentifierByString(context, "pm_time"));
        }
        if (!is24) {
            result = ampmValues + " " + result;
        }
        com.autopai.support.utils.SLog.d(LOGTAG, "updateClock is24 = " + is24 + "; result = " + result);
        return result;
    }

    @Override // com.autopai.support.IChangeTheme
    public void changeTheme() {
        com.autopai.support.utils.SLog.d(LOGTAG, "changeTheme this = " + this + "; mCurResId = " + this.mCurColorResId);
        android.content.res.ColorStateList colorStateList = com.autopai.support.utils.ResourceUtils.getInstance(getContext()).getColorStateList(this.mCurColorResId);
        setTextColor(colorStateList);
    }
}
