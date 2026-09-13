/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.MeterRegistry
 *  io.micrometer.core.instrument.Tag
 *  io.micrometer.core.instrument.binder.jetty.JettyServerThreadPoolMetrics
 *  org.eclipse.jetty.server.Server
 *  org.eclipse.jetty.util.thread.ThreadPool
 */
package org.springframework.boot.actuate.metrics.web.jetty;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.jetty.JettyServerThreadPoolMetrics;
import java.util.Collections;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.springframework.boot.actuate.metrics.web.jetty.AbstractJettyMetricsBinder;

public class JettyServerThreadPoolMetricsBinder
extends AbstractJettyMetricsBinder {
    private final MeterRegistry meterRegistry;
    private final Iterable<Tag> tags;

    public JettyServerThreadPoolMetricsBinder(MeterRegistry meterRegistry) {
        this(meterRegistry, Collections.emptyList());
    }

    public JettyServerThreadPoolMetricsBinder(MeterRegistry meterRegistry, Iterable<Tag> tags) {
        this.meterRegistry = meterRegistry;
        this.tags = tags;
    }

    @Override
    protected void bindMetrics(Server server) {
        ThreadPool threadPool = server.getThreadPool();
        if (threadPool != null) {
            new JettyServerThreadPoolMetrics(threadPool, this.tags).bindTo(this.meterRegistry);
        }
    }
}

