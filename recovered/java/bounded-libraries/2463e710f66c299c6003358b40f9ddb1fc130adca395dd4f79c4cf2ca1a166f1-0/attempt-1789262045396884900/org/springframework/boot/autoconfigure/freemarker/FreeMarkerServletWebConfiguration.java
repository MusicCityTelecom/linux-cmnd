/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  freemarker.template.Configuration
 *  javax.servlet.DispatcherType
 *  javax.servlet.Filter
 *  javax.servlet.Servlet
 *  org.springframework.boot.web.servlet.FilterRegistrationBean
 *  org.springframework.boot.web.servlet.ServletRegistrationBean
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.ui.freemarker.FreeMarkerConfigurationFactory
 *  org.springframework.web.servlet.resource.ResourceUrlEncodingFilter
 *  org.springframework.web.servlet.view.freemarker.FreeMarkerConfig
 *  org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer
 *  org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver
 */
package org.springframework.boot.autoconfigure.freemarker;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.Servlet;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.freemarker.AbstractFreeMarkerConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.boot.autoconfigure.web.ConditionalOnEnabledResourceChain;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@Configuration(proxyBeanMethods=false)
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass(value={Servlet.class, FreeMarkerConfigurer.class})
@AutoConfigureAfter(value={WebMvcAutoConfiguration.class})
class FreeMarkerServletWebConfiguration
extends AbstractFreeMarkerConfiguration {
    protected FreeMarkerServletWebConfiguration(FreeMarkerProperties properties) {
        super(properties);
    }

    @Bean
    @ConditionalOnMissingBean(value={FreeMarkerConfig.class})
    FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        this.applyProperties((FreeMarkerConfigurationFactory)configurer);
        return configurer;
    }

    @Bean
    freemarker.template.Configuration freeMarkerConfiguration(FreeMarkerConfig configurer) {
        return configurer.getConfiguration();
    }

    @Bean
    @ConditionalOnMissingBean(name={"freeMarkerViewResolver"})
    @ConditionalOnProperty(name={"spring.freemarker.enabled"}, matchIfMissing=true)
    FreeMarkerViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        this.getProperties().applyToMvcViewResolver(resolver);
        return resolver;
    }

    @Bean
    @ConditionalOnEnabledResourceChain
    @ConditionalOnMissingFilterBean(value={ResourceUrlEncodingFilter.class})
    FilterRegistrationBean<ResourceUrlEncodingFilter> resourceUrlEncodingFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean((Filter)new ResourceUrlEncodingFilter(), new ServletRegistrationBean[0]);
        registration.setDispatcherTypes(DispatcherType.REQUEST, new DispatcherType[]{DispatcherType.ERROR});
        return registration;
    }
}

