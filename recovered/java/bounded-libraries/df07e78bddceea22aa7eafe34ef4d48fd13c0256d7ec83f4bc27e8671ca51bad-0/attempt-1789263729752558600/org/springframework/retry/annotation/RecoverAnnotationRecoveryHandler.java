/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.annotation.AnnotationUtils
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ReflectionUtils
 *  org.springframework.util.ReflectionUtils$MethodCallback
 *  org.springframework.util.StringUtils
 */
package org.springframework.retry.annotation;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import org.springframework.classify.SubclassClassifier;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.retry.ExhaustedRetryException;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.interceptor.MethodInvocationRecoverer;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

public class RecoverAnnotationRecoveryHandler<T>
implements MethodInvocationRecoverer<T> {
    private final SubclassClassifier<Throwable, Method> classifier = new SubclassClassifier();
    private final Map<Method, SimpleMetadata> methods = new HashMap<Method, SimpleMetadata>();
    private final Object target;
    private String recoverMethodName;

    public RecoverAnnotationRecoveryHandler(Object target, Method method) {
        this.target = target;
        this.init(target, method);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public T recover(Object[] args, Throwable cause) {
        Method method = this.findClosestMatch(args, cause.getClass());
        if (method == null) {
            throw new ExhaustedRetryException("Cannot locate recovery method", cause);
        }
        SimpleMetadata meta = this.methods.get(method);
        Object[] argsToUse = meta.getArgs(cause, args);
        boolean methodAccessible = method.isAccessible();
        try {
            Object result;
            ReflectionUtils.makeAccessible((Method)method);
            RetryContext context = RetrySynchronizationManager.getContext();
            Object proxy = null;
            if (context != null && (proxy = context.getAttribute("___proxy___")) != null) {
                Method proxyMethod = this.findMethodOnProxy(method, proxy);
                if (proxyMethod == null) {
                    proxy = null;
                } else {
                    method = proxyMethod;
                }
            }
            if (proxy == null) {
                proxy = this.target;
            }
            Object object = result = ReflectionUtils.invokeMethod((Method)method, (Object)proxy, (Object[])argsToUse);
            return (T)object;
        }
        finally {
            if (methodAccessible != method.isAccessible()) {
                method.setAccessible(methodAccessible);
            }
        }
    }

    private Method findMethodOnProxy(Method method, Object proxy) {
        try {
            return proxy.getClass().getMethod(method.getName(), method.getParameterTypes());
        }
        catch (NoSuchMethodException e) {
            return null;
        }
        catch (SecurityException e) {
            return null;
        }
    }

    private Method findClosestMatch(Object[] args, Class<? extends Throwable> cause) {
        Method result = null;
        if (StringUtils.isEmpty((Object)this.recoverMethodName)) {
            int min = Integer.MAX_VALUE;
            for (Map.Entry<Method, SimpleMetadata> entry : this.methods.entrySet()) {
                boolean parametersMatch;
                Method method = entry.getKey();
                SimpleMetadata meta = entry.getValue();
                Class<? extends Throwable> type = meta.getType();
                if (type == null) {
                    type = Throwable.class;
                }
                if (!type.isAssignableFrom(cause)) continue;
                int distance = this.calculateDistance(cause, type);
                if (distance < min) {
                    min = distance;
                    result = method;
                    continue;
                }
                if (distance != min || !(parametersMatch = this.compareParameters(args, meta.getArgCount(), method.getParameterTypes()))) continue;
                result = method;
            }
        } else {
            for (Map.Entry<Method, SimpleMetadata> entry : this.methods.entrySet()) {
                SimpleMetadata meta;
                Method method = entry.getKey();
                if (!method.getName().equals(this.recoverMethodName) || !(meta = entry.getValue()).type.isAssignableFrom(cause) || !this.compareParameters(args, meta.getArgCount(), method.getParameterTypes())) continue;
                result = method;
                break;
            }
        }
        return result;
    }

    private int calculateDistance(Class<? extends Throwable> cause, Class<? extends Throwable> type) {
        int result = 0;
        for (Class<? extends Throwable> current = cause; current != type && current != Throwable.class; current = current.getSuperclass()) {
            ++result;
        }
        return result;
    }

    private boolean compareParameters(Object[] args, int argCount, Class<?>[] parameterTypes) {
        if (argCount == args.length + 1) {
            int startingIndex = 0;
            if (parameterTypes.length > 0 && Throwable.class.isAssignableFrom(parameterTypes[0])) {
                startingIndex = 1;
            }
            for (int i = startingIndex; i < parameterTypes.length; ++i) {
                Object argument;
                Object object = argument = i - startingIndex < args.length ? args[i - startingIndex] : null;
                if (argument == null) continue;
                Class parameterType = parameterTypes[i];
                if ((parameterType = ClassUtils.resolvePrimitiveIfNecessary(parameterType)).isAssignableFrom(argument.getClass())) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    private void init(final Object target, Method method) {
        final HashMap types = new HashMap();
        final Method failingMethod = method;
        Retryable retryable = (Retryable)AnnotationUtils.findAnnotation((Method)method, Retryable.class);
        if (retryable != null) {
            this.recoverMethodName = retryable.recover();
        }
        ReflectionUtils.doWithMethods(target.getClass(), (ReflectionUtils.MethodCallback)new ReflectionUtils.MethodCallback(){

            public void doWith(Method method) throws IllegalArgumentException {
                Recover recover = (Recover)AnnotationUtils.findAnnotation((Method)method, Recover.class);
                if (recover == null) {
                    recover = RecoverAnnotationRecoveryHandler.this.findAnnotationOnTarget(target, method);
                }
                if (recover != null && failingMethod.getGenericReturnType() instanceof ParameterizedType && method.getGenericReturnType() instanceof ParameterizedType) {
                    if (RecoverAnnotationRecoveryHandler.this.isParameterizedTypeAssignable((ParameterizedType)method.getGenericReturnType(), (ParameterizedType)failingMethod.getGenericReturnType())) {
                        RecoverAnnotationRecoveryHandler.this.putToMethodsMap(method, types);
                    }
                } else if (recover != null && method.getReturnType().isAssignableFrom(failingMethod.getReturnType())) {
                    RecoverAnnotationRecoveryHandler.this.putToMethodsMap(method, types);
                }
            }
        });
        this.classifier.setTypeMap(types);
        this.optionallyFilterMethodsBy(failingMethod.getReturnType());
    }

    private boolean isParameterizedTypeAssignable(ParameterizedType methodReturnType, ParameterizedType failingMethodReturnType) {
        int startingIndex;
        Type[] failingMethodActualArgs;
        Type[] methodActualArgs = methodReturnType.getActualTypeArguments();
        if (methodActualArgs.length != (failingMethodActualArgs = failingMethodReturnType.getActualTypeArguments()).length) {
            return false;
        }
        for (int i = startingIndex = 0; i < methodActualArgs.length; ++i) {
            Type methodArgType = methodActualArgs[i];
            Type failingMethodArgType = failingMethodActualArgs[i];
            if (methodArgType instanceof ParameterizedType && failingMethodArgType instanceof ParameterizedType) {
                return this.isParameterizedTypeAssignable((ParameterizedType)methodArgType, (ParameterizedType)failingMethodArgType);
            }
            if (!(methodArgType instanceof Class) || !(failingMethodArgType instanceof Class) || failingMethodArgType.equals(methodArgType)) continue;
            return false;
        }
        return true;
    }

    private void putToMethodsMap(Method method, Map<Class<? extends Throwable>, Method> types) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length > 0 && Throwable.class.isAssignableFrom(parameterTypes[0])) {
            Class<?> type = parameterTypes[0];
            types.put(type, method);
            this.methods.put(method, new SimpleMetadata(parameterTypes.length, type));
        } else {
            this.classifier.setDefaultValue(method);
            this.methods.put(method, new SimpleMetadata(parameterTypes.length, null));
        }
    }

    private Recover findAnnotationOnTarget(Object target, Method method) {
        try {
            Method targetMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());
            return (Recover)AnnotationUtils.findAnnotation((Method)targetMethod, Recover.class);
        }
        catch (Exception e) {
            return null;
        }
    }

    private void optionallyFilterMethodsBy(Class<?> returnClass) {
        HashMap<Method, SimpleMetadata> filteredMethods = new HashMap<Method, SimpleMetadata>();
        for (Method method : this.methods.keySet()) {
            if (method.getReturnType() != returnClass) continue;
            filteredMethods.put(method, this.methods.get(method));
        }
        if (filteredMethods.size() > 0) {
            this.methods.clear();
            this.methods.putAll(filteredMethods);
        }
    }

    private static class SimpleMetadata {
        private final int argCount;
        private final Class<? extends Throwable> type;

        public SimpleMetadata(int argCount, Class<? extends Throwable> type) {
            this.argCount = argCount;
            this.type = type;
        }

        public int getArgCount() {
            return this.argCount;
        }

        public Class<? extends Throwable> getType() {
            return this.type;
        }

        public Object[] getArgs(Throwable t, Object[] args) {
            int length;
            Object[] result = new Object[this.getArgCount()];
            int startArgs = 0;
            if (this.type != null) {
                result[0] = t;
                startArgs = 1;
            }
            if ((length = Math.min(result.length - startArgs, args.length)) == 0) {
                return result;
            }
            System.arraycopy(args, 0, result, startArgs, length);
            return result;
        }
    }
}

