/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities;

import org.glassfish.hk2.api.HK2Loader;
import org.glassfish.hk2.api.MultiException;

public class HK2LoaderImpl
implements HK2Loader {
    private final ClassLoader loader;

    public HK2LoaderImpl() {
        this(ClassLoader.getSystemClassLoader());
    }

    public HK2LoaderImpl(ClassLoader loader) {
        if (loader == null) {
            throw new IllegalArgumentException();
        }
        this.loader = loader;
    }

    @Override
    public Class<?> loadClass(String className) throws MultiException {
        try {
            return this.loader.loadClass(className);
        }
        catch (Exception e) {
            throw new MultiException(e);
        }
    }

    public String toString() {
        return "HK2LoaderImpl(" + this.loader + ")";
    }
}

