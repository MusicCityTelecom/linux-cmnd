/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.simpl;

import java.io.InputStream;
import java.net.URL;
import org.quartz.spi.ClassLoadHelper;

public class InitThreadContextClassLoadHelper
implements ClassLoadHelper {
    private ClassLoader initClassLoader;

    @Override
    public void initialize() {
        this.initClassLoader = Thread.currentThread().getContextClassLoader();
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return this.initClassLoader.loadClass(name);
    }

    @Override
    public <T> Class<? extends T> loadClass(String name, Class<T> clazz) throws ClassNotFoundException {
        return this.loadClass(name);
    }

    @Override
    public URL getResource(String name) {
        return this.initClassLoader.getResource(name);
    }

    @Override
    public InputStream getResourceAsStream(String name) {
        return this.initClassLoader.getResourceAsStream(name);
    }

    @Override
    public ClassLoader getClassLoader() {
        return this.initClassLoader;
    }
}

