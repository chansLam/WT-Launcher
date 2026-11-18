package com.incall.apps.service.userservice;

/* loaded from: classes.dex */
public class UserCenterLoadingView extends android.view.View {
    private float center;
    private android.graphics.Paint circlePaint;
    private android.graphics.Paint linePaint;
    private int loadingColor;
    private int margin;
    private float radius;
    private android.graphics.RectF rect;
    private int startAngle;
    private int sweepAngle;

    public UserCenterLoadingView(android.content.Context context) {
        super(context);
        this.margin = 4;
        this.startAngle = -90;
        this.sweepAngle = 90;
        this.linePaint = new android.graphics.Paint();
        this.circlePaint = new android.graphics.Paint();
        this.loadingColor = com.android.launcherWT.R.color.common_flexible_tab_select;
    }

    public UserCenterLoadingView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        this.margin = 4;
        this.startAngle = -90;
        this.sweepAngle = 90;
        this.linePaint = new android.graphics.Paint();
        this.circlePaint = new android.graphics.Paint();
        this.loadingColor = com.android.launcherWT.R.color.common_flexible_tab_select;
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.center = getMeasuredWidth() / 2;
        this.radius = getMeasuredWidth() / 2;
    }

    @Override // android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        initData();
        canvas.drawArc(this.rect, 0.0f, 360.0f, false, this.circlePaint);
        canvas.drawArc(this.rect, this.startAngle, this.sweepAngle, false, this.linePaint);
    }

    public void setThemeType(int themeType) {
        switch (themeType) {
            case 1:
                this.loadingColor = com.android.launcherWT.R.color.common_flexible_tab_select;
                break;
            case 2:
                this.loadingColor = com.android.launcherWT.R.color.common_sport_tab_select;
                break;
            case 3:
                this.loadingColor = com.android.launcherWT.R.color.common_simple_tab_select;
                break;
        }
        invalidate();
    }

    private void initData() {
        this.linePaint.setStyle(android.graphics.Paint.Style.STROKE);
        this.linePaint.setStrokeWidth(getResources().getDimension(com.android.launcherWT.R.dimen.dimen_8px));
        this.linePaint.setColor(getResources().getColor(this.loadingColor));
        this.circlePaint.setColor(getResources().getColor(com.android.launcherWT.R.color.common_text_light));
        this.circlePaint.setAlpha(12);
        this.circlePaint.setStyle(android.graphics.Paint.Style.STROKE);
        this.circlePaint.setStrokeWidth(getResources().getDimension(com.android.launcherWT.R.dimen.dimen_8px));
        this.rect = new android.graphics.RectF((this.center - this.radius) + this.margin, (this.center - this.radius) + this.margin, (this.center + this.radius) - this.margin, (this.center + this.radius) - this.margin);
    }
}
