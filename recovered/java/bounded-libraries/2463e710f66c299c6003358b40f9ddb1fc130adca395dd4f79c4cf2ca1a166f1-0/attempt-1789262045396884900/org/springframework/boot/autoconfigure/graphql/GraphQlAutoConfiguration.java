/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  graphql.GraphQL
 *  graphql.execution.instrumentation.Instrumentation
 *  graphql.schema.idl.RuntimeWiring$Builder
 *  graphql.schema.visibility.GraphqlFieldVisibility
 *  graphql.schema.visibility.NoIntrospectionGraphqlFieldVisibility
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.ListableBeanFactory
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.convert.ApplicationConversionService
 *  org.springframework.context.annotation.Bean
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.support.ResourcePatternResolver
 *  org.springframework.core.log.LogMessage
 *  org.springframework.format.FormatterRegistry
 *  org.springframework.graphql.ExecutionGraphQlService
 *  org.springframework.graphql.data.method.annotation.support.AnnotatedControllerConfigurer
 *  org.springframework.graphql.execution.BatchLoaderRegistry
 *  org.springframework.graphql.execution.DataFetcherExceptionResolver
 *  org.springframework.graphql.execution.DataLoaderRegistrar
 *  org.springframework.graphql.execution.DefaultBatchLoaderRegistry
 *  org.springframework.graphql.execution.DefaultExecutionGraphQlService
 *  org.springframework.graphql.execution.GraphQlSource
 *  org.springframework.graphql.execution.GraphQlSource$SchemaResourceBuilder
 *  org.springframework.graphql.execution.RuntimeWiringConfigurer
 *  org.springframework.graphql.execution.SubscriptionExceptionResolver
 */
package org.springframework.boot.autoconfigure.graphql;

import graphql.GraphQL;
import graphql.execution.instrumentation.Instrumentation;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.visibility.GraphqlFieldVisibility;
import graphql.schema.visibility.NoIntrospectionGraphqlFieldVisibility;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.graphql.ConditionalOnGraphQlSchema;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.log.LogMessage;
import org.springframework.format.FormatterRegistry;
import org.springframework.graphql.ExecutionGraphQlService;
import org.springframework.graphql.data.method.annotation.support.AnnotatedControllerConfigurer;
import org.springframework.graphql.execution.BatchLoaderRegistry;
import org.springframework.graphql.execution.DataFetcherExceptionResolver;
import org.springframework.graphql.execution.DataLoaderRegistrar;
import org.springframework.graphql.execution.DefaultBatchLoaderRegistry;
import org.springframework.graphql.execution.DefaultExecutionGraphQlService;
import org.springframework.graphql.execution.GraphQlSource;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.graphql.execution.SubscriptionExceptionResolver;

@AutoConfiguration
@ConditionalOnClass(value={GraphQL.class, GraphQlSource.class})
@ConditionalOnGraphQlSchema
@EnableConfigurationProperties(value={GraphQlProperties.class})
public class GraphQlAutoConfiguration {
    private static final Log logger = LogFactory.getLog(GraphQlAutoConfiguration.class);
    private final ListableBeanFactory beanFactory;

    public GraphQlAutoConfiguration(ListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Bean
    @ConditionalOnMissingBean
    public GraphQlSource graphQlSource(ResourcePatternResolver resourcePatternResolver, GraphQlProperties properties, ObjectProvider<DataFetcherExceptionResolver> exceptionResolvers, ObjectProvider<SubscriptionExceptionResolver> subscriptionExceptionResolvers, ObjectProvider<Instrumentation> instrumentations, ObjectProvider<RuntimeWiringConfigurer> wiringConfigurers, ObjectProvider<GraphQlSourceBuilderCustomizer> sourceCustomizers) {
        String[] schemaLocations = properties.getSchema().getLocations();
        Resource[] schemaResources = this.resolveSchemaResources(resourcePatternResolver, schemaLocations, properties.getSchema().getFileExtensions());
        GraphQlSource.SchemaResourceBuilder builder = (GraphQlSource.SchemaResourceBuilder)((GraphQlSource.SchemaResourceBuilder)((GraphQlSource.SchemaResourceBuilder)GraphQlSource.schemaResourceBuilder().schemaResources(schemaResources).exceptionResolvers(this.toList(exceptionResolvers))).subscriptionExceptionResolvers(this.toList(subscriptionExceptionResolvers))).instrumentation(this.toList(instrumentations));
        if (!properties.getSchema().getIntrospection().isEnabled()) {
            builder.configureRuntimeWiring(this::enableIntrospection);
        }
        wiringConfigurers.orderedStream().forEach(arg_0 -> ((GraphQlSource.SchemaResourceBuilder)builder).configureRuntimeWiring(arg_0));
        sourceCustomizers.orderedStream().forEach(customizer -> customizer.customize(builder));
        return builder.build();
    }

    private RuntimeWiring.Builder enableIntrospection(RuntimeWiring.Builder wiring) {
        return wiring.fieldVisibility((GraphqlFieldVisibility)NoIntrospectionGraphqlFieldVisibility.NO_INTROSPECTION_FIELD_VISIBILITY);
    }

    private Resource[] resolveSchemaResources(ResourcePatternResolver resolver, String[] locations, String[] extensions) {
        ArrayList<Resource> resources = new ArrayList<Resource>();
        for (String location : locations) {
            for (String extension : extensions) {
                resources.addAll(this.resolveSchemaResources(resolver, location + "*" + extension));
            }
        }
        return resources.toArray(new Resource[0]);
    }

    private List<Resource> resolveSchemaResources(ResourcePatternResolver resolver, String pattern) {
        try {
            return Arrays.asList(resolver.getResources(pattern));
        }
        catch (IOException ex) {
            logger.debug((Object)LogMessage.format((String)"Could not resolve schema location: '%s'", (Object)pattern), (Throwable)ex);
            return Collections.emptyList();
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public BatchLoaderRegistry batchLoaderRegistry() {
        return new DefaultBatchLoaderRegistry();
    }

    @Bean
    @ConditionalOnMissingBean
    public ExecutionGraphQlService executionGraphQlService(GraphQlSource graphQlSource, BatchLoaderRegistry batchLoaderRegistry) {
        DefaultExecutionGraphQlService service = new DefaultExecutionGraphQlService(graphQlSource);
        service.addDataLoaderRegistrar((DataLoaderRegistrar)batchLoaderRegistry);
        return service;
    }

    @Bean
    @ConditionalOnMissingBean
    public AnnotatedControllerConfigurer annotatedControllerConfigurer() {
        AnnotatedControllerConfigurer controllerConfigurer = new AnnotatedControllerConfigurer();
        controllerConfigurer.addFormatterRegistrar(registry -> ApplicationConversionService.addBeans((FormatterRegistry)registry, (ListableBeanFactory)this.beanFactory));
        return controllerConfigurer;
    }

    private <T> List<T> toList(ObjectProvider<T> provider) {
        return provider.orderedStream().collect(Collectors.toList());
    }
}

