/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.PropertySource
 *  org.springframework.core.env.PropertySource$StubPropertySource
 *  org.springframework.util.Assert
 *  org.springframework.util.ConcurrentReferenceHashMap
 *  org.springframework.util.ConcurrentReferenceHashMap$ReferenceType
 */
package org.springframework.boot.context.properties.source;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySourcesPropertySource;
import org.springframework.boot.context.properties.source.SpringConfigurationPropertySource;
import org.springframework.boot.origin.OriginLookup;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.util.Assert;
import org.springframework.util.ConcurrentReferenceHashMap;

class SpringConfigurationPropertySources
implements Iterable<ConfigurationPropertySource> {
    private final Iterable<PropertySource<?>> sources;
    private final Map<PropertySource<?>, ConfigurationPropertySource> cache = new ConcurrentReferenceHashMap(16, ConcurrentReferenceHashMap.ReferenceType.SOFT);

    SpringConfigurationPropertySources(Iterable<PropertySource<?>> sources) {
        Assert.notNull(sources, (String)"Sources must not be null");
        this.sources = sources;
    }

    boolean isUsingSources(Iterable<PropertySource<?>> sources) {
        return this.sources == sources;
    }

    @Override
    public Iterator<ConfigurationPropertySource> iterator() {
        return new SourcesIterator(this.sources.iterator(), this::adapt);
    }

    private ConfigurationPropertySource adapt(PropertySource<?> source) {
        ConfigurationPropertySource result = this.cache.get(source);
        if (result != null && result.getUnderlyingSource() == source) {
            return result;
        }
        result = SpringConfigurationPropertySource.from(source);
        if (source instanceof OriginLookup) {
            result = result.withPrefix(((OriginLookup)source).getPrefix());
        }
        this.cache.put(source, result);
        return result;
    }

    private static class SourcesIterator
    implements Iterator<ConfigurationPropertySource> {
        private final Deque<Iterator<PropertySource<?>>> iterators = new ArrayDeque(4);
        private ConfigurationPropertySource next;
        private final Function<PropertySource<?>, ConfigurationPropertySource> adapter;

        SourcesIterator(Iterator<PropertySource<?>> iterator, Function<PropertySource<?>, ConfigurationPropertySource> adapter) {
            this.iterators.push(iterator);
            this.adapter = adapter;
        }

        @Override
        public boolean hasNext() {
            return this.fetchNext() != null;
        }

        @Override
        public ConfigurationPropertySource next() {
            ConfigurationPropertySource next = this.fetchNext();
            if (next == null) {
                throw new NoSuchElementException();
            }
            this.next = null;
            return next;
        }

        private ConfigurationPropertySource fetchNext() {
            if (this.next == null) {
                if (this.iterators.isEmpty()) {
                    return null;
                }
                if (!this.iterators.peek().hasNext()) {
                    this.iterators.pop();
                    return this.fetchNext();
                }
                PropertySource<?> candidate = this.iterators.peek().next();
                if (candidate.getSource() instanceof ConfigurableEnvironment) {
                    this.push((ConfigurableEnvironment)candidate.getSource());
                    return this.fetchNext();
                }
                if (this.isIgnored(candidate)) {
                    return this.fetchNext();
                }
                this.next = this.adapter.apply(candidate);
            }
            return this.next;
        }

        private void push(ConfigurableEnvironment environment) {
            this.iterators.push(environment.getPropertySources().iterator());
        }

        private boolean isIgnored(PropertySource<?> candidate) {
            return candidate instanceof PropertySource.StubPropertySource || candidate instanceof ConfigurationPropertySourcesPropertySource;
        }
    }
}

