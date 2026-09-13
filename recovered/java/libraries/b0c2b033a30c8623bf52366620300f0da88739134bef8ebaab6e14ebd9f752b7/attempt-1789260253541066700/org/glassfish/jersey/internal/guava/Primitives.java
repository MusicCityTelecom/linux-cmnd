/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.glassfish.jersey.internal.guava.Preconditions;

public final class Primitives {
    private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER_TYPE;

    private Primitives() {
    }

    private static void add(Map<Class<?>, Class<?>> forward, Class<?> key, Class<?> value) {
        forward.put(key, value);
    }

    public static <T> Class<T> wrap(Class<T> type) {
        Preconditions.checkNotNull(type);
        Class<?> wrapped = PRIMITIVE_TO_WRAPPER_TYPE.get(type);
        return wrapped == null ? type : wrapped;
    }

    static {
        HashMap primToWrap = new HashMap(16);
        Primitives.add(primToWrap, Boolean.TYPE, Boolean.class);
        Primitives.add(primToWrap, Byte.TYPE, Byte.class);
        Primitives.add(primToWrap, Character.TYPE, Character.class);
        Primitives.add(primToWrap, Double.TYPE, Double.class);
        Primitives.add(primToWrap, Float.TYPE, Float.class);
        Primitives.add(primToWrap, Integer.TYPE, Integer.class);
        Primitives.add(primToWrap, Long.TYPE, Long.class);
        Primitives.add(primToWrap, Short.TYPE, Short.class);
        Primitives.add(primToWrap, Void.TYPE, Void.class);
        PRIMITIVE_TO_WRAPPER_TYPE = Collections.unmodifiableMap(primToWrap);
    }
}

