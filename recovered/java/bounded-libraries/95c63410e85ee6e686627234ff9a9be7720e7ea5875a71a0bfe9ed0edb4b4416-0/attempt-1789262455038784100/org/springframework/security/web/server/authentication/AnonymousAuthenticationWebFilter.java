/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.authentication.AnonymousAuthenticationToken
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.GrantedAuthority
 *  org.springframework.security.core.authority.AuthorityUtils
 *  org.springframework.security.core.context.ReactiveSecurityContextHolder
 *  org.springframework.security.core.context.SecurityContextImpl
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.server.WebFilter
 *  org.springframework.web.server.WebFilterChain
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.authentication;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class AnonymousAuthenticationWebFilter
implements WebFilter {
    private static final Log logger = LogFactory.getLog(AnonymousAuthenticationWebFilter.class);
    private String key;
    private Object principal;
    private List<GrantedAuthority> authorities;

    public AnonymousAuthenticationWebFilter(String key) {
        this(key, "anonymousUser", AuthorityUtils.createAuthorityList((String[])new String[]{"ROLE_ANONYMOUS"}));
    }

    public AnonymousAuthenticationWebFilter(String key, Object principal, List<GrantedAuthority> authorities) {
        Assert.hasLength((String)key, (String)"key cannot be null or empty");
        Assert.notNull((Object)principal, (String)"Anonymous authentication principal must be set");
        Assert.notNull(authorities, (String)"Anonymous authorities must be set");
        this.key = key;
        this.principal = principal;
        this.authorities = authorities;
    }

    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext().switchIfEmpty(Mono.defer(() -> {
            Authentication authentication = this.createAuthentication(exchange);
            SecurityContextImpl securityContext = new SecurityContextImpl(authentication);
            logger.debug((Object)LogMessage.format((String)"Populated SecurityContext with anonymous token: '%s'", (Object)authentication));
            return chain.filter(exchange).subscriberContext(ReactiveSecurityContextHolder.withSecurityContext((Mono)Mono.just((Object)securityContext))).then(Mono.empty());
        })).flatMap(securityContext -> {
            logger.debug((Object)LogMessage.format((String)"SecurityContext contains anonymous token: '%s'", (Object)securityContext.getAuthentication()));
            return chain.filter(exchange);
        });
    }

    protected Authentication createAuthentication(ServerWebExchange exchange) {
        return new AnonymousAuthenticationToken(this.key, this.principal, this.authorities);
    }
}

