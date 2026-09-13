/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;
import org.glassfish.hk2.api.MultiException;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ClassAnalyzer {
    public static final String DEFAULT_IMPLEMENTATION_NAME = "default";

    public <T> Constructor<T> getConstructor(Class<T> var1) throws MultiException, NoSuchMethodException;

    public <T> Set<Method> getInitializerMethods(Class<T> var1) throws MultiException;

    public <T> Set<Field> getFields(Class<T> var1) throws MultiException;

    public <T> Method getPostConstructMethod(Class<T> var1) throws MultiException;

    public <T> Method getPreDestroyMethod(Class<T> var1) throws MultiException;
}

