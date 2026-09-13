/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.io;

import java.io.InputStream;
import org.cryptacular.io.Resource;

public class ClassPathResource
implements Resource {
    private final String classPath;
    private final ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path, Thread.currentThread().getContextClassLoader());
    }

    public ClassPathResource(String path, ClassLoader loader) {
        this.classPath = path.startsWith("/") ? path.substring(1) : path;
        this.classLoader = loader;
    }

    @Override
    public InputStream getInputStream() {
        return this.classLoader.getResourceAsStream(this.classPath);
    }
}

