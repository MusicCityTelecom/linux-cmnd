/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hazelcast.spring.cache.HazelcastCache
 *  io.micrometer.core.instrument.Tag
 *  io.micrometer.core.instrument.binder.MeterBinder
 *  io.micrometer.core.instrument.binder.cache.HazelcastCacheMetrics
 *  org.springframework.util.ReflectionUtils
 */
package org.springframework.boot.actuate.metrics.cache;

import com.hazelcast.spring.cache.HazelcastCache;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.binder.cache.HazelcastCacheMetrics;
import java.lang.reflect.Method;
import org.springframework.boot.actuate.metrics.cache.CacheMeterBinderProvider;
import org.springframework.util.ReflectionUtils;

public class HazelcastCacheMeterBinderProvider
implements CacheMeterBinderProvider<HazelcastCache> {
    @Override
    public MeterBinder getMeterBinder(HazelcastCache cache, Iterable<Tag> tags) {
        try {
            return new HazelcastCacheMetrics((Object)cache.getNativeCache(), tags);
        }
        catch (NoSuchMethodError ex) {
            return this.createHazelcast4CacheMetrics(cache, tags);
        }
    }

    private MeterBinder createHazelcast4CacheMetrics(HazelcastCache cache, Iterable<Tag> tags) {
        try {
            Method nativeCacheAccessor = ReflectionUtils.findMethod(HazelcastCache.class, (String)"getNativeCache");
            Object nativeCache = ReflectionUtils.invokeMethod((Method)nativeCacheAccessor, (Object)cache);
            return (MeterBinder)HazelcastCacheMetrics.class.getConstructor(Object.class, Iterable.class).newInstance(nativeCache, tags);
        }
        catch (Exception ex) {
            throw new IllegalStateException("Failed to create MeterBinder for Hazelcast", ex);
        }
    }
}

