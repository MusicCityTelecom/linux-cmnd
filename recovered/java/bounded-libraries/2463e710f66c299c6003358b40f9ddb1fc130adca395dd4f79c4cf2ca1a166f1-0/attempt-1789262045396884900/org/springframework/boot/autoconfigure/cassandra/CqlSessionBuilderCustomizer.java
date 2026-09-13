/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.datastax.oss.driver.api.core.CqlSessionBuilder
 */
package org.springframework.boot.autoconfigure.cassandra;

import com.datastax.oss.driver.api.core.CqlSessionBuilder;

@FunctionalInterface
public interface CqlSessionBuilderCustomizer {
    public void customize(CqlSessionBuilder var1);
}

