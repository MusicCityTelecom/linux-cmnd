/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.util.matcher;

import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public final class IpAddressServerWebExchangeMatcher
implements ServerWebExchangeMatcher {
    private final IpAddressMatcher ipAddressMatcher;

    public IpAddressServerWebExchangeMatcher(String ipAddress) {
        Assert.hasText((String)ipAddress, (String)"IP address cannot be empty");
        this.ipAddressMatcher = new IpAddressMatcher(ipAddress);
    }

    @Override
    public Mono<ServerWebExchangeMatcher.MatchResult> matches(ServerWebExchange exchange) {
        return Mono.justOrEmpty((Object)exchange.getRequest().getRemoteAddress()).map(remoteAddress -> remoteAddress.getAddress().getHostAddress()).map(this.ipAddressMatcher::matches).flatMap(matches -> matches != false ? ServerWebExchangeMatcher.MatchResult.match() : ServerWebExchangeMatcher.MatchResult.notMatch()).switchIfEmpty(ServerWebExchangeMatcher.MatchResult.notMatch());
    }

    public String toString() {
        return "IpAddressServerWebExchangeMatcher{ipAddressMatcher=" + this.ipAddressMatcher + '}';
    }
}

