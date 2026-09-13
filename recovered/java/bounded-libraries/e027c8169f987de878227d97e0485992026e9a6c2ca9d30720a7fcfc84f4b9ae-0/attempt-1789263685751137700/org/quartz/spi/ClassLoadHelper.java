/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.spi;

import java.io.InputStream;
import java.net.URL;

public interface ClassLoadHelper {
    public void initialize();

    public Class<?> loadClass(String var1) throws ClassNotFoundException;

    public <T> Class<? extends T> loadClass(String var1, Class<T> var2) throws ClassNotFoundException;

    public URL getResource(String var1);

    public InputStream getResourceAsStream(String var1);

    public ClassLoader getClassLoader();
}

