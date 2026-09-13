/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.couchbase.client.core.diagnostics.DiagnosticsResult
 *  com.couchbase.client.java.Cluster
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.couchbase;

import com.couchbase.client.core.diagnostics.DiagnosticsResult;
import com.couchbase.client.java.Cluster;
import org.springframework.boot.actuate.couchbase.CouchbaseHealth;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.util.Assert;

public class CouchbaseHealthIndicator
extends AbstractHealthIndicator {
    private final Cluster cluster;

    public CouchbaseHealthIndicator(Cluster cluster) {
        super("Couchbase health check failed");
        Assert.notNull((Object)cluster, (String)"Cluster must not be null");
        this.cluster = cluster;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        DiagnosticsResult diagnostics = this.cluster.diagnostics();
        new CouchbaseHealth(diagnostics).applyTo(builder);
    }
}

