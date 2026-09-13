/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.neo4j.driver.Driver
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.data.neo4j.core.DatabaseSelectionProvider
 *  org.springframework.data.neo4j.core.Neo4jClient
 *  org.springframework.data.neo4j.core.Neo4jOperations
 *  org.springframework.data.neo4j.core.Neo4jTemplate
 *  org.springframework.data.neo4j.core.convert.Neo4jConversions
 *  org.springframework.data.neo4j.core.mapping.Neo4jMappingContext
 *  org.springframework.data.neo4j.core.schema.Node
 *  org.springframework.data.neo4j.core.schema.RelationshipProperties
 *  org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager
 *  org.springframework.transaction.PlatformTransactionManager
 *  org.springframework.transaction.TransactionManager
 */
package org.springframework.boot.autoconfigure.data.neo4j;

import java.util.Set;
import org.neo4j.driver.Driver;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataProperties;
import org.springframework.boot.autoconfigure.domain.EntityScanner;
import org.springframework.boot.autoconfigure.neo4j.Neo4jAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.core.DatabaseSelectionProvider;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jOperations;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.core.convert.Neo4jConversions;
import org.springframework.data.neo4j.core.mapping.Neo4jMappingContext;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;

@AutoConfiguration(before={TransactionAutoConfiguration.class}, after={Neo4jAutoConfiguration.class})
@ConditionalOnClass(value={Driver.class, Neo4jTransactionManager.class, PlatformTransactionManager.class})
@EnableConfigurationProperties(value={Neo4jDataProperties.class})
@ConditionalOnBean(value={Driver.class})
public class Neo4jDataAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public Neo4jConversions neo4jConversions() {
        return new Neo4jConversions();
    }

    @Bean
    @ConditionalOnMissingBean
    public Neo4jMappingContext neo4jMappingContext(ApplicationContext applicationContext, Neo4jConversions neo4jConversions) throws ClassNotFoundException {
        Set<Class<?>> initialEntityClasses = new EntityScanner(applicationContext).scan(Node.class, RelationshipProperties.class);
        Neo4jMappingContext context = new Neo4jMappingContext(neo4jConversions);
        context.setInitialEntitySet(initialEntityClasses);
        return context;
    }

    @Bean
    @ConditionalOnMissingBean
    public DatabaseSelectionProvider databaseSelectionProvider(Neo4jDataProperties properties) {
        String database = properties.getDatabase();
        return database != null ? DatabaseSelectionProvider.createStaticDatabaseSelectionProvider((String)database) : DatabaseSelectionProvider.getDefaultSelectionProvider();
    }

    @Bean(value={"neo4jClient"})
    @ConditionalOnMissingBean
    public Neo4jClient neo4jClient(Driver driver, DatabaseSelectionProvider databaseNameProvider) {
        return Neo4jClient.create((Driver)driver, (DatabaseSelectionProvider)databaseNameProvider);
    }

    @Bean(value={"neo4jTemplate"})
    @ConditionalOnMissingBean(value={Neo4jOperations.class})
    public Neo4jTemplate neo4jTemplate(Neo4jClient neo4jClient, Neo4jMappingContext neo4jMappingContext) {
        return new Neo4jTemplate(neo4jClient, neo4jMappingContext);
    }

    @Bean(value={"transactionManager"})
    @ConditionalOnMissingBean(value={TransactionManager.class})
    public Neo4jTransactionManager transactionManager(Driver driver, DatabaseSelectionProvider databaseNameProvider, ObjectProvider<TransactionManagerCustomizers> optionalCustomizers) {
        Neo4jTransactionManager transactionManager = new Neo4jTransactionManager(driver, databaseNameProvider);
        optionalCustomizers.ifAvailable(customizer -> customizer.customize((PlatformTransactionManager)transactionManager));
        return transactionManager;
    }
}

