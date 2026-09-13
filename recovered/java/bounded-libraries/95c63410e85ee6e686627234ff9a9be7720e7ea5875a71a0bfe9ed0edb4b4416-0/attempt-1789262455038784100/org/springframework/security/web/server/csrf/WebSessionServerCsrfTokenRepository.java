/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 *  reactor.core.scheduler.Schedulers
 */
package org.springframework.security.web.server.csrf;

import java.util.Map;
import java.util.UUID;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.security.web.server.csrf.DefaultCsrfToken;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRepository;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class WebSessionServerCsrfTokenRepository
implements ServerCsrfTokenRepository {
    private static final String DEFAULT_CSRF_PARAMETER_NAME = "_csrf";
    private static final String DEFAULT_CSRF_HEADER_NAME = "X-CSRF-TOKEN";
    private static final String DEFAULT_CSRF_TOKEN_ATTR_NAME = WebSessionServerCsrfTokenRepository.class.getName().concat(".CSRF_TOKEN");
    private String parameterName = "_csrf";
    private String headerName = "X-CSRF-TOKEN";
    private String sessionAttributeName = DEFAULT_CSRF_TOKEN_ATTR_NAME;

    @Override
    public Mono<CsrfToken> generateToken(ServerWebExchange exchange) {
        return Mono.fromCallable(() -> this.createCsrfToken()).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Void> saveToken(ServerWebExchange exchange, CsrfToken token) {
        return exchange.getSession().doOnNext(session -> this.putToken(session.getAttributes(), token)).flatMap(session -> session.changeSessionId());
    }

    private void putToken(Map<String, Object> attributes, CsrfToken token) {
        if (token == null) {
            attributes.remove(this.sessionAttributeName);
        } else {
            attributes.put(this.sessionAttributeName, token);
        }
    }

    @Override
    public Mono<CsrfToken> loadToken(ServerWebExchange exchange) {
        return exchange.getSession().filter(session -> session.getAttributes().containsKey(this.sessionAttributeName)).map(session -> (CsrfToken)session.getAttribute(this.sessionAttributeName));
    }

    public void setParameterName(String parameterName) {
        Assert.hasLength((String)parameterName, (String)"parameterName cannot be null or empty");
        this.parameterName = parameterName;
    }

    public void setHeaderName(String headerName) {
        Assert.hasLength((String)headerName, (String)"headerName cannot be null or empty");
        this.headerName = headerName;
    }

    public void setSessionAttributeName(String sessionAttributeName) {
        Assert.hasLength((String)sessionAttributeName, (String)"sessionAttributename cannot be null or empty");
        this.sessionAttributeName = sessionAttributeName;
    }

    private CsrfToken createCsrfToken() {
        return new DefaultCsrfToken(this.headerName, this.parameterName, this.createNewToken());
    }

    private String createNewToken() {
        return UUID.randomUUID().toString();
    }
}

