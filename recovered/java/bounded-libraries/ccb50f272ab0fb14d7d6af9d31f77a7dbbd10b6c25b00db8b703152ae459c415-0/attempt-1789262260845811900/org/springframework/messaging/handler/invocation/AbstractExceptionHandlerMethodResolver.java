/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.ExceptionDepthComparator
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.ConcurrentReferenceHashMap
 */
package org.springframework.messaging.handler.invocation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.core.ExceptionDepthComparator;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ConcurrentReferenceHashMap;

public abstract class AbstractExceptionHandlerMethodResolver {
    private static final Method NO_MATCHING_EXCEPTION_HANDLER_METHOD;
    private final Map<Class<? extends Throwable>, Method> mappedMethods = new HashMap<Class<? extends Throwable>, Method>(16);
    private final Map<Class<? extends Throwable>, Method> exceptionLookupCache = new ConcurrentReferenceHashMap(16);

    protected AbstractExceptionHandlerMethodResolver(Map<Class<? extends Throwable>, Method> mappedMethods) {
        Assert.notNull(mappedMethods, (String)"Mapped Methods must not be null");
        this.mappedMethods.putAll(mappedMethods);
    }

    protected static List<Class<? extends Throwable>> getExceptionsFromMethodSignature(Method method) {
        ArrayList<Class<? extends Throwable>> result = new ArrayList<Class<? extends Throwable>>();
        for (Class<?> paramType : method.getParameterTypes()) {
            if (!Throwable.class.isAssignableFrom(paramType)) continue;
            result.add(paramType);
        }
        if (result.isEmpty()) {
            throw new IllegalStateException("No exception types mapped to " + method);
        }
        return result;
    }

    public boolean hasExceptionMappings() {
        return !this.mappedMethods.isEmpty();
    }

    @Nullable
    public Method resolveMethod(Throwable exception) {
        Throwable cause;
        Method method = this.resolveMethodByExceptionType(exception.getClass());
        if (method == null && (cause = exception.getCause()) != null) {
            method = this.resolveMethodByExceptionType(cause.getClass());
        }
        return method;
    }

    @Nullable
    public Method resolveMethodByExceptionType(Class<? extends Throwable> exceptionType) {
        Method method = this.exceptionLookupCache.get(exceptionType);
        if (method == null) {
            method = this.getMappedMethod(exceptionType);
            this.exceptionLookupCache.put(exceptionType, method);
        }
        return method != NO_MATCHING_EXCEPTION_HANDLER_METHOD ? method : null;
    }

    @Nullable
    private Method getMappedMethod(Class<? extends Throwable> exceptionType) {
        ArrayList<Class<? extends Throwable>> matches = new ArrayList<Class<? extends Throwable>>();
        for (Class<? extends Throwable> mappedException : this.mappedMethods.keySet()) {
            if (!mappedException.isAssignableFrom(exceptionType)) continue;
            matches.add(mappedException);
        }
        if (!matches.isEmpty()) {
            if (matches.size() > 1) {
                matches.sort((Comparator<Class<? extends Throwable>>)new ExceptionDepthComparator(exceptionType));
            }
            return this.mappedMethods.get(matches.get(0));
        }
        return NO_MATCHING_EXCEPTION_HANDLER_METHOD;
    }

    private void noMatchingExceptionHandler() {
    }

    static {
        try {
            NO_MATCHING_EXCEPTION_HANDLER_METHOD = AbstractExceptionHandlerMethodResolver.class.getDeclaredMethod("noMatchingExceptionHandler", new Class[0]);
        }
        catch (NoSuchMethodException ex) {
            throw new IllegalStateException("Expected method not found: " + ex);
        }
    }
}

