/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonAnyGetter
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package org.springframework.boot.actuate.info;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_EMPTY)
public final class Info {
    private final Map<String, Object> details;

    private Info(Builder builder) {
        LinkedHashMap content = new LinkedHashMap(builder.content);
        this.details = Collections.unmodifiableMap(content);
    }

    @JsonAnyGetter
    public Map<String, Object> getDetails() {
        return this.details;
    }

    public Object get(String id) {
        return this.details.get(id);
    }

    public <T> T get(String id, Class<T> type) {
        Object value = this.get(id);
        if (value != null && type != null && !type.isInstance(value)) {
            throw new IllegalStateException("Info entry is not of required type [" + type.getName() + "]: " + value);
        }
        return (T)value;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Info) {
            Info other = (Info)obj;
            return this.details.equals(other.details);
        }
        return false;
    }

    public int hashCode() {
        return this.details.hashCode();
    }

    public String toString() {
        return this.getDetails().toString();
    }

    public static class Builder {
        private final Map<String, Object> content = new LinkedHashMap<String, Object>();

        public Builder withDetail(String key, Object value) {
            this.content.put(key, value);
            return this;
        }

        public Builder withDetails(Map<String, Object> details) {
            this.content.putAll(details);
            return this;
        }

        public Info build() {
            return new Info(this);
        }
    }
}

