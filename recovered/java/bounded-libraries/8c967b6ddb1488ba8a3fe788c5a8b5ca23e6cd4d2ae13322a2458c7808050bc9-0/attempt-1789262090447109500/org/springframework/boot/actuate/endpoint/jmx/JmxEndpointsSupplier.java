/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.jmx;

import org.springframework.boot.actuate.endpoint.EndpointsSupplier;
import org.springframework.boot.actuate.endpoint.jmx.ExposableJmxEndpoint;

@FunctionalInterface
public interface JmxEndpointsSupplier
extends EndpointsSupplier<ExposableJmxEndpoint> {
}

