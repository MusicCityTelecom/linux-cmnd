/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.annotation.Timed
 *  io.micrometer.core.instrument.MeterRegistry
 *  io.micrometer.core.instrument.Tag
 *  org.springframework.data.repository.core.support.RepositoryMethodInvocationListener
 *  org.springframework.data.repository.core.support.RepositoryMethodInvocationListener$RepositoryMethodInvocation
 *  org.springframework.util.function.SingletonSupplier
 */
package org.springframework.boot.actuate.metrics.data;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import org.springframework.boot.actuate.metrics.AutoTimer;
import org.springframework.boot.actuate.metrics.annotation.TimedAnnotations;
import org.springframework.boot.actuate.metrics.data.RepositoryTagsProvider;
import org.springframework.data.repository.core.support.RepositoryMethodInvocationListener;
import org.springframework.util.function.SingletonSupplier;

public class MetricsRepositoryMethodInvocationListener
implements RepositoryMethodInvocationListener {
    private final SingletonSupplier<MeterRegistry> registrySupplier;
    private final RepositoryTagsProvider tagsProvider;
    private final String metricName;
    private final AutoTimer autoTimer;

    public MetricsRepositoryMethodInvocationListener(Supplier<MeterRegistry> registrySupplier, RepositoryTagsProvider tagsProvider, String metricName, AutoTimer autoTimer) {
        this.registrySupplier = registrySupplier instanceof SingletonSupplier ? (SingletonSupplier)registrySupplier : SingletonSupplier.of(registrySupplier);
        this.tagsProvider = tagsProvider;
        this.metricName = metricName;
        this.autoTimer = autoTimer != null ? autoTimer : AutoTimer.DISABLED;
    }

    public void afterInvocation(RepositoryMethodInvocationListener.RepositoryMethodInvocation invocation) {
        Set<Timed> annotations = TimedAnnotations.get(invocation.getMethod(), invocation.getRepositoryInterface());
        Iterable<Tag> tags = this.tagsProvider.repositoryTags(invocation);
        long duration = invocation.getDuration(TimeUnit.NANOSECONDS);
        AutoTimer.apply(this.autoTimer, this.metricName, annotations, builder -> builder.description("Duration of repository invocations").tags(tags).register((MeterRegistry)this.registrySupplier.get()).record(duration, TimeUnit.NANOSECONDS));
    }
}

