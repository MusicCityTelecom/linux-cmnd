/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.batch.core.repository.JobRepository
 *  org.springframework.boot.sql.init.dependency.AbstractBeansOfTypeDependsOnDatabaseInitializationDetector
 */
package org.springframework.boot.autoconfigure.batch;

import java.util.Collections;
import java.util.Set;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.boot.sql.init.dependency.AbstractBeansOfTypeDependsOnDatabaseInitializationDetector;

class JobRepositoryDependsOnDatabaseInitializationDetector
extends AbstractBeansOfTypeDependsOnDatabaseInitializationDetector {
    JobRepositoryDependsOnDatabaseInitializationDetector() {
    }

    protected Set<Class<?>> getDependsOnDatabaseInitializationBeanTypes() {
        return Collections.singleton(JobRepository.class);
    }
}

