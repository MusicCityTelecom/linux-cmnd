/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories
 *  org.springframework.data.elasticsearch.repository.config.ReactiveElasticsearchRepositoryConfigurationExtension
 *  org.springframework.data.repository.config.RepositoryConfigurationExtension
 */
package org.springframework.boot.autoconfigure.data.elasticsearch;

import java.lang.annotation.Annotation;
import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;
import org.springframework.data.elasticsearch.repository.config.ReactiveElasticsearchRepositoryConfigurationExtension;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

class ReactiveElasticsearchRepositoriesRegistrar
extends AbstractRepositoryConfigurationSourceSupport {
    ReactiveElasticsearchRepositoriesRegistrar() {
    }

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableReactiveElasticsearchRepositories.class;
    }

    @Override
    protected Class<?> getConfiguration() {
        return EnableElasticsearchRepositoriesConfiguration.class;
    }

    @Override
    protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
        return new ReactiveElasticsearchRepositoryConfigurationExtension();
    }

    @EnableReactiveElasticsearchRepositories
    private static class EnableElasticsearchRepositoriesConfiguration {
        private EnableElasticsearchRepositoriesConfiguration() {
        }
    }
}

