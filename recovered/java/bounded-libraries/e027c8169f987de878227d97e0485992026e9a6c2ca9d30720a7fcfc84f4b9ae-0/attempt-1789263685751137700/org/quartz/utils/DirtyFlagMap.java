/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.utils;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DirtyFlagMap<K, V>
implements Map<K, V>,
Cloneable,
Serializable {
    private static final long serialVersionUID = 1433884852607126222L;
    private boolean dirty = false;
    private Map<K, V> map;

    public DirtyFlagMap() {
        this.map = new HashMap();
    }

    public DirtyFlagMap(int initialCapacity) {
        this.map = new HashMap(initialCapacity);
    }

    public DirtyFlagMap(int initialCapacity, float loadFactor) {
        this.map = new HashMap(initialCapacity, loadFactor);
    }

    public void clearDirtyFlag() {
        this.dirty = false;
    }

    public boolean isDirty() {
        return this.dirty;
    }

    public Map<K, V> getWrappedMap() {
        return this.map;
    }

    @Override
    public void clear() {
        if (!this.map.isEmpty()) {
            this.dirty = true;
        }
        this.map.clear();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object val) {
        return this.map.containsValue(val);
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return new DirtyFlagMapEntrySet(this.map.entrySet());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof DirtyFlagMap)) {
            return false;
        }
        return this.map.equals(((DirtyFlagMap)obj).getWrappedMap());
    }

    @Override
    public int hashCode() {
        return this.map.hashCode();
    }

    @Override
    public V get(Object key) {
        return this.map.get(key);
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public Set<K> keySet() {
        return new DirtyFlagSet<K>(this.map.keySet());
    }

    @Override
    public V put(K key, V val) {
        this.dirty = true;
        return this.map.put(key, val);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> t) {
        if (!t.isEmpty()) {
            this.dirty = true;
        }
        this.map.putAll(t);
    }

    @Override
    public V remove(Object key) {
        V obj = this.map.remove(key);
        if (obj != null) {
            this.dirty = true;
        }
        return obj;
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public Collection<V> values() {
        return new DirtyFlagCollection<V>(this.map.values());
    }

    public Object clone() {
        DirtyFlagMap copy;
        try {
            copy = (DirtyFlagMap)super.clone();
            if (this.map instanceof HashMap) {
                copy.map = (Map)((HashMap)this.map).clone();
            }
        }
        catch (CloneNotSupportedException ex) {
            throw new IncompatibleClassChangeError("Not Cloneable.");
        }
        return copy;
    }

    private class DirtyFlagMapEntry
    implements Map.Entry<K, V> {
        private Map.Entry<K, V> entry;

        public DirtyFlagMapEntry(Map.Entry<K, V> entry) {
            this.entry = entry;
        }

        @Override
        public V setValue(V o) {
            DirtyFlagMap.this.dirty = true;
            return this.entry.setValue(o);
        }

        @Override
        public K getKey() {
            return this.entry.getKey();
        }

        @Override
        public V getValue() {
            return this.entry.getValue();
        }

        @Override
        public boolean equals(Object o) {
            return this.entry.equals(o);
        }
    }

    private class DirtyFlagMapEntryIterator
    extends DirtyFlagIterator<Map.Entry<K, V>> {
        public DirtyFlagMapEntryIterator(Iterator<Map.Entry<K, V>> iterator) {
            super(iterator);
        }

        @Override
        public DirtyFlagMapEntry next() {
            return new DirtyFlagMapEntry((Map.Entry)super.next());
        }
    }

    private class DirtyFlagMapEntrySet
    extends DirtyFlagSet<Map.Entry<K, V>> {
        public DirtyFlagMapEntrySet(Set<Map.Entry<K, V>> set) {
            super(set);
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new DirtyFlagMapEntryIterator(this.getWrappedSet().iterator());
        }

        @Override
        public Object[] toArray() {
            return this.toArray((U[])new Object[super.size()]);
        }

        @Override
        public <U> U[] toArray(U[] array) {
            if (!array.getClass().getComponentType().isAssignableFrom(Map.Entry.class)) {
                throw new IllegalArgumentException("Array must be of type assignable from Map.Entry");
            }
            int size = super.size();
            U[] result = array.length < size ? (Object[])Array.newInstance(array.getClass().getComponentType(), size) : array;
            Iterator entryIter = this.iterator();
            for (int i = 0; i < size; ++i) {
                result[i] = entryIter.next();
            }
            if (result.length > size) {
                result[size] = null;
            }
            return result;
        }
    }

    private class DirtyFlagIterator<T>
    implements Iterator<T> {
        private Iterator<T> iterator;

        public DirtyFlagIterator(Iterator<T> iterator) {
            this.iterator = iterator;
        }

        @Override
        public void remove() {
            DirtyFlagMap.this.dirty = true;
            this.iterator.remove();
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override
        public T next() {
            return this.iterator.next();
        }
    }

    private class DirtyFlagSet<T>
    extends DirtyFlagCollection<T>
    implements Set<T> {
        public DirtyFlagSet(Set<T> set) {
            super(set);
        }

        protected Set<T> getWrappedSet() {
            return (Set)this.getWrappedCollection();
        }
    }

    private class DirtyFlagCollection<T>
    implements Collection<T> {
        private Collection<T> collection;

        public DirtyFlagCollection(Collection<T> c) {
            this.collection = c;
        }

        protected Collection<T> getWrappedCollection() {
            return this.collection;
        }

        @Override
        public Iterator<T> iterator() {
            return new DirtyFlagIterator<T>(this.collection.iterator());
        }

        @Override
        public boolean remove(Object o) {
            boolean removed = this.collection.remove(o);
            if (removed) {
                DirtyFlagMap.this.dirty = true;
            }
            return removed;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            boolean changed = this.collection.removeAll(c);
            if (changed) {
                DirtyFlagMap.this.dirty = true;
            }
            return changed;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            boolean changed = this.collection.retainAll(c);
            if (changed) {
                DirtyFlagMap.this.dirty = true;
            }
            return changed;
        }

        @Override
        public void clear() {
            if (!this.collection.isEmpty()) {
                DirtyFlagMap.this.dirty = true;
            }
            this.collection.clear();
        }

        @Override
        public int size() {
            return this.collection.size();
        }

        @Override
        public boolean isEmpty() {
            return this.collection.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return this.collection.contains(o);
        }

        @Override
        public boolean add(T o) {
            return this.collection.add(o);
        }

        @Override
        public boolean addAll(Collection<? extends T> c) {
            return this.collection.addAll(c);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return this.collection.containsAll(c);
        }

        @Override
        public Object[] toArray() {
            return this.collection.toArray();
        }

        @Override
        public <U> U[] toArray(U[] array) {
            return this.collection.toArray(array);
        }
    }
}

