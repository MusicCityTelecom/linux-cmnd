/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.Node;
import com.github.benmanes.caffeine.cache.PDA;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.ref.ReferenceQueue;

class PDAR<K, V>
extends PDA<K, V> {
    protected static final VarHandle WRITE_TIME;
    volatile long writeTime;

    PDAR() {
    }

    PDAR(K k, ReferenceQueue<K> referenceQueue, V v, ReferenceQueue<V> referenceQueue2, int n, long l) {
        super(k, referenceQueue, v, referenceQueue2, n, l);
        WRITE_TIME.set(this, l & 0xFFFFFFFFFFFFFFFEL);
    }

    PDAR(Object object, V v, ReferenceQueue<V> referenceQueue, int n, long l) {
        super(object, v, referenceQueue, n, l);
        WRITE_TIME.set(this, l & 0xFFFFFFFFFFFFFFFEL);
    }

    @Override
    public final long getWriteTime() {
        return WRITE_TIME.getOpaque(this);
    }

    @Override
    public final void setWriteTime(long l) {
        WRITE_TIME.set(this, l);
    }

    @Override
    public final boolean casWriteTime(long l, long l2) {
        return this.writeTime == l && WRITE_TIME.compareAndSet(this, l, l2);
    }

    @Override
    public Node<K, V> newNode(K k, ReferenceQueue<K> referenceQueue, V v, ReferenceQueue<V> referenceQueue2, int n, long l) {
        return new PDAR<K, V>(k, referenceQueue, v, referenceQueue2, n, l);
    }

    @Override
    public Node<K, V> newNode(Object object, V v, ReferenceQueue<V> referenceQueue, int n, long l) {
        return new PDAR<K, V>(object, v, referenceQueue, n, l);
    }

    static {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            WRITE_TIME = lookup.findVarHandle(PDAR.class, "writeTime", Long.TYPE);
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new ExceptionInInitializerError(reflectiveOperationException);
        }
    }
}

