/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessagingException
 */
package org.springframework.integration.mapping;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

public class MessageMappingException
extends MessagingException {
    public MessageMappingException(String description) {
        super(description);
    }

    public MessageMappingException(String description, Throwable cause) {
        super(description, cause);
    }

    public MessageMappingException(Message<?> failedMessage, String description) {
        super(failedMessage, description);
    }

    public MessageMappingException(Message<?> failedMessage, String description, Throwable cause) {
        super(failedMessage, description, cause);
    }
}

