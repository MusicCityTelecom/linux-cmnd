/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.reflection;

import java.util.HashMap;

public class Constants {
    public static final String SYSTEM_LOADER_NAME = "SystemLoader";
    public static final HashMap<Class<?>, Class<?>> PRIMITIVE_MAP = new HashMap();

    static {
        PRIMITIVE_MAP.put(Character.TYPE, Character.class);
        PRIMITIVE_MAP.put(Byte.TYPE, Byte.class);
        PRIMITIVE_MAP.put(Short.TYPE, Short.class);
        PRIMITIVE_MAP.put(Integer.TYPE, Integer.class);
        PRIMITIVE_MAP.put(Long.TYPE, Long.class);
        PRIMITIVE_MAP.put(Float.TYPE, Float.class);
        PRIMITIVE_MAP.put(Double.TYPE, Double.class);
    }
}

