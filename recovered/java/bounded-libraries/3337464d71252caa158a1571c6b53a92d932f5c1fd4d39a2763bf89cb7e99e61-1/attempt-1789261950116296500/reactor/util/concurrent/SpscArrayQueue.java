/*
 * Decompiled with CFR 0.152.
 */
package reactor.util.concurrent;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Queue;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.concurrent.SpscArrayQueueP3;

final class SpscArrayQueue<T>
extends SpscArrayQueueP3<T>
implements Queue<T> {
    private static final long serialVersionUID = 494623116936946976L;

    SpscArrayQueue(int capacity) {
        super(Queues.ceilingNextPowerOfTwo(capacity));
    }

    @Override
    public boolean offer(T e) {
        Objects.requireNonNull(e, "e");
        long pi = this.producerIndex;
        int offset = (int)pi & this.mask;
        if (this.get(offset) != null) {
            return false;
        }
        this.lazySet(offset, e);
        PRODUCER_INDEX.lazySet(this, pi + 1L);
        return true;
    }

    @Override
    @Nullable
    public T poll() {
        long ci = this.consumerIndex;
        int offset = (int)ci & this.mask;
        Object v = this.get(offset);
        if (v != null) {
            this.lazySet(offset, null);
            CONSUMER_INDEX.lazySet(this, ci + 1L);
        }
        return (T)v;
    }

    @Override
    @Nullable
    public T peek() {
        int offset = (int)this.consumerIndex & this.mask;
        return (T)this.get(offset);
    }

    @Override
    public boolean isEmpty() {
        return this.producerIndex == this.consumerIndex;
    }

    @Override
    public void clear() {
        while (this.poll() != null && !this.isEmpty()) {
        }
    }

    @Override
    public int size() {
        long ci = this.consumerIndex;
        while (true) {
            long pi = this.producerIndex;
            long ci2 = this.consumerIndex;
            if (ci == ci2) {
                return (int)(pi - ci);
            }
            ci = ci2;
        }
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <R> R[] toArray(R[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(T e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T element() {
        throw new UnsupportedOperationException();
    }
}

