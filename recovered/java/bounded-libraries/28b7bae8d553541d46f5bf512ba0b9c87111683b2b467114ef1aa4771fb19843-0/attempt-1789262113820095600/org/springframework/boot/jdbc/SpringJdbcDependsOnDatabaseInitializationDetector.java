/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
 */
package org.springframework.boot.jdbc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springframework.boot.sql.init.dependency.AbstractBeansOfTypeDependsOnDatabaseInitializationDetector;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

class SpringJdbcDependsOnDatabaseInitializationDetector
extends AbstractBeansOfTypeDependsOnDatabaseInitializationDetector {
    SpringJdbcDependsOnDatabaseInitializationDetector() {
    }

    @Override
    protected Set<Class<?>> getDependsOnDatabaseInitializationBeanTypes() {
        return new HashSet(Arrays.asList(JdbcOperations.class, NamedParameterJdbcOperations.class));
    }
}

