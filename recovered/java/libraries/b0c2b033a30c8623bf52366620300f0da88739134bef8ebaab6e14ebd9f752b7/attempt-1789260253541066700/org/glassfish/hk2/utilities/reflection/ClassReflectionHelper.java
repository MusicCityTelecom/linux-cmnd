/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;
import org.glassfish.hk2.utilities.reflection.MethodWrapper;

public interface ClassReflectionHelper {
    public Set<MethodWrapper> getAllMethods(Class<?> var1);

    public MethodWrapper createMethodWrapper(Method var1);

    public Set<Field> getAllFields(Class<?> var1);

    public Method findPostConstruct(Class<?> var1, Class<?> var2) throws IllegalArgumentException;

    public Method findPreDestroy(Class<?> var1, Class<?> var2) throws IllegalArgumentException;

    public void clean(Class<?> var1);

    public void dispose();

    public int size();
}

