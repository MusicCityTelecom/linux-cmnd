/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.scheduling.TaskScheduler
 *  org.springframework.util.Assert
 *  org.springframework.util.MultiValueMap
 *  org.springframework.util.PathMatcher
 */
package org.springframework.messaging.simp.broker;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.broker.AbstractBrokerMessageHandler;
import org.springframework.messaging.simp.broker.DefaultSubscriptionRegistry;
import org.springframework.messaging.simp.broker.SubscriptionRegistry;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderInitializer;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.util.PathMatcher;

public class SimpleBrokerMessageHandler
extends AbstractBrokerMessageHandler {
    private static final byte[] EMPTY_PAYLOAD = new byte[0];
    @Nullable
    private PathMatcher pathMatcher;
    @Nullable
    private Integer cacheLimit;
    @Nullable
    private String selectorHeaderName = "selector";
    @Nullable
    private TaskScheduler taskScheduler;
    @Nullable
    private long[] heartbeatValue;
    @Nullable
    private MessageHeaderInitializer headerInitializer;
    private SubscriptionRegistry subscriptionRegistry;
    private final Map<String, SessionInfo> sessions = new ConcurrentHashMap<String, SessionInfo>();
    @Nullable
    private ScheduledFuture<?> heartbeatFuture;

    public SimpleBrokerMessageHandler(SubscribableChannel clientInboundChannel, MessageChannel clientOutboundChannel, SubscribableChannel brokerChannel, Collection<String> destinationPrefixes) {
        super(clientInboundChannel, clientOutboundChannel, brokerChannel, destinationPrefixes);
        this.subscriptionRegistry = new DefaultSubscriptionRegistry();
    }

    public void setSubscriptionRegistry(SubscriptionRegistry subscriptionRegistry) {
        Assert.notNull((Object)subscriptionRegistry, (String)"SubscriptionRegistry must not be null");
        this.subscriptionRegistry = subscriptionRegistry;
        this.initPathMatcherToUse();
        this.initCacheLimitToUse();
        this.initSelectorHeaderNameToUse();
    }

    public SubscriptionRegistry getSubscriptionRegistry() {
        return this.subscriptionRegistry;
    }

    public void setPathMatcher(@Nullable PathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
        this.initPathMatcherToUse();
    }

    private void initPathMatcherToUse() {
        if (this.pathMatcher != null && this.subscriptionRegistry instanceof DefaultSubscriptionRegistry) {
            ((DefaultSubscriptionRegistry)this.subscriptionRegistry).setPathMatcher(this.pathMatcher);
        }
    }

    public void setCacheLimit(@Nullable Integer cacheLimit) {
        this.cacheLimit = cacheLimit;
        this.initCacheLimitToUse();
    }

    private void initCacheLimitToUse() {
        if (this.cacheLimit != null && this.subscriptionRegistry instanceof DefaultSubscriptionRegistry) {
            ((DefaultSubscriptionRegistry)this.subscriptionRegistry).setCacheLimit(this.cacheLimit);
        }
    }

    public void setSelectorHeaderName(@Nullable String selectorHeaderName) {
        this.selectorHeaderName = selectorHeaderName;
        this.initSelectorHeaderNameToUse();
    }

    private void initSelectorHeaderNameToUse() {
        if (this.subscriptionRegistry instanceof DefaultSubscriptionRegistry) {
            ((DefaultSubscriptionRegistry)this.subscriptionRegistry).setSelectorHeaderName(this.selectorHeaderName);
        }
    }

    public void setTaskScheduler(@Nullable TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
        if (taskScheduler != null && this.heartbeatValue == null) {
            this.heartbeatValue = new long[]{10000L, 10000L};
        }
    }

    @Nullable
    public TaskScheduler getTaskScheduler() {
        return this.taskScheduler;
    }

    public void setHeartbeatValue(@Nullable long[] heartbeat) {
        if (heartbeat != null && (heartbeat.length != 2 || heartbeat[0] < 0L || heartbeat[1] < 0L)) {
            throw new IllegalArgumentException("Invalid heart-beat: " + Arrays.toString(heartbeat));
        }
        this.heartbeatValue = heartbeat;
    }

    @Nullable
    public long[] getHeartbeatValue() {
        return this.heartbeatValue;
    }

    public void setHeaderInitializer(@Nullable MessageHeaderInitializer headerInitializer) {
        this.headerInitializer = headerInitializer;
    }

    @Nullable
    public MessageHeaderInitializer getHeaderInitializer() {
        return this.headerInitializer;
    }

    @Override
    public void startInternal() {
        this.publishBrokerAvailableEvent();
        if (this.taskScheduler != null) {
            long interval = this.initHeartbeatTaskDelay();
            if (interval > 0L) {
                this.heartbeatFuture = this.taskScheduler.scheduleWithFixedDelay((Runnable)new HeartbeatTask(), interval);
            }
        } else {
            Assert.isTrue((this.getHeartbeatValue() == null || this.getHeartbeatValue()[0] == 0L && this.getHeartbeatValue()[1] == 0L ? 1 : 0) != 0, (String)"Heartbeat values configured but no TaskScheduler provided");
        }
    }

    private long initHeartbeatTaskDelay() {
        if (this.getHeartbeatValue() == null) {
            return 0L;
        }
        if (this.getHeartbeatValue()[0] > 0L && this.getHeartbeatValue()[1] > 0L) {
            return Math.min(this.getHeartbeatValue()[0], this.getHeartbeatValue()[1]);
        }
        return this.getHeartbeatValue()[0] > 0L ? this.getHeartbeatValue()[0] : this.getHeartbeatValue()[1];
    }

    @Override
    public void stopInternal() {
        this.publishBrokerUnavailableEvent();
        if (this.heartbeatFuture != null) {
            this.heartbeatFuture.cancel(true);
        }
    }

    @Override
    protected void handleMessageInternal(Message<?> message) {
        MessageHeaders headers = message.getHeaders();
        String destination = SimpMessageHeaderAccessor.getDestination(headers);
        String sessionId = SimpMessageHeaderAccessor.getSessionId(headers);
        this.updateSessionReadTime(sessionId);
        if (!this.checkDestinationPrefix(destination)) {
            return;
        }
        SimpMessageType messageType = SimpMessageHeaderAccessor.getMessageType(headers);
        if (SimpMessageType.MESSAGE.equals((Object)messageType)) {
            this.logMessage(message);
            this.sendMessageToSubscribers(destination, message);
        } else if (SimpMessageType.CONNECT.equals((Object)messageType)) {
            this.logMessage(message);
            if (sessionId != null) {
                if (this.sessions.get(sessionId) != null) {
                    if (this.logger.isWarnEnabled()) {
                        this.logger.warn((Object)("Ignoring CONNECT in session " + sessionId + ". Already connected."));
                    }
                    return;
                }
                long[] heartbeatIn = SimpMessageHeaderAccessor.getHeartbeat(headers);
                long[] heartbeatOut = this.getHeartbeatValue();
                Principal user = SimpMessageHeaderAccessor.getUser(headers);
                MessageChannel outChannel = this.getClientOutboundChannelForSession(sessionId);
                this.sessions.put(sessionId, new SessionInfo(sessionId, user, outChannel, heartbeatIn, heartbeatOut));
                SimpMessageHeaderAccessor connectAck = SimpMessageHeaderAccessor.create(SimpMessageType.CONNECT_ACK);
                this.initHeaders(connectAck);
                connectAck.setSessionId(sessionId);
                if (user != null) {
                    connectAck.setUser(user);
                }
                connectAck.setHeader("simpConnectMessage", message);
                connectAck.setHeader("simpHeartbeat", heartbeatOut);
                Message<byte[]> messageOut = MessageBuilder.createMessage(EMPTY_PAYLOAD, connectAck.getMessageHeaders());
                this.getClientOutboundChannel().send(messageOut);
            }
        } else if (SimpMessageType.DISCONNECT.equals((Object)messageType)) {
            this.logMessage(message);
            if (sessionId != null) {
                Principal user = SimpMessageHeaderAccessor.getUser(headers);
                this.handleDisconnect(sessionId, user, message);
            }
        } else if (SimpMessageType.SUBSCRIBE.equals((Object)messageType)) {
            this.logMessage(message);
            this.subscriptionRegistry.registerSubscription(message);
        } else if (SimpMessageType.UNSUBSCRIBE.equals((Object)messageType)) {
            this.logMessage(message);
            this.subscriptionRegistry.unregisterSubscription(message);
        }
    }

    private void updateSessionReadTime(@Nullable String sessionId) {
        SessionInfo info;
        if (sessionId != null && (info = this.sessions.get(sessionId)) != null) {
            info.setLastReadTime(System.currentTimeMillis());
        }
    }

    private void logMessage(Message<?> message) {
        if (this.logger.isDebugEnabled()) {
            SimpMessageHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, SimpMessageHeaderAccessor.class);
            accessor = accessor != null ? accessor : SimpMessageHeaderAccessor.wrap(message);
            this.logger.debug((Object)("Processing " + accessor.getShortLogMessage(message.getPayload())));
        }
    }

    private void initHeaders(SimpMessageHeaderAccessor accessor) {
        if (this.getHeaderInitializer() != null) {
            this.getHeaderInitializer().initHeaders(accessor);
        }
    }

    private void handleDisconnect(String sessionId, @Nullable Principal user, @Nullable Message<?> origMessage) {
        this.sessions.remove(sessionId);
        this.subscriptionRegistry.unregisterAllSubscriptions(sessionId);
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create(SimpMessageType.DISCONNECT_ACK);
        accessor.setSessionId(sessionId);
        if (user != null) {
            accessor.setUser(user);
        }
        if (origMessage != null) {
            accessor.setHeader("simpDisconnectMessage", origMessage);
        }
        this.initHeaders(accessor);
        Message<byte[]> message = MessageBuilder.createMessage(EMPTY_PAYLOAD, accessor.getMessageHeaders());
        this.getClientOutboundChannel().send(message);
    }

    protected void sendMessageToSubscribers(@Nullable String destination, Message<?> message) {
        MultiValueMap<String, String> subscriptions = this.subscriptionRegistry.findSubscriptions(message);
        if (!subscriptions.isEmpty() && this.logger.isDebugEnabled()) {
            this.logger.debug((Object)("Broadcasting to " + subscriptions.size() + " sessions."));
        }
        long now = System.currentTimeMillis();
        subscriptions.forEach((sessionId, subscriptionIds) -> {
            for (String subscriptionId : subscriptionIds) {
                SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
                this.initHeaders(headerAccessor);
                headerAccessor.setSessionId((String)sessionId);
                headerAccessor.setSubscriptionId(subscriptionId);
                headerAccessor.copyHeadersIfAbsent(message.getHeaders());
                headerAccessor.setLeaveMutable(true);
                Object payload = message.getPayload();
                Message reply = MessageBuilder.createMessage(payload, headerAccessor.getMessageHeaders());
                SessionInfo info = this.sessions.get(sessionId);
                if (info == null) continue;
                try {
                    info.getClientOutboundChannel().send(reply);
                }
                catch (Throwable ex) {
                    if (!this.logger.isErrorEnabled()) continue;
                    this.logger.error((Object)("Failed to send " + message), ex);
                }
                finally {
                    info.setLastWriteTime(now);
                }
            }
        });
    }

    public String toString() {
        return "SimpleBrokerMessageHandler [" + this.subscriptionRegistry + "]";
    }

    private class HeartbeatTask
    implements Runnable {
        private HeartbeatTask() {
        }

        @Override
        public void run() {
            long now = System.currentTimeMillis();
            for (SessionInfo info : SimpleBrokerMessageHandler.this.sessions.values()) {
                if (info.getReadInterval() > 0L && now - info.getLastReadTime() > info.getReadInterval()) {
                    SimpleBrokerMessageHandler.this.handleDisconnect(info.getSessionId(), info.getUser(), null);
                }
                if (info.getWriteInterval() <= 0L || now - info.getLastWriteTime() <= info.getWriteInterval()) continue;
                SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create(SimpMessageType.HEARTBEAT);
                accessor.setSessionId(info.getSessionId());
                Principal user = info.getUser();
                if (user != null) {
                    accessor.setUser(user);
                }
                SimpleBrokerMessageHandler.this.initHeaders(accessor);
                accessor.setLeaveMutable(true);
                MessageHeaders headers = accessor.getMessageHeaders();
                info.getClientOutboundChannel().send(MessageBuilder.createMessage(EMPTY_PAYLOAD, headers));
            }
        }
    }

    private static class SessionInfo {
        private static final long HEARTBEAT_MULTIPLIER = 3L;
        private final String sessionId;
        @Nullable
        private final Principal user;
        private final MessageChannel clientOutboundChannel;
        private final long readInterval;
        private final long writeInterval;
        private volatile long lastReadTime;
        private volatile long lastWriteTime;

        public SessionInfo(String sessionId, @Nullable Principal user, MessageChannel outboundChannel, @Nullable long[] clientHeartbeat, @Nullable long[] serverHeartbeat) {
            this.sessionId = sessionId;
            this.user = user;
            this.clientOutboundChannel = outboundChannel;
            if (clientHeartbeat != null && serverHeartbeat != null) {
                this.readInterval = clientHeartbeat[0] > 0L && serverHeartbeat[1] > 0L ? Math.max(clientHeartbeat[0], serverHeartbeat[1]) * 3L : 0L;
                this.writeInterval = clientHeartbeat[1] > 0L && serverHeartbeat[0] > 0L ? Math.max(clientHeartbeat[1], serverHeartbeat[0]) : 0L;
            } else {
                this.readInterval = 0L;
                this.writeInterval = 0L;
            }
            this.lastReadTime = this.lastWriteTime = System.currentTimeMillis();
        }

        public String getSessionId() {
            return this.sessionId;
        }

        @Nullable
        public Principal getUser() {
            return this.user;
        }

        public MessageChannel getClientOutboundChannel() {
            return this.clientOutboundChannel;
        }

        public long getReadInterval() {
            return this.readInterval;
        }

        public long getWriteInterval() {
            return this.writeInterval;
        }

        public long getLastReadTime() {
            return this.lastReadTime;
        }

        public void setLastReadTime(long lastReadTime) {
            this.lastReadTime = lastReadTime;
        }

        public long getLastWriteTime() {
            return this.lastWriteTime;
        }

        public void setLastWriteTime(long lastWriteTime) {
            this.lastWriteTime = lastWriteTime;
        }
    }
}

