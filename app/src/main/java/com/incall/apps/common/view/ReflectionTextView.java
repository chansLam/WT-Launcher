package com.incall.apps.common.view;

/* loaded from: classes.dex */
public class ReflectionTextView extends android.widget.TextView {
    private static final float DOUBLES_HEIGHT = 1.4f;
    private android.graphics.Matrix mMatrix;

    public ReflectionTextView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.mMatrix = new android.graphics.Matrix();
        this.mMatrix.preScale(1.0f, -1.0f);
        setLayerType(1, null);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), (int) (getMeasuredHeight() * DOUBLES_HEIGHT));
    }

    @Override // android.widget.TextView, android.view.View
    @android.annotation.SuppressLint({"DrawAllocation"})
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        setDrawingCacheEnabled(true);
        android.graphics.Bitmap reflectionBitmap = android.graphics.Bitmap.createBitmap(getDrawingCache(), 0, height / 2, width, height / 2, this.mMatrix, false);
        canvas.drawBitmap(reflectionBitmap, 0.0f, height / 3.0f, (android.graphics.Paint) null);
        android.graphics.Paint paint = new android.graphics.Paint();
        android.graphics.LinearGradient shader = new android.graphics.LinearGradient(0.0f, height / 2, 0.0f, height, 536870911, 268435455, android.graphics.Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_IN));
        canvas.drawRect(0.0f, height / 1.5f, width, height, paint);
    }

    @Override // android.widget.TextView
    protected void onTextChanged(java.lang.CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        buildDrawingCache();
        postInvalidate();
        destroyDrawingCache();
    }
}
