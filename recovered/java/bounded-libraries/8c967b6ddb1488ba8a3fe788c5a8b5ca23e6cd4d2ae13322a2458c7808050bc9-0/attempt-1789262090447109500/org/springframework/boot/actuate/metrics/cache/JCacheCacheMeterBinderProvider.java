/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.Tag
 *  io.micrometer.core.instrument.binder.MeterBinder
 *  io.micrometer.core.instrument.binder.cache.JCacheMetrics
 *  org.springframework.cache.jcache.JCacheCache
 */
package org.springframework.boot.actuate.metrics.cache;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.binder.cache.JCacheMetrics;
import org.springframework.boot.actuate.metrics.cache.CacheMeterBinderProvider;
import org.springframework.cache.jcache.JCacheCache;

public class JCacheCacheMeterBinderProvider
implements CacheMeterBinderProvider<JCacheCache> {
    @Override
    public MeterBinder getMeterBinder(JCacheCache cache, Iterable<Tag> tags) {
        return new JCacheMetrics(cache.getNativeCache(), tags);
    }
}

