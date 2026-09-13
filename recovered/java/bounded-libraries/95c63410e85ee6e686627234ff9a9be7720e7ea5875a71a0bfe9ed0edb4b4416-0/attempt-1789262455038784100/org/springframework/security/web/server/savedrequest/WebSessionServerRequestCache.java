/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.MediaType
 *  org.springframework.http.server.reactive.ServerHttpRequest
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.server.WebSession
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.savedrequest;

import java.net.URI;
import java.util.Collections;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.web.server.savedrequest.ServerRequestCache;
import org.springframework.security.web.server.util.matcher.AndServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.MediaTypeServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

public class WebSessionServerRequestCache
implements ServerRequestCache {
    private static final String DEFAULT_SAVED_REQUEST_ATTR = "SPRING_SECURITY_SAVED_REQUEST";
    private static final Log logger = LogFactory.getLog(WebSessionServerRequestCache.class);
    private String sessionAttrName = "SPRING_SECURITY_SAVED_REQUEST";
    private ServerWebExchangeMatcher saveRequestMatcher = WebSessionServerRequestCache.createDefaultRequestMacher();

    public void setSaveRequestMatcher(ServerWebExchangeMatcher saveRequestMatcher) {
        Assert.notNull((Object)saveRequestMatcher, (String)"saveRequestMatcher cannot be null");
        this.saveRequestMatcher = saveRequestMatcher;
    }

    @Override
    public Mono<Void> saveRequest(ServerWebExchange exchange) {
        return this.saveRequestMatcher.matches(exchange).filter(ServerWebExchangeMatcher.MatchResult::isMatch).flatMap(m -> exchange.getSession()).map(WebSession::getAttributes).doOnNext(attrs -> {
            String requestPath = WebSessionServerRequestCache.pathInApplication(exchange.getRequest());
            attrs.put(this.sessionAttrName, requestPath);
            logger.debug((Object)LogMessage.format((String)"Request added to WebSession: '%s'", (Object)requestPath));
        }).then();
    }

    @Override
    public Mono<URI> getRedirectUri(ServerWebExchange exchange) {
        return exchange.getSession().flatMap(session -> Mono.justOrEmpty((Object)((String)session.getAttribute(this.sessionAttrName)))).map(URI::create);
    }

    @Override
    public Mono<ServerHttpRequest> removeMatchingRequest(ServerWebExchange exchange) {
        return exchange.getSession().map(WebSession::getAttributes).filter(attributes -> {
            String requestPath = WebSessionServerRequestCache.pathInApplication(exchange.getRequest());
            boolean removed = attributes.remove(this.sessionAttrName, requestPath);
            if (removed) {
                logger.debug((Object)LogMessage.format((String)"Request removed from WebSession: '%s'", (Object)requestPath));
            }
            return removed;
        }).map(attributes -> exchange.getRequest());
    }

    private static String pathInApplication(ServerHttpRequest request) {
        String path = request.getPath().pathWithinApplication().value();
        String query = request.getURI().getRawQuery();
        return path + (query != null ? "?" + query : "");
    }

    private static ServerWebExchangeMatcher createDefaultRequestMacher() {
        ServerWebExchangeMatcher get = ServerWebExchangeMatchers.pathMatchers(HttpMethod.GET, "/**");
        NegatedServerWebExchangeMatcher notFavicon = new NegatedServerWebExchangeMatcher(ServerWebExchangeMatchers.pathMatchers("/favicon.*"));
        MediaTypeServerWebExchangeMatcher html = new MediaTypeServerWebExchangeMatcher(MediaType.TEXT_HTML);
        html.setIgnoredMediaTypes(Collections.singleton(MediaType.ALL));
        return new AndServerWebExchangeMatcher(get, notFavicon, html);
    }
}

