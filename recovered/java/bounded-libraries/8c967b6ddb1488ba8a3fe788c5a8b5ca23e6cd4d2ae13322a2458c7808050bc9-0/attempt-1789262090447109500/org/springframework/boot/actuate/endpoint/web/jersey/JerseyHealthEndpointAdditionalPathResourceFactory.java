/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.glassfish.jersey.server.model.Resource
 */
package org.springframework.boot.actuate.endpoint.web.jersey;

import java.util.Set;
import org.glassfish.jersey.server.model.Resource;
import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
import org.springframework.boot.actuate.endpoint.web.WebOperation;
import org.springframework.boot.actuate.endpoint.web.WebOperationRequestPredicate;
import org.springframework.boot.actuate.endpoint.web.WebServerNamespace;
import org.springframework.boot.actuate.endpoint.web.jersey.JerseyEndpointResourceFactory;
import org.springframework.boot.actuate.health.AdditionalHealthEndpointPath;
import org.springframework.boot.actuate.health.HealthEndpointGroup;
import org.springframework.boot.actuate.health.HealthEndpointGroups;

public class JerseyHealthEndpointAdditionalPathResourceFactory
extends JerseyEndpointResourceFactory {
    private final Set<HealthEndpointGroup> groups;
    private final WebServerNamespace serverNamespace;

    public JerseyHealthEndpointAdditionalPathResourceFactory(WebServerNamespace serverNamespace, HealthEndpointGroups groups) {
        this.serverNamespace = serverNamespace;
        this.groups = groups.getAllWithAdditionalPath(serverNamespace);
    }

    @Override
    protected Resource createResource(EndpointMapping endpointMapping, WebOperation operation) {
        WebOperationRequestPredicate requestPredicate = operation.getRequestPredicate();
        String matchAllRemainingPathSegmentsVariable = requestPredicate.getMatchAllRemainingPathSegmentsVariable();
        if (matchAllRemainingPathSegmentsVariable != null) {
            for (HealthEndpointGroup group : this.groups) {
                AdditionalHealthEndpointPath additionalPath = group.getAdditionalPath();
                if (additionalPath == null) continue;
                return this.getResource(endpointMapping, operation, requestPredicate, additionalPath.getValue(), this.serverNamespace, (data, pathSegmentsVariable) -> data.getUriInfo().getPath());
            }
        }
        return null;
    }
}

