/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.BeanNameGenerator
 *  org.springframework.context.EnvironmentAware
 *  org.springframework.context.ResourceLoaderAware
 *  org.springframework.context.annotation.ImportBeanDefinitionRegistrar
 *  org.springframework.core.env.Environment
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.data.repository.config.AnnotationRepositoryConfigurationSource
 *  org.springframework.data.repository.config.BootstrapMode
 *  org.springframework.data.repository.config.RepositoryConfigurationDelegate
 *  org.springframework.data.repository.config.RepositoryConfigurationExtension
 *  org.springframework.data.repository.config.RepositoryConfigurationSource
 *  org.springframework.data.util.Streamable
 */
package org.springframework.boot.autoconfigure.data;

import java.lang.annotation.Annotation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.repository.config.AnnotationRepositoryConfigurationSource;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.data.repository.config.RepositoryConfigurationDelegate;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;
import org.springframework.data.repository.config.RepositoryConfigurationSource;
import org.springframework.data.util.Streamable;

public abstract class AbstractRepositoryConfigurationSourceSupport
implements ImportBeanDefinitionRegistrar,
BeanFactoryAware,
ResourceLoaderAware,
EnvironmentAware {
    private ResourceLoader resourceLoader;
    private BeanFactory beanFactory;
    private Environment environment;

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
        RepositoryConfigurationDelegate delegate = new RepositoryConfigurationDelegate((RepositoryConfigurationSource)this.getConfigurationSource(registry, importBeanNameGenerator), this.resourceLoader, this.environment);
        delegate.registerRepositoriesIn(registry, this.getRepositoryConfigurationExtension());
    }

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        this.registerBeanDefinitions(importingClassMetadata, registry, null);
    }

    private AnnotationRepositoryConfigurationSource getConfigurationSource(BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
        AnnotationMetadata metadata = AnnotationMetadata.introspect(this.getConfiguration());
        return new AutoConfiguredAnnotationRepositoryConfigurationSource(metadata, (Class)this.getAnnotation(), this.resourceLoader, this.environment, registry, importBeanNameGenerator){};
    }

    protected Streamable<String> getBasePackages() {
        return Streamable.of(AutoConfigurationPackages.get(this.beanFactory));
    }

    protected abstract Class<? extends Annotation> getAnnotation();

    protected abstract Class<?> getConfiguration();

    protected abstract RepositoryConfigurationExtension getRepositoryConfigurationExtension();

    protected BootstrapMode getBootstrapMode() {
        return BootstrapMode.DEFAULT;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    private class AutoConfiguredAnnotationRepositoryConfigurationSource
    extends AnnotationRepositoryConfigurationSource {
        AutoConfiguredAnnotationRepositoryConfigurationSource(AnnotationMetadata metadata, Class<? extends Annotation> annotation, ResourceLoader resourceLoader, Environment environment, BeanDefinitionRegistry registry, BeanNameGenerator generator) {
            super(metadata, annotation, resourceLoader, environment, registry, generator);
        }

        public Streamable<String> getBasePackages() {
            return AbstractRepositoryConfigurationSourceSupport.this.getBasePackages();
        }

        public BootstrapMode getBootstrapMode() {
            return AbstractRepositoryConfigurationSourceSupport.this.getBootstrapMode();
        }
    }
}

