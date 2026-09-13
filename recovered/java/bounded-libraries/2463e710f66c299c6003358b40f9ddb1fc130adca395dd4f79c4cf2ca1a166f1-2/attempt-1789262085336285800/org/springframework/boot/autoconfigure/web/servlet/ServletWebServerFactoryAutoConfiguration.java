/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.DispatcherType
 *  javax.servlet.Filter
 *  javax.servlet.ServletRequest
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.RootBeanDefinition
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.web.server.ErrorPageRegistrarBeanPostProcessor
 *  org.springframework.boot.web.server.WebServerFactoryCustomizerBeanPostProcessor
 *  org.springframework.boot.web.servlet.FilterRegistrationBean
 *  org.springframework.boot.web.servlet.ServletRegistrationBean
 *  org.springframework.boot.web.servlet.WebListenerRegistrar
 *  org.springframework.boot.web.servlet.server.CookieSameSiteSupplier
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Import
 *  org.springframework.context.annotation.ImportBeanDefinitionRegistrar
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.util.ObjectUtils
 *  org.springframework.web.filter.ForwardedHeaderFilter
 */
package org.springframework.boot.autoconfigure.web.servlet;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryCustomizer;
import org.springframework.boot.autoconfigure.web.servlet.TomcatServletWebServerFactoryCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.server.ErrorPageRegistrarBeanPostProcessor;
import org.springframework.boot.web.server.WebServerFactoryCustomizerBeanPostProcessor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.WebListenerRegistrar;
import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.ForwardedHeaderFilter;

@AutoConfiguration
@AutoConfigureOrder(value=-2147483648)
@ConditionalOnClass(value={ServletRequest.class})
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.SERVLET)
@EnableConfigurationProperties(value={ServerProperties.class})
@Import(value={BeanPostProcessorsRegistrar.class, ServletWebServerFactoryConfiguration.EmbeddedTomcat.class, ServletWebServerFactoryConfiguration.EmbeddedJetty.class, ServletWebServerFactoryConfiguration.EmbeddedUndertow.class})
public class ServletWebServerFactoryAutoConfiguration {
    @Bean
    public ServletWebServerFactoryCustomizer servletWebServerFactoryCustomizer(ServerProperties serverProperties, ObjectProvider<WebListenerRegistrar> webListenerRegistrars, ObjectProvider<CookieSameSiteSupplier> cookieSameSiteSuppliers) {
        return new ServletWebServerFactoryCustomizer(serverProperties, webListenerRegistrars.orderedStream().collect(Collectors.toList()), cookieSameSiteSuppliers.orderedStream().collect(Collectors.toList()));
    }

    @Bean
    @ConditionalOnClass(name={"org.apache.catalina.startup.Tomcat"})
    public TomcatServletWebServerFactoryCustomizer tomcatServletWebServerFactoryCustomizer(ServerProperties serverProperties) {
        return new TomcatServletWebServerFactoryCustomizer(serverProperties);
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
            this.registerSyntheticBeanIfMissing(registry, "errorPageRegistrarBeanPostProcessor", ErrorPageRegistrarBeanPostProcessor.class, ErrorPageRegistrarBeanPostProcessor::new);
        }

        private <T> void registerSyntheticBeanIfMissing(BeanDefinitionRegistry registry, String name, Class<T> beanClass, Supplier<T> instanceSupplier) {
            if (ObjectUtils.isEmpty((Object[])this.beanFactory.getBeanNamesForType(beanClass, true, false))) {
                RootBeanDefinition beanDefinition = new RootBeanDefinition(beanClass, instanceSupplier);
                beanDefinition.setSynthetic(true);
                registry.registerBeanDefinition(name, (BeanDefinition)beanDefinition);
            }
        }
    }

    static interface ForwardedHeaderFilterCustomizer {
        public void customize(ForwardedHeaderFilter var1);
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnProperty(value={"server.forward-headers-strategy"}, havingValue="framework")
    @ConditionalOnMissingFilterBean(value={ForwardedHeaderFilter.class})
    static class ForwardedHeaderFilterConfiguration {
        ForwardedHeaderFilterConfiguration() {
        }

        @Bean
        @ConditionalOnClass(name={"org.apache.catalina.startup.Tomcat"})
        ForwardedHeaderFilterCustomizer tomcatForwardedHeaderFilterCustomizer(ServerProperties serverProperties) {
            return filter -> filter.setRelativeRedirects(serverProperties.getTomcat().isUseRelativeRedirects());
        }

        @Bean
        FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter(ObjectProvider<ForwardedHeaderFilterCustomizer> customizerProvider) {
            ForwardedHeaderFilter filter = new ForwardedHeaderFilter();
            customizerProvider.ifAvailable(customizer -> customizer.customize(filter));
            FilterRegistrationBean registration = new FilterRegistrationBean((Filter)filter, new ServletRegistrationBean[0]);
            registration.setDispatcherTypes(DispatcherType.REQUEST, new DispatcherType[]{DispatcherType.ASYNC, DispatcherType.ERROR});
            registration.setOrder(Integer.MIN_VALUE);
            return registration;
        }
    }
}

