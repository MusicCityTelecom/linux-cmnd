/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.principal.ServiceFactory
 *  org.apereo.cas.authentication.principal.ServiceFactoryConfigurer
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.authentication.principal.WebApplicationServiceFactory
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.autoconfigure.AutoConfigureOrder
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.ScopedProxyMode
 */
package org.apereo.cas.config.support;

import org.apereo.cas.authentication.principal.ServiceFactory;
import org.apereo.cas.authentication.principal.ServiceFactoryConfigurer;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.authentication.principal.WebApplicationServiceFactory;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;

@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@AutoConfigureOrder(value=-2147483648)
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.Authentication)
@AutoConfiguration
public class CasWebApplicationServiceFactoryConfiguration {

    @Configuration(value="CasWebApplicationServiceFactoryPlanConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasWebApplicationServiceFactoryPlanConfiguration {
        @Bean
        @ConditionalOnMissingBean(name={"casWebApplicationServiceFactoryConfigurer"})
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public ServiceFactoryConfigurer casWebApplicationServiceFactoryConfigurer(@Qualifier(value="webApplicationServiceFactory") ServiceFactory<WebApplicationService> webApplicationServiceFactory) {
            return () -> CollectionUtils.wrap((Object)webApplicationServiceFactory);
        }
    }

    @Configuration(value="CasWebApplicationServiceFactoryBaseConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasWebApplicationServiceFactoryBaseConfiguration {
        @Bean
        @ConditionalOnMissingBean(name={"webApplicationServiceFactory"})
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public ServiceFactory<WebApplicationService> webApplicationServiceFactory() {
            return new WebApplicationServiceFactory();
        }
    }
}

