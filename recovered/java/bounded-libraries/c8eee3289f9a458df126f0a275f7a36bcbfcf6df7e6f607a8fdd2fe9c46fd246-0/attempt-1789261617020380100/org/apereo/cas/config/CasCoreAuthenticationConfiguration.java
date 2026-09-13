/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationEventExecutionPlan
 *  org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer
 *  org.apereo.cas.authentication.AuthenticationManager
 *  org.apereo.cas.authentication.AuthenticationResultBuilderFactory
 *  org.apereo.cas.authentication.AuthenticationTransactionFactory
 *  org.apereo.cas.authentication.AuthenticationTransactionManager
 *  org.apereo.cas.authentication.DefaultAuthenticationAttributeReleasePolicy
 *  org.apereo.cas.authentication.DefaultAuthenticationEventExecutionPlan
 *  org.apereo.cas.authentication.DefaultAuthenticationManager
 *  org.apereo.cas.authentication.DefaultAuthenticationResultBuilderFactory
 *  org.apereo.cas.authentication.DefaultAuthenticationTransactionFactory
 *  org.apereo.cas.authentication.DefaultAuthenticationTransactionManager
 *  org.apereo.cas.config.CasCoreServicesConfiguration
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.configuration.model.core.authentication.AuthenticationAttributeReleaseProperties
 *  org.apereo.cas.util.model.TriStateBoolean
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.apereo.cas.validation.AuthenticationAttributeReleasePolicy
 *  org.jooq.lambda.Unchecked
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.autoconfigure.AutoConfigureOrder
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.context.ApplicationEventPublisher
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.ScopedProxyMode
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 */
package org.apereo.cas.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationManager;
import org.apereo.cas.authentication.AuthenticationResultBuilderFactory;
import org.apereo.cas.authentication.AuthenticationTransactionFactory;
import org.apereo.cas.authentication.AuthenticationTransactionManager;
import org.apereo.cas.authentication.DefaultAuthenticationAttributeReleasePolicy;
import org.apereo.cas.authentication.DefaultAuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.DefaultAuthenticationManager;
import org.apereo.cas.authentication.DefaultAuthenticationResultBuilderFactory;
import org.apereo.cas.authentication.DefaultAuthenticationTransactionFactory;
import org.apereo.cas.authentication.DefaultAuthenticationTransactionManager;
import org.apereo.cas.config.CasCoreServicesConfiguration;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.configuration.model.core.authentication.AuthenticationAttributeReleaseProperties;
import org.apereo.cas.util.model.TriStateBoolean;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.apereo.cas.validation.AuthenticationAttributeReleasePolicy;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.Authentication)
@AutoConfiguration(after={CasCoreServicesConfiguration.class})
public class CasCoreAuthenticationConfiguration {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CasCoreAuthenticationConfiguration.class);

    @Configuration(value="CasCoreAuthenticationPlanConfiguration", proxyBeanMethods=false)
    @AutoConfigureOrder(value=-2147483648)
    public static class CasCoreAuthenticationPlanConfiguration {
        @ConditionalOnMissingBean(name={"authenticationEventExecutionPlan"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public AuthenticationEventExecutionPlan authenticationEventExecutionPlan(List<AuthenticationEventExecutionPlanConfigurer> configurers) {
            DefaultAuthenticationEventExecutionPlan plan = new DefaultAuthenticationEventExecutionPlan();
            ArrayList<AuthenticationEventExecutionPlanConfigurer> sortedConfigurers = new ArrayList<AuthenticationEventExecutionPlanConfigurer>(configurers);
            AnnotationAwareOrderComparator.sortIfNecessary(sortedConfigurers);
            sortedConfigurers.forEach(Unchecked.consumer(c -> {
                LOGGER.trace("Configuring authentication execution plan [{}]", (Object)c.getName());
                c.configureAuthenticationExecutionPlan((AuthenticationEventExecutionPlan)plan);
            }));
            return plan;
        }
    }

    @Configuration(value="CasCoreAuthenticationManagerConfiguration", proxyBeanMethods=false)
    @AutoConfigureOrder(value=0x7FFFFFFF)
    public static class CasCoreAuthenticationManagerConfiguration {
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @ConditionalOnMissingBean(name={"authenticationTransactionManager"})
        public AuthenticationTransactionManager authenticationTransactionManager(@Qualifier(value="casAuthenticationManager") AuthenticationManager casAuthenticationManager, ConfigurableApplicationContext applicationContext) {
            return new DefaultAuthenticationTransactionManager((ApplicationEventPublisher)applicationContext, casAuthenticationManager);
        }

        @ConditionalOnMissingBean(name={"casAuthenticationManager"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public AuthenticationManager casAuthenticationManager(CasConfigurationProperties casProperties, ConfigurableApplicationContext applicationContext, @Qualifier(value="authenticationEventExecutionPlan") AuthenticationEventExecutionPlan authenticationEventExecutionPlan) {
            boolean isFatal = casProperties.getPersonDirectory().getPrincipalResolutionFailureFatal() == TriStateBoolean.TRUE;
            return new DefaultAuthenticationManager(authenticationEventExecutionPlan, isFatal, applicationContext);
        }
    }

    @Configuration(value="CasCoreAuthenticationBaseConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreAuthenticationBaseConfiguration {
        @ConditionalOnMissingBean(name={"authenticationResultBuilderFactory"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public AuthenticationResultBuilderFactory authenticationResultBuilderFactory() {
            return new DefaultAuthenticationResultBuilderFactory();
        }

        @ConditionalOnMissingBean(name={"authenticationTransactionFactory"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public AuthenticationTransactionFactory authenticationTransactionFactory() {
            return new DefaultAuthenticationTransactionFactory();
        }

        @ConditionalOnMissingBean(name={"authenticationAttributeReleasePolicy"})
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @Bean
        public AuthenticationAttributeReleasePolicy authenticationAttributeReleasePolicy(CasConfigurationProperties casProperties) {
            AuthenticationAttributeReleaseProperties release = casProperties.getAuthn().getAuthenticationAttributeRelease();
            if (!release.isEnabled()) {
                LOGGER.debug("CAS is configured to not release protocol-level authentication attributes.");
                return AuthenticationAttributeReleasePolicy.none();
            }
            return new DefaultAuthenticationAttributeReleasePolicy((Collection)release.getOnlyRelease(), (Collection)release.getNeverRelease(), casProperties.getAuthn().getMfa().getCore().getAuthenticationContextAttribute());
        }
    }
}

