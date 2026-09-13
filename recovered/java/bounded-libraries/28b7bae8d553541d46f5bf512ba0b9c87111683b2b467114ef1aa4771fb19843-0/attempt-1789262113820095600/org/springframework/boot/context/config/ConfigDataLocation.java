/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.context.config;

import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginProvider;
import org.springframework.util.StringUtils;

public final class ConfigDataLocation
implements OriginProvider {
    public static final String OPTIONAL_PREFIX = "optional:";
    private final boolean optional;
    private final String value;
    private final Origin origin;

    private ConfigDataLocation(boolean optional, String value, Origin origin) {
        this.value = value;
        this.optional = optional;
        this.origin = origin;
    }

    public boolean isOptional() {
        return this.optional;
    }

    public String getValue() {
        return this.value;
    }

    public boolean hasPrefix(String prefix) {
        return this.value.startsWith(prefix);
    }

    public String getNonPrefixedValue(String prefix) {
        if (this.hasPrefix(prefix)) {
            return this.value.substring(prefix.length());
        }
        return this.value;
    }

    @Override
    public Origin getOrigin() {
        return this.origin;
    }

    public ConfigDataLocation[] split() {
        return this.split(";");
    }

    public ConfigDataLocation[] split(String delimiter) {
        String[] values = StringUtils.delimitedListToStringArray((String)this.toString(), (String)delimiter);
        ConfigDataLocation[] result = new ConfigDataLocation[values.length];
        for (int i = 0; i < values.length; ++i) {
            result[i] = ConfigDataLocation.of(values[i]).withOrigin(this.getOrigin());
        }
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        ConfigDataLocation other = (ConfigDataLocation)obj;
        return this.value.equals(other.value);
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    public String toString() {
        return !this.optional ? this.value : OPTIONAL_PREFIX + this.value;
    }

    ConfigDataLocation withOrigin(Origin origin) {
        return new ConfigDataLocation(this.optional, this.value, origin);
    }

    public static ConfigDataLocation of(String location) {
        String value;
        boolean optional = location != null && location.startsWith(OPTIONAL_PREFIX);
        String string = value = !optional ? location : location.substring(OPTIONAL_PREFIX.length());
        if (!StringUtils.hasText((String)value)) {
            return null;
        }
        return new ConfigDataLocation(optional, value, null);
    }
}

