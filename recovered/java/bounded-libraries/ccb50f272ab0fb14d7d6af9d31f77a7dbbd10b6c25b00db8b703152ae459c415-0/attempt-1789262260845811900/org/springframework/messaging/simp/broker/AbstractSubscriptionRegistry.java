/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.LinkedMultiValueMap
 *  org.springframework.util.MultiValueMap
 */
package org.springframework.messaging.simp.broker;

import org.apache.commons.logging.Log;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpLogging;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.broker.SubscriptionRegistry;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public abstract class AbstractSubscriptionRegistry
implements SubscriptionRegistry {
    private static final MultiValueMap<String, String> EMPTY_MAP = CollectionUtils.unmodifiableMultiValueMap((MultiValueMap)new LinkedMultiValueMap());
    protected final Log logger = SimpLogging.forLogName(this.getClass());

    @Override
    public final void registerSubscription(Message<?> message) {
        MessageHeaders headers = message.getHeaders();
        SimpMessageType messageType = SimpMessageHeaderAccessor.getMessageType(headers);
        if (!SimpMessageType.SUBSCRIBE.equals((Object)messageType)) {
            throw new IllegalArgumentException("Expected SUBSCRIBE: " + message);
        }
        String sessionId = SimpMessageHeaderAccessor.getSessionId(headers);
        if (sessionId == null) {
            if (this.logger.isErrorEnabled()) {
                this.logger.error((Object)("No sessionId in  " + message));
            }
            return;
        }
        String subscriptionId = SimpMessageHeaderAccessor.getSubscriptionId(headers);
        if (subscriptionId == null) {
            if (this.logger.isErrorEnabled()) {
                this.logger.error((Object)("No subscriptionId in " + message));
            }
            return;
        }
        String destination = SimpMessageHeaderAccessor.getDestination(headers);
        if (destination == null) {
            if (this.logger.isErrorEnabled()) {
                this.logger.error((Object)("No destination in " + message));
            }
            return;
        }
        this.addSubscriptionInternal(sessionId, subscriptionId, destination, message);
    }

    @Override
    public final void unregisterSubscription(Message<?> message) {
        MessageHeaders headers = message.getHeaders();
        SimpMessageType messageType = SimpMessageHeaderAccessor.getMessageType(headers);
        if (!SimpMessageType.UNSUBSCRIBE.equals((Object)messageType)) {
            throw new IllegalArgumentException("Expected UNSUBSCRIBE: " + message);
        }
        String sessionId = SimpMessageHeaderAccessor.getSessionId(headers);
        if (sessionId == null) {
            if (this.logger.isErrorEnabled()) {
                this.logger.error((Object)("No sessionId in " + message));
            }
            return;
        }
        String subscriptionId = SimpMessageHeaderAccessor.getSubscriptionId(headers);
        if (subscriptionId == null) {
            if (this.logger.isErrorEnabled()) {
                this.logger.error((Object)("No subscriptionId " + message));
            }
            return;
        }
        this.removeSubscriptionInternal(sessionId, subscriptionId, message);
    }

    @Override
    public final MultiValueMap<String, String> findSubscriptions(Message<?> message) {
        MessageHeaders headers = message.getHeaders();
        SimpMessageType type = SimpMessageHeaderAccessor.getMessageType(headers);
        if (!SimpMessageType.MESSAGE.equals((Object)type)) {
            throw new IllegalArgumentException("Unexpected message type: " + (Object)((Object)type));
        }
        String destination = SimpMessageHeaderAccessor.getDestination(headers);
        if (destination == null) {
            if (this.logger.isErrorEnabled()) {
                this.logger.error((Object)("No destination in " + message));
            }
            return EMPTY_MAP;
        }
        return this.findSubscriptionsInternal(destination, message);
    }

    protected abstract void addSubscriptionInternal(String var1, String var2, String var3, Message<?> var4);

    protected abstract void removeSubscriptionInternal(String var1, String var2, Message<?> var3);

    protected abstract MultiValueMap<String, String> findSubscriptionsInternal(String var1, Message<?> var2);
}

