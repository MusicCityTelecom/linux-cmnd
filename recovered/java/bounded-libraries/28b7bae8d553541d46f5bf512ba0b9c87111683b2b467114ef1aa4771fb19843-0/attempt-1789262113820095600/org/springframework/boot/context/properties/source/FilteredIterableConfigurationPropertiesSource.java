/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.properties.source;

import java.util.function.Predicate;
import java.util.stream.Stream;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertyState;
import org.springframework.boot.context.properties.source.FilteredConfigurationPropertiesSource;
import org.springframework.boot.context.properties.source.IterableConfigurationPropertySource;

class FilteredIterableConfigurationPropertiesSource
extends FilteredConfigurationPropertiesSource
implements IterableConfigurationPropertySource {
    FilteredIterableConfigurationPropertiesSource(IterableConfigurationPropertySource source, Predicate<ConfigurationPropertyName> filter) {
        super(source, filter);
    }

    @Override
    public Stream<ConfigurationPropertyName> stream() {
        return this.getSource().stream().filter(this.getFilter());
    }

    @Override
    protected IterableConfigurationPropertySource getSource() {
        return (IterableConfigurationPropertySource)super.getSource();
    }

    @Override
    public ConfigurationPropertyState containsDescendantOf(ConfigurationPropertyName name) {
        return ConfigurationPropertyState.search(this, name::isAncestorOf);
    }
}

