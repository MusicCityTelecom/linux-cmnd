/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.cors.CorsConfiguration
 *  org.springframework.web.util.pattern.PathPatternParser
 */
package org.springframework.boot.actuate.endpoint.web.servlet;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.actuate.endpoint.web.EndpointLinksResolver;
import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
import org.springframework.boot.actuate.endpoint.web.Link;
import org.springframework.boot.actuate.endpoint.web.servlet.AbstractWebMvcEndpointHandlerMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.util.pattern.PathPatternParser;

public class WebMvcEndpointHandlerMapping
extends AbstractWebMvcEndpointHandlerMapping {
    private final EndpointLinksResolver linksResolver;

    public WebMvcEndpointHandlerMapping(EndpointMapping endpointMapping, Collection<ExposableWebEndpoint> endpoints, EndpointMediaTypes endpointMediaTypes, CorsConfiguration corsConfiguration, EndpointLinksResolver linksResolver, boolean shouldRegisterLinksMapping) {
        super(endpointMapping, endpoints, endpointMediaTypes, corsConfiguration, shouldRegisterLinksMapping);
        this.linksResolver = linksResolver;
        this.setOrder(-100);
    }

    public WebMvcEndpointHandlerMapping(EndpointMapping endpointMapping, Collection<ExposableWebEndpoint> endpoints, EndpointMediaTypes endpointMediaTypes, CorsConfiguration corsConfiguration, EndpointLinksResolver linksResolver, boolean shouldRegisterLinksMapping, PathPatternParser pathPatternParser) {
        super(endpointMapping, endpoints, endpointMediaTypes, corsConfiguration, shouldRegisterLinksMapping, pathPatternParser);
        this.linksResolver = linksResolver;
        this.setOrder(-100);
    }

    @Override
    protected AbstractWebMvcEndpointHandlerMapping.LinksHandler getLinksHandler() {
        return new WebMvcLinksHandler();
    }

    class WebMvcLinksHandler
    implements AbstractWebMvcEndpointHandlerMapping.LinksHandler {
        WebMvcLinksHandler() {
        }

        @Override
        @ResponseBody
        public Map<String, Map<String, Link>> links(HttpServletRequest request, HttpServletResponse response) {
            return Collections.singletonMap("_links", WebMvcEndpointHandlerMapping.this.linksResolver.resolveLinks(request.getRequestURL().toString()));
        }

        public String toString() {
            return "Actuator root web endpoint";
        }
    }
}

