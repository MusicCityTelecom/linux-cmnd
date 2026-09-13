/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.env;

import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.endpoint.web.annotation.EndpointWebExtension;
import org.springframework.boot.actuate.env.EnvironmentEndpoint;

@EndpointWebExtension(endpoint=EnvironmentEndpoint.class)
public class EnvironmentEndpointWebExtension {
    private final EnvironmentEndpoint delegate;

    public EnvironmentEndpointWebExtension(EnvironmentEndpoint delegate) {
        this.delegate = delegate;
    }

    @ReadOperation
    public WebEndpointResponse<EnvironmentEndpoint.EnvironmentEntryDescriptor> environmentEntry(@Selector String toMatch) {
        EnvironmentEndpoint.EnvironmentEntryDescriptor descriptor = this.delegate.environmentEntry(toMatch);
        return descriptor.getProperty() != null ? new WebEndpointResponse<EnvironmentEndpoint.EnvironmentEntryDescriptor>(descriptor, 200) : new WebEndpointResponse<int>(404);
    }
}

