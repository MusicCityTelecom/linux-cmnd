/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHandlingException
 *  org.springframework.messaging.MessagingException
 *  org.springframework.util.Assert
 */
package org.springframework.integration.aggregator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.aggregator.CorrelationStrategy;
import org.springframework.integration.aggregator.DefaultAggregatingMessageGroupProcessor;
import org.springframework.integration.aggregator.HeaderAttributeCorrelationStrategy;
import org.springframework.integration.aggregator.MessageGroupProcessor;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.integration.handler.DiscardingMessageHandler;
import org.springframework.integration.handler.MessageTriggerAction;
import org.springframework.integration.store.SimpleMessageGroup;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.MessagingException;
import org.springframework.util.Assert;

public class BarrierMessageHandler
extends AbstractReplyProducingMessageHandler
implements MessageTriggerAction,
DiscardingMessageHandler {
    private final Map<Object, SynchronousQueue<Message<?>>> suspensions = new ConcurrentHashMap();
    private final Map<Object, Thread> inProcess = new ConcurrentHashMap<Object, Thread>();
    private final long requestTimeout;
    private final long triggerTimeout;
    private final CorrelationStrategy correlationStrategy;
    private final MessageGroupProcessor messageGroupProcessor;
    private String discardChannelName;
    private MessageChannel discardChannel;

    public BarrierMessageHandler(long timeout) {
        this(timeout, timeout);
    }

    public BarrierMessageHandler(long timeout, MessageGroupProcessor outputProcessor) {
        this(timeout, timeout, outputProcessor);
    }

    public BarrierMessageHandler(long timeout, CorrelationStrategy correlationStrategy) {
        this(timeout, timeout, correlationStrategy);
    }

    public BarrierMessageHandler(long timeout, MessageGroupProcessor outputProcessor, CorrelationStrategy correlationStrategy) {
        this(timeout, timeout, outputProcessor, correlationStrategy);
    }

    public BarrierMessageHandler(long requestTimeout, long triggerTimeout) {
        this(requestTimeout, triggerTimeout, new DefaultAggregatingMessageGroupProcessor());
    }

    public BarrierMessageHandler(long requestTimeout, long triggerTimeout, MessageGroupProcessor outputProcessor) {
        this(requestTimeout, triggerTimeout, outputProcessor, null);
    }

    public BarrierMessageHandler(long requestTimeout, long triggerTimeout, CorrelationStrategy correlationStrategy) {
        this(requestTimeout, triggerTimeout, new DefaultAggregatingMessageGroupProcessor(), correlationStrategy);
    }

    public BarrierMessageHandler(long requestTimeout, long triggerTimeout, MessageGroupProcessor outputProcessor, CorrelationStrategy correlationStrategy) {
        Assert.notNull((Object)outputProcessor, (String)"'messageGroupProcessor' cannot be null");
        this.messageGroupProcessor = outputProcessor;
        this.correlationStrategy = correlationStrategy == null ? new HeaderAttributeCorrelationStrategy("correlationId") : correlationStrategy;
        this.requestTimeout = requestTimeout;
        this.triggerTimeout = triggerTimeout;
    }

    public void setDiscardChannelName(String discardChannelName) {
        this.discardChannelName = discardChannelName;
    }

    public void setDiscardChannel(MessageChannel discardChannel) {
        this.discardChannel = discardChannel;
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
        return "barrier";
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.barrier;
    }

    @Override
    protected Object handleRequestMessage(Message<?> requestMessage) {
        Object key = this.correlationStrategy.getCorrelationKey(requestMessage);
        if (key == null) {
            throw new MessagingException(requestMessage, "Correlation Strategy returned null");
        }
        Thread existing = this.inProcess.putIfAbsent(key, Thread.currentThread());
        if (existing != null) {
            throw new MessagingException(requestMessage, "Correlation key (" + key + ") is already in use by " + existing.getName());
        }
        SynchronousQueue<Message<?>> syncQueue = this.createOrObtainQueue(key);
        try {
            Message<?> releaseMessage = syncQueue.poll(this.requestTimeout, TimeUnit.MILLISECONDS);
            if (releaseMessage != null) {
                Object object = this.processRelease(key, requestMessage, releaseMessage);
                return object;
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new MessageHandlingException(requestMessage, "Interrupted while waiting for release in the [" + this + ']', (Throwable)e);
        }
        finally {
            this.inProcess.remove(key);
            this.suspensions.remove(key);
        }
        return null;
    }

    private Object processRelease(Object key, Message<?> requestMessage, Message<?> releaseMessage) {
        this.suspensions.remove(key);
        if (releaseMessage.getPayload() instanceof Throwable) {
            throw new MessagingException(requestMessage, "Releasing flow returned a throwable", (Throwable)releaseMessage.getPayload());
        }
        return this.buildResult(key, requestMessage, releaseMessage);
    }

    protected Object buildResult(Object key, Message<?> requestMessage, Message<?> releaseMessage) {
        SimpleMessageGroup group = new SimpleMessageGroup(key);
        group.add(requestMessage);
        group.add(releaseMessage);
        return this.messageGroupProcessor.processMessageGroup(group);
    }

    private SynchronousQueue<Message<?>> createOrObtainQueue(Object key) {
        SynchronousQueue syncQueue = new SynchronousQueue();
        SynchronousQueue existing = this.suspensions.putIfAbsent(key, syncQueue);
        if (existing != null) {
            syncQueue = existing;
        }
        return syncQueue;
    }

    @Override
    public void trigger(Message<?> message) {
        Object key = this.correlationStrategy.getCorrelationKey(message);
        if (key == null) {
            throw new MessagingException(message, "Correlation Strategy returned null");
        }
        SynchronousQueue<Message<?>> syncQueue = this.createOrObtainQueue(key);
        try {
            if (!syncQueue.offer(message, this.triggerTimeout, TimeUnit.MILLISECONDS)) {
                this.logger.error((CharSequence)("Suspending thread timed out or did not arrive within timeout for: " + message));
                this.suspensions.remove(key);
                MessageChannel messageChannel = this.getDiscardChannel();
                if (messageChannel != null) {
                    this.messagingTemplate.send(messageChannel, message);
                }
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            this.logger.error((CharSequence)("Interrupted while waiting for the suspending thread for: " + message));
            this.suspensions.remove(key);
        }
    }
}

