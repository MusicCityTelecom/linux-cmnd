/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.util.matcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class NegatedServerWebExchangeMatcher
implements ServerWebExchangeMatcher {
    private static final Log logger = LogFactory.getLog(NegatedServerWebExchangeMatcher.class);
    private final ServerWebExchangeMatcher matcher;

    public NegatedServerWebExchangeMatcher(ServerWebExchangeMatcher matcher) {
        Assert.notNull((Object)matcher, (String)"matcher cannot be null");
        this.matcher = matcher;
    }

    @Override
    public Mono<ServerWebExchangeMatcher.MatchResult> matches(ServerWebExchange exchange) {
        return this.matcher.matches(exchange).flatMap(this::negate).doOnNext(matchResult -> logger.debug((Object)LogMessage.format((String)"matches = %s", (Object)matchResult.isMatch())));
    }

    private Mono<ServerWebExchangeMatcher.MatchResult> negate(ServerWebExchangeMatcher.MatchResult matchResult) {
        return matchResult.isMatch() ? ServerWebExchangeMatcher.MatchResult.notMatch() : ServerWebExchangeMatcher.MatchResult.match();
    }

    public String toString() {
        return "NegatedServerWebExchangeMatcher{matcher=" + this.matcher + '}';
    }
}

