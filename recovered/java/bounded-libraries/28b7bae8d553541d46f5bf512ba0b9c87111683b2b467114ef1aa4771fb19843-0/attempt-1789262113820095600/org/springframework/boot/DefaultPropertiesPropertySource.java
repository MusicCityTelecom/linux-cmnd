/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.MapPropertySource
 *  org.springframework.core.env.MutablePropertySources
 *  org.springframework.core.env.PropertySource
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.boot;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.util.CollectionUtils;

public class DefaultPropertiesPropertySource
extends MapPropertySource {
    public static final String NAME = "defaultProperties";

    public DefaultPropertiesPropertySource(Map<String, Object> source) {
        super(NAME, source);
    }

    public static boolean hasMatchingName(PropertySource<?> propertySource) {
        return propertySource != null && propertySource.getName().equals(NAME);
    }

    public static void ifNotEmpty(Map<String, Object> source, Consumer<DefaultPropertiesPropertySource> action) {
        if (!CollectionUtils.isEmpty(source) && action != null) {
            action.accept(new DefaultPropertiesPropertySource(source));
        }
    }

    public static void addOrMerge(Map<String, Object> source, MutablePropertySources sources) {
        if (!CollectionUtils.isEmpty(source)) {
            HashMap<String, Object> resultingSource = new HashMap<String, Object>();
            DefaultPropertiesPropertySource propertySource = new DefaultPropertiesPropertySource(resultingSource);
            if (sources.contains(NAME)) {
                DefaultPropertiesPropertySource.mergeIfPossible(source, sources, resultingSource);
                sources.replace(NAME, (PropertySource)propertySource);
            } else {
                resultingSource.putAll(source);
                sources.addLast((PropertySource)propertySource);
            }
        }
    }

    private static void mergeIfPossible(Map<String, Object> source, MutablePropertySources sources, Map<String, Object> resultingSource) {
        PropertySource existingSource = sources.get(NAME);
        if (existingSource != null) {
            Object underlyingSource = existingSource.getSource();
            if (underlyingSource instanceof Map) {
                resultingSource.putAll((Map)underlyingSource);
            }
            resultingSource.putAll(source);
        }
    }

    public static void moveToEnd(ConfigurableEnvironment environment) {
        DefaultPropertiesPropertySource.moveToEnd(environment.getPropertySources());
    }

    public static void moveToEnd(MutablePropertySources propertySources) {
        PropertySource propertySource = propertySources.remove(NAME);
        if (propertySource != null) {
            propertySources.addLast(propertySource);
        }
    }
}

