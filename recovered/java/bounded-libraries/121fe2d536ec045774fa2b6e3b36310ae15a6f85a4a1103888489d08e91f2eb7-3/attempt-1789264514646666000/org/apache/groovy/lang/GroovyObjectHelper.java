/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.lang;

import groovy.lang.GroovyObject;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class GroovyObjectHelper {
    private static final ClassValue<AtomicReference<MethodHandles.Lookup>> LOOKUP_MAP = new ClassValue<AtomicReference<MethodHandles.Lookup>>(){

        @Override
        protected AtomicReference<MethodHandles.Lookup> computeValue(Class<?> type) {
            return new AtomicReference<MethodHandles.Lookup>();
        }
    };

    public static Optional<MethodHandles.Lookup> lookup(GroovyObject groovyObject) {
        AtomicReference<MethodHandles.Lookup> lookupAtomicRef = LOOKUP_MAP.get(groovyObject.getClass());
        MethodHandles.Lookup lookup = lookupAtomicRef.get();
        if (null != lookup) {
            return Optional.of(lookup);
        }
        Class<?> groovyObjectClass = groovyObject.getClass();
        if (groovyObjectClass.isMemberClass() && Modifier.isStatic(groovyObjectClass.getModifiers())) {
            ArrayList classList = new ArrayList(3);
            for (Class<?> clazz = groovyObjectClass; null != clazz; clazz = clazz.getEnclosingClass()) {
                if (GroovyObjectHelper.isNonStaticInnerClass(clazz)) {
                    return Optional.empty();
                }
                classList.add(clazz);
            }
            MethodHandles.Lookup caller = MethodHandles.lookup();
            for (int i = classList.size() - 1; i >= 0; --i) {
                Class c = (Class)classList.get(i);
                caller = GroovyObjectHelper.doLookup(c, caller);
                if (null != caller) continue;
                return Optional.empty();
            }
            lookup = caller;
        } else {
            lookup = GroovyObjectHelper.doLookup(groovyObject);
        }
        if (null != lookup) {
            lookupAtomicRef.set(lookup);
        }
        return Optional.ofNullable(lookup);
    }

    private static boolean isNonStaticInnerClass(Class<?> clazz) {
        return clazz.isMemberClass() && !Modifier.isStatic(clazz.getModifiers());
    }

    private static MethodHandles.Lookup doLookup(GroovyObject groovyObject) {
        MethodHandles.Lookup lookup;
        try {
            Class<?> groovyObjectClass = groovyObject.getClass();
            lookup = groovyObjectClass.isAnonymousClass() || GroovyObjectHelper.isNonStaticInnerClass(groovyObjectClass) ? MethodHandles.lookup().unreflect(GroovyObjectHelper.findGetLookupMethod(groovyObjectClass)).bindTo(groovyObject).invokeExact() : GroovyObjectHelper.doLookup(groovyObjectClass);
        }
        catch (Throwable e) {
            lookup = null;
        }
        return lookup;
    }

    private static MethodHandles.Lookup doLookup(Class<?> groovyObjectClass) {
        return GroovyObjectHelper.doLookup(groovyObjectClass, MethodHandles.lookup());
    }

    private static MethodHandles.Lookup doLookup(Class<?> groovyObjectClass, MethodHandles.Lookup caller) {
        try {
            return caller.unreflect(GroovyObjectHelper.findGetLookupMethod(groovyObjectClass)).invokeExact();
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    private static Method findGetLookupMethod(Class<?> groovyObjectClass) throws NoSuchMethodException {
        return groovyObjectClass.getDeclaredMethod("$getLookup", new Class[0]);
    }

    private GroovyObjectHelper() {
    }
}

