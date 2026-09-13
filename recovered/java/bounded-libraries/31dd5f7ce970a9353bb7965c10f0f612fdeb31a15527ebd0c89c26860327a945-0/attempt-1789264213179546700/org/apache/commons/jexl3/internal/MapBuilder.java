/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.jexl3.JexlArithmetic;

public class MapBuilder
implements JexlArithmetic.MapBuilder {
    protected final Map<Object, Object> map;

    public MapBuilder(int size) {
        this.map = new HashMap<Object, Object>(size);
    }

    @Override
    public void put(Object key, Object value) {
        this.map.put(key, value);
    }

    @Override
    public Map<Object, Object> create() {
        return this.map;
    }
}

