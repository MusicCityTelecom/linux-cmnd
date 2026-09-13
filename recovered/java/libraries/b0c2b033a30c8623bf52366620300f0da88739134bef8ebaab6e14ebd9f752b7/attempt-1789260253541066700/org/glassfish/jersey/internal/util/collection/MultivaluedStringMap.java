/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.util.collection;

import java.lang.reflect.Constructor;
import java.util.List;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

public class MultivaluedStringMap
extends MultivaluedHashMap<String, String> {
    static final long serialVersionUID = -6052320403766368902L;

    public MultivaluedStringMap(MultivaluedMap<? extends String, ? extends String> map) {
        super(map);
    }

    public MultivaluedStringMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public MultivaluedStringMap(int initialCapacity) {
        super(initialCapacity);
    }

    public MultivaluedStringMap() {
    }

    @Override
    protected void addFirstNull(List<String> values) {
        values.add("");
    }

    @Override
    protected void addNull(List<String> values) {
        values.add(0, "");
    }

    public final <A> A getFirst(String key, Class<A> type) {
        String value = (String)this.getFirst(key);
        if (value == null) {
            return null;
        }
        Constructor<A> c = null;
        try {
            c = type.getConstructor(String.class);
        }
        catch (Exception ex) {
            throw new IllegalArgumentException(type.getName() + " has no String constructor", ex);
        }
        A retVal = null;
        try {
            retVal = c.newInstance(value);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return retVal;
    }

    public final <A> A getFirst(String key, A defaultValue) {
        String value = (String)this.getFirst(key);
        if (value == null) {
            return defaultValue;
        }
        Class<?> type = defaultValue.getClass();
        Constructor<?> c = null;
        try {
            c = type.getConstructor(String.class);
        }
        catch (Exception ex) {
            throw new IllegalArgumentException(type.getName() + " has no String constructor", ex);
        }
        Object retVal = defaultValue;
        try {
            retVal = c.newInstance(value);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return retVal;
    }
}

