/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.springframework.context.SmartLifecycle
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.messaging.simp.user;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.logging.Log;
import org.springframework.context.SmartLifecycle;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.SimpLogging;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.UserDestinationResolver;
import org.springframework.messaging.simp.user.UserDestinationResult;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderInitializer;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class UserDestinationMessageHandler
implements MessageHandler,
SmartLifecycle {
    private static final Log logger = SimpLogging.forLogName(UserDestinationMessageHandler.class);
    private final SubscribableChannel clientInboundChannel;
    private final SubscribableChannel brokerChannel;
    private final UserDestinationResolver destinationResolver;
    private final MessageSendingOperations<String> messagingTemplate;
    @Nullable
    private BroadcastHandler broadcastHandler;
    @Nullable
    private MessageHeaderInitializer headerInitializer;
    private volatile boolean running;
    private final Object lifecycleMonitor = new Object();

    public UserDestinationMessageHandler(SubscribableChannel clientInboundChannel, SubscribableChannel brokerChannel, UserDestinationResolver resolver) {
        Assert.notNull((Object)clientInboundChannel, (String)"'clientInChannel' must not be null");
        Assert.notNull((Object)brokerChannel, (String)"'brokerChannel' must not be null");
        Assert.notNull((Object)resolver, (String)"resolver must not be null");
        this.clientInboundChannel = clientInboundChannel;
        this.brokerChannel = brokerChannel;
        this.messagingTemplate = new SimpMessagingTemplate(brokerChannel);
        this.destinationResolver = resolver;
    }

    public UserDestinationResolver getUserDestinationResolver() {
        return this.destinationResolver;
    }

    public void setBroadcastDestination(@Nullable String destination) {
        this.broadcastHandler = StringUtils.hasText((String)destination) ? new BroadcastHandler(this.messagingTemplate, destination) : null;
    }

    @Nullable
    public String getBroadcastDestination() {
        return this.broadcastHandler != null ? this.broadcastHandler.getBroadcastDestination() : null;
    }

    public MessageSendingOperations<String> getBrokerMessagingTemplate() {
        return this.messagingTemplate;
    }

    public void setHeaderInitializer(@Nullable MessageHeaderInitializer headerInitializer) {
        this.headerInitializer = headerInitializer;
    }

    @Nullable
    public MessageHeaderInitializer getHeaderInitializer() {
        return this.headerInitializer;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void start() {
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            this.clientInboundChannel.subscribe(this);
            this.brokerChannel.subscribe(this);
            this.running = true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void stop() {
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            this.running = false;
            this.clientInboundChannel.unsubscribe(this);
            this.brokerChannel.unsubscribe(this);
        }
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

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        Message<?> messageToUse = message;
        if (this.broadcastHandler != null && (messageToUse = this.broadcastHandler.preHandle(message)) == null) {
            return;
        }
        UserDestinationResult result = this.destinationResolver.resolveDestination(messageToUse);
        if (result == null) {
            return;
        }
        if (result.getTargetDestinations().isEmpty()) {
            if (logger.isTraceEnabled()) {
                logger.trace((Object)("No active sessions for user destination: " + result.getSourceDestination()));
            }
            if (this.broadcastHandler != null) {
                this.broadcastHandler.handleUnresolved(messageToUse);
            }
            return;
        }
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(messageToUse);
        this.initHeaders(accessor);
        accessor.setNativeHeader("simpOrigDestination", result.getSubscribeDestination());
        accessor.setLeaveMutable(true);
        messageToUse = MessageBuilder.createMessage(messageToUse.getPayload(), accessor.getMessageHeaders());
        if (logger.isTraceEnabled()) {
            logger.trace((Object)("Translated " + result.getSourceDestination() + " -> " + result.getTargetDestinations()));
        }
        for (String target : result.getTargetDestinations()) {
            this.messagingTemplate.send(target, messageToUse);
        }
    }

    private void initHeaders(SimpMessageHeaderAccessor headerAccessor) {
        if (this.getHeaderInitializer() != null) {
            this.getHeaderInitializer().initHeaders(headerAccessor);
        }
    }

    public String toString() {
        return "UserDestinationMessageHandler[" + this.destinationResolver + "]";
    }

    private static class BroadcastHandler {
        private static final List<String> NO_COPY_LIST = Arrays.asList("subscription", "message-id");
        private final MessageSendingOperations<String> messagingTemplate;
        private final String broadcastDestination;

        public BroadcastHandler(MessageSendingOperations<String> template, String destination) {
            this.messagingTemplate = template;
            this.broadcastDestination = destination;
        }

        public String getBroadcastDestination() {
            return this.broadcastDestination;
        }

        @Nullable
        public Message<?> preHandle(Message<?> message) throws MessagingException {
            String destination = SimpMessageHeaderAccessor.getDestination(message.getHeaders());
            if (!this.getBroadcastDestination().equals(destination)) {
                return message;
            }
            SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.getAccessor(message, SimpMessageHeaderAccessor.class);
            Assert.state((accessor != null ? 1 : 0) != 0, (String)"No SimpMessageHeaderAccessor");
            if (accessor.getSessionId() == null) {
                return null;
            }
            destination = accessor.getFirstNativeHeader("simpOrigDestination");
            if (logger.isTraceEnabled()) {
                logger.trace((Object)("Checking unresolved user destination: " + destination));
            }
            SimpMessageHeaderAccessor newAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
            for (String name : accessor.toNativeHeaderMap().keySet()) {
                if (NO_COPY_LIST.contains(name)) continue;
                newAccessor.setNativeHeader(name, accessor.getFirstNativeHeader(name));
            }
            if (destination != null) {
                newAccessor.setDestination(destination);
            }
            newAccessor.setHeader("simpIgnoreError", true);
            return MessageBuilder.createMessage(message.getPayload(), newAccessor.getMessageHeaders());
        }

        public void handleUnresolved(Message<?> message) {
            MessageHeaders headers = message.getHeaders();
            if (SimpMessageHeaderAccessor.getFirstNativeHeader("simpOrigDestination", headers) != null) {
                return;
            }
            SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(message);
            String destination = accessor.getDestination();
            accessor.setNativeHeader("simpOrigDestination", destination);
            accessor.setLeaveMutable(true);
            message = MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
            if (logger.isTraceEnabled()) {
                logger.trace((Object)("Translated " + destination + " -> " + this.getBroadcastDestination()));
            }
            this.messagingTemplate.send(this.getBroadcastDestination(), message);
        }
    }
}

