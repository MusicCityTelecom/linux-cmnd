/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.jaxrs.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUMap<K, V>
extends LinkedHashMap<K, V> {
    protected final int _maxEntries;

    public LRUMap(int initialEntries, int maxEntries) {
        super(initialEntries, 0.8f, true);
        this._maxEntries = maxEntries;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return this.size() > this._maxEntries;
    }
}

