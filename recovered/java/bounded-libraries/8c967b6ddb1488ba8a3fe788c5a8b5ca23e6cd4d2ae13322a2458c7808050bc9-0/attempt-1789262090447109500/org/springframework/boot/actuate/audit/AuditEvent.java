/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.audit;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.util.Assert;

@JsonInclude(value=JsonInclude.Include.NON_EMPTY)
public class AuditEvent
implements Serializable {
    private final Instant timestamp;
    private final String principal;
    private final String type;
    private final Map<String, Object> data;

    public AuditEvent(String principal, String type, Map<String, Object> data) {
        this(Instant.now(), principal, type, data);
    }

    public AuditEvent(String principal, String type, String ... data) {
        this(Instant.now(), principal, type, AuditEvent.convert(data));
    }

    public AuditEvent(Instant timestamp, String principal, String type, Map<String, Object> data) {
        Assert.notNull((Object)timestamp, (String)"Timestamp must not be null");
        Assert.notNull((Object)type, (String)"Type must not be null");
        this.timestamp = timestamp;
        this.principal = principal != null ? principal : "";
        this.type = type;
        this.data = Collections.unmodifiableMap(data);
    }

    private static Map<String, Object> convert(String[] data) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        for (String entry : data) {
            int index = entry.indexOf(61);
            if (index != -1) {
                result.put(entry.substring(0, index), entry.substring(index + 1));
                continue;
            }
            result.put(entry, null);
        }
        return result;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public String getPrincipal() {
        return this.principal;
    }

    public String getType() {
        return this.type;
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    public String toString() {
        return "AuditEvent [timestamp=" + this.timestamp + ", principal=" + this.principal + ", type=" + this.type + ", data=" + this.data + "]";
    }
}

