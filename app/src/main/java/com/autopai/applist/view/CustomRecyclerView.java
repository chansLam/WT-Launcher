package com.autopai.applist.view;

/* loaded from: classes.dex */
public class CustomRecyclerView extends android.support.v7.widget.RecyclerView {
    private static final java.lang.String TAG = "CustomRecyclerView";
    private static final int VELOCITY_RATIO = 2;
    private int endPoint;
    private android.animation.ValueAnimator mAnimator;
    private android.widget.Scroller mGravityScroller;
    private int offsetX;
    private int offsetY;
    private int startPoint;
    private int startX;
    private int startY;

    public CustomRecyclerView(android.content.Context context) {
        super(context);
        this.offsetY = 0;
        this.offsetX = 0;
        this.startY = 0;
        this.startX = 0;
        this.mAnimator = null;
        init();
    }

    public CustomRecyclerView(android.content.Context context, @android.support.annotation.Nullable android.util.AttributeSet attrs) {
        super(context, attrs);
        this.offsetY = 0;
        this.offsetX = 0;
        this.startY = 0;
        this.startX = 0;
        this.mAnimator = null;
        init();
    }

    public CustomRecyclerView(android.content.Context context, @android.support.annotation.Nullable android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.offsetY = 0;
        this.offsetX = 0;
        this.startY = 0;
        this.startX = 0;
        this.mAnimator = null;
        init();
    }

    private void init() {
        this.mGravityScroller = new android.widget.Scroller(getContext(), new android.view.animation.DecelerateInterpolator());
        updateLayoutManger();
    }

    private void updateLayoutManger() {
        if (this.mAnimator != null) {
            this.mAnimator.cancel();
        }
        this.startX = 0;
        this.startY = 0;
        this.offsetX = 0;
        this.offsetY = 0;
    }

    @Override // android.support.v7.widget.RecyclerView
    public void scrollToPosition(int position) {
        android.util.Log.i(TAG, "scrollToPosition: int position == " + position);
        if (this.mAnimator != null) {
            int startPoint = getLayoutManager().canScrollVertically() ? this.offsetY : this.offsetX;
            if (startPoint != this.endPoint) {
                this.mAnimator.setIntValues(startPoint, position);
                this.mAnimator.start();
            }
        }
    }

