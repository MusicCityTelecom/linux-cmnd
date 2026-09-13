/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.annotation.Timed
 *  io.micrometer.core.instrument.MeterRegistry
 *  io.micrometer.core.instrument.Timer
 *  io.micrometer.core.instrument.Timer$Builder
 *  io.micrometer.core.instrument.Timer$Sample
 *  javax.servlet.FilterChain
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.boot.web.servlet.error.ErrorAttributes
 *  org.springframework.http.HttpStatus
 *  org.springframework.web.filter.OncePerRequestFilter
 *  org.springframework.web.method.HandlerMethod
 *  org.springframework.web.servlet.DispatcherServlet
 *  org.springframework.web.servlet.HandlerMapping
 *  org.springframework.web.util.NestedServletException
 */
package org.springframework.boot.actuate.metrics.web.servlet;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.actuate.metrics.AutoTimer;
import org.springframework.boot.actuate.metrics.annotation.TimedAnnotations;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTagsProvider;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.NestedServletException;

public class WebMvcMetricsFilter
extends OncePerRequestFilter {
    private static final Log logger = LogFactory.getLog(WebMvcMetricsFilter.class);
    private final MeterRegistry registry;
    private final WebMvcTagsProvider tagsProvider;
    private final String metricName;
    private final AutoTimer autoTimer;

    public WebMvcMetricsFilter(MeterRegistry registry, WebMvcTagsProvider tagsProvider, String metricName, AutoTimer autoTimer) {
        this.registry = registry;
        this.tagsProvider = tagsProvider;
        this.metricName = metricName;
        this.autoTimer = autoTimer;
    }

    protected boolean shouldNotFilterAsyncDispatch() {
        return false;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        TimingContext timingContext = TimingContext.get(request);
        if (timingContext == null) {
            timingContext = this.startAndAttachTimingContext(request);
        }
        try {
            filterChain.doFilter((ServletRequest)request, (ServletResponse)response);
            if (!request.isAsyncStarted()) {
                Throwable exception = this.fetchException(request);
                this.record(timingContext, request, response, exception);
            }
        }
        catch (Exception ex) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            this.record(timingContext, request, response, this.unwrapNestedServletException(ex));
            throw ex;
        }
    }

    private Throwable unwrapNestedServletException(Throwable ex) {
        return ex instanceof NestedServletException ? ex.getCause() : ex;
    }

    private TimingContext startAndAttachTimingContext(HttpServletRequest request) {
        Timer.Sample timerSample = Timer.start((MeterRegistry)this.registry);
        TimingContext timingContext = new TimingContext(timerSample);
        timingContext.attachTo(request);
        return timingContext;
    }

    private Throwable fetchException(HttpServletRequest request) {
        Throwable exception = (Throwable)request.getAttribute(ErrorAttributes.ERROR_ATTRIBUTE);
        if (exception == null) {
            exception = (Throwable)request.getAttribute(DispatcherServlet.EXCEPTION_ATTRIBUTE);
        }
        return exception;
    }

    private void record(TimingContext timingContext, HttpServletRequest request, HttpServletResponse response, Throwable exception) {
        try {
            Object handler = this.getHandler(request);
            Set<Timed> annotations = this.getTimedAnnotations(handler);
            Timer.Sample timerSample = timingContext.getTimerSample();
            AutoTimer.apply(this.autoTimer, this.metricName, annotations, builder -> timerSample.stop(this.getTimer((Timer.Builder)builder, handler, request, response, exception)));
        }
        catch (Exception ex) {
            logger.warn((Object)"Failed to record timer metrics", (Throwable)ex);
        }
    }

    private Object getHandler(HttpServletRequest request) {
        return request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);
    }

    private Set<Timed> getTimedAnnotations(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            return TimedAnnotations.get(handlerMethod.getMethod(), handlerMethod.getBeanType());
        }
        return Collections.emptySet();
    }

    private Timer getTimer(Timer.Builder builder, Object handler, HttpServletRequest request, HttpServletResponse response, Throwable exception) {
        return builder.description("Duration of HTTP server request handling").tags(this.tagsProvider.getTags(request, response, handler, exception)).register(this.registry);
    }

    private static class TimingContext {
        private static final String ATTRIBUTE = TimingContext.class.getName();
        private final Timer.Sample timerSample;

        TimingContext(Timer.Sample timerSample) {
            this.timerSample = timerSample;
        }

        Timer.Sample getTimerSample() {
            return this.timerSample;
        }

        void attachTo(HttpServletRequest request) {
            request.setAttribute(ATTRIBUTE, (Object)this);
        }

        static TimingContext get(HttpServletRequest request) {
            return (TimingContext)request.getAttribute(ATTRIBUTE);
        }
    }
}

