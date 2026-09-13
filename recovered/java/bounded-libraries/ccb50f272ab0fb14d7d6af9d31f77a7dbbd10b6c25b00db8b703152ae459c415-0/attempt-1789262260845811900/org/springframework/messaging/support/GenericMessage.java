/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.messaging.support;

import java.io.Serializable;
import java.util.Map;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

public class GenericMessage<T>
implements Message<T>,
Serializable {
    private static final long serialVersionUID = 4268801052358035098L;
    private final T payload;
    private final MessageHeaders headers;

    public GenericMessage(T payload) {
        this(payload, new MessageHeaders(null));
    }

    public GenericMessage(T payload, Map<String, Object> headers) {
        this(payload, new MessageHeaders(headers));
    }

    public GenericMessage(T payload, MessageHeaders headers) {
        Assert.notNull(payload, (String)"Payload must not be null");
        Assert.notNull((Object)headers, (String)"MessageHeaders must not be null");
        this.payload = payload;
        this.headers = headers;
    }

    @Override
    public T getPayload() {
        return this.payload;
    }

    @Override
    public MessageHeaders getHeaders() {
        return this.headers;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GenericMessage)) {
            return false;
        }
        GenericMessage otherMsg = (GenericMessage)other;
        return ObjectUtils.nullSafeEquals(this.payload, otherMsg.payload) && this.headers.equals(otherMsg.headers);
    }

    public int hashCode() {
        return ObjectUtils.nullSafeHashCode(this.payload) * 23 + this.headers.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(" [payload=");
        if (this.payload instanceof byte[]) {
            sb.append("byte[").append(((byte[])this.payload).length).append(']');
        } else {
            sb.append(this.payload);
        }
        sb.append(", headers=").append(this.headers).append(']');
        return sb.toString();
    }
}

