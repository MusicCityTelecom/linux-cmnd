/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.properties.source;

import java.time.Duration;
import java.util.function.Consumer;
import org.springframework.boot.context.properties.source.CachingConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertyCaching;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;

class ConfigurationPropertySourcesCaching
implements ConfigurationPropertyCaching {
    private final Iterable<ConfigurationPropertySource> sources;

    ConfigurationPropertySourcesCaching(Iterable<ConfigurationPropertySource> sources) {
        this.sources = sources;
    }

    @Override
    public void enable() {
        this.forEach(ConfigurationPropertyCaching::enable);
    }

    @Override
    public void disable() {
        this.forEach(ConfigurationPropertyCaching::disable);
    }

    @Override
    public void setTimeToLive(Duration timeToLive) {
        this.forEach(caching -> caching.setTimeToLive(timeToLive));
    }

    @Override
    public void clear() {
        this.forEach(ConfigurationPropertyCaching::clear);
    }

    private void forEach(Consumer<ConfigurationPropertyCaching> action) {
        if (this.sources != null) {
            for (ConfigurationPropertySource source : this.sources) {
                ConfigurationPropertyCaching caching = CachingConfigurationPropertySource.find(source);
                if (caching == null) continue;
                action.accept(caching);
            }
        }
    }
}

