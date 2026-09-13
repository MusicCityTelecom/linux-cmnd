/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.principal.DefaultPrincipalAttributesRepository
 *  org.apereo.cas.authentication.principal.ShibbolethCompatiblePersistentIdGenerator
 *  org.apereo.cas.authentication.principal.cache.CachingPrincipalAttributesRepository
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.configuration.model.core.authentication.PrincipalAttributesCoreProperties$MergingStrategyTypes
 *  org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties$MultifactorAuthenticationProviderFailureModes
 *  org.apereo.cas.services.AllAuthenticationHandlersRegisteredServiceAuthenticationPolicyCriteria
 *  org.apereo.cas.services.AnonymousRegisteredServiceUsernameAttributeProvider
 *  org.apereo.cas.services.AnyAuthenticationHandlerRegisteredServiceAuthenticationPolicyCriteria
 *  org.apereo.cas.services.CasRegisteredService
 *  org.apereo.cas.services.ChainingAttributeReleasePolicy
 *  org.apereo.cas.services.ChainingRegisteredServiceAccessStrategy
 *  org.apereo.cas.services.ChainingRegisteredServiceDelegatedAuthenticationPolicy
 *  org.apereo.cas.services.DefaultRegisteredServiceAcceptableUsagePolicy
 *  org.apereo.cas.services.DefaultRegisteredServiceAccessStrategy
 *  org.apereo.cas.services.DefaultRegisteredServiceAuthenticationPolicy
 *  org.apereo.cas.services.DefaultRegisteredServiceContact
 *  org.apereo.cas.services.DefaultRegisteredServiceDelegatedAuthenticationPolicy
 *  org.apereo.cas.services.DefaultRegisteredServiceExpirationPolicy
 *  org.apereo.cas.services.DefaultRegisteredServiceMultifactorPolicy
 *  org.apereo.cas.services.DefaultRegisteredServiceProperty
 *  org.apereo.cas.services.DefaultRegisteredServiceProxyGrantingTicketExpirationPolicy
 *  org.apereo.cas.services.DefaultRegisteredServiceProxyTicketExpirationPolicy
 *  org.apereo.cas.services.DefaultRegisteredServiceServiceTicketExpirationPolicy
 *  org.apereo.cas.services.DefaultRegisteredServiceTicketGrantingTicketExpirationPolicy
 *  org.apereo.cas.services.DefaultRegisteredServiceUsernameProvider
 *  org.apereo.cas.services.DefaultRegisteredServiceWebflowInterruptPolicy
 *  org.apereo.cas.services.DenyAllAttributeReleasePolicy
 *  org.apereo.cas.services.FullRegexRegisteredServiceMatchingStrategy
 *  org.apereo.cas.services.GroovyRegisteredServiceAccessStrategy
 *  org.apereo.cas.services.GroovyRegisteredServiceAuthenticationPolicyCriteria
 *  org.apereo.cas.services.GroovyRegisteredServiceMultifactorPolicy
 *  org.apereo.cas.services.GroovyRegisteredServiceUsernameProvider
 *  org.apereo.cas.services.GroovyScriptAttributeReleasePolicy
 *  org.apereo.cas.services.LiteralRegisteredServiceMatchingStrategy
 *  org.apereo.cas.services.NotPreventedRegisteredServiceAuthenticationPolicyCriteria
 *  org.apereo.cas.services.PartialRegexRegisteredServiceMatchingStrategy
 *  org.apereo.cas.services.PatternMatchingAttributeReleasePolicy
 *  org.apereo.cas.services.PatternMatchingAttributeReleasePolicy$Rule
 *  org.apereo.cas.services.PrincipalAttributeRegisteredServiceUsernameProvider
 *  org.apereo.cas.services.RefuseRegisteredServiceProxyPolicy
 *  org.apereo.cas.services.RegexMatchingRegisteredServiceProxyPolicy
 *  org.apereo.cas.services.RegisteredServiceLogoutType
 *  org.apereo.cas.services.RegisteredServicePublicKeyImpl
 *  org.apereo.cas.services.RemoteEndpointServiceAccessStrategy
 *  org.apereo.cas.services.RestfulRegisteredServiceAuthenticationPolicyCriteria
 *  org.apereo.cas.services.ReturnAllAttributeReleasePolicy
 *  org.apereo.cas.services.ReturnAllowedAttributeReleasePolicy
 *  org.apereo.cas.services.ReturnMappedAttributeReleasePolicy
 *  org.apereo.cas.services.ReturnRestfulAttributeReleasePolicy
 *  org.apereo.cas.services.ReturnStaticAttributeReleasePolicy
 *  org.apereo.cas.services.ScriptedRegisteredServiceAttributeReleasePolicy
 *  org.apereo.cas.services.ScriptedRegisteredServiceUsernameProvider
 *  org.apereo.cas.services.TimeBasedRegisteredServiceAccessStrategy
 *  org.apereo.cas.services.consent.DefaultRegisteredServiceConsentPolicy
 *  org.apereo.cas.services.support.RegisteredServiceChainingAttributeFilter
 *  org.apereo.cas.services.support.RegisteredServiceMappedRegexAttributeFilter
 *  org.apereo.cas.services.support.RegisteredServiceRegexAttributeFilter
 *  org.apereo.cas.services.support.RegisteredServiceScriptedAttributeFilter
 *  org.apereo.cas.util.serialization.ComponentSerializationPlanConfigurer
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.ScopedProxyMode
 */
