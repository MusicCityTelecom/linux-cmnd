/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.dfa;

import groovyjarjarantlr4.v4.runtime.dfa.AbstractEdgeMap;
import groovyjarjarantlr4.v4.runtime.dfa.ArrayEdgeMap;
import groovyjarjarantlr4.v4.runtime.dfa.EdgeMap;
import groovyjarjarantlr4.v4.runtime.dfa.EmptyEdgeMap;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicIntegerArray;

public final class HashEdgeMap<T>
extends AbstractEdgeMap<T> {
    private static final int DEFAULT_MAX_SIZE = 2;
    private final AtomicIntegerArray keys;
    private final T[] values;

    public HashEdgeMap(int minIndex, int maxIndex) {
        this(minIndex, maxIndex, 2);
    }

    public HashEdgeMap(int minIndex, int maxIndex, int maxSparseSize) {
        super(minIndex, maxIndex);
        this.keys = new AtomicIntegerArray(maxSparseSize);
        this.values = new Object[maxSparseSize];
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private HashEdgeMap(@NotNull HashEdgeMap<T> map, int maxSparseSize) {
        super(map.minIndex, map.maxIndex);
        HashEdgeMap<T> hashEdgeMap = map;
        synchronized (hashEdgeMap) {
            if (maxSparseSize < map.values.length) {
                throw new IllegalArgumentException();
            }
            this.keys = new AtomicIntegerArray(maxSparseSize);
            this.values = new Object[maxSparseSize];
            for (int i = 0; i < map.values.length; ++i) {
                int key = map.keys.get(i);
                T value = map.values[i];
                if (value == null) continue;
                int bucket = this.bucket(key);
                this.keys.set(bucket, key);
                this.values[bucket] = value;
            }
        }
    }

    private static int bucket(int length, int key) {
        return key & length - 1;
    }

    private int bucket(int key) {
        return key & this.values.length - 1;
    }

    @NotNull
    AtomicIntegerArray getKeys() {
        return this.keys;
    }

    @NotNull
    T[] getValues() {
        return this.values;
    }

    @Override
    public int size() {
        int size = 0;
        for (T edge : this.values) {
            if (edge == null) continue;
            ++size;
        }
        return size;
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
        int bucket = this.bucket(key);
        T value = this.values[bucket];
        if (value == null || this.keys.get(bucket) != key) {
            return null;
        }
        return value;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public AbstractEdgeMap<T> put(int key, T value) {
        if (key < this.minIndex || key > this.maxIndex) {
            return this;
        }
        if (value == null) {
            return this.remove(key);
        }
        HashEdgeMap hashEdgeMap = this;
        synchronized (hashEdgeMap) {
            int bucket = this.bucket(key);
            int currentKey = this.keys.get(bucket);
            if (currentKey == key) {
                this.values[bucket] = value;
                return this;
            }
            T currentValue = this.values[bucket];
            if (currentValue == null) {
                this.keys.set(bucket, key);
                this.values[bucket] = value;
                return this;
            }
            int newSize = this.values.length;
            do {
                if ((newSize *= 2) < (this.maxIndex - this.minIndex + 1) / 2) continue;
                AbstractEdgeMap arrayMap = new ArrayEdgeMap(this.minIndex, this.maxIndex);
                arrayMap = ((ArrayEdgeMap)arrayMap).putAll((EdgeMap)this);
                ((ArrayEdgeMap)arrayMap).put(key, (Object)value);
                return arrayMap;
            } while (HashEdgeMap.bucket(newSize, currentKey) == HashEdgeMap.bucket(newSize, key));
            HashEdgeMap<T> resized = new HashEdgeMap<T>(this, newSize);
            resized.put(key, (Object)value);
            return resized;
        }
    }

    @Override
    public HashEdgeMap<T> remove(int key) {
        if (this.get(key) == null) {
            return this;
        }
        HashEdgeMap<T> result = new HashEdgeMap<T>(this, this.values.length);
        int bucket = super.bucket(key);
        result.keys.set(bucket, 0);
        result.values[bucket] = null;
        return result;
    }

    @Override
    public AbstractEdgeMap<T> clear() {
        if (this.isEmpty()) {
            return this;
        }
        return new EmptyEdgeMap(this.minIndex, this.maxIndex);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Map<Integer, T> toMap() {
        if (this.isEmpty()) {
            return Collections.emptyMap();
        }
        HashEdgeMap hashEdgeMap = this;
        synchronized (hashEdgeMap) {
            TreeMap<Integer, T> result = new TreeMap<Integer, T>();
            for (int i = 0; i < this.values.length; ++i) {
                int key = this.keys.get(i);
                T value = this.values[i];
                if (value == null) continue;
                result.put(key, value);
            }
            return result;
        }
    }

    @Override
    public Set<Map.Entry<Integer, T>> entrySet() {
        return this.toMap().entrySet();
    }
}

