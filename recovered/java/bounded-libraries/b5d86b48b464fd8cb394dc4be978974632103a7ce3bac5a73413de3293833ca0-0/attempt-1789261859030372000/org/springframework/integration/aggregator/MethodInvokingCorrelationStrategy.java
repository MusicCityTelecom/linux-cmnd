/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.aggregator;

import java.lang.reflect.Method;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.integration.aggregator.CorrelationStrategy;
import org.springframework.integration.handler.MethodInvokingMessageProcessor;
import org.springframework.integration.support.management.ManageableLifecycle;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class MethodInvokingCorrelationStrategy
implements CorrelationStrategy,
BeanFactoryAware,
ManageableLifecycle {
    private final MethodInvokingMessageProcessor<?> processor;

    public MethodInvokingCorrelationStrategy(Object object, String methodName) {
        this.processor = new MethodInvokingMessageProcessor(object, methodName);
    }

    public MethodInvokingCorrelationStrategy(Object object, Method method) {
        Assert.notNull((Object)object, (String)"'object' must not be null");
        Assert.notNull((Object)method, (String)"'method' must not be null");
        Assert.isTrue((!Void.TYPE.equals(method.getReturnType()) ? 1 : 0) != 0, (String)"Method return type must not be void");
        this.processor = new MethodInvokingMessageProcessor(object, method);
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (beanFactory != null) {
            this.processor.setBeanFactory(beanFactory);
        }
    }

    @Override
    public Object getCorrelationKey(Message<?> message) {
        return this.processor.processMessage(message);
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

