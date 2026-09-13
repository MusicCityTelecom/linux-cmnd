/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint;

import java.util.Collection;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;

@FunctionalInterface
public interface EndpointsSupplier<E extends ExposableEndpoint<?>> {
    public Collection<E> getEndpoints();
}

