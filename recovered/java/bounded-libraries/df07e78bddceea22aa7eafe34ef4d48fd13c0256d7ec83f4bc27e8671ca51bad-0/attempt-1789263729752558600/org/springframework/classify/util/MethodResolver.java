/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.classify.util;

import java.lang.reflect.Method;

public interface MethodResolver {
    public Method findMethod(Object var1) throws IllegalArgumentException;

    public Method findMethod(Class<?> var1);
}

