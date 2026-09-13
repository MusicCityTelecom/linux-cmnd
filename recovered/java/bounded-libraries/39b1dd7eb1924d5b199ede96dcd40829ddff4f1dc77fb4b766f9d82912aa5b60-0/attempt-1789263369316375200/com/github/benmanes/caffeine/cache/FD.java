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

class FD<K, V>
extends Node<K, V>
implements NodeFactory<K, V> {
    protected static final VarHandle VALUE;
    volatile References.SoftValueReference<V> value;

    FD() {
    }

    FD(K k, ReferenceQueue<K> referenceQueue, V v, ReferenceQueue<V> referenceQueue2, int n, long l) {
        this(new References.WeakKeyReference<K>(k, referenceQueue), v, referenceQueue2, n, l);
    }

    FD(Object object, V v, ReferenceQueue<V> referenceQueue, int n, long l) {
        VALUE.set(this, new References.SoftValueReference<V>(object, v, referenceQueue));
    }

    @Override
    public final Object getKeyReference() {
        References.SoftValueReference softValueReference = VALUE.get(this);
        return softValueReference.getKeyReference();
    }

    @Override
    public final K getKey() {
        References.SoftValueReference softValueReference = VALUE.get(this);
        Reference reference = (Reference)softValueReference.getKeyReference();
        return (K)reference.get();
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
        VALUE.setRelease(this, new References.SoftValueReference<V>(this.getKeyReference(), v, referenceQueue));
        reference.clear();
    }

    @Override
    public final boolean containsValue(Object object) {
        return this.getValue() == object;
    }

    @Override
    public Node<K, V> newNode(K k, ReferenceQueue<K> referenceQueue, V v, ReferenceQueue<V> referenceQueue2, int n, long l) {
        return new FD<K, V>(k, referenceQueue, v, referenceQueue2, n, l);
    }

    @Override
    public Node<K, V> newNode(Object object, V v, ReferenceQueue<V> referenceQueue, int n, long l) {
        return new FD<K, V>(object, v, referenceQueue, n, l);
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
    public boolean softValues() {
        return true;
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
        References.SoftValueReference softValueReference = VALUE.get(this);
        Reference reference = (Reference)softValueReference.getKeyReference();
        reference.clear();
        softValueReference.setKeyReference(RETIRED_WEAK_KEY);
        softValueReference.clear();
    }

    @Override
    public final boolean isDead() {
        return this.getKeyReference() == DEAD_WEAK_KEY;
    }

    @Override
    public final void die() {
        References.SoftValueReference softValueReference = VALUE.get(this);
        Reference reference = (Reference)softValueReference.getKeyReference();
        reference.clear();
        softValueReference.setKeyReference(DEAD_WEAK_KEY);
        softValueReference.clear();
    }

    static {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            VALUE = lookup.findVarHandle(FD.class, "value", References.SoftValueReference.class);
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new ExceptionInInitializerError(reflectiveOperationException);
        }
    }
}

