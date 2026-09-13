/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint;

import java.util.Collection;
import org.springframework.boot.actuate.endpoint.EndpointId;
import org.springframework.boot.actuate.endpoint.Operation;

public interface ExposableEndpoint<O extends Operation> {
    public EndpointId getEndpointId();

    public boolean isEnableByDefault();

    public Collection<O> getOperations();
}

