/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint;

import org.springframework.boot.actuate.endpoint.ExposableEndpoint;

@FunctionalInterface
public interface EndpointFilter<E extends ExposableEndpoint<?>> {
    public boolean match(E var1);
}

