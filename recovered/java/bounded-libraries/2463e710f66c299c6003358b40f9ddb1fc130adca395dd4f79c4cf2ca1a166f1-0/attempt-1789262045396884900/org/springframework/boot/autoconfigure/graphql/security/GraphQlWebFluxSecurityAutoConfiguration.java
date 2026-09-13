/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  graphql.GraphQL
 *  org.springframework.context.annotation.Bean
 *  org.springframework.graphql.execution.ReactiveSecurityDataFetcherExceptionResolver
 *  org.springframework.graphql.server.webflux.GraphQlHttpHandler
 *  org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
 */
package org.springframework.boot.autoconfigure.graphql.security;

import graphql.GraphQL;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.graphql.reactive.GraphQlWebFluxAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.execution.ReactiveSecurityDataFetcherExceptionResolver;
import org.springframework.graphql.server.webflux.GraphQlHttpHandler;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@AutoConfiguration(after={GraphQlWebFluxAutoConfiguration.class})
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.REACTIVE)
@ConditionalOnClass(value={GraphQL.class, GraphQlHttpHandler.class, EnableWebFluxSecurity.class})
@ConditionalOnBean(value={GraphQlHttpHandler.class})
public class GraphQlWebFluxSecurityAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ReactiveSecurityDataFetcherExceptionResolver reactiveSecurityDataFetcherExceptionResolver() {
        return new ReactiveSecurityDataFetcherExceptionResolver();
    }
}

