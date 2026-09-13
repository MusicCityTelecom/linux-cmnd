/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.inject;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public final class PrimitiveMapper {
    public static final Map<Class, Class> primitiveToClassMap = PrimitiveMapper.getPrimitiveToClassMap();
    public static final Map<Class, Object> primitiveToDefaultValueMap = PrimitiveMapper.getPrimitiveToDefaultValueMap();

    private static Map<Class, Class> getPrimitiveToClassMap() {
        WeakHashMap<Class<Comparable<Boolean>>, Class> m = new WeakHashMap<Class<Comparable<Boolean>>, Class>();
        m.put(Boolean.TYPE, Boolean.class);
        m.put(Byte.TYPE, Byte.class);
        m.put(Character.TYPE, Character.class);
        m.put(Short.TYPE, Short.class);
        m.put(Integer.TYPE, Integer.class);
        m.put(Long.TYPE, Long.class);
        m.put(Float.TYPE, Float.class);
        m.put(Double.TYPE, Double.class);
        return Collections.unmodifiableMap(m);
    }

    private static Map<Class, Object> getPrimitiveToDefaultValueMap() {
        WeakHashMap<Class, Comparable<Boolean>> m = new WeakHashMap<Class, Comparable<Boolean>>();
        m.put(Boolean.class, Boolean.valueOf(false));
        m.put(Byte.class, Byte.valueOf((byte)0));
        m.put(Character.class, Character.valueOf('\u0000'));
        m.put(Short.class, Short.valueOf((short)0));
        m.put(Integer.class, Integer.valueOf(0));
        m.put(Long.class, Long.valueOf(0L));
        m.put(Float.class, Float.valueOf(0.0f));
        m.put(Double.class, Double.valueOf(0.0));
        return Collections.unmodifiableMap(m);
    }

    private PrimitiveMapper() {
    }
}

