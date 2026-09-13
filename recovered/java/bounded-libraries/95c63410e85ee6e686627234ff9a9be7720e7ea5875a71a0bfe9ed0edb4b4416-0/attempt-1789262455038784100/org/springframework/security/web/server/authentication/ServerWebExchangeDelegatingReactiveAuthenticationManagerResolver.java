/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.authentication.AuthenticationServiceException
 *  org.springframework.security.authentication.ReactiveAuthenticationManager
 *  org.springframework.security.authentication.ReactiveAuthenticationManagerResolver
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.authentication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerResolver;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcherEntry;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public final class ServerWebExchangeDelegatingReactiveAuthenticationManagerResolver
implements ReactiveAuthenticationManagerResolver<ServerWebExchange> {
    private final List<ServerWebExchangeMatcherEntry<ReactiveAuthenticationManager>> authenticationManagers;
    private ReactiveAuthenticationManager defaultAuthenticationManager = authentication -> Mono.error((Throwable)new AuthenticationServiceException("Cannot authenticate " + authentication));

    ServerWebExchangeDelegatingReactiveAuthenticationManagerResolver(ServerWebExchangeMatcherEntry<ReactiveAuthenticationManager> ... managers) {
        this(Arrays.asList(managers));
    }

    ServerWebExchangeDelegatingReactiveAuthenticationManagerResolver(List<ServerWebExchangeMatcherEntry<ReactiveAuthenticationManager>> managers) {
        Assert.notNull(managers, (String)"entries cannot be null");
        this.authenticationManagers = managers;
    }

    public Mono<ReactiveAuthenticationManager> resolve(ServerWebExchange exchange) {
        return Flux.fromIterable(this.authenticationManagers).filterWhen(entry -> this.isMatch(exchange, (ServerWebExchangeMatcherEntry<ReactiveAuthenticationManager>)entry)).next().map(ServerWebExchangeMatcherEntry::getEntry).defaultIfEmpty((Object)this.defaultAuthenticationManager);
    }

    public void setDefaultAuthenticationManager(ReactiveAuthenticationManager defaultAuthenticationManager) {
        Assert.notNull((Object)defaultAuthenticationManager, (String)"defaultAuthenticationManager cannot be null");
        this.defaultAuthenticationManager = defaultAuthenticationManager;
    }

    public static Builder builder() {
        return new Builder();
    }

    private Mono<Boolean> isMatch(ServerWebExchange exchange, ServerWebExchangeMatcherEntry<ReactiveAuthenticationManager> entry) {
        ServerWebExchangeMatcher matcher = entry.getMatcher();
        return matcher.matches(exchange).map(ServerWebExchangeMatcher.MatchResult::isMatch);
    }

    public static final class Builder {
        private final List<ServerWebExchangeMatcherEntry<ReactiveAuthenticationManager>> entries = new ArrayList<ServerWebExchangeMatcherEntry<ReactiveAuthenticationManager>>();

        private Builder() {
        }

        public Builder add(ServerWebExchangeMatcher matcher, ReactiveAuthenticationManager manager) {
            Assert.notNull((Object)matcher, (String)"matcher cannot be null");
            Assert.notNull((Object)manager, (String)"manager cannot be null");
            this.entries.add(new ServerWebExchangeMatcherEntry<ReactiveAuthenticationManager>(matcher, manager));
            return this;
        }

        public ServerWebExchangeDelegatingReactiveAuthenticationManagerResolver build() {
            return new ServerWebExchangeDelegatingReactiveAuthenticationManagerResolver(this.entries);
        }
    }
}

