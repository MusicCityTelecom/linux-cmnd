/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.LinkedMultiValueMap
 *  org.springframework.util.MimeType
 *  org.springframework.util.MimeTypeUtils
 *  org.springframework.util.MultiValueMap
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.messaging.simp.stomp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class StompHeaders
implements MultiValueMap<String, String>,
Serializable {
    private static final long serialVersionUID = 7514642206528452544L;
    public static final String CONTENT_TYPE = "content-type";
    public static final String CONTENT_LENGTH = "content-length";
    public static final String RECEIPT = "receipt";
    public static final String HOST = "host";
    public static final String ACCEPT_VERSION = "accept-version";
    public static final String LOGIN = "login";
    public static final String PASSCODE = "passcode";
    public static final String HEARTBEAT = "heart-beat";
    public static final String SESSION = "session";
    public static final String SERVER = "server";
    public static final String DESTINATION = "destination";
    public static final String ID = "id";
    public static final String ACK = "ack";
    public static final String SUBSCRIPTION = "subscription";
    public static final String MESSAGE_ID = "message-id";
    public static final String RECEIPT_ID = "receipt-id";
    private final Map<String, List<String>> headers;

    public StompHeaders() {
        this((Map<String, List<String>>)new LinkedMultiValueMap(3), false);
    }

    private StompHeaders(Map<String, List<String>> headers, boolean readOnly) {
        Assert.notNull(headers, (String)"'headers' must not be null");
        if (readOnly) {
            LinkedMultiValueMap map = new LinkedMultiValueMap(headers.size());
            headers.forEach((arg_0, arg_1) -> StompHeaders.lambda$new$0((Map)map, arg_0, arg_1));
            this.headers = Collections.unmodifiableMap(map);
        } else {
            this.headers = headers;
        }
    }

    public void setContentType(@Nullable MimeType mimeType) {
        if (mimeType != null) {
            Assert.isTrue((!mimeType.isWildcardType() ? 1 : 0) != 0, (String)"'Content-Type' cannot contain wildcard type '*'");
            Assert.isTrue((!mimeType.isWildcardSubtype() ? 1 : 0) != 0, (String)"'Content-Type' cannot contain wildcard subtype '*'");
            this.set(CONTENT_TYPE, mimeType.toString());
        } else {
            this.set(CONTENT_TYPE, null);
        }
    }

    @Nullable
    public MimeType getContentType() {
        String value = this.getFirst(CONTENT_TYPE);
        return StringUtils.hasLength((String)value) ? MimeTypeUtils.parseMimeType((String)value) : null;
    }

    public void setContentLength(long contentLength) {
        this.set(CONTENT_LENGTH, Long.toString(contentLength));
    }

    public long getContentLength() {
        String value = this.getFirst(CONTENT_LENGTH);
        return value != null ? Long.parseLong(value) : -1L;
    }

    public void setReceipt(@Nullable String receipt) {
        this.set(RECEIPT, receipt);
    }

    @Nullable
    public String getReceipt() {
        return this.getFirst(RECEIPT);
    }

    public void setHost(@Nullable String host) {
        this.set(HOST, host);
    }

    @Nullable
    public String getHost() {
        return this.getFirst(HOST);
    }

    public void setAcceptVersion(String ... acceptVersions) {
        if (ObjectUtils.isEmpty((Object[])acceptVersions)) {
            this.set(ACCEPT_VERSION, null);
            return;
        }
        Arrays.stream(acceptVersions).forEach(version -> Assert.isTrue((version != null && (version.equals("1.1") || version.equals("1.2")) ? 1 : 0) != 0, (String)("Invalid version: " + version)));
        this.set(ACCEPT_VERSION, StringUtils.arrayToCommaDelimitedString((Object[])acceptVersions));
    }

    @Nullable
    public String[] getAcceptVersion() {
        String value = this.getFirst(ACCEPT_VERSION);
        return value != null ? StringUtils.commaDelimitedListToStringArray((String)value) : null;
    }

    public void setLogin(@Nullable String login) {
        this.set(LOGIN, login);
    }

    @Nullable
    public String getLogin() {
        return this.getFirst(LOGIN);
    }

    public void setPasscode(@Nullable String passcode) {
        this.set(PASSCODE, passcode);
    }

    @Nullable
    public String getPasscode() {
        return this.getFirst(PASSCODE);
    }

    public void setHeartbeat(@Nullable long[] heartbeat) {
        if (heartbeat == null || heartbeat.length != 2) {
            throw new IllegalArgumentException("Heart-beat array must be of length 2, not " + (heartbeat != null ? Integer.valueOf(heartbeat.length) : "null"));
        }
        String value = heartbeat[0] + "," + heartbeat[1];
        if (heartbeat[0] < 0L || heartbeat[1] < 0L) {
            throw new IllegalArgumentException("Heart-beat values cannot be negative: " + value);
        }
        this.set(HEARTBEAT, value);
    }

    @Nullable
    public long[] getHeartbeat() {
        String rawValue = this.getFirst(HEARTBEAT);
        String[] rawValues = StringUtils.split((String)rawValue, (String)",");
        if (rawValues == null) {
            return null;
        }
        return new long[]{Long.parseLong(rawValues[0]), Long.parseLong(rawValues[1])};
    }

    public boolean isHeartbeatEnabled() {
        long[] heartbeat = this.getHeartbeat();
        return heartbeat != null && heartbeat[0] != 0L && heartbeat[1] != 0L;
    }

    public void setSession(@Nullable String session) {
        this.set(SESSION, session);
    }

    @Nullable
    public String getSession() {
        return this.getFirst(SESSION);
    }

    public void setServer(@Nullable String server) {
        this.set(SERVER, server);
    }

    @Nullable
    public String getServer() {
        return this.getFirst(SERVER);
    }

    public void setDestination(@Nullable String destination) {
        this.set(DESTINATION, destination);
    }

    @Nullable
    public String getDestination() {
        return this.getFirst(DESTINATION);
    }

    public void setId(@Nullable String id) {
        this.set(ID, id);
    }

    @Nullable
    public String getId() {
        return this.getFirst(ID);
    }

    public void setAck(@Nullable String ack) {
        this.set(ACK, ack);
    }

    @Nullable
    public String getAck() {
        return this.getFirst(ACK);
    }

    public void setSubscription(@Nullable String subscription) {
        this.set(SUBSCRIPTION, subscription);
    }

    @Nullable
    public String getSubscription() {
        return this.getFirst(SUBSCRIPTION);
    }

    public void setMessageId(@Nullable String messageId) {
        this.set(MESSAGE_ID, messageId);
    }

    @Nullable
    public String getMessageId() {
        return this.getFirst(MESSAGE_ID);
    }

    public void setReceiptId(@Nullable String receiptId) {
        this.set(RECEIPT_ID, receiptId);
    }

    @Nullable
    public String getReceiptId() {
        return this.getFirst(RECEIPT_ID);
    }

    @Nullable
    public String getFirst(String headerName) {
        List<String> headerValues = this.headers.get(headerName);
        return headerValues != null ? headerValues.get(0) : null;
    }

    public void add(String headerName, @Nullable String headerValue) {
        List headerValues = this.headers.computeIfAbsent(headerName, k -> new ArrayList(1));
        headerValues.add(headerValue);
    }

    public void addAll(String headerName, List<? extends String> headerValues) {
        List currentValues = this.headers.computeIfAbsent(headerName, k -> new ArrayList(1));
        currentValues.addAll(headerValues);
    }

    public void addAll(MultiValueMap<String, String> values) {
        values.forEach(this::addAll);
    }

    public void set(String headerName, @Nullable String headerValue) {
        ArrayList<String> headerValues = new ArrayList<String>(1);
        headerValues.add(headerValue);
        this.headers.put(headerName, headerValues);
    }

    public void setAll(Map<String, String> values) {
        values.forEach(this::set);
    }

    public Map<String, String> toSingleValueMap() {
        LinkedHashMap singleValueMap = CollectionUtils.newLinkedHashMap((int)this.headers.size());
        this.headers.forEach((key, value) -> {
            String cfr_ignored_0 = (String)singleValueMap.put(key, value.get(0));
        });
        return singleValueMap;
    }

    public int size() {
        return this.headers.size();
    }

    public boolean isEmpty() {
        return this.headers.isEmpty();
    }

    public boolean containsKey(Object key) {
        return this.headers.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return this.headers.containsValue(value);
    }

    public List<String> get(Object key) {
        return this.headers.get(key);
    }

    public List<String> put(String key, List<String> value) {
        return this.headers.put(key, value);
    }

    public List<String> remove(Object key) {
        return this.headers.remove(key);
    }

    public void putAll(Map<? extends String, ? extends List<String>> map) {
        this.headers.putAll(map);
    }

    public void clear() {
        this.headers.clear();
    }

    public Set<String> keySet() {
        return this.headers.keySet();
    }

    public Collection<List<String>> values() {
        return this.headers.values();
    }

    public Set<Map.Entry<String, List<String>>> entrySet() {
        return this.headers.entrySet();
    }

    public boolean equals(@Nullable Object other) {
        return this == other || other instanceof StompHeaders && this.headers.equals(((StompHeaders)other).headers);
    }

    public int hashCode() {
        return this.headers.hashCode();
    }

    public String toString() {
        return this.headers.toString();
    }

    public static StompHeaders readOnlyStompHeaders(@Nullable Map<String, List<String>> headers) {
        return new StompHeaders(headers != null ? headers : Collections.emptyMap(), true);
    }

    private static /* synthetic */ void lambda$new$0(Map map, String key, List value) {
        map.put(key, Collections.unmodifiableList(value));
    }
}

