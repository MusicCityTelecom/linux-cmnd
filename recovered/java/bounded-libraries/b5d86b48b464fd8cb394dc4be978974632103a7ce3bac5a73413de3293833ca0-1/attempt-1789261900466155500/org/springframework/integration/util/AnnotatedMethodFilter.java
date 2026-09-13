/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.annotation.AnnotationUtils
 *  org.springframework.expression.MethodFilter
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.MethodFilter;
import org.springframework.util.StringUtils;

public class AnnotatedMethodFilter
implements MethodFilter {
    private final Class<? extends Annotation> annotationType;
    private final String methodName;
    private final boolean requiresReply;

    public AnnotatedMethodFilter(Class<? extends Annotation> annotationType, String methodName, boolean requiresReply) {
        this.annotationType = annotationType;
        this.methodName = methodName;
        this.requiresReply = requiresReply;
    }

    public List<Method> filter(List<Method> methods) {
        ArrayList<Method> annotatedCandidates = new ArrayList<Method>();
        ArrayList<Method> fallbackCandidates = new ArrayList<Method>();
        for (Method method : methods) {
            if (method.isBridge() || this.requiresReply && method.getReturnType().equals(Void.TYPE) || StringUtils.hasText((String)this.methodName) && !this.methodName.equals(method.getName())) continue;
            if (this.annotationType != null && AnnotationUtils.findAnnotation((Method)method, this.annotationType) != null) {
                annotatedCandidates.add(method);
                continue;
            }
            fallbackCandidates.add(method);
        }
        return !annotatedCandidates.isEmpty() ? annotatedCandidates : fallbackCandidates;
    }
}

