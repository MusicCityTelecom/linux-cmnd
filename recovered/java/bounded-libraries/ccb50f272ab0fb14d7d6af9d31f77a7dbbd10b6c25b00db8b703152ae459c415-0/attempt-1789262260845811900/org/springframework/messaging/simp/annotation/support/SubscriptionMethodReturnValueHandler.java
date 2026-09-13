/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.springframework.core.MethodParameter
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.messaging.simp.annotation.support;

import org.apache.commons.logging.Log;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.SimpLogging;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.support.MessageHeaderInitializer;
import org.springframework.util.Assert;

public class SubscriptionMethodReturnValueHandler
implements HandlerMethodReturnValueHandler {
    private static final Log logger = SimpLogging.forLogName(SubscriptionMethodReturnValueHandler.class);
    private final MessageSendingOperations<String> messagingTemplate;
    @Nullable
    private MessageHeaderInitializer headerInitializer;

    public SubscriptionMethodReturnValueHandler(MessageSendingOperations<String> template) {
        Assert.notNull(template, (String)"messagingTemplate must not be null");
        this.messagingTemplate = template;
    }

    public void setHeaderInitializer(@Nullable MessageHeaderInitializer headerInitializer) {
        this.headerInitializer = headerInitializer;
    }

    @Nullable
    public MessageHeaderInitializer getHeaderInitializer() {
        return this.headerInitializer;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.hasMethodAnnotation(SubscribeMapping.class) && !returnType.hasMethodAnnotation(SendTo.class) && !returnType.hasMethodAnnotation(SendToUser.class);
    }

    @Override
    public void handleReturnValue(@Nullable Object returnValue, MethodParameter returnType, Message<?> message) throws Exception {
        if (returnValue == null) {
            return;
        }
        MessageHeaders headers = message.getHeaders();
        String sessionId = SimpMessageHeaderAccessor.getSessionId(headers);
        String subscriptionId = SimpMessageHeaderAccessor.getSubscriptionId(headers);
        String destination = SimpMessageHeaderAccessor.getDestination(headers);
        if (subscriptionId == null) {
            throw new IllegalStateException("No simpSubscriptionId in " + message + " returned by: " + returnType.getMethod());
        }
        if (destination == null) {
            throw new IllegalStateException("No simpDestination in " + message + " returned by: " + returnType.getMethod());
        }
        if (logger.isDebugEnabled()) {
            logger.debug((Object)("Reply to @SubscribeMapping: " + returnValue));
        }
        MessageHeaders headersToSend = this.createHeaders(sessionId, subscriptionId, returnType);
        this.messagingTemplate.convertAndSend(destination, returnValue, headersToSend);
    }

    private MessageHeaders createHeaders(@Nullable String sessionId, String subscriptionId, MethodParameter returnType) {
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        if (this.getHeaderInitializer() != null) {
            this.getHeaderInitializer().initHeaders(accessor);
        }
        if (sessionId != null) {
            accessor.setSessionId(sessionId);
        }
        accessor.setSubscriptionId(subscriptionId);
        accessor.setHeader("conversionHint", returnType);
        accessor.setLeaveMutable(true);
        return accessor.getMessageHeaders();
    }
}

