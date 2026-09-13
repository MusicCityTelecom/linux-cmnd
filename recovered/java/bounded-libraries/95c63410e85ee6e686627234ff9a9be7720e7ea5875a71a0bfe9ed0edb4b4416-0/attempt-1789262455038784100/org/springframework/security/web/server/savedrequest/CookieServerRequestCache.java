/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.http.HttpCookie
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.MediaType
 *  org.springframework.http.ResponseCookie
 *  org.springframework.http.server.reactive.ServerHttpRequest
 *  org.springframework.http.server.reactive.ServerHttpResponse
 *  org.springframework.util.Assert
 *  org.springframework.util.MultiValueMap
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.savedrequest;

import java.net.URI;
import java.time.Duration;
import java.util.Base64;
import java.util.Collections;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.web.server.savedrequest.ServerRequestCache;
import org.springframework.security.web.server.util.matcher.AndServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.MediaTypeServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class CookieServerRequestCache
implements ServerRequestCache {
    private static final String REDIRECT_URI_COOKIE_NAME = "REDIRECT_URI";
    private static final Duration COOKIE_MAX_AGE = Duration.ofSeconds(-1L);
    private static final Log logger = LogFactory.getLog(CookieServerRequestCache.class);
    private ServerWebExchangeMatcher saveRequestMatcher = CookieServerRequestCache.createDefaultRequestMatcher();

    public void setSaveRequestMatcher(ServerWebExchangeMatcher saveRequestMatcher) {
        Assert.notNull((Object)saveRequestMatcher, (String)"saveRequestMatcher cannot be null");
        this.saveRequestMatcher = saveRequestMatcher;
    }

    @Override
    public Mono<Void> saveRequest(ServerWebExchange exchange) {
        return this.saveRequestMatcher.matches(exchange).filter(m -> m.isMatch()).map(m -> exchange.getResponse()).map(ServerHttpResponse::getCookies).doOnNext(cookies -> {
            ResponseCookie redirectUriCookie = CookieServerRequestCache.createRedirectUriCookie(exchange.getRequest());
            cookies.add((Object)REDIRECT_URI_COOKIE_NAME, (Object)redirectUriCookie);
            logger.debug((Object)LogMessage.format((String)"Request added to Cookie: %s", (Object)redirectUriCookie));
        }).then();
    }

    @Override
    public Mono<URI> getRedirectUri(ServerWebExchange exchange) {
        MultiValueMap cookieMap = exchange.getRequest().getCookies();
        return Mono.justOrEmpty((Object)((HttpCookie)cookieMap.getFirst((Object)REDIRECT_URI_COOKIE_NAME))).map(HttpCookie::getValue).map(CookieServerRequestCache::decodeCookie).onErrorResume(IllegalArgumentException.class, ex -> Mono.empty()).map(URI::create);
    }

    @Override
    public Mono<ServerHttpRequest> removeMatchingRequest(ServerWebExchange exchange) {
        return Mono.just((Object)exchange.getResponse()).map(ServerHttpResponse::getCookies).doOnNext(cookies -> cookies.add((Object)REDIRECT_URI_COOKIE_NAME, (Object)CookieServerRequestCache.invalidateRedirectUriCookie(exchange.getRequest()))).thenReturn((Object)exchange.getRequest());
    }

    private static ResponseCookie createRedirectUriCookie(ServerHttpRequest request) {
        String path = request.getPath().pathWithinApplication().value();
        String query = request.getURI().getRawQuery();
        String redirectUri = path + (query != null ? "?" + query : "");
        return CookieServerRequestCache.createResponseCookie(request, CookieServerRequestCache.encodeCookie(redirectUri), COOKIE_MAX_AGE);
    }

    private static ResponseCookie invalidateRedirectUriCookie(ServerHttpRequest request) {
        return CookieServerRequestCache.createResponseCookie(request, null, Duration.ZERO);
    }

    private static ResponseCookie createResponseCookie(ServerHttpRequest request, String cookieValue, Duration age) {
        return ResponseCookie.from((String)REDIRECT_URI_COOKIE_NAME, (String)cookieValue).path(request.getPath().contextPath().value() + "/").maxAge(age).httpOnly(true).secure("https".equalsIgnoreCase(request.getURI().getScheme())).sameSite("Lax").build();
    }

    private static String encodeCookie(String cookieValue) {
        return new String(Base64.getEncoder().encode(cookieValue.getBytes()));
    }

    private static String decodeCookie(String encodedCookieValue) {
        return new String(Base64.getDecoder().decode(encodedCookieValue.getBytes()));
    }

    private static ServerWebExchangeMatcher createDefaultRequestMatcher() {
        ServerWebExchangeMatcher get = ServerWebExchangeMatchers.pathMatchers(HttpMethod.GET, "/**");
        NegatedServerWebExchangeMatcher notFavicon = new NegatedServerWebExchangeMatcher(ServerWebExchangeMatchers.pathMatchers("/favicon.*"));
        MediaTypeServerWebExchangeMatcher html = new MediaTypeServerWebExchangeMatcher(MediaType.TEXT_HTML);
        html.setIgnoredMediaTypes(Collections.singleton(MediaType.ALL));
        return new AndServerWebExchangeMatcher(get, notFavicon, html);
    }
}

