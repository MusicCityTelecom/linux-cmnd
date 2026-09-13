/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.messaging.core;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.core.AbstractDestinationResolvingMessagingTemplate;
import org.springframework.messaging.core.BeanFactoryMessageChannelDestinationResolver;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.Assert;

public class GenericMessagingTemplate
extends AbstractDestinationResolvingMessagingTemplate<MessageChannel>
implements BeanFactoryAware {
    public static final String DEFAULT_SEND_TIMEOUT_HEADER = "sendTimeout";
    public static final String DEFAULT_RECEIVE_TIMEOUT_HEADER = "receiveTimeout";
    private volatile long sendTimeout = -1L;
    private volatile long receiveTimeout = -1L;
    private String sendTimeoutHeader = "sendTimeout";
    private String receiveTimeoutHeader = "receiveTimeout";
    private volatile boolean throwExceptionOnLateReply;

    public void setSendTimeout(long sendTimeout) {
        this.sendTimeout = sendTimeout;
    }

    public long getSendTimeout() {
        return this.sendTimeout;
    }

    public void setReceiveTimeout(long receiveTimeout) {
        this.receiveTimeout = receiveTimeout;
    }

    public long getReceiveTimeout() {
        return this.receiveTimeout;
    }

    public void setSendTimeoutHeader(String sendTimeoutHeader) {
        Assert.notNull((Object)sendTimeoutHeader, (String)"'sendTimeoutHeader' cannot be null");
        this.sendTimeoutHeader = sendTimeoutHeader;
    }

    public String getSendTimeoutHeader() {
        return this.sendTimeoutHeader;
    }

    public void setReceiveTimeoutHeader(String receiveTimeoutHeader) {
        Assert.notNull((Object)receiveTimeoutHeader, (String)"'receiveTimeoutHeader' cannot be null");
        this.receiveTimeoutHeader = receiveTimeoutHeader;
    }

    public String getReceiveTimeoutHeader() {
        return this.receiveTimeoutHeader;
    }

    public void setThrowExceptionOnLateReply(boolean throwExceptionOnLateReply) {
        this.throwExceptionOnLateReply = throwExceptionOnLateReply;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.setDestinationResolver(new BeanFactoryMessageChannelDestinationResolver(beanFactory));
    }

    @Override
    protected final void doSend(MessageChannel channel, Message<?> message) {
        this.doSend(channel, message, this.sendTimeout(message));
    }

    protected final void doSend(MessageChannel channel, Message<?> message, long timeout) {
        boolean sent;
        Assert.notNull((Object)channel, (String)"MessageChannel is required");
        Message<?> messageToSend = message;
        MessageHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, MessageHeaderAccessor.class);
        if (accessor != null && accessor.isMutable()) {
            accessor.removeHeader(this.sendTimeoutHeader);
            accessor.removeHeader(this.receiveTimeoutHeader);
            accessor.setImmutable();
        } else if (message.getHeaders().containsKey(this.sendTimeoutHeader) || message.getHeaders().containsKey(this.receiveTimeoutHeader)) {
            messageToSend = MessageBuilder.fromMessage(message).setHeader(this.sendTimeoutHeader, null).setHeader(this.receiveTimeoutHeader, null).build();
        }
        boolean bl = sent = timeout >= 0L ? channel.send(messageToSend, timeout) : channel.send(messageToSend);
        if (!sent) {
            throw new MessageDeliveryException(message, "Failed to send message to channel '" + channel + "' within timeout: " + timeout);
        }
    }

    @Override
    @Nullable
    protected final Message<?> doReceive(MessageChannel channel) {
        return this.doReceive(channel, this.receiveTimeout);
    }

    @Nullable
    protected final Message<?> doReceive(MessageChannel channel, long timeout) {
        Message<?> message;
        Assert.notNull((Object)channel, (String)"MessageChannel is required");
        Assert.state((boolean)(channel instanceof PollableChannel), (String)"A PollableChannel is required to receive messages");
        Message<?> message2 = message = timeout >= 0L ? ((PollableChannel)channel).receive(timeout) : ((PollableChannel)channel).receive();
        if (message == null && this.logger.isTraceEnabled()) {
            this.logger.trace((Object)("Failed to receive message from channel '" + channel + "' within timeout: " + timeout));
        }
        return message;
    }

    @Override
    @Nullable
    protected final Message<?> doSendAndReceive(MessageChannel channel, Message<?> requestMessage) {
        Assert.notNull((Object)channel, (String)"'channel' is required");
        Object originalReplyChannelHeader = requestMessage.getHeaders().getReplyChannel();
        Object originalErrorChannelHeader = requestMessage.getHeaders().getErrorChannel();
        long sendTimeout = this.sendTimeout(requestMessage);
        long receiveTimeout = this.receiveTimeout(requestMessage);
        TemporaryReplyChannel tempReplyChannel = new TemporaryReplyChannel(this.throwExceptionOnLateReply);
        requestMessage = MessageBuilder.fromMessage(requestMessage).setReplyChannel(tempReplyChannel).setHeader(this.sendTimeoutHeader, null).setHeader(this.receiveTimeoutHeader, null).setErrorChannel(tempReplyChannel).build();
        try {
            this.doSend(channel, requestMessage, sendTimeout);
        }
        catch (RuntimeException ex) {
            tempReplyChannel.setSendFailed(true);
            throw ex;
        }
        Message<?> replyMessage = this.doReceive(tempReplyChannel, receiveTimeout);
        if (replyMessage != null) {
            replyMessage = MessageBuilder.fromMessage(replyMessage).setHeader("replyChannel", originalReplyChannelHeader).setHeader("errorChannel", originalErrorChannelHeader).build();
        }
        return replyMessage;
    }

    private long sendTimeout(Message<?> requestMessage) {
        Long sendTimeout = this.headerToLong(requestMessage.getHeaders().get(this.sendTimeoutHeader));
        return sendTimeout != null ? sendTimeout : this.sendTimeout;
    }

    private long receiveTimeout(Message<?> requestMessage) {
        Long receiveTimeout = this.headerToLong(requestMessage.getHeaders().get(this.receiveTimeoutHeader));
        return receiveTimeout != null ? receiveTimeout : this.receiveTimeout;
    }

    @Nullable
    private Long headerToLong(@Nullable Object headerValue) {
        if (headerValue instanceof Number) {
            return ((Number)headerValue).longValue();
        }
        if (headerValue instanceof String) {
            return Long.parseLong((String)headerValue);
        }
        return null;
    }

    private static final class TemporaryReplyChannel
    implements PollableChannel {
        private final Log logger = LogFactory.getLog(TemporaryReplyChannel.class);
        private final CountDownLatch replyLatch = new CountDownLatch(1);
        private final boolean throwExceptionOnLateReply;
        @Nullable
        private volatile Message<?> replyMessage;
        private volatile boolean hasReceived;
        private volatile boolean hasTimedOut;
        private volatile boolean hasSendFailed;

        TemporaryReplyChannel(boolean throwExceptionOnLateReply) {
            this.throwExceptionOnLateReply = throwExceptionOnLateReply;
        }

        public void setSendFailed(boolean hasSendError) {
            this.hasSendFailed = hasSendError;
        }

        @Override
        @Nullable
        public Message<?> receive() {
            return this.receive(-1L);
        }

        @Override
        @Nullable
        public Message<?> receive(long timeout) {
            try {
                if (timeout < 0L) {
                    this.replyLatch.await();
                    this.hasReceived = true;
                } else if (this.replyLatch.await(timeout, TimeUnit.MILLISECONDS)) {
                    this.hasReceived = true;
                } else {
                    this.hasTimedOut = true;
                }
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            return this.replyMessage;
        }

        @Override
        public boolean send(Message<?> message) {
            return this.send(message, -1L);
        }

        @Override
        public boolean send(Message<?> message, long timeout) {
            this.replyMessage = message;
            boolean alreadyReceivedReply = this.hasReceived;
            this.replyLatch.countDown();
            String errorDescription = null;
            if (this.hasTimedOut) {
                errorDescription = "Reply message received but the receiving thread has exited due to a timeout";
            } else if (alreadyReceivedReply) {
                errorDescription = "Reply message received but the receiving thread has already received a reply";
            } else if (this.hasSendFailed) {
                errorDescription = "Reply message received but the receiving thread has exited due to an exception while sending the request message";
            }
            if (errorDescription != null) {
                if (this.logger.isWarnEnabled()) {
                    this.logger.warn((Object)(errorDescription + ": " + message));
                }
                if (this.throwExceptionOnLateReply) {
                    throw new MessageDeliveryException(message, errorDescription);
                }
            }
            return true;
        }
    }
}

