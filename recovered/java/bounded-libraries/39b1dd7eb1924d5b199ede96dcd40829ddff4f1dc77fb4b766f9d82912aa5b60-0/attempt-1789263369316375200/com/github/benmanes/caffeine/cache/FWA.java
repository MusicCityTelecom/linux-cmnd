/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.FW;
import com.github.benmanes.caffeine.cache.Node;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.ref.ReferenceQueue;

class FWA<K, V>
extends FW<K, V> {
    protected static final VarHandle ACCESS_TIME;
    volatile long accessTime;
    Node<K, V> previousInAccessOrder;
    Node<K, V> nextInAccessOrder;

    FWA() {
    }

    FWA(K k, ReferenceQueue<K> referenceQueue, V v, ReferenceQueue<V> referenceQueue2, int n, long l) {
        super(k, referenceQueue, v, referenceQueue2, n, l);
        ACCESS_TIME.set(this, l);
    }

    FWA(Object object, V v, ReferenceQueue<V> referenceQueue, int n, long l) {
        super(object, v, referenceQueue, n, l);
        ACCESS_TIME.set(this, l);
    }

    @Override
    public Node<K, V> getPreviousInVariableOrder() {
        return this.previousInAccessOrder;
    }

    @Override
    public void setPreviousInVariableOrder(Node<K, V> node) {
        this.previousInAccessOrder = node;
    }

    @Override
    public Node<K, V> getNextInVariableOrder() {
        return this.nextInAccessOrder;
    }

    @Override
    public void setNextInVariableOrder(Node<K, V> node) {
        this.nextInAccessOrder = node;
    }

    @Override
    public long getVariableTime() {
        return ACCESS_TIME.getOpaque(this);
    }

    @Override
    public void setVariableTime(long l) {
        ACCESS_TIME.setOpaque(this, l);
    }

    @Override
    public boolean casVariableTime(long l, long l2) {
        return this.accessTime == l && ACCESS_TIME.compareAndSet(this, l, l2);
    }

    @Override
    public final long getAccessTime() {
        return ACCESS_TIME.getOpaque(this);
    }

    @Override
    public final void setAccessTime(long l) {
        ACCESS_TIME.setOpaque(this, l);
    }

    @Override
    public final Node<K, V> getPreviousInAccessOrder() {
        return this.previousInAccessOrder;
    }

    @Override
    public final void setPreviousInAccessOrder(Node<K, V> node) {
        this.previousInAccessOrder = node;
    }

    @Override
    public final Node<K, V> getNextInAccessOrder() {
        return this.nextInAccessOrder;
    }

    @Override
    public final void setNextInAccessOrder(Node<K, V> node) {
        this.nextInAccessOrder = node;
    }

    @Override
    public Node<K, V> newNode(K k, ReferenceQueue<K> referenceQueue, V v, ReferenceQueue<V> referenceQueue2, int n, long l) {
        return new FWA<K, V>(k, referenceQueue, v, referenceQueue2, n, l);
    }

    @Override
    public Node<K, V> newNode(Object object, V v, ReferenceQueue<V> referenceQueue, int n, long l) {
        return new FWA<K, V>(object, v, referenceQueue, n, l);
    }

    static {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            ACCESS_TIME = lookup.findVarHandle(FWA.class, "accessTime", Long.TYPE);
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new ExceptionInInitializerError(reflectiveOperationException);
        }
    }
}

