/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.cache.CacheManager
 */
package org.springframework.boot.autoconfigure.cache;

import javax.cache.CacheManager;

@FunctionalInterface
public interface JCacheManagerCustomizer {
    public void customize(CacheManager var1);
}

