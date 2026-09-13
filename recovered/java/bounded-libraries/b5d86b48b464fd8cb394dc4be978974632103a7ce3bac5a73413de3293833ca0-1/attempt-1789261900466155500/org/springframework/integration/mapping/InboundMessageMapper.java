/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.mapping;

import java.util.Map;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;

@FunctionalInterface
public interface InboundMessageMapper<T> {
    @Nullable
    default public Message<?> toMessage(T object) {
        return this.toMessage(object, null);
    }

    @Nullable
    public Message<?> toMessage(T var1, @Nullable Map<String, Object> var2);
}

