/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.core.convert.support.DefaultConversionService
 *  org.springframework.jmx.export.annotation.ManagedResource
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageDeliveryException
 *  org.springframework.messaging.MessagingException
 *  org.springframework.util.Assert
 */
package org.springframework.integration.router;

import java.util.Collection;
import java.util.UUID;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.integration.router.MessageRouter;
import org.springframework.integration.support.management.IntegrationManagedResource;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.MessagingException;
import org.springframework.util.Assert;

@ManagedResource
@IntegrationManagedResource
public abstract class AbstractMessageRouter
extends AbstractMessageHandler
implements MessageRouter {
    private volatile MessageChannel defaultOutputChannel;
    private volatile String defaultOutputChannelName;
    private volatile boolean ignoreSendFailures;
    private volatile boolean applySequence;
    private final MessagingTemplate messagingTemplate = new MessagingTemplate();

    public void setDefaultOutputChannel(MessageChannel defaultOutputChannel) {
        this.defaultOutputChannel = defaultOutputChannel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public MessageChannel getDefaultOutputChannel() {
        if (this.defaultOutputChannelName != null) {
            AbstractMessageRouter abstractMessageRouter = this;
            synchronized (abstractMessageRouter) {
                if (this.defaultOutputChannelName != null) {
                    this.defaultOutputChannel = (MessageChannel)this.getChannelResolver().resolveDestination(this.defaultOutputChannelName);
                    this.defaultOutputChannelName = null;
                }
            }
        }
        return this.defaultOutputChannel;
    }

    public void setDefaultOutputChannelName(String defaultOutputChannelName) {
        Assert.hasText((String)defaultOutputChannelName, (String)"'defaultOutputChannelName' must not be empty");
        this.defaultOutputChannelName = defaultOutputChannelName;
    }

    public void setSendTimeout(long timeout) {
        this.messagingTemplate.setSendTimeout(timeout);
    }

    public void setIgnoreSendFailures(boolean ignoreSendFailures) {
        this.ignoreSendFailures = ignoreSendFailures;
    }

    public void setApplySequence(boolean applySequence) {
        this.applySequence = applySequence;
    }

    @Override
    public String getComponentType() {
        return "router";
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.router;
    }

    protected MessagingTemplate getMessagingTemplate() {
        return this.messagingTemplate;
    }

    protected ConversionService getRequiredConversionService() {
        ConversionService conversionService = this.getConversionService();
        if (conversionService == null) {
            conversionService = DefaultConversionService.getSharedInstance();
            this.setConversionService(conversionService);
        }
        return conversionService;
    }

    @Override
    protected void onInit() {
        super.onInit();
        Assert.state((this.defaultOutputChannelName == null || this.defaultOutputChannel == null ? 1 : 0) != 0, (String)"'defaultOutputChannelName' and 'defaultOutputChannel' are mutually exclusive.");
        BeanFactory beanFactory = this.getBeanFactory();
        if (beanFactory != null) {
            this.messagingTemplate.setBeanFactory(beanFactory);
        }
    }

    protected abstract Collection<MessageChannel> determineTargetChannels(Message<?> var1);

    @Override
    protected void handleMessageInternal(Message<?> message) {
        boolean sent = false;
        Collection<MessageChannel> results = this.determineTargetChannels(message);
        if (results != null) {
            int sequenceSize = results.size();
            int sequenceNumber = 1;
            for (MessageChannel channel : results) {
                Message<?> messageToSend;
                if (!this.applySequence) {
                    messageToSend = message;
                } else {
                    UUID id = message.getHeaders().getId();
                    messageToSend = this.getMessageBuilderFactory().fromMessage(message).pushSequenceDetails(id == null ? AbstractMessageRouter.generateId() : id, sequenceNumber++, sequenceSize).build();
                }
                if (channel == null) continue;
                sent |= this.doSend(channel, messageToSend);
            }
        }
        if (!sent) {
            this.getDefaultOutputChannel();
            if (this.defaultOutputChannel != null) {
                this.messagingTemplate.send(this.defaultOutputChannel, message);
            } else {
                throw new MessageDeliveryException(message, "No channel resolved by router '" + this + "' and no 'defaultOutputChannel' defined.");
            }
        }
    }

    private boolean doSend(MessageChannel channel, Message<?> messageToSend) {
        try {
            this.messagingTemplate.send(channel, messageToSend);
            return true;
        }
        catch (MessagingException ex) {
            if (!this.ignoreSendFailures) {
                throw ex;
            }
            this.logger.debug((Throwable)ex, (CharSequence)"Send failure ignored");
            return false;
        }
    }
}

