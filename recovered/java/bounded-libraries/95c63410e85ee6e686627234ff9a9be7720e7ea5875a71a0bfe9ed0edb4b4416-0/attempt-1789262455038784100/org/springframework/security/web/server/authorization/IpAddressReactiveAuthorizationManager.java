/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.authorization.AuthorizationDecision
 *  org.springframework.security.authorization.ReactiveAuthorizationManager
 *  org.springframework.security.core.Authentication
 *  org.springframework.util.Assert
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.authorization;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.util.matcher.IpAddressServerWebExchangeMatcher;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

public final class IpAddressReactiveAuthorizationManager
implements ReactiveAuthorizationManager<AuthorizationContext> {
    private final IpAddressServerWebExchangeMatcher ipAddressExchangeMatcher;

    IpAddressReactiveAuthorizationManager(String ipAddress) {
        this.ipAddressExchangeMatcher = new IpAddressServerWebExchangeMatcher(ipAddress);
    }

    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext context) {
        return Mono.just((Object)context.getExchange()).flatMap(this.ipAddressExchangeMatcher::matches).map(matchResult -> new AuthorizationDecision(matchResult.isMatch()));
    }

    public static IpAddressReactiveAuthorizationManager hasIpAddress(String ipAddress) {
        Assert.notNull((Object)ipAddress, (String)"This IP address is required; it must not be null");
        return new IpAddressReactiveAuthorizationManager(ipAddress);
    }
}

