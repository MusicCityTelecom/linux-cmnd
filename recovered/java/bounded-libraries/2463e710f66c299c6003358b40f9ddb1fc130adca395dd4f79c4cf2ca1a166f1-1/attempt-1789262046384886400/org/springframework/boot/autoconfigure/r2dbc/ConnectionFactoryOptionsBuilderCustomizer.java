/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.r2dbc.spi.ConnectionFactoryOptions$Builder
 */
package org.springframework.boot.autoconfigure.r2dbc;

import io.r2dbc.spi.ConnectionFactoryOptions;

@FunctionalInterface
public interface ConnectionFactoryOptionsBuilderCustomizer {
    public void customize(ConnectionFactoryOptions.Builder var1);
}

