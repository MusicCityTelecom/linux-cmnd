/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.AuthenticationEventExecutionPlan
 *  org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer
 *  org.apereo.cas.authentication.AuthenticationHandlerResolver
 *  org.apereo.cas.authentication.AuthenticationPolicyResolver
 *  org.apereo.cas.authentication.AuthenticationPostProcessor
 *  org.apereo.cas.authentication.AuthenticationPreProcessor
 *  org.apereo.cas.authentication.AuthenticationResultBuilderFactory
 *  org.apereo.cas.authentication.AuthenticationServiceSelectionPlan
 *  org.apereo.cas.authentication.AuthenticationSystemSupport
 *  org.apereo.cas.authentication.AuthenticationTransactionFactory
 *  org.apereo.cas.authentication.AuthenticationTransactionManager
 *  org.apereo.cas.authentication.DefaultAuthenticationSystemSupport
 *  org.apereo.cas.authentication.GroovyAuthenticationPostProcessor
 *  org.apereo.cas.authentication.GroovyAuthenticationPreProcessor
 *  org.apereo.cas.authentication.PrincipalElectionStrategy
 *  org.apereo.cas.authentication.handler.ByCredentialSourceAuthenticationHandlerResolver
 *  org.apereo.cas.authentication.handler.GroovyAuthenticationHandlerResolver
 *  org.apereo.cas.authentication.handler.RegisteredServiceAuthenticationHandlerResolver
 *  org.apereo.cas.authentication.policy.RegisteredServiceAuthenticationPolicyResolver
 *  org.apereo.cas.authentication.principal.PrincipalAttributesRepositoryCache
 *  org.apereo.cas.authentication.principal.cache.DefaultPrincipalAttributesRepositoryCache
 *  org.apereo.cas.config.CasCoreServicesConfiguration
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.configuration.model.core.authentication.AuthenticationEngineProperties
 *  org.apereo.cas.configuration.model.core.authentication.GroovyAuthenticationHandlerResolutionProperties
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.util.spring.beans.BeanCondition
 *  org.apereo.cas.util.spring.beans.BeanSupplier
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.ScopedProxyMode
 *  org.springframework.core.env.PropertyResolver
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.config;

import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationHandlerResolver;
import org.apereo.cas.authentication.AuthenticationPolicyResolver;
import org.apereo.cas.authentication.AuthenticationPostProcessor;
import org.apereo.cas.authentication.AuthenticationPreProcessor;
import org.apereo.cas.authentication.AuthenticationResultBuilderFactory;
import org.apereo.cas.authentication.AuthenticationServiceSelectionPlan;
import org.apereo.cas.authentication.AuthenticationSystemSupport;
import org.apereo.cas.authentication.AuthenticationTransactionFactory;
import org.apereo.cas.authentication.AuthenticationTransactionManager;
import org.apereo.cas.authentication.DefaultAuthenticationSystemSupport;
import org.apereo.cas.authentication.GroovyAuthenticationPostProcessor;
import org.apereo.cas.authentication.GroovyAuthenticationPreProcessor;
import org.apereo.cas.authentication.PrincipalElectionStrategy;
import org.apereo.cas.authentication.handler.ByCredentialSourceAuthenticationHandlerResolver;
import org.apereo.cas.authentication.handler.GroovyAuthenticationHandlerResolver;
import org.apereo.cas.authentication.handler.RegisteredServiceAuthenticationHandlerResolver;
import org.apereo.cas.authentication.policy.RegisteredServiceAuthenticationPolicyResolver;
import org.apereo.cas.authentication.principal.PrincipalAttributesRepositoryCache;
import org.apereo.cas.authentication.principal.cache.DefaultPrincipalAttributesRepositoryCache;
import org.apereo.cas.config.CasCoreServicesConfiguration;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.configuration.model.core.authentication.AuthenticationEngineProperties;
import org.apereo.cas.configuration.model.core.authentication.GroovyAuthenticationHandlerResolutionProperties;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.util.spring.beans.BeanCondition;
import org.apereo.cas.util.spring.beans.BeanSupplier;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.io.Resource;

@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.Authentication)
@AutoConfiguration(after={CasCoreServicesConfiguration.class})
public class CasCoreAuthenticationSupportConfiguration {

