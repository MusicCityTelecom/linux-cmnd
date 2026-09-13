/*
 * Decompiled with CFR 0.152.
 */
package io.micrometer.core.instrument.binder.cache;

import io.micrometer.core.instrument.FunctionCounter;
import io.micrometer.core.instrument.FunctionTimer;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.cache.CacheMeterBinder;
import io.micrometer.core.instrument.binder.cache.HazelcastIMapAdapter;
import io.micrometer.core.lang.NonNullApi;
import io.micrometer.core.lang.NonNullFields;
import io.micrometer.core.lang.Nullable;
import java.util.concurrent.TimeUnit;
import java.util.function.ToDoubleFunction;
import java.util.function.ToLongFunction;

@NonNullApi
@NonNullFields
public class HazelcastCacheMetrics
extends CacheMeterBinder<Object> {
    private final HazelcastIMapAdapter cache;

    public static Object monitor(MeterRegistry registry, Object cache, String ... tags) {
        return HazelcastCacheMetrics.monitor(registry, cache, Tags.of(tags));
    }

    public static Object monitor(MeterRegistry registry, Object cache, Iterable<Tag> tags) {
        new HazelcastCacheMetrics(cache, tags).bindTo(registry);
        return cache;
    }

    public HazelcastCacheMetrics(Object cache, Iterable<Tag> tags) {
        super(cache, HazelcastIMapAdapter.nameOf(cache), tags);
        this.cache = new HazelcastIMapAdapter(cache);
    }

    @Override
    protected Long size() {
        HazelcastIMapAdapter.LocalMapStats localMapStats = this.cache.getLocalMapStats();
        if (localMapStats != null) {
            return localMapStats.getOwnedEntryCount();
        }
        return null;
    }

    @Override
    protected long hitCount() {
        HazelcastIMapAdapter.LocalMapStats localMapStats = this.cache.getLocalMapStats();
        if (localMapStats != null) {
            return localMapStats.getHits();
        }
        return 0L;
    }

    @Override
    protected Long missCount() {
        return null;
    }

    @Override
    @Nullable
    protected Long evictionCount() {
        return null;
    }

    @Override
    protected long putCount() {
        HazelcastIMapAdapter.LocalMapStats localMapStats = this.cache.getLocalMapStats();
        if (localMapStats != null) {
            return localMapStats.getPutOperationCount();
        }
        return 0L;
    }

    @Override
    protected void bindImplementationSpecificMetrics(MeterRegistry registry) {
        Gauge.builder("cache.entries", this.cache, cache -> this.getDouble(cache.getLocalMapStats(), HazelcastIMapAdapter.LocalMapStats::getBackupEntryCount)).tags(this.getTagsWithCacheName()).tag("ownership", "backup").description("The number of entries held by this member").register(registry);
        Gauge.builder("cache.entries", this.cache, cache -> this.getDouble(cache.getLocalMapStats(), HazelcastIMapAdapter.LocalMapStats::getOwnedEntryCount)).tags(this.getTagsWithCacheName()).tag("ownership", "owned").description("The number of entries held by this member").register(registry);
        Gauge.builder("cache.entry.memory", this.cache, cache -> this.getDouble(cache.getLocalMapStats(), HazelcastIMapAdapter.LocalMapStats::getBackupEntryMemoryCost)).tags(this.getTagsWithCacheName()).tag("ownership", "backup").description("Memory cost of entries held by this member").baseUnit("bytes").register(registry);
        Gauge.builder("cache.entry.memory", this.cache, cache -> this.getDouble(cache.getLocalMapStats(), HazelcastIMapAdapter.LocalMapStats::getOwnedEntryMemoryCost)).tags(this.getTagsWithCacheName()).tag("ownership", "owned").description("Memory cost of entries held by this member").baseUnit("bytes").register(registry);
        FunctionCounter.builder("cache.partition.gets", this.cache, cache -> this.getDouble(cache.getLocalMapStats(), HazelcastIMapAdapter.LocalMapStats::getGetOperationCount)).tags(this.getTagsWithCacheName()).description("The total number of get operations executed against this partition").register(registry);
        this.timings(registry);
        this.nearCacheMetrics(registry);
    }

    private double getDouble(HazelcastIMapAdapter.LocalMapStats localMapStats, ToDoubleFunction<HazelcastIMapAdapter.LocalMapStats> function) {
        return localMapStats != null ? function.applyAsDouble(localMapStats) : Double.NaN;
    }

    private void nearCacheMetrics(MeterRegistry registry) {
        HazelcastIMapAdapter.LocalMapStats localMapStats = this.cache.getLocalMapStats();
        if (localMapStats != null && localMapStats.getNearCacheStats() != null) {
            Gauge.builder("cache.near.requests", this.cache, cache -> this.getDouble(cache.getLocalMapStats(), stats -> stats.getNearCacheStats().getHits())).tags(this.getTagsWithCacheName()).tag("result", "hit").description("The number of requests (hits or misses) of near cache entries owned by this member").register(registry);
            Gauge.builder("cache.near.requests", this.cache, cache -> this.getDouble(cache.getLocalMapStats(), stats -> stats.getNearCacheStats().getMisses())).tags(this.getTagsWithCacheName()).tag("result", "miss").description("The number of requests (hits or misses) of near cache entries owned by this member").register(registry);
            Gauge.builder("cache.near.evictions", this.cache, cache -> this.getDouble(cache.getLocalMapStats(), stats -> stats.getNearCacheStats().getEvictions())).tags(this.getTagsWithCacheName()).description("The number of evictions of near cache entries owned by this member").register(registry);
            Gauge.builder("cache.near.persistences", this.cache, cache -> this.getDouble(cache.getLocalMapStats(), stats -> stats.getNearCacheStats().getPersistenceCount())).tags(this.getTagsWithCacheName()).description("The number of near cache key persistences (when the pre-load feature is enabled)").register(registry);
        }
    }

    private void timings(MeterRegistry registry) {
        FunctionTimer.builder("cache.gets.latency", this.cache, cache -> this.getLong(cache.getLocalMapStats(), HazelcastIMapAdapter.LocalMapStats::getGetOperationCount), cache -> this.getDouble(cache.getLocalMapStats(), HazelcastIMapAdapter.LocalMapStats::getTotalGetLatency), TimeUnit.MILLISECONDS).tags(this.getTagsWithCacheName()).description("Cache gets").register(registry);
        FunctionTimer.builder("cache.puts.latency", this.cache, cache -> this.getLong(cache.getLocalMapStats(), HazelcastIMapAdapter.LocalMapStats::getPutOperationCount), cache -> this.getDouble(cache.getLocalMapStats(), HazelcastIMapAdapter.LocalMapStats::getTotalPutLatency), TimeUnit.MILLISECONDS).tags(this.getTagsWithCacheName()).description("Cache puts").register(registry);
        FunctionTimer.builder("cache.removals.latency", this.cache, cache -> this.getLong(cache.getLocalMapStats(), HazelcastIMapAdapter.LocalMapStats::getRemoveOperationCount), cache -> this.getDouble(cache.getLocalMapStats(), HazelcastIMapAdapter.LocalMapStats::getTotalRemoveLatency), TimeUnit.MILLISECONDS).tags(this.getTagsWithCacheName()).description("Cache removals").register(registry);
    }

    private long getLong(HazelcastIMapAdapter.LocalMapStats localMapStats, ToLongFunction<HazelcastIMapAdapter.LocalMapStats> function) {
        return localMapStats != null ? function.applyAsLong(localMapStats) : 0L;
    }
}

