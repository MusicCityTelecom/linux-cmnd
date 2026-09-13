/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.neo4j.driver.Driver
 *  org.springframework.context.annotation.Bean
 *  org.springframework.data.neo4j.core.ReactiveDatabaseSelectionProvider
 *  org.springframework.data.neo4j.core.ReactiveNeo4jClient
 *  org.springframework.data.neo4j.core.ReactiveNeo4jOperations
 *  org.springframework.data.neo4j.core.ReactiveNeo4jTemplate
 *  org.springframework.data.neo4j.core.mapping.Neo4jMappingContext
 *  org.springframework.transaction.ReactiveTransactionManager
 *  reactor.core.publisher.Flux
 */
package org.springframework.boot.autoconfigure.data.neo4j;

import org.neo4j.driver.Driver;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.core.ReactiveDatabaseSelectionProvider;
import org.springframework.data.neo4j.core.ReactiveNeo4jClient;
import org.springframework.data.neo4j.core.ReactiveNeo4jOperations;
import org.springframework.data.neo4j.core.ReactiveNeo4jTemplate;
import org.springframework.data.neo4j.core.mapping.Neo4jMappingContext;
import org.springframework.transaction.ReactiveTransactionManager;
import reactor.core.publisher.Flux;

@AutoConfiguration(after={Neo4jDataAutoConfiguration.class})
@ConditionalOnClass(value={Driver.class, ReactiveNeo4jTemplate.class, ReactiveTransactionManager.class, Flux.class})
@ConditionalOnBean(value={Driver.class})
public class Neo4jReactiveDataAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ReactiveDatabaseSelectionProvider reactiveDatabaseSelectionProvider(Neo4jDataProperties dataProperties) {
        String database = dataProperties.getDatabase();
        return database != null ? ReactiveDatabaseSelectionProvider.createStaticDatabaseSelectionProvider((String)database) : ReactiveDatabaseSelectionProvider.getDefaultSelectionProvider();
    }

    @Bean(value={"reactiveNeo4jClient"})
    @ConditionalOnMissingBean
    public ReactiveNeo4jClient reactiveNeo4jClient(Driver driver, ReactiveDatabaseSelectionProvider databaseNameProvider) {
        return ReactiveNeo4jClient.create((Driver)driver, (ReactiveDatabaseSelectionProvider)databaseNameProvider);
    }

    @Bean(value={"reactiveNeo4jTemplate"})
    @ConditionalOnMissingBean(value={ReactiveNeo4jOperations.class})
    public ReactiveNeo4jTemplate reactiveNeo4jTemplate(ReactiveNeo4jClient neo4jClient, Neo4jMappingContext neo4jMappingContext) {
        return new ReactiveNeo4jTemplate(neo4jClient, neo4jMappingContext);
    }
}

