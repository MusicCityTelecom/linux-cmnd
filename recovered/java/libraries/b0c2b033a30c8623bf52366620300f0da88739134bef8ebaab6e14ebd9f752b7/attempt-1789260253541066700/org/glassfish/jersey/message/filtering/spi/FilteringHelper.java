/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering.spi;

import java.lang.annotation.Annotation;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.security.AccessController;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.xml.bind.JAXBElement;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.message.filtering.spi.ScopeProvider;

public final class FilteringHelper {
    public static final Annotation[] EMPTY_ANNOTATIONS = new Annotation[0];
    private static final ConcurrentMap<Type, Class<?>> ENTITY_CLASSES = new ConcurrentHashMap();

    public static boolean filterableEntityClass(Class<?> clazz) {
        return !ReflectionHelper.isPrimitive(clazz) && !clazz.getPackage().getName().startsWith("java.");
    }

    public static Class<?> getEntityClass(Type genericType) {
        if (!ENTITY_CLASSES.containsKey(genericType)) {
            ENTITY_CLASSES.putIfAbsent(genericType, FilteringHelper._getEntityClass(genericType));
        }
        return (Class)ENTITY_CLASSES.get(genericType);
    }

    private static Class<?> _getEntityClass(Type genericType) {
        if (null == genericType) {
            return Object.class;
        }
        if (genericType instanceof Class && genericType != JAXBElement.class) {
            Class clazz = (Class)genericType;
            if (clazz.isArray()) {
                return FilteringHelper._getEntityClass(clazz.getComponentType());
            }
            return clazz;
        }
        if (genericType instanceof ParameterizedType) {
            Type type;
            ParameterizedType parameterizedType = (ParameterizedType)genericType;
            Type[] arguments = parameterizedType.getActualTypeArguments();
            Type type2 = type = parameterizedType.getRawType() == Map.class ? arguments[1] : arguments[0];
            if (type instanceof ParameterizedType) {
                Type rawType = ((ParameterizedType)type).getRawType();
                if (rawType == JAXBElement.class) {
                    return FilteringHelper._getEntityClass(type);
                }
            } else if (type instanceof WildcardType) {
                Type upperType;
                Type[] upperTypes = ((WildcardType)type).getUpperBounds();
                if (upperTypes.length > 0 && (upperType = upperTypes[0]) instanceof Class) {
                    return (Class)upperType;
                }
            } else if (JAXBElement.class == type || type instanceof TypeVariable) {
                return Object.class;
            }
            return (Class)type;
        }
        if (genericType instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType)genericType;
            return FilteringHelper._getEntityClass(genericArrayType.getGenericComponentType());
        }
        return Object.class;
    }

    public static Map<String, Method> getPropertyMethods(Class<?> clazz, boolean isGetter) {
        HashMap<String, Method> methods = new HashMap<String, Method>();
        for (Method method : AccessController.doPrivileged(ReflectionHelper.getDeclaredMethodsPA(clazz))) {
            if ((!isGetter || !ReflectionHelper.isGetter(method)) && (isGetter || !ReflectionHelper.isSetter(method))) continue;
            methods.put(ReflectionHelper.getPropertyName(method), method);
        }
        Class<?> parent = clazz.getSuperclass();
        if (parent != null && !parent.getPackage().getName().startsWith("java.lang")) {
            methods.putAll(FilteringHelper.getPropertyMethods(parent, isGetter));
        }
        return methods;
    }

    public static Set<String> getDefaultFilteringScope() {
        return Collections.singleton(ScopeProvider.DEFAULT_SCOPE);
    }

    private FilteringHelper() {
    }
}

