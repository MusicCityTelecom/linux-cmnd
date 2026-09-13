/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.web.annotation;

import org.springframework.boot.actuate.endpoint.EndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ExposableControllerEndpoint;

@FunctionalInterface
public interface ControllerEndpointsSupplier
extends EndpointsSupplier<ExposableControllerEndpoint> {
}

