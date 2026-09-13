/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.MediaType
 *  org.springframework.http.codec.multipart.FormFieldPart
 *  org.springframework.http.codec.multipart.Part
 *  org.springframework.http.server.reactive.ServerHttpRequest
 *  org.springframework.security.access.AccessDeniedException
 *  org.springframework.security.crypto.codec.Utf8
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.server.WebFilter
 *  org.springframework.web.server.WebFilterChain
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.csrf;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.security.web.server.authorization.HttpStatusServerAccessDeniedHandler;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.security.web.server.csrf.CsrfException;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.WebSessionServerCsrfTokenRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class CsrfWebFilter
implements WebFilter {
    public static final ServerWebExchangeMatcher DEFAULT_CSRF_MATCHER = new DefaultRequireCsrfProtectionMatcher();
    private static final String SHOULD_NOT_FILTER = "SHOULD_NOT_FILTER" + CsrfWebFilter.class.getName();
    private ServerWebExchangeMatcher requireCsrfProtectionMatcher = DEFAULT_CSRF_MATCHER;
    private ServerCsrfTokenRepository csrfTokenRepository = new WebSessionServerCsrfTokenRepository();
    private ServerAccessDeniedHandler accessDeniedHandler = new HttpStatusServerAccessDeniedHandler(HttpStatus.FORBIDDEN);
    private boolean isTokenFromMultipartDataEnabled;

    public void setAccessDeniedHandler(ServerAccessDeniedHandler accessDeniedHandler) {
        Assert.notNull((Object)accessDeniedHandler, (String)"accessDeniedHandler");
        this.accessDeniedHandler = accessDeniedHandler;
    }

    public void setCsrfTokenRepository(ServerCsrfTokenRepository csrfTokenRepository) {
        Assert.notNull((Object)csrfTokenRepository, (String)"csrfTokenRepository cannot be null");
        this.csrfTokenRepository = csrfTokenRepository;
    }

    public void setRequireCsrfProtectionMatcher(ServerWebExchangeMatcher requireCsrfProtectionMatcher) {
        Assert.notNull((Object)requireCsrfProtectionMatcher, (String)"requireCsrfProtectionMatcher cannot be null");
        this.requireCsrfProtectionMatcher = requireCsrfProtectionMatcher;
    }

    public void setTokenFromMultipartDataEnabled(boolean tokenFromMultipartDataEnabled) {
        this.isTokenFromMultipartDataEnabled = tokenFromMultipartDataEnabled;
    }

    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (Boolean.TRUE.equals(exchange.getAttribute(SHOULD_NOT_FILTER))) {
            return chain.filter(exchange).then(Mono.empty());
        }
        return this.requireCsrfProtectionMatcher.matches(exchange).filter(ServerWebExchangeMatcher.MatchResult::isMatch).filter(matchResult -> !exchange.getAttributes().containsKey(CsrfToken.class.getName())).flatMap(m -> this.validateToken(exchange)).flatMap(m -> this.continueFilterChain(exchange, chain)).switchIfEmpty(this.continueFilterChain(exchange, chain).then(Mono.empty())).onErrorResume(CsrfException.class, ex -> this.accessDeniedHandler.handle(exchange, (AccessDeniedException)((Object)ex)));
    }

    public static void skipExchange(ServerWebExchange exchange) {
        exchange.getAttributes().put(SHOULD_NOT_FILTER, Boolean.TRUE);
    }

    private Mono<Void> validateToken(ServerWebExchange exchange) {
        return this.csrfTokenRepository.loadToken(exchange).switchIfEmpty(Mono.defer(() -> Mono.error((Throwable)((Object)new CsrfException("An expected CSRF token cannot be found"))))).filterWhen(expected -> this.containsValidCsrfToken(exchange, (CsrfToken)expected)).switchIfEmpty(Mono.defer(() -> Mono.error((Throwable)((Object)new CsrfException("Invalid CSRF Token"))))).then();
    }

    private Mono<Boolean> containsValidCsrfToken(ServerWebExchange exchange, CsrfToken expected) {
        return exchange.getFormData().flatMap(data -> Mono.justOrEmpty((Object)((String)data.getFirst((Object)expected.getParameterName())))).switchIfEmpty(Mono.justOrEmpty((Object)exchange.getRequest().getHeaders().getFirst(expected.getHeaderName()))).switchIfEmpty(this.tokenFromMultipartData(exchange, expected)).map(actual -> CsrfWebFilter.equalsConstantTime(actual, expected.getToken()));
    }

    private Mono<String> tokenFromMultipartData(ServerWebExchange exchange, CsrfToken expected) {
        if (!this.isTokenFromMultipartDataEnabled) {
            return Mono.empty();
        }
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        MediaType contentType = headers.getContentType();
        if (!MediaType.MULTIPART_FORM_DATA.isCompatibleWith(contentType)) {
            return Mono.empty();
        }
        return exchange.getMultipartData().map(d -> (Part)d.getFirst((Object)expected.getParameterName())).cast(FormFieldPart.class).map(FormFieldPart::value);
    }

    private Mono<Void> continueFilterChain(ServerWebExchange exchange, WebFilterChain chain) {
        return Mono.defer(() -> {
            Mono<CsrfToken> csrfToken = this.csrfToken(exchange);
            exchange.getAttributes().put(CsrfToken.class.getName(), csrfToken);
            return chain.filter(exchange);
        });
    }

    private Mono<CsrfToken> csrfToken(ServerWebExchange exchange) {
        return this.csrfTokenRepository.loadToken(exchange).switchIfEmpty(this.generateToken(exchange));
    }

    private static boolean equalsConstantTime(String expected, String actual) {
        if (expected == actual) {
            return true;
        }
        if (expected == null || actual == null) {
            return false;
        }
        byte[] expectedBytes = Utf8.encode((CharSequence)expected);
        byte[] actualBytes = Utf8.encode((CharSequence)actual);
        return MessageDigest.isEqual(expectedBytes, actualBytes);
    }

    private Mono<CsrfToken> generateToken(ServerWebExchange exchange) {
        return this.csrfTokenRepository.generateToken(exchange).delayUntil(token -> this.csrfTokenRepository.saveToken(exchange, (CsrfToken)token)).cache();
    }

    private static class DefaultRequireCsrfProtectionMatcher
    implements ServerWebExchangeMatcher {
        private static final Set<HttpMethod> ALLOWED_METHODS = new HashSet<HttpMethod>(Arrays.asList(HttpMethod.GET, HttpMethod.HEAD, HttpMethod.TRACE, HttpMethod.OPTIONS));

        private DefaultRequireCsrfProtectionMatcher() {
        }

        @Override
        public Mono<ServerWebExchangeMatcher.MatchResult> matches(ServerWebExchange exchange) {
            return Mono.just((Object)exchange.getRequest()).flatMap(r -> Mono.justOrEmpty((Object)r.getMethod())).filter(ALLOWED_METHODS::contains).flatMap(m -> ServerWebExchangeMatcher.MatchResult.notMatch()).switchIfEmpty(ServerWebExchangeMatcher.MatchResult.match());
        }
    }
}

