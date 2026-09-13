/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.PathMatcher
 */
package org.springframework.messaging.simp.config;

import java.util.Arrays;
import java.util.Collection;
import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.simp.broker.SimpleBrokerMessageHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.SimpleBrokerRegistration;
import org.springframework.messaging.simp.config.StompBrokerRelayRegistration;
import org.springframework.messaging.simp.stomp.StompBrokerRelayMessageHandler;
import org.springframework.util.Assert;
import org.springframework.util.PathMatcher;

public class MessageBrokerRegistry {
    private final SubscribableChannel clientInboundChannel;
    private final MessageChannel clientOutboundChannel;
    @Nullable
    private SimpleBrokerRegistration simpleBrokerRegistration;
    @Nullable
    private StompBrokerRelayRegistration brokerRelayRegistration;
    private final ChannelRegistration brokerChannelRegistration = new ChannelRegistration();
    @Nullable
    private String[] applicationDestinationPrefixes;
    @Nullable
    private String userDestinationPrefix;
    @Nullable
    private Integer userRegistryOrder;
    @Nullable
    private PathMatcher pathMatcher;
    @Nullable
    private Integer cacheLimit;
    private boolean preservePublishOrder;

    public MessageBrokerRegistry(SubscribableChannel clientInboundChannel, MessageChannel clientOutboundChannel) {
        Assert.notNull((Object)clientInboundChannel, (String)"Inbound channel must not be null");
        Assert.notNull((Object)clientOutboundChannel, (String)"Outbound channel must not be null");
        this.clientInboundChannel = clientInboundChannel;
        this.clientOutboundChannel = clientOutboundChannel;
    }

    public SimpleBrokerRegistration enableSimpleBroker(String ... destinationPrefixes) {
        this.simpleBrokerRegistration = new SimpleBrokerRegistration(this.clientInboundChannel, this.clientOutboundChannel, destinationPrefixes);
        return this.simpleBrokerRegistration;
    }

    public StompBrokerRelayRegistration enableStompBrokerRelay(String ... destinationPrefixes) {
        this.brokerRelayRegistration = new StompBrokerRelayRegistration(this.clientInboundChannel, this.clientOutboundChannel, destinationPrefixes);
        return this.brokerRelayRegistration;
    }

    public ChannelRegistration configureBrokerChannel() {
        return this.brokerChannelRegistration;
    }

    protected ChannelRegistration getBrokerChannelRegistration() {
        return this.brokerChannelRegistration;
    }

    @Nullable
    protected String getUserDestinationBroadcast() {
        return this.brokerRelayRegistration != null ? this.brokerRelayRegistration.getUserDestinationBroadcast() : null;
    }

    @Nullable
    protected String getUserRegistryBroadcast() {
        return this.brokerRelayRegistration != null ? this.brokerRelayRegistration.getUserRegistryBroadcast() : null;
    }

    public MessageBrokerRegistry setApplicationDestinationPrefixes(String ... prefixes) {
        this.applicationDestinationPrefixes = prefixes;
        return this;
    }

    @Nullable
    protected Collection<String> getApplicationDestinationPrefixes() {
        return this.applicationDestinationPrefixes != null ? Arrays.asList(this.applicationDestinationPrefixes) : null;
    }

    public MessageBrokerRegistry setUserDestinationPrefix(String destinationPrefix) {
        this.userDestinationPrefix = destinationPrefix;
        return this;
    }

    @Nullable
    protected String getUserDestinationPrefix() {
        return this.userDestinationPrefix;
    }

    public void setUserRegistryOrder(int order) {
        this.userRegistryOrder = order;
    }

    @Nullable
    protected Integer getUserRegistryOrder() {
        return this.userRegistryOrder;
    }

    public MessageBrokerRegistry setPathMatcher(PathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
        return this;
    }

    @Nullable
    protected PathMatcher getPathMatcher() {
        return this.pathMatcher;
    }

    public MessageBrokerRegistry setCacheLimit(int cacheLimit) {
        this.cacheLimit = cacheLimit;
        return this;
    }

    public MessageBrokerRegistry setPreservePublishOrder(boolean preservePublishOrder) {
        this.preservePublishOrder = preservePublishOrder;
        return this;
    }

    @Nullable
    protected SimpleBrokerMessageHandler getSimpleBroker(SubscribableChannel brokerChannel) {
        if (this.simpleBrokerRegistration == null && this.brokerRelayRegistration == null) {
            this.enableSimpleBroker(new String[0]);
        }
        if (this.simpleBrokerRegistration != null) {
            SimpleBrokerMessageHandler handler = this.simpleBrokerRegistration.getMessageHandler(brokerChannel);
            handler.setPathMatcher(this.pathMatcher);
            handler.setCacheLimit(this.cacheLimit);
            handler.setPreservePublishOrder(this.preservePublishOrder);
            return handler;
        }
        return null;
    }

    @Nullable
    protected StompBrokerRelayMessageHandler getStompBrokerRelay(SubscribableChannel brokerChannel) {
        if (this.brokerRelayRegistration != null) {
            StompBrokerRelayMessageHandler relay = this.brokerRelayRegistration.getMessageHandler(brokerChannel);
            relay.setPreservePublishOrder(this.preservePublishOrder);
            return relay;
        }
        return null;
    }
}

