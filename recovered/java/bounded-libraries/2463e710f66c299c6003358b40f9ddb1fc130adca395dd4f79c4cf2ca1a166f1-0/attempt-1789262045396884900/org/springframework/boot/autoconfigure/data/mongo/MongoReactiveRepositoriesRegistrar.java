/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
 *  org.springframework.data.mongodb.repository.config.ReactiveMongoRepositoryConfigurationExtension
 *  org.springframework.data.repository.config.RepositoryConfigurationExtension
 */
package org.springframework.boot.autoconfigure.data.mongo;

import java.lang.annotation.Annotation;
import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.data.mongodb.repository.config.ReactiveMongoRepositoryConfigurationExtension;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

class MongoReactiveRepositoriesRegistrar
extends AbstractRepositoryConfigurationSourceSupport {
    MongoReactiveRepositoriesRegistrar() {
    }

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableReactiveMongoRepositories.class;
    }

    @Override
    protected Class<?> getConfiguration() {
        return EnableReactiveMongoRepositoriesConfiguration.class;
    }

    @Override
    protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
        return new ReactiveMongoRepositoryConfigurationExtension();
    }

    @EnableReactiveMongoRepositories
    private static class EnableReactiveMongoRepositoriesConfiguration {
        private EnableReactiveMongoRepositoriesConfiguration() {
        }
    }
}

