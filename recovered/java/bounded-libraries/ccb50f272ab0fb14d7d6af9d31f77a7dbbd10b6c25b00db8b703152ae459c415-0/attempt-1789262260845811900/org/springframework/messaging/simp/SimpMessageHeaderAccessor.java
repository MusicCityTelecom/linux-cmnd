/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.messaging.simp;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.support.IdTimestampMessageHeaderInitializer;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.support.NativeMessageHeaderAccessor;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public class SimpMessageHeaderAccessor
extends NativeMessageHeaderAccessor {
    private static final IdTimestampMessageHeaderInitializer headerInitializer = new IdTimestampMessageHeaderInitializer();
    public static final String DESTINATION_HEADER = "simpDestination";
    public static final String MESSAGE_TYPE_HEADER = "simpMessageType";
    public static final String SESSION_ID_HEADER = "simpSessionId";
    public static final String SESSION_ATTRIBUTES = "simpSessionAttributes";
    public static final String SUBSCRIPTION_ID_HEADER = "simpSubscriptionId";
    public static final String USER_HEADER = "simpUser";
    public static final String CONNECT_MESSAGE_HEADER = "simpConnectMessage";
    public static final String DISCONNECT_MESSAGE_HEADER = "simpDisconnectMessage";
    public static final String HEART_BEAT_HEADER = "simpHeartbeat";
    public static final String ORIGINAL_DESTINATION = "simpOrigDestination";
    public static final String IGNORE_ERROR = "simpIgnoreError";
    @Nullable
    private Consumer<Principal> userCallback;

    protected SimpMessageHeaderAccessor(SimpMessageType messageType, @Nullable Map<String, List<String>> externalSourceHeaders) {
        super(externalSourceHeaders);
        Assert.notNull((Object)((Object)messageType), (String)"MessageType must not be null");
        this.setHeader(MESSAGE_TYPE_HEADER, (Object)messageType);
        headerInitializer.initHeaders(this);
    }

    protected SimpMessageHeaderAccessor(Message<?> message) {
        super(message);
        headerInitializer.initHeaders(this);
    }

    @Override
    protected MessageHeaderAccessor createAccessor(Message<?> message) {
        return SimpMessageHeaderAccessor.wrap(message);
    }

    public void setMessageTypeIfNotSet(SimpMessageType messageType) {
        if (this.getMessageType() == null) {
            this.setHeader(MESSAGE_TYPE_HEADER, (Object)messageType);
        }
    }

    @Nullable
    public SimpMessageType getMessageType() {
        return (SimpMessageType)((Object)this.getHeader(MESSAGE_TYPE_HEADER));
    }

    public void setDestination(@Nullable String destination) {
        this.setHeader(DESTINATION_HEADER, destination);
    }

    @Nullable
    public String getDestination() {
        return (String)this.getHeader(DESTINATION_HEADER);
    }

    public void setSubscriptionId(@Nullable String subscriptionId) {
        this.setHeader(SUBSCRIPTION_ID_HEADER, subscriptionId);
    }

    @Nullable
    public String getSubscriptionId() {
        return (String)this.getHeader(SUBSCRIPTION_ID_HEADER);
    }

    public void setSessionId(@Nullable String sessionId) {
        this.setHeader(SESSION_ID_HEADER, sessionId);
    }

    @Nullable
    public String getSessionId() {
        return (String)this.getHeader(SESSION_ID_HEADER);
    }

    public void setSessionAttributes(@Nullable Map<String, Object> attributes) {
        this.setHeader(SESSION_ATTRIBUTES, attributes);
    }

    @Nullable
    public Map<String, Object> getSessionAttributes() {
        return (Map)this.getHeader(SESSION_ATTRIBUTES);
    }

    public void setUser(@Nullable Principal principal) {
        this.setHeader(USER_HEADER, principal);
        if (this.userCallback != null) {
            this.userCallback.accept(principal);
        }
    }

    @Nullable
    public Principal getUser() {
        return (Principal)this.getHeader(USER_HEADER);
    }

    public void setUserChangeCallback(Consumer<Principal> callback) {
        Assert.notNull(callback, (String)"'callback' is required");
        this.userCallback = this.userCallback != null ? this.userCallback.andThen(callback) : callback;
    }

    @Override
    public String getShortLogMessage(Object payload) {
        if (this.getMessageType() == null) {
            return super.getDetailedLogMessage(payload);
        }
        StringBuilder sb = this.getBaseLogMessage();
        if (!CollectionUtils.isEmpty(this.getSessionAttributes())) {
            sb.append(" attributes[").append(this.getSessionAttributes().size()).append(']');
        }
        sb.append(this.getShortPayloadLogMessage(payload));
        return sb.toString();
    }

    @Override
    public String getDetailedLogMessage(@Nullable Object payload) {
        if (this.getMessageType() == null) {
            return super.getDetailedLogMessage(payload);
        }
        StringBuilder sb = this.getBaseLogMessage();
        if (!CollectionUtils.isEmpty(this.getSessionAttributes())) {
            sb.append(" attributes=").append(this.getSessionAttributes());
        }
        if (!CollectionUtils.isEmpty((Map)((Map)this.getHeader("nativeHeaders")))) {
            sb.append(" nativeHeaders=").append(this.getHeader("nativeHeaders"));
        }
        sb.append(this.getDetailedPayloadLogMessage(payload));
        return sb.toString();
    }

    private StringBuilder getBaseLogMessage() {
        String subscriptionId;
        StringBuilder sb = new StringBuilder();
        SimpMessageType messageType = this.getMessageType();
        sb.append(messageType != null ? messageType.name() : SimpMessageType.OTHER);
        String destination = this.getDestination();
        if (destination != null) {
            sb.append(" destination=").append(destination);
        }
        if ((subscriptionId = this.getSubscriptionId()) != null) {
            sb.append(" subscriptionId=").append(subscriptionId);
        }
        sb.append(" session=").append(this.getSessionId());
        Principal user = this.getUser();
        if (user != null) {
            sb.append(" user=").append(user.getName());
        }
        return sb;
    }

    public static SimpMessageHeaderAccessor create() {
        return new SimpMessageHeaderAccessor(SimpMessageType.MESSAGE, null);
    }

    public static SimpMessageHeaderAccessor create(SimpMessageType messageType) {
        return new SimpMessageHeaderAccessor(messageType, null);
    }

    public static SimpMessageHeaderAccessor wrap(Message<?> message) {
        return new SimpMessageHeaderAccessor(message);
    }

    @Nullable
    public static SimpMessageType getMessageType(Map<String, Object> headers) {
        return (SimpMessageType)((Object)headers.get(MESSAGE_TYPE_HEADER));
    }

    @Nullable
    public static String getDestination(Map<String, Object> headers) {
        return (String)headers.get(DESTINATION_HEADER);
    }

    @Nullable
    public static String getSubscriptionId(Map<String, Object> headers) {
        return (String)headers.get(SUBSCRIPTION_ID_HEADER);
    }

    @Nullable
    public static String getSessionId(Map<String, Object> headers) {
        return (String)headers.get(SESSION_ID_HEADER);
    }

    @Nullable
    public static Map<String, Object> getSessionAttributes(Map<String, Object> headers) {
        return (Map)headers.get(SESSION_ATTRIBUTES);
    }

    @Nullable
    public static Principal getUser(Map<String, Object> headers) {
        return (Principal)headers.get(USER_HEADER);
    }

    @Nullable
    public static long[] getHeartbeat(Map<String, Object> headers) {
        return (long[])headers.get(HEART_BEAT_HEADER);
    }

    static {
        headerInitializer.setDisableIdGeneration();
        headerInitializer.setEnableTimestamp(false);
    }
}

