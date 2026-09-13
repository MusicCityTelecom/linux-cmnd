/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.support;

import java.util.Arrays;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.support.MessageBuilderFactory;
import org.springframework.messaging.Message;

public class DefaultMessageBuilderFactory
implements MessageBuilderFactory {
    private String[] readOnlyHeaders;

    public void setReadOnlyHeaders(String ... readOnlyHeaders) {
        this.readOnlyHeaders = readOnlyHeaders != null ? Arrays.copyOf(readOnlyHeaders, readOnlyHeaders.length) : null;
    }

    public void addReadOnlyHeaders(String ... readOnlyHeaders) {
        String[] headers = this.readOnlyHeaders;
        if (headers == null || headers.length == 0) {
            headers = Arrays.copyOf(readOnlyHeaders, readOnlyHeaders.length);
        } else {
            headers = Arrays.copyOf(headers, headers.length + readOnlyHeaders.length);
            System.arraycopy(readOnlyHeaders, 0, headers, this.readOnlyHeaders.length, readOnlyHeaders.length);
        }
        this.readOnlyHeaders = headers;
    }

    public <T> MessageBuilder<T> fromMessage(Message<T> message) {
        return MessageBuilder.fromMessage(message).readOnlyHeaders(this.readOnlyHeaders);
    }

    public <T> MessageBuilder<T> withPayload(T payload) {
        return MessageBuilder.withPayload(payload).readOnlyHeaders(this.readOnlyHeaders);
    }
}

