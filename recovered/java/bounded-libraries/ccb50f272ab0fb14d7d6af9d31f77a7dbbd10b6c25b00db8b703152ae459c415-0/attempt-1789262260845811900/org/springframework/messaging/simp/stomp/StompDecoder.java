/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.springframework.lang.Nullable
 *  org.springframework.util.InvalidMimeTypeException
 *  org.springframework.util.MultiValueMap
 *  org.springframework.util.StreamUtils
 */
package org.springframework.messaging.simp.stomp;

import java.io.ByteArrayOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpLogging;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompConversionException;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderInitializer;
import org.springframework.util.InvalidMimeTypeException;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;

public class StompDecoder {
    static final byte[] HEARTBEAT_PAYLOAD = new byte[]{10};
    private static final Log logger = SimpLogging.forLogName(StompDecoder.class);
    @Nullable
    private MessageHeaderInitializer headerInitializer;

    public void setHeaderInitializer(@Nullable MessageHeaderInitializer headerInitializer) {
        this.headerInitializer = headerInitializer;
    }

    @Nullable
    public MessageHeaderInitializer getHeaderInitializer() {
        return this.headerInitializer;
    }

    public List<Message<byte[]>> decode(ByteBuffer byteBuffer) {
        return this.decode(byteBuffer, null);
    }

    public List<Message<byte[]>> decode(ByteBuffer byteBuffer, @Nullable MultiValueMap<String, String> partialMessageHeaders) {
        Message<byte[]> message;
        ArrayList<Message<byte[]>> messages = new ArrayList<Message<byte[]>>();
        while (byteBuffer.hasRemaining() && (message = this.decodeMessage(byteBuffer, partialMessageHeaders)) != null) {
            messages.add(message);
            this.skipEol(byteBuffer);
            if (byteBuffer.hasRemaining()) continue;
            break;
        }
        return messages;
    }

    @Nullable
    private Message<byte[]> decodeMessage(ByteBuffer byteBuffer, @Nullable MultiValueMap<String, String> headers) {
        Message<byte[]> decodedMessage = null;
        this.skipEol(byteBuffer);
        ByteBuffer buffer = byteBuffer;
        ((Buffer)buffer).mark();
        String command = this.readCommand(byteBuffer);
        if (command.length() > 0) {
            StompCommand stompCommand;
            StompHeaderAccessor headerAccessor = null;
            byte[] payload = null;
            if (byteBuffer.remaining() > 0) {
                stompCommand = StompCommand.valueOf(command);
                headerAccessor = StompHeaderAccessor.create(stompCommand);
                this.initHeaders(headerAccessor);
                this.readHeaders(byteBuffer, headerAccessor);
                payload = this.readPayload(byteBuffer, headerAccessor);
            }
            if (payload != null) {
                if (payload.length > 0 && (stompCommand = headerAccessor.getCommand()) != null && !stompCommand.isBodyAllowed()) {
                    throw new StompConversionException((Object)((Object)stompCommand) + " shouldn't have a payload: length=" + payload.length + ", headers=" + headers);
                }
                headerAccessor.updateSimpMessageHeadersFromStompHeaders();
                headerAccessor.setLeaveMutable(true);
                decodedMessage = MessageBuilder.createMessage(payload, headerAccessor.getMessageHeaders());
                if (logger.isTraceEnabled()) {
                    logger.trace((Object)("Decoded " + headerAccessor.getDetailedLogMessage(payload)));
                }
            } else {
                String name;
                MultiValueMap map;
                logger.trace((Object)"Incomplete frame, resetting input buffer...");
                if (headers != null && headerAccessor != null && (map = (MultiValueMap)headerAccessor.getHeader(name = "nativeHeaders")) != null) {
                    headers.putAll((Map)map);
                }
                ((Buffer)buffer).reset();
            }
        } else {
            StompHeaderAccessor headerAccessor = StompHeaderAccessor.createForHeartbeat();
            this.initHeaders(headerAccessor);
            headerAccessor.setLeaveMutable(true);
            decodedMessage = MessageBuilder.createMessage(HEARTBEAT_PAYLOAD, headerAccessor.getMessageHeaders());
            if (logger.isTraceEnabled()) {
                logger.trace((Object)("Decoded " + headerAccessor.getDetailedLogMessage(null)));
            }
        }
        return decodedMessage;
    }

    private void initHeaders(StompHeaderAccessor headerAccessor) {
        MessageHeaderInitializer initializer = this.getHeaderInitializer();
        if (initializer != null) {
            initializer.initHeaders(headerAccessor);
        }
    }

