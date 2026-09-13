/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.util.LambdaSafe
 *  org.springframework.boot.util.LambdaSafe$Callbacks
 *  org.springframework.cache.CacheManager
 */
package org.springframework.boot.autoconfigure.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.boot.util.LambdaSafe;
import org.springframework.cache.CacheManager;

public class CacheManagerCustomizers {
    private final List<CacheManagerCustomizer<?>> customizers;

    public CacheManagerCustomizers(List<? extends CacheManagerCustomizer<?>> customizers) {
        this.customizers = customizers != null ? new ArrayList(customizers) : Collections.emptyList();
    }

    public <T extends CacheManager> T customize(T cacheManager) {
        ((LambdaSafe.Callbacks)LambdaSafe.callbacks(CacheManagerCustomizer.class, this.customizers, cacheManager, (Object[])new Object[0]).withLogger(CacheManagerCustomizers.class)).invoke(customizer -> customizer.customize(cacheManager));
        return cacheManager;
    }
}

