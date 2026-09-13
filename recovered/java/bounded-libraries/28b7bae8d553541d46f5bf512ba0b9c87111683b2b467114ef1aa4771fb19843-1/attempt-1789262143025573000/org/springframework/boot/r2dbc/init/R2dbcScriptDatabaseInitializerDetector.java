/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.r2dbc.init;

import java.util.Collections;
import java.util.Set;
import org.springframework.boot.r2dbc.init.R2dbcScriptDatabaseInitializer;
import org.springframework.boot.sql.init.dependency.AbstractBeansOfTypeDatabaseInitializerDetector;

class R2dbcScriptDatabaseInitializerDetector
extends AbstractBeansOfTypeDatabaseInitializerDetector {
    R2dbcScriptDatabaseInitializerDetector() {
    }

    @Override
    protected Set<Class<?>> getDatabaseInitializerBeanTypes() {
        return Collections.singleton(R2dbcScriptDatabaseInitializer.class);
    }
}

