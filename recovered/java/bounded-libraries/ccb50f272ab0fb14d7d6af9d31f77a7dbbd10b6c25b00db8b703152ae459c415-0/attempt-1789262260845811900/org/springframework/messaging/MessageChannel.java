/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.messaging;

import org.springframework.messaging.Message;

@FunctionalInterface
public interface MessageChannel {
    public static final long INDEFINITE_TIMEOUT = -1L;

    default public boolean send(Message<?> message) {
        return this.send(message, -1L);
    }

    public boolean send(Message<?> var1, long var2);
}

