/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.MessageHeaders
 */
package org.springframework.integration.support;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.UUID;
import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageHeaders;

public class MutableMessageHeaders
extends MessageHeaders {
    private static final long serialVersionUID = 3084692953798643018L;

    public MutableMessageHeaders(@Nullable Map<String, Object> headers) {
        super(headers, MutableMessageHeaders.extractId(headers), MutableMessageHeaders.extractTimestamp(headers));
    }

    protected MutableMessageHeaders(@Nullable Map<String, Object> headers, @Nullable UUID id, @Nullable Long timestamp) {
        super(headers, id, timestamp);
    }

    protected Map<String, Object> getRawHeaders() {
        return super.getRawHeaders();
    }

    public void putAll(Map<? extends String, ? extends Object> map) {
        super.getRawHeaders().putAll(map);
    }

    public Object put(String key, Object value) {
        return super.getRawHeaders().put(key, value);
    }

    public void clear() {
        super.getRawHeaders().clear();
    }

    public Object remove(Object key) {
        return super.getRawHeaders().remove(key);
    }

    @Nullable
    private static UUID extractId(@Nullable Map<String, Object> headers) {
        if (headers != null && headers.containsKey("id")) {
            Object id = headers.get("id");
            if (id instanceof String) {
                return UUID.fromString((String)id);
            }
            if (id instanceof byte[]) {
                ByteBuffer bb = ByteBuffer.wrap((byte[])id);
                return new UUID(bb.getLong(), bb.getLong());
            }
            return (UUID)id;
        }
        return null;
    }

    @Nullable
    private static Long extractTimestamp(@Nullable Map<String, Object> headers) {
        if (headers != null && headers.containsKey("timestamp")) {
            Object timestamp = headers.get("timestamp");
            return timestamp instanceof String ? Long.parseLong((String)timestamp) : (Long)timestamp;
        }
        return null;
    }
}

