/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.couchbase.repository.config.CouchbaseRepositoryConfigurationExtension
 *  org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories
 *  org.springframework.data.repository.config.RepositoryConfigurationExtension
 */
package org.springframework.boot.autoconfigure.data.couchbase;

import java.lang.annotation.Annotation;
import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.data.couchbase.repository.config.CouchbaseRepositoryConfigurationExtension;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

class CouchbaseRepositoriesRegistrar
extends AbstractRepositoryConfigurationSourceSupport {
    CouchbaseRepositoriesRegistrar() {
    }

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableCouchbaseRepositories.class;
    }

    @Override
    protected Class<?> getConfiguration() {
        return EnableCouchbaseRepositoriesConfiguration.class;
    }

    @Override
    protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
        return new CouchbaseRepositoryConfigurationExtension();
    }

    @EnableCouchbaseRepositories
    private static class EnableCouchbaseRepositoriesConfiguration {
        private EnableCouchbaseRepositoriesConfiguration() {
        }
    }
}

