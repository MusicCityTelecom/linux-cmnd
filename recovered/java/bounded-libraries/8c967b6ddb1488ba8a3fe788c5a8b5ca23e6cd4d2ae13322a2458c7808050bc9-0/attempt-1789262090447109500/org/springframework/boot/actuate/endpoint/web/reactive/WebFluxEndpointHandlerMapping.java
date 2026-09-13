/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.cors.CorsConfiguration
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.util.UriComponentsBuilder
 */
package org.springframework.boot.actuate.endpoint.web.reactive;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.actuate.endpoint.web.EndpointLinksResolver;
import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
import org.springframework.boot.actuate.endpoint.web.Link;
import org.springframework.boot.actuate.endpoint.web.reactive.AbstractWebFluxEndpointHandlerMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;

public class WebFluxEndpointHandlerMapping
extends AbstractWebFluxEndpointHandlerMapping
implements InitializingBean {
    private final EndpointLinksResolver linksResolver;

    public WebFluxEndpointHandlerMapping(EndpointMapping endpointMapping, Collection<ExposableWebEndpoint> endpoints, EndpointMediaTypes endpointMediaTypes, CorsConfiguration corsConfiguration, EndpointLinksResolver linksResolver, boolean shouldRegisterLinksMapping) {
        super(endpointMapping, endpoints, endpointMediaTypes, corsConfiguration, shouldRegisterLinksMapping);
        this.linksResolver = linksResolver;
        this.setOrder(-100);
    }

    @Override
    protected AbstractWebFluxEndpointHandlerMapping.LinksHandler getLinksHandler() {
        return new WebFluxLinksHandler();
    }

    class WebFluxLinksHandler
    implements AbstractWebFluxEndpointHandlerMapping.LinksHandler {
        WebFluxLinksHandler() {
        }

        @Override
        @ResponseBody
        public Map<String, Map<String, Link>> links(ServerWebExchange exchange) {
            String requestUri = UriComponentsBuilder.fromUri((URI)exchange.getRequest().getURI()).replaceQuery(null).toUriString();
            return Collections.singletonMap("_links", WebFluxEndpointHandlerMapping.this.linksResolver.resolveLinks(requestUri));
        }

        public String toString() {
            return "Actuator root web endpoint";
        }
    }
}

