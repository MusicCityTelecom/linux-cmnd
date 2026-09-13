/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.context.annotation.ImportBeanDefinitionRegistrar
 *  org.springframework.core.Conventions
 *  org.springframework.core.type.AnnotationMetadata
 */
package org.springframework.boot.context.properties;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.BoundConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBeanRegistrar;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.validation.beanvalidation.MethodValidationExcludeFilter;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.Conventions;
import org.springframework.core.type.AnnotationMetadata;

class EnableConfigurationPropertiesRegistrar
implements ImportBeanDefinitionRegistrar {
    private static final String METHOD_VALIDATION_EXCLUDE_FILTER_BEAN_NAME = Conventions.getQualifiedAttributeName(EnableConfigurationPropertiesRegistrar.class, (String)"methodValidationExcludeFilter");

    EnableConfigurationPropertiesRegistrar() {
    }

    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        EnableConfigurationPropertiesRegistrar.registerInfrastructureBeans(registry);
        EnableConfigurationPropertiesRegistrar.registerMethodValidationExcludeFilter(registry);
        ConfigurationPropertiesBeanRegistrar beanRegistrar = new ConfigurationPropertiesBeanRegistrar(registry);
        this.getTypes(metadata).forEach(beanRegistrar::register);
    }

    private Set<Class<?>> getTypes(AnnotationMetadata metadata) {
        return metadata.getAnnotations().stream(EnableConfigurationProperties.class).flatMap(annotation -> Arrays.stream(annotation.getClassArray("value"))).filter(type -> Void.TYPE != type).collect(Collectors.toSet());
    }

    static void registerInfrastructureBeans(BeanDefinitionRegistry registry) {
        ConfigurationPropertiesBindingPostProcessor.register(registry);
        BoundConfigurationProperties.register(registry);
    }

    static void registerMethodValidationExcludeFilter(BeanDefinitionRegistry registry) {
        if (!registry.containsBeanDefinition(METHOD_VALIDATION_EXCLUDE_FILTER_BEAN_NAME)) {
            AbstractBeanDefinition definition = BeanDefinitionBuilder.genericBeanDefinition(MethodValidationExcludeFilter.class, () -> MethodValidationExcludeFilter.byAnnotation(ConfigurationProperties.class)).setRole(2).getBeanDefinition();
            registry.registerBeanDefinition(METHOD_VALIDATION_EXCLUDE_FILTER_BEAN_NAME, (BeanDefinition)definition);
        }
    }
}

