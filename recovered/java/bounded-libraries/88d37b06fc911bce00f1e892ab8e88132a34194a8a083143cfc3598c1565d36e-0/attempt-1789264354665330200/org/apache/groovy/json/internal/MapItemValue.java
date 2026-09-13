/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.json.internal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.groovy.json.internal.Exceptions;
import org.apache.groovy.json.internal.Value;

public class MapItemValue
implements Map.Entry<String, Value> {
    final Value name;
    final Value value;
    private String key = null;
    private static final boolean internKeys = Boolean.parseBoolean(System.getProperty("groovy.json.implementation.internKeys", "false"));
    protected static final ConcurrentHashMap<String, String> internedKeysCache = internKeys ? new ConcurrentHashMap() : null;

    public MapItemValue(Value name, Value value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getKey() {
        if (this.key == null) {
            if (internKeys) {
                this.key = this.name.toString();
                String keyPrime = internedKeysCache.get(this.key);
                if (keyPrime == null) {
                    this.key = this.key.intern();
                    internedKeysCache.put(this.key, this.key);
                } else {
                    this.key = keyPrime;
                }
            } else {
                this.key = this.name.toString();
            }
        }
        return this.key;
    }

    @Override
    public Value getValue() {
        return this.value;
    }

    @Override
    public Value setValue(Value value) {
        Exceptions.die("not that kind of Entry");
        return null;
    }
}

