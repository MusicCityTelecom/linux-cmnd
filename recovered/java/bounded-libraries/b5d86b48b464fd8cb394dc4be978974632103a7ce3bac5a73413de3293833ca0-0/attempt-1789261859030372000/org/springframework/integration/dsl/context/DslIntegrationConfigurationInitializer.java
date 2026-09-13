/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.RootBeanDefinition
 *  org.springframework.util.Assert
 */
package org.springframework.integration.dsl.context;

import java.beans.Introspector;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.integration.config.IntegrationConfigurationInitializer;
import org.springframework.integration.dsl.BaseIntegrationFlowDefinition;
import org.springframework.integration.dsl.context.IntegrationFlowBeanPostProcessor;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.dsl.context.StandardIntegrationFlowContext;
import org.springframework.util.Assert;

public class DslIntegrationConfigurationInitializer
implements IntegrationConfigurationInitializer {
    private static final String INTEGRATION_FLOW_BPP_BEAN_NAME = Introspector.decapitalize(IntegrationFlowBeanPostProcessor.class.getName());
    private static final String INTEGRATION_FLOW_CONTEXT_BEAN_NAME = Introspector.decapitalize(IntegrationFlowContext.class.getName());
    private static final String INTEGRATION_FLOW_REPLY_PRODUCER_CLEANER_BEAN_NAME = Introspector.decapitalize(BaseIntegrationFlowDefinition.ReplyProducerCleaner.class.getName());

    @Override
    public void initialize(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        Assert.isInstanceOf(BeanDefinitionRegistry.class, (Object)configurableListableBeanFactory, (String)"To use Spring Integration Java DSL the 'beanFactory' has to be an instance of 'BeanDefinitionRegistry'. Consider using 'GenericApplicationContext' implementation.");
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry)configurableListableBeanFactory;
        if (!registry.containsBeanDefinition(INTEGRATION_FLOW_BPP_BEAN_NAME)) {
            registry.registerBeanDefinition(INTEGRATION_FLOW_BPP_BEAN_NAME, (BeanDefinition)new RootBeanDefinition(IntegrationFlowBeanPostProcessor.class, IntegrationFlowBeanPostProcessor::new));
            registry.registerBeanDefinition(INTEGRATION_FLOW_CONTEXT_BEAN_NAME, (BeanDefinition)new RootBeanDefinition(StandardIntegrationFlowContext.class, StandardIntegrationFlowContext::new));
            registry.registerBeanDefinition(INTEGRATION_FLOW_REPLY_PRODUCER_CLEANER_BEAN_NAME, (BeanDefinition)new RootBeanDefinition(BaseIntegrationFlowDefinition.ReplyProducerCleaner.class, BaseIntegrationFlowDefinition.ReplyProducerCleaner::new));
        }
    }
}

