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
 *  org.springframework.beans.factory.support.RootBeanDefinition
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.web.server.WebServerFactoryCustomizerBeanPostProcessor
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Import
 *  org.springframework.context.annotation.ImportBeanDefinitionRegistrar
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.http.ReactiveHttpInputMessage
 *  org.springframework.util.ObjectUtils
 *  org.springframework.web.server.adapter.ForwardedHeaderTransformer
 */
package org.springframework.boot.autoconfigure.web.reactive;

import java.util.function.Supplier;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryCustomizer;
import org.springframework.boot.autoconfigure.web.reactive.TomcatReactiveWebServerFactoryCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.server.WebServerFactoryCustomizerBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.http.ReactiveHttpInputMessage;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.adapter.ForwardedHeaderTransformer;

@AutoConfigureOrder(value=-2147483648)
@AutoConfiguration
@ConditionalOnClass(value={ReactiveHttpInputMessage.class})
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.REACTIVE)
@EnableConfigurationProperties(value={ServerProperties.class})
@Import(value={BeanPostProcessorsRegistrar.class, ReactiveWebServerFactoryConfiguration.EmbeddedTomcat.class, ReactiveWebServerFactoryConfiguration.EmbeddedJetty.class, ReactiveWebServerFactoryConfiguration.EmbeddedUndertow.class, ReactiveWebServerFactoryConfiguration.EmbeddedNetty.class})
public class ReactiveWebServerFactoryAutoConfiguration {
    @Bean
    public ReactiveWebServerFactoryCustomizer reactiveWebServerFactoryCustomizer(ServerProperties serverProperties) {
        return new ReactiveWebServerFactoryCustomizer(serverProperties);
    }

    @Bean
    @ConditionalOnClass(name={"org.apache.catalina.startup.Tomcat"})
    public TomcatReactiveWebServerFactoryCustomizer tomcatReactiveWebServerFactoryCustomizer(ServerProperties serverProperties) {
        return new TomcatReactiveWebServerFactoryCustomizer(serverProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value={"server.forward-headers-strategy"}, havingValue="framework")
    public ForwardedHeaderTransformer forwardedHeaderTransformer() {
        return new ForwardedHeaderTransformer();
    }

    public static class BeanPostProcessorsRegistrar
    implements ImportBeanDefinitionRegistrar,
    BeanFactoryAware {
        private ConfigurableListableBeanFactory beanFactory;

        public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
            if (beanFactory instanceof ConfigurableListableBeanFactory) {
                this.beanFactory = (ConfigurableListableBeanFactory)beanFactory;
            }
        }

        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
            if (this.beanFactory == null) {
                return;
            }
            this.registerSyntheticBeanIfMissing(registry, "webServerFactoryCustomizerBeanPostProcessor", WebServerFactoryCustomizerBeanPostProcessor.class, WebServerFactoryCustomizerBeanPostProcessor::new);
        }

        private <T> void registerSyntheticBeanIfMissing(BeanDefinitionRegistry registry, String name, Class<T> beanClass, Supplier<T> instanceSupplier) {
            if (ObjectUtils.isEmpty((Object[])this.beanFactory.getBeanNamesForType(beanClass, true, false))) {
                RootBeanDefinition beanDefinition = new RootBeanDefinition(beanClass, instanceSupplier);
                beanDefinition.setSynthetic(true);
                registry.registerBeanDefinition(name, (BeanDefinition)beanDefinition);
            }
        }
    }
}

