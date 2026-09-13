/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.flywaydb.core.Flyway
 */
package org.springframework.boot.autoconfigure.flyway;

import org.flywaydb.core.Flyway;

@FunctionalInterface
public interface FlywayMigrationStrategy {
    public void migrate(Flyway var1);
}

