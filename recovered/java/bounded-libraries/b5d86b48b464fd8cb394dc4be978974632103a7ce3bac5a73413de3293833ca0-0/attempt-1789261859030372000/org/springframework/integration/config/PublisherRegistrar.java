/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanDefinitionStoreException
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.context.annotation.ImportBeanDefinitionRegistrar
 *  org.springframework.core.annotation.AnnotationUtils
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config;

import java.util.Map;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.integration.aop.PublisherAnnotationBeanPostProcessor;
import org.springframework.integration.config.EnablePublisher;
import org.springframework.util.StringUtils;

public class PublisherRegistrar
implements ImportBeanDefinitionRegistrar {
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        if (registry.containsBeanDefinition("org.springframework.integration.internalPublisherAnnotationBeanPostProcessor")) {
            throw new BeanDefinitionStoreException("Only one enable publisher definition (@EnablePublisher or <annotation-config>) can be declared in the application context.");
        }
        Map annotationAttributes = importingClassMetadata.getAnnotationAttributes(EnablePublisher.class.getName());
        String defaultChannel = annotationAttributes == null ? (String)AnnotationUtils.getDefaultValue(EnablePublisher.class) : (String)annotationAttributes.get("defaultChannel");
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(PublisherAnnotationBeanPostProcessor.class, PublisherAnnotationBeanPostProcessor::new).setRole(2);
        if (StringUtils.hasText((String)defaultChannel)) {
            builder.addPropertyValue("defaultChannelName", (Object)defaultChannel);
        }
        if (annotationAttributes != null) {
            builder.addPropertyValue("proxyTargetClass", annotationAttributes.get("proxyTargetClass")).addPropertyValue("order", annotationAttributes.get("order"));
        }
        registry.registerBeanDefinition("org.springframework.integration.internalPublisherAnnotationBeanPostProcessor", (BeanDefinition)builder.getBeanDefinition());
    }
}

