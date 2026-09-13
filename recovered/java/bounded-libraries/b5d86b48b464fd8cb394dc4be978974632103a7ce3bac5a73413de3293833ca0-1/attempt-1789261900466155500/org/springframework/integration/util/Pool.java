/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.util;

public interface Pool<T> {
    public T getItem();

    public void releaseItem(T var1);

    public void removeAllIdleItems();

    public int getPoolSize();

    public int getIdleCount();

    public int getActiveCount();

    public int getAllocatedCount();

    default public void close() {
    }
}

