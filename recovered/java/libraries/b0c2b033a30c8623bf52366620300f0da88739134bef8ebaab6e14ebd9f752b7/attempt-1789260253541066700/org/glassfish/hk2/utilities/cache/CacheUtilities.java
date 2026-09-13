/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.cache;

import org.glassfish.hk2.utilities.cache.Computable;
import org.glassfish.hk2.utilities.cache.WeakCARCache;
import org.glassfish.hk2.utilities.cache.internal.WeakCARCacheImpl;

public class CacheUtilities {
    public static <K, V> WeakCARCache<K, V> createWeakCARCache(Computable<K, V> computable, int maxSize, boolean isWeak) {
        return new WeakCARCacheImpl<K, V>(computable, maxSize, isWeak);
    }
}

