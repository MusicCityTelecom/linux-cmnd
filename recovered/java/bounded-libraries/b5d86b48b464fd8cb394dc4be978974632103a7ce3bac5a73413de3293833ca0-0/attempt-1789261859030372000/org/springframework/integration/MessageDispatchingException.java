/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageDeliveryException
 */
package org.springframework.integration;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageDeliveryException;

public class MessageDispatchingException
extends MessageDeliveryException {
    public MessageDispatchingException(String description) {
        super(description);
    }

    public MessageDispatchingException(Message<?> undeliveredMessage, String description, Throwable cause) {
        super(undeliveredMessage, description, cause);
    }

    public MessageDispatchingException(Message<?> undeliveredMessage, String description) {
        super(undeliveredMessage, description);
    }

    public MessageDispatchingException(Message<?> undeliveredMessage) {
        super(undeliveredMessage);
    }
}

