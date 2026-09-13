/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.couchbase.repository.config.EnableReactiveCouchbaseRepositories
 *  org.springframework.data.couchbase.repository.config.ReactiveCouchbaseRepositoryConfigurationExtension
 *  org.springframework.data.repository.config.RepositoryConfigurationExtension
 */
package org.springframework.boot.autoconfigure.data.couchbase;

import java.lang.annotation.Annotation;
import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.data.couchbase.repository.config.EnableReactiveCouchbaseRepositories;
import org.springframework.data.couchbase.repository.config.ReactiveCouchbaseRepositoryConfigurationExtension;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

class CouchbaseReactiveRepositoriesRegistrar
extends AbstractRepositoryConfigurationSourceSupport {
    CouchbaseReactiveRepositoriesRegistrar() {
    }

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableReactiveCouchbaseRepositories.class;
    }

    @Override
    protected Class<?> getConfiguration() {
        return EnableReactiveCouchbaseRepositoriesConfiguration.class;
    }

    @Override
    protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
        return new ReactiveCouchbaseRepositoryConfigurationExtension();
    }

    @EnableReactiveCouchbaseRepositories
    private static class EnableReactiveCouchbaseRepositoriesConfiguration {
        private EnableReactiveCouchbaseRepositoriesConfiguration() {
        }
    }
}

