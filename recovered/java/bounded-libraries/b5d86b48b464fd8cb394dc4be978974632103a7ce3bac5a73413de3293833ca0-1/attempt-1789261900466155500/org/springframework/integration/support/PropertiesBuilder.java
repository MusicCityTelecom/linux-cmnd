/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.support;

import java.util.Properties;

public class PropertiesBuilder {
    private final Properties properties = new Properties();

    public PropertiesBuilder put(Object key, Object value) {
        this.properties.put(key, value);
        return this;
    }

    public Properties get() {
        return this.properties;
    }
}

