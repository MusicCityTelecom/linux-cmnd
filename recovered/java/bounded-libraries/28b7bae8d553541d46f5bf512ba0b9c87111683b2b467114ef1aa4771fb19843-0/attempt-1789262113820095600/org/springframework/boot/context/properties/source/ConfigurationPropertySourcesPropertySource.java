/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.PropertySource
 */
package org.springframework.boot.context.properties.source;

import org.springframework.boot.context.properties.source.ConfigurationProperty;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginLookup;
import org.springframework.core.env.PropertySource;

class ConfigurationPropertySourcesPropertySource
extends PropertySource<Iterable<ConfigurationPropertySource>>
implements OriginLookup<String> {
    ConfigurationPropertySourcesPropertySource(String name, Iterable<ConfigurationPropertySource> source) {
        super(name, source);
    }

    public boolean containsProperty(String name) {
        return this.findConfigurationProperty(name) != null;
    }

    public Object getProperty(String name) {
        ConfigurationProperty configurationProperty = this.findConfigurationProperty(name);
        return configurationProperty != null ? configurationProperty.getValue() : null;
    }

    @Override
    public Origin getOrigin(String name) {
        return Origin.from(this.findConfigurationProperty(name));
    }

    private ConfigurationProperty findConfigurationProperty(String name) {
        try {
            return this.findConfigurationProperty(ConfigurationPropertyName.of(name, true));
        }
        catch (Exception ex) {
            return null;
        }
    }

    ConfigurationProperty findConfigurationProperty(ConfigurationPropertyName name) {
        if (name == null) {
            return null;
        }
        for (ConfigurationPropertySource configurationPropertySource : (Iterable)this.getSource()) {
            ConfigurationProperty configurationProperty = configurationPropertySource.getConfigurationProperty(name);
            if (configurationProperty == null) continue;
            return configurationProperty;
        }
        return null;
    }
}

