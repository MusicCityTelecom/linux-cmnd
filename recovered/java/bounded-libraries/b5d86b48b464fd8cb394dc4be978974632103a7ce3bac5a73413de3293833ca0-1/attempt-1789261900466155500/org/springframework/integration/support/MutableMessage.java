/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.integration.support;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import org.springframework.integration.support.MutableMessageHeaders;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

public class MutableMessage<T>
implements Message<T>,
Serializable {
    private static final long serialVersionUID = -636635024258737500L;
    private final T payload;
    private final MutableMessageHeaders headers;

    public MutableMessage(T payload) {
        this(payload, (Map<String, Object>)null);
    }

    public MutableMessage(T payload, @Nullable Map<String, Object> headers) {
        this(payload, new MutableMessageHeaders(headers));
    }

    protected MutableMessage(T payload, MutableMessageHeaders headers) {
        Assert.notNull(payload, (String)"payload must not be null");
        Assert.notNull((Object)((Object)headers), (String)"headers must not be null");
        this.payload = payload;
        this.headers = headers;
    }

    public MutableMessageHeaders getHeaders() {
        return this.headers;
    }

    public T getPayload() {
        return this.payload;
    }

    Map<String, Object> getRawHeaders() {
        return this.headers.getRawHeaders();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(" [payload=");
        if (this.payload instanceof byte[]) {
            sb.append("byte[").append(((byte[])this.payload).length).append("]");
        } else {
            sb.append(this.payload);
        }
        sb.append(", headers=").append((Object)this.headers).append("]");
        return sb.toString();
    }

    public int hashCode() {
        return this.headers.hashCode() * 23 + ObjectUtils.nullSafeHashCode(this.payload);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MutableMessage) {
            UUID otherId;
            MutableMessage other = (MutableMessage)obj;
            UUID thisId = this.headers.getId();
            return ObjectUtils.nullSafeEquals((Object)thisId, (Object)(otherId = other.headers.getId())) && this.headers.equals((Object)other.headers) && this.payload.equals(other.payload);
        }
        return false;
    }
}

