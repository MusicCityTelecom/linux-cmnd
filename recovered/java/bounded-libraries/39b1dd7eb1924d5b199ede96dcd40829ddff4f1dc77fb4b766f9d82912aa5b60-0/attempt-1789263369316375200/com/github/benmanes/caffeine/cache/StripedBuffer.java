/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.checkerframework.checker.nullness.qual.Nullable
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.Buffer;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.Arrays;
import java.util.function.Consumer;
import org.checkerframework.checker.nullness.qual.Nullable;

abstract class StripedBuffer<E>
implements Buffer<E> {
    static final VarHandle TABLE_BUSY;
    static final int NCPU;
    static final int MAXIMUM_TABLE_SIZE;
    static final int ATTEMPTS = 3;
    volatile Buffer<E> @Nullable [] table;
    volatile int tableBusy;

    StripedBuffer() {
    }

    final boolean casTableBusy() {
        return TABLE_BUSY.compareAndSet(this, 0, 1);
    }

    protected abstract Buffer<E> create(E var1);

    @Override
    public int offer(E e) {
        int result;
        Buffer<E> buffer;
        int mask;
        long z = StripedBuffer.mix64(Thread.currentThread().getId());
        int increment = (int)(z >>> 32) | 1;
        int h = (int)z;
        boolean uncontended = true;
        Buffer<E>[] buffers = this.table;
        if (buffers == null || (mask = buffers.length - 1) < 0 || (buffer = buffers[h & mask]) == null || !(uncontended = (result = buffer.offer(e)) != -1)) {
            return this.expandOrRetry(e, h, increment, uncontended);
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    final int expandOrRetry(E e, int h, int increment, boolean wasUncontended) {
        int result = -1;
        boolean collide = false;
        for (int attempt = 0; attempt < 3; ++attempt) {
            Buffer[] rs;
            int n;
            Buffer<E>[] buffers = this.table;
            if (this.table != null && (n = buffers.length) > 0) {
                Buffer<E> buffer = buffers[n - 1 & h];
                if (buffer == null) {
                    if (this.tableBusy == 0 && this.casTableBusy()) {
                        boolean created = false;
                        try {
                            int j;
                            int mask;
                            rs = this.table;
                            if (this.table != null && (mask = rs.length) > 0 && rs[j = mask - 1 & h] == null) {
                                rs[j] = this.create(e);
                                created = true;
                            }
                        }
                        finally {
                            this.tableBusy = 0;
                        }
                        if (!created) continue;
                        break;
                    }
                    collide = false;
                } else if (!wasUncontended) {
                    wasUncontended = true;
                } else {
                    result = buffer.offer(e);
                    if (result != -1) break;
                    if (n >= MAXIMUM_TABLE_SIZE || this.table != buffers) {
                        collide = false;
                    } else if (!collide) {
                        collide = true;
                    } else if (this.tableBusy == 0 && this.casTableBusy()) {
                        try {
                            if (this.table == buffers) {
                                this.table = Arrays.copyOf(buffers, n << 1);
                            }
                        }
                        finally {
                            this.tableBusy = 0;
                        }
                        collide = false;
                        continue;
                    }
                }
                h += increment;
                continue;
            }
            if (this.tableBusy != 0 || this.table != buffers || !this.casTableBusy()) continue;
            boolean init = false;
            try {
                if (this.table == buffers) {
                    rs = new Buffer[]{this.create(e)};
                    this.table = rs;
                    init = true;
                }
            }
            finally {
                this.tableBusy = 0;
            }
            if (init) break;
        }
        return result;
    }

    @Override
    public void drainTo(Consumer<E> consumer) {
        Buffer<E>[] buffers = this.table;
        if (buffers == null) {
            return;
        }
        for (Buffer<E> buffer : buffers) {
            if (buffer == null) continue;
            buffer.drainTo(consumer);
        }
    }

    @Override
    public long reads() {
        Buffer<E>[] buffers = this.table;
        if (buffers == null) {
            return 0L;
        }
        long reads = 0L;
        for (Buffer<E> buffer : buffers) {
            if (buffer == null) continue;
            reads += buffer.reads();
        }
        return reads;
    }

    @Override
    public long writes() {
        Buffer<E>[] buffers = this.table;
        if (buffers == null) {
            return 0L;
        }
        long writes = 0L;
        for (Buffer<E> buffer : buffers) {
            if (buffer == null) continue;
            writes += buffer.writes();
        }
        return writes;
    }

    static long mix64(long z) {
        z = (z ^ z >>> 30) * -4658895280553007687L;
        z = (z ^ z >>> 27) * -7723592293110705685L;
        return z ^ z >>> 31;
    }

    static {
        NCPU = Runtime.getRuntime().availableProcessors();
        MAXIMUM_TABLE_SIZE = 4 * Caffeine.ceilingPowerOfTwo(NCPU);
        try {
            TABLE_BUSY = MethodHandles.lookup().findVarHandle(StripedBuffer.class, "tableBusy", Integer.TYPE);
        }
        catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}

