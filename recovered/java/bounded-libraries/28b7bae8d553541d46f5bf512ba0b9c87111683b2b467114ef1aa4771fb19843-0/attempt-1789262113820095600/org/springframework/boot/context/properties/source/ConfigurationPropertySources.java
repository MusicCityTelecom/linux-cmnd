/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.ConfigurablePropertyResolver
 *  org.springframework.core.env.Environment
 *  org.springframework.core.env.MutablePropertySources
 *  org.springframework.core.env.PropertySource
 *  org.springframework.core.env.PropertySource$StubPropertySource
 *  org.springframework.core.env.PropertySources
 *  org.springframework.util.Assert
 */
package org.springframework.boot.context.properties.source;

import java.util.Collections;
import java.util.stream.Stream;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySourcesPropertyResolver;
import org.springframework.boot.context.properties.source.ConfigurationPropertySourcesPropertySource;
import org.springframework.boot.context.properties.source.SpringConfigurationPropertySources;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.ConfigurablePropertyResolver;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;
import org.springframework.util.Assert;

public final class ConfigurationPropertySources {
    private static final String ATTACHED_PROPERTY_SOURCE_NAME = "configurationProperties";

    private ConfigurationPropertySources() {
    }

    public static ConfigurablePropertyResolver createPropertyResolver(MutablePropertySources propertySources) {
        return new ConfigurationPropertySourcesPropertyResolver(propertySources);
    }

    public static boolean isAttachedConfigurationPropertySource(PropertySource<?> propertySource) {
        return ATTACHED_PROPERTY_SOURCE_NAME.equals(propertySource.getName());
    }

    public static void attach(Environment environment) {
        Assert.isInstanceOf(ConfigurableEnvironment.class, (Object)environment);
        MutablePropertySources sources = ((ConfigurableEnvironment)environment).getPropertySources();
        ConfigurationPropertySourcesPropertySource attached = ConfigurationPropertySources.getAttached(sources);
        if (attached == null || !ConfigurationPropertySources.isUsingSources(attached, sources)) {
            attached = new ConfigurationPropertySourcesPropertySource(ATTACHED_PROPERTY_SOURCE_NAME, new SpringConfigurationPropertySources((Iterable<PropertySource<?>>)sources));
        }
        sources.remove(ATTACHED_PROPERTY_SOURCE_NAME);
        sources.addFirst((PropertySource)attached);
    }

    private static boolean isUsingSources(PropertySource<?> attached, MutablePropertySources sources) {
        return attached instanceof ConfigurationPropertySourcesPropertySource && ((SpringConfigurationPropertySources)attached.getSource()).isUsingSources((Iterable<PropertySource<?>>)sources);
    }

    static PropertySource<?> getAttached(MutablePropertySources sources) {
        return sources != null ? sources.get(ATTACHED_PROPERTY_SOURCE_NAME) : null;
    }

    public static Iterable<ConfigurationPropertySource> get(Environment environment) {
        Assert.isInstanceOf(ConfigurableEnvironment.class, (Object)environment);
        MutablePropertySources sources = ((ConfigurableEnvironment)environment).getPropertySources();
        ConfigurationPropertySourcesPropertySource attached = (ConfigurationPropertySourcesPropertySource)sources.get(ATTACHED_PROPERTY_SOURCE_NAME);
        if (attached == null) {
            return ConfigurationPropertySources.from(sources);
        }
        return (Iterable)attached.getSource();
    }

    public static Iterable<ConfigurationPropertySource> from(PropertySource<?> source) {
        return Collections.singleton(ConfigurationPropertySource.from(source));
    }

    public static Iterable<ConfigurationPropertySource> from(Iterable<PropertySource<?>> sources) {
        return new SpringConfigurationPropertySources(sources);
    }

    private static Stream<PropertySource<?>> streamPropertySources(PropertySources sources) {
        return sources.stream().flatMap(ConfigurationPropertySources::flatten).filter(ConfigurationPropertySources::isIncluded);
    }

    private static Stream<PropertySource<?>> flatten(PropertySource<?> source) {
        if (source.getSource() instanceof ConfigurableEnvironment) {
            return ConfigurationPropertySources.streamPropertySources((PropertySources)((ConfigurableEnvironment)source.getSource()).getPropertySources());
        }
        return Stream.of(source);
    }

    private static boolean isIncluded(PropertySource<?> source) {
        return !(source instanceof PropertySource.StubPropertySource) && !(source instanceof ConfigurationPropertySourcesPropertySource);
    }
}

