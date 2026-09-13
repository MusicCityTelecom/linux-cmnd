/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.flywaydb.core.Flyway
 */
package org.springframework.boot.flyway;

import java.util.Collections;
import java.util.Set;
import org.flywaydb.core.Flyway;
import org.springframework.boot.sql.init.dependency.AbstractBeansOfTypeDatabaseInitializerDetector;

class FlywayDatabaseInitializerDetector
extends AbstractBeansOfTypeDatabaseInitializerDetector {
    FlywayDatabaseInitializerDetector() {
    }

    @Override
    protected Set<Class<?>> getDatabaseInitializerBeanTypes() {
        return Collections.singleton(Flyway.class);
    }
}

