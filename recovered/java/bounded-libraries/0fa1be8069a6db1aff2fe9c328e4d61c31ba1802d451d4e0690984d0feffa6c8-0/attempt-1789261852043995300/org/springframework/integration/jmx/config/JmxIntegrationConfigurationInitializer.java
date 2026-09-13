/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.RootBeanDefinition
 *  org.springframework.integration.config.IntegrationConfigurationInitializer
 */
package org.springframework.integration.jmx.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.integration.config.IntegrationConfigurationInitializer;
import org.springframework.integration.jmx.config.MBeanExporterHelper;
import org.springframework.integration.monitor.IntegrationMBeanExporter;

public class JmxIntegrationConfigurationInitializer
implements IntegrationConfigurationInitializer {
    private static final String MBEAN_EXPORTER_HELPER_BEAN_NAME = MBeanExporterHelper.class.getName();

    public void initialize(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        JmxIntegrationConfigurationInitializer.registerMBeanExporterHelperIfNecessary(beanFactory);
    }

    private static void registerMBeanExporterHelperIfNecessary(ConfigurableListableBeanFactory beanFactory) {
        if (!beanFactory.containsBean(MBEAN_EXPORTER_HELPER_BEAN_NAME) && beanFactory.getBeanNamesForType(IntegrationMBeanExporter.class, false, false).length > 0) {
            ((BeanDefinitionRegistry)beanFactory).registerBeanDefinition(MBEAN_EXPORTER_HELPER_BEAN_NAME, (BeanDefinition)new RootBeanDefinition(MBeanExporterHelper.class, MBeanExporterHelper::new));
        }
    }
}

