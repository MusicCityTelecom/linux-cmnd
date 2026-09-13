/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.cache2k.Cache2kBuilder
 */
package org.springframework.boot.autoconfigure.cache;

import org.cache2k.Cache2kBuilder;

public interface Cache2kBuilderCustomizer {
    public void customize(Cache2kBuilder<?, ?> var1);
}

