/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.context.Lifecycle
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.filter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.Lifecycle;
import org.springframework.core.convert.ConversionService;
import org.springframework.integration.core.MessageSelector;
import org.springframework.integration.handler.AbstractMessageProcessor;
import org.springframework.integration.handler.MessageProcessor;
import org.springframework.integration.support.management.ManageableLifecycle;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public abstract class AbstractMessageProcessingSelector
implements MessageSelector,
BeanFactoryAware,
ManageableLifecycle {
    private final MessageProcessor<Boolean> messageProcessor;

    public AbstractMessageProcessingSelector(MessageProcessor<Boolean> messageProcessor) {
        Assert.notNull(messageProcessor, (String)"messageProcessor must not be null");
        this.messageProcessor = messageProcessor;
    }

    public void setConversionService(ConversionService conversionService) {
        if (this.messageProcessor instanceof AbstractMessageProcessor) {
            ((AbstractMessageProcessor)this.messageProcessor).setConversionService(conversionService);
        }
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (this.messageProcessor instanceof BeanFactoryAware) {
            ((BeanFactoryAware)this.messageProcessor).setBeanFactory(beanFactory);
        }
    }

    @Override
    public final boolean accept(Message<?> message) {
        Boolean result = this.messageProcessor.processMessage(message);
        Assert.notNull((Object)result, (String)"result must not be null");
        Assert.isAssignable(Boolean.class, result.getClass(), (String)"a boolean result is required");
        return result;
    }

    @Override
    public void start() {
        if (this.messageProcessor instanceof Lifecycle) {
            ((Lifecycle)this.messageProcessor).start();
        }
    }

    @Override
    public void stop() {
        if (this.messageProcessor instanceof Lifecycle) {
            ((Lifecycle)this.messageProcessor).stop();
        }
    }

    @Override
    public boolean isRunning() {
        return !(this.messageProcessor instanceof Lifecycle) || ((Lifecycle)this.messageProcessor).isRunning();
    }
}

