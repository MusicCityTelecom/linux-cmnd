/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.couchbase.cache.CouchbaseCacheManager$CouchbaseCacheManagerBuilder
 */
package org.springframework.boot.autoconfigure.cache;

import org.springframework.data.couchbase.cache.CouchbaseCacheManager;

@FunctionalInterface
public interface CouchbaseCacheManagerBuilderCustomizer {
    public void customize(CouchbaseCacheManager.CouchbaseCacheManagerBuilder var1);
}

