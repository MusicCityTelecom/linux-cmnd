/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.Tag
 *  io.micrometer.core.instrument.binder.MeterBinder
 *  org.springframework.cache.Cache
 */
package org.springframework.boot.actuate.metrics.cache;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.cache.Cache;

@FunctionalInterface
public interface CacheMeterBinderProvider<C extends Cache> {
    public MeterBinder getMeterBinder(C var1, Iterable<Tag> var2);
}

