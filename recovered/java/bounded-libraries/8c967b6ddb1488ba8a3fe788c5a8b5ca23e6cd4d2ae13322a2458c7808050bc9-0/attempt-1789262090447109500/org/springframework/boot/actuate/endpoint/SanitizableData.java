/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.PropertySource
 */
package org.springframework.boot.actuate.endpoint;

import org.springframework.core.env.PropertySource;

public final class SanitizableData {
    public static final String SANITIZED_VALUE = "******";
    private final PropertySource<?> propertySource;
    private final String key;
    private final Object value;

    public SanitizableData(PropertySource<?> propertySource, String key, Object value) {
        this.propertySource = propertySource;
        this.key = key;
        this.value = value;
    }

    public PropertySource<?> getPropertySource() {
        return this.propertySource;
    }

    public String getKey() {
        return this.key;
    }

    public Object getValue() {
        return this.value;
    }

    public SanitizableData withValue(Object value) {
        return new SanitizableData(this.propertySource, this.key, value);
    }
}

