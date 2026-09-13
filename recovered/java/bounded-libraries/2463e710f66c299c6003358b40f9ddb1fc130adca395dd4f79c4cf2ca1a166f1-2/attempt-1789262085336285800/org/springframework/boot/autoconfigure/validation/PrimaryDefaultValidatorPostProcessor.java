/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.context.annotation.ImportBeanDefinitionRegistrar
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.validation.Validator
 *  org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
 */
package org.springframework.boot.autoconfigure.validation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class PrimaryDefaultValidatorPostProcessor
implements ImportBeanDefinitionRegistrar,
BeanFactoryAware {
    private static final String VALIDATOR_BEAN_NAME = "defaultValidator";
    private ConfigurableListableBeanFactory beanFactory;

    PrimaryDefaultValidatorPostProcessor() {
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof ConfigurableListableBeanFactory) {
            this.beanFactory = (ConfigurableListableBeanFactory)beanFactory;
        }
    }

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinition definition = this.getAutoConfiguredValidator(registry);
        if (definition != null) {
            definition.setPrimary(!this.hasPrimarySpringValidator());
        }
    }

    private BeanDefinition getAutoConfiguredValidator(BeanDefinitionRegistry registry) {
        BeanDefinition definition;
        if (registry.containsBeanDefinition(VALIDATOR_BEAN_NAME) && (definition = registry.getBeanDefinition(VALIDATOR_BEAN_NAME)).getRole() == 2 && this.isTypeMatch(VALIDATOR_BEAN_NAME, LocalValidatorFactoryBean.class)) {
            return definition;
        }
        return null;
    }

    private boolean isTypeMatch(String name, Class<?> type) {
        return this.beanFactory != null && this.beanFactory.isTypeMatch(name, type);
    }

    private boolean hasPrimarySpringValidator() {
        String[] validatorBeans;
        for (String validatorBean : validatorBeans = this.beanFactory.getBeanNamesForType(Validator.class, false, false)) {
            BeanDefinition definition = this.beanFactory.getBeanDefinition(validatorBean);
            if (!definition.isPrimary()) continue;
            return true;
        }
        return false;
    }
}

