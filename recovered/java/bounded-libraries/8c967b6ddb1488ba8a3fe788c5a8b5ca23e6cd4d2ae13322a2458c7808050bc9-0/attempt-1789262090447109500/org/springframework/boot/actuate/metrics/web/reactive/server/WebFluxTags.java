/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.Tag
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.server.reactive.ServerHttpResponse
 *  org.springframework.util.StringUtils
 *  org.springframework.web.reactive.HandlerMapping
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.util.pattern.PathPattern
 */
package org.springframework.boot.actuate.metrics.web.reactive.server;

import io.micrometer.core.instrument.Tag;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import org.springframework.boot.actuate.metrics.http.Outcome;
import org.springframework.boot.actuate.metrics.web.reactive.server.CancelledServerWebExchangeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPattern;

public final class WebFluxTags {
    private static final Tag URI_NOT_FOUND = Tag.of((String)"uri", (String)"NOT_FOUND");
    private static final Tag URI_REDIRECTION = Tag.of((String)"uri", (String)"REDIRECTION");
    private static final Tag URI_ROOT = Tag.of((String)"uri", (String)"root");
    private static final Tag URI_UNKNOWN = Tag.of((String)"uri", (String)"UNKNOWN");
    private static final Tag EXCEPTION_NONE = Tag.of((String)"exception", (String)"None");
    private static final Pattern FORWARD_SLASHES_PATTERN = Pattern.compile("//+");
    private static final Set<String> DISCONNECTED_CLIENT_EXCEPTIONS = new HashSet<String>(Arrays.asList("AbortedException", "ClientAbortException", "EOFException", "EofException"));

    private WebFluxTags() {
    }

    public static Tag method(ServerWebExchange exchange) {
        return Tag.of((String)"method", (String)exchange.getRequest().getMethodValue());
    }

    public static Tag status(ServerWebExchange exchange) {
        HttpStatus status = exchange.getResponse().getStatusCode();
        if (status == null) {
            status = HttpStatus.OK;
        }
        return Tag.of((String)"status", (String)String.valueOf(status.value()));
    }

    public static Tag uri(ServerWebExchange exchange) {
        return WebFluxTags.uri(exchange, false);
    }

    public static Tag uri(ServerWebExchange exchange, boolean ignoreTrailingSlash) {
        String path;
        PathPattern pathPattern = (PathPattern)exchange.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        if (pathPattern != null) {
            String patternString = pathPattern.getPatternString();
            if (ignoreTrailingSlash && patternString.length() > 1) {
                patternString = WebFluxTags.removeTrailingSlash(patternString);
            }
            if (patternString.isEmpty()) {
                return URI_ROOT;
            }
            return Tag.of((String)"uri", (String)patternString);
        }
        HttpStatus status = exchange.getResponse().getStatusCode();
        if (status != null) {
            if (status.is3xxRedirection()) {
                return URI_REDIRECTION;
            }
            if (status == HttpStatus.NOT_FOUND) {
                return URI_NOT_FOUND;
            }
        }
        if ((path = WebFluxTags.getPathInfo(exchange)).isEmpty()) {
            return URI_ROOT;
        }
        return URI_UNKNOWN;
    }

    private static String getPathInfo(ServerWebExchange exchange) {
        String path = exchange.getRequest().getPath().value();
        String uri = StringUtils.hasText((String)path) ? path : "/";
        String singleSlashes = FORWARD_SLASHES_PATTERN.matcher(uri).replaceAll("/");
        return WebFluxTags.removeTrailingSlash(singleSlashes);
    }

    private static String removeTrailingSlash(String text) {
        if (!StringUtils.hasLength((String)text)) {
            return text;
        }
        return text.endsWith("/") ? text.substring(0, text.length() - 1) : text;
    }

    public static Tag exception(Throwable exception) {
        if (exception != null) {
            String simpleName = exception.getClass().getSimpleName();
            return Tag.of((String)"exception", (String)(StringUtils.hasText((String)simpleName) ? simpleName : exception.getClass().getName()));
        }
        return EXCEPTION_NONE;
    }

    public static Tag outcome(ServerWebExchange exchange, Throwable exception) {
        if (exception != null && (exception instanceof CancelledServerWebExchangeException || DISCONNECTED_CLIENT_EXCEPTIONS.contains(exception.getClass().getSimpleName()))) {
            return Outcome.UNKNOWN.asTag();
        }
        Integer statusCode = WebFluxTags.extractStatusCode(exchange);
        Outcome outcome = statusCode != null ? Outcome.forStatus(statusCode) : Outcome.SUCCESS;
        return outcome.asTag();
    }

    private static Integer extractStatusCode(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        Integer statusCode = response.getRawStatusCode();
        if (statusCode != null) {
            return statusCode;
        }
        HttpStatus status = response.getStatusCode();
        return status != null ? Integer.valueOf(status.value()) : null;
    }
}

