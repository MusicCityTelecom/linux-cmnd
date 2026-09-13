/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.server.PathContainer
 *  org.springframework.http.server.reactive.ServerHttpRequest
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.util.pattern.PathPattern
 *  org.springframework.web.util.pattern.PathPatternParser
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.util.matcher;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

public final class PathPatternParserServerWebExchangeMatcher
implements ServerWebExchangeMatcher {
    private static final Log logger = LogFactory.getLog(PathPatternParserServerWebExchangeMatcher.class);
    private static final PathPatternParser DEFAULT_PATTERN_PARSER = new PathPatternParser();
    private final PathPattern pattern;
    private final HttpMethod method;

    public PathPatternParserServerWebExchangeMatcher(PathPattern pattern) {
        this(pattern, null);
    }

    public PathPatternParserServerWebExchangeMatcher(PathPattern pattern, HttpMethod method) {
        Assert.notNull((Object)pattern, (String)"pattern cannot be null");
        this.pattern = pattern;
        this.method = method;
    }

    public PathPatternParserServerWebExchangeMatcher(String pattern, HttpMethod method) {
        Assert.notNull((Object)pattern, (String)"pattern cannot be null");
        this.pattern = DEFAULT_PATTERN_PARSER.parse(pattern);
        this.method = method;
    }

    public PathPatternParserServerWebExchangeMatcher(String pattern) {
        this(pattern, null);
    }

    @Override
    public Mono<ServerWebExchangeMatcher.MatchResult> matches(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        PathContainer path = request.getPath().pathWithinApplication();
        if (this.method != null && !this.method.equals((Object)request.getMethod())) {
            return ServerWebExchangeMatcher.MatchResult.notMatch().doOnNext(result -> {
                if (logger.isDebugEnabled()) {
                    logger.debug((Object)("Request '" + request.getMethod() + " " + path + "' doesn't match '" + this.method + " " + this.pattern.getPatternString() + "'"));
                }
            });
        }
        boolean match = this.pattern.matches(path);
        if (!match) {
            return ServerWebExchangeMatcher.MatchResult.notMatch().doOnNext(result -> {
                if (logger.isDebugEnabled()) {
                    logger.debug((Object)("Request '" + request.getMethod() + " " + path + "' doesn't match '" + this.method + " " + this.pattern.getPatternString() + "'"));
                }
            });
        }
        Map pathVariables = this.pattern.matchAndExtract(path).getUriVariables();
        HashMap<String, Object> variables = new HashMap<String, Object>(pathVariables);
        if (logger.isDebugEnabled()) {
            logger.debug((Object)("Checking match of request : '" + path + "'; against '" + this.pattern.getPatternString() + "'"));
        }
        return ServerWebExchangeMatcher.MatchResult.match(variables);
    }

    public String toString() {
        return "PathMatcherServerWebExchangeMatcher{pattern='" + this.pattern + '\'' + ", method=" + this.method + '}';
    }
}

