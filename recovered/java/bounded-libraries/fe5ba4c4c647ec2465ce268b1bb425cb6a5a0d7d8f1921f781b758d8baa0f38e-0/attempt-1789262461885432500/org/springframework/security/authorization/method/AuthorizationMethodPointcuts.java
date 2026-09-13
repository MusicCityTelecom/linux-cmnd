/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.aop.Pointcut
 *  org.springframework.aop.support.ComposablePointcut
 *  org.springframework.aop.support.Pointcuts
 *  org.springframework.aop.support.annotation.AnnotationMatchingPointcut
 */
package org.springframework.security.authorization.method;

import java.lang.annotation.Annotation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.Pointcuts;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

final class AuthorizationMethodPointcuts {
    @SafeVarargs
    static Pointcut forAnnotations(Class<? extends Annotation> ... annotations) {
        ComposablePointcut pointcut = null;
        for (Class<? extends Annotation> annotation : annotations) {
            if (pointcut == null) {
                pointcut = new ComposablePointcut(AuthorizationMethodPointcuts.classOrMethod(annotation));
                continue;
            }
            pointcut.union(AuthorizationMethodPointcuts.classOrMethod(annotation));
        }
        return pointcut;
    }

    private static Pointcut classOrMethod(Class<? extends Annotation> annotation) {
        return Pointcuts.union((Pointcut)new AnnotationMatchingPointcut(null, annotation, true), (Pointcut)new AnnotationMatchingPointcut(annotation, true));
    }

    private AuthorizationMethodPointcuts() {
    }
}

