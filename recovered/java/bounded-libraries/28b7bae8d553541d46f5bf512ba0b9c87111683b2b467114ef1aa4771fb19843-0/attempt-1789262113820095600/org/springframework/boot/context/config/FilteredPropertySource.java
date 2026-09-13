/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.MutablePropertySources
 *  org.springframework.core.env.PropertySource
 */
package org.springframework.boot.context.config;

import java.util.Set;
import java.util.function.Consumer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

class FilteredPropertySource
extends PropertySource<PropertySource<?>> {
    private final Set<String> filteredProperties;

    FilteredPropertySource(PropertySource<?> original, Set<String> filteredProperties) {
        super(original.getName(), original);
        this.filteredProperties = filteredProperties;
    }

    public Object getProperty(String name) {
        if (this.filteredProperties.contains(name)) {
            return null;
        }
        return ((PropertySource)this.getSource()).getProperty(name);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static void apply(ConfigurableEnvironment environment, String propertySourceName, Set<String> filteredProperties, Consumer<PropertySource<?>> operation) {
        MutablePropertySources propertySources = environment.getPropertySources();
        PropertySource original = propertySources.get(propertySourceName);
        if (original == null) {
            operation.accept(null);
            return;
        }
        propertySources.replace(propertySourceName, (PropertySource)new FilteredPropertySource(original, filteredProperties));
        try {
            operation.accept(original);
        }
        finally {
            propertySources.replace(propertySourceName, original);
        }
    }
}

