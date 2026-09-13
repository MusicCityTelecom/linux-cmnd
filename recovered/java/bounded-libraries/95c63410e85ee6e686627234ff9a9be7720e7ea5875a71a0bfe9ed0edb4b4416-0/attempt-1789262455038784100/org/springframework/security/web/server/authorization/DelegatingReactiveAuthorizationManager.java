/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.authorization.AuthorizationDecision
 *  org.springframework.security.authorization.ReactiveAuthorizationManager
 *  org.springframework.security.core.Authentication
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.authorization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcherEntry;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public final class DelegatingReactiveAuthorizationManager
implements ReactiveAuthorizationManager<ServerWebExchange> {
    private static final Log logger = LogFactory.getLog(DelegatingReactiveAuthorizationManager.class);
    private final List<ServerWebExchangeMatcherEntry<ReactiveAuthorizationManager<AuthorizationContext>>> mappings;

    private DelegatingReactiveAuthorizationManager(List<ServerWebExchangeMatcherEntry<ReactiveAuthorizationManager<AuthorizationContext>>> mappings) {
        this.mappings = mappings;
    }

    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, ServerWebExchange exchange) {
        return Flux.fromIterable(this.mappings).concatMap(mapping -> mapping.getMatcher().matches(exchange).filter(ServerWebExchangeMatcher.MatchResult::isMatch).map(ServerWebExchangeMatcher.MatchResult::getVariables).flatMap(variables -> {
            logger.debug((Object)LogMessage.of(() -> "Checking authorization on '" + exchange.getRequest().getPath().pathWithinApplication() + "' using " + mapping.getEntry()));
            return ((ReactiveAuthorizationManager)mapping.getEntry()).check(authentication, (Object)new AuthorizationContext(exchange, (Map<String, Object>)variables));
        })).next().defaultIfEmpty((Object)new AuthorizationDecision(false));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private final List<ServerWebExchangeMatcherEntry<ReactiveAuthorizationManager<AuthorizationContext>>> mappings = new ArrayList<ServerWebExchangeMatcherEntry<ReactiveAuthorizationManager<AuthorizationContext>>>();

        private Builder() {
        }

        public Builder add(ServerWebExchangeMatcherEntry<ReactiveAuthorizationManager<AuthorizationContext>> entry) {
            this.mappings.add(entry);
            return this;
        }

        public DelegatingReactiveAuthorizationManager build() {
            return new DelegatingReactiveAuthorizationManager(this.mappings);
        }
    }
}

