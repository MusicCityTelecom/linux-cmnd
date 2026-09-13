/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.benmanes.caffeine.cache.Cache
 *  com.github.benmanes.caffeine.cache.Caffeine
 *  com.google.common.base.Predicate
 *  com.google.common.base.Predicates
 *  lombok.Generated
 *  org.apereo.cas.audit.AuditableExecution
 *  org.apereo.cas.authentication.principal.DefaultWebApplicationResponseBuilderLocator
 *  org.apereo.cas.authentication.principal.PersistentIdGenerator
 *  org.apereo.cas.authentication.principal.ResponseBuilder
 *  org.apereo.cas.authentication.principal.ResponseBuilderLocator
 *  org.apereo.cas.authentication.principal.ShibbolethCompatiblePersistentIdGenerator
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.authentication.principal.WebApplicationServiceResponseBuilder
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.configuration.model.core.services.ServiceRegistryCacheProperties
 *  org.apereo.cas.configuration.model.core.services.ServiceRegistryCoreProperties$ServiceManagementTypes
 *  org.apereo.cas.configuration.support.Beans
 *  org.apereo.cas.notifications.CommunicationsManager
 *  org.apereo.cas.services.ChainingServiceRegistry
 *  org.apereo.cas.services.ChainingServicesManager
 *  org.apereo.cas.services.DefaultChainingServiceRegistry
 *  org.apereo.cas.services.DefaultChainingServicesManager
 *  org.apereo.cas.services.DefaultRegisteredServicesEventListener
 *  org.apereo.cas.services.DefaultServiceRegistryExecutionPlan
 *  org.apereo.cas.services.DefaultServicesManager
 *  org.apereo.cas.services.DefaultServicesManagerRegisteredServiceLocator
 *  org.apereo.cas.services.ImmutableServiceRegistry
 *  org.apereo.cas.services.InMemoryServiceRegistry
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceAccessStrategyAuditableEnforcer
 *  org.apereo.cas.services.RegisteredServiceCipherExecutor
 *  org.apereo.cas.services.RegisteredServicePublicKeyCipherExecutor
 *  org.apereo.cas.services.RegisteredServicesEventListener
 *  org.apereo.cas.services.ServiceRegistry
 *  org.apereo.cas.services.ServiceRegistryExecutionPlan
 *  org.apereo.cas.services.ServiceRegistryExecutionPlanConfigurer
 *  org.apereo.cas.services.ServiceRegistryListener
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.services.ServicesManagerConfigurationContext
 *  org.apereo.cas.services.ServicesManagerExecutionPlanConfigurer
 *  org.apereo.cas.services.ServicesManagerRegisteredServiceLocator
 *  org.apereo.cas.services.ServicesManagerScheduledLoader
 *  org.apereo.cas.services.domain.DefaultDomainAwareServicesManager
 *  org.apereo.cas.services.domain.DefaultRegisteredServiceDomainExtractor
 *  org.apereo.cas.services.domain.RegisteredServiceDomainExtractor
 *  org.apereo.cas.services.replication.NoOpRegisteredServiceReplicationStrategy
 *  org.apereo.cas.services.replication.RegisteredServiceReplicationStrategy
 *  org.apereo.cas.services.resource.DefaultRegisteredServiceResourceNamingStrategy
 *  org.apereo.cas.services.resource.RegisteredServiceResourceNamingStrategy
 *  org.apereo.cas.util.spring.beans.BeanCondition
 *  org.apereo.cas.util.spring.beans.BeanSupplier
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.apereo.cas.web.UrlValidator
 *  org.jooq.lambda.Unchecked
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.autoconfigure.AutoConfigureAfter
 *  org.springframework.boot.autoconfigure.AutoConfigureOrder
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 *  org.springframework.boot.context.event.ApplicationReadyEvent
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.ScopedProxyMode
 *  org.springframework.context.event.EventListener
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 *  org.springframework.core.env.Environment
 *  org.springframework.core.env.PropertyResolver
 *  org.springframework.scheduling.annotation.EnableAsync
 */
