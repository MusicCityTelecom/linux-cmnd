/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationSystemSupport
 *  org.apereo.cas.authentication.MultifactorAuthenticationTriggerSelectionStrategy
 *  org.apereo.cas.authentication.principal.ServiceFactory
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.rest.authentication.DefaultRestAuthenticationService
 *  org.apereo.cas.rest.authentication.RestAuthenticationService
 *  org.apereo.cas.rest.factory.ChainingRestHttpRequestCredentialFactory
 *  org.apereo.cas.rest.factory.RestHttpRequestCredentialFactory
 *  org.apereo.cas.rest.factory.UsernamePasswordRestHttpRequestCredentialFactory
 *  org.apereo.cas.rest.plan.RestHttpRequestCredentialFactoryConfigurer
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.apereo.cas.validation.RequestedAuthenticationContextValidator
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.ScopedProxyMode
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 */
package org.apereo.cas.rest.config;

import java.util.List;
import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationSystemSupport;
import org.apereo.cas.authentication.MultifactorAuthenticationTriggerSelectionStrategy;
import org.apereo.cas.authentication.principal.ServiceFactory;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.rest.authentication.DefaultRestAuthenticationService;
import org.apereo.cas.rest.authentication.RestAuthenticationService;
import org.apereo.cas.rest.factory.ChainingRestHttpRequestCredentialFactory;
import org.apereo.cas.rest.factory.RestHttpRequestCredentialFactory;
import org.apereo.cas.rest.factory.UsernamePasswordRestHttpRequestCredentialFactory;
import org.apereo.cas.rest.plan.RestHttpRequestCredentialFactoryConfigurer;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.apereo.cas.validation.RequestedAuthenticationContextValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.RestProtocol)
@AutoConfiguration
public class CasCoreRestConfiguration {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CasCoreRestConfiguration.class);

    @Configuration(value="CasCoreRestCredentialFactoryPlanConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreRestCredentialFactoryPlanConfiguration {
        @ConditionalOnMissingBean(name={"restHttpRequestCredentialFactoryConfigurer"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public RestHttpRequestCredentialFactoryConfigurer restHttpRequestCredentialFactoryConfigurer() {
            return factory -> factory.registerCredentialFactory((RestHttpRequestCredentialFactory)new UsernamePasswordRestHttpRequestCredentialFactory());
        }
    }

    @Configuration(value="CasCoreRestCredentialFactoryConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreRestCredentialFactoryConfiguration {
        @Bean
        @ConditionalOnMissingBean(name={"restHttpRequestCredentialFactory"})
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public RestHttpRequestCredentialFactory restHttpRequestCredentialFactory(List<RestHttpRequestCredentialFactoryConfigurer> configurers) {
            LOGGER.trace("building REST credential factory from [{}]", configurers);
            ChainingRestHttpRequestCredentialFactory factory = new ChainingRestHttpRequestCredentialFactory(new RestHttpRequestCredentialFactory[0]);
            AnnotationAwareOrderComparator.sortIfNecessary(configurers);
            configurers.forEach(c -> {
                LOGGER.trace("Configuring credential factory: [{}]", c);
                c.configureCredentialFactory(factory);
            });
            return factory;
        }
    }

    @Configuration(value="CasCoreRestAuthenticationConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreRestAuthenticationConfiguration {
        @Bean
        @ConditionalOnMissingBean(name={"restAuthenticationService"})
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public RestAuthenticationService restAuthenticationService(@Qualifier(value="restHttpRequestCredentialFactory") RestHttpRequestCredentialFactory restHttpRequestCredentialFactory, @Qualifier(value="defaultMultifactorTriggerSelectionStrategy") MultifactorAuthenticationTriggerSelectionStrategy multifactorTriggerSelectionStrategy, @Qualifier(value="webApplicationServiceFactory") ServiceFactory<WebApplicationService> webApplicationServiceFactory, @Qualifier(value="defaultAuthenticationSystemSupport") AuthenticationSystemSupport authenticationSystemSupport, @Qualifier(value="servicesManager") ServicesManager servicesManager, @Qualifier(value="requestedContextValidator") RequestedAuthenticationContextValidator requestedContextValidator) {
            return new DefaultRestAuthenticationService(authenticationSystemSupport, restHttpRequestCredentialFactory, webApplicationServiceFactory, multifactorTriggerSelectionStrategy, servicesManager, requestedContextValidator);
        }
    }
}

