/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.integration.annotation.IntegrationComponentScan
 *  org.springframework.integration.config.IntegrationComponentScanRegistrar
 */
package org.springframework.boot.autoconfigure.integration;

import java.util.Collection;
import java.util.Collections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.IntegrationComponentScanRegistrar;

class IntegrationAutoConfigurationScanRegistrar
extends IntegrationComponentScanRegistrar
implements BeanFactoryAware {
    private BeanFactory beanFactory;

    IntegrationAutoConfigurationScanRegistrar() {
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        super.registerBeanDefinitions(AnnotationMetadata.introspect(IntegrationComponentScanConfiguration.class), registry);
    }

    protected Collection<String> getBasePackages(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        return AutoConfigurationPackages.has(this.beanFactory) ? AutoConfigurationPackages.get(this.beanFactory) : Collections.emptyList();
    }

    @IntegrationComponentScan
    private static class IntegrationComponentScanConfiguration {
        private IntegrationComponentScanConfiguration() {
        }
    }
}

