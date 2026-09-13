/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.json.internal;

import java.util.Map;
import org.apache.groovy.json.internal.MapItemValue;
import org.apache.groovy.json.internal.Value;

public interface ValueMap<K, V>
extends Map<K, V> {
    public void add(MapItemValue var1);

    public int len();

    public boolean hydrated();

    public Map.Entry<String, Value>[] items();
}

