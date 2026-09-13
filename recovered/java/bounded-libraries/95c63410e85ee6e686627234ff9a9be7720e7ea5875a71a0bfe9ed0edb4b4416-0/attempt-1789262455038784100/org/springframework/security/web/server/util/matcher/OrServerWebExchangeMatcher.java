/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.util.matcher;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class OrServerWebExchangeMatcher
implements ServerWebExchangeMatcher {
    private static final Log logger = LogFactory.getLog(OrServerWebExchangeMatcher.class);
    private final List<ServerWebExchangeMatcher> matchers;

    public OrServerWebExchangeMatcher(List<ServerWebExchangeMatcher> matchers) {
        Assert.notEmpty(matchers, (String)"matchers cannot be empty");
        this.matchers = matchers;
    }

    public OrServerWebExchangeMatcher(ServerWebExchangeMatcher ... matchers) {
        this(Arrays.asList(matchers));
    }

    @Override
    public Mono<ServerWebExchangeMatcher.MatchResult> matches(ServerWebExchange exchange) {
        return Flux.fromIterable(this.matchers).doOnNext(matcher -> logger.debug((Object)LogMessage.format((String)"Trying to match using %s", (Object)matcher))).flatMap(matcher -> matcher.matches(exchange)).filter(ServerWebExchangeMatcher.MatchResult::isMatch).next().switchIfEmpty(ServerWebExchangeMatcher.MatchResult.notMatch()).doOnNext(matchResult -> logger.debug((Object)(matchResult.isMatch() ? "matched" : "No matches found")));
    }

    public String toString() {
        return "OrServerWebExchangeMatcher{matchers=" + this.matchers + '}';
    }
}

