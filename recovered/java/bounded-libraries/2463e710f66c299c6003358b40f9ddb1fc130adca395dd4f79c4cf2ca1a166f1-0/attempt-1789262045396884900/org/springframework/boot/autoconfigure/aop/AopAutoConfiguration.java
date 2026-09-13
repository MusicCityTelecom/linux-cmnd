/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aspectj.weaver.Advice
 *  org.springframework.aop.config.AopConfigUtils
 *  org.springframework.beans.factory.config.BeanFactoryPostProcessor
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.EnableAspectJAutoProxy
 */
package org.springframework.boot.autoconfigure.aop;

import org.aspectj.weaver.Advice;
import org.springframework.aop.config.AopConfigUtils;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@AutoConfiguration
@ConditionalOnProperty(prefix="spring.aop", name={"auto"}, havingValue="true", matchIfMissing=true)
public class AopAutoConfiguration {

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnMissingClass(value={"org.aspectj.weaver.Advice"})
    @ConditionalOnProperty(prefix="spring.aop", name={"proxy-target-class"}, havingValue="true", matchIfMissing=true)
    static class ClassProxyingConfiguration {
        ClassProxyingConfiguration() {
        }

        @Bean
        static BeanFactoryPostProcessor forceAutoProxyCreatorToUseClassProxying() {
            return beanFactory -> {
                if (beanFactory instanceof BeanDefinitionRegistry) {
                    BeanDefinitionRegistry registry = (BeanDefinitionRegistry)beanFactory;
                    AopConfigUtils.registerAutoProxyCreatorIfNecessary((BeanDefinitionRegistry)registry);
                    AopConfigUtils.forceAutoProxyCreatorToUseClassProxying((BeanDefinitionRegistry)registry);
                }
            };
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={Advice.class})
    static class AspectJAutoProxyingConfiguration {
        AspectJAutoProxyingConfiguration() {
        }

        @Configuration(proxyBeanMethods=false)
        @EnableAspectJAutoProxy(proxyTargetClass=true)
        @ConditionalOnProperty(prefix="spring.aop", name={"proxy-target-class"}, havingValue="true", matchIfMissing=true)
        static class CglibAutoProxyConfiguration {
            CglibAutoProxyConfiguration() {
            }
        }

        @Configuration(proxyBeanMethods=false)
        @EnableAspectJAutoProxy(proxyTargetClass=false)
        @ConditionalOnProperty(prefix="spring.aop", name={"proxy-target-class"}, havingValue="false")
        static class JdkDynamicAutoProxyConfiguration {
            JdkDynamicAutoProxyConfiguration() {
            }
        }
    }
}

