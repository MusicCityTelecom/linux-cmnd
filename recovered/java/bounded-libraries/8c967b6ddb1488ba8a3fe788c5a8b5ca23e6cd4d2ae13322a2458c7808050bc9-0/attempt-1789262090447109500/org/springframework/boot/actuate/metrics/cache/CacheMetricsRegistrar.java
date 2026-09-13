/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.MeterRegistry
 *  io.micrometer.core.instrument.Tag
 *  io.micrometer.core.instrument.Tags
 *  io.micrometer.core.instrument.binder.MeterBinder
 *  org.springframework.boot.util.LambdaSafe
 *  org.springframework.boot.util.LambdaSafe$Callbacks
 *  org.springframework.cache.Cache
 *  org.springframework.cache.transaction.TransactionAwareCacheDecorator
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.actuate.metrics.cache;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.MeterBinder;
import java.util.Collection;
import java.util.Objects;
import org.springframework.boot.actuate.metrics.cache.CacheMeterBinderProvider;
import org.springframework.boot.util.LambdaSafe;
import org.springframework.cache.Cache;
import org.springframework.cache.transaction.TransactionAwareCacheDecorator;
import org.springframework.util.ClassUtils;

public class CacheMetricsRegistrar {
    private final MeterRegistry registry;
    private final Collection<CacheMeterBinderProvider<?>> binderProviders;

    public CacheMetricsRegistrar(MeterRegistry registry, Collection<CacheMeterBinderProvider<?>> binderProviders) {
        this.registry = registry;
        this.binderProviders = binderProviders;
    }

    public boolean bindCacheToRegistry(Cache cache, Tag ... tags) {
        MeterBinder meterBinder = this.getMeterBinder(this.unwrapIfNecessary(cache), Tags.of((Tag[])tags));
        if (meterBinder != null) {
            meterBinder.bindTo(this.registry);
            return true;
        }
        return false;
    }

    private MeterBinder getMeterBinder(Cache cache, Tags tags) {
        Tags cacheTags = tags.and(this.getAdditionalTags(cache));
        return ((LambdaSafe.Callbacks)LambdaSafe.callbacks(CacheMeterBinderProvider.class, this.binderProviders, (Object)cache, (Object[])new Object[0]).withLogger(CacheMetricsRegistrar.class)).invokeAnd(binderProvider -> binderProvider.getMeterBinder(cache, (Iterable<Tag>)cacheTags)).filter(Objects::nonNull).findFirst().orElse(null);
    }

    protected Iterable<Tag> getAdditionalTags(Cache cache) {
        return Tags.of((String)"name", (String)cache.getName());
    }

    private Cache unwrapIfNecessary(Cache cache) {
        if (ClassUtils.isPresent((String)"org.springframework.cache.transaction.TransactionAwareCacheDecorator", (ClassLoader)this.getClass().getClassLoader())) {
            return TransactionAwareCacheDecoratorHandler.unwrapIfNecessary(cache);
        }
        return cache;
    }

    private static class TransactionAwareCacheDecoratorHandler {
        private TransactionAwareCacheDecoratorHandler() {
        }

        private static Cache unwrapIfNecessary(Cache cache) {
            try {
                if (cache instanceof TransactionAwareCacheDecorator) {
                    return ((TransactionAwareCacheDecorator)cache).getTargetCache();
                }
            }
            catch (NoClassDefFoundError noClassDefFoundError) {
                // empty catch block
            }
            return cache;
        }
    }
}

