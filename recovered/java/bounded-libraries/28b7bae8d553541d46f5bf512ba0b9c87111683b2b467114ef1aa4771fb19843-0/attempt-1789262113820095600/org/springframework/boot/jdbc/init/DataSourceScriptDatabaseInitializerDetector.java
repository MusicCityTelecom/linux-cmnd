/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.jdbc.init;

import java.util.Collections;
import java.util.Set;
import org.springframework.boot.jdbc.init.DataSourceScriptDatabaseInitializer;
import org.springframework.boot.sql.init.dependency.AbstractBeansOfTypeDatabaseInitializerDetector;

class DataSourceScriptDatabaseInitializerDetector
extends AbstractBeansOfTypeDatabaseInitializerDetector {
    static final int PRECEDENCE = 2147483547;

    DataSourceScriptDatabaseInitializerDetector() {
    }

    @Override
    protected Set<Class<?>> getDatabaseInitializerBeanTypes() {
        return Collections.singleton(DataSourceScriptDatabaseInitializer.class);
    }

    @Override
    public int getOrder() {
        return 2147483547;
    }
}

