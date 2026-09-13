/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cache.CacheManager
 */
package org.springframework.boot.autoconfigure.cache;

import org.springframework.cache.CacheManager;

@FunctionalInterface
public interface CacheManagerCustomizer<T extends CacheManager> {
    public void customize(T var1);
}

