/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.neo4j.repository.config.EnableReactiveNeo4jRepositories
 *  org.springframework.data.neo4j.repository.config.ReactiveNeo4jRepositoryConfigurationExtension
 *  org.springframework.data.repository.config.RepositoryConfigurationExtension
 */
package org.springframework.boot.autoconfigure.data.neo4j;

import java.lang.annotation.Annotation;
import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.data.neo4j.repository.config.EnableReactiveNeo4jRepositories;
import org.springframework.data.neo4j.repository.config.ReactiveNeo4jRepositoryConfigurationExtension;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

class Neo4jReactiveRepositoriesRegistrar
extends AbstractRepositoryConfigurationSourceSupport {
    Neo4jReactiveRepositoriesRegistrar() {
    }

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableReactiveNeo4jRepositories.class;
    }

    @Override
    protected Class<?> getConfiguration() {
        return EnableReactiveNeo4jRepositoriesConfiguration.class;
    }

    @Override
    protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
        return new ReactiveNeo4jRepositoryConfigurationExtension();
    }

    @EnableReactiveNeo4jRepositories
    private static class EnableReactiveNeo4jRepositoriesConfiguration {
        private EnableReactiveNeo4jRepositoriesConfiguration() {
        }
    }
}

