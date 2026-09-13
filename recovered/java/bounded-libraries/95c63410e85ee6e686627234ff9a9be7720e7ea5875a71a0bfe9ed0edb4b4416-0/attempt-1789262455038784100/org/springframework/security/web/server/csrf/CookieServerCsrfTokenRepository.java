/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.HttpCookie
 *  org.springframework.http.ResponseCookie
 *  org.springframework.http.server.reactive.ServerHttpRequest
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 *  reactor.core.scheduler.Schedulers
 */
package org.springframework.security.web.server.csrf;

import java.util.UUID;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.security.web.server.csrf.DefaultCsrfToken;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRepository;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public final class CookieServerCsrfTokenRepository
implements ServerCsrfTokenRepository {
    static final String DEFAULT_CSRF_COOKIE_NAME = "XSRF-TOKEN";
    static final String DEFAULT_CSRF_PARAMETER_NAME = "_csrf";
    static final String DEFAULT_CSRF_HEADER_NAME = "X-XSRF-TOKEN";
    private String parameterName = "_csrf";
    private String headerName = "X-XSRF-TOKEN";
    private String cookiePath;
    private String cookieDomain;
    private String cookieName = "XSRF-TOKEN";
    private boolean cookieHttpOnly = true;
    private Boolean secure;

    public static CookieServerCsrfTokenRepository withHttpOnlyFalse() {
        CookieServerCsrfTokenRepository result = new CookieServerCsrfTokenRepository();
        result.setCookieHttpOnly(false);
        return result;
    }

    @Override
    public Mono<CsrfToken> generateToken(ServerWebExchange exchange) {
        return Mono.fromCallable(this::createCsrfToken).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Void> saveToken(ServerWebExchange exchange, CsrfToken token) {
        return Mono.fromRunnable(() -> {
            String tokenValue = token != null ? token.getToken() : "";
            ResponseCookie cookie = ResponseCookie.from((String)this.cookieName, (String)tokenValue).domain(this.cookieDomain).httpOnly(this.cookieHttpOnly).maxAge(!tokenValue.isEmpty() ? -1L : 0L).path(this.cookiePath != null ? this.cookiePath : this.getRequestContext(exchange.getRequest())).secure(this.secure != null ? this.secure : exchange.getRequest().getSslInfo() != null).build();
            exchange.getResponse().addCookie(cookie);
        });
    }

    @Override
    public Mono<CsrfToken> loadToken(ServerWebExchange exchange) {
        return Mono.fromCallable(() -> {
            HttpCookie csrfCookie = (HttpCookie)exchange.getRequest().getCookies().getFirst((Object)this.cookieName);
            if (csrfCookie == null || !StringUtils.hasText((String)csrfCookie.getValue())) {
                return null;
            }
            return this.createCsrfToken(csrfCookie.getValue());
        });
    }

    public void setCookieHttpOnly(boolean cookieHttpOnly) {
        this.cookieHttpOnly = cookieHttpOnly;
    }

    public void setCookieName(String cookieName) {
        Assert.hasLength((String)cookieName, (String)"cookieName can't be null");
        this.cookieName = cookieName;
    }

    public void setParameterName(String parameterName) {
        Assert.hasLength((String)parameterName, (String)"parameterName can't be null");
        this.parameterName = parameterName;
    }

    public void setHeaderName(String headerName) {
        Assert.hasLength((String)headerName, (String)"headerName can't be null");
        this.headerName = headerName;
    }

    public void setCookiePath(String cookiePath) {
        this.cookiePath = cookiePath;
    }

    public void setCookieDomain(String cookieDomain) {
        this.cookieDomain = cookieDomain;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    private CsrfToken createCsrfToken() {
        return this.createCsrfToken(this.createNewToken());
    }

    private CsrfToken createCsrfToken(String tokenValue) {
        return new DefaultCsrfToken(this.headerName, this.parameterName, tokenValue);
    }

    private String createNewToken() {
        return UUID.randomUUID().toString();
    }

    private String getRequestContext(ServerHttpRequest request) {
        String contextPath = request.getPath().contextPath().value();
        return StringUtils.hasLength((String)contextPath) ? contextPath : "/";
    }
}

