/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.annotation.Timed
 *  org.springframework.core.annotation.MergedAnnotationCollectors
 *  org.springframework.core.annotation.MergedAnnotations
 *  org.springframework.util.ConcurrentReferenceHashMap
 */
package org.springframework.boot.actuate.metrics.annotation;

import io.micrometer.core.annotation.Timed;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.springframework.core.annotation.MergedAnnotationCollectors;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.util.ConcurrentReferenceHashMap;

public final class TimedAnnotations {
    private static final Map<AnnotatedElement, Set<Timed>> cache = new ConcurrentReferenceHashMap();

    private TimedAnnotations() {
    }

    public static Set<Timed> get(Method method, Class<?> type) {
        Set<Timed> methodAnnotations = TimedAnnotations.findTimedAnnotations(method);
        if (!methodAnnotations.isEmpty()) {
            return methodAnnotations;
        }
        return TimedAnnotations.findTimedAnnotations(type);
    }

    private static Set<Timed> findTimedAnnotations(AnnotatedElement element) {
        if (element == null) {
            return Collections.emptySet();
        }
        Set<Object> result = cache.get(element);
        if (result != null) {
            return result;
        }
        MergedAnnotations annotations = MergedAnnotations.from((AnnotatedElement)element);
        result = !annotations.isPresent(Timed.class) ? Collections.emptySet() : (Set)annotations.stream(Timed.class).collect(MergedAnnotationCollectors.toAnnotationSet());
        cache.put(element, result);
        return result;
    }
}

