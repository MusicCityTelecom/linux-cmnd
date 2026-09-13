/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jooq.DSLContext
 */
package org.springframework.boot.jooq;

import java.util.Collections;
import java.util.Set;
import org.jooq.DSLContext;
import org.springframework.boot.sql.init.dependency.AbstractBeansOfTypeDependsOnDatabaseInitializationDetector;

class JooqDependsOnDatabaseInitializationDetector
extends AbstractBeansOfTypeDependsOnDatabaseInitializationDetector {
    JooqDependsOnDatabaseInitializationDetector() {
    }

    @Override
    protected Set<Class<?>> getDependsOnDatabaseInitializationBeanTypes() {
        return Collections.singleton(DSLContext.class);
    }
}

