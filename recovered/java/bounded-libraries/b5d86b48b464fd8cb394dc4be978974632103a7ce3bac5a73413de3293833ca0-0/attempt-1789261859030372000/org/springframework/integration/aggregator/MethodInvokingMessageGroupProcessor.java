/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.aggregator;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.integration.aggregator.AbstractAggregatingMessageGroupProcessor;
import org.springframework.integration.aggregator.MethodInvokingMessageListProcessor;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.store.MessageGroup;
import org.springframework.integration.support.management.ManageableLifecycle;
import org.springframework.messaging.Message;

public class MethodInvokingMessageGroupProcessor
extends AbstractAggregatingMessageGroupProcessor
implements ManageableLifecycle {
    private final MethodInvokingMessageListProcessor<Object> processor;

    public MethodInvokingMessageGroupProcessor(Object target) {
        this.processor = new MethodInvokingMessageListProcessor(target, Aggregator.class);
    }

    public MethodInvokingMessageGroupProcessor(Object target, String methodName) {
        this.processor = new MethodInvokingMessageListProcessor(target, methodName);
    }

    public MethodInvokingMessageGroupProcessor(Object target, Method method) {
        this.processor = new MethodInvokingMessageListProcessor(target, method);
    }

    public void setConversionService(ConversionService conversionService) {
        this.processor.setConversionService(conversionService);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        super.setBeanFactory(beanFactory);
        this.processor.setBeanFactory(beanFactory);
    }

    @Override
    protected final Object aggregatePayloads(MessageGroup group, Map<String, Object> headers) {
        Collection<Message<?>> messagesUpForProcessing = group.getMessages();
        return this.processor.process(messagesUpForProcessing, headers);
    }

    @Override
    public void start() {
        this.processor.start();
    }

    @Override
    public void stop() {
        this.processor.stop();
    }

    @Override
    public boolean isRunning() {
        return this.processor.isRunning();
    }
}

