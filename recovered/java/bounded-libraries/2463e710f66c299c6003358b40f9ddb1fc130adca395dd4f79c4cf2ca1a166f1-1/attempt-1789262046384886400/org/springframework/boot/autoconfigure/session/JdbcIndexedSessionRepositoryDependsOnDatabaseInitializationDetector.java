/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.sql.init.dependency.AbstractBeansOfTypeDependsOnDatabaseInitializationDetector
 *  org.springframework.session.jdbc.JdbcIndexedSessionRepository
 */
package org.springframework.boot.autoconfigure.session;

import java.util.Collections;
import java.util.Set;
import org.springframework.boot.sql.init.dependency.AbstractBeansOfTypeDependsOnDatabaseInitializationDetector;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;

class JdbcIndexedSessionRepositoryDependsOnDatabaseInitializationDetector
extends AbstractBeansOfTypeDependsOnDatabaseInitializationDetector {
    JdbcIndexedSessionRepositoryDependsOnDatabaseInitializationDetector() {
    }

    protected Set<Class<?>> getDependsOnDatabaseInitializationBeanTypes() {
        return Collections.singleton(JdbcIndexedSessionRepository.class);
    }
}

