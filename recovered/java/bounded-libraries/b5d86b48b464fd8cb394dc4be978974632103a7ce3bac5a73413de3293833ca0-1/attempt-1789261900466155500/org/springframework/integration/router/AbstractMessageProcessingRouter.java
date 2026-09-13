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
package org.springframework.integration.router;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.Lifecycle;
import org.springframework.core.convert.ConversionService;
import org.springframework.integration.handler.AbstractMessageProcessor;
import org.springframework.integration.handler.MessageProcessor;
import org.springframework.integration.router.AbstractMappingMessageRouter;
import org.springframework.integration.support.management.ManageableLifecycle;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

class AbstractMessageProcessingRouter
extends AbstractMappingMessageRouter
implements ManageableLifecycle {
    private final MessageProcessor<?> messageProcessor;

    AbstractMessageProcessingRouter(MessageProcessor<?> messageProcessor) {
        Assert.notNull(messageProcessor, (String)"messageProcessor must not be null");
        this.messageProcessor = messageProcessor;
    }

    @Override
    public final void onInit() {
        ConversionService conversionService;
        super.onInit();
        if (this.messageProcessor instanceof AbstractMessageProcessor && (conversionService = this.getConversionService()) != null) {
            ((AbstractMessageProcessor)this.messageProcessor).setConversionService(conversionService);
        }
        if (this.messageProcessor instanceof BeanFactoryAware && this.getBeanFactory() != null) {
            ((BeanFactoryAware)this.messageProcessor).setBeanFactory(this.getBeanFactory());
        }
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

    @Override
    protected List<Object> getChannelKeys(Message<?> message) {
        Object result = this.messageProcessor.processMessage(message);
        return Collections.singletonList(result);
    }
}

