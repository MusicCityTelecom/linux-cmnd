/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.annotation.Timed
 *  io.micrometer.core.instrument.LongTaskTimer
 *  io.micrometer.core.instrument.LongTaskTimer$Builder
 *  io.micrometer.core.instrument.LongTaskTimer$Sample
 *  io.micrometer.core.instrument.MeterRegistry
 *  io.micrometer.core.instrument.Tag
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.annotation.MergedAnnotationCollectors
 *  org.springframework.core.annotation.MergedAnnotations
 *  org.springframework.web.method.HandlerMethod
 *  org.springframework.web.servlet.HandlerInterceptor
 */
package org.springframework.boot.actuate.metrics.web.servlet;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.LongTaskTimer;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTagsProvider;
import org.springframework.core.annotation.MergedAnnotationCollectors;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class LongTaskTimingHandlerInterceptor
implements HandlerInterceptor {
    private static final Log logger = LogFactory.getLog(LongTaskTimingHandlerInterceptor.class);
    private final MeterRegistry registry;
    private final WebMvcTagsProvider tagsProvider;

    public LongTaskTimingHandlerInterceptor(MeterRegistry registry, WebMvcTagsProvider tagsProvider) {
        this.registry = registry;
        this.tagsProvider = tagsProvider;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LongTaskTimingContext timingContext = LongTaskTimingContext.get(request);
        if (timingContext == null) {
            this.startAndAttachTimingContext(request, handler);
        }
        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (!request.isAsyncStarted()) {
            this.stopLongTaskTimers(LongTaskTimingContext.get(request));
        }
    }

    private void startAndAttachTimingContext(HttpServletRequest request, Object handler) {
        Set<Timed> annotations = this.getTimedAnnotations(handler);
        Collection<LongTaskTimer.Sample> longTaskTimerSamples = this.getLongTaskTimerSamples(request, handler, annotations);
        LongTaskTimingContext timingContext = new LongTaskTimingContext(longTaskTimerSamples);
        timingContext.attachTo(request);
    }

    private Collection<LongTaskTimer.Sample> getLongTaskTimerSamples(HttpServletRequest request, Object handler, Set<Timed> annotations) {
        ArrayList<LongTaskTimer.Sample> samples = new ArrayList<LongTaskTimer.Sample>();
        try {
            annotations.stream().filter(Timed::longTask).forEach(annotation -> {
                Iterable<Tag> tags = this.tagsProvider.getLongRequestTags(request, handler);
                LongTaskTimer.Builder builder = LongTaskTimer.builder((Timed)annotation).tags(tags);
                LongTaskTimer timer = builder.register(this.registry);
                samples.add(timer.start());
            });
        }
        catch (Exception ex) {
            logger.warn((Object)"Failed to start long task timers", (Throwable)ex);
        }
        return samples;
    }

    private Set<Timed> getTimedAnnotations(Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return Collections.emptySet();
        }
        return this.getTimedAnnotations((HandlerMethod)handler);
    }

    private Set<Timed> getTimedAnnotations(HandlerMethod handler) {
        Set<Timed> timed = this.findTimedAnnotations(handler.getMethod());
        if (timed.isEmpty()) {
            return this.findTimedAnnotations(handler.getBeanType());
        }
        return timed;
    }

    private Set<Timed> findTimedAnnotations(AnnotatedElement element) {
        return (Set)MergedAnnotations.from((AnnotatedElement)element).stream(Timed.class).collect(MergedAnnotationCollectors.toAnnotationSet());
    }

    private void stopLongTaskTimers(LongTaskTimingContext timingContext) {
        for (LongTaskTimer.Sample sample : timingContext.getLongTaskTimerSamples()) {
            sample.stop();
        }
    }

    static class LongTaskTimingContext {
        private static final String ATTRIBUTE = LongTaskTimingContext.class.getName();
        private final Collection<LongTaskTimer.Sample> longTaskTimerSamples;

        LongTaskTimingContext(Collection<LongTaskTimer.Sample> longTaskTimerSamples) {
            this.longTaskTimerSamples = longTaskTimerSamples;
        }

        Collection<LongTaskTimer.Sample> getLongTaskTimerSamples() {
            return this.longTaskTimerSamples;
        }

        void attachTo(HttpServletRequest request) {
            request.setAttribute(ATTRIBUTE, (Object)this);
        }

        static LongTaskTimingContext get(HttpServletRequest request) {
            return (LongTaskTimingContext)request.getAttribute(ATTRIBUTE);
        }
    }
}

