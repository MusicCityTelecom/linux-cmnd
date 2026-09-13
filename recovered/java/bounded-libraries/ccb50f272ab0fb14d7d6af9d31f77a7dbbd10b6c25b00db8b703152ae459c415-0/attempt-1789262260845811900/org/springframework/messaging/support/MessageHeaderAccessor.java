/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.IdGenerator
 *  org.springframework.util.MimeType
 *  org.springframework.util.MimeTypeUtils
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.PatternMatchUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.messaging.support;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.util.Assert;
import org.springframework.util.IdGenerator;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

public class MessageHeaderAccessor {
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static final MimeType[] READABLE_MIME_TYPES = new MimeType[]{MimeTypeUtils.APPLICATION_JSON, MimeTypeUtils.APPLICATION_XML, new MimeType("text", "*"), new MimeType("application", "*+json"), new MimeType("application", "*+xml")};
    private final MutableMessageHeaders headers;
    private boolean leaveMutable = false;
    private boolean modified = false;
    private boolean enableTimestamp = false;
    @Nullable
    private IdGenerator idGenerator;

    public MessageHeaderAccessor() {
        this(null);
    }

    public MessageHeaderAccessor(@Nullable Message<?> message) {
        this.headers = new MutableMessageHeaders(message != null ? message.getHeaders() : null);
    }

    protected MessageHeaderAccessor createAccessor(Message<?> message) {
        return new MessageHeaderAccessor(message);
    }

    public void setLeaveMutable(boolean leaveMutable) {
        Assert.state((boolean)this.headers.isMutable(), (String)"Already immutable");
        this.leaveMutable = leaveMutable;
    }

    public void setImmutable() {
        this.headers.setImmutable();
    }

    public boolean isMutable() {
        return this.headers.isMutable();
    }

    protected void setModified(boolean modified) {
        this.modified = modified;
    }

    public boolean isModified() {
        return this.modified;
    }

    void setEnableTimestamp(boolean enableTimestamp) {
        this.enableTimestamp = enableTimestamp;
    }

    void setIdGenerator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public MessageHeaders getMessageHeaders() {
        if (!this.leaveMutable) {
            this.setImmutable();
        }
        return this.headers;
    }

    public MessageHeaders toMessageHeaders() {
        return new MessageHeaders(this.headers);
    }

    public Map<String, Object> toMap() {
        return new HashMap<String, Object>(this.headers);
    }

    @Nullable
    public Object getHeader(String headerName) {
        return this.headers.get(headerName);
    }

    public void setHeader(String name, @Nullable Object value) {
        if (this.isReadOnly(name)) {
            throw new IllegalArgumentException("'" + name + "' header is read-only");
        }
        this.verifyType(name, value);
        if (value != null) {
            if (!ObjectUtils.nullSafeEquals((Object)value, (Object)this.getHeader(name))) {
                this.modified = true;
                this.headers.getRawHeaders().put(name, value);
            }
        } else if (this.headers.containsKey(name)) {
            this.modified = true;
            this.headers.getRawHeaders().remove(name);
        }
    }

    protected void verifyType(@Nullable String headerName, @Nullable Object headerValue) {
        if (headerName != null && headerValue != null && ("errorChannel".equals(headerName) || "replyChannel".endsWith(headerName)) && !(headerValue instanceof MessageChannel) && !(headerValue instanceof String)) {
            throw new IllegalArgumentException("'" + headerName + "' header value must be a MessageChannel or String");
        }
    }

    public void setHeaderIfAbsent(String name, Object value) {
        if (this.getHeader(name) == null) {
            this.setHeader(name, value);
        }
    }

    public void removeHeader(String headerName) {
        if (StringUtils.hasLength((String)headerName) && !this.isReadOnly(headerName)) {
            this.setHeader(headerName, null);
        }
    }

