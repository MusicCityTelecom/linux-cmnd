/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.Tag
 *  io.micrometer.core.instrument.binder.MeterBinder
 *  io.micrometer.core.instrument.binder.cache.CaffeineCacheMetrics
 *  org.springframework.cache.caffeine.CaffeineCache
 */
package org.springframework.boot.actuate.metrics.cache;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.binder.cache.CaffeineCacheMetrics;
import org.springframework.boot.actuate.metrics.cache.CacheMeterBinderProvider;
import org.springframework.cache.caffeine.CaffeineCache;

public class CaffeineCacheMeterBinderProvider
implements CacheMeterBinderProvider<CaffeineCache> {
    @Override
    public MeterBinder getMeterBinder(CaffeineCache cache, Iterable<Tag> tags) {
        return new CaffeineCacheMetrics(cache.getNativeCache(), cache.getName(), tags);
    }
}

