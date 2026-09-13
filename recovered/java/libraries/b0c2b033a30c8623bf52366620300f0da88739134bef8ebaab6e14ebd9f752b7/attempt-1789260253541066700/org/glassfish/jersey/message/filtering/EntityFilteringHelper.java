/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.glassfish.jersey.message.filtering.EntityFiltering;
import org.glassfish.jersey.message.filtering.spi.FilteringHelper;

final class EntityFilteringHelper {
    public static Set<String> getFilteringScopes(Annotation[] annotations) {
        return EntityFilteringHelper.getFilteringScopes(annotations, true);
    }

    public static Set<String> getFilteringScopes(Annotation[] annotations, boolean filter) {
        if (annotations.length == 0) {
            return Collections.emptySet();
        }
        HashSet<String> contexts = new HashSet<String>(annotations.length);
        for (Annotation annotation : annotations = filter ? EntityFilteringHelper.getFilteringAnnotations(annotations) : annotations) {
            contexts.add(annotation.annotationType().getName());
        }
        return contexts;
    }

    public static Annotation[] getFilteringAnnotations(Annotation[] annotations) {
        if (annotations == null || annotations.length == 0) {
            return FilteringHelper.EMPTY_ANNOTATIONS;
        }
        ArrayList<Annotation> filteringAnnotations = new ArrayList<Annotation>(annotations.length);
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            for (Annotation metaAnnotation : annotationType.getDeclaredAnnotations()) {
                if (!(metaAnnotation instanceof EntityFiltering)) continue;
                filteringAnnotations.add(annotation);
            }
        }
        return filteringAnnotations.toArray(new Annotation[filteringAnnotations.size()]);
    }

    public static <T extends Annotation> T getAnnotation(Annotation[] annotations, Class<T> clazz) {
        for (Annotation annotation : annotations) {
            if (!annotation.annotationType().getClass().isAssignableFrom(clazz)) continue;
            return (T)annotation;
        }
        return null;
    }

    private EntityFilteringHelper() {
    }
}

