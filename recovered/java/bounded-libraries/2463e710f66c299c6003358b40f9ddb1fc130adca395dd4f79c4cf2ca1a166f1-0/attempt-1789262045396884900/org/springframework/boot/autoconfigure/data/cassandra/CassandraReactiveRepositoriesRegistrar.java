/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories
 *  org.springframework.data.cassandra.repository.config.ReactiveCassandraRepositoryConfigurationExtension
 *  org.springframework.data.repository.config.RepositoryConfigurationExtension
 */
package org.springframework.boot.autoconfigure.data.cassandra;

import java.lang.annotation.Annotation;
import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;
import org.springframework.data.cassandra.repository.config.ReactiveCassandraRepositoryConfigurationExtension;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

class CassandraReactiveRepositoriesRegistrar
extends AbstractRepositoryConfigurationSourceSupport {
    CassandraReactiveRepositoriesRegistrar() {
    }

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableReactiveCassandraRepositories.class;
    }

    @Override
    protected Class<?> getConfiguration() {
        return EnableReactiveCassandraRepositoriesConfiguration.class;
    }

    @Override
    protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
        return new ReactiveCassandraRepositoryConfigurationExtension();
    }

    @EnableReactiveCassandraRepositories
    private static class EnableReactiveCassandraRepositoriesConfiguration {
        private EnableReactiveCassandraRepositoriesConfiguration() {
        }
    }
}

