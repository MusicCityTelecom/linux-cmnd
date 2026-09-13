/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.dfa;

import groovyjarjarantlr4.v4.runtime.dfa.AbstractEdgeMap;
import groovyjarjarantlr4.v4.runtime.dfa.ArrayEdgeMap;
import groovyjarjarantlr4.v4.runtime.dfa.EdgeMap;
import groovyjarjarantlr4.v4.runtime.dfa.EmptyEdgeMap;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Deprecated
public final class SparseEdgeMap<T>
extends AbstractEdgeMap<T> {
    private static final int DEFAULT_MAX_SIZE = 5;
    private final int[] keys;
    private final List<T> values;

    public SparseEdgeMap(int minIndex, int maxIndex) {
        this(minIndex, maxIndex, 5);
    }

    public SparseEdgeMap(int minIndex, int maxIndex, int maxSparseSize) {
        super(minIndex, maxIndex);
        this.keys = new int[maxSparseSize];
        this.values = new ArrayList<T>(maxSparseSize);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private SparseEdgeMap(@NotNull SparseEdgeMap<T> map, int maxSparseSize) {
        super(map.minIndex, map.maxIndex);
        SparseEdgeMap<T> sparseEdgeMap = map;
        synchronized (sparseEdgeMap) {
            if (maxSparseSize < map.values.size()) {
                throw new IllegalArgumentException();
            }
            this.keys = Arrays.copyOf(map.keys, maxSparseSize);
            this.values = new ArrayList<T>(maxSparseSize);
            this.values.addAll(map.values);
        }
    }

    public final int[] getKeys() {
        return this.keys;
    }

    public final List<T> getValues() {
        return this.values;
    }

    public final int getMaxSparseSize() {
        return this.keys.length;
    }

    @Override
    public int size() {
        return this.values.size();
    }

    @Override
    public boolean isEmpty() {
        return this.values.isEmpty();
    }

    @Override
    public boolean containsKey(int key) {
        return this.get(key) != null;
    }

    @Override
    public T get(int key) {
        int index = Arrays.binarySearch(this.keys, 0, this.size(), key);
        if (index < 0) {
            return null;
        }
        return this.values.get(index);
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
        SparseEdgeMap sparseEdgeMap = this;
        synchronized (sparseEdgeMap) {
            int space;
            int index = Arrays.binarySearch(this.keys, 0, this.size(), key);
            if (index >= 0) {
                this.values.set(index, value);
                return this;
            }
            assert (index < 0 && value != null);
            int insertIndex = -index - 1;
            if (this.size() < this.getMaxSparseSize() && insertIndex == this.size()) {
                this.keys[insertIndex] = key;
                this.values.add(value);
                return this;
            }
            int desiredSize = this.size() >= this.getMaxSparseSize() ? this.getMaxSparseSize() * 2 : this.getMaxSparseSize();
            if (desiredSize >= (space = this.maxIndex - this.minIndex + 1) / 2) {
                AbstractEdgeMap arrayMap = new ArrayEdgeMap(this.minIndex, this.maxIndex);
                arrayMap = ((ArrayEdgeMap)arrayMap).putAll((EdgeMap)this);
                ((ArrayEdgeMap)arrayMap).put(key, (Object)value);
                return arrayMap;
            }
            SparseEdgeMap<T> resized = new SparseEdgeMap<T>(this, desiredSize);
            System.arraycopy(resized.keys, insertIndex, resized.keys, insertIndex + 1, this.size() - insertIndex);
            resized.keys[insertIndex] = key;
            resized.values.add(insertIndex, value);
            return resized;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public SparseEdgeMap<T> remove(int key) {
        SparseEdgeMap sparseEdgeMap = this;
        synchronized (sparseEdgeMap) {
            int index = Arrays.binarySearch(this.keys, 0, this.size(), key);
            if (index < 0) {
                return this;
            }
            SparseEdgeMap<T> result = new SparseEdgeMap<T>(this, this.getMaxSparseSize());
            System.arraycopy(result.keys, index + 1, result.keys, index, this.size() - index - 1);
            result.values.remove(index);
            return result;
        }
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
        SparseEdgeMap sparseEdgeMap = this;
        synchronized (sparseEdgeMap) {
            LinkedHashMap<Integer, T> result = new LinkedHashMap<Integer, T>();
            for (int i = 0; i < this.size(); ++i) {
                result.put(this.keys[i], this.values.get(i));
            }
            return result;
        }
    }

    @Override
    public Set<Map.Entry<Integer, T>> entrySet() {
        return this.toMap().entrySet();
    }
}

