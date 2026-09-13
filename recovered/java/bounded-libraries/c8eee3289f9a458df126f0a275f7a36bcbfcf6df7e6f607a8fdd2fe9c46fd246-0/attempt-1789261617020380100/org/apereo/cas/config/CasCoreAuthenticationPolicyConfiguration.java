/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer
 *  org.apereo.cas.authentication.CoreAuthenticationUtils
 *  org.apereo.cas.authentication.adaptive.AdaptiveAuthenticationPolicy
 *  org.apereo.cas.authentication.adaptive.DefaultAdaptiveAuthenticationPolicy
 *  org.apereo.cas.authentication.adaptive.geo.GeoLocationService
 *  org.apereo.cas.authentication.adaptive.intel.IPAddressIntelligenceService
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.configuration.model.core.authentication.AdaptiveAuthenticationProperties
 *  org.apereo.cas.configuration.model.core.authentication.AuthenticationPolicyProperties
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.ScopedProxyMode
 */
package org.apereo.cas.config;

import java.util.Collection;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.CoreAuthenticationUtils;
import org.apereo.cas.authentication.adaptive.AdaptiveAuthenticationPolicy;
import org.apereo.cas.authentication.adaptive.DefaultAdaptiveAuthenticationPolicy;
import org.apereo.cas.authentication.adaptive.geo.GeoLocationService;
import org.apereo.cas.authentication.adaptive.intel.IPAddressIntelligenceService;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.configuration.model.core.authentication.AdaptiveAuthenticationProperties;
import org.apereo.cas.configuration.model.core.authentication.AuthenticationPolicyProperties;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;

@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.Authentication)
@AutoConfiguration
public class CasCoreAuthenticationPolicyConfiguration {
    @ConditionalOnMissingBean(name={"ipAddressIntelligenceService"})
    @Bean
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    public IPAddressIntelligenceService ipAddressIntelligenceService(CasConfigurationProperties casProperties) {
        AdaptiveAuthenticationProperties adaptive = casProperties.getAuthn().getAdaptive();
        return CoreAuthenticationUtils.newIpAddressIntelligenceService((AdaptiveAuthenticationProperties)adaptive);
    }

    @Configuration(value="CasCoreAuthenticationPolicyPlanConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreAuthenticationPolicyPlanConfiguration {
        @ConditionalOnMissingBean(name={"authenticationPolicyExecutionPlanConfigurer"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public AuthenticationEventExecutionPlanConfigurer authenticationPolicyExecutionPlanConfigurer(CasConfigurationProperties casProperties) {
            return plan -> {
                AuthenticationPolicyProperties policyProps = casProperties.getAuthn().getPolicy();
                Collection authPolicy = CoreAuthenticationUtils.newAuthenticationPolicy((AuthenticationPolicyProperties)policyProps);
                if (authPolicy != null) {
                    plan.registerAuthenticationPolicies(authPolicy);
                }
            };
        }

        @ConditionalOnMissingBean(name={"adaptiveAuthenticationPolicy"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public AdaptiveAuthenticationPolicy adaptiveAuthenticationPolicy(CasConfigurationProperties casProperties, @Qualifier(value="ipAddressIntelligenceService") IPAddressIntelligenceService ipAddressIntelligenceService, @Qualifier(value="geoLocationService") ObjectProvider<GeoLocationService> geoLocationService) {
            return new DefaultAdaptiveAuthenticationPolicy((GeoLocationService)geoLocationService.getIfAvailable(), ipAddressIntelligenceService, casProperties.getAuthn().getAdaptive());
        }
    }
}

