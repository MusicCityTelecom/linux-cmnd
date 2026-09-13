/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.jexl3.JexlContext;

public class MapContext
implements JexlContext {
    private final Map<String, Object> map;

    public MapContext() {
        this(null);
    }

    public MapContext(Map<String, Object> vars) {
        this.map = vars == null ? new HashMap() : vars;
    }

    @Override
    public boolean has(String name) {
        return this.map.containsKey(name);
    }

    @Override
    public Object get(String name) {
        return this.map.get(name);
    }

    @Override
    public void set(String name, Object value) {
        this.map.put(name, value);
    }

    public void clear() {
        this.map.clear();
    }
}

