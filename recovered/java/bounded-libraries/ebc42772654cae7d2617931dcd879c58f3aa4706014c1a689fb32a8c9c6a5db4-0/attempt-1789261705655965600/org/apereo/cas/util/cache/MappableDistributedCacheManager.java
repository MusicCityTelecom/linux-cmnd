/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CanIgnoreReturnValue
 *  lombok.Generated
 *  org.apereo.cas.util.cache.DistributedCacheManager
 */
package org.apereo.cas.util.cache;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apereo.cas.util.PublisherIdentifier;
import org.apereo.cas.util.cache.BaseDistributedCacheManager;
import org.apereo.cas.util.cache.DistributedCacheManager;
import org.apereo.cas.util.cache.DistributedCacheObject;

public class MappableDistributedCacheManager<K extends Serializable, V extends DistributedCacheObject>
extends BaseDistributedCacheManager<K, V> {
    protected final Map<String, V> mapInstance;

    public V get(K key) {
        if (this.contains(key)) {
            String cacheKey = this.buildKey(key);
            return (V)((DistributedCacheObject)this.mapInstance.get(cacheKey));
        }
        return null;
    }

    public Collection<V> getAll() {
        return this.mapInstance.values();
    }

    @CanIgnoreReturnValue
    public DistributedCacheManager<K, V, PublisherIdentifier> set(K key, V item, boolean publish) {
        this.mapInstance.put(this.buildKey(key), item);
        return this;
    }

    public void clear() {
        this.mapInstance.clear();
    }

    public boolean contains(K key) {
        return this.mapInstance.containsKey(this.buildKey(key));
    }

    @CanIgnoreReturnValue
    public DistributedCacheManager<K, V, PublisherIdentifier> update(K key, V item, boolean publish) {
        this.remove(key, item, publish);
        this.set(key, item, publish);
        return this;
    }

    @CanIgnoreReturnValue
    public DistributedCacheManager<K, V, PublisherIdentifier> remove(K key, V item, boolean publish) {
        String cacheKey = this.buildKey(key);
        this.mapInstance.remove(cacheKey);
        return this;
    }

    public Collection<V> findAll(Predicate<V> filter) {
        return this.getAll().stream().filter(filter).collect(Collectors.toList());
    }

    protected String buildKey(K key) {
        return key.toString();
    }

    @Generated
    public MappableDistributedCacheManager(Map<String, V> mapInstance) {
        this.mapInstance = mapInstance;
    }
}

