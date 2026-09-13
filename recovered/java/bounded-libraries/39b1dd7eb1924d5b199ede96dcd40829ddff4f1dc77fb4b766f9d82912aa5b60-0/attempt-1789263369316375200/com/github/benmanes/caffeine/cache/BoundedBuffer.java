/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.BBHeader;
import com.github.benmanes.caffeine.cache.Buffer;
import com.github.benmanes.caffeine.cache.StripedBuffer;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.function.Consumer;

final class BoundedBuffer<E>
extends StripedBuffer<E> {
    static final int BUFFER_SIZE = 16;
    static final int MASK = 15;

    BoundedBuffer() {
    }

    @Override
    protected Buffer<E> create(E e) {
        return new RingBuffer<E>(e);
    }

    static final class RingBuffer<E>
    extends BBHeader.ReadAndWriteCounterRef
    implements Buffer<E> {
        static final VarHandle BUFFER = MethodHandles.arrayElementVarHandle(Object[].class);
        final Object[] buffer = new Object[16];

        public RingBuffer(E e) {
            BUFFER.set(this.buffer, 0, e);
        }

        @Override
        public int offer(E e) {
            long head = this.readCounter;
            long tail = this.writeCounterOpaque();
            long size = tail - head;
            if (size >= 16L) {
                return 1;
            }
            if (this.casWriteCounter(tail, tail + 1L)) {
                int index = (int)(tail & 0xFL);
                BUFFER.setRelease(this.buffer, index, e);
                return 0;
            }
            return -1;
        }

        @Override
        public void drainTo(Consumer<E> consumer) {
            int index;
            Object e;
            long head = this.readCounter;
            long tail = this.writeCounterOpaque();
            long size = tail - head;
            if (size == 0L) {
                return;
            }
            while ((e = BUFFER.getAcquire(this.buffer, index = (int)(head & 0xFL))) != null) {
                BUFFER.setRelease(this.buffer, index, null);
                consumer.accept(e);
                if (++head != tail) continue;
            }
            this.setReadCounterOpaque(head);
        }

        @Override
        public long reads() {
            return this.readCounter;
        }

        @Override
        public long writes() {
            return this.writeCounter;
        }
    }
}

