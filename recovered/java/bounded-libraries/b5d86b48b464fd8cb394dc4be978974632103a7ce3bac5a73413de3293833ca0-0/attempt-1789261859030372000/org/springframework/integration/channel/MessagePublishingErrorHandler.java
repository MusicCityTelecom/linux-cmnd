/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessagingException
 *  org.springframework.messaging.core.DestinationResolver
 *  org.springframework.messaging.support.ErrorMessage
 *  org.springframework.util.Assert
 *  org.springframework.util.ErrorHandler
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.channel;

import org.springframework.integration.core.ErrorMessagePublisher;
import org.springframework.integration.support.ErrorMessageStrategy;
import org.springframework.integration.support.MessagingExceptionWrapper;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.core.DestinationResolver;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.util.Assert;
import org.springframework.util.ErrorHandler;
import org.springframework.util.StringUtils;

public class MessagePublishingErrorHandler
extends ErrorMessagePublisher
implements ErrorHandler {
    private static final int DEFAULT_SEND_TIMEOUT = 1000;
    private static final ErrorMessageStrategy DEFAULT_ERROR_MESSAGE_STRATEGY = (ex, attrs) -> {
        if (ex instanceof MessagingExceptionWrapper) {
            return new ErrorMessage(ex.getCause(), ((MessagingExceptionWrapper)((Object)((Object)ex))).getFailedMessage());
        }
        return new ErrorMessage(ex);
    };

    public MessagePublishingErrorHandler() {
        this.setErrorMessageStrategy(DEFAULT_ERROR_MESSAGE_STRATEGY);
        this.setSendTimeout(1000L);
    }

    public MessagePublishingErrorHandler(DestinationResolver<MessageChannel> channelResolver) {
        this();
        this.setChannelResolver(channelResolver);
    }

    public void setDefaultErrorChannel(@Nullable MessageChannel defaultErrorChannel) {
        this.setChannel(defaultErrorChannel);
    }

    @Nullable
    public MessageChannel getDefaultErrorChannel() {
        return this.getChannel();
    }

    public void setDefaultErrorChannelName(String defaultErrorChannelName) {
        this.setChannelName(defaultErrorChannelName);
    }

    public final void handleError(Throwable ex) {
        boolean sent;
        block4: {
            MessageChannel errorChannel = this.resolveErrorChannel(ex);
            sent = false;
            if (errorChannel != null) {
                try {
                    this.getMessagingTemplate().send(errorChannel, (Message)this.getErrorMessageStrategy().buildErrorMessage(ex, null));
                    sent = true;
                }
                catch (Exception errorDeliveryError) {
                    if (!this.logger.isWarnEnabled()) break block4;
                    this.logger.warn((Object)"Error message was not delivered.", (Throwable)errorDeliveryError);
                }
            }
        }
        if (!sent && this.logger.isErrorEnabled()) {
            Message failedMessage = ex instanceof MessagingException ? ((MessagingException)ex).getFailedMessage() : null;
            this.logger.error((Object)("failure occurred in messaging task" + (failedMessage != null ? " with message: " + failedMessage : "")), ex);
        }
    }

    @Nullable
    private MessageChannel resolveErrorChannel(Throwable t) {
        Message failedMessage;
        DestinationResolver<MessageChannel> channelResolver = this.getChannelResolver();
        Throwable actualThrowable = t;
        if (t instanceof MessagingExceptionWrapper) {
            actualThrowable = t.getCause();
        }
        Message message = failedMessage = actualThrowable instanceof MessagingException ? ((MessagingException)actualThrowable).getFailedMessage() : null;
        if (this.getDefaultErrorChannel() == null && channelResolver != null) {
            this.setChannel((MessageChannel)channelResolver.resolveDestination("errorChannel"));
        }
        if (failedMessage != null && failedMessage.getHeaders().getErrorChannel() != null) {
            Object errorChannelHeader = failedMessage.getHeaders().getErrorChannel();
            if (errorChannelHeader instanceof MessageChannel) {
                return (MessageChannel)errorChannelHeader;
            }
            Assert.isInstanceOf(String.class, (Object)errorChannelHeader, () -> "Unsupported error channel header type. Expected MessageChannel or String, but actual type is [" + errorChannelHeader.getClass() + "]");
            if (channelResolver != null && StringUtils.hasText((String)((String)errorChannelHeader))) {
                return (MessageChannel)channelResolver.resolveDestination((String)errorChannelHeader);
            }
        }
        return this.getDefaultErrorChannel();
    }
}

