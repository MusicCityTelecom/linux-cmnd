/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.RootBeanDefinition
 */
package org.springframework.integration.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.SpelFunctionFactoryBean;

public final class IntegrationConfigUtils {
    @Deprecated
    public static final String BASE_PACKAGE = "org.springframework.integration";
    public static final String HANDLER_ALIAS_SUFFIX = ".handler";

    public static void registerSpelFunctionBean(BeanDefinitionRegistry registry, String functionId, String className, String methodSignature) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(SpelFunctionFactoryBean.class).addConstructorArgValue((Object)className).addConstructorArgValue((Object)methodSignature);
        registry.registerBeanDefinition(functionId, (BeanDefinition)builder.getBeanDefinition());
    }

    public static void registerSpelFunctionBean(BeanDefinitionRegistry registry, String functionId, Class<?> aClass, String methodSignature) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(SpelFunctionFactoryBean.class, () -> new SpelFunctionFactoryBean(aClass, methodSignature)).addConstructorArgValue(aClass).addConstructorArgValue((Object)methodSignature);
        registry.registerBeanDefinition(functionId, (BeanDefinition)builder.getBeanDefinition());
    }

    public static void autoCreateDirectChannel(String channelName, BeanDefinitionRegistry registry) {
        registry.registerBeanDefinition(channelName, (BeanDefinition)new RootBeanDefinition(DirectChannel.class, DirectChannel::new));
    }

    private IntegrationConfigUtils() {
    }
}

