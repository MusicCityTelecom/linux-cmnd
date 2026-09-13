/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

public interface SingleCache<T> {
    public T getCache();

    public boolean isCacheSet();

    public void setCache(T var1);

    public void releaseCache();
}

