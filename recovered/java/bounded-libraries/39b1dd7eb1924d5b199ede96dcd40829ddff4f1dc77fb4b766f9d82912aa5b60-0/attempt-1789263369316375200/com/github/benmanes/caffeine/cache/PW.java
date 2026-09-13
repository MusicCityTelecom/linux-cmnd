/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.Node;
import com.github.benmanes.caffeine.cache.NodeFactory;
import com.github.benmanes.caffeine.cache.References;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

class PW<K, V>
extends Node<K, V>
implements NodeFactory<K, V> {
    protected static final VarHandle VALUE;
    volatile References.WeakValueReference<V> value;

    PW() {
    }

    PW(K k, ReferenceQueue<K> referenceQueue, V v, ReferenceQueue<V> referenceQueue2, int n, long l) {
        this(k, v, referenceQueue2, n, l);
    }

    PW(Object object, V v, ReferenceQueue<V> referenceQueue, int n, long l) {
        VALUE.set(this, new References.WeakValueReference<V>(object, v, referenceQueue));
    }

    @Override
    public final Object getKeyReference() {
        References.WeakValueReference weakValueReference = VALUE.get(this);
        return weakValueReference.getKeyReference();
    }

    @Override
    public final K getKey() {
        References.WeakValueReference weakValueReference = VALUE.get(this);
        return (K)weakValueReference.getKeyReference();
    }

    @Override
    public final V getValue() {
        Reference reference;
        Object t;
        while ((t = (reference = VALUE.getOpaque(this)).get()) == null && reference != VALUE.getAcquire(this)) {
        }
        return (V)t;
    }

    @Override
    public final Object getValueReference() {
        return VALUE.get(this);
    }

    @Override
    public final void setValue(V v, ReferenceQueue<V> referenceQueue) {
        Reference reference = VALUE.get(this);
        VALUE.setRelease(this, new References.WeakValueReference<V>(this.getKeyReference(), v, referenceQueue));
        reference.clear();
    }

    @Override
    public final boolean containsValue(Object object) {
        return this.getValue() == object;
    }

    @Override
    public Node<K, V> newNode(K k, ReferenceQueue<K> referenceQueue, V v, ReferenceQueue<V> referenceQueue2, int n, long l) {
        return new PW<K, V>(k, referenceQueue, v, referenceQueue2, n, l);
    }

    @Override
    public Node<K, V> newNode(Object object, V v, ReferenceQueue<V> referenceQueue, int n, long l) {
        return new PW<K, V>(object, v, referenceQueue, n, l);
    }

    @Override
    public boolean weakValues() {
        return true;
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
        References.WeakValueReference weakValueReference = VALUE.get(this);
        weakValueReference.setKeyReference(RETIRED_STRONG_KEY);
        weakValueReference.clear();
    }

    @Override
    public final boolean isDead() {
        return this.getKeyReference() == DEAD_STRONG_KEY;
    }

    @Override
    public final void die() {
        References.WeakValueReference weakValueReference = VALUE.get(this);
        weakValueReference.setKeyReference(DEAD_STRONG_KEY);
        weakValueReference.clear();
    }

    static {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            VALUE = lookup.findVarHandle(PW.class, "value", References.WeakValueReference.class);
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new ExceptionInInitializerError(reflectiveOperationException);
        }
    }
}

