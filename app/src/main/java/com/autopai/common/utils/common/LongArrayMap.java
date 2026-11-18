package com.autopai.common.utils.common;

/* loaded from: classes.dex */
public class LongArrayMap<E> extends android.util.LongSparseArray<E> implements java.lang.Iterable<E> {
    public boolean containsKey(long key) {
        return indexOfKey(key) >= 0;
    }

    public boolean isEmpty() {
        return size() <= 0;
    }

    @Override // android.util.LongSparseArray
    public com.autopai.common.utils.common.LongArrayMap<E> clone() {
        return (com.autopai.common.utils.common.LongArrayMap) super.clone();
    }

    @Override // java.lang.Iterable
    public java.util.Iterator<E> iterator() {
        return new com.autopai.common.utils.common.LongArrayMap.ValueIterator();
    }

    class ValueIterator implements java.util.Iterator<E> {
        private int mNextIndex = 0;

        ValueIterator() {
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.mNextIndex < com.autopai.common.utils.common.LongArrayMap.this.size();
        }

        @Override // java.util.Iterator
        public E next() {
            com.autopai.common.utils.common.LongArrayMap<E> longArrayMap = com.autopai.common.utils.common.LongArrayMap.this;
            int i = this.mNextIndex;
            this.mNextIndex = i + 1;
            return longArrayMap.valueAt(i);
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }
}
