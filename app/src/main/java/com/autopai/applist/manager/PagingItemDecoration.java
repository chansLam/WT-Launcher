package com.autopai.applist.manager;

/* loaded from: classes.dex */
public class PagingItemDecoration extends android.support.v7.widget.RecyclerView.ItemDecoration {
    private static final int[] ATTRS = {android.R.attr.listDivider};
    private static final java.lang.String TAG = "PagingItemDecoration";
    private int colorActive;
    private int colorInactive;
    private com.autopai.applist.manager.HorizontalPageLayoutManager layoutManager;
    private android.graphics.drawable.Drawable mDivider;
    private float mIndicatorHeight;
    private float mIndicatorItemPadding;
    private float mLongIndicatorItemLength;
    private float mShortIndicatorItemLength;
    private final android.graphics.Paint mPaint = new android.graphics.Paint();
    private final android.view.animation.Interpolator mInterpolator = new android.view.animation.AccelerateDecelerateInterpolator();

    public PagingItemDecoration(android.content.Context context) {
        android.content.res.TypedArray a = context.obtainStyledAttributes(ATTRS);
        this.mDivider = a.getDrawable(0);
        a.recycle();
        init();
    }

    private void init() {
        initColor();
        this.mIndicatorHeight = 48.0f;
        this.mLongIndicatorItemLength = 100.0f;
        this.mShortIndicatorItemLength = 50.0f;
        this.mIndicatorItemPadding = 50.0f;
        this.mPaint.setStrokeCap(android.graphics.Paint.Cap.ROUND);
        this.mPaint.setStrokeWidth(4.0f);
        this.mPaint.setStyle(android.graphics.Paint.Style.STROKE);
        this.mPaint.setAntiAlias(true);
    }

    public void initColor() {
        java.lang.String[] markerColor = com.autopai.applist.manager.AppIconManager.getMarkerResourcedIds();
        this.colorActive = android.graphics.Color.parseColor(markerColor[0]);
        this.colorInactive = android.graphics.Color.parseColor(markerColor[1]);
    }

    @Override // android.support.v7.widget.RecyclerView.ItemDecoration
    public void onDraw(@android.support.annotation.NonNull android.graphics.Canvas c, @android.support.annotation.NonNull android.support.v7.widget.RecyclerView parent, @android.support.annotation.NonNull android.support.v7.widget.RecyclerView.State state) {
    }

    private void drawHorizontal(android.graphics.Canvas c, android.support.v7.widget.RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            android.view.View child = parent.getChildAt(i);
            android.support.v7.widget.RecyclerView.LayoutParams params = (android.support.v7.widget.RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft() - params.leftMargin;
            int right = child.getRight() + params.rightMargin + this.mDivider.getIntrinsicWidth();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + this.mDivider.getIntrinsicHeight();
            this.mDivider.setBounds(left, top, right, bottom);
            this.mDivider.draw(c);
        }
    }

    private void drawVertical(android.graphics.Canvas c, android.support.v7.widget.RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            android.view.View child = parent.getChildAt(i);
            android.support.v7.widget.RecyclerView.LayoutParams params = (android.support.v7.widget.RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getTop() - params.topMargin;
            int bottom = child.getBottom() + params.bottomMargin;
            int left = child.getRight() + params.rightMargin;
            int right = left + this.mDivider.getIntrinsicWidth();
            this.mDivider.setBounds(left, top, right, bottom);
            this.mDivider.draw(c);
        }
    }

