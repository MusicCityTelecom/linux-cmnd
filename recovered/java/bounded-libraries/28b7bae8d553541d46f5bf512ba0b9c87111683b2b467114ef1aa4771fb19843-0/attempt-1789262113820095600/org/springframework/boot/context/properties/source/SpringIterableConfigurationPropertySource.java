/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.EnumerablePropertySource
 *  org.springframework.core.env.MapPropertySource
 *  org.springframework.core.env.PropertySource
 */
package org.springframework.boot.context.properties.source;

import java.util.Arrays;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.springframework.boot.context.properties.source.CachingConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationProperty;
import org.springframework.boot.context.properties.source.ConfigurationPropertyCaching;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertyState;
import org.springframework.boot.context.properties.source.IterableConfigurationPropertySource;
import org.springframework.boot.context.properties.source.PropertyMapper;
import org.springframework.boot.context.properties.source.SoftReferenceConfigurationPropertyCache;
import org.springframework.boot.context.properties.source.SpringConfigurationPropertySource;
import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginLookup;
import org.springframework.boot.origin.PropertySourceOrigin;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

class SpringIterableConfigurationPropertySource
extends SpringConfigurationPropertySource
implements IterableConfigurationPropertySource,
CachingConfigurationPropertySource {
    private final BiPredicate<ConfigurationPropertyName, ConfigurationPropertyName> ancestorOfCheck;
    private final SoftReferenceConfigurationPropertyCache<Mappings> cache;
    private volatile ConfigurationPropertyName[] configurationPropertyNames;

    SpringIterableConfigurationPropertySource(EnumerablePropertySource<?> propertySource, PropertyMapper ... mappers) {
        super((PropertySource<?>)propertySource, mappers);
        this.assertEnumerablePropertySource();
        this.ancestorOfCheck = this.getAncestorOfCheck(mappers);
        this.cache = new SoftReferenceConfigurationPropertyCache(this.isImmutablePropertySource());
    }

    private BiPredicate<ConfigurationPropertyName, ConfigurationPropertyName> getAncestorOfCheck(PropertyMapper[] mappers) {
        BiPredicate<ConfigurationPropertyName, ConfigurationPropertyName> ancestorOfCheck = mappers[0].getAncestorOfCheck();
        for (int i = 1; i < mappers.length; ++i) {
            ancestorOfCheck = ancestorOfCheck.or(mappers[i].getAncestorOfCheck());
        }
        return ancestorOfCheck;
    }

    private void assertEnumerablePropertySource() {
        if (this.getPropertySource() instanceof MapPropertySource) {
            try {
                ((Map)((MapPropertySource)this.getPropertySource()).getSource()).size();
            }
            catch (UnsupportedOperationException ex) {
                throw new IllegalArgumentException("PropertySource must be fully enumerable");
            }
        }
    }

    @Override
    public ConfigurationPropertyCaching getCaching() {
        return this.cache;
    }

    @Override
    public ConfigurationProperty getConfigurationProperty(ConfigurationPropertyName name) {
        if (name == null) {
            return null;
        }
        ConfigurationProperty configurationProperty = super.getConfigurationProperty(name);
        if (configurationProperty != null) {
            return configurationProperty;
        }
        for (String candidate : this.getMappings().getMapped(name)) {
            Object value = this.getPropertySource().getProperty(candidate);
            if (value == null) continue;
            Origin origin = PropertySourceOrigin.get(this.getPropertySource(), candidate);
            return ConfigurationProperty.of(this, name, value, origin);
        }
        return null;
    }

    @Override
    public Stream<ConfigurationPropertyName> stream() {
        ConfigurationPropertyName[] names = this.getConfigurationPropertyNames();
        return Arrays.stream(names).filter(Objects::nonNull);
    }

    @Override
    public Iterator<ConfigurationPropertyName> iterator() {
        return new ConfigurationPropertyNamesIterator(this.getConfigurationPropertyNames());
    }

    @Override
    public ConfigurationPropertyState containsDescendantOf(ConfigurationPropertyName name) {
        ConfigurationPropertyName[] candidates;
        ConfigurationPropertyState result = super.containsDescendantOf(name);
        if (result != ConfigurationPropertyState.UNKNOWN) {
            return result;
        }
        if (this.ancestorOfCheck == PropertyMapper.DEFAULT_ANCESTOR_OF_CHECK) {
            return this.getMappings().containsDescendantOf(name, this.ancestorOfCheck);
        }
        for (ConfigurationPropertyName candidate : candidates = this.getConfigurationPropertyNames()) {
            if (candidate == null || !this.ancestorOfCheck.test(name, candidate)) continue;
            return ConfigurationPropertyState.PRESENT;
        }
        return ConfigurationPropertyState.ABSENT;
    }

    private ConfigurationPropertyName[] getConfigurationPropertyNames() {
        if (!this.isImmutablePropertySource()) {
            return this.getMappings().getConfigurationPropertyNames(this.getPropertySource().getPropertyNames());
        }
        ConfigurationPropertyName[] configurationPropertyNames = this.configurationPropertyNames;
        if (configurationPropertyNames == null) {
            configurationPropertyNames = this.getMappings().getConfigurationPropertyNames(this.getPropertySource().getPropertyNames());
            this.configurationPropertyNames = configurationPropertyNames;
        }
        return configurationPropertyNames;
    }

    private Mappings getMappings() {
        return this.cache.get(this::createMappings, this::updateMappings);
    }

    private Mappings createMappings() {
        return new Mappings(this.getMappers(), this.isImmutablePropertySource(), this.ancestorOfCheck == PropertyMapper.DEFAULT_ANCESTOR_OF_CHECK);
    }

    private Mappings updateMappings(Mappings mappings) {
        mappings.updateMappings(() -> this.getPropertySource().getPropertyNames());
        return mappings;
    }

    private boolean isImmutablePropertySource() {
        EnumerablePropertySource<?> source = this.getPropertySource();
        if (source instanceof OriginLookup) {
            return ((OriginLookup)source).isImmutable();
        }
        if ("systemEnvironment".equals(source.getName())) {
            return source.getSource() == System.getenv();
        }
        return false;
    }

    protected EnumerablePropertySource<?> getPropertySource() {
        return (EnumerablePropertySource)super.getPropertySource();
    }

    private static class ConfigurationPropertyNamesIterator
    implements Iterator<ConfigurationPropertyName> {
        private final ConfigurationPropertyName[] names;
        private int index = 0;

        ConfigurationPropertyNamesIterator(ConfigurationPropertyName[] names) {
            this.names = names;
        }

        @Override
        public boolean hasNext() {
            this.skipNulls();
            return this.index < this.names.length;
        }

        @Override
        public ConfigurationPropertyName next() {
            this.skipNulls();
            if (this.index >= this.names.length) {
                throw new NoSuchElementException();
            }
            return this.names[this.index++];
        }

        private void skipNulls() {
            while (this.index < this.names.length) {
                if (this.names[this.index] != null) {
                    return;
                }
                ++this.index;
            }
        }
    }

    private static class Mappings {
        private static final ConfigurationPropertyName[] EMPTY_NAMES_ARRAY = new ConfigurationPropertyName[0];
        private final PropertyMapper[] mappers;
        private final boolean immutable;
        private final boolean trackDescendants;
        private volatile Map<ConfigurationPropertyName, Set<String>> mappings;
        private volatile Map<String, ConfigurationPropertyName> reverseMappings;
        private volatile Map<ConfigurationPropertyName, Set<ConfigurationPropertyName>> descendants;
        private volatile ConfigurationPropertyName[] configurationPropertyNames;
        private volatile String[] lastUpdated;

        Mappings(PropertyMapper[] mappers, boolean immutable, boolean trackDescendants) {
            this.mappers = mappers;
            this.immutable = immutable;
            this.trackDescendants = trackDescendants;
        }

        void updateMappings(Supplier<String[]> propertyNames) {
            if (this.mappings == null || !this.immutable) {
                int count = 0;
                while (true) {
                    try {
                        this.updateMappings(propertyNames.get());
                        return;
                    }
                    catch (ConcurrentModificationException ex) {
                        if (count++ <= 10) continue;
                        throw ex;
                    }
                    break;
                }
            }
        }

        private void updateMappings(String[] propertyNames) {
            Object[] lastUpdated = this.lastUpdated;
            if (lastUpdated != null && Arrays.equals(lastUpdated, propertyNames)) {
                return;
            }
            int size = propertyNames.length;
            Map<ConfigurationPropertyName, Set<String>> mappings = this.cloneOrCreate(this.mappings, size);
            Map<String, ConfigurationPropertyName> reverseMappings = this.cloneOrCreate(this.reverseMappings, size);
            Map<ConfigurationPropertyName, Set<ConfigurationPropertyName>> descendants = this.cloneOrCreate(this.descendants, size);
            for (PropertyMapper propertyMapper : this.mappers) {
                for (String propertyName : propertyNames) {
                    ConfigurationPropertyName configurationPropertyName;
                    if (reverseMappings.containsKey(propertyName) || (configurationPropertyName = propertyMapper.map(propertyName)) == null || configurationPropertyName.isEmpty()) continue;
                    this.add(mappings, configurationPropertyName, propertyName);
                    reverseMappings.put(propertyName, configurationPropertyName);
                    if (!this.trackDescendants) continue;
                    this.addParents(descendants, configurationPropertyName);
                }
            }
            this.mappings = mappings;
            this.reverseMappings = reverseMappings;
            this.descendants = descendants;
            this.lastUpdated = this.immutable ? null : propertyNames;
            this.configurationPropertyNames = this.immutable ? reverseMappings.values().toArray(new ConfigurationPropertyName[0]) : null;
        }

        private <K, V> Map<K, V> cloneOrCreate(Map<K, V> source, int size) {
            return source != null ? new LinkedHashMap<K, V>(source) : new LinkedHashMap(size);
        }

        private void addParents(Map<ConfigurationPropertyName, Set<ConfigurationPropertyName>> descendants, ConfigurationPropertyName name) {
            ConfigurationPropertyName parent = name;
            while (!parent.isEmpty()) {
                this.add(descendants, parent, name);
                parent = parent.getParent();
            }
        }

        private <K, T> void add(Map<K, Set<T>> map, K key, T value) {
            map.computeIfAbsent(key, k -> new HashSet()).add(value);
        }

        Set<String> getMapped(ConfigurationPropertyName configurationPropertyName) {
            return this.mappings.getOrDefault(configurationPropertyName, Collections.emptySet());
        }

        ConfigurationPropertyName[] getConfigurationPropertyNames(String[] propertyNames) {
            ConfigurationPropertyName[] names = this.configurationPropertyNames;
            if (names != null) {
                return names;
            }
            Map<String, ConfigurationPropertyName> reverseMappings = this.reverseMappings;
            if (reverseMappings == null || reverseMappings.isEmpty()) {
                return EMPTY_NAMES_ARRAY;
            }
            names = new ConfigurationPropertyName[propertyNames.length];
            for (int i = 0; i < propertyNames.length; ++i) {
                names[i] = reverseMappings.get(propertyNames[i]);
            }
            return names;
        }

        ConfigurationPropertyState containsDescendantOf(ConfigurationPropertyName name, BiPredicate<ConfigurationPropertyName, ConfigurationPropertyName> ancestorOfCheck) {
            if (name.isEmpty() && !this.descendants.isEmpty()) {
                return ConfigurationPropertyState.PRESENT;
            }
            Set candidates = this.descendants.getOrDefault(name, Collections.emptySet());
            for (ConfigurationPropertyName candidate : candidates) {
                if (!ancestorOfCheck.test(name, candidate)) continue;
                return ConfigurationPropertyState.PRESENT;
            }
            return ConfigurationPropertyState.ABSENT;
        }
    }
}

