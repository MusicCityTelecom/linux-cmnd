/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.springframework.boot.actuate.endpoint.web;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
import org.springframework.boot.actuate.endpoint.web.Link;
import org.springframework.boot.actuate.endpoint.web.PathMappedEndpoint;
import org.springframework.boot.actuate.endpoint.web.WebOperation;

public class EndpointLinksResolver {
    private static final Log logger = LogFactory.getLog(EndpointLinksResolver.class);
    private final Collection<? extends ExposableEndpoint<?>> endpoints;

    public EndpointLinksResolver(Collection<? extends ExposableEndpoint<?>> endpoints) {
        this.endpoints = endpoints;
    }

    public EndpointLinksResolver(Collection<? extends ExposableEndpoint<?>> endpoints, String basePath) {
        this.endpoints = endpoints;
        if (logger.isInfoEnabled()) {
            logger.info((Object)("Exposing " + endpoints.size() + " endpoint(s) beneath base path '" + basePath + "'"));
        }
    }

    public Map<String, Link> resolveLinks(String requestUrl) {
        String normalizedUrl = this.normalizeRequestUrl(requestUrl);
        LinkedHashMap<String, Link> links = new LinkedHashMap<String, Link>();
        links.put("self", new Link(normalizedUrl));
        for (ExposableEndpoint<?> endpoint : this.endpoints) {
            if (endpoint instanceof ExposableWebEndpoint) {
                this.collectLinks(links, (ExposableWebEndpoint)endpoint, normalizedUrl);
                continue;
            }
            if (!(endpoint instanceof PathMappedEndpoint)) continue;
            String rootPath = ((PathMappedEndpoint)((Object)endpoint)).getRootPath();
            Link link = this.createLink(normalizedUrl, rootPath);
            links.put(endpoint.getEndpointId().toLowerCaseString(), link);
        }
        return links;
    }

    private String normalizeRequestUrl(String requestUrl) {
        if (requestUrl.endsWith("/")) {
            return requestUrl.substring(0, requestUrl.length() - 1);
        }
        return requestUrl;
    }

    private void collectLinks(Map<String, Link> links, ExposableWebEndpoint endpoint, String normalizedUrl) {
        for (WebOperation operation : endpoint.getOperations()) {
            links.put(operation.getId(), this.createLink(normalizedUrl, operation));
        }
    }

    private Link createLink(String requestUrl, WebOperation operation) {
        return this.createLink(requestUrl, operation.getRequestPredicate().getPath());
    }

    private Link createLink(String requestUrl, String path) {
        return new Link(requestUrl + (path.startsWith("/") ? path : "/" + path));
    }
}

