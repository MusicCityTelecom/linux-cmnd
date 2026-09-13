/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.context.Lifecycle
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.util.Assert
 */
package org.springframework.integration.filter;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.Lifecycle;
import org.springframework.core.convert.ConversionService;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.MessageRejectedException;
import org.springframework.integration.core.MessageSelector;
import org.springframework.integration.filter.AbstractMessageProcessingSelector;
import org.springframework.integration.handler.AbstractReplyProducingPostProcessingMessageHandler;
import org.springframework.integration.handler.DiscardingMessageHandler;
import org.springframework.integration.support.management.ManageableLifecycle;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.util.Assert;

public class MessageFilter
extends AbstractReplyProducingPostProcessingMessageHandler
implements DiscardingMessageHandler,
ManageableLifecycle {
    private final MessageSelector selector;
    private boolean throwExceptionOnRejection;
    private MessageChannel discardChannel;
    private String discardChannelName;

    public MessageFilter(MessageSelector selector) {
        Assert.notNull((Object)selector, (String)"selector must not be null");
        this.selector = selector;
    }

    public void setThrowExceptionOnRejection(boolean throwExceptionOnRejection) {
        this.throwExceptionOnRejection = throwExceptionOnRejection;
    }

    public void setDiscardChannel(MessageChannel discardChannel) {
        this.discardChannel = discardChannel;
    }

    public void setDiscardChannelName(String discardChannelName) {
        Assert.hasText((String)discardChannelName, (String)"'discardChannelName' must not be empty");
        this.discardChannelName = discardChannelName;
    }

    public void setDiscardWithinAdvice(boolean discardWithinAdvice) {
        this.setPostProcessWithinAdvice(discardWithinAdvice);
    }

    @Override
    public MessageChannel getDiscardChannel() {
        String channelName = this.discardChannelName;
        if (channelName != null) {
            this.discardChannel = (MessageChannel)this.getChannelResolver().resolveDestination(channelName);
            this.discardChannelName = null;
        }
        return this.discardChannel;
    }

    @Override
    public String getComponentType() {
        return "filter";
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.filter;
    }

    @Override
    protected void doInit() {
        ConversionService conversionService;
        Assert.state((this.discardChannelName == null || this.discardChannel == null ? 1 : 0) != 0, (String)"'discardChannelName' and 'discardChannel' are mutually exclusive.");
        if (this.selector instanceof AbstractMessageProcessingSelector && (conversionService = this.getConversionService()) != null) {
            ((AbstractMessageProcessingSelector)this.selector).setConversionService(conversionService);
        }
        BeanFactory beanFactory = this.getBeanFactory();
        if (this.selector instanceof BeanFactoryAware && beanFactory != null) {
            ((BeanFactoryAware)this.selector).setBeanFactory(beanFactory);
        }
    }

    @Override
    public void start() {
        if (this.selector instanceof Lifecycle) {
            ((Lifecycle)this.selector).start();
        }
    }

    @Override
    public void stop() {
        if (this.selector instanceof Lifecycle) {
            ((Lifecycle)this.selector).stop();
        }
    }

    @Override
    public boolean isRunning() {
        return !(this.selector instanceof Lifecycle) || ((Lifecycle)this.selector).isRunning();
    }

    @Override
    protected Object doHandleRequestMessage(Message<?> message) {
        if (this.selector.accept(message)) {
            return message;
        }
        return null;
    }

    @Override
    public Object postProcess(Message<?> message, Object result) {
        if (result == null) {
            MessageChannel channel = this.getDiscardChannel();
            if (channel != null) {
                this.messagingTemplate.send(channel, message);
            }
            if (this.throwExceptionOnRejection) {
                throw new MessageRejectedException(message, "message has been rejected in filter: " + this);
            }
        }
        return result;
    }

    @Override
    protected boolean shouldCopyRequestHeaders() {
        return false;
    }
}

