/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher
 *  org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher
 *  org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher
 *  org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher$MatchResult
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.boot.autoconfigure.security.reactive;

import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.boot.autoconfigure.security.StaticResourceLocation;
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public final class StaticResourceRequest {
    static final StaticResourceRequest INSTANCE = new StaticResourceRequest();

    private StaticResourceRequest() {
    }

    public StaticResourceServerWebExchange atCommonLocations() {
        return this.at(EnumSet.allOf(StaticResourceLocation.class));
    }

    public StaticResourceServerWebExchange at(StaticResourceLocation first, StaticResourceLocation ... rest) {
        return this.at(EnumSet.of(first, rest));
    }

    public StaticResourceServerWebExchange at(Set<StaticResourceLocation> locations) {
        Assert.notNull(locations, (String)"Locations must not be null");
        return new StaticResourceServerWebExchange(new LinkedHashSet<StaticResourceLocation>(locations));
    }

    public static final class StaticResourceServerWebExchange
    implements ServerWebExchangeMatcher {
        private final Set<StaticResourceLocation> locations;

        private StaticResourceServerWebExchange(Set<StaticResourceLocation> locations) {
            this.locations = locations;
        }

        public StaticResourceServerWebExchange excluding(StaticResourceLocation first, StaticResourceLocation ... rest) {
            return this.excluding(EnumSet.of(first, rest));
        }

        public StaticResourceServerWebExchange excluding(Set<StaticResourceLocation> locations) {
            Assert.notNull(locations, (String)"Locations must not be null");
            LinkedHashSet<StaticResourceLocation> subset = new LinkedHashSet<StaticResourceLocation>(this.locations);
            subset.removeAll(locations);
            return new StaticResourceServerWebExchange(subset);
        }

        private List<ServerWebExchangeMatcher> getDelegateMatchers() {
            return this.getPatterns().map(PathPatternParserServerWebExchangeMatcher::new).collect(Collectors.toList());
        }

        private Stream<String> getPatterns() {
            return this.locations.stream().flatMap(StaticResourceLocation::getPatterns);
        }

        public Mono<ServerWebExchangeMatcher.MatchResult> matches(ServerWebExchange exchange) {
            OrServerWebExchangeMatcher matcher = new OrServerWebExchangeMatcher(this.getDelegateMatchers());
            return matcher.matches(exchange);
        }
    }
}

