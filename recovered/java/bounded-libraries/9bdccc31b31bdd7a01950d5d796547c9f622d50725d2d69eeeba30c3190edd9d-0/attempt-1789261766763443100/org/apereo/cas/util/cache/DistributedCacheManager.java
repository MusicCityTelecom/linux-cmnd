/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CanIgnoreReturnValue
 */
package org.apereo.cas.util.cache;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Closeable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public interface DistributedCacheManager<K extends Serializable, V extends Serializable, I extends Serializable>
extends Closeable {
    public static DistributedCacheManager noOp() {
        return new DistributedCacheManager(){};
    }

    default public V get(K key) {
        return null;
    }

    default public Collection<V> getAll() {
        return new ArrayList(0);
    }

    default public boolean contains(K key) {
        return false;
    }

    default public void clear() {
    }

    @CanIgnoreReturnValue
    default public DistributedCacheManager<K, V, I> set(K key, V item, boolean publish) {
        return this;
    }

    @CanIgnoreReturnValue
    default public DistributedCacheManager<K, V, I> update(K key, V item, boolean publish) {
        return this;
    }

    @CanIgnoreReturnValue
    default public DistributedCacheManager<K, V, I> remove(K key, V item, boolean publish) {
        return this;
    }

    default public String getName() {
        return this.getClass().getSimpleName();
    }

    default public Collection<V> findAll(Predicate<V> filter) {
        return new ArrayList(0);
    }

    default public Optional<V> find(Predicate<V> filter) {
        Collection<V> results = this.findAll(filter);
        if (results.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of((Serializable)results.iterator().next());
    }

    @Override
    default public void close() {
    }
}

