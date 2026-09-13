/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.HttpMethod
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.util.matcher;

import java.util.ArrayList;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public abstract class ServerWebExchangeMatchers {
    private ServerWebExchangeMatchers() {
    }

    public static ServerWebExchangeMatcher pathMatchers(HttpMethod method, String ... patterns) {
        ArrayList<ServerWebExchangeMatcher> matchers = new ArrayList<ServerWebExchangeMatcher>(patterns.length);
        for (String pattern : patterns) {
            matchers.add(new PathPatternParserServerWebExchangeMatcher(pattern, method));
        }
        return new OrServerWebExchangeMatcher(matchers);
    }

    public static ServerWebExchangeMatcher pathMatchers(String ... patterns) {
        return ServerWebExchangeMatchers.pathMatchers(null, patterns);
    }

    public static ServerWebExchangeMatcher matchers(ServerWebExchangeMatcher ... matchers) {
        return new OrServerWebExchangeMatcher(matchers);
    }

    public static ServerWebExchangeMatcher anyExchange() {
        return new ServerWebExchangeMatcher(){

            @Override
            public Mono<ServerWebExchangeMatcher.MatchResult> matches(ServerWebExchange exchange) {
                return ServerWebExchangeMatcher.MatchResult.match();
            }
        };
    }
}

