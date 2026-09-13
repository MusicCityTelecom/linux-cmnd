/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.web.annotation;

import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.Operation;
import org.springframework.boot.actuate.endpoint.web.PathMappedEndpoint;

public interface ExposableControllerEndpoint
extends ExposableEndpoint<Operation>,
PathMappedEndpoint {
    public Object getController();
}

