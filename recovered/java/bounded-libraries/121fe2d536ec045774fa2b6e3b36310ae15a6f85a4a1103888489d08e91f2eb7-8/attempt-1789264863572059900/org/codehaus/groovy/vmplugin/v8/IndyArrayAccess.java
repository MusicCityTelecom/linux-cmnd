/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.vmplugin.v8;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.TypeDescriptor;
import java.lang.reflect.Array;
import java.util.HashMap;
import org.codehaus.groovy.GroovyBugError;

public class IndyArrayAccess {
    private static final MethodHandle notNegative;
    private static final MethodHandle normalizeIndex;
    private static final HashMap<Class<?>, MethodHandle> getterMap;
    private static final HashMap<Class<?>, MethodHandle> setterMap;

    private static MethodHandle buildGetter(Class<?> arrayClass) {
        MethodHandle get = MethodHandles.arrayElementGetter(arrayClass);
        MethodHandle fallback = MethodHandles.explicitCastArguments(get, get.type().changeParameterType(0, Object.class));
        fallback = MethodHandles.dropArguments(fallback, 2, Integer.TYPE);
        MethodType reorderType = fallback.type().insertParameterTypes(0, Integer.TYPE).dropParameterTypes(2, 3);
        fallback = MethodHandles.permuteArguments(fallback, reorderType, 1, 0, 0);
        fallback = MethodHandles.foldArguments(fallback, normalizeIndex);
        fallback = MethodHandles.explicitCastArguments(fallback, get.type());
        MethodHandle guard = MethodHandles.dropArguments(notNegative, 0, arrayClass);
        MethodHandle handle = MethodHandles.guardWithTest(guard, get, fallback);
        return handle;
    }

    private static MethodHandle buildSetter(Class<?> arrayClass) {
        MethodHandle set = MethodHandles.arrayElementSetter(arrayClass);
        MethodHandle fallback = MethodHandles.explicitCastArguments(set, set.type().changeParameterType(0, Object.class));
        fallback = MethodHandles.dropArguments(fallback, 3, Integer.TYPE);
        MethodType reorderType = fallback.type().insertParameterTypes(0, Integer.TYPE).dropParameterTypes(4, 5);
        fallback = MethodHandles.permuteArguments(fallback, reorderType, 1, 0, 3, 0);
        fallback = MethodHandles.foldArguments(fallback, normalizeIndex);
        fallback = MethodHandles.explicitCastArguments(fallback, set.type());
        MethodHandle guard = MethodHandles.dropArguments(notNegative, 0, arrayClass);
        MethodHandle handle = MethodHandles.guardWithTest(guard, set, fallback);
        return handle;
    }

    private static int getLength(Object array) {
        if (null == array || !array.getClass().isArray()) {
            return 0;
        }
        return Array.getLength(array);
    }

    private static int normalizeIndex(Object array, int i) {
        int temp = i;
        int size = IndyArrayAccess.getLength(array);
        if ((i += size) < 0) {
            throw new ArrayIndexOutOfBoundsException("Negative array index [" + temp + "] too large for array size " + size);
        }
        return i;
    }

    public static boolean notNegative(int index) {
        return index >= 0;
    }

    public static MethodHandle arrayGet(MethodType type) {
        TypeDescriptor.OfField key = type.parameterType(0);
        MethodHandle res = getterMap.get(key);
        if (res != null) {
            return res;
        }
        res = IndyArrayAccess.buildGetter(key);
        res = MethodHandles.explicitCastArguments(res, type);
        return res;
    }

    public static MethodHandle arraySet(MethodType type) {
        TypeDescriptor.OfField key = type.parameterType(0);
        MethodHandle res = setterMap.get(key);
        if (res != null) {
            return res;
        }
        res = IndyArrayAccess.buildSetter(key);
        res = MethodHandles.explicitCastArguments(res, type);
        return res;
    }

    static {
        MethodHandle handle;
        Class[] classes;
        try {
            notNegative = MethodHandles.lookup().findStatic(IndyArrayAccess.class, "notNegative", MethodType.methodType(Boolean.TYPE, Integer.TYPE));
            normalizeIndex = MethodHandles.lookup().findStatic(IndyArrayAccess.class, "normalizeIndex", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE));
        }
        catch (ReflectiveOperationException e) {
            throw new GroovyBugError(e);
        }
        getterMap = new HashMap();
        for (Class arrayClass : classes = new Class[]{int[].class, byte[].class, short[].class, long[].class, double[].class, float[].class, boolean[].class, char[].class, Object[].class}) {
            handle = IndyArrayAccess.buildGetter(arrayClass);
            getterMap.put(arrayClass, handle);
        }
        setterMap = new HashMap();
        for (Class arrayClass : classes) {
            handle = IndyArrayAccess.buildSetter(arrayClass);
            setterMap.put(arrayClass, handle);
        }
    }
}

