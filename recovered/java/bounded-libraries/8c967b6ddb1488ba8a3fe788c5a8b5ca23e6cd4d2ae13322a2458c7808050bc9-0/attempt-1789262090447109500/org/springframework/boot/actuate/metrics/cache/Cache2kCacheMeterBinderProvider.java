/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.Tag
 *  io.micrometer.core.instrument.binder.MeterBinder
 *  org.cache2k.extra.micrometer.Cache2kCacheMetrics
 *  org.cache2k.extra.spring.SpringCache2kCache
 */
package org.springframework.boot.actuate.metrics.cache;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.cache2k.extra.micrometer.Cache2kCacheMetrics;
import org.cache2k.extra.spring.SpringCache2kCache;
import org.springframework.boot.actuate.metrics.cache.CacheMeterBinderProvider;

public class Cache2kCacheMeterBinderProvider
implements CacheMeterBinderProvider<SpringCache2kCache> {
    @Override
    public MeterBinder getMeterBinder(SpringCache2kCache cache, Iterable<Tag> tags) {
        return new Cache2kCacheMetrics(cache.getNativeCache(), tags);
    }
}

