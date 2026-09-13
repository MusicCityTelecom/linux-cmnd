/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanDefinitionStoreException
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.context.annotation.ImportBeanDefinitionRegistrar
 *  org.springframework.core.type.AnnotationMetadata
 */
package org.springframework.integration.config;

import java.util.Map;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.integration.config.EnableMessageHistory;
import org.springframework.integration.history.MessageHistoryConfigurer;

public class MessageHistoryRegistrar
implements ImportBeanDefinitionRegistrar {
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        if (registry.containsBeanDefinition("messageHistoryConfigurer")) {
            throw new BeanDefinitionStoreException("Only one @EnableMessageHistory or <message-history/> can be declared in the application context.");
        }
        Map annotationAttributes = importingClassMetadata.getAnnotationAttributes(EnableMessageHistory.class.getName());
        Object componentNamePatterns = annotationAttributes.get("value");
        String patterns = componentNamePatterns instanceof String[] ? String.join((CharSequence)",", (String[])componentNamePatterns) : (String)componentNamePatterns;
        AbstractBeanDefinition messageHistoryConfigurer = BeanDefinitionBuilder.genericBeanDefinition(MessageHistoryConfigurer.class, MessageHistoryConfigurer::new).addPropertyValue("componentNamePatterns", (Object)patterns).getBeanDefinition();
        registry.registerBeanDefinition("messageHistoryConfigurer", (BeanDefinition)messageHistoryConfigurer);
    }
}

