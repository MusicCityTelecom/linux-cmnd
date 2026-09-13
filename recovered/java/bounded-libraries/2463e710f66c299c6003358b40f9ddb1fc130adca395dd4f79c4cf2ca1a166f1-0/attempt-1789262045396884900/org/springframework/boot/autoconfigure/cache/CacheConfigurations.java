/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.autoconfigure.cache;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.boot.autoconfigure.cache.Cache2kCacheConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration;
import org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration;
import org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration;
import org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration;
import org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration;
import org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration;
import org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration;
import org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration;
import org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration;
import org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration;
import org.springframework.util.Assert;

final class CacheConfigurations {
    private static final Map<CacheType, String> MAPPINGS;

    private CacheConfigurations() {
    }

    static String getConfigurationClass(CacheType cacheType) {
        String configurationClassName = MAPPINGS.get((Object)cacheType);
        Assert.state((configurationClassName != null ? 1 : 0) != 0, () -> "Unknown cache type " + (Object)((Object)cacheType));
        return configurationClassName;
    }

    static CacheType getType(String configurationClassName) {
        for (Map.Entry<CacheType, String> entry : MAPPINGS.entrySet()) {
            if (!entry.getValue().equals(configurationClassName)) continue;
            return entry.getKey();
        }
        throw new IllegalStateException("Unknown configuration class " + configurationClassName);
    }

    static {
        EnumMap<CacheType, String> mappings = new EnumMap<CacheType, String>(CacheType.class);
        mappings.put(CacheType.GENERIC, GenericCacheConfiguration.class.getName());
        mappings.put(CacheType.EHCACHE, EhCacheCacheConfiguration.class.getName());
        mappings.put(CacheType.HAZELCAST, HazelcastCacheConfiguration.class.getName());
        mappings.put(CacheType.INFINISPAN, InfinispanCacheConfiguration.class.getName());
        mappings.put(CacheType.JCACHE, JCacheCacheConfiguration.class.getName());
        mappings.put(CacheType.COUCHBASE, CouchbaseCacheConfiguration.class.getName());
        mappings.put(CacheType.REDIS, RedisCacheConfiguration.class.getName());
        mappings.put(CacheType.CAFFEINE, CaffeineCacheConfiguration.class.getName());
        mappings.put(CacheType.CACHE2K, Cache2kCacheConfiguration.class.getName());
        mappings.put(CacheType.SIMPLE, SimpleCacheConfiguration.class.getName());
        mappings.put(CacheType.NONE, NoOpCacheConfiguration.class.getName());
        MAPPINGS = Collections.unmodifiableMap(mappings);
    }
}

