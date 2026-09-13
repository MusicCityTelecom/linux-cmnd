/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.cassandra.repository.config.CassandraRepositoryConfigurationExtension
 *  org.springframework.data.cassandra.repository.config.EnableCassandraRepositories
 *  org.springframework.data.repository.config.RepositoryConfigurationExtension
 */
package org.springframework.boot.autoconfigure.data.cassandra;

import java.lang.annotation.Annotation;
import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.data.cassandra.repository.config.CassandraRepositoryConfigurationExtension;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

class CassandraRepositoriesRegistrar
extends AbstractRepositoryConfigurationSourceSupport {
    CassandraRepositoriesRegistrar() {
    }

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableCassandraRepositories.class;
    }

    @Override
    protected Class<?> getConfiguration() {
        return EnableCassandraRepositoriesConfiguration.class;
    }

    @Override
    protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
        return new CassandraRepositoryConfigurationExtension();
    }

    @EnableCassandraRepositories
    private static class EnableCassandraRepositoriesConfiguration {
        private EnableCassandraRepositoriesConfiguration() {
        }
    }
}

