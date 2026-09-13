/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public final class ReflectUtil {
    private static final Map<String, Method> METHOD_CACHE = new HashMap<String, Method>();

    private ReflectUtil() {
    }

    public static Method getMethod(Class<?> target, String name, Class<?> ... parameters) {
        String key = target.getName() + '.' + name;
        Method method = METHOD_CACHE.get(key);
        if (method != null) {
            return method;
        }
        try {
            method = target.getMethod(name, parameters);
            METHOD_CACHE.put(key, method);
            return method;
        }
        catch (NoSuchMethodException e) {
            return null;
        }
    }

    public static Object invoke(Object target, Method method, Object ... parameters) {
        try {
            return method.invoke(target, parameters);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed invoking " + method, e);
        }
    }
}

