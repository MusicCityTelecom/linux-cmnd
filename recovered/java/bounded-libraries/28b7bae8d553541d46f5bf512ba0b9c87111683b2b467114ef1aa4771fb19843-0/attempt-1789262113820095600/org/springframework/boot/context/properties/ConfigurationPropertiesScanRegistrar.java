/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
 *  org.springframework.context.annotation.ImportBeanDefinitionRegistrar
 *  org.springframework.core.annotation.AnnotationAttributes
 *  org.springframework.core.annotation.MergedAnnotations
 *  org.springframework.core.annotation.MergedAnnotations$SearchStrategy
 *  org.springframework.core.env.Environment
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.core.type.filter.AnnotationTypeFilter
 *  org.springframework.core.type.filter.TypeFilter
 *  org.springframework.stereotype.Component
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.context.properties;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBeanRegistrar;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

class ConfigurationPropertiesScanRegistrar
implements ImportBeanDefinitionRegistrar {
    private final Environment environment;
    private final ResourceLoader resourceLoader;

    ConfigurationPropertiesScanRegistrar(Environment environment, ResourceLoader resourceLoader) {
        this.environment = environment;
        this.resourceLoader = resourceLoader;
    }

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Set<String> packagesToScan = this.getPackagesToScan(importingClassMetadata);
        this.scan(registry, packagesToScan);
    }

    private Set<String> getPackagesToScan(AnnotationMetadata metadata) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap((Map)metadata.getAnnotationAttributes(ConfigurationPropertiesScan.class.getName()));
        String[] basePackages = attributes.getStringArray("basePackages");
        Class[] basePackageClasses = attributes.getClassArray("basePackageClasses");
        LinkedHashSet<String> packagesToScan = new LinkedHashSet<String>(Arrays.asList(basePackages));
        for (Class basePackageClass : basePackageClasses) {
            packagesToScan.add(ClassUtils.getPackageName((Class)basePackageClass));
        }
        if (packagesToScan.isEmpty()) {
            packagesToScan.add(ClassUtils.getPackageName((String)metadata.getClassName()));
        }
        packagesToScan.removeIf(candidate -> !StringUtils.hasText((String)candidate));
        return packagesToScan;
    }

    private void scan(BeanDefinitionRegistry registry, Set<String> packages) {
        ConfigurationPropertiesBeanRegistrar registrar = new ConfigurationPropertiesBeanRegistrar(registry);
        ClassPathScanningCandidateComponentProvider scanner = this.getScanner(registry);
        for (String basePackage : packages) {
            for (BeanDefinition candidate : scanner.findCandidateComponents(basePackage)) {
                this.register(registrar, candidate.getBeanClassName());
            }
        }
    }

    private ClassPathScanningCandidateComponentProvider getScanner(BeanDefinitionRegistry registry) {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.setEnvironment(this.environment);
        scanner.setResourceLoader(this.resourceLoader);
        scanner.addIncludeFilter((TypeFilter)new AnnotationTypeFilter(ConfigurationProperties.class));
        TypeExcludeFilter typeExcludeFilter = new TypeExcludeFilter();
        typeExcludeFilter.setBeanFactory((BeanFactory)registry);
        scanner.addExcludeFilter((TypeFilter)typeExcludeFilter);
        return scanner;
    }

    private void register(ConfigurationPropertiesBeanRegistrar registrar, String className) throws LinkageError {
        try {
            this.register(registrar, ClassUtils.forName((String)className, null));
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
    }

    private void register(ConfigurationPropertiesBeanRegistrar registrar, Class<?> type) {
        if (!this.isComponent(type)) {
            registrar.register(type);
        }
    }

    private boolean isComponent(Class<?> type) {
        return MergedAnnotations.from(type, (MergedAnnotations.SearchStrategy)MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).isPresent(Component.class);
    }
}

