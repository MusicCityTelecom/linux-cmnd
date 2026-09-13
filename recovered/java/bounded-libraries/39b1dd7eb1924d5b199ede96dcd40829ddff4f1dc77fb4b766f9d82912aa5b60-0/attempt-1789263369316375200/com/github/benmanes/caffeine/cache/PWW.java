/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.Node;
import com.github.benmanes.caffeine.cache.PW;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.ref.ReferenceQueue;

class PWW<K, V>
extends PW<K, V> {
    protected static final VarHandle WRITE_TIME;
    volatile long writeTime;
    Node<K, V> previousInWriteOrder;
    Node<K, V> nextInWriteOrder;

    PWW() {
    }

    PWW(K k, ReferenceQueue<K> referenceQueue, V v, ReferenceQueue<V> referenceQueue2, int n, long l) {
        super(k, referenceQueue, v, referenceQueue2, n, l);
        WRITE_TIME.set(this, l & 0xFFFFFFFFFFFFFFFEL);
    }

    PWW(Object object, V v, ReferenceQueue<V> referenceQueue, int n, long l) {
        super(object, v, referenceQueue, n, l);
        WRITE_TIME.set(this, l & 0xFFFFFFFFFFFFFFFEL);
    }

    @Override
    public Node<K, V> getPreviousInVariableOrder() {
        return this.previousInWriteOrder;
    }

    @Override
    public void setPreviousInVariableOrder(Node<K, V> node) {
        this.previousInWriteOrder = node;
    }

    @Override
    public Node<K, V> getNextInVariableOrder() {
        return this.nextInWriteOrder;
    }

    @Override
    public void setNextInVariableOrder(Node<K, V> node) {
        this.nextInWriteOrder = node;
    }

    @Override
    public long getVariableTime() {
        return WRITE_TIME.getOpaque(this);
    }

    @Override
    public void setVariableTime(long l) {
        WRITE_TIME.setOpaque(this, l);
    }

    @Override
    public boolean casVariableTime(long l, long l2) {
        return this.writeTime == l && WRITE_TIME.compareAndSet(this, l, l2);
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
    public final Node<K, V> getPreviousInWriteOrder() {
        return this.previousInWriteOrder;
    }

    @Override
    public final void setPreviousInWriteOrder(Node<K, V> node) {
        this.previousInWriteOrder = node;
    }

    @Override
    public final Node<K, V> getNextInWriteOrder() {
        return this.nextInWriteOrder;
    }

    @Override
    public final void setNextInWriteOrder(Node<K, V> node) {
        this.nextInWriteOrder = node;
    }

    @Override
    public Node<K, V> newNode(K k, ReferenceQueue<K> referenceQueue, V v, ReferenceQueue<V> referenceQueue2, int n, long l) {
        return new PWW<K, V>(k, referenceQueue, v, referenceQueue2, n, l);
    }

    @Override
    public Node<K, V> newNode(Object object, V v, ReferenceQueue<V> referenceQueue, int n, long l) {
        return new PWW<K, V>(object, v, referenceQueue, n, l);
    }

    static {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            WRITE_TIME = lookup.findVarHandle(PWW.class, "writeTime", Long.TYPE);
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new ExceptionInInitializerError(reflectiveOperationException);
        }
    }
}

