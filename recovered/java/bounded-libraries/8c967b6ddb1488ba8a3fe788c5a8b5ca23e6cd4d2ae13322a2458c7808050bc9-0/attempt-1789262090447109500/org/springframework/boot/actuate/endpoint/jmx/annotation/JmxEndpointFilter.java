/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.jmx.annotation;

import org.springframework.boot.actuate.endpoint.annotation.DiscovererEndpointFilter;
import org.springframework.boot.actuate.endpoint.jmx.annotation.JmxEndpointDiscoverer;

class JmxEndpointFilter
extends DiscovererEndpointFilter {
    JmxEndpointFilter() {
        super(JmxEndpointDiscoverer.class);
    }
}

