/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.runtime;

import org.codehaus.groovy.ast.ClassNode;

public class ArrayTypeUtils {
    public static int dimension(Class clazz) {
        ArrayTypeUtils.checkArrayType(clazz);
        int result = 0;
        while (clazz.isArray()) {
            ++result;
            clazz = clazz.getComponentType();
        }
        return result;
    }

    public static int dimension(ClassNode clazz) {
        ArrayTypeUtils.checkArrayType(clazz);
        int result = 0;
        while (clazz.isArray()) {
            ++result;
            clazz = clazz.getComponentType();
        }
        return result;
    }

    public static Class elementType(Class clazz) {
        ArrayTypeUtils.checkArrayType(clazz);
        while (clazz.isArray()) {
            clazz = clazz.getComponentType();
        }
        return clazz;
    }

    public static ClassNode elementType(ClassNode clazz) {
        ArrayTypeUtils.checkArrayType(clazz);
        while (clazz.isArray()) {
            clazz = clazz.getComponentType();
        }
        return clazz;
    }

    public static Class elementType(Class clazz, int dim) {
        ArrayTypeUtils.checkArrayType(clazz);
        if (dim < 0) {
            throw new IllegalArgumentException("The target dimension should not be less than zero: " + dim);
        }
        while (clazz.isArray() && ArrayTypeUtils.dimension(clazz) > dim) {
            clazz = clazz.getComponentType();
        }
        return clazz;
    }

    private static void checkArrayType(Class clazz) {
        if (null == clazz) {
            throw new IllegalArgumentException("clazz can not be null");
        }
        if (!clazz.isArray()) {
            throw new IllegalArgumentException(clazz.getCanonicalName() + " is not array type");
        }
    }

    private static void checkArrayType(ClassNode clazz) {
        if (null == clazz) {
            throw new IllegalArgumentException("clazz can not be null");
        }
        if (!clazz.isArray()) {
            throw new IllegalArgumentException(clazz.getName() + " is not array type");
        }
    }
}

