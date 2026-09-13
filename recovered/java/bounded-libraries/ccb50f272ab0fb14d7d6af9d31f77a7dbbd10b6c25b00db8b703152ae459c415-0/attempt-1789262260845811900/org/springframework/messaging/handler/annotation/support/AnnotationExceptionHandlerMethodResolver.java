/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.MethodIntrospector
 *  org.springframework.core.annotation.AnnotatedElementUtils
 */
package org.springframework.messaging.handler.annotation.support;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.invocation.AbstractExceptionHandlerMethodResolver;

public class AnnotationExceptionHandlerMethodResolver
extends AbstractExceptionHandlerMethodResolver {
    public AnnotationExceptionHandlerMethodResolver(Class<?> handlerType) {
        super(AnnotationExceptionHandlerMethodResolver.initExceptionMappings(handlerType));
    }

    private static Map<Class<? extends Throwable>, Method> initExceptionMappings(Class<?> handlerType) {
        Map methods = MethodIntrospector.selectMethods(handlerType, method -> (MessageExceptionHandler)AnnotatedElementUtils.findMergedAnnotation((AnnotatedElement)method, MessageExceptionHandler.class));
        HashMap<Class<? extends Throwable>, Method> result = new HashMap<Class<? extends Throwable>, Method>();
        for (Map.Entry entry : methods.entrySet()) {
            Method method2 = (Method)entry.getKey();
            ArrayList<Class<? extends Throwable>> exceptionTypes = new ArrayList<Class<? extends Throwable>>(Arrays.asList(((MessageExceptionHandler)entry.getValue()).value()));
            if (exceptionTypes.isEmpty()) {
                exceptionTypes.addAll(AnnotationExceptionHandlerMethodResolver.getExceptionsFromMethodSignature(method2));
            }
            for (Class clazz : exceptionTypes) {
                Method oldMethod = result.put(clazz, method2);
                if (oldMethod == null || oldMethod.equals(method2)) continue;
                throw new IllegalStateException("Ambiguous @ExceptionHandler method mapped for [" + clazz + "]: {" + oldMethod + ", " + method2 + "}");
            }
        }
        return result;
    }
}

