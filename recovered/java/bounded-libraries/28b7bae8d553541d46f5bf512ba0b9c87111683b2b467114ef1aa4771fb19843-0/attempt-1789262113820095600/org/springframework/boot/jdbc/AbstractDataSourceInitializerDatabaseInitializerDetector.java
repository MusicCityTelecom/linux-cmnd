/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.jdbc;

import java.util.Collections;
import java.util.Set;
import org.springframework.boot.jdbc.AbstractDataSourceInitializer;
import org.springframework.boot.sql.init.dependency.AbstractBeansOfTypeDatabaseInitializerDetector;

@Deprecated
class AbstractDataSourceInitializerDatabaseInitializerDetector
extends AbstractBeansOfTypeDatabaseInitializerDetector {
    private static final int PRECEDENCE = 2147483547;

    AbstractDataSourceInitializerDatabaseInitializerDetector() {
    }

    @Override
    protected Set<Class<?>> getDatabaseInitializerBeanTypes() {
        return Collections.singleton(AbstractDataSourceInitializer.class);
    }

    @Override
    public int getOrder() {
        return 2147483547;
    }
}

