/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.Node;
import com.github.benmanes.caffeine.cache.PDR;
import java.lang.ref.ReferenceQueue;

final class PDRMW<K, V>
extends PDR<K, V> {
    int queueType;
    int weight;
    int policyWeight;
    Node<K, V> previousInAccessOrder;
    Node<K, V> nextInAccessOrder;

    PDRMW() {
    }

    PDRMW(K k, ReferenceQueue<K> referenceQueue, V v, ReferenceQueue<V> referenceQueue2, int n, long l) {
        super(k, referenceQueue, v, referenceQueue2, n, l);
        this.weight = n;
    }

    PDRMW(Object object, V v, ReferenceQueue<V> referenceQueue, int n, long l) {
        super(object, v, referenceQueue, n, l);
        this.weight = n;
    }

    @Override
    public int getQueueType() {
        return this.queueType;
    }

    @Override
    public void setQueueType(int n) {
        this.queueType = n;
    }

    @Override
    public int getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(int n) {
        this.weight = n;
    }

    @Override
    public int getPolicyWeight() {
        return this.policyWeight;
    }

    @Override
    public void setPolicyWeight(int n) {
        this.policyWeight = n;
    }

    @Override
    public Node<K, V> getPreviousInAccessOrder() {
        return this.previousInAccessOrder;
    }

    @Override
    public void setPreviousInAccessOrder(Node<K, V> node) {
        this.previousInAccessOrder = node;
    }

    @Override
    public Node<K, V> getNextInAccessOrder() {
        return this.nextInAccessOrder;
    }

    @Override
    public void setNextInAccessOrder(Node<K, V> node) {
        this.nextInAccessOrder = node;
    }

    @Override
    public Node<K, V> newNode(K k, ReferenceQueue<K> referenceQueue, V v, ReferenceQueue<V> referenceQueue2, int n, long l) {
        return new PDRMW<K, V>(k, referenceQueue, v, referenceQueue2, n, l);
    }

    @Override
    public Node<K, V> newNode(Object object, V v, ReferenceQueue<V> referenceQueue, int n, long l) {
        return new PDRMW<K, V>(object, v, referenceQueue, n, l);
    }
}

