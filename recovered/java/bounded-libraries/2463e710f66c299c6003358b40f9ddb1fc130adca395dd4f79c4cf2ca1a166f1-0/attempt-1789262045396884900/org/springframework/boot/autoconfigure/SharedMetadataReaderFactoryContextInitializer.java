/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.MutablePropertyValues
 *  org.springframework.beans.factory.BeanClassLoaderAware
 *  org.springframework.beans.factory.FactoryBean
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.BeanFactoryPostProcessor
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.beans.factory.config.RuntimeBeanReference
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor
 *  org.springframework.boot.type.classreading.ConcurrentReferenceCachingMetadataReaderFactory
 *  org.springframework.context.ApplicationContextInitializer
 *  org.springframework.context.ApplicationListener
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.annotation.ConfigurationClassPostProcessor
 *  org.springframework.context.event.ContextRefreshedEvent
 *  org.springframework.core.Ordered
 *  org.springframework.core.PriorityOrdered
 *  org.springframework.core.type.classreading.CachingMetadataReaderFactory
 *  org.springframework.core.type.classreading.MetadataReaderFactory
 */
package org.springframework.boot.autoconfigure;

import java.util.function.Supplier;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.type.classreading.ConcurrentReferenceCachingMetadataReaderFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;

class SharedMetadataReaderFactoryContextInitializer
implements ApplicationContextInitializer<ConfigurableApplicationContext>,
Ordered {
    public static final String BEAN_NAME = "org.springframework.boot.autoconfigure.internalCachingMetadataReaderFactory";

    SharedMetadataReaderFactoryContextInitializer() {
    }

    public void initialize(ConfigurableApplicationContext applicationContext) {
        CachingMetadataReaderFactoryPostProcessor postProcessor = new CachingMetadataReaderFactoryPostProcessor(applicationContext);
        applicationContext.addBeanFactoryPostProcessor((BeanFactoryPostProcessor)postProcessor);
    }

    public int getOrder() {
        return 0;
    }

    static class SharedMetadataReaderFactoryBean
    implements FactoryBean<ConcurrentReferenceCachingMetadataReaderFactory>,
    BeanClassLoaderAware,
    ApplicationListener<ContextRefreshedEvent> {
        private ConcurrentReferenceCachingMetadataReaderFactory metadataReaderFactory;

        SharedMetadataReaderFactoryBean() {
        }

        public void setBeanClassLoader(ClassLoader classLoader) {
            this.metadataReaderFactory = new ConcurrentReferenceCachingMetadataReaderFactory(classLoader);
        }

        public ConcurrentReferenceCachingMetadataReaderFactory getObject() throws Exception {
            return this.metadataReaderFactory;
        }

        public Class<?> getObjectType() {
            return CachingMetadataReaderFactory.class;
        }

        public boolean isSingleton() {
            return true;
        }

        public void onApplicationEvent(ContextRefreshedEvent event) {
            this.metadataReaderFactory.clearCache();
        }
    }

    static class ConfigurationClassPostProcessorCustomizingSupplier
    implements Supplier<Object> {
        private final ConfigurableApplicationContext context;
        private final Supplier<?> instanceSupplier;

        ConfigurationClassPostProcessorCustomizingSupplier(ConfigurableApplicationContext context, Supplier<?> instanceSupplier) {
            this.context = context;
            this.instanceSupplier = instanceSupplier;
        }

        @Override
        public Object get() {
            Object instance = this.instanceSupplier.get();
            if (instance instanceof ConfigurationClassPostProcessor) {
                this.configureConfigurationClassPostProcessor((ConfigurationClassPostProcessor)instance);
            }
            return instance;
        }

        private void configureConfigurationClassPostProcessor(ConfigurationClassPostProcessor instance) {
            instance.setMetadataReaderFactory((MetadataReaderFactory)this.context.getBean(SharedMetadataReaderFactoryContextInitializer.BEAN_NAME, MetadataReaderFactory.class));
        }
    }

    static class CachingMetadataReaderFactoryPostProcessor
    implements BeanDefinitionRegistryPostProcessor,
    PriorityOrdered {
        private final ConfigurableApplicationContext context;

        CachingMetadataReaderFactoryPostProcessor(ConfigurableApplicationContext context) {
            this.context = context;
        }

        public int getOrder() {
            return Integer.MIN_VALUE;
        }

        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        }

        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
            this.register(registry);
            this.configureConfigurationClassPostProcessor(registry);
        }

        private void register(BeanDefinitionRegistry registry) {
            if (!registry.containsBeanDefinition(SharedMetadataReaderFactoryContextInitializer.BEAN_NAME)) {
                AbstractBeanDefinition definition = BeanDefinitionBuilder.rootBeanDefinition(SharedMetadataReaderFactoryBean.class, SharedMetadataReaderFactoryBean::new).getBeanDefinition();
                registry.registerBeanDefinition(SharedMetadataReaderFactoryContextInitializer.BEAN_NAME, (BeanDefinition)definition);
            }
        }

        private void configureConfigurationClassPostProcessor(BeanDefinitionRegistry registry) {
            try {
                this.configureConfigurationClassPostProcessor(registry.getBeanDefinition("org.springframework.context.annotation.internalConfigurationAnnotationProcessor"));
            }
            catch (NoSuchBeanDefinitionException noSuchBeanDefinitionException) {
                // empty catch block
            }
        }

        private void configureConfigurationClassPostProcessor(BeanDefinition definition) {
            if (definition instanceof AbstractBeanDefinition) {
                this.configureConfigurationClassPostProcessor((AbstractBeanDefinition)definition);
                return;
            }
            this.configureConfigurationClassPostProcessor(definition.getPropertyValues());
        }

        private void configureConfigurationClassPostProcessor(AbstractBeanDefinition definition) {
            Supplier instanceSupplier = definition.getInstanceSupplier();
            if (instanceSupplier != null) {
                definition.setInstanceSupplier((Supplier)new ConfigurationClassPostProcessorCustomizingSupplier(this.context, instanceSupplier));
                return;
            }
            this.configureConfigurationClassPostProcessor(definition.getPropertyValues());
        }

        private void configureConfigurationClassPostProcessor(MutablePropertyValues propertyValues) {
            propertyValues.add("metadataReaderFactory", (Object)new RuntimeBeanReference(SharedMetadataReaderFactoryContextInitializer.BEAN_NAME));
        }
    }
}

