/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.RootBeanDefinition
 */
package org.springframework.integration.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.integration.config.ConverterRegistrar;
import org.springframework.integration.config.CustomConversionServiceFactoryBean;
import org.springframework.integration.config.IntegrationConfigurationInitializer;

public class IntegrationConverterInitializer
implements IntegrationConfigurationInitializer {
    @Override
    public void initialize(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry)beanFactory;
        if (!registry.containsBeanDefinition("converterRegistrar")) {
            registry.registerBeanDefinition("converterRegistrar", (BeanDefinition)new RootBeanDefinition(ConverterRegistrar.class, ConverterRegistrar::new));
        }
        if (!registry.containsBeanDefinition("integrationConversionService")) {
            registry.registerBeanDefinition("integrationConversionService", (BeanDefinition)new RootBeanDefinition(CustomConversionServiceFactoryBean.class, CustomConversionServiceFactoryBean::new));
        }
    }
}

