/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.PropertySource
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.context.properties.source;

import java.util.function.Predicate;
import org.springframework.boot.context.properties.source.AliasedConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationProperty;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases;
import org.springframework.boot.context.properties.source.ConfigurationPropertySourcesPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertyState;
import org.springframework.boot.context.properties.source.FilteredConfigurationPropertiesSource;
import org.springframework.boot.context.properties.source.PrefixedConfigurationPropertySource;
import org.springframework.boot.context.properties.source.SpringConfigurationPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.util.StringUtils;

@FunctionalInterface
public interface ConfigurationPropertySource {
    public ConfigurationProperty getConfigurationProperty(ConfigurationPropertyName var1);

    default public ConfigurationPropertyState containsDescendantOf(ConfigurationPropertyName name) {
        return ConfigurationPropertyState.UNKNOWN;
    }

    default public ConfigurationPropertySource filter(Predicate<ConfigurationPropertyName> filter) {
        return new FilteredConfigurationPropertiesSource(this, filter);
    }

    default public ConfigurationPropertySource withAliases(ConfigurationPropertyNameAliases aliases) {
        return new AliasedConfigurationPropertySource(this, aliases);
    }

    default public ConfigurationPropertySource withPrefix(String prefix) {
        return StringUtils.hasText((String)prefix) ? new PrefixedConfigurationPropertySource(this, prefix) : this;
    }

    default public Object getUnderlyingSource() {
        return null;
    }

    public static ConfigurationPropertySource from(PropertySource<?> source) {
        if (source instanceof ConfigurationPropertySourcesPropertySource) {
            return null;
        }
        return SpringConfigurationPropertySource.from(source);
    }
}

