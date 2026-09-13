/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.context.properties.source;

import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.springframework.boot.context.properties.source.AliasedIterableConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertyState;
import org.springframework.boot.context.properties.source.FilteredIterableConfigurationPropertiesSource;
import org.springframework.boot.context.properties.source.PrefixedIterableConfigurationPropertySource;
import org.springframework.util.StringUtils;

public interface IterableConfigurationPropertySource
extends ConfigurationPropertySource,
Iterable<ConfigurationPropertyName> {
    @Override
    default public Iterator<ConfigurationPropertyName> iterator() {
        return this.stream().iterator();
    }

    public Stream<ConfigurationPropertyName> stream();

    @Override
    default public ConfigurationPropertyState containsDescendantOf(ConfigurationPropertyName name) {
        return ConfigurationPropertyState.search(this, name::isAncestorOf);
    }

    @Override
    default public IterableConfigurationPropertySource filter(Predicate<ConfigurationPropertyName> filter) {
        return new FilteredIterableConfigurationPropertiesSource(this, filter);
    }

    @Override
    default public IterableConfigurationPropertySource withAliases(ConfigurationPropertyNameAliases aliases) {
        return new AliasedIterableConfigurationPropertySource(this, aliases);
    }

    @Override
    default public IterableConfigurationPropertySource withPrefix(String prefix) {
        return StringUtils.hasText((String)prefix) ? new PrefixedIterableConfigurationPropertySource(this, prefix) : this;
    }
}

