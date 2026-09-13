/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.couchbase.client.core.diagnostics.DiagnosticsResult
 *  com.couchbase.client.java.Cluster
 *  reactor.core.publisher.Mono
 */
package org.springframework.boot.actuate.couchbase;

import com.couchbase.client.core.diagnostics.DiagnosticsResult;
import com.couchbase.client.java.Cluster;
import org.springframework.boot.actuate.couchbase.CouchbaseHealth;
import org.springframework.boot.actuate.health.AbstractReactiveHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import reactor.core.publisher.Mono;

public class CouchbaseReactiveHealthIndicator
extends AbstractReactiveHealthIndicator {
    private final Cluster cluster;

    public CouchbaseReactiveHealthIndicator(Cluster cluster) {
        super("Couchbase health check failed");
        this.cluster = cluster;
    }

    @Override
    protected Mono<Health> doHealthCheck(Health.Builder builder) {
        DiagnosticsResult diagnostics = this.cluster.diagnostics();
        new CouchbaseHealth(diagnostics).applyTo(builder);
        return Mono.just((Object)builder.build());
    }
}

