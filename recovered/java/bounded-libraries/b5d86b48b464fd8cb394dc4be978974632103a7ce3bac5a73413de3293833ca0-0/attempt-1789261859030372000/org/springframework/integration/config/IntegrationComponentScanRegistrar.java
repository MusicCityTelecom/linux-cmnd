/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanUtils
 *  org.springframework.beans.factory.Aware
 *  org.springframework.beans.factory.BeanClassLoaderAware
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.annotation.AnnotatedBeanDefinition
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.ConfigurableBeanFactory
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.context.EnvironmentAware
 *  org.springframework.context.ResourceLoaderAware
 *  org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
 *  org.springframework.context.annotation.FilterType
 *  org.springframework.context.annotation.ImportBeanDefinitionRegistrar
 *  org.springframework.core.annotation.AnnotationAttributes
 *  org.springframework.core.env.Environment
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.core.type.filter.AnnotationTypeFilter
 *  org.springframework.core.type.filter.AspectJTypeFilter
 *  org.springframework.core.type.filter.AssignableTypeFilter
 *  org.springframework.core.type.filter.RegexPatternTypeFilter
 *  org.springframework.core.type.filter.TypeFilter
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AspectJTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.config.MessagingGatewayRegistrar;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

public class IntegrationComponentScanRegistrar
implements ImportBeanDefinitionRegistrar,
ResourceLoaderAware,
EnvironmentAware {
    private final Map<TypeFilter, ImportBeanDefinitionRegistrar> componentRegistrars = new HashMap<TypeFilter, ImportBeanDefinitionRegistrar>();
    private ResourceLoader resourceLoader;
    private Environment environment;

    public IntegrationComponentScanRegistrar() {
        this.componentRegistrars.put((TypeFilter)new AnnotationTypeFilter(MessagingGateway.class, true), new MessagingGatewayRegistrar());
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, final BeanDefinitionRegistry registry) {
        Map componentScan = importingClassMetadata.getAnnotationAttributes(IntegrationComponentScan.class.getName());
        Collection<String> basePackages = this.getBasePackages(importingClassMetadata, registry);
        if (basePackages.isEmpty()) {
            basePackages = Collections.singleton(ClassUtils.getPackageName((String)importingClassMetadata.getClassName()));
        }
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false, this.environment){

            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                return beanDefinition.getMetadata().isIndependent() && !beanDefinition.getMetadata().isAnnotation();
            }

            protected BeanDefinitionRegistry getRegistry() {
                return registry;
            }
        };
        this.filter(registry, componentScan, scanner);
        scanner.setResourceLoader(this.resourceLoader);
        for (String basePackage : basePackages) {
            Set candidateComponents = scanner.findCandidateComponents(basePackage);
            for (BeanDefinition candidateComponent : candidateComponents) {
                if (!(candidateComponent instanceof AnnotatedBeanDefinition)) continue;
                for (ImportBeanDefinitionRegistrar registrar : this.componentRegistrars.values()) {
                    registrar.registerBeanDefinitions(((AnnotatedBeanDefinition)candidateComponent).getMetadata(), registry);
                }
            }
        }
    }

    private void filter(BeanDefinitionRegistry registry, Map<String, Object> componentScan, ClassPathScanningCandidateComponentProvider scanner) {
        if (((Boolean)componentScan.get("useDefaultFilters")).booleanValue()) {
            for (TypeFilter typeFilter : this.componentRegistrars.keySet()) {
                scanner.addIncludeFilter(typeFilter);
            }
        }
        for (AnnotationAttributes filter2 : (AnnotationAttributes[])componentScan.get("includeFilters")) {
            for (TypeFilter typeFilter : this.typeFiltersFor(filter2, registry)) {
                scanner.addIncludeFilter(typeFilter);
            }
        }
        for (AnnotationAttributes filter2 : (AnnotationAttributes[])componentScan.get("excludeFilters")) {
            for (TypeFilter typeFilter : this.typeFiltersFor(filter2, registry)) {
                scanner.addExcludeFilter(typeFilter);
            }
        }
    }

    protected Collection<String> getBasePackages(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map componentScan = importingClassMetadata.getAnnotationAttributes(IntegrationComponentScan.class.getName());
        HashSet<String> basePackages = new HashSet<String>();
        for (String pkg : (String[])componentScan.get("value")) {
            if (!StringUtils.hasText((String)pkg)) continue;
            basePackages.add(pkg);
        }
        for (Class clazz : (Class[])componentScan.get("basePackageClasses")) {
            basePackages.add(ClassUtils.getPackageName((Class)clazz));
        }
        return basePackages;
    }

    private List<TypeFilter> typeFiltersFor(AnnotationAttributes filter2, BeanDefinitionRegistry registry) {
        ArrayList<TypeFilter> typeFilters = new ArrayList<TypeFilter>();
        FilterType filterType = (FilterType)filter2.getEnum("type");
        block9: for (Class filterClass : filter2.getClassArray("classes")) {
            switch (filterType) {
                case ANNOTATION: {
                    Assert.isAssignable(Annotation.class, (Class)filterClass, (String)"An error occurred while processing a @IntegrationComponentScan ANNOTATION type filter: ");
                    Class annotationType = filterClass;
                    typeFilters.add((TypeFilter)new AnnotationTypeFilter(annotationType));
                    continue block9;
                }
                case ASSIGNABLE_TYPE: {
                    typeFilters.add((TypeFilter)new AssignableTypeFilter(filterClass));
                    continue block9;
                }
                case CUSTOM: {
                    Assert.isAssignable(TypeFilter.class, (Class)filterClass, (String)"An error occurred while processing a @IntegrationComponentScan CUSTOM type filter: ");
                    TypeFilter typeFilter = (TypeFilter)BeanUtils.instantiateClass((Class)filterClass, TypeFilter.class);
                    IntegrationComponentScanRegistrar.invokeAwareMethods(filter2, this.environment, this.resourceLoader, registry);
                    typeFilters.add(typeFilter);
                    continue block9;
                }
                default: {
                    throw new IllegalArgumentException("Filter type not supported with Class value: " + filterType);
                }
            }
        }
        block10: for (String expression : filter2.getStringArray("pattern")) {
            switch (filterType) {
                case ASPECTJ: {
                    typeFilters.add((TypeFilter)new AspectJTypeFilter(expression, this.resourceLoader.getClassLoader()));
                    continue block10;
                }
                case REGEX: {
                    typeFilters.add((TypeFilter)new RegexPatternTypeFilter(Pattern.compile(expression)));
                    continue block10;
                }
                default: {
                    throw new IllegalArgumentException("Filter type not supported with String pattern: " + filterType);
                }
            }
        }
        return typeFilters;
    }

    private static void invokeAwareMethods(Object parserStrategyBean, Environment environment, ResourceLoader resourceLoader, BeanDefinitionRegistry registry) {
        if (parserStrategyBean instanceof Aware) {
            if (parserStrategyBean instanceof BeanClassLoaderAware) {
                ClassLoader classLoader;
                ClassLoader classLoader2 = classLoader = registry instanceof ConfigurableBeanFactory ? ((ConfigurableBeanFactory)registry).getBeanClassLoader() : resourceLoader.getClassLoader();
                if (classLoader != null) {
                    ((BeanClassLoaderAware)parserStrategyBean).setBeanClassLoader(classLoader);
                }
            }
            if (parserStrategyBean instanceof BeanFactoryAware && registry instanceof BeanFactory) {
                ((BeanFactoryAware)parserStrategyBean).setBeanFactory((BeanFactory)registry);
            }
            if (parserStrategyBean instanceof EnvironmentAware) {
                ((EnvironmentAware)parserStrategyBean).setEnvironment(environment);
            }
            if (parserStrategyBean instanceof ResourceLoaderAware) {
                ((ResourceLoaderAware)parserStrategyBean).setResourceLoader(resourceLoader);
            }
        }
    }
}

