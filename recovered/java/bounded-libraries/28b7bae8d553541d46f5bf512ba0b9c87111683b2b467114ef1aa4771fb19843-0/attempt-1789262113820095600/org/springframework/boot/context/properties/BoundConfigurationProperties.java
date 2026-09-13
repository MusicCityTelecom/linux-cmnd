/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.context.ApplicationContext
 *  org.springframework.util.Assert
 */
package org.springframework.boot.context.properties;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.source.ConfigurationProperty;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

public class BoundConfigurationProperties {
    private Map<ConfigurationPropertyName, ConfigurationProperty> properties = new LinkedHashMap<ConfigurationPropertyName, ConfigurationProperty>();
    private static final String BEAN_NAME = BoundConfigurationProperties.class.getName();

    void add(ConfigurationProperty configurationProperty) {
        this.properties.put(configurationProperty.getName(), configurationProperty);
    }

    public ConfigurationProperty get(ConfigurationPropertyName name) {
        return this.properties.get(name);
    }

    public Map<ConfigurationPropertyName, ConfigurationProperty> getAll() {
        return Collections.unmodifiableMap(this.properties);
    }

    public static BoundConfigurationProperties get(ApplicationContext context) {
        if (!context.containsBeanDefinition(BEAN_NAME)) {
            return null;
        }
        return (BoundConfigurationProperties)context.getBean(BEAN_NAME, BoundConfigurationProperties.class);
    }

    static void register(BeanDefinitionRegistry registry) {
        Assert.notNull((Object)registry, (String)"Registry must not be null");
        if (!registry.containsBeanDefinition(BEAN_NAME)) {
            AbstractBeanDefinition definition = BeanDefinitionBuilder.genericBeanDefinition(BoundConfigurationProperties.class, BoundConfigurationProperties::new).getBeanDefinition();
            definition.setRole(2);
            registry.registerBeanDefinition(BEAN_NAME, (BeanDefinition)definition);
        }
    }
}

