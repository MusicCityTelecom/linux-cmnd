/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.annotation.MergedAnnotations
 *  org.springframework.core.annotation.MergedAnnotations$SearchStrategy
 */
package org.springframework.boot.validation.beanvalidation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import org.springframework.core.annotation.MergedAnnotations;

public interface MethodValidationExcludeFilter {
    public boolean isExcluded(Class<?> var1);

    public static MethodValidationExcludeFilter byAnnotation(Class<? extends Annotation> annotationType) {
        return MethodValidationExcludeFilter.byAnnotation(annotationType, MergedAnnotations.SearchStrategy.INHERITED_ANNOTATIONS);
    }

    public static MethodValidationExcludeFilter byAnnotation(Class<? extends Annotation> annotationType, MergedAnnotations.SearchStrategy searchStrategy) {
        return type -> MergedAnnotations.from((AnnotatedElement)type, (MergedAnnotations.SearchStrategy)searchStrategy).isPresent(annotationType);
    }
}

