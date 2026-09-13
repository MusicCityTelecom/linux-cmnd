/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.MimeType
 *  org.springframework.util.MimeTypeUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.messaging.simp.stomp;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StringUtils;

public class StompHeaderAccessor
extends SimpMessageHeaderAccessor {
    private static final AtomicLong messageIdCounter = new AtomicLong();
    private static final long[] DEFAULT_HEARTBEAT = new long[]{0L, 0L};
    public static final String STOMP_ID_HEADER = "id";
    public static final String STOMP_HOST_HEADER = "host";
    public static final String STOMP_ACCEPT_VERSION_HEADER = "accept-version";
    public static final String STOMP_MESSAGE_ID_HEADER = "message-id";
    public static final String STOMP_RECEIPT_HEADER = "receipt";
    public static final String STOMP_RECEIPT_ID_HEADER = "receipt-id";
    public static final String STOMP_SUBSCRIPTION_HEADER = "subscription";
    public static final String STOMP_VERSION_HEADER = "version";
    public static final String STOMP_MESSAGE_HEADER = "message";
    public static final String STOMP_ACK_HEADER = "ack";
    public static final String STOMP_NACK_HEADER = "nack";
    public static final String STOMP_LOGIN_HEADER = "login";
    public static final String STOMP_PASSCODE_HEADER = "passcode";
    public static final String STOMP_DESTINATION_HEADER = "destination";
    public static final String STOMP_CONTENT_TYPE_HEADER = "content-type";
    public static final String STOMP_CONTENT_LENGTH_HEADER = "content-length";
    public static final String STOMP_HEARTBEAT_HEADER = "heart-beat";
    private static final String COMMAND_HEADER = "stompCommand";
    private static final String CREDENTIALS_HEADER = "stompCredentials";

    StompHeaderAccessor(StompCommand command, @Nullable Map<String, List<String>> externalSourceHeaders) {
        super(command.getMessageType(), externalSourceHeaders);
        this.setHeader(COMMAND_HEADER, (Object)command);
        this.updateSimpMessageHeadersFromStompHeaders();
    }

    StompHeaderAccessor(Message<?> message) {
        super(message);
        this.updateStompHeadersFromSimpMessageHeaders();
    }

    StompHeaderAccessor() {
        super(SimpMessageType.HEARTBEAT, null);
    }

    void updateSimpMessageHeadersFromStompHeaders() {
        StompCommand command;
        if (this.getNativeHeaders() == null) {
            return;
        }
        String value = this.getFirstNativeHeader(STOMP_DESTINATION_HEADER);
        if (value != null) {
            super.setDestination(value);
        }
        if ((value = this.getFirstNativeHeader(STOMP_CONTENT_TYPE_HEADER)) != null) {
            super.setContentType(MimeTypeUtils.parseMimeType((String)value));
        }
        if (StompCommand.MESSAGE.equals((Object)(command = this.getCommand()))) {
            value = this.getFirstNativeHeader(STOMP_SUBSCRIPTION_HEADER);
            if (value != null) {
                super.setSubscriptionId(value);
            }
        } else if (StompCommand.SUBSCRIBE.equals((Object)command) || StompCommand.UNSUBSCRIBE.equals((Object)command)) {
            value = this.getFirstNativeHeader(STOMP_ID_HEADER);
            if (value != null) {
                super.setSubscriptionId(value);
            }
        } else if (StompCommand.CONNECT.equals((Object)command) || StompCommand.STOMP.equals((Object)command)) {
            this.protectPasscode();
        }
    }

    void updateStompHeadersFromSimpMessageHeaders() {
        MimeType contentType;
        String destination = this.getDestination();
        if (destination != null) {
            this.setNativeHeader(STOMP_DESTINATION_HEADER, destination);
        }
        if ((contentType = this.getContentType()) != null) {
            this.setNativeHeader(STOMP_CONTENT_TYPE_HEADER, contentType.toString());
        }
        this.trySetStompHeaderForSubscriptionId();
    }

    @Override
    protected MessageHeaderAccessor createAccessor(Message<?> message) {
        return StompHeaderAccessor.wrap(message);
    }

    @Override
    @Nullable
    protected Map<String, List<String>> getNativeHeaders() {
        return super.getNativeHeaders();
    }

    public StompCommand updateStompCommandAsClientMessage() {
        SimpMessageType messageType = this.getMessageType();
        if (messageType != SimpMessageType.MESSAGE) {
            throw new IllegalStateException("Unexpected message type " + (Object)((Object)messageType));
        }
        StompCommand command = this.getCommand();
        if (command == null) {
            command = StompCommand.SEND;
            this.setHeader(COMMAND_HEADER, (Object)command);
        } else if (!command.equals((Object)StompCommand.SEND)) {
            throw new IllegalStateException("Unexpected STOMP command " + (Object)((Object)command));
        }
        return command;
    }

    public void updateStompCommandAsServerMessage() {
        SimpMessageType messageType = this.getMessageType();
        if (messageType != SimpMessageType.MESSAGE) {
            throw new IllegalStateException("Unexpected message type " + (Object)((Object)messageType));
        }
        StompCommand command = this.getCommand();
        if (command == null || StompCommand.SEND.equals((Object)command)) {
            this.setHeader(COMMAND_HEADER, (Object)StompCommand.MESSAGE);
        } else if (!StompCommand.MESSAGE.equals((Object)command)) {
            throw new IllegalStateException("Unexpected STOMP command " + (Object)((Object)command));
        }
        this.trySetStompHeaderForSubscriptionId();
        if (this.getMessageId() == null) {
            String messageId = this.getSessionId() + '-' + messageIdCounter.getAndIncrement();
            this.setNativeHeader(STOMP_MESSAGE_ID_HEADER, messageId);
        }
    }

    @Nullable
    public StompCommand getCommand() {
        return (StompCommand)((Object)this.getHeader(COMMAND_HEADER));
    }

    public boolean isHeartbeat() {
        return SimpMessageType.HEARTBEAT == this.getMessageType();
    }

    public long[] getHeartbeat() {
        String rawValue = this.getFirstNativeHeader(STOMP_HEARTBEAT_HEADER);
        String[] rawValues = StringUtils.split((String)rawValue, (String)",");
        if (rawValues == null) {
            return Arrays.copyOf(DEFAULT_HEARTBEAT, 2);
        }
        return new long[]{Long.parseLong(rawValues[0]), Long.parseLong(rawValues[1])};
    }

    public void setAcceptVersion(String acceptVersion) {
        this.setNativeHeader(STOMP_ACCEPT_VERSION_HEADER, acceptVersion);
    }

    public Set<String> getAcceptVersion() {
        String rawValue = this.getFirstNativeHeader(STOMP_ACCEPT_VERSION_HEADER);
        return rawValue != null ? StringUtils.commaDelimitedListToSet((String)rawValue) : Collections.emptySet();
    }

    public void setHost(@Nullable String host) {
        this.setNativeHeader(STOMP_HOST_HEADER, host);
    }

    @Nullable
    public String getHost() {
        return this.getFirstNativeHeader(STOMP_HOST_HEADER);
    }

    @Override
    public void setDestination(@Nullable String destination) {
        super.setDestination(destination);
        this.setNativeHeader(STOMP_DESTINATION_HEADER, destination);
    }

    @Override
    public void setContentType(MimeType contentType) {
        super.setContentType(contentType);
        this.setNativeHeader(STOMP_CONTENT_TYPE_HEADER, contentType.toString());
    }

    @Override
    public void setSubscriptionId(@Nullable String subscriptionId) {
        super.setSubscriptionId(subscriptionId);
        this.trySetStompHeaderForSubscriptionId();
    }

    private void trySetStompHeaderForSubscriptionId() {
        String subscriptionId = this.getSubscriptionId();
        if (subscriptionId != null) {
            StompCommand command = this.getCommand();
            if (command != null && StompCommand.MESSAGE.equals((Object)command)) {
                this.setNativeHeader(STOMP_SUBSCRIPTION_HEADER, subscriptionId);
            } else {
                SimpMessageType messageType = this.getMessageType();
                if (SimpMessageType.SUBSCRIBE.equals((Object)messageType) || SimpMessageType.UNSUBSCRIBE.equals((Object)messageType)) {
                    this.setNativeHeader(STOMP_ID_HEADER, subscriptionId);
                }
            }
        }
    }

    @Nullable
    public Integer getContentLength() {
        String header = this.getFirstNativeHeader(STOMP_CONTENT_LENGTH_HEADER);
        return header != null ? Integer.valueOf(header) : null;
    }

    public void setContentLength(int contentLength) {
        this.setNativeHeader(STOMP_CONTENT_LENGTH_HEADER, String.valueOf(contentLength));
    }

    public void setHeartbeat(long cx, long cy) {
        this.setNativeHeader(STOMP_HEARTBEAT_HEADER, cx + "," + cy);
    }

    public void setAck(@Nullable String ack) {
        this.setNativeHeader(STOMP_ACK_HEADER, ack);
    }

    @Nullable
    public String getAck() {
        return this.getFirstNativeHeader(STOMP_ACK_HEADER);
    }

    public void setNack(@Nullable String nack) {
        this.setNativeHeader(STOMP_NACK_HEADER, nack);
    }

    @Nullable
    public String getNack() {
        return this.getFirstNativeHeader(STOMP_NACK_HEADER);
    }

    public void setLogin(@Nullable String login) {
        this.setNativeHeader(STOMP_LOGIN_HEADER, login);
    }

    @Nullable
    public String getLogin() {
        return this.getFirstNativeHeader(STOMP_LOGIN_HEADER);
    }

    public void setPasscode(@Nullable String passcode) {
        this.setNativeHeader(STOMP_PASSCODE_HEADER, passcode);
        this.protectPasscode();
    }

    private void protectPasscode() {
        String value = this.getFirstNativeHeader(STOMP_PASSCODE_HEADER);
        if (value != null && !"PROTECTED".equals(value)) {
            this.setHeader(CREDENTIALS_HEADER, new StompPasscode(value));
            this.setNativeHeader(STOMP_PASSCODE_HEADER, "PROTECTED");
        }
    }

    @Nullable
    public String getPasscode() {
        StompPasscode credentials = (StompPasscode)this.getHeader(CREDENTIALS_HEADER);
        return credentials != null ? credentials.passcode : null;
    }

    public void setReceiptId(@Nullable String receiptId) {
        this.setNativeHeader(STOMP_RECEIPT_ID_HEADER, receiptId);
    }

    @Nullable
    public String getReceiptId() {
        return this.getFirstNativeHeader(STOMP_RECEIPT_ID_HEADER);
    }

    public void setReceipt(@Nullable String receiptId) {
        this.setNativeHeader(STOMP_RECEIPT_HEADER, receiptId);
    }

    @Nullable
    public String getReceipt() {
        return this.getFirstNativeHeader(STOMP_RECEIPT_HEADER);
    }

    @Nullable
    public String getMessage() {
        return this.getFirstNativeHeader(STOMP_MESSAGE_HEADER);
    }

    public void setMessage(@Nullable String content) {
        this.setNativeHeader(STOMP_MESSAGE_HEADER, content);
    }

    @Nullable
    public String getMessageId() {
        return this.getFirstNativeHeader(STOMP_MESSAGE_ID_HEADER);
    }

    public void setMessageId(@Nullable String id) {
        this.setNativeHeader(STOMP_MESSAGE_ID_HEADER, id);
    }

    @Nullable
    public String getVersion() {
        return this.getFirstNativeHeader(STOMP_VERSION_HEADER);
    }

    public void setVersion(@Nullable String version) {
        this.setNativeHeader(STOMP_VERSION_HEADER, version);
    }

    @Override
    public String getShortLogMessage(Object payload) {
        StompCommand command = this.getCommand();
        if (StompCommand.SUBSCRIBE.equals((Object)command)) {
            return "SUBSCRIBE " + this.getDestination() + " id=" + this.getSubscriptionId() + this.appendSession();
        }
        if (StompCommand.UNSUBSCRIBE.equals((Object)command)) {
            return "UNSUBSCRIBE id=" + this.getSubscriptionId() + this.appendSession();
        }
        if (StompCommand.SEND.equals((Object)command)) {
            return "SEND " + this.getDestination() + this.appendSession() + this.appendPayload(payload);
        }
        if (StompCommand.CONNECT.equals((Object)command)) {
            Principal user = this.getUser();
            return "CONNECT" + (user != null ? " user=" + user.getName() : "") + this.appendSession();
        }
        if (StompCommand.STOMP.equals((Object)command)) {
            Principal user = this.getUser();
            return "STOMP" + (user != null ? " user=" + user.getName() : "") + this.appendSession();
        }
        if (StompCommand.CONNECTED.equals((Object)command)) {
            return "CONNECTED heart-beat=" + Arrays.toString(this.getHeartbeat()) + this.appendSession();
        }
        if (StompCommand.DISCONNECT.equals((Object)command)) {
            String receipt = this.getReceipt();
            return "DISCONNECT" + (receipt != null ? " receipt=" + receipt : "") + this.appendSession();
        }
        return this.getDetailedLogMessage(payload);
    }

    @Override
    public String getDetailedLogMessage(@Nullable Object payload) {
        if (this.isHeartbeat()) {
            String sessionId = this.getSessionId();
            return STOMP_HEARTBEAT_HEADER + (sessionId != null ? " in session " + sessionId : "");
        }
        StompCommand command = this.getCommand();
        if (command == null) {
            return super.getDetailedLogMessage(payload);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(command.name()).append(' ');
        Map<String, List<String>> nativeHeaders = this.getNativeHeaders();
        if (nativeHeaders != null) {
            sb.append(nativeHeaders);
        }
        sb.append(this.appendSession());
        if (this.getUser() != null) {
            sb.append(", user=").append(this.getUser().getName());
        }
        if (payload != null && command.isBodyAllowed()) {
            sb.append(this.appendPayload(payload));
        }
        return sb.toString();
    }

    private String appendSession() {
        return " session=" + this.getSessionId();
    }

    private String appendPayload(Object payload) {
        String contentType;
        if (payload.getClass() != byte[].class) {
            throw new IllegalStateException("Expected byte array payload but got: " + ClassUtils.getQualifiedName(payload.getClass()));
        }
        byte[] bytes = (byte[])payload;
        MimeType mimeType = this.getContentType();
        String string = contentType = mimeType != null ? " " + mimeType.toString() : "";
        if (bytes.length == 0 || mimeType == null || !this.isReadableContentType()) {
            return contentType;
        }
        Charset charset = mimeType.getCharset();
        charset = charset != null ? charset : StandardCharsets.UTF_8;
        return bytes.length < 80 ? contentType + " payload=" + new String(bytes, charset) : contentType + " payload=" + new String(Arrays.copyOf(bytes, 80), charset) + "...(truncated)";
    }

    public static StompHeaderAccessor create(StompCommand command) {
        return new StompHeaderAccessor(command, null);
    }

    public static StompHeaderAccessor create(StompCommand command, Map<String, List<String>> headers) {
        return new StompHeaderAccessor(command, headers);
    }

    public static StompHeaderAccessor createForHeartbeat() {
        return new StompHeaderAccessor();
    }

    public static StompHeaderAccessor wrap(Message<?> message) {
        return new StompHeaderAccessor(message);
    }

    @Nullable
    public static StompCommand getCommand(Map<String, Object> headers) {
        return (StompCommand)((Object)headers.get(COMMAND_HEADER));
    }

    @Nullable
    public static String getPasscode(Map<String, Object> headers) {
        StompPasscode credentials = (StompPasscode)headers.get(CREDENTIALS_HEADER);
        return credentials != null ? credentials.passcode : null;
    }

    @Nullable
    public static Integer getContentLength(Map<String, List<String>> nativeHeaders) {
        List<String> values = nativeHeaders.get(STOMP_CONTENT_LENGTH_HEADER);
        return !CollectionUtils.isEmpty(values) ? Integer.valueOf(values.get(0)) : null;
    }

    private static class StompPasscode {
        private final String passcode;

        public StompPasscode(String passcode) {
            this.passcode = passcode;
        }

        public String toString() {
            return "[PROTECTED]";
        }
    }
}

