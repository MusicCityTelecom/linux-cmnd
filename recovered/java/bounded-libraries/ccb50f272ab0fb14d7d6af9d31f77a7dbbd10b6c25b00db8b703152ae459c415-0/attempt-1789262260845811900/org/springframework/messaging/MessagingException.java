/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.NestedRuntimeException
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging;

import org.springframework.core.NestedRuntimeException;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;

public class MessagingException
extends NestedRuntimeException {
    @Nullable
    private final Message<?> failedMessage;

    public MessagingException(Message<?> message) {
        super(null, null);
        this.failedMessage = message;
    }

    public MessagingException(String description) {
        super(description);
        this.failedMessage = null;
    }

    public MessagingException(@Nullable String description, @Nullable Throwable cause) {
        super(description, cause);
        this.failedMessage = null;
    }

    public MessagingException(Message<?> message, String description) {
        super(description);
        this.failedMessage = message;
    }

    public MessagingException(Message<?> message, Throwable cause) {
        super(null, cause);
        this.failedMessage = message;
    }

    public MessagingException(Message<?> message, @Nullable String description, @Nullable Throwable cause) {
        super(description, cause);
        this.failedMessage = message;
    }

    @Nullable
    public Message<?> getFailedMessage() {
        return this.failedMessage;
    }

    public String toString() {
        return super.toString() + (this.failedMessage == null ? "" : ", failedMessage=" + this.failedMessage);
    }
}

