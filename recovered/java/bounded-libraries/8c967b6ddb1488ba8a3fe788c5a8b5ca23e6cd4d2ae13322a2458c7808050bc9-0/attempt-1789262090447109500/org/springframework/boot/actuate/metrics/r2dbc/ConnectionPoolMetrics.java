/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.Gauge
 *  io.micrometer.core.instrument.Gauge$Builder
 *  io.micrometer.core.instrument.MeterRegistry
 *  io.micrometer.core.instrument.Tag
 *  io.micrometer.core.instrument.Tags
 *  io.micrometer.core.instrument.binder.MeterBinder
 *  io.r2dbc.pool.ConnectionPool
 *  io.r2dbc.pool.PoolMetrics
 */
package org.springframework.boot.actuate.metrics.r2dbc;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.PoolMetrics;

public class ConnectionPoolMetrics
implements MeterBinder {
    private static final String CONNECTIONS = "connections";
    private final ConnectionPool pool;
    private final Iterable<Tag> tags;

    public ConnectionPoolMetrics(ConnectionPool pool, String name, Iterable<Tag> tags) {
        this.pool = pool;
        this.tags = Tags.concat(tags, (String[])new String[]{"name", name});
    }

    public void bindTo(MeterRegistry registry) {
        this.pool.getMetrics().ifPresent(poolMetrics -> {
            this.bindConnectionPoolMetric(registry, Gauge.builder((String)ConnectionPoolMetrics.metricKey("acquired"), (Object)poolMetrics, PoolMetrics::acquiredSize).description("Size of successfully acquired connections which are in active use."));
            this.bindConnectionPoolMetric(registry, Gauge.builder((String)ConnectionPoolMetrics.metricKey("allocated"), (Object)poolMetrics, PoolMetrics::allocatedSize).description("Size of allocated connections in the pool which are in active use or idle."));
            this.bindConnectionPoolMetric(registry, Gauge.builder((String)ConnectionPoolMetrics.metricKey("idle"), (Object)poolMetrics, PoolMetrics::idleSize).description("Size of idle connections in the pool."));
            this.bindConnectionPoolMetric(registry, Gauge.builder((String)ConnectionPoolMetrics.metricKey("pending"), (Object)poolMetrics, PoolMetrics::pendingAcquireSize).description("Size of pending to acquire connections from the underlying connection factory."));
            this.bindConnectionPoolMetric(registry, Gauge.builder((String)ConnectionPoolMetrics.metricKey("max.allocated"), (Object)poolMetrics, PoolMetrics::getMaxAllocatedSize).description("Maximum size of allocated connections that this pool allows."));
            this.bindConnectionPoolMetric(registry, Gauge.builder((String)ConnectionPoolMetrics.metricKey("max.pending"), (Object)poolMetrics, PoolMetrics::getMaxPendingAcquireSize).description("Maximum size of pending state to acquire connections that this pool allows."));
        });
    }

    private void bindConnectionPoolMetric(MeterRegistry registry, Gauge.Builder<?> builder) {
        builder.tags(this.tags).baseUnit(CONNECTIONS).register(registry);
    }

    private static String metricKey(String name) {
        return "r2dbc.pool." + name;
    }
}

