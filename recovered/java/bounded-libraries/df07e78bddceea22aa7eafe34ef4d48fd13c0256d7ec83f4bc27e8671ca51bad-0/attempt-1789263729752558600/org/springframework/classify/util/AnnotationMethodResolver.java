/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.aop.support.AopUtils
 *  org.springframework.core.annotation.AnnotationUtils
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.ReflectionUtils
 *  org.springframework.util.ReflectionUtils$MethodCallback
 */
package org.springframework.classify.util;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.aop.support.AopUtils;
import org.springframework.classify.util.MethodResolver;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

public class AnnotationMethodResolver
implements MethodResolver {
    private Class<? extends Annotation> annotationType;

    public AnnotationMethodResolver(Class<? extends Annotation> annotationType) {
        Assert.notNull(annotationType, (String)"annotationType must not be null");
        Assert.isTrue((boolean)ObjectUtils.containsElement((Object[])annotationType.getAnnotation(Target.class).value(), (Object)((Object)ElementType.METHOD)), (String)("Annotation [" + annotationType + "] is not a Method-level annotation."));
        this.annotationType = annotationType;
    }

    @Override
    public Method findMethod(Object candidate) {
        Assert.notNull((Object)candidate, (String)"candidate object must not be null");
        Class<?> targetClass = AopUtils.getTargetClass((Object)candidate);
        if (targetClass == null) {
            targetClass = candidate.getClass();
        }
        return this.findMethod(targetClass);
    }

    @Override
    public Method findMethod(final Class<?> clazz) {
        Assert.notNull(clazz, (String)"class must not be null");
        final AtomicReference annotatedMethod = new AtomicReference();
        ReflectionUtils.doWithMethods(clazz, (ReflectionUtils.MethodCallback)new ReflectionUtils.MethodCallback(){

            public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
                Annotation annotation = AnnotationUtils.findAnnotation((Method)method, (Class)AnnotationMethodResolver.this.annotationType);
                if (annotation != null) {
                    Assert.isNull(annotatedMethod.get(), (String)("found more than one method on target class [" + clazz + "] with the annotation type [" + AnnotationMethodResolver.this.annotationType + "]"));
                    annotatedMethod.set(method);
                }
            }
        });
        return (Method)annotatedMethod.get();
    }
}

