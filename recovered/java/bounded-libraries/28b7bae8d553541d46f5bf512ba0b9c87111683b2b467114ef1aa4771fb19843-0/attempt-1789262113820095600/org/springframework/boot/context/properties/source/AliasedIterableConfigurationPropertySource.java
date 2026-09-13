/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.boot.context.properties.source;

import java.util.List;
import java.util.stream.Stream;
import org.springframework.boot.context.properties.source.AliasedConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases;
import org.springframework.boot.context.properties.source.IterableConfigurationPropertySource;
import org.springframework.util.CollectionUtils;

class AliasedIterableConfigurationPropertySource
extends AliasedConfigurationPropertySource
implements IterableConfigurationPropertySource {
    AliasedIterableConfigurationPropertySource(IterableConfigurationPropertySource source, ConfigurationPropertyNameAliases aliases) {
        super(source, aliases);
    }

    @Override
    public Stream<ConfigurationPropertyName> stream() {
        return this.getSource().stream().flatMap(this::addAliases);
    }

    private Stream<ConfigurationPropertyName> addAliases(ConfigurationPropertyName name) {
        Stream<ConfigurationPropertyName> names = Stream.of(name);
        List<ConfigurationPropertyName> aliases = this.getAliases().getAliases(name);
        if (CollectionUtils.isEmpty(aliases)) {
            return names;
        }
        return Stream.concat(names, aliases.stream());
    }

    @Override
    protected IterableConfigurationPropertySource getSource() {
        return (IterableConfigurationPropertySource)super.getSource();
    }
}

