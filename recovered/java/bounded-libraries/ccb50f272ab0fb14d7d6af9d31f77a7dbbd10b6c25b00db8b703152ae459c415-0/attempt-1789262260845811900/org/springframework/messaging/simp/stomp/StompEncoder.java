/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.messaging.simp.stomp;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpLogging;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompDecoder;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.util.Assert;

public class StompEncoder {
    private static final Byte LINE_FEED_BYTE = 10;
    private static final Byte COLON_BYTE = 58;
    private static final Log logger = SimpLogging.forLogName(StompEncoder.class);
    private static final int HEADER_KEY_CACHE_LIMIT = 32;
    private final Map<String, byte[]> headerKeyAccessCache = new ConcurrentHashMap<String, byte[]>(32);
    private final Map<String, byte[]> headerKeyUpdateCache = new LinkedHashMap<String, byte[]>(32, 0.75f, true){

        @Override
        protected boolean removeEldestEntry(Map.Entry<String, byte[]> eldest) {
            if (this.size() > 32) {
                StompEncoder.this.headerKeyAccessCache.remove(eldest.getKey());
                return true;
            }
            return false;
        }
    };

    public byte[] encode(Message<byte[]> message) {
        return this.encode(message.getHeaders(), message.getPayload());
    }

    public byte[] encode(Map<String, Object> headers, byte[] payload) {
        Assert.notNull(headers, (String)"'headers' is required");
        Assert.notNull((Object)payload, (String)"'payload' is required");
        if (SimpMessageType.HEARTBEAT.equals((Object)SimpMessageHeaderAccessor.getMessageType(headers))) {
            logger.trace((Object)"Encoding heartbeat");
            return StompDecoder.HEARTBEAT_PAYLOAD;
        }
        StompCommand command = StompHeaderAccessor.getCommand(headers);
        if (command == null) {
            throw new IllegalStateException("Missing STOMP command: " + headers);
        }
        DefaultResult result = new DefaultResult();
        result.add(command.toString().getBytes(StandardCharsets.UTF_8));
        result.add(LINE_FEED_BYTE);
        this.writeHeaders(command, headers, payload, result);
        result.add(LINE_FEED_BYTE);
        result.add(payload);
        result.add((byte)0);
        return result.toByteArray();
    }

    private void writeHeaders(StompCommand command, Map<String, Object> headers, byte[] payload, Result result) {
        Map nativeHeaders = (Map)headers.get("nativeHeaders");
        if (logger.isTraceEnabled()) {
            logger.trace((Object)("Encoding STOMP " + (Object)((Object)command) + ", headers=" + nativeHeaders));
        }
        if (nativeHeaders == null) {
            return;
        }
        boolean shouldEscape = command != StompCommand.CONNECT && command != StompCommand.STOMP && command != StompCommand.CONNECTED;
        for (Map.Entry entry : nativeHeaders.entrySet()) {
            if (command.requiresContentLength() && "content-length".equals(entry.getKey())) continue;
            List<String> values = (List<String>)entry.getValue();
            if ((StompCommand.CONNECT.equals((Object)command) || StompCommand.STOMP.equals((Object)command)) && "passcode".equals(entry.getKey())) {
                values = Collections.singletonList(StompHeaderAccessor.getPasscode(headers));
            }
            byte[] encodedKey = this.encodeHeaderKey((String)entry.getKey(), shouldEscape);
            for (String value : values) {
                result.add(encodedKey);
                result.add(COLON_BYTE);
                result.add(this.encodeHeaderValue(value, shouldEscape));
                result.add(LINE_FEED_BYTE);
            }
        }
        if (command.requiresContentLength()) {
            int contentLength = payload.length;
            result.add("content-length:".getBytes(StandardCharsets.UTF_8));
            result.add(Integer.toString(contentLength).getBytes(StandardCharsets.UTF_8));
            result.add(LINE_FEED_BYTE);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private byte[] encodeHeaderKey(String input, boolean escape) {
        String inputToUse;
        String string = inputToUse = escape ? this.escape(input) : input;
        if (this.headerKeyAccessCache.containsKey(inputToUse)) {
            return this.headerKeyAccessCache.get(inputToUse);
        }
        Map<String, byte[]> map = this.headerKeyUpdateCache;
        synchronized (map) {
            byte[] bytes = this.headerKeyUpdateCache.get(inputToUse);
            if (bytes == null) {
                bytes = inputToUse.getBytes(StandardCharsets.UTF_8);
                this.headerKeyAccessCache.put(inputToUse, bytes);
                this.headerKeyUpdateCache.put(inputToUse, bytes);
            }
            return bytes;
        }
    }

    private byte[] encodeHeaderValue(String input, boolean escape) {
        String inputToUse = escape ? this.escape(input) : input;
        return inputToUse.getBytes(StandardCharsets.UTF_8);
    }

    private String escape(String inString) {
        StringBuilder sb = null;
        for (int i2 = 0; i2 < inString.length(); ++i2) {
            char c = inString.charAt(i2);
            if (c == '\\') {
                sb = this.getStringBuilder(sb, inString, i2);
                sb.append("\\\\");
                continue;
            }
            if (c == ':') {
                sb = this.getStringBuilder(sb, inString, i2);
                sb.append("\\c");
                continue;
            }
            if (c == '\n') {
                sb = this.getStringBuilder(sb, inString, i2);
                sb.append("\\n");
                continue;
            }
            if (c == '\r') {
                sb = this.getStringBuilder(sb, inString, i2);
                sb.append("\\r");
                continue;
            }
            if (sb == null) continue;
            sb.append(c);
        }
        return sb != null ? sb.toString() : inString;
    }

    private StringBuilder getStringBuilder(@Nullable StringBuilder sb, String inString, int i2) {
        if (sb == null) {
            sb = new StringBuilder(inString.length());
            sb.append(inString, 0, i2);
        }
        return sb;
    }

    private static class DefaultResult
    extends ArrayList<Object>
    implements Result {
        private int size;

        private DefaultResult() {
        }

        @Override
        public void add(byte[] bytes) {
            this.size += bytes.length;
            super.add(bytes);
        }

        @Override
        public void add(byte b) {
            ++this.size;
            super.add(b);
        }

        @Override
        public byte[] toByteArray() {
            byte[] result = new byte[this.size];
            int position = 0;
            for (Object o : this) {
                if (o instanceof byte[]) {
                    byte[] src = (byte[])o;
                    System.arraycopy(src, 0, result, position, src.length);
                    position += src.length;
                    continue;
                }
                result[position++] = (Byte)o;
            }
            return result;
        }
    }

    private static interface Result {
        public void add(byte[] var1);

        public void add(byte var1);

        public byte[] toByteArray();
    }
}

