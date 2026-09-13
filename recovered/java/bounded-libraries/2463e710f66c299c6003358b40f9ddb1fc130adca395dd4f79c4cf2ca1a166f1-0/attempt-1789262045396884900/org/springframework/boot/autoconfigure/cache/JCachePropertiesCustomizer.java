/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.autoconfigure.cache;

import java.util.Properties;
import org.springframework.boot.autoconfigure.cache.CacheProperties;

interface JCachePropertiesCustomizer {
    public void customize(CacheProperties var1, Properties var2);
}

