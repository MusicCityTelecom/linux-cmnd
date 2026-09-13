/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.Tag
 *  io.micrometer.core.instrument.binder.MeterBinder
 *  io.micrometer.core.instrument.binder.cache.EhCache2Metrics
 *  org.springframework.cache.ehcache.EhCacheCache
 */
package org.springframework.boot.actuate.metrics.cache;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.binder.cache.EhCache2Metrics;
import org.springframework.boot.actuate.metrics.cache.CacheMeterBinderProvider;
import org.springframework.cache.ehcache.EhCacheCache;

public class EhCache2CacheMeterBinderProvider
implements CacheMeterBinderProvider<EhCacheCache> {
    @Override
    public MeterBinder getMeterBinder(EhCacheCache cache, Iterable<Tag> tags) {
        return new EhCache2Metrics(cache.getNativeCache(), tags);
    }
}

