/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.boot.actuate.health;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

@JsonInclude(value=JsonInclude.Include.NON_EMPTY)
public final class Status {
    public static final Status UNKNOWN = new Status("UNKNOWN");
    public static final Status UP = new Status("UP");
    public static final Status DOWN = new Status("DOWN");
    public static final Status OUT_OF_SERVICE = new Status("OUT_OF_SERVICE");
    private final String code;
    private final String description;

    public Status(String code) {
        this(code, "");
    }

    public Status(String code, String description) {
        Assert.notNull((Object)code, (String)"Code must not be null");
        Assert.notNull((Object)description, (String)"Description must not be null");
        this.code = code;
        this.description = description;
    }

    @JsonProperty(value="status")
    public String getCode() {
        return this.code;
    }

    @JsonInclude(value=JsonInclude.Include.NON_EMPTY)
    public String getDescription() {
        return this.description;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Status) {
            return ObjectUtils.nullSafeEquals((Object)this.code, (Object)((Status)obj).code);
        }
        return false;
    }

    public int hashCode() {
        return this.code.hashCode();
    }

    public String toString() {
        return this.code;
    }
}

