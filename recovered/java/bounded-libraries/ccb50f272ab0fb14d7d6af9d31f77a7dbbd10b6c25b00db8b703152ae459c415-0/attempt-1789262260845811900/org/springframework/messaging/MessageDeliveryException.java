/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.messaging;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

public class MessageDeliveryException
extends MessagingException {
    public MessageDeliveryException(String description) {
        super(description);
    }

    public MessageDeliveryException(Message<?> undeliveredMessage) {
        super(undeliveredMessage);
    }

    public MessageDeliveryException(Message<?> undeliveredMessage, String description) {
        super(undeliveredMessage, description);
    }

    public MessageDeliveryException(Message<?> message, Throwable cause) {
        super(message, cause);
    }

    public MessageDeliveryException(Message<?> undeliveredMessage, String description, Throwable cause) {
        super(undeliveredMessage, description, cause);
    }
}

