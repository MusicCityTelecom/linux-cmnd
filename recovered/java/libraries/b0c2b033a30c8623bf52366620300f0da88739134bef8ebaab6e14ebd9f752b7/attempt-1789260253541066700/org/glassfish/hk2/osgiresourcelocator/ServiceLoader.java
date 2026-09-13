/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.osgiresourcelocator;

public abstract class ServiceLoader {
    private static ServiceLoader _me;

    ServiceLoader() {
    }

    public static synchronized void initialize(ServiceLoader singleton) {
        if (singleton == null) {
            throw new NullPointerException("Did you intend to call reset()?");
        }
        if (_me != null) {
            throw new IllegalStateException("Already initialzed with [" + _me + "]");
        }
        _me = singleton;
    }

    public static synchronized void reset() {
        if (_me == null) {
            throw new IllegalStateException("Not yet initialized");
        }
        _me = null;
    }

    public static <T> Iterable<? extends T> lookupProviderInstances(Class<T> serviceClass) {
        return ServiceLoader.lookupProviderInstances(serviceClass, null);
    }

    public static <T> Iterable<? extends T> lookupProviderInstances(Class<T> serviceClass, ProviderFactory<T> factory) {
        if (_me == null) {
            return null;
        }
        return _me.lookupProviderInstances1(serviceClass, factory);
    }

    public static <T> Iterable<Class> lookupProviderClasses(Class<T> serviceClass) {
        return _me.lookupProviderClasses1(serviceClass);
    }

    abstract <T> Iterable<? extends T> lookupProviderInstances1(Class<T> var1, ProviderFactory<T> var2);

    abstract <T> Iterable<Class> lookupProviderClasses1(Class<T> var1);

    public static interface ProviderFactory<T> {
        public T make(Class var1, Class<T> var2) throws Exception;
    }
}

