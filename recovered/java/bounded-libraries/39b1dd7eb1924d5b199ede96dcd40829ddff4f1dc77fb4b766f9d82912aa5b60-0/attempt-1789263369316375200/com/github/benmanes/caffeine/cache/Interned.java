/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.Node;
import com.github.benmanes.caffeine.cache.NodeFactory;
import com.github.benmanes.caffeine.cache.References;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.Objects;

final class Interned<K, V>
extends Node<K, V>
implements NodeFactory<K, V> {
    volatile Reference<?> keyReference;

    Interned() {
    }

    Interned(Reference<K> keyReference) {
        this.keyReference = keyReference;
    }

    @Override
    public K getKey() {
        return (K)this.keyReference.get();
    }

    @Override
    public Object getKeyReference() {
        return this.keyReference;
    }

    @Override
    public V getValue() {
        return (V)Boolean.TRUE;
    }

    @Override
    public V getValueReference() {
        return (V)Boolean.TRUE;
    }

    @Override
    public void setValue(V value, ReferenceQueue<V> referenceQueue) {
    }

    @Override
    public boolean containsValue(Object value) {
        return Objects.equals(value, this.getValue());
    }

    @Override
    public Node<K, V> newNode(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new Interned<K, V>(new References.WeakKeyEqualsReference<K>(key, keyReferenceQueue));
    }

    @Override
    public Node<K, V> newNode(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new Interned<K, V>((Reference)keyReference);
    }

    @Override
    public Object newLookupKey(Object key) {
        return new References.LookupKeyEqualsReference<Object>(key);
    }

    @Override
    public Object newReferenceKey(K key, ReferenceQueue<K> referenceQueue) {
        return new References.WeakKeyEqualsReference<K>(key, referenceQueue);
    }

    @Override
    public boolean isAlive() {
        Reference<?> keyRef = this.keyReference;
        return keyRef != RETIRED_WEAK_KEY && keyRef != DEAD_WEAK_KEY;
    }

    @Override
    public boolean isRetired() {
        return this.keyReference == RETIRED_WEAK_KEY;
    }

    @Override
    public void retire() {
        this.keyReference = RETIRED_WEAK_KEY;
    }

    @Override
    public boolean isDead() {
        return this.keyReference == DEAD_WEAK_KEY;
    }

    @Override
    public void die() {
        this.keyReference.clear();
        this.keyReference = DEAD_WEAK_KEY;
    }
}

