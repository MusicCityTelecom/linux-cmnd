/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.lang.NonNull
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.aggregator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.integration.handler.support.MessagingMethodInvokerHelper;
import org.springframework.integration.support.management.ManageableLifecycle;
import org.springframework.integration.util.AbstractExpressionEvaluator;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;

public class MethodInvokingMessageListProcessor<T>
extends AbstractExpressionEvaluator
implements ManageableLifecycle {
    private final MessagingMethodInvokerHelper delegate;

    public MethodInvokingMessageListProcessor(Object targetObject, Method method, Class<T> expectedType) {
        this.delegate = new MessagingMethodInvokerHelper(targetObject, method, expectedType, true);
    }

    public MethodInvokingMessageListProcessor(Object targetObject, Method method) {
        this.delegate = new MessagingMethodInvokerHelper(targetObject, method, true);
    }

    public MethodInvokingMessageListProcessor(Object targetObject, String methodName, Class<T> expectedType) {
        this.delegate = new MessagingMethodInvokerHelper(targetObject, methodName, expectedType, true);
    }

    public MethodInvokingMessageListProcessor(Object targetObject, String methodName) {
        this.delegate = new MessagingMethodInvokerHelper(targetObject, methodName, true);
    }

    public MethodInvokingMessageListProcessor(Object targetObject, Class<? extends Annotation> annotationType) {
        this.delegate = new MessagingMethodInvokerHelper(targetObject, annotationType, Object.class, true);
    }

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) {
        super.setBeanFactory(beanFactory);
        this.delegate.setBeanFactory(beanFactory);
    }

    public void setUseSpelInvoker(boolean useSpelInvoker) {
        this.delegate.setUseSpelInvoker(useSpelInvoker);
    }

    public String toString() {
        return this.delegate.toString();
    }

    public T process(Collection<Message<?>> messages, Map<String, Object> aggregateHeaders) {
        return (T)this.delegate.process(messages, aggregateHeaders);
    }

    @Override
    public void start() {
        this.delegate.start();
    }

    @Override
    public void stop() {
        this.delegate.stop();
    }

    @Override
    public boolean isRunning() {
        return this.delegate.isRunning();
    }
}

