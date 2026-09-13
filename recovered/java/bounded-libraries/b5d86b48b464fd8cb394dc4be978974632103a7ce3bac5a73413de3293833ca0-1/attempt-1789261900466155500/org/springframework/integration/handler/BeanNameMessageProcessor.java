/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.handler;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.integration.handler.MessageProcessor;
import org.springframework.integration.handler.MethodInvokingMessageProcessor;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;

public class BeanNameMessageProcessor<T>
implements MessageProcessor<T>,
BeanFactoryAware {
    private final String beanName;
    private final String methodName;
    private MessageProcessor<T> delegate;
    private BeanFactory beanFactory;

    public BeanNameMessageProcessor(String object, String methodName) {
        this.beanName = object;
        this.methodName = methodName;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    @Nullable
    public T processMessage(Message<?> message) {
        if (this.delegate == null) {
            Object target = this.beanFactory.getBean(this.beanName);
            MethodInvokingMessageProcessor methodInvokingMessageProcessor = new MethodInvokingMessageProcessor(target, this.methodName);
            methodInvokingMessageProcessor.setBeanFactory(this.beanFactory);
            this.delegate = methodInvokingMessageProcessor;
        }
        return this.delegate.processMessage(message);
    }
}

