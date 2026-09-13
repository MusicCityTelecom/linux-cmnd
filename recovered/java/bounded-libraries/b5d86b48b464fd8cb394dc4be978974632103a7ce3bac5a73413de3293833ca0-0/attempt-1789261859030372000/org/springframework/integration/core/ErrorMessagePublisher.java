/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.core.AttributeAccessor
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessagingException
 *  org.springframework.messaging.core.DestinationResolver
 *  org.springframework.messaging.support.ErrorMessage
 *  org.springframework.util.Assert
 */
package org.springframework.integration.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.core.AttributeAccessor;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.DefaultErrorMessageStrategy;
import org.springframework.integration.support.ErrorMessageStrategy;
import org.springframework.integration.support.ErrorMessageUtils;
import org.springframework.integration.support.channel.ChannelResolverUtils;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.core.DestinationResolver;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.util.Assert;

public class ErrorMessagePublisher
implements BeanFactoryAware {
    protected final Log logger = LogFactory.getLog(this.getClass());
    protected final MessagingTemplate messagingTemplate = new MessagingTemplate();
    private DestinationResolver<MessageChannel> channelResolver;
    private MessageChannel channel;
    private String channelName;
    private ErrorMessageStrategy errorMessageStrategy = new DefaultErrorMessageStrategy();

    public final void setErrorMessageStrategy(ErrorMessageStrategy errorMessageStrategy) {
        Assert.notNull((Object)errorMessageStrategy, (String)"'errorMessageStrategy' must not be null");
        this.errorMessageStrategy = errorMessageStrategy;
    }

    public final void setChannel(MessageChannel channel) {
        this.channel = channel;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public ErrorMessageStrategy getErrorMessageStrategy() {
        return this.errorMessageStrategy;
    }

    public MessageChannel getChannel() {
        this.populateChannel();
        return this.channel;
    }

    public final void setSendTimeout(long sendTimeout) {
        this.messagingTemplate.setSendTimeout(sendTimeout);
    }

    public final void setChannelResolver(DestinationResolver<MessageChannel> channelResolver) {
        Assert.notNull(channelResolver, (String)"channelResolver must not be null");
        this.channelResolver = channelResolver;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        Assert.notNull((Object)beanFactory, (String)"beanFactory must not be null");
        if (this.channelResolver == null) {
            this.channelResolver = ChannelResolverUtils.getChannelResolver(beanFactory);
        }
    }

    protected MessagingTemplate getMessagingTemplate() {
        return this.messagingTemplate;
    }

    @Nullable
    protected DestinationResolver<MessageChannel> getChannelResolver() {
        return this.channelResolver;
    }

    public void publish(MessagingException exception) {
        this.publish(null, exception.getFailedMessage(), exception);
    }

    public void publish(Message<?> failedMessage, Throwable throwable) {
        this.publish(null, failedMessage, throwable);
    }

    public void publish(Message<?> inputMessage, MessagingException exception) {
        this.publish(inputMessage, exception.getFailedMessage(), exception);
    }

    public void publish(@Nullable Message<?> inputMessage, Message<?> failedMessage, Throwable throwable) {
        this.publish(throwable, ErrorMessageUtils.getAttributeAccessor(inputMessage, failedMessage));
    }

    public void publish(Throwable throwable, AttributeAccessor context) {
        this.populateChannel();
        Throwable payload = this.determinePayload(throwable, context);
        ErrorMessage errorMessage = this.errorMessageStrategy.buildErrorMessage(payload, context);
        if (this.logger.isDebugEnabled() && payload instanceof MessagingException) {
            MessagingException exception = (MessagingException)((Object)errorMessage.getPayload());
            this.logger.debug((Object)("Sending ErrorMessage: failedMessage: " + exception.getFailedMessage()), (Throwable)exception);
        }
        this.messagingTemplate.send((Message)errorMessage);
    }

    protected Throwable determinePayload(Throwable throwable, AttributeAccessor context) {
        Throwable lastThrowable = throwable;
        if (lastThrowable == null) {
            lastThrowable = this.payloadWhenNull(context);
        } else if (!(lastThrowable instanceof MessagingException)) {
            Message message = (Message)context.getAttribute("message");
            lastThrowable = message == null ? new MessagingException(lastThrowable.getMessage(), lastThrowable) : new MessagingException(message, lastThrowable.getMessage(), lastThrowable);
        }
        return lastThrowable;
    }

    protected Throwable payloadWhenNull(AttributeAccessor context) {
        Message message = (Message)context.getAttribute("message");
        return message == null ? new MessagingException("No root cause exception available") : new MessagingException(message, "No root cause exception available");
    }

    private void populateChannel() {
        if (this.messagingTemplate.getDefaultDestination() == null) {
            if (this.channel == null) {
                String recoveryChannelName = this.channelName;
                if (recoveryChannelName == null) {
                    recoveryChannelName = "errorChannel";
                }
                if (this.channelResolver != null) {
                    this.channel = (MessageChannel)this.channelResolver.resolveDestination(recoveryChannelName);
                }
            }
            this.messagingTemplate.setDefaultChannel(this.channel);
        }
    }
}

