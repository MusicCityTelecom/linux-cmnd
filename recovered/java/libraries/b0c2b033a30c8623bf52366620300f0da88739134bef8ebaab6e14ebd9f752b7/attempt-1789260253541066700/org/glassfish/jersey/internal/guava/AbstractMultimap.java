/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.glassfish.jersey.internal.guava.Iterators;
import org.glassfish.jersey.internal.guava.Maps;
import org.glassfish.jersey.internal.guava.Multimap;
import org.glassfish.jersey.internal.guava.Multimaps;
import org.glassfish.jersey.internal.guava.Preconditions;
import org.glassfish.jersey.internal.guava.SetMultimap;
import org.glassfish.jersey.internal.guava.Sets;

abstract class AbstractMultimap<K, V>
implements Multimap<K, V> {
    private transient Collection<Map.Entry<K, V>> entries;
    private transient Set<K> keySet;
    private transient Collection<V> values;
    private transient Map<K, Collection<V>> asMap;

    AbstractMultimap() {
    }

    @Override
    public boolean containsValue(Object value) {
        for (Collection<V> collection : this.asMap().values()) {
            if (!collection.contains(value)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean containsEntry(Object key, Object value) {
        Collection<V> collection = this.asMap().get(key);
        return collection != null && collection.contains(value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        Collection<V> collection = this.asMap().get(key);
        return collection != null && collection.remove(value);
    }

    @Override
    public boolean put(K key, V value) {
        return this.get(key).add(value);
    }

    @Override
    public boolean putAll(K key, Iterable<? extends V> values) {
        Preconditions.checkNotNull(values);
        if (values instanceof Collection) {
            Collection valueCollection = (Collection)values;
            return !valueCollection.isEmpty() && this.get(key).addAll(valueCollection);
        }
        Iterator<V> valueItr = values.iterator();
        return valueItr.hasNext() && Iterators.addAll(this.get(key), valueItr);
    }

    @Override
    public Collection<Map.Entry<K, V>> entries() {
        Collection<Map.Entry<K, V>> result = this.entries;
        return result == null ? (this.entries = this.createEntries()) : result;
    }

    private Collection<Map.Entry<K, V>> createEntries() {
        if (this instanceof SetMultimap) {
            return new EntrySet();
        }
        return new Entries();
    }

    abstract Iterator<Map.Entry<K, V>> entryIterator();

    @Override
    public Set<K> keySet() {
        Set<K> result = this.keySet;
        return result == null ? (this.keySet = this.createKeySet()) : result;
    }

    Set<K> createKeySet() {
        return new Maps.KeySet<K, Collection<V>>(this.asMap());
    }

    @Override
    public Collection<V> values() {
        Collection<V> result = this.values;
        return result == null ? (this.values = this.createValues()) : result;
    }

    private Collection<V> createValues() {
        return new Values();
    }

    Iterator<V> valueIterator() {
        return Maps.valueIterator(this.entries().iterator());
    }

    @Override
    public Map<K, Collection<V>> asMap() {
        Map<K, Collection<Collection<V>>> result = this.asMap;
        return result == null ? (this.asMap = this.createAsMap()) : result;
    }

    abstract Map<K, Collection<V>> createAsMap();

    @Override
    public boolean equals(Object object) {
        return Multimaps.equalsImpl(this, object);
    }

    @Override
    public int hashCode() {
        return this.asMap().hashCode();
    }

    public String toString() {
        return this.asMap().toString();
    }

    private class Values
    extends AbstractCollection<V> {
        private Values() {
        }

        @Override
        public Iterator<V> iterator() {
            return AbstractMultimap.this.valueIterator();
        }

        @Override
        public int size() {
            return AbstractMultimap.this.size();
        }

        @Override
        public boolean contains(Object o) {
            return AbstractMultimap.this.containsValue(o);
        }

        @Override
        public void clear() {
            AbstractMultimap.this.clear();
        }
    }

    private class EntrySet
    extends Entries
    implements Set<Map.Entry<K, V>> {
        private EntrySet() {
        }

        @Override
        public int hashCode() {
            return Sets.hashCodeImpl(this);
        }

        @Override
        public boolean equals(Object obj) {
            return Sets.equalsImpl(this, obj);
        }
    }

    private class Entries
    extends Multimaps.Entries<K, V> {
        private Entries() {
        }

        @Override
        Multimap<K, V> multimap() {
            return AbstractMultimap.this;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return AbstractMultimap.this.entryIterator();
        }
    }
}

