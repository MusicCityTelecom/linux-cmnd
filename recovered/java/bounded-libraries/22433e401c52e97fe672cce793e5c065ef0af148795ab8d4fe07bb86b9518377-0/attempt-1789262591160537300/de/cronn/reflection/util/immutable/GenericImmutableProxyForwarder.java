/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.bytebuddy.description.method.MethodDescription$ForLoadedMethod
 *  net.bytebuddy.implementation.bind.annotation.AllArguments
 *  net.bytebuddy.implementation.bind.annotation.FieldValue
 *  net.bytebuddy.implementation.bind.annotation.Origin
 *  net.bytebuddy.implementation.bind.annotation.RuntimeType
 *  net.bytebuddy.matcher.ElementMatchers
 */
package de.cronn.reflection.util.immutable;

import de.cronn.reflection.util.ClassUtils;
import de.cronn.reflection.util.immutable.ImmutableProxy;
import de.cronn.reflection.util.immutable.ImmutableProxyOption;
import de.cronn.reflection.util.immutable.ReadOnly;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.matcher.ElementMatchers;

public final class GenericImmutableProxyForwarder {
    private static final Map<Method, Boolean> shouldProxyReturnValueCache = new ConcurrentHashMap<Method, Boolean>();

    private GenericImmutableProxyForwarder() {
    }

    @RuntimeType
    public static Object forward(@Origin Method method, @FieldValue(value="$delegate") Object delegate, @FieldValue(value="$options") ImmutableProxyOption[] options, @AllArguments Object[] args) throws InvocationTargetException, IllegalAccessException {
        Object value = method.invoke(delegate, args);
        if (ImmutableProxy.isImmutable(value)) {
            return value;
        }
        if (!GenericImmutableProxyForwarder.shouldProxyReturnValue(method)) {
            return value;
        }
        if (value instanceof Collection) {
            return GenericImmutableProxyForwarder.createImmutableCollection(value, method);
        }
        if (value instanceof Map) {
            return GenericImmutableProxyForwarder.createImmutableMap(value, method);
        }
        return ImmutableProxy.create(value, options);
    }

    private static boolean shouldProxyReturnValue(Method method) {
        return shouldProxyReturnValueCache.computeIfAbsent(method, m -> {
            if (GenericImmutableProxyForwarder.isCloneMethod(m)) {
                return false;
            }
            ReadOnly readOnlyAnnotation = ClassUtils.findAnnotation(m, ReadOnly.class);
            return readOnlyAnnotation == null || readOnlyAnnotation.proxyReturnValue();
        });
    }

    private static boolean isCloneMethod(Method method) {
        return ElementMatchers.isClone().matches((Object)new MethodDescription.ForLoadedMethod(method));
    }

    private static Object createImmutableCollection(Object value, Method method) {
        Class<?> returnType = method.getReturnType();
        if (returnType.equals(Set.class)) {
            Set collection = (Set)value;
            return ImmutableProxy.create(collection);
        }
        if (returnType.equals(List.class)) {
            List collection = (List)value;
            return ImmutableProxy.create(collection);
        }
        if (returnType.equals(Collection.class) || returnType.equals(Iterable.class)) {
            Collection collection = (Collection)value;
            return ImmutableProxy.create(collection);
        }
        throw new UnsupportedOperationException("Cannot create immutable collection for " + GenericImmutableProxyForwarder.describeMethod(method) + ". The return type is unknown or too specific: " + returnType + ". Consider to define a more generic type: Set/List/Collection");
    }

    private static Object createImmutableMap(Object value, Method method) {
        Class<?> returnType = method.getReturnType();
        if (returnType.equals(Map.class)) {
            Map map = (Map)value;
            return ImmutableProxy.create(map);
        }
        throw new UnsupportedOperationException("Cannot create immutable map for " + GenericImmutableProxyForwarder.describeMethod(method) + ". The return type is unknown or too specific: " + returnType + ". Consider to define a more generic type: Map");
    }

    private static String describeMethod(Method method) {
        return method.getDeclaringClass().getSimpleName() + "." + method.getName();
    }
}

