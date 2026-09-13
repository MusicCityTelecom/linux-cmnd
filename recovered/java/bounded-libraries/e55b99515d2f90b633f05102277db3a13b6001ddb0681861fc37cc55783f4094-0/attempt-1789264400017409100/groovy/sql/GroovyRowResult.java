/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.GroovyObjectSupport
 *  groovy.lang.MissingPropertyException
 */
package groovy.sql;

import groovy.lang.GroovyObjectSupport;
import groovy.lang.MissingPropertyException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class GroovyRowResult
extends GroovyObjectSupport
implements Map<String, Object> {
    private final Map<String, Object> result;

    public GroovyRowResult(Map<String, Object> result) {
        this.result = result;
    }

    public Object getProperty(String property) {
        try {
            Object key = this.lookupKeyIgnoringCase(property);
            if (key != null) {
                return this.result.get(key);
            }
            throw new MissingPropertyException(property, GroovyRowResult.class);
        }
        catch (Exception e) {
            throw new MissingPropertyException(property, GroovyRowResult.class, (Throwable)e);
        }
    }

    private Object lookupKeyIgnoringCase(Object key) {
        if (this.result.containsKey(key)) {
            return key;
        }
        if (!(key instanceof CharSequence)) {
            return null;
        }
        String keyStr = key.toString();
        for (String next : this.result.keySet()) {
            if (!(next instanceof String) || !keyStr.equalsIgnoreCase(next)) continue;
            return next;
        }
        return null;
    }

    public Object getAt(int index) {
        try {
            if (index < 0) {
                index += this.result.size();
            }
            Iterator<Object> it = this.result.values().iterator();
            int i = 0;
            Object obj = null;
            while (obj == null && it.hasNext()) {
                if (i == index) {
                    obj = it.next();
                } else {
                    it.next();
                }
                ++i;
            }
            return obj;
        }
        catch (Exception e) {
            throw new MissingPropertyException(Integer.toString(index), GroovyRowResult.class, (Throwable)e);
        }
    }

    public String toString() {
        return this.result.toString();
    }

    @Override
    public void clear() {
        this.result.clear();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.lookupKeyIgnoringCase(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return this.result.containsValue(value);
    }

    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        return this.result.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return this.result.equals(o);
    }

    @Override
    public Object get(Object property) {
        if (property instanceof String) {
            return this.getProperty((String)property);
        }
        return null;
    }

    @Override
    public int hashCode() {
        return this.result.hashCode();
    }

    @Override
    public boolean isEmpty() {
        return this.result.isEmpty();
    }

    @Override
    public Set<String> keySet() {
        return this.result.keySet();
    }

    @Override
    public Object put(String key, Object value) {
        Object orig = this.remove(key);
        this.result.put(key, value);
        return orig;
    }

    @Override
    public void putAll(Map<? extends String, ?> t) {
        for (Map.Entry<String, ?> next : t.entrySet()) {
            this.put(next.getKey(), next.getValue());
        }
    }

    @Override
    public Object remove(Object rawKey) {
        return this.result.remove(this.lookupKeyIgnoringCase(rawKey));
    }

    @Override
    public int size() {
        return this.result.size();
    }

    @Override
    public Collection<Object> values() {
        return this.result.values();
    }
}

