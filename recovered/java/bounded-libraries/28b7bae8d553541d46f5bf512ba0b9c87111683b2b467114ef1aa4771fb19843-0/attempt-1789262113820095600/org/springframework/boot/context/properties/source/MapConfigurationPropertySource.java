/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.EnumerablePropertySource
 *  org.springframework.core.env.MapPropertySource
 *  org.springframework.util.Assert
 */
package org.springframework.boot.context.properties.source;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.springframework.boot.context.properties.source.ConfigurationProperty;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.DefaultPropertyMapper;
import org.springframework.boot.context.properties.source.IterableConfigurationPropertySource;
import org.springframework.boot.context.properties.source.PropertyMapper;
import org.springframework.boot.context.properties.source.SpringIterableConfigurationPropertySource;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.util.Assert;

public class MapConfigurationPropertySource
implements IterableConfigurationPropertySource {
    private static final PropertyMapper[] DEFAULT_MAPPERS = new PropertyMapper[]{DefaultPropertyMapper.INSTANCE};
    private final Map<String, Object> source = new LinkedHashMap<String, Object>();
    private final IterableConfigurationPropertySource delegate;

    public MapConfigurationPropertySource() {
        this(Collections.emptyMap());
    }

    public MapConfigurationPropertySource(Map<?, ?> map) {
        MapPropertySource mapPropertySource = new MapPropertySource("source", this.source);
        this.delegate = new SpringIterableConfigurationPropertySource((EnumerablePropertySource<?>)mapPropertySource, DEFAULT_MAPPERS);
        this.putAll(map);
    }

    public void putAll(Map<?, ?> map) {
        Assert.notNull(map, (String)"Map must not be null");
        this.assertNotReadOnlySystemAttributesMap(map);
        map.forEach(this::put);
    }

    public void put(Object name, Object value) {
        this.source.put(name != null ? name.toString() : null, value);
    }

    @Override
    public Object getUnderlyingSource() {
        return this.source;
    }

    @Override
    public ConfigurationProperty getConfigurationProperty(ConfigurationPropertyName name) {
        return this.delegate.getConfigurationProperty(name);
    }

    @Override
    public Iterator<ConfigurationPropertyName> iterator() {
        return this.delegate.iterator();
    }

    @Override
    public Stream<ConfigurationPropertyName> stream() {
        return this.delegate.stream();
    }

    private void assertNotReadOnlySystemAttributesMap(Map<?, ?> map) {
        try {
            map.size();
        }
        catch (UnsupportedOperationException ex) {
            throw new IllegalArgumentException("Security restricted maps are not supported", ex);
        }
    }
}

