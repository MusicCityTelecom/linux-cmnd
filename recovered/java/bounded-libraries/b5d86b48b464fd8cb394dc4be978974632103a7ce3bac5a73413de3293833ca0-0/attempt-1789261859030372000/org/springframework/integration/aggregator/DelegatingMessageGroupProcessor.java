/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.context.Lifecycle
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.aggregator;

import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.Lifecycle;
import org.springframework.integration.aggregator.MessageGroupProcessor;
import org.springframework.integration.store.MessageGroup;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.DefaultMessageBuilderFactory;
import org.springframework.integration.support.MessageBuilderFactory;
import org.springframework.integration.support.management.ManageableLifecycle;
import org.springframework.integration.support.utils.IntegrationUtils;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class DelegatingMessageGroupProcessor
implements MessageGroupProcessor,
BeanFactoryAware,
ManageableLifecycle {
    private final MessageGroupProcessor delegate;
    private final Function<MessageGroup, Map<String, Object>> headersFunction;
    private MessageBuilderFactory messageBuilderFactory = new DefaultMessageBuilderFactory();
    private volatile boolean messageBuilderFactorySet;
    private BeanFactory beanFactory;

    public DelegatingMessageGroupProcessor(MessageGroupProcessor delegate, Function<MessageGroup, Map<String, Object>> headersFunction) {
        Assert.notNull((Object)delegate, (String)"'delegate' must not be null");
        Assert.notNull(headersFunction, (String)"'headersFunction' must not be null");
        this.delegate = delegate;
        this.headersFunction = headersFunction;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        if (this.delegate instanceof BeanFactoryAware) {
            ((BeanFactoryAware)this.delegate).setBeanFactory(beanFactory);
        }
    }

    @Override
    public Object processMessageGroup(MessageGroup group) {
        AbstractIntegrationMessageBuilder<Object> result = this.delegate.processMessageGroup(group);
        if (!(result instanceof Message) && !(result instanceof AbstractIntegrationMessageBuilder)) {
            result = this.getMessageBuilderFactory().withPayload(result).copyHeadersIfAbsent(this.headersFunction.apply(group));
        }
        return result;
    }

    private MessageBuilderFactory getMessageBuilderFactory() {
        if (!this.messageBuilderFactorySet) {
            if (this.beanFactory != null) {
                this.messageBuilderFactory = IntegrationUtils.getMessageBuilderFactory(this.beanFactory);
            }
            this.messageBuilderFactorySet = true;
        }
        return this.messageBuilderFactory;
    }

    @Override
    public void start() {
        if (this.delegate instanceof Lifecycle) {
            ((Lifecycle)this.delegate).start();
        }
    }

    @Override
    public void stop() {
        if (this.delegate instanceof Lifecycle) {
            ((Lifecycle)this.delegate).stop();
        }
    }

    @Override
    public boolean isRunning() {
        return this.delegate instanceof Lifecycle && ((Lifecycle)this.delegate).isRunning();
    }
}

