/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.aop.framework.AopProxyUtils
 *  org.springframework.core.annotation.AnnotatedElementUtils
 *  org.springframework.core.annotation.AnnotationUtils
 *  org.springframework.messaging.MessagingException
 *  org.springframework.messaging.handler.annotation.Header
 *  org.springframework.messaging.handler.annotation.Headers
 *  org.springframework.messaging.handler.annotation.Payload
 *  org.springframework.util.ReflectionUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.integration.annotation.EndpointId;
import org.springframework.integration.annotation.Payloads;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

public final class MessagingAnnotationUtils {
    public static <T> T resolveAttribute(List<Annotation> annotations, String name, Class<T> requiredType) {
        for (Annotation annotation : annotations) {
            Object value = AnnotationUtils.getValue((Annotation)annotation, (String)name);
            if (!requiredType.isInstance(value) || !MessagingAnnotationUtils.hasValue(value)) continue;
            return (T)value;
        }
        return null;
    }

    public static boolean hasValue(Object value) {
        return !(value == null || value instanceof String && !StringUtils.hasText((String)((String)value)) || value.getClass().isArray() && ((Object[])value).length <= 0);
    }

    public static Method findAnnotatedMethod(Object target, Class<? extends Annotation> annotationType) {
        AtomicReference reference = new AtomicReference();
        ReflectionUtils.doWithMethods((Class)AopProxyUtils.ultimateTargetClass((Object)target), method -> reference.compareAndSet(null, method), method -> ReflectionUtils.USER_DECLARED_METHODS.matches(method) && AnnotatedElementUtils.isAnnotated((AnnotatedElement)method, (String)annotationType.getName()));
        return (Method)reference.get();
    }

    public static Annotation findMessagePartAnnotation(Annotation[] annotations, boolean payloads) {
        if (annotations == null || annotations.length == 0) {
            return null;
        }
        Annotation match = null;
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> type = annotation.annotationType();
            if (!type.equals(Payload.class) && !type.equals(Header.class) && !type.equals(Headers.class) && (!payloads || !type.equals(Payloads.class))) continue;
            if (match != null) {
                throw new MessagingException("At most one parameter annotation can be provided for message mapping, but found two: [" + match.annotationType().getName() + "] and [" + annotation.annotationType().getName() + "]");
            }
            match = annotation;
        }
        return match;
    }

    public static String endpointIdValue(Method method) {
        EndpointId endpointId = (EndpointId)AnnotationUtils.findAnnotation((Method)method, EndpointId.class);
        return endpointId != null ? endpointId.value() : null;
    }

    private MessagingAnnotationUtils() {
    }
}

