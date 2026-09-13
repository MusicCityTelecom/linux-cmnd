/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  liquibase.integration.spring.SpringLiquibase
 */
package org.springframework.boot.liquibase;

import java.util.Collections;
import java.util.Set;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.sql.init.dependency.AbstractBeansOfTypeDatabaseInitializerDetector;

class LiquibaseDatabaseInitializerDetector
extends AbstractBeansOfTypeDatabaseInitializerDetector {
    LiquibaseDatabaseInitializerDetector() {
    }

    @Override
    protected Set<Class<?>> getDatabaseInitializerBeanTypes() {
        return Collections.singleton(SpringLiquibase.class);
    }
}

