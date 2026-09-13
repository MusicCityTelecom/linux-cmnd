/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.AbstractPropertyResolver
 *  org.springframework.core.env.MutablePropertySources
 *  org.springframework.core.env.PropertySource
 *  org.springframework.core.env.PropertySources
 *  org.springframework.core.env.PropertySourcesPropertyResolver
 */
package org.springframework.boot.context.properties.source;

import org.springframework.boot.context.properties.source.ConfigurationProperty;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.context.properties.source.ConfigurationPropertySourcesPropertySource;
import org.springframework.boot.context.properties.source.SpringConfigurationPropertySources;
import org.springframework.core.env.AbstractPropertyResolver;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;
import org.springframework.core.env.PropertySourcesPropertyResolver;

class ConfigurationPropertySourcesPropertyResolver
extends AbstractPropertyResolver {
    private final MutablePropertySources propertySources;
    private final DefaultResolver defaultResolver;

    ConfigurationPropertySourcesPropertyResolver(MutablePropertySources propertySources) {
        this.propertySources = propertySources;
        this.defaultResolver = new DefaultResolver((PropertySources)propertySources);
    }

    public boolean containsProperty(String key) {
        ConfigurationPropertyName name;
        ConfigurationPropertySourcesPropertySource attached = this.getAttached();
        if (attached != null && (name = ConfigurationPropertyName.of(key, true)) != null) {
            try {
                return attached.findConfigurationProperty(name) != null;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return this.defaultResolver.containsProperty(key);
    }

    public String getProperty(String key) {
        return this.getProperty(key, String.class, true);
    }

    public <T> T getProperty(String key, Class<T> targetValueType) {
        return this.getProperty(key, targetValueType, true);
    }

    protected String getPropertyAsRawString(String key) {
        return this.getProperty(key, String.class, false);
    }

    private <T> T getProperty(String key, Class<T> targetValueType, boolean resolveNestedPlaceholders) {
        Object value = this.findPropertyValue(key);
        if (value == null) {
            return null;
        }
        if (resolveNestedPlaceholders && value instanceof String) {
            value = this.resolveNestedPlaceholders((String)value);
        }
        return (T)this.convertValueIfNecessary(value, targetValueType);
    }

    private Object findPropertyValue(String key) {
        ConfigurationPropertyName name;
        ConfigurationPropertySourcesPropertySource attached = this.getAttached();
        if (attached != null && (name = ConfigurationPropertyName.of(key, true)) != null) {
            try {
                ConfigurationProperty configurationProperty = attached.findConfigurationProperty(name);
                return configurationProperty != null ? configurationProperty.getValue() : null;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return this.defaultResolver.getProperty(key, Object.class, false);
    }

    private ConfigurationPropertySourcesPropertySource getAttached() {
        Iterable attachedSource;
        ConfigurationPropertySourcesPropertySource attached = (ConfigurationPropertySourcesPropertySource)ConfigurationPropertySources.getAttached(this.propertySources);
        Iterable iterable = attachedSource = attached != null ? (Iterable)attached.getSource() : null;
        if (attachedSource instanceof SpringConfigurationPropertySources && ((SpringConfigurationPropertySources)attachedSource).isUsingSources((Iterable<PropertySource<?>>)this.propertySources)) {
            return attached;
        }
        return null;
    }

    static class DefaultResolver
    extends PropertySourcesPropertyResolver {
        DefaultResolver(PropertySources propertySources) {
            super(propertySources);
        }

        public <T> T getProperty(String key, Class<T> targetValueType, boolean resolveNestedPlaceholders) {
            return (T)super.getProperty(key, targetValueType, resolveNestedPlaceholders);
        }
    }
}