    @Configuration(value="CasCoreAuthenticationExecutionPlanConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreAuthenticationExecutionPlanConfiguration {
        @ConditionalOnMissingBean(name={"authenticationHandlerResolversExecutionPlanConfigurer"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public AuthenticationEventExecutionPlanConfigurer authenticationHandlerResolversExecutionPlanConfigurer(@Qualifier(value="byCredentialSourceAuthenticationHandlerResolver") ObjectProvider<AuthenticationHandlerResolver> byCredentialSourceAuthenticationHandlerResolver, @Qualifier(value="registeredServiceAuthenticationHandlerResolver") AuthenticationHandlerResolver registeredServiceAuthenticationHandlerResolver, @Qualifier(value="registeredServiceAuthenticationPolicyResolver") AuthenticationPolicyResolver registeredServiceAuthenticationPolicyResolver, @Qualifier(value="groovyAuthenticationHandlerResolver") ObjectProvider<AuthenticationHandlerResolver> groovyAuthenticationHandlerResolver) {
            return plan -> {
                byCredentialSourceAuthenticationHandlerResolver.ifAvailable(arg_0 -> ((AuthenticationEventExecutionPlan)plan).registerAuthenticationHandlerResolver(arg_0));
                plan.registerAuthenticationHandlerResolver(registeredServiceAuthenticationHandlerResolver);
                plan.registerAuthenticationPolicyResolver(registeredServiceAuthenticationPolicyResolver);
                groovyAuthenticationHandlerResolver.ifAvailable(arg_0 -> ((AuthenticationEventExecutionPlan)plan).registerAuthenticationHandlerResolver(arg_0));
            };
        }

        @ConditionalOnMissingBean(name={"groovyAuthenticationProcessorExecutionPlanConfigurer"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public AuthenticationEventExecutionPlanConfigurer groovyAuthenticationProcessorExecutionPlanConfigurer(CasConfigurationProperties casProperties) {
            return plan -> {
                Resource postResource;
                AuthenticationEngineProperties engine = casProperties.getAuthn().getCore().getEngine();
                Resource preResource = engine.getGroovyPreProcessor().getLocation();
                if (preResource != null) {
                    plan.registerAuthenticationPreProcessor((AuthenticationPreProcessor)new GroovyAuthenticationPreProcessor(preResource));
                }
                if ((postResource = engine.getGroovyPostProcessor().getLocation()) != null) {
                    plan.registerAuthenticationPostProcessor((AuthenticationPostProcessor)new GroovyAuthenticationPostProcessor(postResource));
                }
            };
        }
    }

    @Configuration(value="CasCoreAuthenticationPolicyResolverConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreAuthenticationPolicyResolverConfiguration {
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @Bean
        @ConditionalOnMissingBean(name={"registeredServiceAuthenticationPolicyResolver"})
        public AuthenticationPolicyResolver registeredServiceAuthenticationPolicyResolver(@Qualifier(value="servicesManager") ServicesManager servicesManager, @Qualifier(value="authenticationServiceSelectionPlan") AuthenticationServiceSelectionPlan authenticationServiceSelectionPlan) {
            return new RegisteredServiceAuthenticationPolicyResolver(servicesManager, authenticationServiceSelectionPlan);
        }
    }

    @Configuration(value="CasCoreAuthenticationSupportBaseConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreAuthenticationSupportBaseConfiguration {
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @Bean
        @ConditionalOnMissingBean(name={"defaultAuthenticationSystemSupport"})
        public AuthenticationSystemSupport defaultAuthenticationSystemSupport(@Qualifier(value="authenticationTransactionManager") AuthenticationTransactionManager authenticationTransactionManager, @Qualifier(value="principalElectionStrategy") PrincipalElectionStrategy principalElectionStrategy, @Qualifier(value="authenticationResultBuilderFactory") AuthenticationResultBuilderFactory authenticationResultBuilderFactory, @Qualifier(value="authenticationTransactionFactory") AuthenticationTransactionFactory authenticationTransactionFactory) {
            return new DefaultAuthenticationSystemSupport(authenticationTransactionManager, principalElectionStrategy, authenticationResultBuilderFactory, authenticationTransactionFactory);
        }
    }

    @Configuration(value="CasCoreAuthenticationHandlerResolverConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreAuthenticationHandlerResolverConfiguration {
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @Bean
        @ConditionalOnMissingBean(name={"registeredServiceAuthenticationHandlerResolver"})
        public AuthenticationHandlerResolver registeredServiceAuthenticationHandlerResolver(CasConfigurationProperties casProperties, @Qualifier(value="servicesManager") ServicesManager servicesManager, @Qualifier(value="authenticationServiceSelectionPlan") AuthenticationServiceSelectionPlan authenticationServiceSelectionPlan) {
            RegisteredServiceAuthenticationHandlerResolver resolver = new RegisteredServiceAuthenticationHandlerResolver(servicesManager, authenticationServiceSelectionPlan);
            resolver.setOrder(casProperties.getAuthn().getCore().getServiceAuthenticationResolution().getOrder());
            return resolver;
        }

        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @Bean
        @ConditionalOnMissingBean(name={"groovyAuthenticationHandlerResolver"})
        public AuthenticationHandlerResolver groovyAuthenticationHandlerResolver(ConfigurableApplicationContext applicationContext, CasConfigurationProperties casProperties, @Qualifier(value="servicesManager") ServicesManager servicesManager) throws Exception {
            return (AuthenticationHandlerResolver)BeanSupplier.of(AuthenticationHandlerResolver.class).when(BeanCondition.on((String)"cas.authn.core.groovy-authentication-resolution.location").exists().given((PropertyResolver)applicationContext.getEnvironment())).supply(() -> {
                GroovyAuthenticationHandlerResolutionProperties groovy = casProperties.getAuthn().getCore().getGroovyAuthenticationResolution();
                return new GroovyAuthenticationHandlerResolver(groovy.getLocation(), servicesManager, groovy.getOrder());
            }).otherwise(AuthenticationHandlerResolver::noOp).get();
        }

        @Bean
        @ConditionalOnMissingBean(name={"byCredentialSourceAuthenticationHandlerResolver"})
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public AuthenticationHandlerResolver byCredentialSourceAuthenticationHandlerResolver(ConfigurableApplicationContext applicationContext) throws Exception {
            return (AuthenticationHandlerResolver)BeanSupplier.of(AuthenticationHandlerResolver.class).when(BeanCondition.on((String)"cas.authn.policy.source-selection-enabled").isTrue().given((PropertyResolver)applicationContext.getEnvironment())).supply(ByCredentialSourceAuthenticationHandlerResolver::new).otherwise(AuthenticationHandlerResolver::noOp).get();
        }
    }

    @Configuration(value="CasCoreAuthenticationPrincipalCacheConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreAuthenticationPrincipalCacheConfiguration {
        @ConditionalOnMissingBean(name={"principalAttributesRepositoryCache"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public PrincipalAttributesRepositoryCache principalAttributesRepositoryCache() {
            return new DefaultPrincipalAttributesRepositoryCache();
        }
    }
}

