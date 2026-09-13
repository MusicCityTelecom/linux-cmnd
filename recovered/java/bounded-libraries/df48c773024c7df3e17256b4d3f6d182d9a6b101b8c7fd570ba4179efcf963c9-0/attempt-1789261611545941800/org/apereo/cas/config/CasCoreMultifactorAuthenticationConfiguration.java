/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.DefaultMultifactorAuthenticationContextValidator
 *  org.apereo.cas.authentication.DefaultMultifactorAuthenticationFailureModeEvaluator
 *  org.apereo.cas.authentication.DefaultRequestedAuthenticationContextValidator
 *  org.apereo.cas.authentication.MultifactorAuthenticationContextValidator
 *  org.apereo.cas.authentication.MultifactorAuthenticationFailureModeEvaluator
 *  org.apereo.cas.authentication.MultifactorAuthenticationTriggerSelectionStrategy
 *  org.apereo.cas.config.CasCoreServicesConfiguration
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.configuration.model.support.mfa.MultifactorAuthenticationProperties
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.apereo.cas.validation.RequestedAuthenticationContextValidator
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.ScopedProxyMode
 */
package org.apereo.cas.config;

import org.apereo.cas.authentication.DefaultMultifactorAuthenticationContextValidator;
import org.apereo.cas.authentication.DefaultMultifactorAuthenticationFailureModeEvaluator;
import org.apereo.cas.authentication.DefaultRequestedAuthenticationContextValidator;
import org.apereo.cas.authentication.MultifactorAuthenticationContextValidator;
import org.apereo.cas.authentication.MultifactorAuthenticationFailureModeEvaluator;
import org.apereo.cas.authentication.MultifactorAuthenticationTriggerSelectionStrategy;
import org.apereo.cas.config.CasCoreServicesConfiguration;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.configuration.model.support.mfa.MultifactorAuthenticationProperties;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.apereo.cas.validation.RequestedAuthenticationContextValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;

@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.MultifactorAuthentication)
@AutoConfiguration(after={CasCoreServicesConfiguration.class})
public class CasCoreMultifactorAuthenticationConfiguration {

    @Configuration(value="CasCoreMultifactorAuthenticationFailureConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreMultifactorAuthenticationFailureConfiguration {
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @Bean
        @ConditionalOnMissingBean(name={"authenticationContextValidator"})
        public MultifactorAuthenticationContextValidator authenticationContextValidator(CasConfigurationProperties casProperties, ConfigurableApplicationContext applicationContext) {
            MultifactorAuthenticationProperties mfa = casProperties.getAuthn().getMfa();
            String contextAttribute = mfa.getCore().getAuthenticationContextAttribute();
            String authnAttributeName = mfa.getTrusted().getCore().getAuthenticationContextAttribute();
            return new DefaultMultifactorAuthenticationContextValidator(contextAttribute, authnAttributeName, applicationContext);
        }

        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @Bean
        @ConditionalOnMissingBean(name={"failureModeEvaluator"})
        public MultifactorAuthenticationFailureModeEvaluator failureModeEvaluator(CasConfigurationProperties casProperties) {
            return new DefaultMultifactorAuthenticationFailureModeEvaluator(casProperties);
        }
    }

    @Configuration(value="CasCoreMultifactorAuthenticationContextConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreMultifactorAuthenticationContextConfiguration {
        @Bean
        @ConditionalOnMissingBean(name={"requestedContextValidator"})
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public RequestedAuthenticationContextValidator requestedContextValidator(@Qualifier(value="servicesManager") ServicesManager servicesManager, @Qualifier(value="defaultMultifactorTriggerSelectionStrategy") MultifactorAuthenticationTriggerSelectionStrategy multifactorTriggerSelectionStrategy, @Qualifier(value="authenticationContextValidator") MultifactorAuthenticationContextValidator authenticationContextValidator) {
            return new DefaultRequestedAuthenticationContextValidator(servicesManager, multifactorTriggerSelectionStrategy, authenticationContextValidator);
        }
    }
}

