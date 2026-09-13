/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.ApplicationEventPublisher
 *  org.springframework.context.ApplicationEventPublisherAware
 *  org.springframework.context.SmartLifecycle
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.messaging.simp.broker;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import org.apache.commons.logging.Log;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.SmartLifecycle;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.simp.SimpLogging;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.messaging.simp.broker.OrderedMessageChannelDecorator;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.InterceptableChannel;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public abstract class AbstractBrokerMessageHandler
implements MessageHandler,
ApplicationEventPublisherAware,
SmartLifecycle {
    protected final Log logger = SimpLogging.forLogName(this.getClass());
    private final SubscribableChannel clientInboundChannel;
    private final MessageChannel clientOutboundChannel;
    private final SubscribableChannel brokerChannel;
    private final Collection<String> destinationPrefixes;
    @Nullable
    private Predicate<String> userDestinationPredicate;
    private boolean preservePublishOrder = false;
    @Nullable
    private ApplicationEventPublisher eventPublisher;
    private AtomicBoolean brokerAvailable = new AtomicBoolean();
    private final BrokerAvailabilityEvent availableEvent = new BrokerAvailabilityEvent(true, this);
    private final BrokerAvailabilityEvent notAvailableEvent = new BrokerAvailabilityEvent(false, this);
    private boolean autoStartup = true;
    private volatile boolean running;
    private final Object lifecycleMonitor = new Object();
    private final ChannelInterceptor unsentDisconnectInterceptor = new UnsentDisconnectChannelInterceptor();

    public AbstractBrokerMessageHandler(SubscribableChannel inboundChannel, MessageChannel outboundChannel, SubscribableChannel brokerChannel) {
        this(inboundChannel, outboundChannel, brokerChannel, Collections.emptyList());
    }

    public AbstractBrokerMessageHandler(SubscribableChannel inboundChannel, MessageChannel outboundChannel, SubscribableChannel brokerChannel, @Nullable Collection<String> destinationPrefixes) {
        Assert.notNull((Object)inboundChannel, (String)"'inboundChannel' must not be null");
        Assert.notNull((Object)outboundChannel, (String)"'outboundChannel' must not be null");
        Assert.notNull((Object)brokerChannel, (String)"'brokerChannel' must not be null");
        this.clientInboundChannel = inboundChannel;
        this.clientOutboundChannel = outboundChannel;
        this.brokerChannel = brokerChannel;
        destinationPrefixes = destinationPrefixes != null ? destinationPrefixes : Collections.emptyList();
        this.destinationPrefixes = Collections.unmodifiableCollection(destinationPrefixes);
    }

    public SubscribableChannel getClientInboundChannel() {
        return this.clientInboundChannel;
    }

    public MessageChannel getClientOutboundChannel() {
        return this.clientOutboundChannel;
    }

    public SubscribableChannel getBrokerChannel() {
        return this.brokerChannel;
    }

    public Collection<String> getDestinationPrefixes() {
        return this.destinationPrefixes;
    }

    public void setUserDestinationPredicate(@Nullable Predicate<String> predicate) {
        this.userDestinationPredicate = predicate;
    }

    public void setPreservePublishOrder(boolean preservePublishOrder) {
        OrderedMessageChannelDecorator.configureInterceptor(this.clientOutboundChannel, preservePublishOrder);
        this.preservePublishOrder = preservePublishOrder;
    }

    public boolean isPreservePublishOrder() {
        return this.preservePublishOrder;
    }

    public void setApplicationEventPublisher(@Nullable ApplicationEventPublisher publisher) {
        this.eventPublisher = publisher;
    }

    @Nullable
    public ApplicationEventPublisher getApplicationEventPublisher() {
        return this.eventPublisher;
    }

    public void setAutoStartup(boolean autoStartup) {
        this.autoStartup = autoStartup;
    }

    public boolean isAutoStartup() {
        return this.autoStartup;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void start() {
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            this.logger.info((Object)"Starting...");
            this.clientInboundChannel.subscribe(this);
            this.brokerChannel.subscribe(this);
            if (this.clientInboundChannel instanceof InterceptableChannel) {
                ((InterceptableChannel)((Object)this.clientInboundChannel)).addInterceptor(0, this.unsentDisconnectInterceptor);
            }
            this.startInternal();
            this.running = true;
            this.logger.info((Object)"Started.");
        }
    }

    protected void startInternal() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void stop() {
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            this.logger.info((Object)"Stopping...");
            this.stopInternal();
            this.clientInboundChannel.unsubscribe(this);
            this.brokerChannel.unsubscribe(this);
            if (this.clientInboundChannel instanceof InterceptableChannel) {
                ((InterceptableChannel)((Object)this.clientInboundChannel)).removeInterceptor(this.unsentDisconnectInterceptor);
            }
            this.running = false;
            this.logger.info((Object)"Stopped.");
        }
    }

    protected void stopInternal() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void stop(Runnable callback) {
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            this.stop();
            callback.run();
        }
    }

    public final boolean isRunning() {
        return this.running;
    }

    public boolean isBrokerAvailable() {
        return this.brokerAvailable.get();
    }

    @Override
    public void handleMessage(Message<?> message) {
        if (!this.running) {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace((Object)(this + " not running yet. Ignoring " + message));
            }
            return;
        }
        this.handleMessageInternal(message);
    }

    protected abstract void handleMessageInternal(Message<?> var1);

    protected boolean checkDestinationPrefix(@Nullable String destination) {
        if (destination == null) {
            return true;
        }
        if (CollectionUtils.isEmpty(this.destinationPrefixes)) {
            return !this.isUserDestination(destination);
        }
        for (String prefix : this.destinationPrefixes) {
            if (!destination.startsWith(prefix)) continue;
            return true;
        }
        return false;
    }

    private boolean isUserDestination(String destination) {
        return this.userDestinationPredicate != null && this.userDestinationPredicate.test(destination);
    }

    protected void publishBrokerAvailableEvent() {
        boolean shouldPublish = this.brokerAvailable.compareAndSet(false, true);
        if (this.eventPublisher != null && shouldPublish) {
            if (this.logger.isInfoEnabled()) {
                this.logger.info((Object)this.availableEvent);
            }
            this.eventPublisher.publishEvent((ApplicationEvent)this.availableEvent);
        }
    }

    protected void publishBrokerUnavailableEvent() {
        boolean shouldPublish = this.brokerAvailable.compareAndSet(true, false);
        if (this.eventPublisher != null && shouldPublish) {
            if (this.logger.isInfoEnabled()) {
                this.logger.info((Object)this.notAvailableEvent);
            }
            this.eventPublisher.publishEvent((ApplicationEvent)this.notAvailableEvent);
        }
    }

    protected MessageChannel getClientOutboundChannelForSession(String sessionId) {
        return this.preservePublishOrder ? new OrderedMessageChannelDecorator(this.getClientOutboundChannel(), this.logger) : this.getClientOutboundChannel();
    }

    private class UnsentDisconnectChannelInterceptor
    implements ChannelInterceptor {
        private UnsentDisconnectChannelInterceptor() {
        }

        @Override
        public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, @Nullable Exception ex) {
            SimpMessageType messageType;
            if (!sent && SimpMessageType.DISCONNECT.equals((Object)(messageType = SimpMessageHeaderAccessor.getMessageType(message.getHeaders())))) {
                AbstractBrokerMessageHandler.this.logger.debug((Object)"Detected unsent DISCONNECT message. Processing anyway.");
                AbstractBrokerMessageHandler.this.handleMessage(message);
            }
        }
    }
}

