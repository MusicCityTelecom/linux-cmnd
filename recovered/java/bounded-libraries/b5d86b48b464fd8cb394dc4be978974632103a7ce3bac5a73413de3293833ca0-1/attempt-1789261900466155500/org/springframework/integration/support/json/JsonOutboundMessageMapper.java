/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.support.json;

import java.io.IOException;
import java.io.UncheckedIOException;
import org.springframework.integration.mapping.OutboundMessageMapper;
import org.springframework.integration.support.json.JsonObjectMapper;
import org.springframework.integration.support.json.JsonObjectMapperProvider;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class JsonOutboundMessageMapper
implements OutboundMessageMapper<String> {
    private volatile boolean shouldExtractPayload = false;
    private volatile JsonObjectMapper<?, ?> jsonObjectMapper;

    public JsonOutboundMessageMapper() {
        this(JsonObjectMapperProvider.newInstance());
    }

    public JsonOutboundMessageMapper(JsonObjectMapper<?, ?> jsonObjectMapper) {
        Assert.notNull(jsonObjectMapper, (String)"jsonObjectMapper must not be null");
        this.jsonObjectMapper = jsonObjectMapper;
    }

    public void setShouldExtractPayload(boolean shouldExtractPayload) {
        this.shouldExtractPayload = shouldExtractPayload;
    }

    @Override
    public String fromMessage(Message<?> message) {
        try {
            return this.jsonObjectMapper.toJson(this.shouldExtractPayload ? message.getPayload() : message);
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}

