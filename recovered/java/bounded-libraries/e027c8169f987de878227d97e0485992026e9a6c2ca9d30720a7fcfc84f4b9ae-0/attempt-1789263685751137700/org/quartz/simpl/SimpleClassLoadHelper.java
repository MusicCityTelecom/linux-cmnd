/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.simpl;

import java.io.InputStream;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.net.URL;
import org.quartz.spi.ClassLoadHelper;

public class SimpleClassLoadHelper
implements ClassLoadHelper {
    @Override
    public void initialize() {
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return Class.forName(name);
    }

    @Override
    public <T> Class<? extends T> loadClass(String name, Class<T> clazz) throws ClassNotFoundException {
        return this.loadClass(name);
    }

    @Override
    public URL getResource(String name) {
        return this.getClassLoader().getResource(name);
    }

    @Override
    public InputStream getResourceAsStream(String name) {
        return this.getClassLoader().getResourceAsStream(name);
    }

    @Override
    public ClassLoader getClassLoader() {
        try {
            ClassLoader cl = this.getClass().getClassLoader();
            Method mthd = ClassLoader.class.getDeclaredMethod("getCallerClassLoader", new Class[0]);
            AccessibleObject.setAccessible(new AccessibleObject[]{mthd}, true);
            return (ClassLoader)mthd.invoke(cl, new Object[0]);
        }
        catch (Throwable all) {
            return this.getClass().getClassLoader();
        }
    }
}

