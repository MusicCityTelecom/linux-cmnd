/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.context.Lifecycle
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.splitter;

import java.util.Collection;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.Lifecycle;
import org.springframework.core.convert.ConversionService;
import org.springframework.integration.handler.AbstractMessageProcessor;
import org.springframework.integration.handler.MessageProcessor;
import org.springframework.integration.splitter.AbstractMessageSplitter;
import org.springframework.integration.support.management.ManageableLifecycle;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

abstract class AbstractMessageProcessingSplitter
extends AbstractMessageSplitter
implements ManageableLifecycle {
    private final MessageProcessor<Collection<?>> messageProcessor;

    protected AbstractMessageProcessingSplitter(MessageProcessor<Collection<?>> expressionEvaluatingMessageProcessor) {
        Assert.notNull(expressionEvaluatingMessageProcessor, (String)"messageProcessor must not be null");
        this.messageProcessor = expressionEvaluatingMessageProcessor;
    }

    @Override
    protected void doInit() {
        ConversionService conversionService = this.getConversionService();
        if (conversionService != null && this.messageProcessor instanceof AbstractMessageProcessor) {
            ((AbstractMessageProcessor)this.messageProcessor).setConversionService(conversionService);
        }
        if (this.messageProcessor instanceof BeanFactoryAware && this.getBeanFactory() != null) {
            ((BeanFactoryAware)this.messageProcessor).setBeanFactory(this.getBeanFactory());
        }
    }

    @Override
    protected final Object splitMessage(Message<?> message) {
        return this.messageProcessor.processMessage(message);
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

