/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.FDAW;
import com.github.benmanes.caffeine.cache.Node;
import java.lang.ref.ReferenceQueue;

class FDAWR<K, V>
extends FDAW<K, V> {
    FDAWR() {
    }

    FDAWR(K k, ReferenceQueue<K> referenceQueue, V v, ReferenceQueue<V> referenceQueue2, int n, long l) {
        super(k, referenceQueue, v, referenceQueue2, n, l);
    }

    FDAWR(Object object, V v, ReferenceQueue<V> referenceQueue, int n, long l) {
        super(object, v, referenceQueue, n, l);
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
    public final boolean casWriteTime(long l, long l2) {
        return this.writeTime == l && WRITE_TIME.compareAndSet(this, l, l2);
    }

    @Override
    public Node<K, V> newNode(K k, ReferenceQueue<K> referenceQueue, V v, ReferenceQueue<V> referenceQueue2, int n, long l) {
        return new FDAWR<K, V>(k, referenceQueue, v, referenceQueue2, n, l);
    }

    @Override
    public Node<K, V> newNode(Object object, V v, ReferenceQueue<V> referenceQueue, int n, long l) {
        return new FDAWR<K, V>(object, v, referenceQueue, n, l);
    }
}

