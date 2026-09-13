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

public class MessageTimeoutException
extends MessageDeliveryException {
    public MessageTimeoutException(String description) {
        super(description);
    }

    public MessageTimeoutException(Message<?> failedMessage, String description, Throwable cause) {
        super(failedMessage, description, cause);
    }

    public MessageTimeoutException(Message<?> failedMessage, String description) {
        super(failedMessage, description);
    }

    public MessageTimeoutException(Message<?> failedMessage) {
        super(failedMessage);
    }
}