    protected void skipEol(ByteBuffer byteBuffer) {
        while (this.tryConsumeEndOfLine(byteBuffer)) {
        }
    }

    private String readCommand(ByteBuffer byteBuffer) {
        ByteArrayOutputStream command = new ByteArrayOutputStream(256);
        while (byteBuffer.remaining() > 0 && !this.tryConsumeEndOfLine(byteBuffer)) {
            command.write(byteBuffer.get());
        }
        return StreamUtils.copyToString((ByteArrayOutputStream)command, (Charset)StandardCharsets.UTF_8);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void readHeaders(ByteBuffer byteBuffer, StompHeaderAccessor headerAccessor) {
        while (true) {
            ByteArrayOutputStream headerStream = new ByteArrayOutputStream(256);
            boolean headerComplete = false;
            while (byteBuffer.hasRemaining()) {
                if (this.tryConsumeEndOfLine(byteBuffer)) {
                    headerComplete = true;
                    break;
                }
                headerStream.write(byteBuffer.get());
            }
            if (headerStream.size() <= 0 || !headerComplete) return;
            String header = StreamUtils.copyToString((ByteArrayOutputStream)headerStream, (Charset)StandardCharsets.UTF_8);
            int colonIndex = header.indexOf(58);
            if (colonIndex <= 0) {
                if (byteBuffer.remaining() <= 0) continue;
                throw new StompConversionException("Illegal header: '" + header + "'. A header must be of the form <name>:[<value>].");
            }
            String headerName = this.unescape(header.substring(0, colonIndex));
            String headerValue = this.unescape(header.substring(colonIndex + 1));
            try {
                headerAccessor.addNativeHeader(headerName, headerValue);
                continue;
            }
            catch (InvalidMimeTypeException ex) {
                if (byteBuffer.remaining() > 0) throw ex;
                continue;
            }
            break;
        }
    }

    private String unescape(String inString) {
        StringBuilder sb = new StringBuilder(inString.length());
        int pos = 0;
        int index = inString.indexOf(92);
        while (index >= 0) {
            sb.append(inString, pos, index);
            if (index + 1 >= inString.length()) {
                throw new StompConversionException("Illegal escape sequence at index " + index + ": " + inString);
            }
            char c = inString.charAt(index + 1);
            if (c == 'r') {
                sb.append('\r');
            } else if (c == 'n') {
                sb.append('\n');
            } else if (c == 'c') {
                sb.append(':');
            } else if (c == '\\') {
                sb.append('\\');
            } else {
                throw new StompConversionException("Illegal escape sequence at index " + index + ": " + inString);
            }
            pos = index + 2;
            index = inString.indexOf(92, pos);
        }
        sb.append(inString.substring(pos));
        return sb.toString();
    }

    @Nullable
    private byte[] readPayload(ByteBuffer byteBuffer, StompHeaderAccessor headerAccessor) {
        Integer contentLength;
        try {
            contentLength = headerAccessor.getContentLength();
        }
        catch (NumberFormatException ex) {
            if (logger.isDebugEnabled()) {
                logger.debug((Object)("Ignoring invalid content-length: '" + headerAccessor));
            }
            contentLength = null;
        }
        if (contentLength != null && contentLength >= 0) {
            if (byteBuffer.remaining() > contentLength) {
                byte[] payload = new byte[contentLength.intValue()];
                byteBuffer.get(payload);
                if (byteBuffer.get() != 0) {
                    throw new StompConversionException("Frame must be terminated with a null octet");
                }
                return payload;
            }
            return null;
        }
        ByteArrayOutputStream payload = new ByteArrayOutputStream(256);
        while (byteBuffer.remaining() > 0) {
            byte b = byteBuffer.get();
            if (b == 0) {
                return payload.toByteArray();
            }
            payload.write(b);
        }
        return null;
    }

    private boolean tryConsumeEndOfLine(ByteBuffer byteBuffer) {
        if (byteBuffer.remaining() > 0) {
            byte b = byteBuffer.get();
            if (b == 10) {
                return true;
            }
            if (b == 13) {
                if (byteBuffer.remaining() > 0 && byteBuffer.get() == 10) {
                    return true;
                }
                throw new StompConversionException("'\\r' must be followed by '\\n'");
            }
            ((Buffer)byteBuffer).position(byteBuffer.position() - 1);
        }
        return false;
    }
}

