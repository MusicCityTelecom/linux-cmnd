/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 */
package org.springframework.boot;

import org.springframework.beans.factory.config.BeanDefinition;

@FunctionalInterface
public interface LazyInitializationExcludeFilter {
    public boolean isExcluded(String var1, BeanDefinition var2, Class<?> var3);

    public static LazyInitializationExcludeFilter forBeanTypes(Class<?> ... types) {
        return (beanName, beanDefinition, beanType) -> {
            for (Class type : types) {
                if (!type.isAssignableFrom(beanType)) continue;
                return true;
            }
            return false;
        };
    }
}

