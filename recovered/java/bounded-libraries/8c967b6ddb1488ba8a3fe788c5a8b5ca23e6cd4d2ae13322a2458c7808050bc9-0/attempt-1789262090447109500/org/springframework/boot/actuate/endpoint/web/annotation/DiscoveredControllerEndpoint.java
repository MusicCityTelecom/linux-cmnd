/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.web.annotation;

import java.util.Collections;
import org.springframework.boot.actuate.endpoint.EndpointId;
import org.springframework.boot.actuate.endpoint.Operation;
import org.springframework.boot.actuate.endpoint.annotation.AbstractDiscoveredEndpoint;
import org.springframework.boot.actuate.endpoint.annotation.EndpointDiscoverer;
import org.springframework.boot.actuate.endpoint.web.annotation.ExposableControllerEndpoint;

class DiscoveredControllerEndpoint
extends AbstractDiscoveredEndpoint<Operation>
implements ExposableControllerEndpoint {
    private final String rootPath;

    DiscoveredControllerEndpoint(EndpointDiscoverer<?, ?> discoverer, Object endpointBean, EndpointId id, String rootPath, boolean enabledByDefault) {
        super(discoverer, endpointBean, id, enabledByDefault, Collections.emptyList());
        this.rootPath = rootPath;
    }

    @Override
    public Object getController() {
        return this.getEndpointBean();
    }

    @Override
    public String getRootPath() {
        return this.rootPath;
    }
}