package org.apereo.cas.config;

import org.apereo.cas.authentication.principal.DefaultPrincipalAttributesRepository;
import org.apereo.cas.authentication.principal.ShibbolethCompatiblePersistentIdGenerator;
import org.apereo.cas.authentication.principal.cache.CachingPrincipalAttributesRepository;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.configuration.model.core.authentication.PrincipalAttributesCoreProperties;
import org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties;
import org.apereo.cas.services.AllAuthenticationHandlersRegisteredServiceAuthenticationPolicyCriteria;
import org.apereo.cas.services.AnonymousRegisteredServiceUsernameAttributeProvider;
import org.apereo.cas.services.AnyAuthenticationHandlerRegisteredServiceAuthenticationPolicyCriteria;
import org.apereo.cas.services.CasRegisteredService;
import org.apereo.cas.services.ChainingAttributeReleasePolicy;
import org.apereo.cas.services.ChainingRegisteredServiceAccessStrategy;
import org.apereo.cas.services.ChainingRegisteredServiceDelegatedAuthenticationPolicy;
import org.apereo.cas.services.DefaultRegisteredServiceAcceptableUsagePolicy;
import org.apereo.cas.services.DefaultRegisteredServiceAccessStrategy;
import org.apereo.cas.services.DefaultRegisteredServiceAuthenticationPolicy;
import org.apereo.cas.services.DefaultRegisteredServiceContact;
import org.apereo.cas.services.DefaultRegisteredServiceDelegatedAuthenticationPolicy;
import org.apereo.cas.services.DefaultRegisteredServiceExpirationPolicy;
import org.apereo.cas.services.DefaultRegisteredServiceMultifactorPolicy;
import org.apereo.cas.services.DefaultRegisteredServiceProperty;
import org.apereo.cas.services.DefaultRegisteredServiceProxyGrantingTicketExpirationPolicy;
import org.apereo.cas.services.DefaultRegisteredServiceProxyTicketExpirationPolicy;
import org.apereo.cas.services.DefaultRegisteredServiceServiceTicketExpirationPolicy;
import org.apereo.cas.services.DefaultRegisteredServiceTicketGrantingTicketExpirationPolicy;
import org.apereo.cas.services.DefaultRegisteredServiceUsernameProvider;
import org.apereo.cas.services.DefaultRegisteredServiceWebflowInterruptPolicy;
import org.apereo.cas.services.DenyAllAttributeReleasePolicy;
import org.apereo.cas.services.FullRegexRegisteredServiceMatchingStrategy;
import org.apereo.cas.services.GroovyRegisteredServiceAccessStrategy;
import org.apereo.cas.services.GroovyRegisteredServiceAuthenticationPolicyCriteria;
import org.apereo.cas.services.GroovyRegisteredServiceMultifactorPolicy;
import org.apereo.cas.services.GroovyRegisteredServiceUsernameProvider;
import org.apereo.cas.services.GroovyScriptAttributeReleasePolicy;
import org.apereo.cas.services.LiteralRegisteredServiceMatchingStrategy;
import org.apereo.cas.services.NotPreventedRegisteredServiceAuthenticationPolicyCriteria;
import org.apereo.cas.services.PartialRegexRegisteredServiceMatchingStrategy;
import org.apereo.cas.services.PatternMatchingAttributeReleasePolicy;
import org.apereo.cas.services.PrincipalAttributeRegisteredServiceUsernameProvider;
import org.apereo.cas.services.RefuseRegisteredServiceProxyPolicy;
import org.apereo.cas.services.RegexMatchingRegisteredServiceProxyPolicy;
import org.apereo.cas.services.RegisteredServiceLogoutType;
import org.apereo.cas.services.RegisteredServicePublicKeyImpl;
import org.apereo.cas.services.RemoteEndpointServiceAccessStrategy;
import org.apereo.cas.services.RestfulRegisteredServiceAuthenticationPolicyCriteria;
import org.apereo.cas.services.ReturnAllAttributeReleasePolicy;
import org.apereo.cas.services.ReturnAllowedAttributeReleasePolicy;
import org.apereo.cas.services.ReturnMappedAttributeReleasePolicy;
import org.apereo.cas.services.ReturnRestfulAttributeReleasePolicy;
import org.apereo.cas.services.ReturnStaticAttributeReleasePolicy;
import org.apereo.cas.services.ScriptedRegisteredServiceAttributeReleasePolicy;
import org.apereo.cas.services.ScriptedRegisteredServiceUsernameProvider;
import org.apereo.cas.services.TimeBasedRegisteredServiceAccessStrategy;
import org.apereo.cas.services.consent.DefaultRegisteredServiceConsentPolicy;
import org.apereo.cas.services.support.RegisteredServiceChainingAttributeFilter;
import org.apereo.cas.services.support.RegisteredServiceMappedRegexAttributeFilter;
import org.apereo.cas.services.support.RegisteredServiceRegexAttributeFilter;
import org.apereo.cas.services.support.RegisteredServiceScriptedAttributeFilter;
import org.apereo.cas.util.serialization.ComponentSerializationPlanConfigurer;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;

