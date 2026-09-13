/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.properties.source;

import org.springframework.boot.context.properties.source.ConfigurationPropertyCaching;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;

interface CachingConfigurationPropertySource {
    public ConfigurationPropertyCaching getCaching();

    public static ConfigurationPropertyCaching find(ConfigurationPropertySource source) {
        if (source instanceof CachingConfigurationPropertySource) {
            return ((CachingConfigurationPropertySource)((Object)source)).getCaching();
        }
        return null;
    }
}

