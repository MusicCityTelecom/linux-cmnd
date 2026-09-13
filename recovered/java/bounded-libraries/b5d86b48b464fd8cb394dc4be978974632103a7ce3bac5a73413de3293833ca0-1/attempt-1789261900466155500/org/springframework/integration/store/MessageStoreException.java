/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessagingException
 */
package org.springframework.integration.store;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

public class MessageStoreException
extends MessagingException {
    private static final long serialVersionUID = 1L;

    public MessageStoreException(Message<?> message) {
        super(message);
    }

    public MessageStoreException(String description) {
        super(description);
    }

    public MessageStoreException(String description, Throwable cause) {
        super(description, cause);
    }

    public MessageStoreException(Message<?> message, String description) {
        super(message, description);
    }

    public MessageStoreException(Message<?> message, Throwable cause) {
        super(message, cause);
    }

    public MessageStoreException(Message<?> message, String description, Throwable cause) {
        super(message, description, cause);
    }
}

