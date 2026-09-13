/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.ResolvableType
 *  org.springframework.core.convert.TypeDescriptor
 *  org.springframework.lang.Nullable
 *  org.springframework.util.ClassUtils
 */
package org.springframework.integration.mapping.support;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.springframework.core.ResolvableType;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

public final class JsonHeaders {
    public static final String PREFIX = "json";
    public static final String TYPE_ID = "json__TypeId__";
    public static final String CONTENT_TYPE_ID = "json__ContentTypeId__";
    public static final String KEY_TYPE_ID = "json__KeyTypeId__";
    public static final String RESOLVABLE_TYPE = "json_resolvableType";
    public static final Collection<String> HEADERS = Collections.unmodifiableList(Arrays.asList("json__TypeId__", "json__ContentTypeId__", "json__KeyTypeId__", "json_resolvableType"));

    private JsonHeaders() {
    }

    public static ResolvableType buildResolvableType(ClassLoader classLoader, Object targetClassValue, @Nullable Object contentClassValue, @Nullable Object keyClassValue) {
        Class<?> targetClass = JsonHeaders.getClassForValue(classLoader, targetClassValue);
        Class<?> keyClass = JsonHeaders.getClassForValue(classLoader, keyClassValue);
        Class<?> contentClass = JsonHeaders.getClassForValue(classLoader, contentClassValue);
        return JsonHeaders.buildResolvableType(targetClass, contentClass, keyClass);
    }

    @Nullable
    private static Class<?> getClassForValue(ClassLoader classLoader, @Nullable Object classValue) {
        if (classValue instanceof Class) {
            return (Class)classValue;
        }
        if (classValue != null) {
            try {
                return ClassUtils.forName((String)classValue.toString(), (ClassLoader)classLoader);
            }
            catch (ClassNotFoundException | LinkageError e) {
                throw new IllegalStateException(e);
            }
        }
        return null;
    }

    public static ResolvableType buildResolvableType(Class<?> targetClass, @Nullable Class<?> contentClass, @Nullable Class<?> keyClass) {
        if (keyClass != null) {
            return TypeDescriptor.map(targetClass, (TypeDescriptor)TypeDescriptor.valueOf(keyClass), (TypeDescriptor)TypeDescriptor.valueOf(contentClass)).getResolvableType();
        }
        if (contentClass != null) {
            return TypeDescriptor.collection(targetClass, (TypeDescriptor)TypeDescriptor.valueOf(contentClass)).getResolvableType();
        }
        return ResolvableType.forClass(targetClass);
    }
}

