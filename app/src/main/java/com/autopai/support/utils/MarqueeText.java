package com.autopai.support.utils;

/* loaded from: classes.dex */
public class MarqueeText extends android.widget.TextView implements java.lang.Runnable {
    private static final int DELAY_TIME = 3000;
    private int currentScrollX;
    private int delayed;
    private int endX;
    private boolean isFirstDraw;
    private boolean isStop;
    private int mWidth;
    private int speed;
    private int textWidth;

    public MarqueeText(android.content.Context context) {
        super(context);
        this.currentScrollX = 0;
        this.isStop = false;
        this.mWidth = 0;
        this.speed = 5;
        this.delayed = 300;
        this.isFirstDraw = true;
    }

    public MarqueeText(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        this.currentScrollX = 0;
        this.isStop = false;
        this.mWidth = 0;
        this.speed = 5;
        this.delayed = 300;
        this.isFirstDraw = true;
        setSingleLine();
    }

    public MarqueeText(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.currentScrollX = 0;
        this.isStop = false;
        this.mWidth = 0;
        this.speed = 5;
        this.delayed = 300;
        this.isFirstDraw = true;
    }

    @Override // android.view.View
    public boolean isFocused() {
        return true;
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        if (this.isFirstDraw) {
            getTextWidth();
            this.currentScrollX = 0;
            this.mWidth = getWidth();
            this.endX = this.textWidth;
            this.isFirstDraw = false;
            removeCallbacks(this);
            postDelayed(this, 3000L);
        }
    }

    public void setSpeed(int sp) {
        this.speed = sp;
    }

    public void setDelayed(int delay) {
        this.delayed = delay;
    }

    private void getTextWidth() {
        android.graphics.Paint paint = getPaint();
        java.lang.String str = getText().toString();
        this.textWidth = (int) paint.measureText(str);
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.textWidth < (this.mWidth * 4) / 5 || this.endX <= 0 || this.isStop) {
            this.currentScrollX = 0;
            scrollTo(this.currentScrollX, 0);
            return;
        }
        this.currentScrollX += this.speed;
        if (this.currentScrollX >= this.endX) {
            scrollTo(0, 0);
            this.currentScrollX = 0;
            removeCallbacks(this);
            postDelayed(this, 3000L);
            return;
        }
        scrollTo(this.currentScrollX, 0);
        removeCallbacks(this);
        postDelayed(this, this.delayed);
    }

    @Override // android.widget.TextView
    protected void onTextChanged(java.lang.CharSequence text, int start, int lengthBefore, int lengthAfter) {
        this.isStop = true;
        removeCallbacks(this);
        this.currentScrollX = 0;
        scrollTo(this.currentScrollX, 0);
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.isFirstDraw = true;
        this.isStop = false;
    }

    public void startScroll() {
        this.isStop = false;
        removeCallbacks(this);
        postDelayed(this, 3000L);
    }

    public void stopScroll() {
        this.isStop = true;
    }

    public void startFor0() {
        this.currentScrollX = 0;
        startScroll();
    }
}
