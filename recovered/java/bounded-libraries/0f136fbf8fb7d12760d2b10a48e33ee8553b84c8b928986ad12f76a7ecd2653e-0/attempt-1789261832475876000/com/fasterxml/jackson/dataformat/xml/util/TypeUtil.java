/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JavaType
 */
package com.fasterxml.jackson.dataformat.xml.util;

import com.fasterxml.jackson.databind.JavaType;
import java.util.Collection;

public class TypeUtil {
    public static boolean isIndexedType(JavaType type) {
        if (type.isContainerType()) {
            Class cls = type.getRawClass();
            if (cls == byte[].class || cls == char[].class) {
                return false;
            }
            return !type.isMapLikeType();
        }
        return false;
    }

    public static boolean isIndexedType(Class<?> cls) {
        return cls.isArray() && cls != byte[].class && cls != char[].class || Collection.class.isAssignableFrom(cls);
    }
}

