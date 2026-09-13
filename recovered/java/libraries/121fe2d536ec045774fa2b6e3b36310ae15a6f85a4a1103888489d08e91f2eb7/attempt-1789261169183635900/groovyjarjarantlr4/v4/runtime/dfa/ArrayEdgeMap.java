/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.dfa;

import groovyjarjarantlr4.v4.runtime.dfa.AbstractEdgeMap;
import groovyjarjarantlr4.v4.runtime.dfa.EdgeMap;
import groovyjarjarantlr4.v4.runtime.dfa.EmptyEdgeMap;
import groovyjarjarantlr4.v4.runtime.dfa.HashEdgeMap;
import groovyjarjarantlr4.v4.runtime.dfa.SingletonEdgeMap;
import groovyjarjarantlr4.v4.runtime.dfa.SparseEdgeMap;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReferenceArray;

public final class ArrayEdgeMap<T>
extends AbstractEdgeMap<T> {
    private final AtomicReferenceArray<T> arrayData;
    private final AtomicInteger size;

    public ArrayEdgeMap(int minIndex, int maxIndex) {
        super(minIndex, maxIndex);
        this.arrayData = new AtomicReferenceArray(maxIndex - minIndex + 1);
        this.size = new AtomicInteger();
    }

    @Override
    public int size() {
        return this.size.get();
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean containsKey(int key) {
        return this.get(key) != null;
    }

    @Override
    public T get(int key) {
        if (key < this.minIndex || key > this.maxIndex) {
            return null;
        }
        return this.arrayData.get(key - this.minIndex);
    }

    @Override
    public ArrayEdgeMap<T> put(int key, T value) {
        if (key >= this.minIndex && key <= this.maxIndex) {
            T existing = this.arrayData.getAndSet(key - this.minIndex, value);
            if (existing == null && value != null) {
                this.size.incrementAndGet();
            } else if (existing != null && value == null) {
                this.size.decrementAndGet();
            }
        }
        return this;
    }

    @Override
    public ArrayEdgeMap<T> remove(int key) {
        return this.put(key, (Object)null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ArrayEdgeMap<T> putAll(EdgeMap<? extends T> m) {
        if (m.isEmpty()) {
            return this;
        }
        if (m instanceof HashEdgeMap) {
            HashEdgeMap other = (HashEdgeMap)m;
            AtomicIntegerArray keys = other.getKeys();
            T[] values = other.getValues();
            AbstractEdgeMap result = this;
            for (int i = 0; i < values.length; ++i) {
                Object value = values[i];
                if (value == null) continue;
                result = result.put(keys.get(i), value);
            }
            return result;
        }
        if (m instanceof ArrayEdgeMap) {
            ArrayEdgeMap other = (ArrayEdgeMap)m;
            int minOverlap = Math.max(this.minIndex, other.minIndex);
            int maxOverlap = Math.min(this.maxIndex, other.maxIndex);
            AbstractEdgeMap result = this;
            for (int i = minOverlap; i <= maxOverlap; ++i) {
                result = result.put(i, (Object)m.get(i));
            }
            return result;
        }
        if (m instanceof SingletonEdgeMap) {
            SingletonEdgeMap other = (SingletonEdgeMap)m;
            assert (!other.isEmpty());
            return this.put(other.getKey(), other.getValue());
        }
        if (m instanceof SparseEdgeMap) {
            SparseEdgeMap other;
            SparseEdgeMap sparseEdgeMap = other = (SparseEdgeMap)m;
            synchronized (sparseEdgeMap) {
                int[] keys = other.getKeys();
                List values = other.getValues();
                AbstractEdgeMap result = this;
                for (int i = 0; i < values.size(); ++i) {
                    result = result.put(keys[i], values.get(i));
                }
                return result;
            }
        }
        throw new UnsupportedOperationException(String.format("EdgeMap of type %s is supported yet.", m.getClass().getName()));
    }

    @Override
    public EmptyEdgeMap<T> clear() {
        return new EmptyEdgeMap(this.minIndex, this.maxIndex);
    }

    @Override
    public Map<Integer, T> toMap() {
        if (this.isEmpty()) {
            return Collections.emptyMap();
        }
        LinkedHashMap<Integer, T> result = new LinkedHashMap<Integer, T>();
        for (int i = 0; i < this.arrayData.length(); ++i) {
            T element = this.arrayData.get(i);
            if (element == null) continue;
            result.put(i + this.minIndex, element);
        }
        return result;
    }

    @Override
    public Set<Map.Entry<Integer, T>> entrySet() {
        return new EntrySet();
    }

    private class EntryIterator
    implements Iterator<Map.Entry<Integer, T>> {
        private int currentIndex = -1;

        private EntryIterator() {
        }

        @Override
        public boolean hasNext() {
            return this.currentIndex < ArrayEdgeMap.this.arrayData.length() - 1;
        }

        @Override
        public Map.Entry<Integer, T> next() {
            Object element = null;
            while (element == null && this.currentIndex < ArrayEdgeMap.this.arrayData.length() - 1) {
                ++this.currentIndex;
                element = ArrayEdgeMap.this.arrayData.get(this.currentIndex);
            }
            if (element == null) {
                throw new NoSuchElementException();
            }
            ++this.currentIndex;
            final Object currentElement = element;
            return new Map.Entry<Integer, T>(){
                private final int key;
                private final T value;
                {
                    this.key = ArrayEdgeMap.this.minIndex + EntryIterator.this.currentIndex - 1;
                    this.value = currentElement;
                }

                @Override
                public Integer getKey() {
                    return this.key;
                }

                @Override
                public T getValue() {
                    return this.value;
                }

                @Override
                public T setValue(T value) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            };
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class EntrySet
    extends AbstractEdgeMap.AbstractEntrySet {
        private EntrySet() {
        }

        @Override
        public Iterator<Map.Entry<Integer, T>> iterator() {
            return new EntryIterator();
        }
    }
}

