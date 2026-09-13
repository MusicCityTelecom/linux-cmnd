/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessagingException
 */
package org.springframework.integration.transformer;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

public class MessageTransformationException
extends MessagingException {
    public MessageTransformationException(Message<?> message, String description, Throwable cause) {
        super(message, description, cause);
    }

    public MessageTransformationException(Message<?> message, String description) {
        super(message, description);
    }

    public MessageTransformationException(String description, Throwable cause) {
        super(description, cause);
    }

    public MessageTransformationException(String description) {
        super(description);
    }
}

