/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonFactory
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonToken
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.support.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.integration.support.json.AbstractJacksonJsonMessageParser;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class Jackson2JsonMessageParser
extends AbstractJacksonJsonMessageParser<JsonParser> {
    public Jackson2JsonMessageParser() {
        this(new Jackson2JsonObjectMapper());
    }

    public Jackson2JsonMessageParser(Jackson2JsonObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    protected JsonParser createJsonParser(String jsonMessage) {
        try {
            return new JsonFactory().createParser(jsonMessage);
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    protected Message<?> parseWithHeaders(JsonParser parser, String jsonMessage, @Nullable Map<String, Object> headersToAdd) {
        try {
            String error = "JSON message is invalid.  Expected a message in the format of either {\"headers\":{...},\"payload\":{...}} or {\"payload\":{...}.\"headers\":{...}} but was " + jsonMessage;
            Assert.isTrue((JsonToken.START_OBJECT == parser.nextToken() ? 1 : 0) != 0, (String)error);
            Map<String, Object> headers = null;
            Object payload = null;
            while (JsonToken.END_OBJECT != parser.nextToken()) {
                Assert.isTrue((JsonToken.FIELD_NAME == parser.getCurrentToken() ? 1 : 0) != 0, (String)error);
                boolean isHeadersToken = "headers".equals(parser.getCurrentName());
                boolean isPayloadToken = "payload".equals(parser.getCurrentName());
                Assert.isTrue((isHeadersToken || isPayloadToken ? 1 : 0) != 0, (String)error);
                if (isHeadersToken) {
                    Assert.isTrue((parser.nextToken() == JsonToken.START_OBJECT ? 1 : 0) != 0, (String)error);
                    headers = this.readHeaders(parser, jsonMessage);
                    continue;
                }
                if (!isPayloadToken) continue;
                parser.nextToken();
                payload = this.readPayload(parser, jsonMessage);
            }
            Assert.notNull(headers, (String)error);
            return this.getMessageBuilderFactory().withPayload(payload).copyHeaders(headers).copyHeadersIfAbsent(headersToAdd).build();
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Map<String, Object> readHeaders(JsonParser parser, String jsonMessage) throws IOException {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<String, Object>();
        while (JsonToken.END_OBJECT != parser.nextToken()) {
            String headerName = parser.getCurrentName();
            parser.nextToken();
            Object headerValue = this.readHeader(parser, headerName, jsonMessage);
            headers.put(headerName, headerValue);
        }
        return headers;
    }
}

