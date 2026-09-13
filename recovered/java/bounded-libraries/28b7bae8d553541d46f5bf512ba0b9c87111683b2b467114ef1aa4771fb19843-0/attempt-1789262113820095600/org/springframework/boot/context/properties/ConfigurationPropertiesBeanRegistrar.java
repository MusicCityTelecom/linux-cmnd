/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.HierarchicalBeanFactory
 *  org.springframework.beans.factory.ListableBeanFactory
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.RootBeanDefinition
 *  org.springframework.core.annotation.MergedAnnotation
 *  org.springframework.core.annotation.MergedAnnotations
 *  org.springframework.core.annotation.MergedAnnotations$SearchStrategy
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.context.properties;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBean;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindException;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinder;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

final class ConfigurationPropertiesBeanRegistrar {
    private final BeanDefinitionRegistry registry;
    private final BeanFactory beanFactory;

    ConfigurationPropertiesBeanRegistrar(BeanDefinitionRegistry registry) {
        this.registry = registry;
        this.beanFactory = (BeanFactory)this.registry;
    }

    void register(Class<?> type) {
        MergedAnnotation annotation = MergedAnnotations.from(type, (MergedAnnotations.SearchStrategy)MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).get(ConfigurationProperties.class);
        this.register(type, (MergedAnnotation<ConfigurationProperties>)annotation);
    }

    void register(Class<?> type, MergedAnnotation<ConfigurationProperties> annotation) {
        String name = this.getName(type, annotation);
        if (!this.containsBeanDefinition(name)) {
            this.registerBeanDefinition(name, type, annotation);
        }
    }

    private String getName(Class<?> type, MergedAnnotation<ConfigurationProperties> annotation) {
        String prefix = annotation.isPresent() ? annotation.getString("prefix") : "";
        return StringUtils.hasText((String)prefix) ? prefix + "-" + type.getName() : type.getName();
    }

    private boolean containsBeanDefinition(String name) {
        return this.containsBeanDefinition(this.beanFactory, name);
    }

    private boolean containsBeanDefinition(BeanFactory beanFactory, String name) {
        if (beanFactory instanceof ListableBeanFactory && ((ListableBeanFactory)beanFactory).containsBeanDefinition(name)) {
            return true;
        }
        if (beanFactory instanceof HierarchicalBeanFactory) {
            return this.containsBeanDefinition(((HierarchicalBeanFactory)beanFactory).getParentBeanFactory(), name);
        }
        return false;
    }

    private void registerBeanDefinition(String beanName, Class<?> type, MergedAnnotation<ConfigurationProperties> annotation) {
        Assert.state((boolean)annotation.isPresent(), () -> "No " + ConfigurationProperties.class.getSimpleName() + " annotation found on  '" + type.getName() + "'.");
        this.registry.registerBeanDefinition(beanName, this.createBeanDefinition(beanName, type));
    }

    private BeanDefinition createBeanDefinition(String beanName, Class<?> type) {
        ConfigurationPropertiesBean.BindMethod bindMethod = ConfigurationPropertiesBean.BindMethod.forType(type);
        RootBeanDefinition definition = new RootBeanDefinition(type);
        definition.setAttribute(ConfigurationPropertiesBean.BindMethod.class.getName(), (Object)bindMethod);
        if (bindMethod == ConfigurationPropertiesBean.BindMethod.VALUE_OBJECT) {
            definition.setInstanceSupplier(() -> this.createValueObject(beanName, type));
        }
        return definition;
    }

    private Object createValueObject(String beanName, Class<?> beanType) {
        ConfigurationPropertiesBean bean = ConfigurationPropertiesBean.forValueObject(beanType, beanName);
        ConfigurationPropertiesBinder binder = ConfigurationPropertiesBinder.get(this.beanFactory);
        try {
            return binder.bindOrCreate(bean);
        }
        catch (Exception ex) {
            throw new ConfigurationPropertiesBindException(bean, ex);
        }
    }
}

