package com.autopai.applist.manager;

/* loaded from: classes.dex */
public class HorizontalPageLayoutManager extends android.support.v7.widget.RecyclerView.LayoutManager {
    private static final java.lang.String TAG = "HorizontalLayoutManager";
    int columns;
    private int itemHeightUsed;
    private int itemWidthUsed;
    private int[] mPageScrolls;
    int onePageSize;
    private int rows;
    private android.graphics.Rect mDisplayRect = new android.graphics.Rect();
    private android.util.SparseArray<android.graphics.Rect> allItemFrames = new android.util.SparseArray<>();
    private java.util.ArrayList<android.view.View> mInvisibleViews = new java.util.ArrayList<>();
    private int totalHeight = 0;
    private int totalWidth = 0;
    private int offsetY = 0;
    private int offsetX = 0;
    private int pageSize = 0;
    private int itemWidth = 0;
    private int itemHeight = 0;
    private int mCurrentPage = 0;

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public android.support.v7.widget.RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new android.support.v7.widget.RecyclerView.LayoutParams(-2, -2);
    }

    public HorizontalPageLayoutManager(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.onePageSize = rows * columns;
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public boolean canScrollVertically() {
        return false;
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public int scrollHorizontallyBy(int dx, android.support.v7.widget.RecyclerView.Recycler recycler, android.support.v7.widget.RecyclerView.State state) {
        int newX = this.offsetX + dx;
        int result = dx;
        if (newX > this.totalWidth) {
            result = this.totalWidth - this.offsetX;
        } else if (newX < 0) {
            result = 0 - this.offsetX;
        }
        this.offsetX += result;
        relayoutChildren(recycler, state);
        return result;
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public int scrollVerticallyBy(int dy, android.support.v7.widget.RecyclerView.Recycler recycler, android.support.v7.widget.RecyclerView.State state) {
        int newY = this.offsetY + dy;
        int result = dy;
        if (newY > this.totalHeight) {
            result = this.totalHeight - this.offsetY;
        } else if (newY < 0) {
            result = 0 - this.offsetY;
        }
        this.offsetY += result;
        relayoutChildren(recycler, state);
        return result;
    }

    private int getUsableWidth() {
        return (getWidth() - getPaddingLeft()) - getPaddingRight();
    }

    private int getUsableHeight() {
        return (getHeight() - getPaddingTop()) - getPaddingBottom();
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x0097, code lost:
    
        r4 = r4 + 1;
     */
    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onLayoutChildren(android.support.v7.widget.RecyclerView.Recycler r12, android.support.v7.widget.RecyclerView.State r13) {
        /*
            Method dump skipped, instructions count: 321
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autopai.applist.manager.HorizontalPageLayoutManager.onLayoutChildren(android.support.v7.widget.RecyclerView$Recycler, android.support.v7.widget.RecyclerView$State):void");
    }

    public int computePageSize() {
        this.pageSize = (getItemCount() % this.onePageSize == 0 ? 0 : 1) + (getItemCount() / this.onePageSize);
        if (this.mPageScrolls == null || this.mPageScrolls.length != this.pageSize) {
            this.mPageScrolls = new int[this.pageSize];
            for (int i = 0; i < this.pageSize; i++) {
                if (canScrollHorizontally()) {
                    this.mPageScrolls[i] = getWidth() * i;
                } else if (canScrollVertically()) {
                    this.mPageScrolls[i] = getHeight() * i;
                }
            }
        }
        return this.pageSize;
    }

    public int getItemWidth() {
        return this.itemWidth;
    }

    public int getItemHeight() {
        return this.itemHeight;
    }

    public int getScrollForPage(int index) {
        if (this.mPageScrolls == null || index >= this.mPageScrolls.length || index < 0) {
            return 0;
        }
        return this.mPageScrolls[index];
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public int getOffsetX() {
        return this.offsetX;
    }

    public int getOffsetY() {
        return this.offsetY;
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void onDetachedFromWindow(android.support.v7.widget.RecyclerView view, android.support.v7.widget.RecyclerView.Recycler recycler) {
        super.onDetachedFromWindow(view, recycler);
        this.offsetX = 0;
        this.offsetY = 0;
    }

    private void relayoutChildren(android.support.v7.widget.RecyclerView.Recycler recycler, android.support.v7.widget.RecyclerView.State state) {
        if (!state.isPreLayout()) {
            if (canScrollVertically()) {
                this.mDisplayRect.set(getPaddingLeft(), getPaddingTop() + this.offsetY, getWidth() - getPaddingRight(), (getHeight() - getPaddingBottom()) + this.offsetY);
            } else if (canScrollHorizontally()) {
                this.mDisplayRect.set(getPaddingLeft() + this.offsetX, getPaddingTop(), (getWidth() - getPaddingRight()) + this.offsetX, getHeight() - getPaddingBottom());
            } else {
                return;
            }
            recycleChildren(recycler);
            detachAndScrapAttachedViews(recycler);
            if (!onLayout(recycler, state)) {
                removeAndRecycleAllViews(recycler);
            }
        }
    }

    private boolean onLayout(android.support.v7.widget.RecyclerView.Recycler recycler, android.support.v7.widget.RecyclerView.State state) {
        int start;
        int end;
        if (canScrollHorizontally()) {
            int currentIndex = this.offsetX / getWidth();
            int nextIndex = this.offsetX % getWidth() > 0 ? currentIndex + 1 : currentIndex;
            start = this.rows * currentIndex * this.columns;
            end = (nextIndex + 1) * this.rows * this.columns;
            if (end > state.getItemCount()) {
                end = state.getItemCount();
            }
        } else {
            int currentIndex2 = this.offsetY / getHeight();
            int nextIndex2 = this.offsetY % getHeight() > 0 ? currentIndex2 + 1 : currentIndex2;
            start = this.rows * currentIndex2 * this.columns;
            end = (nextIndex2 + 1) * this.rows * this.columns;
            if (end > state.getItemCount()) {
                end = state.getItemCount();
            }
            android.util.Log.e(TAG, "onLayout: currentIndex: " + currentIndex2 + "/ nextIndex: " + nextIndex2 + "/ start: " + start + "/ end: " + end + "/ state.getItemCount(): " + state.getItemCount());
        }
        android.view.View view = null;
        for (int i = start; i < end; i++) {
            if (this.allItemFrames.get(i) == null) {
                return false;
            }
            if (android.graphics.Rect.intersects(this.mDisplayRect, this.allItemFrames.get(i))) {
                view = recycler.getViewForPosition(i);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                android.graphics.Rect rect = this.allItemFrames.get(i);
                if (canScrollVertically()) {
                    layoutDecoratedWithMargins(view, rect.left, rect.top - this.offsetY, rect.right, rect.bottom - this.offsetY);
                } else if (canScrollHorizontally()) {
                    layoutDecoratedWithMargins(view, rect.left - this.offsetX, rect.top, rect.right - this.offsetX, rect.bottom);
                }
            }
        }
        return view != null;
    }

    private void recycleChildren(android.support.v7.widget.RecyclerView.Recycler recycler) {
        getRecycleChildren();
        int size = this.mInvisibleViews.size();
        for (int i = 0; i < size; i++) {
            android.view.View child = this.mInvisibleViews.get(i);
            removeAndRecycleView(child, recycler);
        }
    }

    private void getRecycleChildren() {
        this.mInvisibleViews.clear();
        int size = getChildCount();
        for (int i = 0; i < size; i++) {
            android.view.View child = getChildAt(i);
            android.graphics.Rect childRect = this.allItemFrames.get(getPosition(child));
            if (!android.graphics.Rect.intersects(this.mDisplayRect, childRect)) {
                this.mInvisibleViews.add(child);
            }
        }
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollRange(android.support.v7.widget.RecyclerView.State state) {
        computePageSize();
        return this.pageSize * getWidth();
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollOffset(android.support.v7.widget.RecyclerView.State state) {
        return this.offsetX;
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollExtent(android.support.v7.widget.RecyclerView.State state) {
        return getWidth();
    }

    public int getPageIndex() {
        if (this.mPageScrolls == null) {
            return 0;
        }
        if (canScrollHorizontally()) {
            for (int i = 0; i < this.mPageScrolls.length; i++) {
                if (this.offsetX == this.mPageScrolls[i]) {
                    this.mCurrentPage = i;
                }
            }
        } else {
            for (int i2 = 0; i2 < this.mPageScrolls.length; i2++) {
                if (this.offsetY == this.mPageScrolls[i2]) {
                    this.mCurrentPage = i2;
                }
            }
        }
        return this.mCurrentPage;
    }
}
