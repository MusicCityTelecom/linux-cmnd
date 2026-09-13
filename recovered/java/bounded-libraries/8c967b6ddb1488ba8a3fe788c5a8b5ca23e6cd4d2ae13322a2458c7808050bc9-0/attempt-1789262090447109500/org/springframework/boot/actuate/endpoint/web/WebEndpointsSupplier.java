/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.web;

import org.springframework.boot.actuate.endpoint.EndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;

@FunctionalInterface
public interface WebEndpointsSupplier
extends EndpointsSupplier<ExposableWebEndpoint> {
}

