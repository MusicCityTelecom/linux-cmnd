/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.checkerframework.checker.nullness.qual.Nullable
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.BoundedLocalCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Node;
import com.github.benmanes.caffeine.cache.RemovalCause;
import java.lang.ref.ReferenceQueue;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.checkerframework.checker.nullness.qual.Nullable;

final class TimerWheel<K, V>
implements Iterable<Node<K, V>> {
    static final int[] BUCKETS = new int[]{64, 64, 32, 4, 1};
    static final long[] SPANS = new long[]{Caffeine.ceilingPowerOfTwo(TimeUnit.SECONDS.toNanos(1L)), Caffeine.ceilingPowerOfTwo(TimeUnit.MINUTES.toNanos(1L)), Caffeine.ceilingPowerOfTwo(TimeUnit.HOURS.toNanos(1L)), Caffeine.ceilingPowerOfTwo(TimeUnit.DAYS.toNanos(1L)), (long)BUCKETS[3] * Caffeine.ceilingPowerOfTwo(TimeUnit.DAYS.toNanos(1L)), (long)BUCKETS[3] * Caffeine.ceilingPowerOfTwo(TimeUnit.DAYS.toNanos(1L))};
    static final long[] SHIFT = new long[]{Long.numberOfTrailingZeros(SPANS[0]), Long.numberOfTrailingZeros(SPANS[1]), Long.numberOfTrailingZeros(SPANS[2]), Long.numberOfTrailingZeros(SPANS[3]), Long.numberOfTrailingZeros(SPANS[4])};
    final BoundedLocalCache<K, V> cache;
    final Node<K, V>[][] wheel;
    long nanos;

    TimerWheel(BoundedLocalCache<K, V> cache) {
        this.cache = Objects.requireNonNull(cache);
        this.wheel = new Node[BUCKETS.length][];
        for (int i = 0; i < this.wheel.length; ++i) {
            this.wheel[i] = new Node[BUCKETS[i]];
            for (int j = 0; j < this.wheel[i].length; ++j) {
                this.wheel[i][j] = new Sentinel();
            }
        }
    }

    public void advance(long currentTimeNanos) {
        long previousTimeNanos = this.nanos;
        try {
            long previousTicks;
            long currentTicks;
            this.nanos = currentTimeNanos;
            if (previousTimeNanos < 0L && currentTimeNanos > 0L) {
                previousTimeNanos += Long.MAX_VALUE;
                currentTimeNanos += Long.MAX_VALUE;
            }
            for (int i = 0; i < SHIFT.length && (currentTicks = currentTimeNanos >>> (int)SHIFT[i]) - (previousTicks = previousTimeNanos >>> (int)SHIFT[i]) > 0L; ++i) {
                this.expire(i, previousTicks, currentTicks);
            }
        }
        catch (Throwable t) {
            this.nanos = previousTimeNanos;
            throw t;
        }
    }

    void expire(int index, long previousTicks, long currentTicks) {
        Node<K, V>[] timerWheel = this.wheel[index];
        int mask = timerWheel.length - 1;
        int steps = Math.min(1 + Math.abs((int)(currentTicks - previousTicks)), timerWheel.length);
        int start = (int)(previousTicks & (long)mask);
        int end = start + steps;
        for (int i = start; i < end; ++i) {
            Node<K, V> sentinel = timerWheel[i & mask];
            Node<K, V> prev = sentinel.getPreviousInVariableOrder();
            Node<K, V> node = sentinel.getNextInVariableOrder();
            sentinel.setPreviousInVariableOrder(sentinel);
            sentinel.setNextInVariableOrder(sentinel);
            while (node != sentinel) {
                Node<K, V> next = node.getNextInVariableOrder();
                node.setPreviousInVariableOrder(null);
                node.setNextInVariableOrder(null);
                try {
                    if (node.getVariableTime() - this.nanos > 0L || !this.cache.evictEntry(node, RemovalCause.EXPIRED, this.nanos)) {
                        this.schedule(node);
                    }
                    node = next;
                }
                catch (Throwable t) {
                    node.setPreviousInVariableOrder(sentinel.getPreviousInVariableOrder());
                    node.setNextInVariableOrder(next);
                    sentinel.getPreviousInVariableOrder().setNextInVariableOrder(node);
                    sentinel.setPreviousInVariableOrder(prev);
                    throw t;
                }
            }
        }
    }

    public void schedule(Node<K, V> node) {
        Node<K, V> sentinel = this.findBucket(node.getVariableTime());
        this.link(sentinel, node);
    }

    public void reschedule(Node<K, V> node) {
        if (node.getNextInVariableOrder() != null) {
            this.unlink(node);
            this.schedule(node);
        }
    }

    public void deschedule(Node<K, V> node) {
        this.unlink(node);
        node.setNextInVariableOrder(null);
        node.setPreviousInVariableOrder(null);
    }

    Node<K, V> findBucket(long time) {
        long duration = time - this.nanos;
        int length = this.wheel.length - 1;
        for (int i = 0; i < length; ++i) {
            if (duration >= SPANS[i + 1]) continue;
            long ticks = time >>> (int)SHIFT[i];
            int index = (int)(ticks & (long)(this.wheel[i].length - 1));
            return this.wheel[i][index];
        }
        return this.wheel[length][0];
    }

    void link(Node<K, V> sentinel, Node<K, V> node) {
        node.setPreviousInVariableOrder(sentinel.getPreviousInVariableOrder());
        node.setNextInVariableOrder(sentinel);
        sentinel.getPreviousInVariableOrder().setNextInVariableOrder(node);
        sentinel.setPreviousInVariableOrder(node);
    }

    void unlink(Node<K, V> node) {
        Node<K, V> next = node.getNextInVariableOrder();
        if (next != null) {
            Node<K, V> prev = node.getPreviousInVariableOrder();
            next.setPreviousInVariableOrder(prev);
            prev.setNextInVariableOrder(next);
        }
    }

    public long getExpirationDelay() {
        for (int i = 0; i < SHIFT.length; ++i) {
            Node<K, V>[] timerWheel = this.wheel[i];
            long ticks = this.nanos >>> (int)SHIFT[i];
            long spanMask = SPANS[i] - 1L;
            int start = (int)(ticks & spanMask);
            int end = start + timerWheel.length;
            int mask = timerWheel.length - 1;
            for (int j = start; j < end; ++j) {
                Node<K, V> sentinel = timerWheel[j & mask];
                Node<K, V> next = sentinel.getNextInVariableOrder();
                if (next == sentinel) continue;
                long buckets = j - start;
                long delay = (buckets << (int)SHIFT[i]) - (this.nanos & spanMask);
                delay = delay > 0L ? delay : SPANS[i];
                for (int k = i + 1; k < SHIFT.length; ++k) {
                    long nextDelay = this.peekAhead(k);
                    delay = Math.min(delay, nextDelay);
                }
                return delay;
            }
        }
        return Long.MAX_VALUE;
    }

    long peekAhead(int i) {
        long ticks = this.nanos >>> (int)SHIFT[i];
        Node<K, V>[] timerWheel = this.wheel[i];
        long spanMask = SPANS[i] - 1L;
        int mask = timerWheel.length - 1;
        int probe = (int)(ticks + 1L & (long)mask);
        Node<K, V> sentinel = timerWheel[probe];
        Node<K, V> next = sentinel.getNextInVariableOrder();
        return next == sentinel ? Long.MAX_VALUE : SPANS[i] - (this.nanos & spanMask);
    }

    @Override
    public Iterator<Node<K, V>> iterator() {
        return new AscendingIterator();
    }

    public Iterator<Node<K, V>> descendingIterator() {
        return new DescendingIterator();
    }

    static final class Sentinel<K, V>
    extends Node<K, V> {
        Node<K, V> prev;
        Node<K, V> next;

        Sentinel() {
            this.prev = this.next = this;
        }

        @Override
        public Node<K, V> getPreviousInVariableOrder() {
            return this.prev;
        }

        @Override
        public void setPreviousInVariableOrder(@Nullable Node<K, V> prev) {
            this.prev = prev;
        }

        @Override
        public Node<K, V> getNextInVariableOrder() {
            return this.next;
        }

        @Override
        public void setNextInVariableOrder(@Nullable Node<K, V> next) {
            this.next = next;
        }

        @Override
        public @Nullable K getKey() {
            return null;
        }

        @Override
        public Object getKeyReference() {
            throw new UnsupportedOperationException();
        }

        @Override
        public @Nullable V getValue() {
            return null;
        }

        @Override
        public Object getValueReference() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setValue(V value, @Nullable ReferenceQueue<V> referenceQueue) {
        }

        @Override
        public boolean containsValue(Object value) {
            return false;
        }

        @Override
        public boolean isAlive() {
            return false;
        }

        @Override
        public boolean isRetired() {
            return false;
        }

        @Override
        public boolean isDead() {
            return false;
        }

        @Override
        public void retire() {
        }

        @Override
        public void die() {
        }
    }

    final class DescendingIterator
    extends Traverser {
        int wheelIndex;
        int steps;

        DescendingIterator() {
            this.wheelIndex = TimerWheel.this.wheel.length - 1;
        }

        @Override
        boolean isDone() {
            return this.wheelIndex == -1;
        }

        @Override
        Node<K, V> sentinel() {
            return TimerWheel.this.wheel[this.wheelIndex][this.bucketIndex()];
        }

        @Override
        @Nullable Node<K, V> goToNextBucket() {
            return ++this.steps < TimerWheel.this.wheel[this.wheelIndex].length ? TimerWheel.this.wheel[this.wheelIndex][this.bucketIndex()] : null;
        }

        @Override
        @Nullable Node<K, V> goToNextWheel() {
            if (--this.wheelIndex < 0) {
                return null;
            }
            this.steps = 0;
            return TimerWheel.this.wheel[this.wheelIndex][this.bucketIndex()];
        }

        @Override
        Node<K, V> traverse(Node<K, V> node) {
            return node.getPreviousInVariableOrder();
        }

        int bucketIndex() {
            int ticks = (int)(TimerWheel.this.nanos >>> (int)SHIFT[this.wheelIndex]);
            int bucketMask = TimerWheel.this.wheel[this.wheelIndex].length - 1;
            int bucketOffset = ticks & bucketMask;
            return bucketOffset - this.steps & bucketMask;
        }
    }

    final class AscendingIterator
    extends Traverser {
        int wheelIndex;
        int steps;

        AscendingIterator() {
        }

        @Override
        boolean isDone() {
            return this.wheelIndex == TimerWheel.this.wheel.length;
        }

        @Override
        Node<K, V> sentinel() {
            return TimerWheel.this.wheel[this.wheelIndex][this.bucketIndex()];
        }

        @Override
        Node<K, V> traverse(Node<K, V> node) {
            return node.getNextInVariableOrder();
        }

        @Override
        @Nullable Node<K, V> goToNextBucket() {
            return ++this.steps < TimerWheel.this.wheel[this.wheelIndex].length ? TimerWheel.this.wheel[this.wheelIndex][this.bucketIndex()] : null;
        }

        @Override
        @Nullable Node<K, V> goToNextWheel() {
            if (++this.wheelIndex == TimerWheel.this.wheel.length) {
                return null;
            }
            this.steps = 0;
            return TimerWheel.this.wheel[this.wheelIndex][this.bucketIndex()];
        }

        int bucketIndex() {
            int ticks = (int)(TimerWheel.this.nanos >>> (int)SHIFT[this.wheelIndex]);
            int bucketMask = TimerWheel.this.wheel[this.wheelIndex].length - 1;
            int bucketOffset = (ticks & bucketMask) + 1;
            return bucketOffset + this.steps & bucketMask;
        }
    }

    abstract class Traverser
    implements Iterator<Node<K, V>> {
        final long expectedNanos;
        @Nullable Node<K, V> previous;
        @Nullable Node<K, V> current;

        Traverser() {
            this.expectedNanos = TimerWheel.this.nanos;
        }

        @Override
        public boolean hasNext() {
            if (TimerWheel.this.nanos != this.expectedNanos) {
                throw new ConcurrentModificationException();
            }
            if (this.current != null) {
                return true;
            }
            if (this.isDone()) {
                return false;
            }
            this.current = this.computeNext();
            return this.current != null;
        }

        @Override
        public Node<K, V> next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.previous = this.current;
            this.current = null;
            return this.previous;
        }

        @Nullable Node<K, V> computeNext() {
            Node node;
            Node node2 = node = this.previous == null ? this.sentinel() : this.previous;
            do {
                if ((node = this.traverse(node)) == this.sentinel()) continue;
                return node;
            } while ((node = this.goToNextBucket()) != null || (node = this.goToNextWheel()) != null);
            return null;
        }

        abstract boolean isDone();

        abstract Node<K, V> sentinel();

        abstract Node<K, V> traverse(Node<K, V> var1);

        abstract @Nullable Node<K, V> goToNextBucket();

        abstract @Nullable Node<K, V> goToNextWheel();
    }
}

