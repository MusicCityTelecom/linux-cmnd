/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.CoreAuthenticationUtils
 *  org.apereo.cas.authentication.PrincipalElectionStrategy
 *  org.apereo.cas.authentication.principal.ChainingPrincipalElectionStrategy
 *  org.apereo.cas.authentication.principal.DefaultPrincipalAttributesRepository
 *  org.apereo.cas.authentication.principal.DefaultPrincipalElectionStrategy
 *  org.apereo.cas.authentication.principal.DefaultPrincipalResolutionExecutionPlan
 *  org.apereo.cas.authentication.principal.PrincipalElectionStrategyConfigurer
 *  org.apereo.cas.authentication.principal.PrincipalFactory
 *  org.apereo.cas.authentication.principal.PrincipalFactoryUtils
 *  org.apereo.cas.authentication.principal.PrincipalResolutionExecutionPlan
 *  org.apereo.cas.authentication.principal.PrincipalResolutionExecutionPlanConfigurer
 *  org.apereo.cas.authentication.principal.PrincipalResolver
 *  org.apereo.cas.authentication.principal.RegisteredServicePrincipalAttributesRepository
 *  org.apereo.cas.authentication.principal.cache.CachingPrincipalAttributesRepository
 *  org.apereo.cas.authentication.principal.resolvers.ChainingPrincipalResolver
 *  org.apereo.cas.authentication.principal.resolvers.EchoingPrincipalResolver
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.configuration.model.core.authentication.PersonDirectoryPrincipalResolverProperties
 *  org.apereo.cas.configuration.model.core.authentication.PrincipalAttributesCoreProperties
 *  org.apereo.cas.configuration.model.core.authentication.PrincipalAttributesCoreProperties$MergingStrategyTypes
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.apereo.services.persondir.support.merger.IAttributeMerger
 *  org.jooq.lambda.Unchecked
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.ObjectProvider
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
package org.apereo.cas.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Generated;
import org.apereo.cas.authentication.CoreAuthenticationUtils;
import org.apereo.cas.authentication.PrincipalElectionStrategy;
import org.apereo.cas.authentication.principal.ChainingPrincipalElectionStrategy;
import org.apereo.cas.authentication.principal.DefaultPrincipalAttributesRepository;
import org.apereo.cas.authentication.principal.DefaultPrincipalElectionStrategy;
import org.apereo.cas.authentication.principal.DefaultPrincipalResolutionExecutionPlan;
import org.apereo.cas.authentication.principal.PrincipalElectionStrategyConfigurer;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.authentication.principal.PrincipalFactoryUtils;
import org.apereo.cas.authentication.principal.PrincipalResolutionExecutionPlan;
import org.apereo.cas.authentication.principal.PrincipalResolutionExecutionPlanConfigurer;
import org.apereo.cas.authentication.principal.PrincipalResolver;
import org.apereo.cas.authentication.principal.RegisteredServicePrincipalAttributesRepository;
import org.apereo.cas.authentication.principal.cache.CachingPrincipalAttributesRepository;
import org.apereo.cas.authentication.principal.resolvers.ChainingPrincipalResolver;
import org.apereo.cas.authentication.principal.resolvers.EchoingPrincipalResolver;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.configuration.model.core.authentication.PersonDirectoryPrincipalResolverProperties;
import org.apereo.cas.configuration.model.core.authentication.PrincipalAttributesCoreProperties;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.apereo.services.persondir.support.merger.IAttributeMerger;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.Authentication)
@AutoConfiguration
public class CasCoreAuthenticationPrincipalConfiguration {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CasCoreAuthenticationPrincipalConfiguration.class);

    @Configuration(value="CasCoreAuthenticationPrincipalFactoryConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreAuthenticationPrincipalFactoryConfiguration {
        @ConditionalOnMissingBean(name={"principalFactory"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public PrincipalFactory principalFactory() {
            return PrincipalFactoryUtils.newPrincipalFactory();
        }

        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @ConditionalOnMissingBean(name={"globalPrincipalAttributeRepository"})
        public RegisteredServicePrincipalAttributesRepository globalPrincipalAttributeRepository(CasConfigurationProperties casProperties) {
            PrincipalAttributesCoreProperties props = casProperties.getAuthn().getAttributeRepository().getCore();
            int cacheTime = props.getExpirationTime();
            if (cacheTime <= 0) {
                LOGGER.warn("Caching for the global principal attribute repository is disabled");
                return new DefaultPrincipalAttributesRepository();
            }
            return new CachingPrincipalAttributesRepository(props.getExpirationTimeUnit().toUpperCase(), (long)cacheTime);
        }
    }

    @Configuration(value="CasCoreAuthenticationPrincipalElectionConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreAuthenticationPrincipalElectionConfiguration {
        @ConditionalOnMissingBean(name={"principalElectionStrategy"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public PrincipalElectionStrategy principalElectionStrategy(List<PrincipalElectionStrategyConfigurer> configurers, @Qualifier(value="principalElectionAttributeMerger") IAttributeMerger attributeMerger) {
            LOGGER.trace("Building principal election strategies from [{}]", configurers);
            ChainingPrincipalElectionStrategy chain = new ChainingPrincipalElectionStrategy(new PrincipalElectionStrategy[0]);
            chain.setAttributeMerger(attributeMerger);
            AnnotationAwareOrderComparator.sortIfNecessary(configurers);
            configurers.forEach(c -> {
                LOGGER.trace("Configuring principal selection strategy: [{}]", c);
                c.configurePrincipalElectionStrategy(chain);
            });
            return chain;
        }

        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @ConditionalOnMissingBean(name={"principalElectionAttributeMerger"})
        public IAttributeMerger principalElectionAttributeMerger(CasConfigurationProperties casProperties) {
            return CoreAuthenticationUtils.getAttributeMerger((PrincipalAttributesCoreProperties.MergingStrategyTypes)casProperties.getAuthn().getAttributeRepository().getCore().getMerger());
        }

        @ConditionalOnMissingBean(name={"defaultPrincipalElectionStrategyConfigurer"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public PrincipalElectionStrategyConfigurer defaultPrincipalElectionStrategyConfigurer(@Qualifier(value="principalElectionAttributeMerger") IAttributeMerger attributeMerger, CasConfigurationProperties casProperties, @Qualifier(value="principalFactory") PrincipalFactory principalFactory) {
            return chain -> {
                DefaultPrincipalElectionStrategy strategy = new DefaultPrincipalElectionStrategy(principalFactory, CoreAuthenticationUtils.newPrincipalElectionStrategyConflictResolver((PersonDirectoryPrincipalResolverProperties)casProperties.getPersonDirectory()));
                strategy.setAttributeMerger(attributeMerger);
                chain.registerElectionStrategy((PrincipalElectionStrategy)strategy);
            };
        }
    }

    @Configuration(value="CasCoreAuthenticationPrincipalResolutionConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreAuthenticationPrincipalResolutionConfiguration {
        @Bean
        @ConditionalOnMissingBean(name={"defaultPrincipalResolver"})
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public PrincipalResolver defaultPrincipalResolver(ObjectProvider<List<PrincipalResolutionExecutionPlanConfigurer>> configurers, CasConfigurationProperties casProperties, @Qualifier(value="principalElectionStrategy") PrincipalElectionStrategy principalElectionStrategy) {
            DefaultPrincipalResolutionExecutionPlan plan = new DefaultPrincipalResolutionExecutionPlan();
            ArrayList sortedConfigurers = new ArrayList(Optional.ofNullable((List)configurers.getIfAvailable()).orElseGet(() -> new ArrayList(0)));
            AnnotationAwareOrderComparator.sortIfNecessary(sortedConfigurers);
            sortedConfigurers.forEach(Unchecked.consumer(c -> {
                LOGGER.trace("Configuring principal resolution execution plan [{}]", (Object)c.getName());
                c.configurePrincipalResolutionExecutionPlan((PrincipalResolutionExecutionPlan)plan);
            }));
            plan.registerPrincipalResolver((PrincipalResolver)new EchoingPrincipalResolver());
            List registeredPrincipalResolvers = plan.getRegisteredPrincipalResolvers();
            ChainingPrincipalResolver resolver = new ChainingPrincipalResolver(principalElectionStrategy, casProperties);
            resolver.setChain(registeredPrincipalResolvers);
            return resolver;
        }
    }
}

