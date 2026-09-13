/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.annotation.Timed
 *  io.micrometer.core.instrument.MeterRegistry
 *  io.micrometer.core.instrument.Tag
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.reactivestreams.Publisher
 *  org.springframework.boot.web.reactive.error.ErrorAttributes
 *  org.springframework.core.annotation.Order
 *  org.springframework.http.server.reactive.ServerHttpResponse
 *  org.springframework.web.method.HandlerMethod
 *  org.springframework.web.reactive.HandlerMapping
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.server.WebFilter
 *  org.springframework.web.server.WebFilterChain
 *  reactor.core.publisher.Mono
 */
package org.springframework.boot.actuate.metrics.web.reactive.server;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reactivestreams.Publisher;
import org.springframework.boot.actuate.metrics.AutoTimer;
import org.springframework.boot.actuate.metrics.annotation.TimedAnnotations;
import org.springframework.boot.actuate.metrics.web.reactive.server.CancelledServerWebExchangeException;
import org.springframework.boot.actuate.metrics.web.reactive.server.WebFluxTagsProvider;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Order(value=-2147483647)
public class MetricsWebFilter
implements WebFilter {
    private static Log logger = LogFactory.getLog(MetricsWebFilter.class);
    private final MeterRegistry registry;
    private final WebFluxTagsProvider tagsProvider;
    private final String metricName;
    private final AutoTimer autoTimer;

    public MetricsWebFilter(MeterRegistry registry, WebFluxTagsProvider tagsProvider, String metricName, AutoTimer autoTimer) {
        this.registry = registry;
        this.tagsProvider = tagsProvider;
        this.metricName = metricName;
        this.autoTimer = autoTimer != null ? autoTimer : AutoTimer.DISABLED;
    }

    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange).transformDeferred(call -> this.filter(exchange, (Mono<Void>)call));
    }

    private Publisher<Void> filter(ServerWebExchange exchange, Mono<Void> call) {
        long start = System.nanoTime();
        return call.doOnEach(signal -> this.onTerminalSignal(exchange, signal.getThrowable(), start)).doOnCancel(() -> this.onTerminalSignal(exchange, new CancelledServerWebExchangeException(), start));
    }

    private void onTerminalSignal(ServerWebExchange exchange, Throwable cause, long start) {
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted() || cause instanceof CancelledServerWebExchangeException) {
            this.record(exchange, cause, start);
        } else {
            response.beforeCommit(() -> {
                this.record(exchange, cause, start);
                return Mono.empty();
            });
        }
    }

    private void record(ServerWebExchange exchange, Throwable cause, long start) {
        try {
            cause = cause != null ? cause : (Throwable)exchange.getAttribute(ErrorAttributes.ERROR_ATTRIBUTE);
            Object handler = exchange.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);
            Set<Timed> annotations = this.getTimedAnnotations(handler);
            Iterable<Tag> tags = this.tagsProvider.httpRequestTags(exchange, cause);
            long duration = System.nanoTime() - start;
            AutoTimer.apply(this.autoTimer, this.metricName, annotations, builder -> builder.description("Duration of HTTP server request handling").tags(tags).register(this.registry).record(duration, TimeUnit.NANOSECONDS));
        }
        catch (Exception ex) {
            logger.warn((Object)"Failed to record timer metrics", (Throwable)ex);
        }
    }

    private Set<Timed> getTimedAnnotations(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            return TimedAnnotations.get(handlerMethod.getMethod(), handlerMethod.getBeanType());
        }
        return Collections.emptySet();
    }
}

