/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessagingException
 */
package org.springframework.integration.handler;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

public class ReplyRequiredException
extends MessagingException {
    public ReplyRequiredException(Message<?> failedMessage, String description) {
        super(failedMessage, description);
    }

    public ReplyRequiredException(Message<?> failedMessage, String description, Throwable t) {
        super(failedMessage, description, t);
    }
}

