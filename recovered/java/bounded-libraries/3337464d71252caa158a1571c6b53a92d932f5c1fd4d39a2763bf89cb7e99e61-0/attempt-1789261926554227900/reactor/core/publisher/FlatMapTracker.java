/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

abstract class FlatMapTracker<T> {
    volatile T[] array = this.empty();
    int[] free = FREE_EMPTY;
    long producerIndex;
    long consumerIndex;
    volatile int size;
    static final AtomicIntegerFieldUpdater<FlatMapTracker> SIZE = AtomicIntegerFieldUpdater.newUpdater(FlatMapTracker.class, "size");
    static final int[] FREE_EMPTY = new int[0];

    FlatMapTracker() {
    }

    abstract T[] empty();

    abstract T[] terminated();

    abstract T[] newArray(int var1);

    abstract void unsubscribeEntry(T var1);

    abstract void setIndex(T var1, int var2);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    final void unsubscribe() {
        T[] t = this.terminated();
        T[] TArray = this;
        synchronized (this) {
            T[] a = this.array;
            if (a == t) {
                // ** MonitorExit[var3_2] (shouldn't be in output)
                return;
            }
            SIZE.lazySet((FlatMapTracker)this, 0);
            this.free = null;
            this.array = t;
            // ** MonitorExit[var3_2] (shouldn't be in output)
            for (T e : a) {
                if (e == null) continue;
                this.unsubscribeEntry(e);
            }
            return;
        }
    }

    final T[] get() {
        return this.array;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    final boolean add(T entry) {
        T[] a = this.array;
        if (a == this.terminated()) {
            return false;
        }
        FlatMapTracker flatMapTracker = this;
        synchronized (flatMapTracker) {
            a = this.array;
            if (a == this.terminated()) {
                return false;
            }
            int idx = this.pollFree();
            if (idx < 0) {
                int n = a.length;
                T[] b = n != 0 ? this.newArray(n << 1) : this.newArray(4);
                System.arraycopy(a, 0, b, 0, n);
                this.array = b;
                a = b;
                int m = b.length;
                int[] u = new int[m];
                for (int i = n + 1; i < m; ++i) {
                    u[i] = i;
                }
                this.free = u;
                this.consumerIndex = n + 1;
                this.producerIndex = m;
                idx = n;
            }
            this.setIndex(entry, idx);
            SIZE.lazySet(this, this.size);
            a[idx] = entry;
            SIZE.lazySet(this, this.size + 1);
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    final void remove(int index) {
        FlatMapTracker flatMapTracker = this;
        synchronized (flatMapTracker) {
            T[] a = this.array;
            if (a != this.terminated()) {
                a[index] = null;
                this.offerFree(index);
                SIZE.lazySet(this, this.size - 1);
            }
        }
    }

    int pollFree() {
        int[] a = this.free;
        int m = a.length - 1;
        long ci = this.consumerIndex;
        if (this.producerIndex == ci) {
            return -1;
        }
        int offset = (int)ci & m;
        this.consumerIndex = ci + 1L;
        return a[offset];
    }

    void offerFree(int index) {
        int[] a = this.free;
        int m = a.length - 1;
        long pi = this.producerIndex;
        int offset = (int)pi & m;
        a[offset] = index;
        this.producerIndex = pi + 1L;
    }

    final boolean isEmpty() {
        return this.size == 0;
    }
}

