/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.beans.factory.NoUniqueBeanDefinitionException
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.ConfigurableBeanFactory
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.diagnostics.analyzer;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.diagnostics.analyzer.AbstractInjectionFailureAnalyzer;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

class NoUniqueBeanDefinitionFailureAnalyzer
extends AbstractInjectionFailureAnalyzer<NoUniqueBeanDefinitionException> {
    private final ConfigurableBeanFactory beanFactory;

    NoUniqueBeanDefinitionFailureAnalyzer(BeanFactory beanFactory) {
        Assert.isInstanceOf(ConfigurableBeanFactory.class, (Object)beanFactory);
        this.beanFactory = (ConfigurableBeanFactory)beanFactory;
    }

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, NoUniqueBeanDefinitionException cause, String description) {
        if (description == null) {
            return null;
        }
        String[] beanNames = this.extractBeanNames(cause);
        if (beanNames == null) {
            return null;
        }
        StringBuilder message = new StringBuilder();
        message.append(String.format("%s required a single bean, but %d were found:%n", description, beanNames.length));
        for (String beanName : beanNames) {
            this.buildMessage(message, beanName);
        }
        return new FailureAnalysis(message.toString(), "Consider marking one of the beans as @Primary, updating the consumer to accept multiple beans, or using @Qualifier to identify the bean that should be consumed", (Throwable)cause);
    }

    private void buildMessage(StringBuilder message, String beanName) {
        try {
            BeanDefinition definition = this.beanFactory.getMergedBeanDefinition(beanName);
            message.append(this.getDefinitionDescription(beanName, definition));
        }
        catch (NoSuchBeanDefinitionException ex) {
            message.append(String.format("\t- %s: a programmatically registered singleton", beanName));
        }
    }

    private String getDefinitionDescription(String beanName, BeanDefinition definition) {
        if (StringUtils.hasText((String)definition.getFactoryMethodName())) {
            return String.format("\t- %s: defined by method '%s' in %s%n", beanName, definition.getFactoryMethodName(), definition.getResourceDescription());
        }
        return String.format("\t- %s: defined in %s%n", beanName, definition.getResourceDescription());
    }

    private String[] extractBeanNames(NoUniqueBeanDefinitionException cause) {
        if (cause.getMessage().contains("but found")) {
            return StringUtils.commaDelimitedListToStringArray((String)cause.getMessage().substring(cause.getMessage().lastIndexOf(58) + 1).trim());
        }
        return null;
    }
}

