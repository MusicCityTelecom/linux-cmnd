/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.lang.Nullable
 *  org.springframework.util.AlternativeJdkIdGenerator
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.IdGenerator
 */
package org.springframework.messaging;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.CollectionUtils;
import org.springframework.util.IdGenerator;

public class MessageHeaders
implements Map<String, Object>,
Serializable {
    public static final UUID ID_VALUE_NONE = new UUID(0L, 0L);
    public static final String ID = "id";
    public static final String TIMESTAMP = "timestamp";
    public static final String CONTENT_TYPE = "contentType";
    public static final String REPLY_CHANNEL = "replyChannel";
    public static final String ERROR_CHANNEL = "errorChannel";
    private static final long serialVersionUID = 7035068984263400920L;
    private static final Log logger = LogFactory.getLog(MessageHeaders.class);
    private static final IdGenerator defaultIdGenerator = new AlternativeJdkIdGenerator();
    @Nullable
    private static volatile IdGenerator idGenerator;
    private final Map<String, Object> headers;

    public MessageHeaders(@Nullable Map<String, Object> headers) {
        this(headers, null, null);
    }

    protected MessageHeaders(@Nullable Map<String, Object> headers, @Nullable UUID id, @Nullable Long timestamp) {
        Map<String, Object> map = this.headers = headers != null ? new HashMap<String, Object>(headers) : new HashMap();
        if (id == null) {
            this.headers.put(ID, MessageHeaders.getIdGenerator().generateId());
        } else if (id == ID_VALUE_NONE) {
            this.headers.remove(ID);
        } else {
            this.headers.put(ID, id);
        }
        if (timestamp == null) {
            this.headers.put(TIMESTAMP, System.currentTimeMillis());
        } else if (timestamp < 0L) {
            this.headers.remove(TIMESTAMP);
        } else {
            this.headers.put(TIMESTAMP, timestamp);
        }
    }

    private MessageHeaders(MessageHeaders original, Set<String> keysToIgnore) {
        this.headers = CollectionUtils.newHashMap((int)original.headers.size());
        original.headers.forEach((key, value) -> {
            if (!keysToIgnore.contains(key)) {
                this.headers.put((String)key, value);
            }
        });
    }

    protected Map<String, Object> getRawHeaders() {
        return this.headers;
    }

    protected static IdGenerator getIdGenerator() {
        IdGenerator generator = idGenerator;
        return generator != null ? generator : defaultIdGenerator;
    }

    @Nullable
    public UUID getId() {
        return this.get(ID, UUID.class);
    }

    @Nullable
    public Long getTimestamp() {
        return this.get(TIMESTAMP, Long.class);
    }

    @Nullable
    public Object getReplyChannel() {
        return this.get(REPLY_CHANNEL);
    }

    @Nullable
    public Object getErrorChannel() {
        return this.get(ERROR_CHANNEL);
    }

    @Nullable
    public <T> T get(Object key, Class<T> type) {
        Object value = this.headers.get(key);
        if (value == null) {
            return null;
        }
        if (!type.isAssignableFrom(value.getClass())) {
            throw new IllegalArgumentException("Incorrect type specified for header '" + key + "'. Expected [" + type + "] but actual type is [" + value.getClass() + "]");
        }
        return (T)value;
    }

    @Override
    public boolean containsKey(Object key) {
        return this.headers.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.headers.containsValue(value);
    }

    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        return Collections.unmodifiableMap(this.headers).entrySet();
    }

    @Override
    @Nullable
    public Object get(Object key) {
        return this.headers.get(key);
    }

    @Override
    public boolean isEmpty() {
        return this.headers.isEmpty();
    }

    @Override
    public Set<String> keySet() {
        return Collections.unmodifiableSet(this.headers.keySet());
    }

    @Override
    public int size() {
        return this.headers.size();
    }

    @Override
    public Collection<Object> values() {
        return Collections.unmodifiableCollection(this.headers.values());
    }

    @Override
    public Object put(String key, Object value) {
        throw new UnsupportedOperationException("MessageHeaders is immutable");
    }

    @Override
    public void putAll(Map<? extends String, ? extends Object> map) {
        throw new UnsupportedOperationException("MessageHeaders is immutable");
    }

    @Override
    public Object remove(Object key) {
        throw new UnsupportedOperationException("MessageHeaders is immutable");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("MessageHeaders is immutable");
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        HashSet<String> keysToIgnore = new HashSet<String>();
        this.headers.forEach((key, value) -> {
            if (!(value instanceof Serializable)) {
                keysToIgnore.add((String)key);
            }
        });
        if (keysToIgnore.isEmpty()) {
            out.defaultWriteObject();
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug((Object)("Ignoring non-serializable message headers: " + keysToIgnore));
            }
            out.writeObject(new MessageHeaders(this, keysToIgnore));
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }

    @Override
    public boolean equals(@Nullable Object other) {
        return this == other || other instanceof MessageHeaders && this.headers.equals(((MessageHeaders)other).headers);
    }

    @Override
    public int hashCode() {
        return this.headers.hashCode();
    }

    public String toString() {
        return this.headers.toString();
    }
}

