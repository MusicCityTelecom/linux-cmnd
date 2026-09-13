/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.redis.cache.RedisCacheManager$RedisCacheManagerBuilder
 */
package org.springframework.boot.autoconfigure.cache;

import org.springframework.data.redis.cache.RedisCacheManager;

@FunctionalInterface
public interface RedisCacheManagerBuilderCustomizer {
    public void customize(RedisCacheManager.RedisCacheManagerBuilder var1);
}

