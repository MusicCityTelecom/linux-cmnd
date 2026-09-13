/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.dfa;

import groovyjarjarantlr4.v4.runtime.dfa.AbstractEdgeMap;
import groovyjarjarantlr4.v4.runtime.dfa.SingletonEdgeMap;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public final class EmptyEdgeMap<T>
extends AbstractEdgeMap<T> {
    public EmptyEdgeMap(int minIndex, int maxIndex) {
        super(minIndex, maxIndex);
    }

    @Override
    public AbstractEdgeMap<T> put(int key, T value) {
        if (value == null || key < this.minIndex || key > this.maxIndex) {
            return this;
        }
        return new SingletonEdgeMap<T>(this.minIndex, this.maxIndex, key, value);
    }

    @Override
    public AbstractEdgeMap<T> clear() {
        return this;
    }

    @Override
    public AbstractEdgeMap<T> remove(int key) {
        return this;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean containsKey(int key) {
        return false;
    }

    @Override
    public T get(int key) {
        return null;
    }

    @Override
    public Map<Integer, T> toMap() {
        return Collections.emptyMap();
    }

    @Override
    public Set<Map.Entry<Integer, T>> entrySet() {
        return Collections.emptyMap().entrySet();
    }
}

