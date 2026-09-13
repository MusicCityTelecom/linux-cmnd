/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.aop.support.AopUtils
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanInitializationException
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageDeliveryException
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.messaging.MessageHeaders
 *  org.springframework.messaging.PollableChannel
 *  org.springframework.messaging.SubscribableChannel
 *  org.springframework.messaging.support.ChannelInterceptor
 *  org.springframework.messaging.support.InterceptableChannel
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 */
package org.springframework.integration.scattergather;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.channel.FixedSubscriberChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.channel.ReactiveStreamsSubscribableChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.endpoint.AbstractEndpoint;
import org.springframework.integration.endpoint.EventDrivenConsumer;
import org.springframework.integration.endpoint.PollingConsumer;
import org.springframework.integration.endpoint.ReactiveStreamsConsumer;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.integration.support.management.ManageableLifecycle;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.InterceptableChannel;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

public class ScatterGatherHandler
extends AbstractReplyProducingMessageHandler
implements ManageableLifecycle {
    private static final String GATHER_RESULT_CHANNEL = "gatherResultChannel";
    private static final String ORIGINAL_ERROR_CHANNEL = "originalErrorChannel";
    private final MessageChannel scatterChannel;
    private final MessageHandler gatherer;
    private MessageChannel gatherChannel;
    private String errorChannelName = "errorChannel";
    private long gatherTimeout = -1L;
    private AbstractEndpoint gatherEndpoint;

    public ScatterGatherHandler(MessageHandler scatterer, MessageHandler gatherer) {
        this((MessageChannel)new FixedSubscriberChannel(scatterer), gatherer);
        Assert.notNull((Object)scatterer, (String)"'scatterer' must not be null");
        Class scattererClass = AopUtils.getTargetClass((Object)scatterer);
        ScatterGatherHandler.checkClass(scattererClass, "org.springframework.integration.router.RecipientListRouter", "scatterer");
    }

    public ScatterGatherHandler(MessageChannel scatterChannel, MessageHandler gatherer) {
        Assert.notNull((Object)scatterChannel, (String)"'scatterChannel' must not be null");
        Assert.notNull((Object)gatherer, (String)"'gatherer' must not be null");
        Class gathererClass = AopUtils.getTargetClass((Object)gatherer);
        ScatterGatherHandler.checkClass(gathererClass, "org.springframework.integration.aggregator.AggregatingMessageHandler", "gatherer");
        this.scatterChannel = scatterChannel;
        this.gatherer = gatherer;
    }

    public void setGatherChannel(MessageChannel gatherChannel) {
        this.gatherChannel = gatherChannel;
    }

    public void setGatherTimeout(long gatherTimeout) {
        this.gatherTimeout = gatherTimeout;
    }

    public void setErrorChannelName(String errorChannelName) {
        Assert.hasText((String)errorChannelName, (String)"'errorChannelName' must not be empty.");
        this.errorChannelName = errorChannelName;
    }

    @Override
    public String getComponentType() {
        return "scatter-gather";
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.scatter_gather;
    }

    @Override
    protected void doInit() {
        BeanFactory beanFactory = this.getBeanFactory();
        if (this.gatherChannel == null) {
            this.gatherChannel = new FixedSubscriberChannel(message -> this.gatherer.handleMessage(this.enhanceScatterReplyMessage(message)));
        } else {
            Assert.isInstanceOf(InterceptableChannel.class, (Object)this.gatherChannel, () -> "An injected 'gatherChannel' '" + this.gatherChannel + "' must be an 'InterceptableChannel' instance.");
            ((InterceptableChannel)this.gatherChannel).addInterceptor(0, new ChannelInterceptor(){

                public Message<?> preSend(Message<?> message, MessageChannel channel) {
                    return ScatterGatherHandler.this.enhanceScatterReplyMessage(message);
                }
            });
            if (this.gatherChannel instanceof SubscribableChannel) {
                this.gatherEndpoint = new EventDrivenConsumer((SubscribableChannel)this.gatherChannel, this.gatherer);
            } else if (this.gatherChannel instanceof PollableChannel) {
                this.gatherEndpoint = new PollingConsumer((PollableChannel)this.gatherChannel, this.gatherer);
                ((PollingConsumer)this.gatherEndpoint).setReceiveTimeout(this.gatherTimeout);
            } else if (this.gatherChannel instanceof ReactiveStreamsSubscribableChannel) {
                this.gatherEndpoint = new ReactiveStreamsConsumer(this.gatherChannel, this.gatherer);
            } else {
                throw new BeanInitializationException("Unsupported 'gatherChannel' type '" + this.gatherChannel.getClass() + "'. 'SubscribableChannel', 'PollableChannel' or 'ReactiveStreamsSubscribableChannel' types are supported.");
            }
            this.gatherEndpoint.setBeanFactory(beanFactory);
            this.gatherEndpoint.afterPropertiesSet();
        }
        ((MessageProducer)this.gatherer).setOutputChannel((MessageChannel)new FixedSubscriberChannel(message -> {
            MessageHeaders headers = message.getHeaders();
            MessageChannel gatherResultChannel = (MessageChannel)headers.get((Object)GATHER_RESULT_CHANNEL, MessageChannel.class);
            if (gatherResultChannel == null) {
                throw new MessageDeliveryException(message, "The 'gatherResultChannel' header is required to deliver the gather result.");
            }
            this.messagingTemplate.send(gatherResultChannel, message);
        }));
    }

    private Message<?> enhanceScatterReplyMessage(Message<?> message) {
        MessageHeaders headers = message.getHeaders();
        return this.getMessageBuilderFactory().fromMessage(message).setHeader("errorChannel", headers.get((Object)ORIGINAL_ERROR_CHANNEL)).build();
    }

    @Override
    protected Object handleRequestMessage(Message<?> requestMessage) {
        MessageHeaders requestMessageHeaders = requestMessage.getHeaders();
        QueueChannel gatherResultChannel = new QueueChannel();
        Message<?> scatterMessage = this.getMessageBuilderFactory().fromMessage(requestMessage).setHeader(GATHER_RESULT_CHANNEL, gatherResultChannel).setHeader(ORIGINAL_ERROR_CHANNEL, requestMessageHeaders.getErrorChannel()).setReplyChannel(this.gatherChannel).setErrorChannelName(this.errorChannelName).build();
        this.messagingTemplate.send(this.scatterChannel, scatterMessage);
        Message gatherResult = gatherResultChannel.receive(this.gatherTimeout);
        if (gatherResult != null) {
            return this.getMessageBuilderFactory().fromMessage(gatherResult).removeHeaders(GATHER_RESULT_CHANNEL, ORIGINAL_ERROR_CHANNEL, "replyChannel", "errorChannel");
        }
        return null;
    }

    @Override
    public void start() {
        if (this.gatherEndpoint != null) {
            this.gatherEndpoint.start();
        }
    }

    @Override
    public void stop() {
        if (this.gatherEndpoint != null) {
            this.gatherEndpoint.stop();
        }
    }

    @Override
    public boolean isRunning() {
        return this.gatherEndpoint == null || this.gatherEndpoint.isRunning();
    }

    private static void checkClass(Class<?> gathererClass, String className, String type) throws LinkageError {
        try {
            Class clazz = ClassUtils.forName((String)className, (ClassLoader)ClassUtils.getDefaultClassLoader());
            Assert.isAssignable((Class)clazz, gathererClass, () -> "the '" + type + "' must be an " + className + " instance");
        }
        catch (ClassNotFoundException e) {
            throw new IllegalStateException("The class for '" + className + "' cannot be loaded", e);
        }
    }
}

