/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.core.BridgeMethodResolver
 *  org.springframework.core.MethodParameter
 *  org.springframework.core.annotation.AnnotatedElementUtils
 *  org.springframework.core.annotation.SynthesizingMethodParameter
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.ReflectionUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.messaging.handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.SynthesizingMethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

public class HandlerMethod {
    public static final Log defaultLogger = LogFactory.getLog(HandlerMethod.class);
    private final Object bean;
    @Nullable
    private final BeanFactory beanFactory;
    private final Class<?> beanType;
    private final Method method;
    private final Method bridgedMethod;
    private final MethodParameter[] parameters;
    @Nullable
    private HandlerMethod resolvedFromHandlerMethod;
    protected Log logger = defaultLogger;

    public HandlerMethod(Object bean, Method method) {
        Assert.notNull((Object)bean, (String)"Bean is required");
        Assert.notNull((Object)method, (String)"Method is required");
        this.bean = bean;
        this.beanFactory = null;
        this.beanType = ClassUtils.getUserClass((Object)bean);
        this.method = method;
        this.bridgedMethod = BridgeMethodResolver.findBridgedMethod((Method)method);
        ReflectionUtils.makeAccessible((Method)this.bridgedMethod);
        this.parameters = this.initMethodParameters();
    }

    public HandlerMethod(Object bean, String methodName, Class<?> ... parameterTypes) throws NoSuchMethodException {
        Assert.notNull((Object)bean, (String)"Bean is required");
        Assert.notNull((Object)methodName, (String)"Method name is required");
        this.bean = bean;
        this.beanFactory = null;
        this.beanType = ClassUtils.getUserClass((Object)bean);
        this.method = bean.getClass().getMethod(methodName, parameterTypes);
        this.bridgedMethod = BridgeMethodResolver.findBridgedMethod((Method)this.method);
        ReflectionUtils.makeAccessible((Method)this.bridgedMethod);
        this.parameters = this.initMethodParameters();
    }

    public HandlerMethod(String beanName, BeanFactory beanFactory, Method method) {
        Assert.hasText((String)beanName, (String)"Bean name is required");
        Assert.notNull((Object)beanFactory, (String)"BeanFactory is required");
        Assert.notNull((Object)method, (String)"Method is required");
        this.bean = beanName;
        this.beanFactory = beanFactory;
        Class beanType = beanFactory.getType(beanName);
        if (beanType == null) {
            throw new IllegalStateException("Cannot resolve bean type for bean with name '" + beanName + "'");
        }
        this.beanType = ClassUtils.getUserClass((Class)beanType);
        this.method = method;
        this.bridgedMethod = BridgeMethodResolver.findBridgedMethod((Method)method);
        ReflectionUtils.makeAccessible((Method)this.bridgedMethod);
        this.parameters = this.initMethodParameters();
    }

    protected HandlerMethod(HandlerMethod handlerMethod) {
        Assert.notNull((Object)handlerMethod, (String)"HandlerMethod is required");
        this.bean = handlerMethod.bean;
        this.beanFactory = handlerMethod.beanFactory;
        this.beanType = handlerMethod.beanType;
        this.method = handlerMethod.method;
        this.bridgedMethod = handlerMethod.bridgedMethod;
        this.parameters = handlerMethod.parameters;
        this.resolvedFromHandlerMethod = handlerMethod.resolvedFromHandlerMethod;
    }

    private HandlerMethod(HandlerMethod handlerMethod, Object handler) {
        Assert.notNull((Object)handlerMethod, (String)"HandlerMethod is required");
        Assert.notNull((Object)handler, (String)"Handler object is required");
        this.bean = handler;
        this.beanFactory = handlerMethod.beanFactory;
        this.beanType = handlerMethod.beanType;
        this.method = handlerMethod.method;
        this.bridgedMethod = handlerMethod.bridgedMethod;
        this.parameters = handlerMethod.parameters;
        this.resolvedFromHandlerMethod = handlerMethod;
    }

    private MethodParameter[] initMethodParameters() {
        int count = this.bridgedMethod.getParameterCount();
        MethodParameter[] result = new MethodParameter[count];
        for (int i2 = 0; i2 < count; ++i2) {
            result[i2] = new HandlerMethodParameter(i2);
        }
        return result;
    }

    public void setLogger(Log logger) {
        this.logger = logger;
    }

    public Log getLogger() {
        return this.logger;
    }

    public Object getBean() {
        return this.bean;
    }

    public Method getMethod() {
        return this.method;
    }

    public Class<?> getBeanType() {
        return this.beanType;
    }

    protected Method getBridgedMethod() {
        return this.bridgedMethod;
    }

    public MethodParameter[] getMethodParameters() {
        return this.parameters;
    }

    public MethodParameter getReturnType() {
        return new HandlerMethodParameter(-1);
    }

    public MethodParameter getReturnValueType(@Nullable Object returnValue) {
        return new ReturnValueMethodParameter(returnValue);
    }

