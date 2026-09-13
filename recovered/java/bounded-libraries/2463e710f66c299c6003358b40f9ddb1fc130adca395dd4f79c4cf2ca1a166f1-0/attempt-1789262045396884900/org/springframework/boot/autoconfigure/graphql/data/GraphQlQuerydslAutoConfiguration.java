/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  graphql.GraphQL
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.context.annotation.Bean
 *  org.springframework.data.querydsl.QuerydslPredicateExecutor
 *  org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor
 *  org.springframework.graphql.data.query.QuerydslDataFetcher
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
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;
import org.springframework.graphql.data.query.QuerydslDataFetcher;
import org.springframework.graphql.execution.GraphQlSource;

@AutoConfiguration(after={GraphQlAutoConfiguration.class})
@ConditionalOnClass(value={GraphQL.class, QuerydslDataFetcher.class, QuerydslPredicateExecutor.class})
@ConditionalOnBean(value={GraphQlSource.class})
public class GraphQlQuerydslAutoConfiguration {
    @Bean
    public GraphQlSourceBuilderCustomizer querydslRegistrar(ObjectProvider<QuerydslPredicateExecutor<?>> executors, ObjectProvider<ReactiveQuerydslPredicateExecutor<?>> reactiveExecutors) {
        return new GraphQlQuerydslSourceBuilderCustomizer(QuerydslDataFetcher::autoRegistrationConfigurer, executors, reactiveExecutors);
    }
}

