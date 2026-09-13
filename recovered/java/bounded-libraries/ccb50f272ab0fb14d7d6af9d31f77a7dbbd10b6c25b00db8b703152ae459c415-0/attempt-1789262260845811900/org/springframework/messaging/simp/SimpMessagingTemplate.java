/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.messaging.simp;

import java.util.Map;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.core.AbstractMessageSendingTemplate;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderInitializer;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class SimpMessagingTemplate
extends AbstractMessageSendingTemplate<String>
implements SimpMessageSendingOperations {
    private final MessageChannel messageChannel;
    private String destinationPrefix = "/user/";
    private volatile long sendTimeout = -1L;
    @Nullable
    private MessageHeaderInitializer headerInitializer;

    public SimpMessagingTemplate(MessageChannel messageChannel) {
        Assert.notNull((Object)messageChannel, (String)"MessageChannel must not be null");
        this.messageChannel = messageChannel;
    }

    public MessageChannel getMessageChannel() {
        return this.messageChannel;
    }

    public void setUserDestinationPrefix(String prefix) {
        Assert.hasText((String)prefix, (String)"User destination prefix must not be empty");
        this.destinationPrefix = prefix.endsWith("/") ? prefix : prefix + "/";
    }

    public String getUserDestinationPrefix() {
        return this.destinationPrefix;
    }

    public void setSendTimeout(long sendTimeout) {
        this.sendTimeout = sendTimeout;
    }

    public long getSendTimeout() {
        return this.sendTimeout;
    }

    public void setHeaderInitializer(@Nullable MessageHeaderInitializer headerInitializer) {
        this.headerInitializer = headerInitializer;
    }

    @Nullable
    public MessageHeaderInitializer getHeaderInitializer() {
        return this.headerInitializer;
    }

    @Override
    public void send(Message<?> message) {
        Assert.notNull(message, (String)"Message is required");
        String destination = SimpMessageHeaderAccessor.getDestination(message.getHeaders());
        if (destination != null) {
            this.sendInternal(message);
            return;
        }
        this.doSend((String)this.getRequiredDefaultDestination(), message);
    }

    @Override
    protected void doSend(String destination, Message<?> message) {
        Assert.notNull((Object)destination, (String)"Destination must not be null");
        SimpMessageHeaderAccessor simpAccessor = MessageHeaderAccessor.getAccessor(message, SimpMessageHeaderAccessor.class);
        if (simpAccessor != null) {
            if (simpAccessor.isMutable()) {
                simpAccessor.setDestination(destination);
                simpAccessor.setMessageTypeIfNotSet(SimpMessageType.MESSAGE);
                simpAccessor.setImmutable();
                this.sendInternal(message);
                return;
            }
            simpAccessor = (SimpMessageHeaderAccessor)MessageHeaderAccessor.getMutableAccessor(message);
            this.initHeaders(simpAccessor);
        } else {
            simpAccessor = SimpMessageHeaderAccessor.wrap(message);
            this.initHeaders(simpAccessor);
        }
        simpAccessor.setDestination(destination);
        simpAccessor.setMessageTypeIfNotSet(SimpMessageType.MESSAGE);
        message = MessageBuilder.createMessage(message.getPayload(), simpAccessor.getMessageHeaders());
        this.sendInternal(message);
    }

    private void sendInternal(Message<?> message) {
        boolean sent;
        String destination = SimpMessageHeaderAccessor.getDestination(message.getHeaders());
        Assert.notNull((Object)destination, (String)"Destination header required");
        long timeout = this.sendTimeout;
        boolean bl = sent = timeout >= 0L ? this.messageChannel.send(message, timeout) : this.messageChannel.send(message);
        if (!sent) {
            throw new MessageDeliveryException(message, "Failed to send message to destination '" + destination + "' within timeout: " + timeout);
        }
    }

    private void initHeaders(SimpMessageHeaderAccessor simpAccessor) {
        if (this.getHeaderInitializer() != null) {
            this.getHeaderInitializer().initHeaders(simpAccessor);
        }
    }

    @Override
    public void convertAndSendToUser(String user, String destination, Object payload) throws MessagingException {
        this.convertAndSendToUser(user, destination, payload, (MessagePostProcessor)null);
    }

    @Override
    public void convertAndSendToUser(String user, String destination, Object payload, @Nullable Map<String, Object> headers) throws MessagingException {
        this.convertAndSendToUser(user, destination, payload, headers, null);
    }

    @Override
    public void convertAndSendToUser(String user, String destination, Object payload, @Nullable MessagePostProcessor postProcessor) throws MessagingException {
        this.convertAndSendToUser(user, destination, payload, null, postProcessor);
    }

    @Override
    public void convertAndSendToUser(String user, String destination, Object payload, @Nullable Map<String, Object> headers, @Nullable MessagePostProcessor postProcessor) throws MessagingException {
        Assert.notNull((Object)user, (String)"User must not be null");
        Assert.isTrue((!user.contains("%2F") ? 1 : 0) != 0, (String)("Invalid sequence \"%2F\" in user name: " + user));
        user = StringUtils.replace((String)user, (String)"/", (String)"%2F");
        destination = destination.startsWith("/") ? destination : "/" + destination;
        super.convertAndSend(this.destinationPrefix + user + destination, payload, headers, postProcessor);
    }

    @Override
    protected Map<String, Object> processHeadersToSend(@Nullable Map<String, Object> headers) {
        SimpMessageHeaderAccessor accessor;
        if (headers == null) {
            SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
            this.initHeaders(headerAccessor);
            headerAccessor.setLeaveMutable(true);
            return headerAccessor.getMessageHeaders();
        }
        if (headers.containsKey("nativeHeaders")) {
            return headers;
        }
        if (headers instanceof MessageHeaders && (accessor = MessageHeaderAccessor.getAccessor((MessageHeaders)headers, SimpMessageHeaderAccessor.class)) != null) {
            return headers;
        }
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        this.initHeaders(headerAccessor);
        headers.forEach((key, value) -> headerAccessor.setNativeHeader((String)key, value != null ? value.toString() : null));
        return headerAccessor.getMessageHeaders();
    }
}