package org.apereo.cas.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.base.Predicates;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apereo.cas.audit.AuditableExecution;
import org.apereo.cas.authentication.principal.DefaultWebApplicationResponseBuilderLocator;
import org.apereo.cas.authentication.principal.PersistentIdGenerator;
import org.apereo.cas.authentication.principal.ResponseBuilder;
import org.apereo.cas.authentication.principal.ResponseBuilderLocator;
import org.apereo.cas.authentication.principal.ShibbolethCompatiblePersistentIdGenerator;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.authentication.principal.WebApplicationServiceResponseBuilder;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.configuration.model.core.services.ServiceRegistryCacheProperties;
import org.apereo.cas.configuration.model.core.services.ServiceRegistryCoreProperties;
import org.apereo.cas.configuration.support.Beans;
import org.apereo.cas.notifications.CommunicationsManager;
import org.apereo.cas.services.ChainingServiceRegistry;
import org.apereo.cas.services.ChainingServicesManager;
import org.apereo.cas.services.DefaultChainingServiceRegistry;
import org.apereo.cas.services.DefaultChainingServicesManager;
import org.apereo.cas.services.DefaultRegisteredServicesEventListener;
import org.apereo.cas.services.DefaultServiceRegistryExecutionPlan;
import org.apereo.cas.services.DefaultServicesManager;
import org.apereo.cas.services.DefaultServicesManagerRegisteredServiceLocator;
import org.apereo.cas.services.ImmutableServiceRegistry;
import org.apereo.cas.services.InMemoryServiceRegistry;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceAccessStrategyAuditableEnforcer;
import org.apereo.cas.services.RegisteredServiceCipherExecutor;
import org.apereo.cas.services.RegisteredServicePublicKeyCipherExecutor;
import org.apereo.cas.services.RegisteredServicesEventListener;
import org.apereo.cas.services.ServiceRegistry;
import org.apereo.cas.services.ServiceRegistryExecutionPlan;
import org.apereo.cas.services.ServiceRegistryExecutionPlanConfigurer;
import org.apereo.cas.services.ServiceRegistryListener;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.services.ServicesManagerConfigurationContext;
import org.apereo.cas.services.ServicesManagerExecutionPlanConfigurer;
import org.apereo.cas.services.ServicesManagerRegisteredServiceLocator;
import org.apereo.cas.services.ServicesManagerScheduledLoader;
import org.apereo.cas.services.domain.DefaultDomainAwareServicesManager;
import org.apereo.cas.services.domain.DefaultRegisteredServiceDomainExtractor;
import org.apereo.cas.services.domain.RegisteredServiceDomainExtractor;
import org.apereo.cas.services.replication.NoOpRegisteredServiceReplicationStrategy;
import org.apereo.cas.services.replication.RegisteredServiceReplicationStrategy;
import org.apereo.cas.services.resource.DefaultRegisteredServiceResourceNamingStrategy;
import org.apereo.cas.services.resource.RegisteredServiceResourceNamingStrategy;
import org.apereo.cas.util.spring.beans.BeanCondition;
import org.apereo.cas.util.spring.beans.BeanSupplier;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.apereo.cas.web.UrlValidator;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@EnableAsync(proxyTargetClass=false)
@AutoConfigureOrder(value=-2147483648)
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.ServiceRegistry)
@AutoConfiguration
public class CasCoreServicesConfiguration {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CasCoreServicesConfiguration.class);

    @Configuration(value="CasCoreServicesSchedulingConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    @AutoConfigureOrder(value=0x7FFFFFFF)
    @AutoConfigureAfter(value={CasCoreServicesManagerConfiguration.class, CasCoreServiceRegistryConfiguration.class})
    public static class CasCoreServicesSchedulingConfiguration {
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public Runnable servicesManagerScheduledLoader(ConfigurableApplicationContext applicationContext, @Qualifier(value="serviceRegistryExecutionPlan") ServiceRegistryExecutionPlan serviceRegistryExecutionPlan, CasConfigurationProperties casProperties, @Qualifier(value="servicesManager") ServicesManager servicesManager) throws Exception {
            return (Runnable)BeanSupplier.of(Runnable.class).when(BeanCondition.on((String)"cas.service-registry.schedule.enabled").isTrue().evenIfMissing().given((PropertyResolver)applicationContext.getEnvironment())).supply(() -> {
                com.google.common.base.Predicate filter = Predicates.not((com.google.common.base.Predicate)Predicates.instanceOf(ImmutableServiceRegistry.class));
                if (!serviceRegistryExecutionPlan.find((Predicate)filter).isEmpty()) {
                    LOGGER.trace("Background task to load services is enabled to run every [{}]", (Object)casProperties.getServiceRegistry().getSchedule().getRepeatInterval());
                    return new ServicesManagerScheduledLoader(servicesManager);
                }
                LOGGER.trace("Background task to load services is disabled");
                return ServicesManagerScheduledLoader.noOp();
            }).otherwiseProxy().get();
        }
    }

    @Configuration(value="CasCoreServicesManagerConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    @AutoConfigureAfter(value={CasCoreServiceRegistryConfiguration.class})
    public static class CasCoreServicesManagerConfiguration {
        @ConditionalOnMissingBean(name={"servicesManager"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public ChainingServicesManager servicesManager(List<ServicesManagerExecutionPlanConfigurer> configurers) {
            DefaultChainingServicesManager chain = new DefaultChainingServicesManager();
            AnnotationAwareOrderComparator.sortIfNecessary(configurers);
            configurers.forEach(c -> chain.registerServiceManager(c.configureServicesManager()));
            return chain;
        }

        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @Bean
        @ConditionalOnMissingBean(name={"servicesManagerCache"})
        public Cache<Long, RegisteredService> servicesManagerCache(CasConfigurationProperties casProperties) {
            ServiceRegistryCacheProperties cacheProperties = casProperties.getServiceRegistry().getCache();
            Caffeine builder = Caffeine.newBuilder();
            Duration duration = Beans.newDuration((String)cacheProperties.getDuration());
            return builder.initialCapacity(cacheProperties.getInitialCapacity()).maximumSize(cacheProperties.getCacheSize()).expireAfterWrite(duration).build();
        }

        @EventListener
        public void refreshServicesManagerWhenReady(ApplicationReadyEvent event) {
            ChainingServicesManager servicesManager = (ChainingServicesManager)event.getApplicationContext().getBean("servicesManager", ChainingServicesManager.class);
            servicesManager.load();
        }
    }

    @Configuration(value="CasCoreServicesManagerExecutionPlanConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreServicesManagerExecutionPlanConfiguration {
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public ServicesManagerConfigurationContext servicesManagerConfigurationContext(@Qualifier(value="serviceRegistry") ChainingServiceRegistry serviceRegistry, @Qualifier(value="servicesManagerCache") Cache<Long, RegisteredService> servicesManagerCache, List<ServicesManagerRegisteredServiceLocator> servicesManagerRegisteredServiceLocators, Environment environment, ConfigurableApplicationContext applicationContext) {
            AnnotationAwareOrderComparator.sortIfNecessary(servicesManagerRegisteredServiceLocators);
            Set activeProfiles = Arrays.stream(environment.getActiveProfiles()).collect(Collectors.toSet());
            return ServicesManagerConfigurationContext.builder().serviceRegistry((ServiceRegistry)serviceRegistry).applicationContext(applicationContext).environments(activeProfiles).servicesCache(servicesManagerCache).registeredServiceLocators(servicesManagerRegisteredServiceLocators).build();
        }

        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @ConditionalOnMissingBean(name={"defaultServicesManagerExecutionPlanConfigurer"})
        public ServicesManagerExecutionPlanConfigurer defaultServicesManagerExecutionPlanConfigurer(CasConfigurationProperties casProperties, @Qualifier(value="servicesManagerConfigurationContext") ServicesManagerConfigurationContext configurationContext) throws Exception {
            return (ServicesManagerExecutionPlanConfigurer)BeanSupplier.of(ServicesManagerExecutionPlanConfigurer.class).alwaysMatch().supply(() -> {
                ServiceRegistryCoreProperties.ServiceManagementTypes managementType = casProperties.getServiceRegistry().getCore().getManagementType();
                if (managementType == ServiceRegistryCoreProperties.ServiceManagementTypes.DOMAIN) {
                    return () -> new DefaultDomainAwareServicesManager(configurationContext, (RegisteredServiceDomainExtractor)new DefaultRegisteredServiceDomainExtractor());
                }
                return () -> new DefaultServicesManager(configurationContext);
            }).get();
        }

        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @ConditionalOnMissingBean(name={"defaultServicesManagerRegisteredServiceLocator"})
        public ServicesManagerRegisteredServiceLocator defaultServicesManagerRegisteredServiceLocator() {
            return new DefaultServicesManagerRegisteredServiceLocator();
        }
    }

    @Configuration(value="CasCoreServiceRegistryConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreServiceRegistryConfiguration {
        private static Optional<List<RegisteredService>> getInMemoryRegisteredServices(ApplicationContext applicationContext) {
            if (applicationContext.containsBean("inMemoryRegisteredServices")) {
                return Optional.of((List)applicationContext.getBean("inMemoryRegisteredServices", List.class));
            }
            return Optional.empty();
        }

        @ConditionalOnMissingBean(name={"serviceRegistry"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public ChainingServiceRegistry serviceRegistry(ConfigurableApplicationContext applicationContext, ObjectProvider<List<ServiceRegistryListener>> serviceRegistryListeners, @Qualifier(value="serviceRegistryExecutionPlan") ServiceRegistryExecutionPlan serviceRegistryExecutionPlan) throws Exception {
            com.google.common.base.Predicate filter = Predicates.not((com.google.common.base.Predicate)Predicates.instanceOf(ImmutableServiceRegistry.class));
            DefaultChainingServiceRegistry chainingRegistry = new DefaultChainingServiceRegistry(applicationContext);
            if (serviceRegistryExecutionPlan.find((Predicate)filter).isEmpty()) {
                LOGGER.info("Runtime memory is used as the persistence storage for retrieving and persisting service definitions. Changes that are made to service definitions during runtime WILL be LOST when the CAS server is restarted. Ideally for production, you should choose a storage option (JSON, JDBC, MongoDb, etc) to track service definitions.");
                List services = CasCoreServiceRegistryConfiguration.getInMemoryRegisteredServices((ApplicationContext)applicationContext).orElseGet(ArrayList::new);
                InMemoryServiceRegistry inMemoryServiceRegistry = new InMemoryServiceRegistry(applicationContext, services, (Collection)Optional.ofNullable((List)serviceRegistryListeners.getIfAvailable()).orElseGet(ArrayList::new));
                chainingRegistry.addServiceRegistry((ServiceRegistry)inMemoryServiceRegistry);
            }
            chainingRegistry.addServiceRegistries(serviceRegistryExecutionPlan.getServiceRegistries());
            return chainingRegistry;
        }
    }

    @Configuration(value="CasCoreServicesListenerConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreServicesListenerConfiguration {
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @ConditionalOnMissingBean(name={"defaultServiceRegistryListener"})
        public ServiceRegistryListener defaultServiceRegistryListener() {
            return ServiceRegistryListener.noOp();
        }
    }

    @Configuration(value="CasCoreServiceRegistryPlanConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreServiceRegistryPlanConfiguration {
        @ConditionalOnMissingBean(name={"serviceRegistryExecutionPlan"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public ServiceRegistryExecutionPlan serviceRegistryExecutionPlan(ObjectProvider<List<ServiceRegistryExecutionPlanConfigurer>> provider) {
            DefaultServiceRegistryExecutionPlan plan = new DefaultServiceRegistryExecutionPlan();
            provider.ifAvailable(configurers -> configurers.stream().filter(BeanSupplier::isNotProxy).forEach(Unchecked.consumer(c -> {
                LOGGER.trace("Configuring service registry [{}]", (Object)c.getName());
                c.configureServiceRegistry((ServiceRegistryExecutionPlan)plan);
            })));
            return plan;
        }
    }

    @Configuration(value="CasCoreServicesStrategyConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreServicesStrategyConfiguration {
        @ConditionalOnMissingBean(name={"registeredServiceReplicationStrategy"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public RegisteredServiceReplicationStrategy registeredServiceReplicationStrategy() {
            return new NoOpRegisteredServiceReplicationStrategy();
        }

        @ConditionalOnMissingBean(name={"registeredServiceResourceNamingStrategy"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public RegisteredServiceResourceNamingStrategy registeredServiceResourceNamingStrategy() {
            return new DefaultRegisteredServiceResourceNamingStrategy();
        }
    }

    @Configuration(value="CasCoreServicesBaseConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreServicesBaseConfiguration {
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @Bean
        @ConditionalOnMissingBean(name={"shibbolethCompatiblePersistentIdGenerator"})
        public PersistentIdGenerator shibbolethCompatiblePersistentIdGenerator() {
            return new ShibbolethCompatiblePersistentIdGenerator();
        }

        @ConditionalOnMissingBean(name={"registeredServiceCipherExecutor"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public RegisteredServiceCipherExecutor registeredServiceCipherExecutor() {
            return new RegisteredServicePublicKeyCipherExecutor();
        }

        @ConditionalOnMissingBean(name={"registeredServiceAccessStrategyEnforcer"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public AuditableExecution registeredServiceAccessStrategyEnforcer(CasConfigurationProperties casProperties) {
            return new RegisteredServiceAccessStrategyAuditableEnforcer(casProperties);
        }
    }

    @Configuration(value="CasCoreServicesResponseBuilderConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreServicesResponseBuilderConfiguration {
        @Bean
        @ConditionalOnMissingBean(name={"webApplicationServiceResponseBuilder"})
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public ResponseBuilder<WebApplicationService> webApplicationServiceResponseBuilder(@Qualifier(value="servicesManager") ServicesManager servicesManager, @Qualifier(value="urlValidator") UrlValidator urlValidator) {
            return new WebApplicationServiceResponseBuilder(servicesManager, urlValidator);
        }
    }

    @Configuration(value="CasCoreServicesEventsConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    @AutoConfigureOrder(value=0x7FFFFFFF)
    public static class CasCoreServicesEventsConfiguration {
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public RegisteredServicesEventListener registeredServicesEventListener(CasConfigurationProperties casProperties, @Qualifier(value="servicesManager") ServicesManager servicesManager, @Qualifier(value="communicationsManager") CommunicationsManager communicationsManager) {
            return new DefaultRegisteredServicesEventListener(servicesManager, casProperties, communicationsManager);
        }
    }

    @Configuration(value="CasCoreServicesResponseLocatorConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreServicesResponseLocatorConfiguration {
        @ConditionalOnMissingBean(name={"webApplicationResponseBuilderLocator"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public ResponseBuilderLocator webApplicationResponseBuilderLocator(List<ResponseBuilder> responseBuilders) {
            AnnotationAwareOrderComparator.sortIfNecessary(responseBuilders);
            return new DefaultWebApplicationResponseBuilderLocator(responseBuilders);
        }
    }
}

