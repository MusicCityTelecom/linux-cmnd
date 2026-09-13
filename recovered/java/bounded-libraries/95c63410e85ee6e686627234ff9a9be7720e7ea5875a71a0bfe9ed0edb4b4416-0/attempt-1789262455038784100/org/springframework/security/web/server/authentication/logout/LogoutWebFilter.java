/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.http.HttpMethod
 *  org.springframework.security.authentication.AnonymousAuthenticationToken
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.authority.AuthorityUtils
 *  org.springframework.security.core.context.ReactiveSecurityContextHolder
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.server.WebFilter
 *  org.springframework.web.server.WebFilterChain
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.authentication.logout;

import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.SecurityContextServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class LogoutWebFilter
implements WebFilter {
    private static final Log logger = LogFactory.getLog(LogoutWebFilter.class);
    private AnonymousAuthenticationToken anonymousAuthenticationToken = new AnonymousAuthenticationToken("key", (Object)"anonymous", (Collection)AuthorityUtils.createAuthorityList((String[])new String[]{"ROLE_ANONYMOUS"}));
    private ServerLogoutHandler logoutHandler = new SecurityContextServerLogoutHandler();
    private ServerLogoutSuccessHandler logoutSuccessHandler = new RedirectServerLogoutSuccessHandler();
    private ServerWebExchangeMatcher requiresLogout = ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/logout");

    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return this.requiresLogout.matches(exchange).filter(result -> result.isMatch()).switchIfEmpty(chain.filter(exchange).then(Mono.empty())).map(result -> exchange).flatMap(this::flatMapAuthentication).flatMap(authentication -> {
            WebFilterExchange webFilterExchange = new WebFilterExchange(exchange, chain);
            return this.logout(webFilterExchange, (Authentication)authentication);
        });
    }

    private Mono<Authentication> flatMapAuthentication(ServerWebExchange exchange) {
        return exchange.getPrincipal().cast(Authentication.class).defaultIfEmpty((Object)this.anonymousAuthenticationToken);
    }

    private Mono<Void> logout(WebFilterExchange webFilterExchange, Authentication authentication) {
        logger.debug((Object)LogMessage.format((String)"Logging out user '%s' and transferring to logout destination", (Object)authentication));
        return this.logoutHandler.logout(webFilterExchange, authentication).then(this.logoutSuccessHandler.onLogoutSuccess(webFilterExchange, authentication)).subscriberContext(ReactiveSecurityContextHolder.clearContext());
    }

    public void setLogoutSuccessHandler(ServerLogoutSuccessHandler logoutSuccessHandler) {
        Assert.notNull((Object)logoutSuccessHandler, (String)"logoutSuccessHandler cannot be null");
        this.logoutSuccessHandler = logoutSuccessHandler;
    }

    public void setLogoutHandler(ServerLogoutHandler logoutHandler) {
        Assert.notNull((Object)logoutHandler, (String)"logoutHandler must not be null");
        this.logoutHandler = logoutHandler;
    }

    public void setRequiresLogoutMatcher(ServerWebExchangeMatcher requiresLogoutMatcher) {
        Assert.notNull((Object)requiresLogoutMatcher, (String)"requiresLogoutMatcher must not be null");
        this.requiresLogout = requiresLogoutMatcher;
    }
}

