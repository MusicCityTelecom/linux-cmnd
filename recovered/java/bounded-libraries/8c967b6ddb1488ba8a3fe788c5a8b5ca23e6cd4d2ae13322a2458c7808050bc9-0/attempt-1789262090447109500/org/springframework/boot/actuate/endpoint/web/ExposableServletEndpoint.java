/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.web;

import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.Operation;
import org.springframework.boot.actuate.endpoint.web.EndpointServlet;
import org.springframework.boot.actuate.endpoint.web.PathMappedEndpoint;

public interface ExposableServletEndpoint
extends ExposableEndpoint<Operation>,
PathMappedEndpoint {
    public EndpointServlet getEndpointServlet();
}