    public boolean isVoid() {
        return Void.TYPE.equals(this.getReturnType().getParameterType());
    }

    @Nullable
    public <A extends Annotation> A getMethodAnnotation(Class<A> annotationType) {
        return (A)AnnotatedElementUtils.findMergedAnnotation((AnnotatedElement)this.method, annotationType);
    }

    public <A extends Annotation> boolean hasMethodAnnotation(Class<A> annotationType) {
        return AnnotatedElementUtils.hasAnnotation((AnnotatedElement)this.method, annotationType);
    }

    @Nullable
    public HandlerMethod getResolvedFromHandlerMethod() {
        return this.resolvedFromHandlerMethod;
    }

    public HandlerMethod createWithResolvedBean() {
        Object handler = this.bean;
        if (this.bean instanceof String) {
            Assert.state((this.beanFactory != null ? 1 : 0) != 0, (String)"Cannot resolve bean name without BeanFactory");
            String beanName = (String)this.bean;
            handler = this.beanFactory.getBean(beanName);
        }
        return new HandlerMethod(this, handler);
    }

    public String getShortLogMessage() {
        int args = this.method.getParameterCount();
        return this.getBeanType().getSimpleName() + "#" + this.method.getName() + "[" + args + " args]";
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HandlerMethod)) {
            return false;
        }
        HandlerMethod otherMethod = (HandlerMethod)other;
        return this.bean.equals(otherMethod.bean) && this.method.equals(otherMethod.method);
    }

    public int hashCode() {
        return this.bean.hashCode() * 31 + this.method.hashCode();
    }

    public String toString() {
        return this.method.toGenericString();
    }

    @Nullable
    protected static Object findProvidedArgument(MethodParameter parameter, Object ... providedArgs) {
        if (!ObjectUtils.isEmpty((Object[])providedArgs)) {
            for (Object providedArg : providedArgs) {
                if (!parameter.getParameterType().isInstance(providedArg)) continue;
                return providedArg;
            }
        }
        return null;
    }

    protected static String formatArgumentError(MethodParameter param, String message) {
        return "Could not resolve parameter [" + param.getParameterIndex() + "] in " + param.getExecutable().toGenericString() + (StringUtils.hasText((String)message) ? ": " + message : "");
    }

    protected void assertTargetBean(Method method, Object targetBean, Object[] args) {
        Class<?> targetBeanClass;
        Class<?> methodDeclaringClass = method.getDeclaringClass();
        if (!methodDeclaringClass.isAssignableFrom(targetBeanClass = targetBean.getClass())) {
            String text = "The mapped handler method class '" + methodDeclaringClass.getName() + "' is not an instance of the actual endpoint bean class '" + targetBeanClass.getName() + "'. If the endpoint requires proxying (e.g. due to @Transactional), please use class-based proxying.";
            throw new IllegalStateException(this.formatInvokeError(text, args));
        }
    }

    protected String formatInvokeError(String text, Object[] args) {
        String formattedArgs = IntStream.range(0, args.length).mapToObj(i2 -> args[i2] != null ? "[" + i2 + "] [type=" + args[i2].getClass().getName() + "] [value=" + args[i2] + "]" : "[" + i2 + "] [null]").collect(Collectors.joining(",\n", " ", " "));
        return text + "\nEndpoint [" + this.getBeanType().getName() + "]\nMethod [" + this.getBridgedMethod().toGenericString() + "] with argument values:\n" + formattedArgs;
    }

    private class ReturnValueMethodParameter
    extends HandlerMethodParameter {
        @Nullable
        private final Class<?> returnValueType;

        public ReturnValueMethodParameter(Object returnValue) {
            super(-1);
            this.returnValueType = returnValue != null ? returnValue.getClass() : null;
        }

        protected ReturnValueMethodParameter(ReturnValueMethodParameter original) {
            super(original);
            this.returnValueType = original.returnValueType;
        }

        public Class<?> getParameterType() {
            return this.returnValueType != null ? this.returnValueType : super.getParameterType();
        }

        @Override
        public ReturnValueMethodParameter clone() {
            return new ReturnValueMethodParameter(this);
        }
    }

    protected class HandlerMethodParameter
    extends SynthesizingMethodParameter {
        public HandlerMethodParameter(int index) {
            super(HandlerMethod.this.bridgedMethod, index);
        }

        protected HandlerMethodParameter(HandlerMethodParameter original) {
            super((SynthesizingMethodParameter)original);
        }

        public Class<?> getContainingClass() {
            return HandlerMethod.this.getBeanType();
        }

        public <T extends Annotation> T getMethodAnnotation(Class<T> annotationType) {
            return HandlerMethod.this.getMethodAnnotation(annotationType);
        }

        public <T extends Annotation> boolean hasMethodAnnotation(Class<T> annotationType) {
            return HandlerMethod.this.hasMethodAnnotation(annotationType);
        }

        public HandlerMethodParameter clone() {
            return new HandlerMethodParameter(this);
        }
    }
}

