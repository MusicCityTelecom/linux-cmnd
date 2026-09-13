/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  graphql.GraphQL
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.context.annotation.Bean
 *  org.springframework.data.repository.query.ReactiveQueryByExampleExecutor
 *  org.springframework.graphql.data.query.QueryByExampleDataFetcher
 *  org.springframework.graphql.execution.GraphQlSource
 */
package org.springframework.boot.autoconfigure.graphql.data;

import graphql.GraphQL;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.graphql.GraphQlAutoConfiguration;
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.boot.autoconfigure.graphql.data.GraphQlQuerydslSourceBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.graphql.data.query.QueryByExampleDataFetcher;
import org.springframework.graphql.execution.GraphQlSource;

@AutoConfiguration(after={GraphQlAutoConfiguration.class})
@ConditionalOnClass(value={GraphQL.class, QueryByExampleDataFetcher.class, ReactiveQueryByExampleExecutor.class})
@ConditionalOnBean(value={GraphQlSource.class})
public class GraphQlReactiveQueryByExampleAutoConfiguration {
    @Bean
    public GraphQlSourceBuilderCustomizer reactiveQueryByExampleRegistrar(ObjectProvider<ReactiveQueryByExampleExecutor<?>> reactiveExecutors) {
        return new GraphQlQuerydslSourceBuilderCustomizer(QueryByExampleDataFetcher::autoRegistrationConfigurer, (ObjectProvider)null, reactiveExecutors);
    }
}

