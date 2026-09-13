/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.FWW;
import com.github.benmanes.caffeine.cache.Node;
import java.lang.ref.ReferenceQueue;

class FWWR<K, V>
extends FWW<K, V> {
    FWWR() {
    }

    FWWR(K k, ReferenceQueue<K> referenceQueue, V v, ReferenceQueue<V> referenceQueue2, int n, long l) {
        super(k, referenceQueue, v, referenceQueue2, n, l);
    }

    FWWR(Object object, V v, ReferenceQueue<V> referenceQueue, int n, long l) {
        super(object, v, referenceQueue, n, l);
    }

    @Override
    public final boolean casWriteTime(long l, long l2) {
        return this.writeTime == l && WRITE_TIME.compareAndSet(this, l, l2);
    }

    @Override
    public Node<K, V> newNode(K k, ReferenceQueue<K> referenceQueue, V v, ReferenceQueue<V> referenceQueue2, int n, long l) {
        return new FWWR<K, V>(k, referenceQueue, v, referenceQueue2, n, l);
    }

    @Override
    public Node<K, V> newNode(Object object, V v, ReferenceQueue<V> referenceQueue, int n, long l) {
        return new FWWR<K, V>(object, v, referenceQueue, n, l);
    }
}

