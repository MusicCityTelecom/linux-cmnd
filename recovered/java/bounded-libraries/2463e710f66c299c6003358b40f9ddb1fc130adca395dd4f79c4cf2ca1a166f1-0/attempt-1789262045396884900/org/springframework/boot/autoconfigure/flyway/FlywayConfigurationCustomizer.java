/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.flywaydb.core.api.configuration.FluentConfiguration
 */
package org.springframework.boot.autoconfigure.flyway;

import org.flywaydb.core.api.configuration.FluentConfiguration;

@FunctionalInterface
public interface FlywayConfigurationCustomizer {
    public void customize(FluentConfiguration var1);
}

