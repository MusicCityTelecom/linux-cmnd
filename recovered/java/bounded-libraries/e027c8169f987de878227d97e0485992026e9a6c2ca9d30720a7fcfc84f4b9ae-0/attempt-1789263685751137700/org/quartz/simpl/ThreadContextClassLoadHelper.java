/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.simpl;

import java.io.InputStream;
import java.net.URL;
import org.quartz.spi.ClassLoadHelper;

public class ThreadContextClassLoadHelper
implements ClassLoadHelper {
    @Override
    public void initialize() {
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return this.getClassLoader().loadClass(name);
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
        return Thread.currentThread().getContextClassLoader();
    }
}

