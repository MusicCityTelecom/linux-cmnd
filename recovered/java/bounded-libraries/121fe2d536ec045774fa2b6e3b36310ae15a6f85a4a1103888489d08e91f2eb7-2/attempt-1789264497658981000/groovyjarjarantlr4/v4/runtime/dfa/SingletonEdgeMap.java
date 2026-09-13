/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.dfa;

import groovyjarjarantlr4.v4.runtime.dfa.AbstractEdgeMap;
import groovyjarjarantlr4.v4.runtime.dfa.EdgeMap;
import groovyjarjarantlr4.v4.runtime.dfa.EmptyEdgeMap;
import groovyjarjarantlr4.v4.runtime.dfa.HashEdgeMap;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public final class SingletonEdgeMap<T>
extends AbstractEdgeMap<T> {
    private final int key;
    private final T value;

    public SingletonEdgeMap(int minIndex, int maxIndex, int key, T value) {
        super(minIndex, maxIndex);
        if (key >= minIndex && key <= maxIndex) {
            this.key = key;
            this.value = value;
        } else {
            this.key = 0;
            this.value = null;
        }
    }

    public int getKey() {
        return this.key;
    }

    public T getValue() {
        return this.value;
    }

    @Override
    public int size() {
        return this.value != null ? 1 : 0;
    }

    @Override
    public boolean isEmpty() {
        return this.value == null;
    }

    @Override
    public boolean containsKey(int key) {
        return key == this.key && this.value != null;
    }

    @Override
    public T get(int key) {
        if (key == this.key) {
            return this.value;
        }
        return null;
    }

    @Override
    public AbstractEdgeMap<T> put(int key, T value) {
        if (key < this.minIndex || key > this.maxIndex) {
            return this;
        }
        if (key == this.key || this.value == null) {
            return new SingletonEdgeMap<T>(this.minIndex, this.maxIndex, key, value);
        }
        if (value != null) {
            EdgeMap result = new HashEdgeMap(this.minIndex, this.maxIndex);
            result = ((AbstractEdgeMap)result).put(this.key, (Object)this.value);
            result = ((AbstractEdgeMap)result).put(key, (Object)value);
            return result;
        }
        return this;
    }

    @Override
    public AbstractEdgeMap<T> remove(int key) {
        if (key == this.key && this.value != null) {
            return new EmptyEdgeMap(this.minIndex, this.maxIndex);
        }
        return this;
    }

    @Override
    public AbstractEdgeMap<T> clear() {
        if (this.value != null) {
            return new EmptyEdgeMap(this.minIndex, this.maxIndex);
        }
        return this;
    }

    @Override
    public Map<Integer, T> toMap() {
        if (this.isEmpty()) {
            return Collections.emptyMap();
        }
        return Collections.singletonMap(this.key, this.value);
    }

    @Override
    public Set<Map.Entry<Integer, T>> entrySet() {
        return new EntrySet();
    }

    private class EntryIterator
    implements Iterator<Map.Entry<Integer, T>> {
        private int current;

        private EntryIterator() {
        }

        @Override
        public boolean hasNext() {
            return this.current < SingletonEdgeMap.this.size();
        }

        @Override
        public Map.Entry<Integer, T> next() {
            if (this.current >= SingletonEdgeMap.this.size()) {
                throw new NoSuchElementException();
            }
            ++this.current;
            return new Map.Entry<Integer, T>(){
                private final int key;
                private final T value;
                {
                    this.key = SingletonEdgeMap.this.key;
                    this.value = SingletonEdgeMap.this.value;
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

