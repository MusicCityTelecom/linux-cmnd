/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.Node;
import com.github.benmanes.caffeine.cache.NodeFactory;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.ref.ReferenceQueue;
import java.util.Objects;

class PS<K, V>
extends Node<K, V>
implements NodeFactory<K, V> {
    protected static final VarHandle KEY;
    protected static final VarHandle VALUE;
    volatile K key;
    volatile V value;

    PS() {
    }

    PS(K k, ReferenceQueue<K> referenceQueue, V v, ReferenceQueue<V> referenceQueue2, int n, long l) {
        this(k, v, referenceQueue2, n, l);
    }

    PS(Object object, V v, ReferenceQueue<V> referenceQueue, int n, long l) {
        KEY.set(this, object);
        VALUE.set(this, v);
    }

    @Override
    public final K getKey() {
        return (K)KEY.get(this);
    }

    @Override
    public final Object getKeyReference() {
        return KEY.get(this);
    }

    @Override
    public final V getValue() {
        return (V)VALUE.get(this);
    }

    @Override
    public final Object getValueReference() {
        return VALUE.get(this);
    }

    @Override
    public final void setValue(V v, ReferenceQueue<V> referenceQueue) {
        VALUE.set(this, v);
    }

    @Override
    public final boolean containsValue(Object object) {
        return Objects.equals(object, this.getValue());
    }

    @Override
    public Node<K, V> newNode(K k, ReferenceQueue<K> referenceQueue, V v, ReferenceQueue<V> referenceQueue2, int n, long l) {
        return new PS<K, V>(k, referenceQueue, v, referenceQueue2, n, l);
    }

    @Override
    public Node<K, V> newNode(Object object, V v, ReferenceQueue<V> referenceQueue, int n, long l) {
        return new PS<K, V>(object, v, referenceQueue, n, l);
    }

    @Override
    public final boolean isAlive() {
        Object object = this.getKeyReference();
        return object != RETIRED_STRONG_KEY && object != DEAD_STRONG_KEY;
    }

    @Override
    public final boolean isRetired() {
        return this.getKeyReference() == RETIRED_STRONG_KEY;
    }

    @Override
    public final void retire() {
        KEY.set(this, RETIRED_STRONG_KEY);
    }

    @Override
    public final boolean isDead() {
        return this.getKeyReference() == DEAD_STRONG_KEY;
    }

    @Override
    public final void die() {
        VALUE.set(this, null);
        KEY.set(this, DEAD_STRONG_KEY);
    }

    static {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            KEY = lookup.findVarHandle(PS.class, "key", Object.class);
            VALUE = lookup.findVarHandle(PS.class, "value", Object.class);
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new ExceptionInInitializerError(reflectiveOperationException);
        }
    }
}

