/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.FunctionCounter
 *  io.micrometer.core.instrument.MeterRegistry
 *  io.micrometer.core.instrument.Tag
 *  io.micrometer.core.instrument.TimeGauge
 *  io.micrometer.core.instrument.binder.cache.CacheMeterBinder
 *  org.springframework.data.redis.cache.RedisCache
 */
package org.springframework.boot.actuate.metrics.cache;

import io.micrometer.core.instrument.FunctionCounter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.TimeGauge;
import io.micrometer.core.instrument.binder.cache.CacheMeterBinder;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.cache.RedisCache;

public class RedisCacheMetrics
extends CacheMeterBinder<RedisCache> {
    private final RedisCache cache;

    public RedisCacheMetrics(RedisCache cache, Iterable<Tag> tags) {
        super((Object)cache, cache.getName(), tags);
        this.cache = cache;
    }

    protected Long size() {
        return null;
    }

    protected long hitCount() {
        return this.cache.getStatistics().getHits();
    }

    protected Long missCount() {
        return this.cache.getStatistics().getMisses();
    }

    protected Long evictionCount() {
        return null;
    }

    protected long putCount() {
        return this.cache.getStatistics().getPuts();
    }

    protected void bindImplementationSpecificMetrics(MeterRegistry registry) {
        FunctionCounter.builder((String)"cache.removals", (Object)this.cache, cache -> cache.getStatistics().getDeletes()).tags(this.getTagsWithCacheName()).description("Cache removals").register(registry);
        FunctionCounter.builder((String)"cache.gets", (Object)this.cache, cache -> cache.getStatistics().getPending()).tags(this.getTagsWithCacheName()).tag("result", "pending").description("The number of pending requests").register(registry);
        TimeGauge.builder((String)"cache.lock.duration", (Object)this.cache, (TimeUnit)TimeUnit.NANOSECONDS, cache -> cache.getStatistics().getLockWaitDuration(TimeUnit.NANOSECONDS)).tags(this.getTagsWithCacheName()).description("The time the cache has spent waiting on a lock").register(registry);
    }
}

