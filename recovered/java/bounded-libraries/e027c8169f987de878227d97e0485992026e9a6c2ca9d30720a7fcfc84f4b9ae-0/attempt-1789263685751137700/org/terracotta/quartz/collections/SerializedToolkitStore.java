/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.terracotta.toolkit.concurrent.locks.ToolkitReadWriteLock
 *  org.terracotta.toolkit.config.Configuration
 *  org.terracotta.toolkit.search.QueryBuilder
 *  org.terracotta.toolkit.search.attribute.ToolkitAttributeExtractor
 *  org.terracotta.toolkit.store.ToolkitStore
 */
package org.terracotta.quartz.collections;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.terracotta.quartz.collections.SerializationHelper;
import org.terracotta.toolkit.concurrent.locks.ToolkitReadWriteLock;
import org.terracotta.toolkit.config.Configuration;
import org.terracotta.toolkit.search.QueryBuilder;
import org.terracotta.toolkit.search.attribute.ToolkitAttributeExtractor;
import org.terracotta.toolkit.store.ToolkitStore;

public class SerializedToolkitStore<K, V extends Serializable>
implements ToolkitStore<K, V> {
    private final ToolkitStore<String, V> toolkitStore;

    public SerializedToolkitStore(ToolkitStore toolkitMap) {
        this.toolkitStore = toolkitMap;
    }

    public int size() {
        return this.toolkitStore.size();
    }

    public boolean isEmpty() {
        return this.toolkitStore.isEmpty();
    }

    private static String serializeToString(Object key) {
        try {
            return SerializationHelper.serializeToString(key);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object deserializeFromString(String key) {
        try {
            return SerializationHelper.deserializeFromString(key);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean containsKey(Object key) {
        return this.toolkitStore.containsKey((Object)SerializedToolkitStore.serializeToString(key));
    }

    public V get(Object key) {
        return (V)((Serializable)this.toolkitStore.get((Object)SerializedToolkitStore.serializeToString(key)));
    }

    public V put(K key, V value) {
        return (V)((Serializable)this.toolkitStore.put((Object)SerializedToolkitStore.serializeToString(key), value));
    }

    public V remove(Object key) {
        return (V)((Serializable)this.toolkitStore.remove((Object)SerializedToolkitStore.serializeToString(key)));
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        HashMap<String, V> tempMap = new HashMap<String, V>();
        for (Map.Entry<K, V> entry : m.entrySet()) {
            tempMap.put(SerializedToolkitStore.serializeToString(entry.getKey()), entry.getValue());
        }
        this.toolkitStore.putAll(tempMap);
    }

    public void clear() {
        this.toolkitStore.clear();
    }

    public Set<K> keySet() {
        return new ToolkitKeySet(this.toolkitStore.keySet());
    }

    public boolean isDestroyed() {
        return this.toolkitStore.isDestroyed();
    }

    public void destroy() {
        this.toolkitStore.destroy();
    }

    public String getName() {
        return this.toolkitStore.getName();
    }

    public ToolkitReadWriteLock createLockForKey(K key) {
        return this.toolkitStore.createLockForKey((Object)SerializedToolkitStore.serializeToString(key));
    }

    public void removeNoReturn(Object key) {
        this.toolkitStore.removeNoReturn((Object)SerializedToolkitStore.serializeToString(key));
    }

    public void putNoReturn(K key, V value) {
        this.toolkitStore.putNoReturn((Object)SerializedToolkitStore.serializeToString(key), value);
    }

    public Map<K, V> getAll(Collection<? extends K> keys) {
        HashSet<String> tempSet = new HashSet<String>();
        for (K key : keys) {
            tempSet.add(SerializedToolkitStore.serializeToString(key));
        }
        Map m = this.toolkitStore.getAll(tempSet);
        Map tempMap = m.isEmpty() ? Collections.EMPTY_MAP : new HashMap();
        for (Map.Entry entry : m.entrySet()) {
            tempMap.put(SerializedToolkitStore.deserializeFromString((String)entry.getKey()), entry.getValue());
        }
        return tempMap;
    }

    public Configuration getConfiguration() {
        return this.toolkitStore.getConfiguration();
    }

    public void setConfigField(String name, Serializable value) {
        this.toolkitStore.setConfigField(name, value);
    }

    public boolean containsValue(Object value) {
        return this.toolkitStore.containsValue(value);
    }

    public V putIfAbsent(K key, V value) {
        return (V)((Serializable)this.toolkitStore.putIfAbsent((Object)SerializedToolkitStore.serializeToString(key), value));
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return new ToolkitEntrySet(this.toolkitStore.entrySet());
    }

    public Collection<V> values() {
        return this.toolkitStore.values();
    }

    public boolean remove(Object key, Object value) {
        return this.toolkitStore.remove((Object)SerializedToolkitStore.serializeToString(key), value);
    }

    public boolean replace(K key, V oldValue, V newValue) {
        return this.toolkitStore.replace((Object)SerializedToolkitStore.serializeToString(key), oldValue, newValue);
    }

    public V replace(K key, V value) {
        return (V)((Serializable)this.toolkitStore.replace((Object)SerializedToolkitStore.serializeToString(key), value));
    }

    public boolean isBulkLoadEnabled() {
        return this.toolkitStore.isBulkLoadEnabled();
    }

    public boolean isNodeBulkLoadEnabled() {
        return this.toolkitStore.isNodeBulkLoadEnabled();
    }

    public void setNodeBulkLoadEnabled(boolean enabledBulkLoad) {
        this.toolkitStore.setNodeBulkLoadEnabled(enabledBulkLoad);
    }

    public void waitUntilBulkLoadComplete() throws InterruptedException {
        this.toolkitStore.waitUntilBulkLoadComplete();
    }

    public void setAttributeExtractor(ToolkitAttributeExtractor attrExtractor) {
        this.toolkitStore.setAttributeExtractor(attrExtractor);
    }

    public QueryBuilder createQueryBuilder() {
        throw new UnsupportedOperationException();
    }

    private static class ToolkitKeyIterator<K>
    implements Iterator<K> {
        private final Iterator<String> iter;

        public ToolkitKeyIterator(Iterator<String> iter) {
            this.iter = iter;
        }

        @Override
        public boolean hasNext() {
            return this.iter.hasNext();
        }

        @Override
        public K next() {
            String k = this.iter.next();
            if (k == null) {
                return null;
            }
            return (K)SerializedToolkitStore.deserializeFromString(k);
        }

        @Override
        public void remove() {
            this.iter.remove();
        }
    }

    private static class ToolkitKeySet<K>
    implements Set<K> {
        private final Set<String> set;

        public ToolkitKeySet(Set<String> set) {
            this.set = set;
        }

        @Override
        public int size() {
            return this.set.size();
        }

        @Override
        public boolean isEmpty() {
            return this.set.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return this.set.contains(SerializedToolkitStore.serializeToString(o));
        }

        @Override
        public Iterator<K> iterator() {
            return new ToolkitKeyIterator(this.set.iterator());
        }

        @Override
        public Object[] toArray() {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean add(K e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends K> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }
    }

    private static class ToolkitMapEntry<K, V>
    implements Map.Entry<K, V> {
        private final K k;
        private final V v;

        public ToolkitMapEntry(K k, V v) {
            this.k = k;
            this.v = v;
        }

        @Override
        public K getKey() {
            return this.k;
        }

        @Override
        public V getValue() {
            return this.v;
        }

        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException();
        }
    }

    private static class ToolkitEntryIterator<K, V>
    implements Iterator<Map.Entry<K, V>> {
        private final Iterator<Map.Entry<String, V>> iter;

        public ToolkitEntryIterator(Iterator<Map.Entry<String, V>> iter) {
            this.iter = iter;
        }

        @Override
        public boolean hasNext() {
            return this.iter.hasNext();
        }

        @Override
        public Map.Entry<K, V> next() {
            Map.Entry<String, V> entry = this.iter.next();
            if (entry == null) {
                return null;
            }
            return new ToolkitMapEntry<Object, V>(SerializedToolkitStore.deserializeFromString(entry.getKey()), entry.getValue());
        }

        @Override
        public void remove() {
            this.iter.remove();
        }
    }

    private static class ToolkitEntrySet<K, V>
    implements Set<Map.Entry<K, V>> {
        private final Set<Map.Entry<String, V>> set;

        public ToolkitEntrySet(Set<Map.Entry<String, V>> set) {
            this.set = set;
        }

        @Override
        public int size() {
            return this.set.size();
        }

        @Override
        public boolean isEmpty() {
            return this.set.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry)o;
            ToolkitMapEntry toolkitEntry = null;
            toolkitEntry = new ToolkitMapEntry(SerializedToolkitStore.serializeToString(entry.getKey()), entry.getValue());
            return this.set.contains(toolkitEntry);
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new ToolkitEntryIterator(this.set.iterator());
        }

        @Override
        public Object[] toArray() {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean add(Map.Entry<K, V> e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends Map.Entry<K, V>> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }
    }
}

