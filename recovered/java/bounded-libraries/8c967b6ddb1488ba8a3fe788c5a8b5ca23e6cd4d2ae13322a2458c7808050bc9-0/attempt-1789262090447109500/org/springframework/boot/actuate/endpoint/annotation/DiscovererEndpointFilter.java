/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.endpoint.annotation;

import org.springframework.boot.actuate.endpoint.EndpointFilter;
import org.springframework.boot.actuate.endpoint.annotation.DiscoveredEndpoint;
import org.springframework.boot.actuate.endpoint.annotation.EndpointDiscoverer;
import org.springframework.util.Assert;

public abstract class DiscovererEndpointFilter
implements EndpointFilter<DiscoveredEndpoint<?>> {
    private final Class<? extends EndpointDiscoverer<?, ?>> discoverer;

    protected DiscovererEndpointFilter(Class<? extends EndpointDiscoverer<?, ?>> discoverer) {
        Assert.notNull(discoverer, (String)"Discoverer must not be null");
        this.discoverer = discoverer;
    }

    @Override
    public boolean match(DiscoveredEndpoint<?> endpoint) {
        return endpoint.wasDiscoveredBy(this.discoverer);
    }
}

