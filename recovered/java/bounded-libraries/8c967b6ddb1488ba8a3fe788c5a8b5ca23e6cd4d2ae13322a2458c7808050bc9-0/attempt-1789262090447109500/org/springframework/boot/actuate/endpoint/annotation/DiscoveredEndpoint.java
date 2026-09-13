/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.annotation;

import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.Operation;
import org.springframework.boot.actuate.endpoint.annotation.EndpointDiscoverer;

public interface DiscoveredEndpoint<O extends Operation>
extends ExposableEndpoint<O> {
    public boolean wasDiscoveredBy(Class<? extends EndpointDiscoverer<?, ?>> var1);

    public Object getEndpointBean();
}