    public void removeHeaders(String ... headerPatterns) {
        ArrayList<String> headersToRemove = new ArrayList<String>();
        for (String pattern : headerPatterns) {
            if (!StringUtils.hasLength((String)pattern)) continue;
            if (pattern.contains("*")) {
                headersToRemove.addAll(this.getMatchingHeaderNames(pattern, this.headers));
                continue;
            }
            headersToRemove.add(pattern);
        }
        for (String headerToRemove : headersToRemove) {
            this.removeHeader(headerToRemove);
        }
    }

    private List<String> getMatchingHeaderNames(String pattern, @Nullable Map<String, Object> headers) {
        if (headers == null) {
            return Collections.emptyList();
        }
        ArrayList<String> matchingHeaderNames = new ArrayList<String>();
        for (String key : headers.keySet()) {
            if (!PatternMatchUtils.simpleMatch((String)pattern, (String)key)) continue;
            matchingHeaderNames.add(key);
        }
        return matchingHeaderNames;
    }

    public void copyHeaders(@Nullable Map<String, ?> headersToCopy) {
        if (headersToCopy == null || this.headers == headersToCopy) {
            return;
        }
        headersToCopy.forEach((key, value) -> {
            if (!this.isReadOnly((String)key)) {
                this.setHeader((String)key, value);
            }
        });
    }

    public void copyHeadersIfAbsent(@Nullable Map<String, ?> headersToCopy) {
        if (headersToCopy == null || this.headers == headersToCopy) {
            return;
        }
        headersToCopy.forEach((key, value) -> {
            if (!this.isReadOnly((String)key)) {
                this.setHeaderIfAbsent((String)key, value);
            }
        });
    }

    protected boolean isReadOnly(String headerName) {
        return "id".equals(headerName) || "timestamp".equals(headerName);
    }

    @Nullable
    public UUID getId() {
        Object value = this.getHeader("id");
        if (value == null) {
            return null;
        }
        return value instanceof UUID ? (UUID)value : UUID.fromString(value.toString());
    }

    @Nullable
    public Long getTimestamp() {
        Object value = this.getHeader("timestamp");
        if (value == null) {
            return null;
        }
        return value instanceof Long ? (Long)value : Long.parseLong(value.toString());
    }

    public void setContentType(MimeType contentType) {
        this.setHeader("contentType", contentType);
    }

    @Nullable
    public MimeType getContentType() {
        Object value = this.getHeader("contentType");
        if (value == null) {
            return null;
        }
        return value instanceof MimeType ? (MimeType)value : MimeType.valueOf((String)value.toString());
    }

    private Charset getCharset() {
        MimeType contentType = this.getContentType();
        Charset charset = contentType != null ? contentType.getCharset() : null;
        return charset != null ? charset : DEFAULT_CHARSET;
    }

    public void setReplyChannelName(String replyChannelName) {
        this.setHeader("replyChannel", replyChannelName);
    }

    public void setReplyChannel(MessageChannel replyChannel) {
        this.setHeader("replyChannel", replyChannel);
    }

    @Nullable
    public Object getReplyChannel() {
        return this.getHeader("replyChannel");
    }

    public void setErrorChannelName(String errorChannelName) {
        this.setHeader("errorChannel", errorChannelName);
    }

    public void setErrorChannel(MessageChannel errorChannel) {
        this.setHeader("errorChannel", errorChannel);
    }

    @Nullable
    public Object getErrorChannel() {
        return this.getHeader("errorChannel");
    }

    public String getShortLogMessage(Object payload) {
        return "headers=" + this.headers.toString() + this.getShortPayloadLogMessage(payload);
    }

    public String getDetailedLogMessage(@Nullable Object payload) {
        return "headers=" + this.headers.toString() + this.getDetailedPayloadLogMessage(payload);
    }

    protected String getShortPayloadLogMessage(Object payload) {
        if (payload instanceof String) {
            String payloadText = (String)payload;
            return payloadText.length() < 80 ? " payload=" + payloadText : " payload=" + payloadText.substring(0, 80) + "...(truncated)";
        }
        if (payload instanceof byte[]) {
            byte[] bytes = (byte[])payload;
            if (this.isReadableContentType()) {
                return bytes.length < 80 ? " payload=" + new String(bytes, this.getCharset()) : " payload=" + new String(Arrays.copyOf(bytes, 80), this.getCharset()) + "...(truncated)";
            }
            return " payload=byte[" + bytes.length + "]";
        }
        String payloadText = payload.toString();
        return payloadText.length() < 80 ? " payload=" + payloadText : " payload=" + ObjectUtils.identityToString((Object)payload);
    }

