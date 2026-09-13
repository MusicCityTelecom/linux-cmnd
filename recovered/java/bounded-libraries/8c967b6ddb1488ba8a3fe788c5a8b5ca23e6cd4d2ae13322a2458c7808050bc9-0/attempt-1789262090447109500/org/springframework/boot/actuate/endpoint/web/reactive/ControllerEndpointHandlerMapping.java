/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.web.cors.CorsConfiguration
 *  org.springframework.web.reactive.result.method.RequestMappingInfo
 *  org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping
 *  org.springframework.web.util.pattern.PathPattern
 */
package org.springframework.boot.actuate.endpoint.web.reactive;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
import org.springframework.boot.actuate.endpoint.web.annotation.ExposableControllerEndpoint;
import org.springframework.util.Assert;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.reactive.result.method.RequestMappingInfo;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

public class ControllerEndpointHandlerMapping
extends RequestMappingHandlerMapping {
    private final EndpointMapping endpointMapping;
    private final CorsConfiguration corsConfiguration;
    private final Map<Object, ExposableControllerEndpoint> handlers;

    public ControllerEndpointHandlerMapping(EndpointMapping endpointMapping, Collection<ExposableControllerEndpoint> endpoints, CorsConfiguration corsConfiguration) {
        Assert.notNull((Object)endpointMapping, (String)"EndpointMapping must not be null");
        Assert.notNull(endpoints, (String)"Endpoints must not be null");
        this.endpointMapping = endpointMapping;
        this.handlers = this.getHandlers(endpoints);
        this.corsConfiguration = corsConfiguration;
        this.setOrder(-100);
    }

    private Map<Object, ExposableControllerEndpoint> getHandlers(Collection<ExposableControllerEndpoint> endpoints) {
        LinkedHashMap handlers = new LinkedHashMap();
        endpoints.forEach(endpoint -> handlers.put(endpoint.getController(), endpoint));
        return Collections.unmodifiableMap(handlers);
    }

    protected void initHandlerMethods() {
        this.handlers.keySet().forEach(arg_0 -> ((ControllerEndpointHandlerMapping)this).detectHandlerMethods(arg_0));
    }

    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
        ExposableControllerEndpoint endpoint = this.handlers.get(handler);
        mapping = this.withEndpointMappedPatterns(endpoint, mapping);
        super.registerHandlerMethod(handler, method, mapping);
    }

    private RequestMappingInfo withEndpointMappedPatterns(ExposableControllerEndpoint endpoint, RequestMappingInfo mapping) {
        Set<PathPattern> patterns = mapping.getPatternsCondition().getPatterns();
        if (patterns.isEmpty()) {
            patterns = Collections.singleton(this.getPathPatternParser().parse(""));
        }
        String[] endpointMappedPatterns = (String[])patterns.stream().map(pattern -> this.getEndpointMappedPattern(endpoint, (PathPattern)pattern)).toArray(String[]::new);
        return mapping.mutate().paths(endpointMappedPatterns).build();
    }

    private String getEndpointMappedPattern(ExposableControllerEndpoint endpoint, PathPattern pattern) {
        return this.endpointMapping.createSubPath(endpoint.getRootPath() + pattern);
    }

    protected boolean hasCorsConfigurationSource(Object handler) {
        return this.corsConfiguration != null;
    }

    protected CorsConfiguration initCorsConfiguration(Object handler, Method method, RequestMappingInfo mapping) {
        return this.corsConfiguration;
    }
}

