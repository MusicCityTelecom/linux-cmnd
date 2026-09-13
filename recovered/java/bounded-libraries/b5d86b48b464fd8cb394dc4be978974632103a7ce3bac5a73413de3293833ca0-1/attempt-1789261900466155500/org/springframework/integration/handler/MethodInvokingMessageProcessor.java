/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.integration.handler.AbstractMessageProcessor;
import org.springframework.integration.handler.support.MessagingMethodInvokerHelper;
import org.springframework.integration.support.management.ManageableLifecycle;
import org.springframework.integration.support.utils.IntegrationUtils;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;

public class MethodInvokingMessageProcessor<T>
extends AbstractMessageProcessor<T>
implements ManageableLifecycle {
    private final MessagingMethodInvokerHelper delegate;

    public MethodInvokingMessageProcessor(Object targetObject, Method method) {
        this.delegate = new MessagingMethodInvokerHelper(targetObject, method, false);
    }

    public MethodInvokingMessageProcessor(Object targetObject, String methodName) {
        this.delegate = new MessagingMethodInvokerHelper(targetObject, methodName, false);
    }

    public MethodInvokingMessageProcessor(Object targetObject, String methodName, boolean canProcessMessageList) {
        this.delegate = new MessagingMethodInvokerHelper(targetObject, methodName, canProcessMessageList);
    }

    public MethodInvokingMessageProcessor(Object targetObject, Class<? extends Annotation> annotationType) {
        this.delegate = new MessagingMethodInvokerHelper(targetObject, annotationType, false);
    }

    @Override
    public void setConversionService(ConversionService conversionService) {
        super.setConversionService(conversionService);
        this.delegate.setConversionService(conversionService);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        super.setBeanFactory(beanFactory);
        this.delegate.setBeanFactory(beanFactory);
    }

    public void setUseSpelInvoker(boolean useSpelInvoker) {
        this.delegate.setUseSpelInvoker(useSpelInvoker);
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

    @Override
    @Nullable
    public T processMessage(Message<?> message) {
        try {
            return (T)this.delegate.process(message);
        }
        catch (Exception ex) {
            throw IntegrationUtils.wrapInHandlingExceptionIfNecessary(message, () -> "error occurred during processing message in 'MethodInvokingMessageProcessor' [" + this + ']', ex);
        }
    }
}

