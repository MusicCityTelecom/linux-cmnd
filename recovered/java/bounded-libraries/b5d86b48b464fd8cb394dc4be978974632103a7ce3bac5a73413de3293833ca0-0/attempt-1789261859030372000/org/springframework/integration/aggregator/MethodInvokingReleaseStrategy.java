/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.core.convert.ConversionService
 */
package org.springframework.integration.aggregator;

import java.lang.reflect.Method;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.core.convert.ConversionService;
import org.springframework.integration.aggregator.MethodInvokingMessageListProcessor;
import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.store.MessageGroup;
import org.springframework.integration.support.management.ManageableLifecycle;

public class MethodInvokingReleaseStrategy
implements ReleaseStrategy,
BeanFactoryAware,
ManageableLifecycle {
    private final MethodInvokingMessageListProcessor<Boolean> adapter;

    public MethodInvokingReleaseStrategy(Object object, Method method) {
        this.adapter = new MethodInvokingMessageListProcessor<Boolean>(object, method, Boolean.class);
    }

    public MethodInvokingReleaseStrategy(Object object, String methodName) {
        this.adapter = new MethodInvokingMessageListProcessor<Boolean>(object, methodName, Boolean.class);
    }

    public void setConversionService(ConversionService conversionService) {
        this.adapter.setConversionService(conversionService);
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.adapter.setBeanFactory(beanFactory);
    }

    @Override
    public boolean canRelease(MessageGroup messages) {
        return this.adapter.process(messages.getMessages(), null);
    }

    @Override
    public void start() {
        this.adapter.start();
    }

    @Override
    public void stop() {
        this.adapter.stop();
    }

    @Override
    public boolean isRunning() {
        return this.adapter.isRunning();
    }
}

