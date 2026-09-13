/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jooq.impl.DefaultConfiguration
 */
package org.springframework.boot.autoconfigure.jooq;

import org.jooq.impl.DefaultConfiguration;

@FunctionalInterface
public interface DefaultConfigurationCustomizer {
    public void customize(DefaultConfiguration var1);
}

