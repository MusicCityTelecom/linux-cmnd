/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ReflectionUtils
 */
package org.springframework.integration.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;

public abstract class ClassUtils {
    private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_TYPE_MAP = new HashMap(8);
    public static final Method FUNCTION_APPLY_METHOD = ReflectionUtils.findMethod(Function.class, (String)"apply", (Class[])null);
    public static final Method SUPPLIER_GET_METHOD = ReflectionUtils.findMethod(Supplier.class, (String)"get", (Class[])null);
    public static final Method SELECTOR_ACCEPT_METHOD;
    public static final Method TRANSFORMER_TRANSFORM_METHOD;
    public static final Method HANDLER_HANDLE_METHOD;
    public static final Class<?> KOTLIN_FUNCTION_0_CLASS;
    public static final Method KOTLIN_FUNCTION_0_INVOKE_METHOD;
    public static final Class<?> KOTLIN_FUNCTION_1_CLASS;
    public static final Class<?> KOTLIN_UNIT_CLASS;

    public static Class<?> findClosestMatch(Class<?> type, Set<Class<?>> candidates, boolean failOnTie) {
        int minTypeDiffWeight = Integer.MAX_VALUE;
        Class<?> closestMatch = null;
        for (Class<?> candidate : candidates) {
            int typeDiffWeight = ClassUtils.getTypeDifferenceWeight(candidate, type);
            if (typeDiffWeight < minTypeDiffWeight) {
                minTypeDiffWeight = typeDiffWeight;
                closestMatch = candidate;
                continue;
            }
            if (!failOnTie || typeDiffWeight >= Integer.MAX_VALUE || typeDiffWeight != minTypeDiffWeight) continue;
            throw new IllegalStateException("Unresolvable ambiguity while attempting to find closest match for [" + type.getName() + "]. Candidate types [" + closestMatch.getName() + "] and [" + candidate.getName() + "] have equal weight.");
        }
        return closestMatch;
    }

    private static int getTypeDifferenceWeight(Class<?> candidate, Class<?> type) {
        int result = 0;
        if (!org.springframework.util.ClassUtils.isAssignable(candidate, type)) {
            return Integer.MAX_VALUE;
        }
        Class<?> superClass = type.getSuperclass();
        while (superClass != null) {
            if (type.equals(superClass)) {
                result += 2;
                superClass = null;
                continue;
            }
            if (org.springframework.util.ClassUtils.isAssignable(candidate, superClass)) {
                result += 2;
                superClass = superClass.getSuperclass();
                continue;
            }
            superClass = null;
        }
        if (candidate.isInterface()) {
            ++result;
        }
        return result;
    }

    @Nullable
    public static Class<?> resolvePrimitiveType(Class<?> clazz) {
        return PRIMITIVE_WRAPPER_TYPE_MAP.get(clazz);
    }

    public static boolean isLambda(Class<?> aClass) {
        return aClass.isSynthetic() && !aClass.isAnonymousClass() && !aClass.isLocalClass() || aClass.getName().contains("$inlined$");
    }

    @Deprecated
    public static boolean isKotlinFaction0(Class<?> aClass) {
        return KOTLIN_FUNCTION_0_CLASS != null && KOTLIN_FUNCTION_0_CLASS.isAssignableFrom(aClass);
    }

    public static boolean isKotlinFunction0(Class<?> aClass) {
        return KOTLIN_FUNCTION_0_CLASS != null && KOTLIN_FUNCTION_0_CLASS.isAssignableFrom(aClass);
    }

    @Deprecated
    public static boolean isKotlinFaction1(Class<?> aClass) {
        return KOTLIN_FUNCTION_1_CLASS != null && KOTLIN_FUNCTION_1_CLASS.isAssignableFrom(aClass);
    }

    public static boolean isKotlinFunction1(Class<?> aClass) {
        return KOTLIN_FUNCTION_1_CLASS != null && KOTLIN_FUNCTION_1_CLASS.isAssignableFrom(aClass);
    }

    public static boolean isKotlinUnit(Class<?> aClass) {
        return KOTLIN_UNIT_CLASS != null && KOTLIN_UNIT_CLASS.isAssignableFrom(aClass);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static {
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Boolean.class, Boolean.TYPE);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Byte.class, Byte.TYPE);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Character.class, Character.TYPE);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Double.class, Double.TYPE);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Float.class, Float.TYPE);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Integer.class, Integer.TYPE);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Long.class, Long.TYPE);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Short.class, Short.TYPE);
        Class genericSelectorClass = null;
        try {
            genericSelectorClass = org.springframework.util.ClassUtils.forName((String)"org.springframework.integration.core.GenericSelector", (ClassLoader)org.springframework.util.ClassUtils.getDefaultClassLoader());
        }
        catch (ClassNotFoundException e) {
            ReflectionUtils.rethrowRuntimeException((Throwable)e);
        }
        SELECTOR_ACCEPT_METHOD = ReflectionUtils.findMethod((Class)genericSelectorClass, (String)"accept", (Class[])null);
        Class genericTransformerClass = null;
        try {
            genericTransformerClass = org.springframework.util.ClassUtils.forName((String)"org.springframework.integration.transformer.GenericTransformer", (ClassLoader)org.springframework.util.ClassUtils.getDefaultClassLoader());
        }
        catch (ClassNotFoundException e) {
            ReflectionUtils.rethrowRuntimeException((Throwable)e);
        }
        TRANSFORMER_TRANSFORM_METHOD = ReflectionUtils.findMethod((Class)genericTransformerClass, (String)"transform", (Class[])null);
        Class genericHandlerClass = null;
        try {
            genericHandlerClass = org.springframework.util.ClassUtils.forName((String)"org.springframework.integration.handler.GenericHandler", (ClassLoader)org.springframework.util.ClassUtils.getDefaultClassLoader());
        }
        catch (ClassNotFoundException e) {
            ReflectionUtils.rethrowRuntimeException((Throwable)e);
        }
        HANDLER_HANDLE_METHOD = ReflectionUtils.findMethod((Class)genericHandlerClass, (String)"handle", (Class[])null);
        Class kotlinClass = null;
        Method kotlinMethod = null;
        try {
            kotlinClass = org.springframework.util.ClassUtils.forName((String)"kotlin.jvm.functions.Function0", (ClassLoader)org.springframework.util.ClassUtils.getDefaultClassLoader());
            kotlinMethod = ReflectionUtils.findMethod((Class)kotlinClass, (String)"invoke", (Class[])null);
        }
        catch (ClassNotFoundException classNotFoundException) {
        }
        finally {
            KOTLIN_FUNCTION_0_CLASS = kotlinClass;
            KOTLIN_FUNCTION_0_INVOKE_METHOD = kotlinMethod;
        }
        kotlinClass = null;
        kotlinMethod = null;
        try {
            kotlinClass = org.springframework.util.ClassUtils.forName((String)"kotlin.jvm.functions.Function1", (ClassLoader)org.springframework.util.ClassUtils.getDefaultClassLoader());
        }
        catch (ClassNotFoundException classNotFoundException) {
        }
        finally {
            KOTLIN_FUNCTION_1_CLASS = kotlinClass;
        }
        kotlinClass = null;
        try {
            kotlinClass = org.springframework.util.ClassUtils.forName((String)"kotlin.Unit", (ClassLoader)org.springframework.util.ClassUtils.getDefaultClassLoader());
        }
        catch (ClassNotFoundException classNotFoundException) {
        }
        finally {
            KOTLIN_UNIT_CLASS = kotlinClass;
        }
    }
}

