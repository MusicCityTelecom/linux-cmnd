/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging.support;

import java.util.Map;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;

public class ErrorMessage
extends GenericMessage<Throwable> {
    private static final long serialVersionUID = -5470210965279837728L;
    @Nullable
    private final Message<?> originalMessage;

    public ErrorMessage(Throwable payload) {
        super(payload);
        this.originalMessage = null;
    }

    public ErrorMessage(Throwable payload, Map<String, Object> headers) {
        super(payload, headers);
        this.originalMessage = null;
    }

    public ErrorMessage(Throwable payload, MessageHeaders headers) {
        super(payload, headers);
        this.originalMessage = null;
    }

    public ErrorMessage(Throwable payload, Message<?> originalMessage) {
        super(payload);
        this.originalMessage = originalMessage;
    }

    public ErrorMessage(Throwable payload, Map<String, Object> headers, Message<?> originalMessage) {
        super(payload, headers);
        this.originalMessage = originalMessage;
    }

    public ErrorMessage(Throwable payload, MessageHeaders headers, Message<?> originalMessage) {
        super(payload, headers);
        this.originalMessage = originalMessage;
    }

    @Nullable
    public Message<?> getOriginalMessage() {
        return this.originalMessage;
    }

    @Override
    public String toString() {
        if (this.originalMessage == null) {
            return super.toString();
        }
        return super.toString() + " for original " + this.originalMessage;
    }
}