    @Override // android.support.v7.widget.RecyclerView
    public boolean fling(int velocityX, int velocityY) {
        android.util.Log.e(TAG, "onFling velocityX : " + velocityX + ", velocityY: " + velocityY + ", mRecyclerView.getMinFlingVelocity(): " + getMinFlingVelocity());
        android.support.v7.widget.RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager == null) {
            return false;
        }
        android.support.v7.widget.RecyclerView.Adapter adapter = getAdapter();
        if (adapter == null) {
            return super.fling(velocityX, velocityY);
        }
        return snapFromFling(layoutManager, velocityX, velocityY);
    }

    private boolean snapFromFling(@android.support.annotation.NonNull android.support.v7.widget.RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        int position;
        int minFlingVelocity = getMinFlingVelocity() * 2;
        com.autopai.applist.manager.HorizontalPageLayoutManager horizontalPageLayoutManager = (com.autopai.applist.manager.HorizontalPageLayoutManager) layoutManager;
        int[] out = calculateScrollDistance(velocityX, velocityY);
        android.util.Log.e(TAG, "snapFromFling: " + out[0] + ", " + out[1]);
        int page = -1;
        if (velocityX > 0 && (java.lang.Math.abs(out[0]) > layoutManager.getWidth() * 0.2f || java.lang.Math.abs(velocityX) > minFlingVelocity)) {
            page = horizontalPageLayoutManager.getPageIndex() + 1;
        } else if (velocityX < 0 && (java.lang.Math.abs(out[0]) > layoutManager.getWidth() * 0.2f || java.lang.Math.abs(velocityX) > minFlingVelocity)) {
            page = horizontalPageLayoutManager.getPageIndex() - 1;
        }
        if (velocityY > 0 && (java.lang.Math.abs(out[1]) > layoutManager.getHeight() * 0.2f || java.lang.Math.abs(velocityY) > minFlingVelocity)) {
            page = horizontalPageLayoutManager.getPageIndex() + 1;
        } else if (velocityY < 0 && (java.lang.Math.abs(out[1]) > layoutManager.getHeight() * 0.2f || java.lang.Math.abs(velocityY) > minFlingVelocity)) {
            page = horizontalPageLayoutManager.getPageIndex() - 1;
        }
        if (page <= -1) {
            return false;
        }
        if (getLayoutManager().canScrollVertically()) {
            position = getHeight() * page;
        } else {
            position = getWidth() * page;
        }
        scrollToPosition(position);
        return true;
    }

    private int[] calculateScrollDistance(int velocityX, int velocityY) {
        this.mGravityScroller.fling(0, 0, velocityX, velocityY, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
        int[] outDist = {this.mGravityScroller.getFinalX(), this.mGravityScroller.getFinalY()};
        android.util.Log.e(TAG, "calculateScrollDistance x: " + outDist[0] + ", y: " + outDist[1]);
        return outDist;
    }

    private int getStartPageIndex() {
        int page;
        if (getHeight() == 0 || getWidth() == 0) {
            return 0;
        }
        if (getLayoutManager().canScrollVertically()) {
            page = this.startY / getHeight();
        } else {
            page = this.startX / getWidth();
        }
        return page;
    }

    @Override // android.support.v7.widget.RecyclerView, android.view.View
    public boolean onTouchEvent(android.view.MotionEvent e) {
        int offset;
        int i;
        android.util.Log.e(TAG, "onTouchEvent: " + e.getAction());
        if (e.getAction() == 0) {
            this.startY = this.offsetY;
            this.startX = this.offsetX;
        }
        if (e.getAction() == 1 || e.getAction() == 3) {
            if (getLayoutManager().canScrollVertically()) {
                offset = this.offsetY;
                i = (((com.autopai.applist.manager.HorizontalPageLayoutManager) getLayoutManager()).computePageSize() - 1) * getHeight();
            } else {
                offset = this.offsetX;
                i = (((com.autopai.applist.manager.HorizontalPageLayoutManager) getLayoutManager()).computePageSize() - 1) * getWidth();
            }
            if (offset != 0 && offset != i) {
                if (this.mAnimator == null) {
                    this.mAnimator = android.animation.ValueAnimator.ofInt(this.startPoint, this.endPoint);
                    this.mAnimator.setDuration(200L);
                    this.mAnimator.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: com.autopai.applist.view.CustomRecyclerView.1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public void onAnimationUpdate(android.animation.ValueAnimator animation) {
                            int nowPoint = ((java.lang.Integer) animation.getAnimatedValue()).intValue();
                            if (com.autopai.applist.view.CustomRecyclerView.this.getLayoutManager().canScrollVertically()) {
                                int dy = nowPoint - com.autopai.applist.view.CustomRecyclerView.this.offsetY;
                                com.autopai.applist.view.CustomRecyclerView.this.scrollBy(0, dy);
                            } else {
                                int dx = nowPoint - com.autopai.applist.view.CustomRecyclerView.this.offsetX;
                                com.autopai.applist.view.CustomRecyclerView.this.scrollBy(dx, 0);
                            }
                        }
                    });
                    this.mAnimator.addListener(new android.animation.AnimatorListenerAdapter() { // from class: com.autopai.applist.view.CustomRecyclerView.2
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(android.animation.Animator animation) {
                            com.autopai.applist.view.CustomRecyclerView.this.stopScroll();
                            com.autopai.applist.view.CustomRecyclerView.this.startY = com.autopai.applist.view.CustomRecyclerView.this.offsetY;
                            com.autopai.applist.view.CustomRecyclerView.this.startX = com.autopai.applist.view.CustomRecyclerView.this.offsetX;
                        }
                    });
                } else {
                    this.mAnimator.cancel();
                    this.mAnimator.setIntValues(this.startPoint, this.endPoint);
                }
                this.mAnimator.start();
            }
        }
        return super.onTouchEvent(e);
    }

    @Override // android.support.v7.widget.RecyclerView
    public void onScrolled(int dx, int dy) {
        this.offsetY += dy;
        this.offsetX += dx;
        int pageIndex = getStartPageIndex();
        this.endPoint = 0;
        this.startPoint = 0;
        if (getLayoutManager().canScrollVertically()) {
            this.startPoint = this.offsetY;
            if (this.offsetY - this.startY < 0 && java.lang.Math.abs(this.offsetY - this.startY) > getHeight() * 0.2f) {
                android.util.Log.i(TAG, "onScrolled : " + getStartPageIndex() + ", startPoint: " + this.startPoint);
                pageIndex--;
            } else if (this.offsetY - this.startY > 0 && java.lang.Math.abs(this.offsetY - this.startY) > getHeight() * 0.2f) {
                android.util.Log.i(TAG, "onScrolled : " + getStartPageIndex() + ", startPoint: " + this.startPoint);
                pageIndex++;
            }
            this.endPoint = getHeight() * pageIndex;
        } else {
            this.startPoint = this.offsetX;
            if (this.offsetX - this.startX < 0 && java.lang.Math.abs(this.offsetX - this.startX) > getWidth() * 0.2f) {
                android.util.Log.i(TAG, "onScrolled : " + getStartPageIndex() + ", startPoint: " + this.startPoint);
                pageIndex--;
            } else if (this.offsetX - this.startX > 0 && java.lang.Math.abs(this.offsetX - this.startX) > getWidth() * 0.2f) {
                android.util.Log.i(TAG, "onScrolled : " + getStartPageIndex() + ", startPoint: " + this.startPoint);
                pageIndex++;
            }
            this.endPoint = getWidth() * pageIndex;
        }
        if (this.endPoint < 0) {
            this.endPoint = 0;
        }
    }
}
