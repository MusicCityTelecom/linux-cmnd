/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.boot.context.properties.bind.Binder
 *  org.springframework.context.annotation.ConditionContext
 *  org.springframework.context.annotation.ConfigurationCondition
 *  org.springframework.context.annotation.ConfigurationCondition$ConfigurationPhase
 *  org.springframework.core.env.Environment
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.io.support.ResourcePatternResolver
 *  org.springframework.core.io.support.ResourcePatternUtils
 *  org.springframework.core.type.AnnotatedTypeMetadata
 */
package org.springframework.boot.autoconfigure.graphql;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.autoconfigure.graphql.ConditionalOnGraphQlSchema;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.AnnotatedTypeMetadata;

class DefaultGraphQlSchemaCondition
extends SpringBootCondition
implements ConfigurationCondition {
    DefaultGraphQlSchemaCondition() {
    }

    public ConfigurationCondition.ConfigurationPhase getConfigurationPhase() {
        return ConfigurationCondition.ConfigurationPhase.REGISTER_BEAN;
    }

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        boolean match = false;
        ArrayList<ConditionMessage> messages = new ArrayList<ConditionMessage>(2);
        ConditionMessage.Builder message = ConditionMessage.forCondition(ConditionalOnGraphQlSchema.class, new Object[0]);
        Binder binder = Binder.get((Environment)context.getEnvironment());
        GraphQlProperties.Schema schema = (GraphQlProperties.Schema)binder.bind("spring.graphql.schema", GraphQlProperties.Schema.class).orElse((Object)new GraphQlProperties.Schema());
        ResourcePatternResolver resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver((ResourceLoader)context.getResourceLoader());
        List<Resource> schemaResources = this.resolveSchemaResources(resourcePatternResolver, schema.getLocations(), schema.getFileExtensions());
        if (!schemaResources.isEmpty()) {
            match = true;
            messages.add(message.found("schema", "schemas").items(ConditionMessage.Style.QUOTE, schemaResources));
        } else {
            messages.add(message.didNotFind("schema files in locations").items(ConditionMessage.Style.QUOTE, Arrays.asList(schema.getLocations())));
        }
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        String[] customizerBeans = beanFactory.getBeanNamesForType(GraphQlSourceBuilderCustomizer.class, false, false);
        if (customizerBeans.length != 0) {
            match = true;
            messages.add(message.found("customizer", "customizers").items(Arrays.asList(customizerBeans)));
        } else {
            messages.add(message.didNotFind("GraphQlSourceBuilderCustomizer").atAll());
        }
        return new ConditionOutcome(match, ConditionMessage.of(messages));
    }

    private List<Resource> resolveSchemaResources(ResourcePatternResolver resolver, String[] locations, String[] extensions) {
        ArrayList<Resource> resources = new ArrayList<Resource>();
        for (String location : locations) {
            for (String extension : extensions) {
                resources.addAll(this.resolveSchemaResources(resolver, location + "*" + extension));
            }
        }
        return resources;
    }

    private List<Resource> resolveSchemaResources(ResourcePatternResolver resolver, String pattern) {
        try {
            return Arrays.asList(resolver.getResources(pattern));
        }
        catch (IOException ex) {
            return Collections.emptyList();
        }
    }
}

