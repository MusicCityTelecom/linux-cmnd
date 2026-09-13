/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.messaging;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

public class MessageHandlingException
extends MessagingException {
    public MessageHandlingException(Message<?> failedMessage) {
        super(failedMessage);
    }

    public MessageHandlingException(Message<?> message, String description) {
        super(message, description);
    }

    public MessageHandlingException(Message<?> failedMessage, Throwable cause) {
        super(failedMessage, cause);
    }

    public MessageHandlingException(Message<?> message, String description, Throwable cause) {
        super(message, description, cause);
    }
}

