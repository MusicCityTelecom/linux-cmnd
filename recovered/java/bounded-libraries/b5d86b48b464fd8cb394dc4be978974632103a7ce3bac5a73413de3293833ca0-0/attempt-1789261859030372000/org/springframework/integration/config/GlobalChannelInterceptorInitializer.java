/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.annotation.AnnotatedBeanDefinition
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionReaderUtils
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.core.type.MethodMetadata
 *  org.springframework.messaging.support.ChannelInterceptor
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.integration.config;

import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.integration.channel.interceptor.GlobalChannelInterceptorWrapper;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.integration.config.IntegrationConfigurationInitializer;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.util.CollectionUtils;

public class GlobalChannelInterceptorInitializer
implements IntegrationConfigurationInitializer {
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void initialize(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry)beanFactory;
        for (String beanName : registry.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(beanName);
            if (!(beanDefinition instanceof AnnotatedBeanDefinition)) continue;
            AnnotationMetadata metadata = ((AnnotatedBeanDefinition)beanDefinition).getMetadata();
            Map annotationAttributes = metadata.getAnnotationAttributes(GlobalChannelInterceptor.class.getName());
            if (CollectionUtils.isEmpty((Map)annotationAttributes) && beanDefinition.getSource() instanceof MethodMetadata) {
                MethodMetadata beanMethod = (MethodMetadata)beanDefinition.getSource();
                annotationAttributes = beanMethod.getAnnotationAttributes(GlobalChannelInterceptor.class.getName());
            }
            if (CollectionUtils.isEmpty((Map)annotationAttributes)) continue;
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(GlobalChannelInterceptorWrapper.class, () -> this.createGlobalChannelInterceptorWrapper(beanName)).addConstructorArgReference(beanName).addPropertyValue("patterns", annotationAttributes.get("patterns")).addPropertyValue("order", annotationAttributes.get("order"));
            BeanDefinitionReaderUtils.registerWithGeneratedName((AbstractBeanDefinition)builder.getBeanDefinition(), (BeanDefinitionRegistry)registry);
        }
    }

    private GlobalChannelInterceptorWrapper createGlobalChannelInterceptorWrapper(String interceptorBeanName) {
        ChannelInterceptor interceptor = (ChannelInterceptor)this.beanFactory.getBean(interceptorBeanName, ChannelInterceptor.class);
        return new GlobalChannelInterceptorWrapper(interceptor);
    }
}

