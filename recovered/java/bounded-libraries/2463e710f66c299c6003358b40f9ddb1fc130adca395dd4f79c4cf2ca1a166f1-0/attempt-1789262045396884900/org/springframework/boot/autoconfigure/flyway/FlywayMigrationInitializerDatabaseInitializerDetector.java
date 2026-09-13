/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.sql.init.dependency.AbstractBeansOfTypeDatabaseInitializerDetector
 */
package org.springframework.boot.autoconfigure.flyway;

import java.util.Collections;
import java.util.Set;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.boot.sql.init.dependency.AbstractBeansOfTypeDatabaseInitializerDetector;

class FlywayMigrationInitializerDatabaseInitializerDetector
extends AbstractBeansOfTypeDatabaseInitializerDetector {
    FlywayMigrationInitializerDatabaseInitializerDetector() {
    }

    protected Set<Class<?>> getDatabaseInitializerBeanTypes() {
        return Collections.singleton(FlywayMigrationInitializer.class);
    }

    public int getOrder() {
        return 1;
    }
}

