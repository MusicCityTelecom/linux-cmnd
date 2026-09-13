/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessagingException
 */
package org.springframework.integration.gateway;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

@FunctionalInterface
public interface RequestReplyExchanger {
    public Message<?> exchange(Message<?> var1) throws MessagingException;
}

