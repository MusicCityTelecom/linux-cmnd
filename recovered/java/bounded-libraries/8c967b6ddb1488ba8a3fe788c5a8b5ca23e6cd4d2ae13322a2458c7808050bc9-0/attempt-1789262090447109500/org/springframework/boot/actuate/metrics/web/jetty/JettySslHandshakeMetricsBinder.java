/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.MeterRegistry
 *  io.micrometer.core.instrument.Tag
 *  io.micrometer.core.instrument.binder.jetty.JettySslHandshakeMetrics
 *  org.eclipse.jetty.server.Server
 */
package org.springframework.boot.actuate.metrics.web.jetty;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.jetty.JettySslHandshakeMetrics;
import java.util.Collections;
import org.eclipse.jetty.server.Server;
import org.springframework.boot.actuate.metrics.web.jetty.AbstractJettyMetricsBinder;

public class JettySslHandshakeMetricsBinder
extends AbstractJettyMetricsBinder {
    private final MeterRegistry meterRegistry;
    private final Iterable<Tag> tags;

    public JettySslHandshakeMetricsBinder(MeterRegistry meterRegistry) {
        this(meterRegistry, Collections.emptyList());
    }

    public JettySslHandshakeMetricsBinder(MeterRegistry meterRegistry, Iterable<Tag> tags) {
        this.meterRegistry = meterRegistry;
        this.tags = tags;
    }

    @Override
    protected void bindMetrics(Server server) {
        JettySslHandshakeMetrics.addToAllConnectors((Server)server, (MeterRegistry)this.meterRegistry, this.tags);
    }
}

