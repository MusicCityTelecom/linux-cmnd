/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.support.json;

import java.lang.reflect.Type;
import java.util.Map;
import org.springframework.integration.support.json.AbstractJsonInboundMessageMapper;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class JsonInboundMessageMapper
extends AbstractJsonInboundMessageMapper<JsonMessageParser<?>> {
    private volatile JsonMessageParser<?> messageParser;

    public JsonInboundMessageMapper(Class<?> payloadType, JsonMessageParser<?> messageParser) {
        this((Type)payloadType, messageParser);
    }

    public JsonInboundMessageMapper(Type payloadType, JsonMessageParser<?> messageParser) {
        super(payloadType);
        Assert.notNull(messageParser, (String)"'messageParser' cannot be null");
        this.messageParser = messageParser;
    }

    public Type getPayloadType() {
        return this.payloadType;
    }

    public Map<String, Class<?>> getHeaderTypes() {
        return this.headerTypes;
    }

    @Override
    public Message<?> toMessage(String jsonMessage, @Nullable Map<String, Object> headers) {
        return this.messageParser.doInParser(this, jsonMessage, headers);
    }

    @Override
    protected Map<String, Object> readHeaders(JsonMessageParser<?> parser, String jsonMessage) {
        return null;
    }

    @Override
    protected Object readPayload(JsonMessageParser<?> parser, String jsonMessage) {
        return null;
    }

    public static interface JsonMessageParser<P> {
        public Message<?> doInParser(JsonInboundMessageMapper var1, String var2, @Nullable Map<String, Object> var3);
    }
}

