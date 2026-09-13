/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.NonNull
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.mapping;

import java.util.Map;
import org.springframework.integration.mapping.InboundMessageMapper;
import org.springframework.integration.mapping.OutboundMessageMapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;

public interface BytesMessageMapper
extends InboundMessageMapper<byte[]>,
OutboundMessageMapper<byte[]> {
    @Override
    @NonNull
    default public Message<?> toMessage(byte[] object) {
        return this.toMessage(object, (Map<String, Object>)null);
    }

    @Override
    @NonNull
    public Message<?> toMessage(byte[] var1, @Nullable Map<String, Object> var2);
}

