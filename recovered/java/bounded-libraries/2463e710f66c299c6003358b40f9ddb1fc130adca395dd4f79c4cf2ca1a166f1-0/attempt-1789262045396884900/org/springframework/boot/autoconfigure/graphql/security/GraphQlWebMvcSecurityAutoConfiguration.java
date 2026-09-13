/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  graphql.GraphQL
 *  org.springframework.context.annotation.Bean
 *  org.springframework.graphql.execution.SecurityContextThreadLocalAccessor
 *  org.springframework.graphql.execution.SecurityDataFetcherExceptionResolver
 *  org.springframework.graphql.server.webmvc.GraphQlHttpHandler
 *  org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
 */
package org.springframework.boot.autoconfigure.graphql.security;

import graphql.GraphQL;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.graphql.servlet.GraphQlWebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.execution.SecurityContextThreadLocalAccessor;
import org.springframework.graphql.execution.SecurityDataFetcherExceptionResolver;
import org.springframework.graphql.server.webmvc.GraphQlHttpHandler;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@AutoConfiguration(after={GraphQlWebMvcAutoConfiguration.class})
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass(value={GraphQL.class, GraphQlHttpHandler.class, EnableWebSecurity.class})
@ConditionalOnBean(value={GraphQlHttpHandler.class})
public class GraphQlWebMvcSecurityAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public SecurityDataFetcherExceptionResolver securityDataFetcherExceptionResolver() {
        return new SecurityDataFetcherExceptionResolver();
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityContextThreadLocalAccessor securityContextThreadLocalAccessor() {
        return new SecurityContextThreadLocalAccessor();
    }
}

