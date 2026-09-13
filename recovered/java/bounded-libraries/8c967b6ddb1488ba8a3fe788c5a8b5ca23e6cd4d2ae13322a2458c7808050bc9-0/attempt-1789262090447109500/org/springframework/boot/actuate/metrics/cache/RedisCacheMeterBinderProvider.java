/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.Tag
 *  io.micrometer.core.instrument.binder.MeterBinder
 *  org.springframework.data.redis.cache.RedisCache
 */
package org.springframework.boot.actuate.metrics.cache;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.boot.actuate.metrics.cache.CacheMeterBinderProvider;
import org.springframework.boot.actuate.metrics.cache.RedisCacheMetrics;
import org.springframework.data.redis.cache.RedisCache;

public class RedisCacheMeterBinderProvider
implements CacheMeterBinderProvider<RedisCache> {
    @Override
    public MeterBinder getMeterBinder(RedisCache cache, Iterable<Tag> tags) {
        return new RedisCacheMetrics(cache, tags);
    }
}

