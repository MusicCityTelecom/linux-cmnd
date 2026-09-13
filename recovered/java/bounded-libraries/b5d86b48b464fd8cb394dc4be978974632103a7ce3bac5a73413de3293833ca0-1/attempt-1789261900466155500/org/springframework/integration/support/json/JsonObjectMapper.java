/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.ResolvableType
 */
package org.springframework.integration.support.json;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import org.springframework.core.ResolvableType;
import org.springframework.integration.mapping.support.JsonHeaders;

public interface JsonObjectMapper<N, P> {
    default public String toJson(Object value) throws IOException {
        return null;
    }

    default public void toJson(Object value, Writer writer) throws IOException {
    }

    default public N toJsonNode(Object value) throws IOException {
        return null;
    }

    default public <T> T fromJson(Object json, Class<T> valueType) throws IOException {
        return null;
    }

    default public <T> T fromJson(Object json, ResolvableType valueType) throws IOException {
        return null;
    }

    default public <T> T fromJson(Object json, Map<String, Object> javaTypes) throws IOException {
        return null;
    }

    default public <T> T fromJson(P parser, Type valueType) throws IOException {
        return null;
    }

    default public void populateJavaTypes(Map<String, Object> map, Object object) {
        Object firstElement;
        Class<?> targetClass = object.getClass();
        Class<?> contentClass = null;
        Class<?> keyClass = null;
        map.put("json__TypeId__", targetClass);
        if (object instanceof Collection && !((Collection)object).isEmpty() && (firstElement = ((Collection)object).iterator().next()) != null) {
            contentClass = firstElement.getClass();
            map.put("json__ContentTypeId__", contentClass);
        }
        if (object instanceof Map && !((Map)object).isEmpty()) {
            Object firstKey;
            Object firstValue = ((Map)object).values().iterator().next();
            if (firstValue != null) {
                contentClass = firstValue.getClass();
                map.put("json__ContentTypeId__", contentClass);
            }
            if ((firstKey = ((Map)object).keySet().iterator().next()) != null) {
                keyClass = firstKey.getClass();
                map.put("json__KeyTypeId__", keyClass);
            }
        }
        map.put("json_resolvableType", JsonHeaders.buildResolvableType(targetClass, contentClass, keyClass));
    }
}

