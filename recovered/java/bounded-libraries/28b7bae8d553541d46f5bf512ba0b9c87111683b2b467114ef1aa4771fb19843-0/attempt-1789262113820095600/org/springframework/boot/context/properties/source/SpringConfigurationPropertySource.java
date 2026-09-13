/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.EnumerablePropertySource
 *  org.springframework.core.env.PropertySource
 *  org.springframework.core.env.SystemEnvironmentPropertySource
 *  org.springframework.util.Assert
 */
package org.springframework.boot.context.properties.source;

import java.util.Map;
import java.util.Random;
import org.springframework.boot.context.properties.source.ConfigurationProperty;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertyState;
import org.springframework.boot.context.properties.source.DefaultPropertyMapper;
import org.springframework.boot.context.properties.source.PropertyMapper;
import org.springframework.boot.context.properties.source.SpringIterableConfigurationPropertySource;
import org.springframework.boot.context.properties.source.SystemEnvironmentPropertyMapper;
import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.PropertySourceOrigin;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.util.Assert;

class SpringConfigurationPropertySource
implements ConfigurationPropertySource {
    private static final PropertyMapper[] DEFAULT_MAPPERS = new PropertyMapper[]{DefaultPropertyMapper.INSTANCE};
    private static final PropertyMapper[] SYSTEM_ENVIRONMENT_MAPPERS = new PropertyMapper[]{SystemEnvironmentPropertyMapper.INSTANCE, DefaultPropertyMapper.INSTANCE};
    private final PropertySource<?> propertySource;
    private final PropertyMapper[] mappers;

    SpringConfigurationPropertySource(PropertySource<?> propertySource, PropertyMapper ... mappers) {
        Assert.notNull(propertySource, (String)"PropertySource must not be null");
        Assert.isTrue((mappers.length > 0 ? 1 : 0) != 0, (String)"Mappers must contain at least one item");
        this.propertySource = propertySource;
        this.mappers = mappers;
    }

    @Override
    public ConfigurationProperty getConfigurationProperty(ConfigurationPropertyName name) {
        if (name == null) {
            return null;
        }
        for (PropertyMapper mapper : this.mappers) {
            try {
                for (String candidate : mapper.map(name)) {
                    Object value = this.getPropertySource().getProperty(candidate);
                    if (value == null) continue;
                    Origin origin = PropertySourceOrigin.get(this.propertySource, candidate);
                    return ConfigurationProperty.of(this, name, value, origin);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return null;
    }

    @Override
    public ConfigurationPropertyState containsDescendantOf(ConfigurationPropertyName name) {
        PropertySource<?> source = this.getPropertySource();
        if (source.getSource() instanceof Random) {
            return SpringConfigurationPropertySource.containsDescendantOfForRandom("random", name);
        }
        if (source.getSource() instanceof PropertySource && ((PropertySource)source.getSource()).getSource() instanceof Random) {
            return SpringConfigurationPropertySource.containsDescendantOfForRandom(source.getName(), name);
        }
        return ConfigurationPropertyState.UNKNOWN;
    }

    private static ConfigurationPropertyState containsDescendantOfForRandom(String prefix, ConfigurationPropertyName name) {
        if (name.getNumberOfElements() > 1 && name.getElement(0, ConfigurationPropertyName.Form.DASHED).equals(prefix)) {
            return ConfigurationPropertyState.PRESENT;
        }
        return ConfigurationPropertyState.ABSENT;
    }

    @Override
    public Object getUnderlyingSource() {
        return this.propertySource;
    }

    protected PropertySource<?> getPropertySource() {
        return this.propertySource;
    }

    protected final PropertyMapper[] getMappers() {
        return this.mappers;
    }

    public String toString() {
        return this.propertySource.toString();
    }

    static SpringConfigurationPropertySource from(PropertySource<?> source) {
        Assert.notNull(source, (String)"Source must not be null");
        PropertyMapper[] mappers = SpringConfigurationPropertySource.getPropertyMappers(source);
        if (SpringConfigurationPropertySource.isFullEnumerable(source)) {
            return new SpringIterableConfigurationPropertySource((EnumerablePropertySource)source, mappers);
        }
        return new SpringConfigurationPropertySource(source, mappers);
    }

    private static PropertyMapper[] getPropertyMappers(PropertySource<?> source) {
        if (source instanceof SystemEnvironmentPropertySource && SpringConfigurationPropertySource.hasSystemEnvironmentName(source)) {
            return SYSTEM_ENVIRONMENT_MAPPERS;
        }
        return DEFAULT_MAPPERS;
    }

    private static boolean hasSystemEnvironmentName(PropertySource<?> source) {
        String name = source.getName();
        return "systemEnvironment".equals(name) || name.endsWith("-systemEnvironment");
    }

    private static boolean isFullEnumerable(PropertySource<?> source) {
        PropertySource<?> rootSource = SpringConfigurationPropertySource.getRootSource(source);
        if (rootSource.getSource() instanceof Map) {
            try {
                ((Map)rootSource.getSource()).size();
            }
            catch (UnsupportedOperationException ex) {
                return false;
            }
        }
        return source instanceof EnumerablePropertySource;
    }

    private static PropertySource<?> getRootSource(PropertySource<?> source) {
        while (source.getSource() != null && source.getSource() instanceof PropertySource) {
            source = (PropertySource)source.getSource();
        }
        return source;
    }
}

