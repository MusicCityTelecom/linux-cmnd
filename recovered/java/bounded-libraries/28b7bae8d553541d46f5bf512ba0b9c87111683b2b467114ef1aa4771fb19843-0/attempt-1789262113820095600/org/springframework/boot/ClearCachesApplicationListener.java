/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationListener
 *  org.springframework.context.event.ContextRefreshedEvent
 *  org.springframework.util.ReflectionUtils
 */
package org.springframework.boot;

import java.lang.reflect.Method;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.ReflectionUtils;

class ClearCachesApplicationListener
implements ApplicationListener<ContextRefreshedEvent> {
    ClearCachesApplicationListener() {
    }

    public void onApplicationEvent(ContextRefreshedEvent event) {
        ReflectionUtils.clearCache();
        this.clearClassLoaderCaches(Thread.currentThread().getContextClassLoader());
    }

    private void clearClassLoaderCaches(ClassLoader classLoader) {
        if (classLoader == null) {
            return;
        }
        try {
            Method clearCacheMethod = classLoader.getClass().getDeclaredMethod("clearCache", new Class[0]);
            clearCacheMethod.invoke(classLoader, new Object[0]);
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.clearClassLoaderCaches(classLoader.getParent());
    }
}

