/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ReflectionUtils
 *  org.springframework.util.ReflectionUtils$MethodFilter
 */
package org.springframework.integration.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

public class UniqueMethodFilter
implements ReflectionUtils.MethodFilter {
    private final List<Method> uniqueMethods = new ArrayList<Method>();

    public UniqueMethodFilter(Class<?> targetClass) {
        Method[] allMethods;
        for (Method method : allMethods = ReflectionUtils.getAllDeclaredMethods(targetClass)) {
            this.uniqueMethods.add(ClassUtils.getMostSpecificMethod((Method)method, targetClass));
        }
    }

    public boolean matches(Method method) {
        return this.uniqueMethods.contains(method);
    }
}

