/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHeaders
 *  org.springframework.messaging.support.GenericMessage
 */
package org.springframework.integration.support.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.mapping.BytesMessageMapper;
import org.springframework.integration.support.MutableMessage;
import org.springframework.integration.support.MutableMessageHeaders;
import org.springframework.integration.support.json.JacksonJsonUtils;
import org.springframework.integration.support.utils.PatternMatchUtils;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;

public class EmbeddedJsonHeadersMessageMapper
implements BytesMessageMapper {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private final ObjectMapper objectMapper;
    private final String[] headerPatterns;
    private final boolean allHeaders;
    private boolean rawBytes = true;
    private boolean caseSensitive;

    public EmbeddedJsonHeadersMessageMapper() {
        this("*");
    }

    public EmbeddedJsonHeadersMessageMapper(String ... headerPatterns) {
        this(JacksonJsonUtils.messagingAwareMapper(new String[0]), headerPatterns);
    }

    public EmbeddedJsonHeadersMessageMapper(ObjectMapper objectMapper) {
        this(objectMapper, "*");
    }

    public EmbeddedJsonHeadersMessageMapper(ObjectMapper objectMapper, String ... headerPatterns) {
        this.objectMapper = objectMapper;
        this.headerPatterns = Arrays.copyOf(headerPatterns, headerPatterns.length);
        this.allHeaders = this.headerPatterns.length == 1 && this.headerPatterns[0].equals("*");
    }

    public void setRawBytes(boolean rawBytes) {
        this.rawBytes = rawBytes;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public Collection<String> getHeaderPatterns() {
        return Arrays.asList(this.headerPatterns);
    }

    @Override
    public byte[] fromMessage(Message<?> message) {
        MessageHeaders headersToEncode;
        Object object = headersToEncode = this.allHeaders ? message.getHeaders() : this.pruneHeaders(message.getHeaders());
        if (this.rawBytes && message.getPayload() instanceof byte[]) {
            return this.fromBytesPayload((byte[])message.getPayload(), (Map<String, Object>)headersToEncode);
        }
        Message<?> messageToEncode = message;
        if (!this.allHeaders) {
            if (!headersToEncode.containsKey("id")) {
                headersToEncode.put("id", MessageHeaders.ID_VALUE_NONE);
            }
            if (!headersToEncode.containsKey("timestamp")) {
                headersToEncode.put("timestamp", -1L);
            }
            messageToEncode = new MutableMessage<Object>(message.getPayload(), (Map<String, Object>)headersToEncode);
        }
        try {
            return this.objectMapper.writeValueAsBytes(messageToEncode);
        }
        catch (JsonProcessingException e) {
            throw new UncheckedIOException((IOException)((Object)e));
        }
    }

    private Map<String, Object> pruneHeaders(MessageHeaders messageHeaders) {
        return messageHeaders.entrySet().stream().filter(e -> this.matchHeader((String)e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private boolean matchHeader(String header) {
        return Boolean.TRUE.equals(this.caseSensitive ? PatternMatchUtils.smartMatch(header, this.headerPatterns) : PatternMatchUtils.smartMatchIgnoreCase(header, this.headerPatterns));
    }

    private byte[] fromBytesPayload(byte[] payload, Map<String, Object> headersToEncode) {
        try {
            byte[] headers = this.objectMapper.writeValueAsBytes(headersToEncode);
            ByteBuffer buffer = ByteBuffer.wrap(new byte[8 + headers.length + payload.length]);
            buffer.putInt(headers.length);
            buffer.put(headers);
            buffer.putInt(payload.length);
            buffer.put(payload);
            return buffer.array();
        }
        catch (JsonProcessingException e) {
            throw new UncheckedIOException((IOException)((Object)e));
        }
    }

    @Override
    public Message<?> toMessage(byte[] bytes, @Nullable Map<String, Object> headers) {
        Message message = null;
        try {
            message = this.decodeNativeFormat(bytes, headers);
        }
        catch (Exception e) {
            this.logger.debug((Object)"Failed to decode native format", (Throwable)e);
        }
        if (message == null) {
            try {
                message = (Message)this.objectMapper.readValue(bytes, Object.class);
            }
            catch (Exception e) {
                this.logger.debug((Object)"Failed to decode JSON", (Throwable)e);
            }
        }
        if (message != null) {
            return message;
        }
        return headers == null ? new GenericMessage((Object)bytes) : new GenericMessage((Object)bytes, headers);
    }

    @Nullable
    private Message<?> decodeNativeFormat(byte[] bytes, @Nullable Map<String, Object> headersToAdd) throws IOException {
        int headersLen;
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        if (buffer.remaining() > 4 && (headersLen = buffer.getInt()) >= 0 && headersLen < buffer.remaining() - 4) {
            ((Buffer)buffer).position(headersLen + 4);
            int payloadLen = buffer.getInt();
            if (payloadLen != buffer.remaining()) {
                return null;
            }
            ((Buffer)buffer).position(4);
            Map headers = (Map)this.objectMapper.readValue(bytes, buffer.position(), headersLen, Map.class);
            ((Buffer)buffer).position(buffer.position() + headersLen);
            buffer.getInt();
            byte[] payloadBytes = new byte[payloadLen];
            buffer.get(payloadBytes);
            byte[] payload = payloadBytes;
            if (headersToAdd != null) {
                headersToAdd.forEach(headers::putIfAbsent);
            }
            return new GenericMessage((Object)payload, (MessageHeaders)new MutableMessageHeaders(headers));
        }
        return null;
    }
}

