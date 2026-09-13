/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.couchbase.client.core.diagnostics.ClusterState
 *  com.couchbase.client.core.diagnostics.DiagnosticsResult
 *  com.couchbase.client.core.diagnostics.EndpointDiagnostics
 */
package org.springframework.boot.actuate.couchbase;

import com.couchbase.client.core.diagnostics.ClusterState;
import com.couchbase.client.core.diagnostics.DiagnosticsResult;
import com.couchbase.client.core.diagnostics.EndpointDiagnostics;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.boot.actuate.health.Health;

class CouchbaseHealth {
    private final DiagnosticsResult diagnostics;

    CouchbaseHealth(DiagnosticsResult diagnostics) {
        this.diagnostics = diagnostics;
    }

    void applyTo(Health.Builder builder) {
        builder = this.isCouchbaseUp(this.diagnostics) ? builder.up() : builder.down();
        builder.withDetail("sdk", this.diagnostics.sdk());
        builder.withDetail("endpoints", this.diagnostics.endpoints().values().stream().flatMap(Collection::stream).map(this::describe).collect(Collectors.toList()));
    }

    private boolean isCouchbaseUp(DiagnosticsResult diagnostics) {
        return diagnostics.state() == ClusterState.ONLINE;
    }

    private Map<String, Object> describe(EndpointDiagnostics endpointHealth) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", endpointHealth.id());
        map.put("lastActivity", endpointHealth.lastActivity());
        map.put("local", endpointHealth.local());
        map.put("remote", endpointHealth.remote());
        map.put("state", endpointHealth.state());
        map.put("type", endpointHealth.type());
        return map;
    }
}

