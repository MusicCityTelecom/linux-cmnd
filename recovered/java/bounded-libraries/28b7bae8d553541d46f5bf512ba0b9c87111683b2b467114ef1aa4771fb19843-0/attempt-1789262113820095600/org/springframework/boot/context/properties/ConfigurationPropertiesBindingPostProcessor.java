/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.BeanPostProcessor
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.core.PriorityOrdered
 *  org.springframework.util.Assert
 */
package org.springframework.boot.context.properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.ConfigurationPropertiesBean;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindException;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.util.Assert;

public class ConfigurationPropertiesBindingPostProcessor
implements BeanPostProcessor,
PriorityOrdered,
ApplicationContextAware,
InitializingBean {
    public static final String BEAN_NAME = ConfigurationPropertiesBindingPostProcessor.class.getName();
    private ApplicationContext applicationContext;
    private BeanDefinitionRegistry registry;
    private ConfigurationPropertiesBinder binder;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void afterPropertiesSet() throws Exception {
        this.registry = (BeanDefinitionRegistry)this.applicationContext.getAutowireCapableBeanFactory();
        this.binder = ConfigurationPropertiesBinder.get((BeanFactory)this.applicationContext);
    }

    public int getOrder() {
        return -2147483647;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        this.bind(ConfigurationPropertiesBean.get(this.applicationContext, bean, beanName));
        return bean;
    }

    private void bind(ConfigurationPropertiesBean bean) {
        if (bean == null || this.hasBoundValueObject(bean.getName())) {
            return;
        }
        Assert.state((bean.getBindMethod() == ConfigurationPropertiesBean.BindMethod.JAVA_BEAN ? 1 : 0) != 0, (String)("Cannot bind @ConfigurationProperties for bean '" + bean.getName() + "'. Ensure that @ConstructorBinding has not been applied to regular bean"));
        try {
            this.binder.bind(bean);
        }
        catch (Exception ex) {
            throw new ConfigurationPropertiesBindException(bean, ex);
        }
    }

    private boolean hasBoundValueObject(String beanName) {
        return this.registry.containsBeanDefinition(beanName) && ConfigurationPropertiesBean.BindMethod.VALUE_OBJECT.equals(this.registry.getBeanDefinition(beanName).getAttribute(ConfigurationPropertiesBean.BindMethod.class.getName()));
    }

    public static void register(BeanDefinitionRegistry registry) {
        Assert.notNull((Object)registry, (String)"Registry must not be null");
        if (!registry.containsBeanDefinition(BEAN_NAME)) {
            AbstractBeanDefinition definition = BeanDefinitionBuilder.rootBeanDefinition(ConfigurationPropertiesBindingPostProcessor.class).getBeanDefinition();
            definition.setRole(2);
            registry.registerBeanDefinition(BEAN_NAME, (BeanDefinition)definition);
        }
        ConfigurationPropertiesBinder.register(registry);
    }
}

