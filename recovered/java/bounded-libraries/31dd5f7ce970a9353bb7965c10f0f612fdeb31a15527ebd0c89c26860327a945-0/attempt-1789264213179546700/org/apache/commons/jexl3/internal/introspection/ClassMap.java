/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 */
package org.apache.commons.jexl3.internal.introspection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.commons.jexl3.internal.introspection.MethodKey;
import org.apache.commons.jexl3.internal.introspection.Permissions;
import org.apache.commons.logging.Log;

final class ClassMap {
    private static final Method CACHE_MISS = ClassMap.cacheMiss();
    private final ConcurrentMap<MethodKey, Method> byKey = new ConcurrentHashMap<MethodKey, Method>();
    private final Map<String, Method[]> byName = new HashMap<String, Method[]>();
    private final Map<String, Field> fieldCache;

    public static Method cacheMiss() {
        try {
            return ClassMap.class.getMethod("cacheMiss", new Class[0]);
        }
        catch (Exception xio) {
            return null;
        }
    }

    ClassMap(Class<?> aClass, Permissions permissions, Log log) {
        ClassMap.create(this, permissions, aClass, log);
        Field[] fields = aClass.getFields();
        if (fields.length > 0) {
            HashMap<String, Field> cache = new HashMap<String, Field>();
            for (Field field : fields) {
                if (!permissions.allow(field)) continue;
                cache.put(field.getName(), field);
            }
            this.fieldCache = cache;
        } else {
            this.fieldCache = Collections.emptyMap();
        }
    }

    Field getField(String fname) {
        return this.fieldCache.get(fname);
    }

    String[] getFieldNames() {
        return this.fieldCache.keySet().toArray(new String[this.fieldCache.size()]);
    }

    String[] getMethodNames() {
        return this.byName.keySet().toArray(new String[this.byName.size()]);
    }

    Method[] getMethods(String methodName) {
        Method[] lm = this.byName.get(methodName);
        if (lm != null && lm.length > 0) {
            return (Method[])lm.clone();
        }
        return null;
    }

    Method getMethod(MethodKey methodKey) throws MethodKey.AmbiguousException {
        Method cacheEntry = (Method)this.byKey.get(methodKey);
        if (cacheEntry == CACHE_MISS) {
            return null;
        }
        if (cacheEntry == null) {
            try {
                Method[] methodList = this.byName.get(methodKey.getMethod());
                if (methodList != null) {
                    cacheEntry = methodKey.getMostSpecificMethod(methodList);
                }
                if (cacheEntry == null) {
                    this.byKey.put(methodKey, CACHE_MISS);
                } else {
                    this.byKey.put(methodKey, cacheEntry);
                }
            }
            catch (MethodKey.AmbiguousException ae) {
                this.byKey.put(methodKey, CACHE_MISS);
                throw ae;
            }
        }
        return cacheEntry;
    }

    private static void create(ClassMap cache, Permissions permissions, Class<?> classToReflect, Log log) {
        while (classToReflect != null) {
            Class<?>[] interfaces;
            if (Modifier.isPublic(classToReflect.getModifiers())) {
                ClassMap.populateWithClass(cache, permissions, classToReflect, log);
            }
            for (Class<?> anInterface : interfaces = classToReflect.getInterfaces()) {
                ClassMap.populateWithInterface(cache, permissions, anInterface, log);
            }
            classToReflect = classToReflect.getSuperclass();
        }
        if (!cache.byKey.isEmpty()) {
            ArrayList<Object> lm = new ArrayList<Object>(cache.byKey.size());
            lm.addAll(cache.byKey.values());
            lm.sort(Comparator.comparing(Method::getName));
            int start = 0;
            while (start < lm.size()) {
                String walk;
                int end;
                String name = ((Method)lm.get(start)).getName();
                for (end = start + 1; end < lm.size() && (walk = ((Method)lm.get(end)).getName()).equals(name); ++end) {
                }
                Method[] lmn = lm.subList(start, end).toArray(new Method[end - start]);
                cache.byName.put(name, lmn);
                start = end;
            }
        }
    }

    private static void populateWithInterface(ClassMap cache, Permissions permissions, Class<?> iface, Log log) {
        if (Modifier.isPublic(iface.getModifiers())) {
            Class<?>[] supers;
            ClassMap.populateWithClass(cache, permissions, iface, log);
            for (Class<?> aSuper : supers = iface.getInterfaces()) {
                ClassMap.populateWithInterface(cache, permissions, aSuper, log);
            }
        }
    }

    private static void populateWithClass(ClassMap cache, Permissions permissions, Class<?> clazz, Log log) {
        block3: {
            try {
                Method[] methods;
                for (Method mi : methods = clazz.getDeclaredMethods()) {
                    MethodKey key = new MethodKey(mi);
                    Method pmi = cache.byKey.putIfAbsent(key, permissions.allow(mi) ? mi : CACHE_MISS);
                    if (pmi == null || pmi == CACHE_MISS || !log.isDebugEnabled() || key.equals(new MethodKey(pmi))) continue;
                    log.debug((Object)("Method " + pmi + " is already registered, key: " + key.debugString()));
                }
            }
            catch (SecurityException se) {
                if (!log.isDebugEnabled()) break block3;
                log.debug((Object)("While accessing methods of " + clazz + ": "), (Throwable)se);
            }
        }
    }
}

