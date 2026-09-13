/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.StringUtils
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.reactive.result.method.RequestMappingInfo
 */
package org.springframework.boot.actuate.endpoint.web.reactive;

import java.util.Collections;
import java.util.Set;
import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
import org.springframework.boot.actuate.endpoint.web.WebOperation;
import org.springframework.boot.actuate.endpoint.web.WebOperationRequestPredicate;
import org.springframework.boot.actuate.endpoint.web.reactive.AbstractWebFluxEndpointHandlerMapping;
import org.springframework.boot.actuate.health.AdditionalHealthEndpointPath;
import org.springframework.boot.actuate.health.HealthEndpointGroup;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.result.method.RequestMappingInfo;

public class AdditionalHealthEndpointPathsWebFluxHandlerMapping
extends AbstractWebFluxEndpointHandlerMapping {
    private final EndpointMapping endpointMapping;
    private final ExposableWebEndpoint endpoint;
    private final Set<HealthEndpointGroup> groups;

    public AdditionalHealthEndpointPathsWebFluxHandlerMapping(EndpointMapping endpointMapping, ExposableWebEndpoint endpoint, Set<HealthEndpointGroup> groups) {
        super(endpointMapping, Collections.singletonList(endpoint), null, null, false);
        this.endpointMapping = endpointMapping;
        this.groups = groups;
        this.endpoint = endpoint;
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
                RequestMappingInfo requestMappingInfo = this.getRequestMappingInfo(operation, additionalPath.getValue());
                this.registerReadMapping(requestMappingInfo, this.endpoint, operation);
            }
        }
    }

    private RequestMappingInfo getRequestMappingInfo(WebOperation operation, String additionalPath) {
        WebOperationRequestPredicate predicate = operation.getRequestPredicate();
        String path = this.endpointMapping.createSubPath(additionalPath);
        RequestMethod method = RequestMethod.valueOf((String)predicate.getHttpMethod().name());
        String[] consumes = StringUtils.toStringArray(predicate.getConsumes());
        String[] produces = StringUtils.toStringArray(predicate.getProduces());
        return RequestMappingInfo.paths((String[])new String[]{path}).methods(new RequestMethod[]{method}).consumes(consumes).produces(produces).build();
    }

    @Override
    protected AbstractWebFluxEndpointHandlerMapping.LinksHandler getLinksHandler() {
        return null;
    }
}

