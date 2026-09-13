/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.messaging;

import org.springframework.messaging.MessageHeaders;

public interface Message<T> {
    public T getPayload();

    public MessageHeaders getHeaders();
}