    protected String getDetailedPayloadLogMessage(@Nullable Object payload) {
        if (payload instanceof String) {
            return " payload=" + payload;
        }
        if (payload instanceof byte[]) {
            byte[] bytes = (byte[])payload;
            if (this.isReadableContentType()) {
                return " payload=" + new String(bytes, this.getCharset());
            }
            return " payload=byte[" + bytes.length + "]";
        }
        return " payload=" + payload;
    }

    protected boolean isReadableContentType() {
        MimeType contentType = this.getContentType();
        for (MimeType mimeType : READABLE_MIME_TYPES) {
            if (!mimeType.includes(contentType)) continue;
            return true;
        }
        return false;
    }

    public String toString() {
        return this.getClass().getSimpleName() + " [headers=" + this.headers + "]";
    }

    @Nullable
    public static MessageHeaderAccessor getAccessor(Message<?> message) {
        return MessageHeaderAccessor.getAccessor(message.getHeaders(), null);
    }

    @Nullable
    public static <T extends MessageHeaderAccessor> T getAccessor(Message<?> message, @Nullable Class<T> requiredType) {
        return MessageHeaderAccessor.getAccessor(message.getHeaders(), requiredType);
    }

    @Nullable
    public static <T extends MessageHeaderAccessor> T getAccessor(MessageHeaders messageHeaders, @Nullable Class<T> requiredType) {
        if (messageHeaders instanceof MutableMessageHeaders) {
            MutableMessageHeaders mutableHeaders = (MutableMessageHeaders)messageHeaders;
            MessageHeaderAccessor headerAccessor = mutableHeaders.getAccessor();
            if (requiredType == null || requiredType.isInstance(headerAccessor)) {
                return (T)headerAccessor;
            }
        }
        return null;
    }

    public static MessageHeaderAccessor getMutableAccessor(Message<?> message) {
        if (message.getHeaders() instanceof MutableMessageHeaders) {
            MutableMessageHeaders mutableHeaders = (MutableMessageHeaders)message.getHeaders();
            MessageHeaderAccessor accessor = mutableHeaders.getAccessor();
            return accessor.isMutable() ? accessor : accessor.createAccessor(message);
        }
        return new MessageHeaderAccessor(message);
    }

    private class MutableMessageHeaders
    extends MessageHeaders {
        private boolean mutable;

        public MutableMessageHeaders(Map<String, Object> headers) {
            super(headers, MessageHeaders.ID_VALUE_NONE, -1L);
            this.mutable = true;
        }

        @Override
        public Map<String, Object> getRawHeaders() {
            Assert.state((boolean)this.mutable, (String)"Already immutable");
            return super.getRawHeaders();
        }

        public void setImmutable() {
            IdGenerator idGenerator;
            UUID id;
            if (!this.mutable) {
                return;
            }
            if (this.getId() == null && (id = (idGenerator = MessageHeaderAccessor.this.idGenerator != null ? MessageHeaderAccessor.this.idGenerator : MessageHeaders.getIdGenerator()).generateId()) != MessageHeaders.ID_VALUE_NONE) {
                this.getRawHeaders().put("id", id);
            }
            if (this.getTimestamp() == null && MessageHeaderAccessor.this.enableTimestamp) {
                this.getRawHeaders().put("timestamp", System.currentTimeMillis());
            }
            this.mutable = false;
        }

        public boolean isMutable() {
            return this.mutable;
        }

        public MessageHeaderAccessor getAccessor() {
            return MessageHeaderAccessor.this;
        }

        protected Object writeReplace() {
            return new MessageHeaders(this);
        }
    }
}

