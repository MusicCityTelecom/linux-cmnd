/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.authentication.ReactiveAuthenticationManager
 *  org.springframework.security.authentication.ReactiveAuthenticationManagerResolver
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.AuthenticationException
 *  org.springframework.security.core.context.ReactiveSecurityContextHolder
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.security.core.context.SecurityContextImpl
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.server.WebFilter
 *  org.springframework.web.server.WebFilterChain
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.authentication;

import java.util.function.Function;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.HttpBasicServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.ServerHttpBasicAuthenticationConverter;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class AuthenticationWebFilter
implements WebFilter {
    private static final Log logger = LogFactory.getLog(AuthenticationWebFilter.class);
    private final ReactiveAuthenticationManagerResolver<ServerWebExchange> authenticationManagerResolver;
    private ServerAuthenticationSuccessHandler authenticationSuccessHandler = new WebFilterChainServerAuthenticationSuccessHandler();
    private ServerAuthenticationConverter authenticationConverter = new ServerHttpBasicAuthenticationConverter();
    private ServerAuthenticationFailureHandler authenticationFailureHandler = new ServerAuthenticationEntryPointFailureHandler(new HttpBasicServerAuthenticationEntryPoint());
    private ServerSecurityContextRepository securityContextRepository = NoOpServerSecurityContextRepository.getInstance();
    private ServerWebExchangeMatcher requiresAuthenticationMatcher = ServerWebExchangeMatchers.anyExchange();

    public AuthenticationWebFilter(ReactiveAuthenticationManager authenticationManager) {
        Assert.notNull((Object)authenticationManager, (String)"authenticationManager cannot be null");
        this.authenticationManagerResolver = request -> Mono.just((Object)authenticationManager);
    }

    public AuthenticationWebFilter(ReactiveAuthenticationManagerResolver<ServerWebExchange> authenticationManagerResolver) {
        Assert.notNull(authenticationManagerResolver, (String)"authenticationResolverManager cannot be null");
        this.authenticationManagerResolver = authenticationManagerResolver;
    }

    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return this.requiresAuthenticationMatcher.matches(exchange).filter(matchResult -> matchResult.isMatch()).flatMap(matchResult -> this.authenticationConverter.convert(exchange)).switchIfEmpty(chain.filter(exchange).then(Mono.empty())).flatMap(token -> this.authenticate(exchange, chain, (Authentication)token)).onErrorResume(AuthenticationException.class, ex -> this.authenticationFailureHandler.onAuthenticationFailure(new WebFilterExchange(exchange, chain), (AuthenticationException)((Object)ex)));
    }

    private Mono<Void> authenticate(ServerWebExchange exchange, WebFilterChain chain, Authentication token) {
        return this.authenticationManagerResolver.resolve((Object)exchange).flatMap(authenticationManager -> authenticationManager.authenticate(token)).switchIfEmpty(Mono.defer(() -> Mono.error((Throwable)new IllegalStateException("No provider found for " + token.getClass())))).flatMap(authentication -> this.onAuthenticationSuccess((Authentication)authentication, new WebFilterExchange(exchange, chain))).doOnError(AuthenticationException.class, ex -> logger.debug((Object)LogMessage.format((String)"Authentication failed: %s", (Object)ex.getMessage())));
    }

    protected Mono<Void> onAuthenticationSuccess(Authentication authentication, WebFilterExchange webFilterExchange) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        SecurityContextImpl securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(authentication);
        return this.securityContextRepository.save(exchange, (SecurityContext)securityContext).then(this.authenticationSuccessHandler.onAuthenticationSuccess(webFilterExchange, authentication)).subscriberContext(ReactiveSecurityContextHolder.withSecurityContext((Mono)Mono.just((Object)securityContext)));
    }

    public void setSecurityContextRepository(ServerSecurityContextRepository securityContextRepository) {
        Assert.notNull((Object)securityContextRepository, (String)"securityContextRepository cannot be null");
        this.securityContextRepository = securityContextRepository;
    }

    public void setAuthenticationSuccessHandler(ServerAuthenticationSuccessHandler authenticationSuccessHandler) {
        Assert.notNull((Object)authenticationSuccessHandler, (String)"authenticationSuccessHandler cannot be null");
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Deprecated
    public void setAuthenticationConverter(Function<ServerWebExchange, Mono<Authentication>> authenticationConverter) {
        Assert.notNull(authenticationConverter, (String)"authenticationConverter cannot be null");
        this.setServerAuthenticationConverter(authenticationConverter::apply);
    }

    public void setServerAuthenticationConverter(ServerAuthenticationConverter authenticationConverter) {
        Assert.notNull((Object)authenticationConverter, (String)"authenticationConverter cannot be null");
        this.authenticationConverter = authenticationConverter;
    }

    public void setAuthenticationFailureHandler(ServerAuthenticationFailureHandler authenticationFailureHandler) {
        Assert.notNull((Object)authenticationFailureHandler, (String)"authenticationFailureHandler cannot be null");
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public void setRequiresAuthenticationMatcher(ServerWebExchangeMatcher requiresAuthenticationMatcher) {
        Assert.notNull((Object)requiresAuthenticationMatcher, (String)"requiresAuthenticationMatcher cannot be null");
        this.requiresAuthenticationMatcher = requiresAuthenticationMatcher;
    }
}

