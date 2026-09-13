/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.jmx.export.annotation.ManagedAttribute
 *  org.springframework.jmx.export.annotation.ManagedOperation
 *  org.springframework.jmx.export.annotation.ManagedResource
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.support.ChannelInterceptor
 *  org.springframework.messaging.support.InterceptableChannel
 *  org.springframework.util.Assert
 */
package org.springframework.integration.channel.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.integration.channel.interceptor.VetoCapableInterceptor;
import org.springframework.integration.core.MessageSelector;
import org.springframework.integration.support.channel.ChannelResolverUtils;
import org.springframework.integration.support.management.ManageableLifecycle;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.InterceptableChannel;
import org.springframework.util.Assert;

@ManagedResource
public class WireTap
implements ChannelInterceptor,
ManageableLifecycle,
VetoCapableInterceptor,
BeanFactoryAware {
    private static final Log LOGGER = LogFactory.getLog(WireTap.class);
    private final MessageSelector selector;
    private MessageChannel channel;
    private String channelName;
    private long timeout = 0L;
    private BeanFactory beanFactory;
    private volatile boolean running = true;

    public WireTap(MessageChannel channel) {
        this(channel, null);
    }

    public WireTap(MessageChannel channel, MessageSelector selector) {
        Assert.notNull((Object)channel, (String)"channel must not be null");
        this.channel = channel;
        this.selector = selector;
    }

    public WireTap(String channelName) {
        this(channelName, null);
    }

    public WireTap(String channelName, MessageSelector selector) {
        Assert.hasText((String)channelName, (String)"channelName must not be empty");
        this.channelName = channelName;
        this.selector = selector;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (this.beanFactory == null) {
            this.beanFactory = beanFactory;
        }
    }

    @Override
    @ManagedAttribute
    public boolean isRunning() {
        return this.running;
    }

    @Override
    @ManagedOperation
    public void start() {
        this.running = true;
    }

    @Override
    @ManagedOperation
    public void stop() {
        this.running = false;
    }

    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        MessageChannel wireTapChannel = this.getChannel();
        if (wireTapChannel.equals(channel)) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)("WireTap is refusing to intercept its own channel '" + wireTapChannel + "'"));
            }
            return message;
        }
        if (this.running && (this.selector == null || this.selector.accept(message))) {
            boolean sent;
            boolean bl = sent = this.timeout >= 0L ? wireTapChannel.send(message, this.timeout) : wireTapChannel.send(message);
            if (!sent && LOGGER.isWarnEnabled()) {
                LOGGER.warn((Object)("failed to send message to WireTap channel '" + wireTapChannel + "'"));
            }
        }
        return message;
    }

    @Override
    public boolean shouldIntercept(String beanName, InterceptableChannel channel) {
        return !this.getChannel().equals(channel);
    }

    private MessageChannel getChannel() {
        String channelNameToUse = this.channelName;
        if (channelNameToUse != null) {
            this.channel = (MessageChannel)ChannelResolverUtils.getChannelResolver(this.beanFactory).resolveDestination(channelNameToUse);
            this.channelName = null;
        }
        return this.channel;
    }
}

