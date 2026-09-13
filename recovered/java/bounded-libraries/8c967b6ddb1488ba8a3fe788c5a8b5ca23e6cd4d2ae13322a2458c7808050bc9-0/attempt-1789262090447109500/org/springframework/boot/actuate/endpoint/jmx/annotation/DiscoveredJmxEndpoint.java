/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.jmx.annotation;

import java.util.Collection;
import org.springframework.boot.actuate.endpoint.EndpointId;
import org.springframework.boot.actuate.endpoint.annotation.AbstractDiscoveredEndpoint;
import org.springframework.boot.actuate.endpoint.annotation.EndpointDiscoverer;
import org.springframework.boot.actuate.endpoint.jmx.ExposableJmxEndpoint;
import org.springframework.boot.actuate.endpoint.jmx.JmxOperation;

class DiscoveredJmxEndpoint
extends AbstractDiscoveredEndpoint<JmxOperation>
implements ExposableJmxEndpoint {
    DiscoveredJmxEndpoint(EndpointDiscoverer<?, ?> discoverer, Object endpointBean, EndpointId id, boolean enabledByDefault, Collection<JmxOperation> operations) {
        super(discoverer, endpointBean, id, enabledByDefault, operations);
    }
}

