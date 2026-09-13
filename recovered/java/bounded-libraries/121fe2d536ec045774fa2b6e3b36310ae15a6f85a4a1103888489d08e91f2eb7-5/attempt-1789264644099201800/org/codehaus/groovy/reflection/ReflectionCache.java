/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.reflection;

import org.codehaus.groovy.classgen.asm.util.TypeUtil;
import org.codehaus.groovy.reflection.CachedClass;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.util.TripleKeyHashMap;

public class ReflectionCache {
    @Deprecated
    static TripleKeyHashMap mopNames = new TripleKeyHashMap();
    static final CachedClass STRING_CLASS = ReflectionCache.getCachedClass(String.class);
    public static final CachedClass OBJECT_CLASS = ReflectionCache.getCachedClass(Object.class);
    public static final CachedClass OBJECT_ARRAY_CLASS = ReflectionCache.getCachedClass(Object[].class);

    public static Class autoboxType(Class type) {
        return TypeUtil.autoboxType(type);
    }

    @Deprecated
    public static String getMOPMethodName(CachedClass declaringClass, String name, boolean useThis) {
        TripleKeyHashMap.Entry mopNameEntry = mopNames.getOrPut(declaringClass, name, useThis);
        if (mopNameEntry.value == null) {
            mopNameEntry.value = (useThis ? "this$" : "super$") + declaringClass.getSuperClassDistance() + "$" + name;
        }
        return (String)mopNameEntry.value;
    }

    public static boolean isArray(Class klazz) {
        return klazz.isArray();
    }

    static void setAssignableFrom(Class klazz, Class aClass) {
    }

    public static boolean isAssignableFrom(Class klazz, Class aClass) {
        if (klazz == aClass) {
            return true;
        }
        return klazz.isAssignableFrom(aClass);
    }

    static boolean arrayContentsEq(Object[] a1, Object[] a2) {
        if (a1 == null) {
            return a2 == null || a2.length == 0;
        }
        if (a2 == null) {
            return a1.length == 0;
        }
        if (a1.length != a2.length) {
            return false;
        }
        for (int i = 0; i < a1.length; ++i) {
            if (a1[i] == a2[i]) continue;
            return false;
        }
        return true;
    }

    public static CachedClass getCachedClass(Class klazz) {
        if (klazz == null) {
            return null;
        }
        return ClassInfo.getClassInfo(klazz).getCachedClass();
    }
}

