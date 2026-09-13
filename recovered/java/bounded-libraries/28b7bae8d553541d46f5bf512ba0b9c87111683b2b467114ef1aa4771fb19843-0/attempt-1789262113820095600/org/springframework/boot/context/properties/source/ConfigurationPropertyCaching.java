/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.Environment
 *  org.springframework.util.Assert
 */
package org.springframework.boot.context.properties.source;

import java.time.Duration;
import org.springframework.boot.context.properties.source.CachingConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.context.properties.source.ConfigurationPropertySourcesCaching;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

public interface ConfigurationPropertyCaching {
    public void enable();

    public void disable();

    public void setTimeToLive(Duration var1);

    public void clear();

    public static ConfigurationPropertyCaching get(Environment environment) {
        return ConfigurationPropertyCaching.get(environment, null);
    }

    public static ConfigurationPropertyCaching get(Environment environment, Object underlyingSource) {
        Iterable<ConfigurationPropertySource> sources = ConfigurationPropertySources.get(environment);
        return ConfigurationPropertyCaching.get(sources, underlyingSource);
    }

    public static ConfigurationPropertyCaching get(Iterable<ConfigurationPropertySource> sources) {
        return ConfigurationPropertyCaching.get(sources, null);
    }

    public static ConfigurationPropertyCaching get(Iterable<ConfigurationPropertySource> sources, Object underlyingSource) {
        Assert.notNull(sources, (String)"Sources must not be null");
        if (underlyingSource == null) {
            return new ConfigurationPropertySourcesCaching(sources);
        }
        for (ConfigurationPropertySource source : sources) {
            ConfigurationPropertyCaching caching;
            if (source.getUnderlyingSource() != underlyingSource || (caching = CachingConfigurationPropertySource.find(source)) == null) continue;
            return caching;
        }
        throw new IllegalStateException("Unable to find cache from configuration property sources");
    }
}

