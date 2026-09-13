/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.springframework.aop.framework.Advised
 *  org.springframework.beans.factory.BeanCreationException
 *  org.springframework.context.Lifecycle
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessagingException
 *  org.springframework.util.Assert
 */
package org.springframework.integration.endpoint;

import org.reactivestreams.Publisher;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.Lifecycle;
import org.springframework.integration.StaticMessageHeaderAccessor;
import org.springframework.integration.acks.AckUtils;
import org.springframework.integration.acks.AcknowledgmentCallback;
import org.springframework.integration.channel.ReactiveStreamsSubscribableChannel;
import org.springframework.integration.context.ExpressionCapable;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.endpoint.AbstractPollingEndpoint;
import org.springframework.integration.history.MessageHistory;
import org.springframework.integration.support.context.NamedComponent;
import org.springframework.integration.support.management.TrackableComponent;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.util.Assert;

public class SourcePollingChannelAdapter
extends AbstractPollingEndpoint
implements TrackableComponent {
    private final MessagingTemplate messagingTemplate = new MessagingTemplate();
    private MessageSource<?> originalSource;
    private volatile MessageSource<?> source;
    private volatile MessageChannel outputChannel;
    private volatile String outputChannelName;
    private volatile boolean shouldTrack;

    public void setSource(MessageSource<?> source) {
        this.source = source;
        Object target = SourcePollingChannelAdapter.extractProxyTarget(source);
        MessageSource messageSource = this.originalSource = target != null ? (MessageSource)target : source;
        if (source instanceof ExpressionCapable) {
            this.setPrimaryExpression(((ExpressionCapable)((Object)source)).getExpression());
        }
    }

    public void setOutputChannel(MessageChannel outputChannel) {
        this.outputChannel = outputChannel;
    }

    public MessageSource<?> getMessageSource() {
        return this.source;
    }

    public void setOutputChannelName(String outputChannelName) {
        Assert.hasText((String)outputChannelName, (String)"'outputChannelName' must not be empty");
        this.outputChannelName = outputChannelName;
    }

    public void setSendTimeout(long sendTimeout) {
        this.messagingTemplate.setSendTimeout(sendTimeout);
    }

    @Override
    public void setShouldTrack(boolean shouldTrack) {
        this.shouldTrack = shouldTrack;
    }

    @Override
    public String getComponentType() {
        return this.source instanceof NamedComponent ? ((NamedComponent)((Object)this.source)).getComponentType() : "inbound-channel-adapter";
    }

    @Override
    protected boolean isReactive() {
        return this.getOutputChannel() instanceof ReactiveStreamsSubscribableChannel;
    }

    @Override
    protected Object getReceiveMessageSource() {
        return this.getMessageSource();
    }

    @Override
    protected final void setReceiveMessageSource(Object source) {
        this.source = (MessageSource)source;
    }

    @Override
    protected void doStart() {
        if (this.source instanceof Lifecycle) {
            ((Lifecycle)this.source).start();
        }
        super.doStart();
        if (this.isReactive()) {
            ((ReactiveStreamsSubscribableChannel)this.outputChannel).subscribeTo((Publisher<? extends Message<?>>)this.getPollingFlux());
        }
    }

    @Override
    protected void doStop() {
        super.doStop();
        if (this.source instanceof Lifecycle) {
            ((Lifecycle)this.source).stop();
        }
    }

    @Override
    protected void onInit() {
        Assert.notNull(this.source, (String)"source must not be null");
        Assert.state((this.outputChannelName == null && this.outputChannel != null || this.outputChannelName != null && this.outputChannel == null ? 1 : 0) != 0, (String)"One and only one of 'outputChannelName' or 'outputChannel' is required.");
        super.onInit();
        if (this.getBeanFactory() != null) {
            this.messagingTemplate.setBeanFactory(this.getBeanFactory());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public MessageChannel getOutputChannel() {
        if (this.outputChannelName != null) {
            SourcePollingChannelAdapter sourcePollingChannelAdapter = this;
            synchronized (sourcePollingChannelAdapter) {
                if (this.outputChannelName != null) {
                    this.outputChannel = (MessageChannel)this.getChannelResolver().resolveDestination(this.outputChannelName);
                    this.outputChannelName = null;
                }
            }
        }
        return this.outputChannel;
    }

    @Override
    protected void handleMessage(Message<?> messageArg) {
        Message<?> message = messageArg;
        if (this.shouldTrack) {
            message = MessageHistory.write(message, this, this.getMessageBuilderFactory());
        }
        AcknowledgmentCallback ackCallback = StaticMessageHeaderAccessor.getAcknowledgmentCallback(message);
        try {
            this.messagingTemplate.send(this.getOutputChannel(), message);
            AckUtils.autoAck(ackCallback);
        }
        catch (Exception e) {
            AckUtils.autoNack(ackCallback);
            if (e instanceof MessagingException) {
                throw (MessagingException)((Object)e);
            }
            throw new MessagingException(message, "Failed to send Message", (Throwable)e);
        }
    }

    @Override
    protected Message<?> receiveMessage() {
        return this.source.receive();
    }

    @Override
    protected Object getResourceToBind() {
        return this.originalSource;
    }

    @Override
    protected String getResourceKey() {
        return "messageSource";
    }

    private static Object extractProxyTarget(Object target) {
        if (!(target instanceof Advised)) {
            return target;
        }
        Advised advised = (Advised)target;
        try {
            return SourcePollingChannelAdapter.extractProxyTarget(advised.getTargetSource().getTarget());
        }
        catch (Exception e) {
            throw new BeanCreationException("Could not extract target", (Throwable)e);
        }
    }
}

