/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.mapping;

import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;

@FunctionalInterface
public interface OutboundMessageMapper<T> {
    @Nullable
    public T fromMessage(Message<?> var1);
}