@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.ServiceRegistry)
@AutoConfiguration
public class CasCoreServicesComponentSerializationConfiguration {
    @Bean
    @ConditionalOnMissingBean(name={"casCoreServicesComponentSerializationPlanConfigurer"})
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    public ComponentSerializationPlanConfigurer casCoreServicesComponentSerializationPlanConfigurer() {
        return plan -> {
            plan.registerSerializableClass(CasRegisteredService.class);
            plan.registerSerializableClass(RegisteredServiceLogoutType.class);
            plan.registerSerializableClass(RegisteredServicePublicKeyImpl.class);
            plan.registerSerializableClass(DefaultRegisteredServiceContact.class);
            plan.registerSerializableClass(DefaultRegisteredServiceProperty.class);
            plan.registerSerializableClass(DefaultRegisteredServiceDelegatedAuthenticationPolicy.class);
            plan.registerSerializableClass(ChainingRegisteredServiceDelegatedAuthenticationPolicy.class);
            plan.registerSerializableClass(DefaultRegisteredServiceExpirationPolicy.class);
            plan.registerSerializableClass(DefaultRegisteredServiceServiceTicketExpirationPolicy.class);
            plan.registerSerializableClass(DefaultRegisteredServiceProxyTicketExpirationPolicy.class);
            plan.registerSerializableClass(DefaultRegisteredServiceDelegatedAuthenticationPolicy.class);
            plan.registerSerializableClass(DefaultRegisteredServiceAcceptableUsagePolicy.class);
            plan.registerSerializableClass(DefaultRegisteredServiceAuthenticationPolicy.class);
            plan.registerSerializableClass(ShibbolethCompatiblePersistentIdGenerator.class);
            plan.registerSerializableClass(FullRegexRegisteredServiceMatchingStrategy.class);
            plan.registerSerializableClass(PartialRegexRegisteredServiceMatchingStrategy.class);
            plan.registerSerializableClass(LiteralRegisteredServiceMatchingStrategy.class);
            plan.registerSerializableClass(RegexMatchingRegisteredServiceProxyPolicy.class);
            plan.registerSerializableClass(RefuseRegisteredServiceProxyPolicy.class);
            plan.registerSerializableClass(DefaultRegisteredServiceAccessStrategy.class);
            plan.registerSerializableClass(ChainingRegisteredServiceAccessStrategy.class);
            plan.registerSerializableClass(GroovyRegisteredServiceAccessStrategy.class);
            plan.registerSerializableClass(RemoteEndpointServiceAccessStrategy.class);
            plan.registerSerializableClass(TimeBasedRegisteredServiceAccessStrategy.class);
            plan.registerSerializableClass(DefaultRegisteredServiceProxyGrantingTicketExpirationPolicy.class);
            plan.registerSerializableClass(DefaultRegisteredServiceProxyTicketExpirationPolicy.class);
            plan.registerSerializableClass(DefaultRegisteredServiceTicketGrantingTicketExpirationPolicy.class);
            plan.registerSerializableClass(DefaultRegisteredServiceServiceTicketExpirationPolicy.class);
            plan.registerSerializableClass(PrincipalAttributeRegisteredServiceUsernameProvider.class);
            plan.registerSerializableClass(AnonymousRegisteredServiceUsernameAttributeProvider.class);
            plan.registerSerializableClass(GroovyRegisteredServiceUsernameProvider.class);
            plan.registerSerializableClass(DefaultRegisteredServiceUsernameProvider.class);
            plan.registerSerializableClass(DefaultRegisteredServiceWebflowInterruptPolicy.class);
            plan.registerSerializableClass(ScriptedRegisteredServiceUsernameProvider.class);
            plan.registerSerializableClass(RegisteredServiceRegexAttributeFilter.class);
            plan.registerSerializableClass(RegisteredServiceChainingAttributeFilter.class);
            plan.registerSerializableClass(RegisteredServiceMappedRegexAttributeFilter.class);
            plan.registerSerializableClass(RegisteredServiceScriptedAttributeFilter.class);
            plan.registerSerializableClass(ChainingAttributeReleasePolicy.class);
            plan.registerSerializableClass(DenyAllAttributeReleasePolicy.class);
            plan.registerSerializableClass(ReturnAllowedAttributeReleasePolicy.class);
            plan.registerSerializableClass(ReturnAllAttributeReleasePolicy.class);
            plan.registerSerializableClass(ReturnStaticAttributeReleasePolicy.class);
            plan.registerSerializableClass(ReturnMappedAttributeReleasePolicy.class);
            plan.registerSerializableClass(GroovyScriptAttributeReleasePolicy.class);
            plan.registerSerializableClass(ScriptedRegisteredServiceAttributeReleasePolicy.class);
            plan.registerSerializableClass(ReturnRestfulAttributeReleasePolicy.class);
            plan.registerSerializableClass(PatternMatchingAttributeReleasePolicy.class);
            plan.registerSerializableClass(PatternMatchingAttributeReleasePolicy.Rule.class);
            plan.registerSerializableClass(DefaultRegisteredServiceMultifactorPolicy.class);
            plan.registerSerializableClass(GroovyRegisteredServiceMultifactorPolicy.class);
            plan.registerSerializableClass(BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes.class);
            plan.registerSerializableClass(CachingPrincipalAttributesRepository.class);
            plan.registerSerializableClass(DefaultPrincipalAttributesRepository.class);
            plan.registerSerializableClass(PrincipalAttributesCoreProperties.MergingStrategyTypes.class);
            plan.registerSerializableClass(DefaultRegisteredServiceConsentPolicy.class);
            plan.registerSerializableClass(AllAuthenticationHandlersRegisteredServiceAuthenticationPolicyCriteria.class);
            plan.registerSerializableClass(AnyAuthenticationHandlerRegisteredServiceAuthenticationPolicyCriteria.class);
            plan.registerSerializableClass(GroovyRegisteredServiceAuthenticationPolicyCriteria.class);
            plan.registerSerializableClass(NotPreventedRegisteredServiceAuthenticationPolicyCriteria.class);
            plan.registerSerializableClass(RestfulRegisteredServiceAuthenticationPolicyCriteria.class);
        };
    }
}

