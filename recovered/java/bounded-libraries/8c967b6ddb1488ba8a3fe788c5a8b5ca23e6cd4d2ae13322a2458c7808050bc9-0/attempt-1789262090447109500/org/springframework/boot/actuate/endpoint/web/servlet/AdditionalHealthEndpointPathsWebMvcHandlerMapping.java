/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.web.servlet;

import java.util.Collections;
import java.util.Set;
import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
import org.springframework.boot.actuate.endpoint.web.WebOperation;
import org.springframework.boot.actuate.endpoint.web.WebOperationRequestPredicate;
import org.springframework.boot.actuate.endpoint.web.servlet.AbstractWebMvcEndpointHandlerMapping;
import org.springframework.boot.actuate.health.AdditionalHealthEndpointPath;
import org.springframework.boot.actuate.health.HealthEndpointGroup;

public class AdditionalHealthEndpointPathsWebMvcHandlerMapping
extends AbstractWebMvcEndpointHandlerMapping {
    private final ExposableWebEndpoint endpoint;
    private final Set<HealthEndpointGroup> groups;

    public AdditionalHealthEndpointPathsWebMvcHandlerMapping(ExposableWebEndpoint endpoint, Set<HealthEndpointGroup> groups) {
        super(new EndpointMapping(""), Collections.singletonList(endpoint), null, false);
        this.endpoint = endpoint;
        this.groups = groups;
    }

    @Override
    protected void initHandlerMethods() {
        for (WebOperation operation : this.endpoint.getOperations()) {
            WebOperationRequestPredicate predicate = operation.getRequestPredicate();
            String matchAllRemainingPathSegmentsVariable = predicate.getMatchAllRemainingPathSegmentsVariable();
            if (matchAllRemainingPathSegmentsVariable == null) continue;
            for (HealthEndpointGroup group : this.groups) {
                AdditionalHealthEndpointPath additionalPath = group.getAdditionalPath();
                if (additionalPath == null) continue;
                this.registerMapping(this.endpoint, predicate, operation, additionalPath.getValue());
            }
        }
    }

    @Override
    protected AbstractWebMvcEndpointHandlerMapping.LinksHandler getLinksHandler() {
        return null;
    }
}

