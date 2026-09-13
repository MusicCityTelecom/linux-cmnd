/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.GenericBeanDefinition
 *  org.springframework.context.annotation.ImportBeanDefinitionRegistrar
 *  org.springframework.core.annotation.AnnotationAttributes
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.web.servlet;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.web.servlet.ServletComponentRegisteringPostProcessor;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

class ServletComponentScanRegistrar
implements ImportBeanDefinitionRegistrar {
    private static final String BEAN_NAME = "servletComponentRegisteringPostProcessor";

    ServletComponentScanRegistrar() {
    }

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Set<String> packagesToScan = this.getPackagesToScan(importingClassMetadata);
        if (registry.containsBeanDefinition(BEAN_NAME)) {
            this.updatePostProcessor(registry, packagesToScan);
        } else {
            this.addPostProcessor(registry, packagesToScan);
        }
    }

    private void updatePostProcessor(BeanDefinitionRegistry registry, Set<String> packagesToScan) {
        ServletComponentRegisteringPostProcessorBeanDefinition definition = (ServletComponentRegisteringPostProcessorBeanDefinition)registry.getBeanDefinition(BEAN_NAME);
        definition.addPackageNames(packagesToScan);
    }

    private void addPostProcessor(BeanDefinitionRegistry registry, Set<String> packagesToScan) {
        ServletComponentRegisteringPostProcessorBeanDefinition definition = new ServletComponentRegisteringPostProcessorBeanDefinition(packagesToScan);
        registry.registerBeanDefinition(BEAN_NAME, (BeanDefinition)definition);
    }

    private Set<String> getPackagesToScan(AnnotationMetadata metadata) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap((Map)metadata.getAnnotationAttributes(ServletComponentScan.class.getName()));
        String[] basePackages = attributes.getStringArray("basePackages");
        Class[] basePackageClasses = attributes.getClassArray("basePackageClasses");
        LinkedHashSet<String> packagesToScan = new LinkedHashSet<String>(Arrays.asList(basePackages));
        for (Class basePackageClass : basePackageClasses) {
            packagesToScan.add(ClassUtils.getPackageName((Class)basePackageClass));
        }
        if (packagesToScan.isEmpty()) {
            packagesToScan.add(ClassUtils.getPackageName((String)metadata.getClassName()));
        }
        return packagesToScan;
    }

    static final class ServletComponentRegisteringPostProcessorBeanDefinition
    extends GenericBeanDefinition {
        private Set<String> packageNames = new LinkedHashSet<String>();

        ServletComponentRegisteringPostProcessorBeanDefinition(Collection<String> packageNames) {
            this.setBeanClass(ServletComponentRegisteringPostProcessor.class);
            this.setRole(2);
            this.addPackageNames(packageNames);
        }

        public Supplier<?> getInstanceSupplier() {
            return () -> new ServletComponentRegisteringPostProcessor(this.packageNames);
        }

        private void addPackageNames(Collection<String> additionalPackageNames) {
            this.packageNames.addAll(additionalPackageNames);
        }
    }
}

