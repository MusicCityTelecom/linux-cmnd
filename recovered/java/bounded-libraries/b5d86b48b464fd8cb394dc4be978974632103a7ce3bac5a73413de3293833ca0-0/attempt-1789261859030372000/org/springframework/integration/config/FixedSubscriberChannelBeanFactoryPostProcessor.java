/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.BeanFactoryPostProcessor
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.beans.factory.config.ConstructorArgumentValues
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.util.Assert
 */
package org.springframework.integration.config;

import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.integration.channel.FixedSubscriberChannel;
import org.springframework.util.Assert;

public final class FixedSubscriberChannelBeanFactoryPostProcessor
implements BeanFactoryPostProcessor {
    private final Map<String, String> candidateFixedChannelHandlerMap;

    FixedSubscriberChannelBeanFactoryPostProcessor(Map<String, String> candidateHandlers) {
        this.candidateFixedChannelHandlerMap = candidateHandlers;
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof BeanDefinitionRegistry) {
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry)beanFactory;
            if (this.candidateFixedChannelHandlerMap.size() > 0) {
                for (Map.Entry<String, String> entry : this.candidateFixedChannelHandlerMap.entrySet()) {
                    String handlerName = entry.getKey();
                    String channelName = entry.getValue();
                    BeanDefinition handlerBeanDefinition = null;
                    if (registry.containsBeanDefinition(handlerName)) {
                        handlerBeanDefinition = registry.getBeanDefinition(handlerName);
                    }
                    if (handlerBeanDefinition == null || !registry.containsBeanDefinition(channelName)) continue;
                    BeanDefinition inputChannelDefinition = registry.getBeanDefinition(channelName);
                    if (!FixedSubscriberChannel.class.getName().equals(inputChannelDefinition.getBeanClassName())) continue;
                    ConstructorArgumentValues constructorArgumentValues = inputChannelDefinition.getConstructorArgumentValues();
                    Assert.isTrue((boolean)constructorArgumentValues.isEmpty(), (String)"Only one subscriber is allowed for a FixedSubscriberChannel.");
                    constructorArgumentValues.addGenericArgumentValue((Object)handlerBeanDefinition);
                }
            }
        }
    }
}

