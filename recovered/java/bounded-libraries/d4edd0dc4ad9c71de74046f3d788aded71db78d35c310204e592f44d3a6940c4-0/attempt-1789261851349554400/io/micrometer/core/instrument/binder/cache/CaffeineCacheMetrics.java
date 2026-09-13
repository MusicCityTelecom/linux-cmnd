/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.benmanes.caffeine.cache.AsyncCache
 *  com.github.benmanes.caffeine.cache.Cache
 *  com.github.benmanes.caffeine.cache.LoadingCache
 */
package io.micrometer.core.instrument.binder.cache;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.LoadingCache;
import io.micrometer.core.instrument.FunctionCounter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.TimeGauge;
import io.micrometer.core.instrument.binder.cache.CacheMeterBinder;
import io.micrometer.core.lang.NonNullApi;
import io.micrometer.core.lang.NonNullFields;
import io.micrometer.core.lang.Nullable;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.ToLongFunction;

@NonNullApi
@NonNullFields
public class CaffeineCacheMetrics<K, V, C extends Cache<K, V>>
extends CacheMeterBinder<C> {
    public CaffeineCacheMetrics(C cache, String cacheName, Iterable<Tag> tags) {
        super(cache, cacheName, tags);
    }

    public static <K, V, C extends Cache<K, V>> C monitor(MeterRegistry registry, C cache, String cacheName, String ... tags) {
        return CaffeineCacheMetrics.monitor(registry, cache, cacheName, Tags.of(tags));
    }

    public static <K, V, C extends Cache<K, V>> C monitor(MeterRegistry registry, C cache, String cacheName, Iterable<Tag> tags) {
        new CaffeineCacheMetrics<K, V, C>(cache, cacheName, tags).bindTo(registry);
        return cache;
    }

    public static <K, V, C extends AsyncCache<K, V>> C monitor(MeterRegistry registry, C cache, String cacheName, String ... tags) {
        return CaffeineCacheMetrics.monitor(registry, cache, cacheName, Tags.of(tags));
    }

    public static <K, V, C extends AsyncCache<K, V>> C monitor(MeterRegistry registry, C cache, String cacheName, Iterable<Tag> tags) {
        CaffeineCacheMetrics.monitor(registry, cache.synchronous(), cacheName, tags);
        return cache;
    }

    @Override
    protected Long size() {
        return this.getOrDefault(Cache::estimatedSize, null);
    }

    @Override
    protected long hitCount() {
        return this.getOrDefault((C c) -> c.stats().hitCount(), 0L);
    }

    @Override
    protected Long missCount() {
        return this.getOrDefault((C c) -> c.stats().missCount(), null);
    }

    @Override
    protected Long evictionCount() {
        return this.getOrDefault((C c) -> c.stats().evictionCount(), null);
    }

    @Override
    protected long putCount() {
        return this.getOrDefault((C c) -> c.stats().loadCount(), 0L);
    }

    @Override
    protected void bindImplementationSpecificMetrics(MeterRegistry registry) {
        Cache cache = (Cache)this.getCache();
        FunctionCounter.builder("cache.eviction.weight", cache, c -> c.stats().evictionWeight()).tags(this.getTagsWithCacheName()).description("The sum of weights of evicted entries. This total does not include manual invalidations.").register(registry);
        if (cache instanceof LoadingCache) {
            TimeGauge.builder("cache.load.duration", cache, TimeUnit.NANOSECONDS, c -> c.stats().totalLoadTime()).tags(this.getTagsWithCacheName()).description("The time the cache has spent loading new values").register(registry);
            FunctionCounter.builder("cache.load", cache, c -> c.stats().loadSuccessCount()).tags(this.getTagsWithCacheName()).tags("result", "success").description("The number of times cache lookup methods have successfully loaded a new value or failed to load a new value, either because no value was found or an exception was thrown while loading").register(registry);
            FunctionCounter.builder("cache.load", cache, c -> c.stats().loadFailureCount()).tags(this.getTagsWithCacheName()).tags("result", "failure").description("The number of times cache lookup methods have successfully loaded a new value or failed to load a new value, either because no value was found or an exception was thrown while loading").register(registry);
        }
    }

    @Nullable
    private Long getOrDefault(Function<C, Long> function, @Nullable Long defaultValue) {
        Cache cache = (Cache)this.getCache();
        if (cache != null) {
            return function.apply(cache);
        }
        return defaultValue;
    }

    private long getOrDefault(ToLongFunction<C> function, long defaultValue) {
        Cache cache = (Cache)this.getCache();
        if (cache != null) {
            return function.applyAsLong(cache);
        }
        return defaultValue;
    }
}

