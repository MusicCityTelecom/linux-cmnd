/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHandlingException
 */
package org.springframework.integration;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandlingException;

public class MessageRejectedException
extends MessageHandlingException {
    public MessageRejectedException(Message<?> failedMessage, String description) {
        super(failedMessage, description);
    }

    public MessageRejectedException(Message<?> failedMessage, String description, Throwable cause) {
        super(failedMessage, description, cause);
    }
}

