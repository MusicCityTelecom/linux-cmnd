/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.datastax.oss.driver.api.core.config.ProgrammaticDriverConfigLoaderBuilder
 */
package org.springframework.boot.autoconfigure.cassandra;

import com.datastax.oss.driver.api.core.config.ProgrammaticDriverConfigLoaderBuilder;

public interface DriverConfigLoaderBuilderCustomizer {
    public void customize(ProgrammaticDriverConfigLoaderBuilder var1);
}

