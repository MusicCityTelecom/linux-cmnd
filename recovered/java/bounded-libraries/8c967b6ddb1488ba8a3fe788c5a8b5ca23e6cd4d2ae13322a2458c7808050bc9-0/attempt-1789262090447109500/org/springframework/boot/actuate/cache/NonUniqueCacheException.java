/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.cache;

import java.util.Collection;
import java.util.Collections;

public class NonUniqueCacheException
extends RuntimeException {
    private final String cacheName;
    private final Collection<String> cacheManagerNames;

    public NonUniqueCacheException(String cacheName, Collection<String> cacheManagerNames) {
        super(String.format("Multiple caches named %s found, specify the 'cacheManager' to use: %s", cacheName, cacheManagerNames));
        this.cacheName = cacheName;
        this.cacheManagerNames = Collections.unmodifiableCollection(cacheManagerNames);
    }

    public String getCacheName() {
        return this.cacheName;
    }

    public Collection<String> getCacheManagerNames() {
        return this.cacheManagerNames;
    }
}