    @Override // android.support.v7.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(@android.support.annotation.NonNull android.graphics.Rect outRect, @android.support.annotation.NonNull android.view.View view, @android.support.annotation.NonNull android.support.v7.widget.RecyclerView parent, @android.support.annotation.NonNull android.support.v7.widget.RecyclerView.State state) {
        int itemPosition = parent.getChildAdapterPosition(view);
        this.layoutManager = (com.autopai.applist.manager.HorizontalPageLayoutManager) parent.getLayoutManager();
        outRect.left = 56;
        outRect.right = 32;
        if (itemPosition % this.layoutManager.onePageSize < this.layoutManager.columns) {
            outRect.bottom = 24;
        }
        if (itemPosition % this.layoutManager.onePageSize >= this.layoutManager.columns) {
            outRect.top = 24;
        }
    }

    @Override // android.support.v7.widget.RecyclerView.ItemDecoration
    public void onDrawOver(@android.support.annotation.NonNull android.graphics.Canvas c, @android.support.annotation.NonNull android.support.v7.widget.RecyclerView parent, @android.support.annotation.NonNull android.support.v7.widget.RecyclerView.State state) {
        float indicatorStartX;
        float indicatorPosY;
        int offset;
        int nextPage;
        float f;
        super.onDrawOver(c, parent, state);
        if (this.layoutManager != null) {
            int pageCount = this.layoutManager.getPageSize();
            float totalLength = this.mLongIndicatorItemLength + (this.mShortIndicatorItemLength * (pageCount - 1));
            float paddingBetweenItems = java.lang.Math.max(0, pageCount - 1) * this.mIndicatorItemPadding;
            float indicatorTotalWidth = totalLength + paddingBetweenItems;
            if (this.layoutManager.canScrollHorizontally()) {
                indicatorStartX = (parent.getWidth() - indicatorTotalWidth) / 2.0f;
                indicatorPosY = parent.getHeight() - this.mIndicatorHeight;
            } else {
                indicatorStartX = 0.0f;
                indicatorPosY = (parent.getHeight() - indicatorTotalWidth) / 2.0f;
            }
            int mCurrentPage = this.layoutManager.getPageIndex();
            if (this.layoutManager.canScrollHorizontally()) {
                offset = this.layoutManager.getOffsetX();
            } else {
                offset = this.layoutManager.getOffsetY();
            }
            if (offset - this.layoutManager.getScrollForPage(mCurrentPage) > 0) {
                nextPage = mCurrentPage + 1;
            } else if (offset - this.layoutManager.getScrollForPage(mCurrentPage) < 0) {
                nextPage = mCurrentPage - 1;
            } else {
                nextPage = mCurrentPage;
            }
            if (mCurrentPage == 0) {
                if (this.layoutManager.canScrollHorizontally()) {
                    f = this.layoutManager.getOffsetX() / parent.getWidth();
                } else {
                    f = this.layoutManager.getOffsetY() / parent.getHeight();
                }
            } else if (this.layoutManager.canScrollHorizontally()) {
                f = (this.layoutManager.getOffsetX() - (parent.getWidth() * mCurrentPage)) / parent.getWidth();
            } else {
                f = (this.layoutManager.getOffsetY() - (parent.getHeight() * mCurrentPage)) / parent.getHeight();
            }
            float progress = this.mInterpolator.getInterpolation(f);
            drawInactiveIndicators(c, indicatorStartX, indicatorPosY, mCurrentPage, progress, pageCount, nextPage);
            return;
        }
        android.util.Log.e(TAG, "HorizontalPageLayoutManager is null");
    }

    private void drawInactiveIndicators(android.graphics.Canvas c, float indicatorStartX, float indicatorPosY, int highlightPosition, float progress, int pageCount, int nextPage) {
        float start;
        int blendColor = android.support.v4.graphics.ColorUtils.blendARGB(this.colorActive, this.colorInactive, progress);
        int blendColor1 = android.support.v4.graphics.ColorUtils.blendARGB(this.colorActive, this.colorInactive, 1.0f - progress);
        float itemWidth = this.mLongIndicatorItemLength + this.mIndicatorItemPadding;
        if (this.layoutManager.canScrollHorizontally()) {
            start = indicatorStartX;
        } else {
            start = indicatorPosY;
        }
        if (progress == 0.0f) {
            for (int i = 0; i < pageCount; i++) {
                if (i == highlightPosition) {
                    this.mPaint.setColor(this.colorActive);
                    if (this.layoutManager.canScrollHorizontally()) {
                        c.drawLine(start, indicatorPosY, start + this.mLongIndicatorItemLength, indicatorPosY, this.mPaint);
                        start += itemWidth;
                    } else {
                        c.drawLine(indicatorStartX, start, indicatorStartX, start + this.mLongIndicatorItemLength, this.mPaint);
                        start += itemWidth;
                    }
                } else {
                    this.mPaint.setColor(this.colorInactive);
                    if (this.layoutManager.canScrollHorizontally()) {
                        c.drawLine(start, indicatorPosY, start + this.mShortIndicatorItemLength, indicatorPosY, this.mPaint);
                        start += this.mShortIndicatorItemLength + this.mIndicatorItemPadding;
                    } else {
                        c.drawLine(indicatorStartX, start, indicatorStartX, start + this.mShortIndicatorItemLength, this.mPaint);
                        start += this.mShortIndicatorItemLength + this.mIndicatorItemPadding;
                    }
                }
            }
            return;
        }
        float partialLength = this.mShortIndicatorItemLength * progress;
        for (int i2 = 0; i2 < pageCount; i2++) {
            if (nextPage > highlightPosition) {
                if (i2 == highlightPosition) {
                    this.mPaint.setColor(blendColor);
                    if (this.layoutManager.canScrollHorizontally()) {
                        c.drawLine(start, indicatorPosY, (this.mLongIndicatorItemLength + start) - partialLength, indicatorPosY, this.mPaint);
                        start += itemWidth;
                    } else {
                        c.drawLine(indicatorStartX, start, indicatorStartX, (this.mLongIndicatorItemLength + start) - partialLength, this.mPaint);
                        start += itemWidth;
                    }
                } else if (i2 == nextPage) {
                    this.mPaint.setColor(blendColor1);
                    if (this.layoutManager.canScrollHorizontally()) {
                        c.drawLine(start - partialLength, indicatorPosY, start + this.mShortIndicatorItemLength, indicatorPosY, this.mPaint);
                        start += this.mShortIndicatorItemLength + this.mIndicatorItemPadding;
                    } else {
                        c.drawLine(indicatorStartX, start - partialLength, indicatorStartX, start + this.mShortIndicatorItemLength, this.mPaint);
                        start += this.mShortIndicatorItemLength + this.mIndicatorItemPadding;
                    }
                } else {
                    this.mPaint.setColor(this.colorInactive);
                    if (this.layoutManager.canScrollHorizontally()) {
                        c.drawLine(start, indicatorPosY, start + this.mShortIndicatorItemLength, indicatorPosY, this.mPaint);
                        start += this.mShortIndicatorItemLength + this.mIndicatorItemPadding;
                    } else {
                        c.drawLine(indicatorStartX, start, indicatorStartX, start + this.mShortIndicatorItemLength, this.mPaint);
                        start += this.mShortIndicatorItemLength + this.mIndicatorItemPadding;
                    }
                }
            } else if (i2 == highlightPosition) {
                this.mPaint.setColor(blendColor);
                if (this.layoutManager.canScrollHorizontally()) {
                    c.drawLine(start + partialLength, indicatorPosY, start + this.mLongIndicatorItemLength, indicatorPosY, this.mPaint);
                    start += itemWidth;
                } else {
                    c.drawLine(indicatorStartX, start + partialLength, indicatorStartX, start + this.mLongIndicatorItemLength, this.mPaint);
                    start += itemWidth;
                }
            } else if (i2 == nextPage) {
                this.mPaint.setColor(blendColor1);
                if (this.layoutManager.canScrollHorizontally()) {
                    c.drawLine(start, indicatorPosY, this.mShortIndicatorItemLength + start + partialLength, indicatorPosY, this.mPaint);
                    start += this.mShortIndicatorItemLength + this.mIndicatorItemPadding;
                } else {
                    c.drawLine(indicatorStartX, start, indicatorStartX, this.mShortIndicatorItemLength + start + partialLength, this.mPaint);
                    start += this.mShortIndicatorItemLength + this.mIndicatorItemPadding;
                }
            } else {
                this.mPaint.setColor(this.colorInactive);
                if (this.layoutManager.canScrollHorizontally()) {
                    c.drawLine(start, indicatorPosY, start + this.mShortIndicatorItemLength, indicatorPosY, this.mPaint);
                    start += this.mShortIndicatorItemLength + this.mIndicatorItemPadding;
                } else {
                    c.drawLine(indicatorStartX, start, indicatorStartX, start + this.mShortIndicatorItemLength, this.mPaint);
                    start += this.mShortIndicatorItemLength + this.mIndicatorItemPadding;
                }
            }
        }
    }
}
