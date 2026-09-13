/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.context.ApplicationContextException
 *  org.springframework.context.annotation.ImportBeanDefinitionRegistrar
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.lang.Nullable
 *  org.springframework.util.ClassUtils
 */
package org.springframework.integration.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.integration.config.DefaultConfiguringBeanFactoryPostProcessor;
import org.springframework.integration.config.IntegrationConfigurationBeanFactoryPostProcessor;
import org.springframework.integration.config.annotation.MessagingAnnotationPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

public class IntegrationRegistrar
implements ImportBeanDefinitionRegistrar {
    public void registerBeanDefinitions(@Nullable AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        this.registerDefaultConfiguringBeanFactoryPostProcessor(registry);
        this.registerIntegrationConfigurationBeanFactoryPostProcessor(registry);
        if (importingClassMetadata != null) {
            this.registerMessagingAnnotationPostProcessors(registry);
        }
    }

    private void registerDefaultConfiguringBeanFactoryPostProcessor(BeanDefinitionRegistry registry) {
        if (!registry.containsBeanDefinition("DefaultConfiguringBeanFactoryPostProcessor")) {
            BeanDefinitionBuilder postProcessorBuilder = BeanDefinitionBuilder.genericBeanDefinition(DefaultConfiguringBeanFactoryPostProcessor.class, DefaultConfiguringBeanFactoryPostProcessor::new).setRole(2);
            registry.registerBeanDefinition("DefaultConfiguringBeanFactoryPostProcessor", (BeanDefinition)postProcessorBuilder.getBeanDefinition());
        }
    }

    private void registerIntegrationConfigurationBeanFactoryPostProcessor(BeanDefinitionRegistry registry) {
        if (!registry.containsBeanDefinition("integrationConfigurationBeanFactoryPostProcessor")) {
            BeanDefinitionBuilder postProcessorBuilder = BeanDefinitionBuilder.genericBeanDefinition(IntegrationConfigurationBeanFactoryPostProcessor.class, IntegrationConfigurationBeanFactoryPostProcessor::new).setRole(2);
            registry.registerBeanDefinition("integrationConfigurationBeanFactoryPostProcessor", (BeanDefinition)postProcessorBuilder.getBeanDefinition());
        }
    }

    private void registerMessagingAnnotationPostProcessors(BeanDefinitionRegistry registry) {
        if (!registry.containsBeanDefinition("org.springframework.integration.internalMessagingAnnotationPostProcessor")) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MessagingAnnotationPostProcessor.class, MessagingAnnotationPostProcessor::new).setRole(2);
            registry.registerBeanDefinition("org.springframework.integration.internalMessagingAnnotationPostProcessor", (BeanDefinition)builder.getBeanDefinition());
        }
    }

    static {
        if (ClassUtils.isPresent((String)"org.springframework.integration.dsl.support.Function", null)) {
            throw new ApplicationContextException("Starting with Spring Integration 5.0, the 'spring-integration-java-dsl' dependency is no longer needed; the Java DSL has been merged into the core project. If it is present on the classpath, it will cause class loading conflicts.");
        }
    }
}

