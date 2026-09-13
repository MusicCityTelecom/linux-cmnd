/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.context.properties.source;

import org.springframework.boot.context.properties.source.ConfigurationProperty;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertyState;
import org.springframework.util.Assert;

class PrefixedConfigurationPropertySource
implements ConfigurationPropertySource {
    private final ConfigurationPropertySource source;
    private final ConfigurationPropertyName prefix;

    PrefixedConfigurationPropertySource(ConfigurationPropertySource source, String prefix) {
        Assert.notNull((Object)source, (String)"Source must not be null");
        Assert.hasText((String)prefix, (String)"Prefix must not be empty");
        this.source = source;
        this.prefix = ConfigurationPropertyName.of(prefix);
    }

    protected final ConfigurationPropertyName getPrefix() {
        return this.prefix;
    }

    @Override
    public ConfigurationProperty getConfigurationProperty(ConfigurationPropertyName name) {
        ConfigurationProperty configurationProperty = this.source.getConfigurationProperty(this.getPrefixedName(name));
        if (configurationProperty == null) {
            return null;
        }
        return ConfigurationProperty.of(configurationProperty.getSource(), name, configurationProperty.getValue(), configurationProperty.getOrigin());
    }

    private ConfigurationPropertyName getPrefixedName(ConfigurationPropertyName name) {
        return this.prefix.append(name);
    }

    @Override
    public ConfigurationPropertyState containsDescendantOf(ConfigurationPropertyName name) {
        return this.source.containsDescendantOf(this.getPrefixedName(name));
    }

    @Override
    public Object getUnderlyingSource() {
        return this.source.getUnderlyingSource();
    }

    protected ConfigurationPropertySource getSource() {
        return this.source;
    }
}

