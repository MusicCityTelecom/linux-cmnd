/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.Node;
import com.github.benmanes.caffeine.cache.NodeFactory;
import com.github.benmanes.caffeine.cache.References;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.ref.ReferenceQueue;
import java.util.Objects;

class FS<K, V>
extends Node<K, V>
implements NodeFactory<K, V> {
    protected static final VarHandle KEY;
    protected static final VarHandle VALUE;
    volatile References.WeakKeyReference<K> key;
    volatile V value;

    FS() {
    }

    FS(K k, ReferenceQueue<K> referenceQueue, V v, ReferenceQueue<V> referenceQueue2, int n, long l) {
        this(new References.WeakKeyReference<K>(k, referenceQueue), v, referenceQueue2, n, l);
    }

    FS(Object object, V v, ReferenceQueue<V> referenceQueue, int n, long l) {
        KEY.set(this, object);
        VALUE.set(this, v);
    }

    @Override
    public final K getKey() {
        return (K)KEY.get(this).get();
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
        return new FS<K, V>(k, referenceQueue, v, referenceQueue2, n, l);
    }

    @Override
    public Node<K, V> newNode(Object object, V v, ReferenceQueue<V> referenceQueue, int n, long l) {
        return new FS<K, V>(object, v, referenceQueue, n, l);
    }

    @Override
    public Object newLookupKey(Object object) {
        return new References.LookupKeyReference<Object>(object);
    }

    @Override
    public Object newReferenceKey(K k, ReferenceQueue<K> referenceQueue) {
        return new References.WeakKeyReference<K>(k, referenceQueue);
    }

    @Override
    public final boolean isAlive() {
        Object object = this.getKeyReference();
        return object != RETIRED_WEAK_KEY && object != DEAD_WEAK_KEY;
    }

    @Override
    public final boolean isRetired() {
        return this.getKeyReference() == RETIRED_WEAK_KEY;
    }

    @Override
    public final void retire() {
        this.key.clear();
        KEY.set(this, RETIRED_WEAK_KEY);
    }

    @Override
    public final boolean isDead() {
        return this.getKeyReference() == DEAD_WEAK_KEY;
    }

    @Override
    public final void die() {
        this.key.clear();
        VALUE.set(this, null);
        KEY.set(this, DEAD_WEAK_KEY);
    }

    static {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            KEY = lookup.findVarHandle(FS.class, "key", References.WeakKeyReference.class);
            VALUE = lookup.findVarHandle(FS.class, "value", Object.class);
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new ExceptionInInitializerError(reflectiveOperationException);
        }
    }
}

