/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.MessageSource
 *  org.springframework.context.MessageSourceAware
 *  org.springframework.http.HttpStatus
 *  org.springframework.security.access.AccessDeniedException
 *  org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
 *  org.springframework.security.authentication.AuthenticationTrustResolver
 *  org.springframework.security.authentication.AuthenticationTrustResolverImpl
 *  org.springframework.security.authentication.InsufficientAuthenticationException
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.AuthenticationException
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.server.WebFilter
 *  org.springframework.web.server.WebFilterChain
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.authorization;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.HttpBasicServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.HttpStatusServerAccessDeniedHandler;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class ExceptionTranslationWebFilter
implements WebFilter,
MessageSourceAware {
    private ServerAuthenticationEntryPoint authenticationEntryPoint = new HttpBasicServerAuthenticationEntryPoint();
    private ServerAccessDeniedHandler accessDeniedHandler = new HttpStatusServerAccessDeniedHandler(HttpStatus.FORBIDDEN);
    private AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange).onErrorResume(AccessDeniedException.class, denied -> exchange.getPrincipal().filter(principal -> !(principal instanceof Authentication) || principal instanceof Authentication && !this.authenticationTrustResolver.isAnonymous((Authentication)principal)).switchIfEmpty(this.commenceAuthentication(exchange, (AuthenticationException)new InsufficientAuthenticationException("Full authentication is required to access this resource"))).flatMap(principal -> this.accessDeniedHandler.handle(exchange, (AccessDeniedException)((Object)denied))).then());
    }

    public void setAccessDeniedHandler(ServerAccessDeniedHandler accessDeniedHandler) {
        Assert.notNull((Object)accessDeniedHandler, (String)"accessDeniedHandler cannot be null");
        this.accessDeniedHandler = accessDeniedHandler;
    }

    public void setAuthenticationEntryPoint(ServerAuthenticationEntryPoint authenticationEntryPoint) {
        Assert.notNull((Object)authenticationEntryPoint, (String)"authenticationEntryPoint cannot be null");
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    public void setAuthenticationTrustResolver(AuthenticationTrustResolver authenticationTrustResolver) {
        Assert.notNull((Object)authenticationTrustResolver, (String)"authenticationTrustResolver must not be null");
        this.authenticationTrustResolver = authenticationTrustResolver;
    }

    @Deprecated
    public void setMessageSource(MessageSource messageSource) {
    }

    private <T> Mono<T> commenceAuthentication(ServerWebExchange exchange, AuthenticationException denied) {
        return this.authenticationEntryPoint.commence(exchange, (AuthenticationException)((Object)new AuthenticationCredentialsNotFoundException("Not Authenticated", (Throwable)denied))).then(Mono.empty());
    }
}

