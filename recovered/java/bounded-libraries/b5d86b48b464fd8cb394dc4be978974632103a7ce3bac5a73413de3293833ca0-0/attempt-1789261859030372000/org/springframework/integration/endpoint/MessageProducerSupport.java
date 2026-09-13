/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscription
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.SmartInitializingSingleton
 *  org.springframework.core.AttributeAccessor
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessagingException
 *  org.springframework.messaging.support.ErrorMessage
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 *  reactor.core.publisher.Flux
 */
package org.springframework.integration.endpoint;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.core.AttributeAccessor;
import org.springframework.integration.IntegrationPattern;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.channel.ReactiveStreamsSubscribableChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.endpoint.AbstractEndpoint;
import org.springframework.integration.history.MessageHistory;
import org.springframework.integration.support.DefaultErrorMessageStrategy;
import org.springframework.integration.support.ErrorMessageStrategy;
import org.springframework.integration.support.ErrorMessageUtils;
import org.springframework.integration.support.management.TrackableComponent;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

public abstract class MessageProducerSupport
extends AbstractEndpoint
implements MessageProducer,
TrackableComponent,
SmartInitializingSingleton,
IntegrationPattern {
    private final MessagingTemplate messagingTemplate = new MessagingTemplate();
    private ErrorMessageStrategy errorMessageStrategy = new DefaultErrorMessageStrategy();
    private MessageChannel outputChannel;
    private String outputChannelName;
    private MessageChannel errorChannel;
    private String errorChannelName;
    private boolean shouldTrack = false;
    private volatile Subscription subscription;

    protected MessageProducerSupport() {
        this.setPhase(0x3FFFFFFF);
    }

    @Override
    public void setOutputChannel(MessageChannel outputChannel) {
        this.outputChannel = outputChannel;
    }

    @Override
    public void setOutputChannelName(String outputChannelName) {
        Assert.hasText((String)outputChannelName, (String)"'outputChannelName' must not be null or empty");
        this.outputChannelName = outputChannelName;
    }

    @Override
    public MessageChannel getOutputChannel() {
        String channelName = this.outputChannelName;
        if (channelName != null) {
            this.outputChannel = (MessageChannel)this.getChannelResolver().resolveDestination(channelName);
            this.outputChannelName = null;
        }
        return this.outputChannel;
    }

    public void setErrorChannel(MessageChannel errorChannel) {
        this.errorChannel = errorChannel;
    }

    public void setErrorChannelName(String errorChannelName) {
        Assert.hasText((String)errorChannelName, (String)"'errorChannelName' must not be empty");
        this.errorChannelName = errorChannelName;
    }

    @Nullable
    public MessageChannel getErrorChannel() {
        String channelName = this.errorChannelName;
        if (channelName != null) {
            this.errorChannel = (MessageChannel)this.getChannelResolver().resolveDestination(channelName);
            this.errorChannelName = null;
        }
        return this.errorChannel;
    }

    public void setSendTimeout(long sendTimeout) {
        this.messagingTemplate.setSendTimeout(sendTimeout);
    }

    @Override
    public void setShouldTrack(boolean shouldTrack) {
        this.shouldTrack = shouldTrack;
    }

    public final void setErrorMessageStrategy(ErrorMessageStrategy errorMessageStrategy) {
        Assert.notNull((Object)errorMessageStrategy, (String)"'errorMessageStrategy' cannot be null");
        this.errorMessageStrategy = errorMessageStrategy;
    }

    protected MessagingTemplate getMessagingTemplate() {
        return this.messagingTemplate;
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.inbound_channel_adapter;
    }

    public void afterSingletonsInstantiated() {
        Assert.state((this.outputChannel != null || StringUtils.hasText((String)this.outputChannelName) ? 1 : 0) != 0, (String)"'outputChannel' or 'outputChannelName' is required");
    }

    @Override
    protected void onInit() {
        super.onInit();
        BeanFactory beanFactory = this.getBeanFactory();
        if (beanFactory != null) {
            this.messagingTemplate.setBeanFactory(beanFactory);
        }
    }

    @Override
    protected void doStart() {
    }

    @Override
    protected void doStop() {
        Subscription subscriptionToCancel = this.subscription;
        if (subscriptionToCancel != null) {
            this.subscription = null;
            subscriptionToCancel.cancel();
        }
    }

    protected void sendMessage(Message<?> messageArg) {
        block3: {
            Message<?> message = messageArg;
            if (message == null) {
                throw new MessagingException("cannot send a null message");
            }
            message = this.trackMessageIfAny(message);
            try {
                this.messagingTemplate.send(this.getRequiredOutputChannel(), message);
            }
            catch (RuntimeException ex) {
                if (this.sendErrorMessageIfNecessary(message, ex)) break block3;
                throw ex;
            }
        }
    }

    protected void subscribeToPublisher(Publisher<? extends Message<?>> publisher) {
        MessageChannel channelForSubscription = this.getRequiredOutputChannel();
        Flux messageFlux = Flux.from(publisher).map(this::trackMessageIfAny).doOnComplete(this::stop).doOnCancel(this::stop).doOnSubscribe(subscription -> {
            this.subscription = subscription;
        });
        if (channelForSubscription instanceof ReactiveStreamsSubscribableChannel) {
            ((ReactiveStreamsSubscribableChannel)channelForSubscription).subscribeTo((Publisher<? extends Message<?>>)messageFlux);
        } else {
            messageFlux.doOnNext(message -> {
                try {
                    this.sendMessage((Message<?>)message);
                }
                catch (Exception ex) {
                    this.logger.error((Throwable)ex, () -> "Error sending a message: " + message);
                }
            }).subscribe();
        }
    }

    protected final boolean sendErrorMessageIfNecessary(@Nullable Message<?> message, Exception exception) {
        MessageChannel channel = this.getErrorChannel();
        if (channel != null) {
            this.messagingTemplate.send(channel, (Message)this.buildErrorMessage(message, exception));
            return true;
        }
        return false;
    }

    protected final ErrorMessage buildErrorMessage(@Nullable Message<?> message, Exception exception) {
        return this.errorMessageStrategy.buildErrorMessage(exception, this.getErrorMessageAttributes(message));
    }

    protected AttributeAccessor getErrorMessageAttributes(@Nullable Message<?> message) {
        return ErrorMessageUtils.getAttributeAccessor(message, null);
    }

    private MessageChannel getRequiredOutputChannel() {
        MessageChannel messageChannel = this.getOutputChannel();
        Assert.state((messageChannel != null ? 1 : 0) != 0, (String)"The 'outputChannel' or `outputChannelName` must be configured");
        return messageChannel;
    }

    private Message<?> trackMessageIfAny(Message<?> message) {
        if (this.shouldTrack) {
            return MessageHistory.write(message, this, this.getMessageBuilderFactory());
        }
        return message;
    }
}

