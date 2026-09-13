/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.BaseMpscLinkedArrayQueueColdProducerFields;
import com.github.benmanes.caffeine.cache.BaseMpscLinkedArrayQueueConsumerFields;
import com.github.benmanes.caffeine.cache.BaseMpscLinkedArrayQueueProducerFields;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.Iterator;

abstract class BaseMpscLinkedArrayQueue<E>
extends BaseMpscLinkedArrayQueueColdProducerFields<E> {
    static final VarHandle REF_ARRAY;
    static final VarHandle P_INDEX;
    static final VarHandle C_INDEX;
    static final VarHandle P_LIMIT;
    private static final Object JUMP;

    BaseMpscLinkedArrayQueue(int initialCapacity) {
        if (initialCapacity < 2) {
            throw new IllegalArgumentException("Initial capacity must be 2 or more");
        }
        int p2capacity = Caffeine.ceilingPowerOfTwo(initialCapacity);
        long mask = (long)p2capacity - 1L << 1;
        E[] buffer = BaseMpscLinkedArrayQueue.allocate(p2capacity + 1);
        this.producerBuffer = buffer;
        this.producerMask = mask;
        this.consumerBuffer = buffer;
        this.consumerMask = mask;
        BaseMpscLinkedArrayQueue.soProducerLimit(this, mask);
    }

    @Override
    public final Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "@" + Integer.toHexString(this.hashCode());
    }

    @Override
    public boolean offer(E e) {
        Object[] buffer;
        long mask;
        long pIndex;
        if (null == e) {
            throw new NullPointerException();
        }
        block6: while (true) {
            long producerLimit = this.lvProducerLimit();
            pIndex = BaseMpscLinkedArrayQueue.lvProducerIndex(this);
            if ((pIndex & 1L) == 1L) continue;
            mask = this.producerMask;
            buffer = this.producerBuffer;
            if (producerLimit <= pIndex) {
                int result = this.offerSlowPath(mask, pIndex, producerLimit);
                switch (result) {
                    case 0: {
                        break;
                    }
                    case 1: {
                        continue block6;
                    }
                    case 2: {
                        return false;
                    }
                    case 3: {
                        this.resize(mask, buffer, pIndex, e);
                        return true;
                    }
                }
            }
            if (BaseMpscLinkedArrayQueue.casProducerIndex(this, pIndex, pIndex + 2L)) break;
        }
        long offset = BaseMpscLinkedArrayQueue.modifiedCalcElementOffset(pIndex, mask);
        BaseMpscLinkedArrayQueue.soElement(buffer, offset, e);
        return true;
    }

    private int offerSlowPath(long mask, long pIndex, long producerLimit) {
        long cIndex = BaseMpscLinkedArrayQueue.lvConsumerIndex(this);
        long bufferCapacity = this.getCurrentBufferCapacity(mask);
        int result = 0;
        if (cIndex + bufferCapacity > pIndex) {
            if (!BaseMpscLinkedArrayQueue.casProducerLimit(this, producerLimit, cIndex + bufferCapacity)) {
                result = 1;
            }
        } else {
            result = this.availableInQueue(pIndex, cIndex) <= 0L ? 2 : (BaseMpscLinkedArrayQueue.casProducerIndex(this, pIndex, pIndex + 1L) ? 3 : 1);
        }
        return result;
    }

    protected abstract long availableInQueue(long var1, long var3);

    @Override
    public E poll() {
        Object[] buffer = this.consumerBuffer;
        long index = this.consumerIndex;
        long mask = this.consumerMask;
        long offset = BaseMpscLinkedArrayQueue.modifiedCalcElementOffset(index, mask);
        Object e = BaseMpscLinkedArrayQueue.lvElement(buffer, offset);
        if (e == null) {
            if (index != BaseMpscLinkedArrayQueue.lvProducerIndex(this)) {
                while ((e = BaseMpscLinkedArrayQueue.lvElement(buffer, offset)) == null) {
                }
            } else {
                return null;
            }
        }
        if (e == JUMP) {
            Object[] nextBuffer = this.getNextBuffer(buffer, mask);
            return (E)this.newBufferPoll(nextBuffer, index);
        }
        BaseMpscLinkedArrayQueue.soElement(buffer, offset, null);
        BaseMpscLinkedArrayQueue.soConsumerIndex(this, index + 2L);
        return (E)e;
    }

    @Override
    public E peek() {
        Object[] buffer = this.consumerBuffer;
        long index = this.consumerIndex;
        long mask = this.consumerMask;
        long offset = BaseMpscLinkedArrayQueue.modifiedCalcElementOffset(index, mask);
        Object e = BaseMpscLinkedArrayQueue.lvElement(buffer, offset);
        if (e == null && index != BaseMpscLinkedArrayQueue.lvProducerIndex(this)) {
            while ((e = BaseMpscLinkedArrayQueue.lvElement(buffer, offset)) == null) {
            }
        }
        if (e == JUMP) {
            return (E)this.newBufferPeek(this.getNextBuffer(buffer, mask), index);
        }
        return (E)e;
    }

    private E[] getNextBuffer(E[] buffer, long mask) {
        long nextArrayOffset = this.nextArrayOffset(mask);
        Object[] nextBuffer = (Object[])BaseMpscLinkedArrayQueue.lvElement(buffer, nextArrayOffset);
        BaseMpscLinkedArrayQueue.soElement(buffer, nextArrayOffset, null);
        return nextBuffer;
    }

    private long nextArrayOffset(long mask) {
        return BaseMpscLinkedArrayQueue.modifiedCalcElementOffset(mask + 2L, Long.MAX_VALUE);
    }

    private E newBufferPoll(E[] nextBuffer, long index) {
        long offsetInNew = this.newBufferAndOffset(nextBuffer, index);
        E n = BaseMpscLinkedArrayQueue.lvElement(nextBuffer, offsetInNew);
        if (n == null) {
            throw new IllegalStateException("new buffer must have at least one element");
        }
        BaseMpscLinkedArrayQueue.soElement(nextBuffer, offsetInNew, null);
        BaseMpscLinkedArrayQueue.soConsumerIndex(this, index + 2L);
        return n;
    }

    private E newBufferPeek(E[] nextBuffer, long index) {
        long offsetInNew = this.newBufferAndOffset(nextBuffer, index);
        E n = BaseMpscLinkedArrayQueue.lvElement(nextBuffer, offsetInNew);
        if (null == n) {
            throw new IllegalStateException("new buffer must have at least one element");
        }
        return n;
    }

    private long newBufferAndOffset(E[] nextBuffer, long index) {
        this.consumerBuffer = nextBuffer;
        this.consumerMask = (long)nextBuffer.length - 2L << 1;
        long offsetInNew = BaseMpscLinkedArrayQueue.modifiedCalcElementOffset(index, this.consumerMask);
        return offsetInNew;
    }

    @Override
    public final int size() {
        long currentProducerIndex;
        long before;
        long after = BaseMpscLinkedArrayQueue.lvConsumerIndex(this);
        do {
            before = after;
            currentProducerIndex = BaseMpscLinkedArrayQueue.lvProducerIndex(this);
        } while (before != (after = BaseMpscLinkedArrayQueue.lvConsumerIndex(this)));
        long size = currentProducerIndex - after >> 1;
        if (size > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int)size;
    }

    @Override
    public final boolean isEmpty() {
        return BaseMpscLinkedArrayQueue.lvConsumerIndex(this) == BaseMpscLinkedArrayQueue.lvProducerIndex(this);
    }

    private long lvProducerLimit() {
        return this.producerLimit;
    }

    public long currentProducerIndex() {
        return BaseMpscLinkedArrayQueue.lvProducerIndex(this) / 2L;
    }

    public long currentConsumerIndex() {
        return BaseMpscLinkedArrayQueue.lvConsumerIndex(this) / 2L;
    }

    public abstract int capacity();

    public boolean relaxedOffer(E e) {
        return this.offer(e);
    }

    public E relaxedPoll() {
        Object[] buffer = this.consumerBuffer;
        long index = this.consumerIndex;
        long mask = this.consumerMask;
        long offset = BaseMpscLinkedArrayQueue.modifiedCalcElementOffset(index, mask);
        Object e = BaseMpscLinkedArrayQueue.lvElement(buffer, offset);
        if (e == null) {
            return null;
        }
        if (e == JUMP) {
            Object[] nextBuffer = this.getNextBuffer(buffer, mask);
            return (E)this.newBufferPoll(nextBuffer, index);
        }
        BaseMpscLinkedArrayQueue.soElement(buffer, offset, null);
        BaseMpscLinkedArrayQueue.soConsumerIndex(this, index + 2L);
        return (E)e;
    }

    public E relaxedPeek() {
        Object[] buffer = this.consumerBuffer;
        long index = this.consumerIndex;
        long mask = this.consumerMask;
        long offset = BaseMpscLinkedArrayQueue.modifiedCalcElementOffset(index, mask);
        Object e = BaseMpscLinkedArrayQueue.lvElement(buffer, offset);
        if (e == JUMP) {
            return (E)this.newBufferPeek(this.getNextBuffer(buffer, mask), index);
        }
        return (E)e;
    }

    private void resize(long oldMask, E[] oldBuffer, long pIndex, E e) {
        int newBufferLength = this.getNextBufferSize(oldBuffer);
        E[] newBuffer = BaseMpscLinkedArrayQueue.allocate(newBufferLength);
        this.producerBuffer = newBuffer;
        int newMask = newBufferLength - 2 << 1;
        this.producerMask = newMask;
        long offsetInOld = BaseMpscLinkedArrayQueue.modifiedCalcElementOffset(pIndex, oldMask);
        long offsetInNew = BaseMpscLinkedArrayQueue.modifiedCalcElementOffset(pIndex, newMask);
        BaseMpscLinkedArrayQueue.soElement(newBuffer, offsetInNew, e);
        BaseMpscLinkedArrayQueue.soElement(oldBuffer, this.nextArrayOffset(oldMask), newBuffer);
        long cIndex = BaseMpscLinkedArrayQueue.lvConsumerIndex(this);
        long availableInQueue = this.availableInQueue(pIndex, cIndex);
        if (availableInQueue <= 0L) {
            throw new IllegalStateException();
        }
        BaseMpscLinkedArrayQueue.soProducerLimit(this, pIndex + Math.min((long)newMask, availableInQueue));
        BaseMpscLinkedArrayQueue.soProducerIndex(this, pIndex + 2L);
        BaseMpscLinkedArrayQueue.soElement(oldBuffer, offsetInOld, JUMP);
    }

    public static <E> E[] allocate(int capacity) {
        return new Object[capacity];
    }

    protected abstract int getNextBufferSize(E[] var1);

    protected abstract long getCurrentBufferCapacity(long var1);

    static long lvProducerIndex(BaseMpscLinkedArrayQueue<?> self) {
        return P_INDEX.getVolatile(self);
    }

    static long lvConsumerIndex(BaseMpscLinkedArrayQueue<?> self) {
        return C_INDEX.getVolatile(self);
    }

    static void soProducerIndex(BaseMpscLinkedArrayQueue<?> self, long v) {
        P_INDEX.setRelease(self, v);
    }

    static boolean casProducerIndex(BaseMpscLinkedArrayQueue<?> self, long expect, long newValue) {
        return P_INDEX.compareAndSet(self, expect, newValue);
    }

    static void soConsumerIndex(BaseMpscLinkedArrayQueue<?> self, long v) {
        C_INDEX.setRelease(self, v);
    }

    static boolean casProducerLimit(BaseMpscLinkedArrayQueue<?> self, long expect, long newValue) {
        return P_LIMIT.compareAndSet(self, expect, newValue);
    }

    static void soProducerLimit(BaseMpscLinkedArrayQueue<?> self, long v) {
        P_LIMIT.setRelease(self, v);
    }

    static <E> void spElement(E[] buffer, long offset, E e) {
        REF_ARRAY.set(buffer, (int)offset, e);
    }

    static <E> void soElement(E[] buffer, long offset, E e) {
        REF_ARRAY.setRelease(buffer, (int)offset, e);
    }

    static <E> E lpElement(E[] buffer, long offset) {
        return (E)REF_ARRAY.get(buffer, (int)offset);
    }

    static <E> E lvElement(E[] buffer, long offset) {
        return (E)REF_ARRAY.getVolatile(buffer, (int)offset);
    }

    static long calcElementOffset(long index) {
        return index >> 1;
    }

    static long modifiedCalcElementOffset(long index, long mask) {
        return (index & mask) >> 1;
    }

    static {
        JUMP = new Object();
        try {
            MethodHandles.Lookup pIndexLookup = MethodHandles.privateLookupIn(BaseMpscLinkedArrayQueueProducerFields.class, MethodHandles.lookup());
            MethodHandles.Lookup cIndexLookup = MethodHandles.privateLookupIn(BaseMpscLinkedArrayQueueConsumerFields.class, MethodHandles.lookup());
            MethodHandles.Lookup pLimitLookup = MethodHandles.privateLookupIn(BaseMpscLinkedArrayQueueColdProducerFields.class, MethodHandles.lookup());
            P_INDEX = pIndexLookup.findVarHandle(BaseMpscLinkedArrayQueueProducerFields.class, "producerIndex", Long.TYPE);
            C_INDEX = cIndexLookup.findVarHandle(BaseMpscLinkedArrayQueueConsumerFields.class, "consumerIndex", Long.TYPE);
            P_LIMIT = pLimitLookup.findVarHandle(BaseMpscLinkedArrayQueueColdProducerFields.class, "producerLimit", Long.TYPE);
            REF_ARRAY = MethodHandles.arrayElementVarHandle(Object[].class);
        }
        catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}

