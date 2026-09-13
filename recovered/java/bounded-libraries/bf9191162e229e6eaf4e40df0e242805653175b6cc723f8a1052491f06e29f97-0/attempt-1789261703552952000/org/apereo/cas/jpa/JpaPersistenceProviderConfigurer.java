/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.jpa;

import org.apereo.cas.jpa.JpaPersistenceProviderContext;
import org.springframework.core.Ordered;

@FunctionalInterface
public interface JpaPersistenceProviderConfigurer
extends Ordered {
    public void configure(JpaPersistenceProviderContext var1);

    default public int getOrder() {
        return Integer.MAX_VALUE;
    }
}

