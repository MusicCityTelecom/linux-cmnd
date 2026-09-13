/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.neo4j.driver.Driver
 *  org.springframework.context.annotation.Import
 *  org.springframework.data.neo4j.repository.ReactiveNeo4jRepository
 *  org.springframework.data.neo4j.repository.config.ReactiveNeo4jRepositoryConfigurationExtension
 *  org.springframework.data.neo4j.repository.support.ReactiveNeo4jRepositoryFactoryBean
 *  reactor.core.publisher.Flux
 */
package org.springframework.boot.autoconfigure.data.neo4j;

import org.neo4j.driver.Driver;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.boot.autoconfigure.data.RepositoryType;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jReactiveRepositoriesRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.config.ReactiveNeo4jRepositoryConfigurationExtension;
import org.springframework.data.neo4j.repository.support.ReactiveNeo4jRepositoryFactoryBean;
import reactor.core.publisher.Flux;

@AutoConfiguration(after={Neo4jReactiveDataAutoConfiguration.class})
@ConditionalOnClass(value={Driver.class, ReactiveNeo4jRepository.class, Flux.class})
@ConditionalOnMissingBean(value={ReactiveNeo4jRepositoryFactoryBean.class, ReactiveNeo4jRepositoryConfigurationExtension.class})
@ConditionalOnRepositoryType(store="neo4j", type=RepositoryType.REACTIVE)
@Import(value={Neo4jReactiveRepositoriesRegistrar.class})
public class Neo4jReactiveRepositoriesAutoConfiguration {
}

