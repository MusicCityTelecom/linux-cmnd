/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer
 *  org.apereo.cas.authentication.AuthenticationPolicy
 *  org.apereo.cas.authentication.PseudoPlatformTransactionManager
 *  org.apereo.cas.authentication.policy.UniquePrincipalAuthenticationPolicy
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.configuration.model.core.authentication.AuthenticationPolicyProperties
 *  org.apereo.cas.configuration.model.core.ticket.registry.InMemoryTicketRegistryProperties
 *  org.apereo.cas.configuration.model.core.util.EncryptionJwtSigningJwtCryptographyProperties
 *  org.apereo.cas.configuration.model.core.util.EncryptionRandomizedSigningJwtCryptographyProperties
 *  org.apereo.cas.logout.LogoutManager
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.ticket.DefaultServiceTicketSessionTrackingPolicy
 *  org.apereo.cas.ticket.DefaultTicketCatalog
 *  org.apereo.cas.ticket.ExpirationPolicyBuilder
 *  org.apereo.cas.ticket.ServiceTicketFactory
 *  org.apereo.cas.ticket.ServiceTicketGeneratorAuthority
 *  org.apereo.cas.ticket.ServiceTicketSessionTrackingPolicy
 *  org.apereo.cas.ticket.TicketCatalog
 *  org.apereo.cas.ticket.TicketCatalogConfigurer
 *  org.apereo.cas.ticket.TicketFactory
 *  org.apereo.cas.ticket.TicketFactoryExecutionPlanConfigurer
 *  org.apereo.cas.ticket.TicketGrantingTicketFactory
 *  org.apereo.cas.ticket.TransientSessionTicketFactory
 *  org.apereo.cas.ticket.UniqueTicketIdGenerator
 *  org.apereo.cas.ticket.expiration.builder.ProxyGrantingTicketExpirationPolicyBuilder
 *  org.apereo.cas.ticket.expiration.builder.ProxyTicketExpirationPolicyBuilder
 *  org.apereo.cas.ticket.expiration.builder.ServiceTicketExpirationPolicyBuilder
 *  org.apereo.cas.ticket.expiration.builder.TicketGrantingTicketExpirationPolicyBuilder
 *  org.apereo.cas.ticket.expiration.builder.TransientSessionTicketExpirationPolicyBuilder
 *  org.apereo.cas.ticket.factory.DefaultProxyGrantingTicketFactory
 *  org.apereo.cas.ticket.factory.DefaultProxyTicketFactory
 *  org.apereo.cas.ticket.factory.DefaultServiceTicketFactory
 *  org.apereo.cas.ticket.factory.DefaultTicketFactory
 *  org.apereo.cas.ticket.factory.DefaultTicketGrantingTicketFactory
 *  org.apereo.cas.ticket.factory.DefaultTransientSessionTicketFactory
 *  org.apereo.cas.ticket.proxy.ProxyGrantingTicketFactory
 *  org.apereo.cas.ticket.proxy.ProxyTicketFactory
 *  org.apereo.cas.ticket.registry.CachingTicketRegistry
 *  org.apereo.cas.ticket.registry.DefaultTicketRegistry
 *  org.apereo.cas.ticket.registry.DefaultTicketRegistrySupport
 *  org.apereo.cas.ticket.registry.TicketRegistry
 *  org.apereo.cas.ticket.registry.TicketRegistrySupport
 *  org.apereo.cas.util.CoreTicketUtils
 *  org.apereo.cas.util.ProxyGrantingTicketIdGenerator
 *  org.apereo.cas.util.ProxyTicketIdGenerator
 *  org.apereo.cas.util.TicketGrantingTicketIdGenerator
 *  org.apereo.cas.util.cipher.CipherExecutorUtils
 *  org.apereo.cas.util.cipher.ProtocolTicketCipherExecutor
 *  org.apereo.cas.util.crypto.CipherExecutor
 *  org.apereo.cas.util.lock.LockRepository
 *  org.apereo.cas.util.spring.beans.BeanCondition
 *  org.apereo.cas.util.spring.beans.BeanSupplier
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.autoconfigure.AutoConfigureOrder
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.EnableAspectJAutoProxy
 *  org.springframework.context.annotation.ScopedProxyMode
 *  org.springframework.core.env.PropertyResolver
 *  org.springframework.scheduling.annotation.EnableAsync
 *  org.springframework.scheduling.annotation.EnableScheduling
 *  org.springframework.transaction.PlatformTransactionManager
 *  org.springframework.transaction.annotation.EnableTransactionManagement
 */
package org.apereo.cas.config;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationPolicy;
import org.apereo.cas.authentication.PseudoPlatformTransactionManager;
import org.apereo.cas.authentication.policy.UniquePrincipalAuthenticationPolicy;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.configuration.model.core.authentication.AuthenticationPolicyProperties;
import org.apereo.cas.configuration.model.core.ticket.registry.InMemoryTicketRegistryProperties;
import org.apereo.cas.configuration.model.core.util.EncryptionJwtSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.model.core.util.EncryptionRandomizedSigningJwtCryptographyProperties;
import org.apereo.cas.logout.LogoutManager;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.ticket.DefaultServiceTicketSessionTrackingPolicy;
import org.apereo.cas.ticket.DefaultTicketCatalog;
import org.apereo.cas.ticket.ExpirationPolicyBuilder;
import org.apereo.cas.ticket.ServiceTicketFactory;
import org.apereo.cas.ticket.ServiceTicketGeneratorAuthority;
import org.apereo.cas.ticket.ServiceTicketSessionTrackingPolicy;
import org.apereo.cas.ticket.TicketCatalog;
import org.apereo.cas.ticket.TicketCatalogConfigurer;
import org.apereo.cas.ticket.TicketFactory;
import org.apereo.cas.ticket.TicketFactoryExecutionPlanConfigurer;
import org.apereo.cas.ticket.TicketGrantingTicketFactory;
import org.apereo.cas.ticket.TransientSessionTicketFactory;
import org.apereo.cas.ticket.UniqueTicketIdGenerator;
import org.apereo.cas.ticket.expiration.builder.ProxyGrantingTicketExpirationPolicyBuilder;
import org.apereo.cas.ticket.expiration.builder.ProxyTicketExpirationPolicyBuilder;
import org.apereo.cas.ticket.expiration.builder.ServiceTicketExpirationPolicyBuilder;
import org.apereo.cas.ticket.expiration.builder.TicketGrantingTicketExpirationPolicyBuilder;
import org.apereo.cas.ticket.expiration.builder.TransientSessionTicketExpirationPolicyBuilder;
import org.apereo.cas.ticket.factory.DefaultProxyGrantingTicketFactory;
import org.apereo.cas.ticket.factory.DefaultProxyTicketFactory;
import org.apereo.cas.ticket.factory.DefaultServiceTicketFactory;
import org.apereo.cas.ticket.factory.DefaultTicketFactory;
import org.apereo.cas.ticket.factory.DefaultTicketGrantingTicketFactory;
import org.apereo.cas.ticket.factory.DefaultTransientSessionTicketFactory;
import org.apereo.cas.ticket.proxy.ProxyGrantingTicketFactory;
import org.apereo.cas.ticket.proxy.ProxyTicketFactory;
import org.apereo.cas.ticket.registry.CachingTicketRegistry;
import org.apereo.cas.ticket.registry.DefaultTicketRegistry;
import org.apereo.cas.ticket.registry.DefaultTicketRegistrySupport;
import org.apereo.cas.ticket.registry.TicketRegistry;
import org.apereo.cas.ticket.registry.TicketRegistrySupport;
import org.apereo.cas.util.CoreTicketUtils;
import org.apereo.cas.util.ProxyGrantingTicketIdGenerator;
import org.apereo.cas.util.ProxyTicketIdGenerator;
import org.apereo.cas.util.TicketGrantingTicketIdGenerator;
import org.apereo.cas.util.cipher.CipherExecutorUtils;
import org.apereo.cas.util.cipher.ProtocolTicketCipherExecutor;
import org.apereo.cas.util.crypto.CipherExecutor;
import org.apereo.cas.util.lock.LockRepository;
import org.apereo.cas.util.spring.beans.BeanCondition;
import org.apereo.cas.util.spring.beans.BeanSupplier;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.PropertyResolver;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@EnableScheduling
@EnableAsync(proxyTargetClass=false)
@EnableAspectJAutoProxy(proxyTargetClass=false)
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.TicketRegistry)
@AutoConfiguration
public class CasCoreTicketsConfiguration {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CasCoreTicketsConfiguration.class);

    @Configuration(value="CasCoreTicketLockingConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreTicketLockingConfiguration {
        @Bean
        @ConditionalOnMissingBean(name={"casTicketRegistryLockRepository"})
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public LockRepository casTicketRegistryLockRepository(ConfigurableApplicationContext applicationContext) throws Exception {
            return (LockRepository)BeanSupplier.of(LockRepository.class).when(BeanCondition.on((String)"cas.ticket.registry.core.enable-locking").isTrue().evenIfMissing().given((PropertyResolver)applicationContext.getEnvironment())).supply(LockRepository::asDefault).otherwise(LockRepository::noOp).get();
        }
    }

    @Configuration(value="CasCoreTicketTransactionConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    @EnableTransactionManagement(proxyTargetClass=false)
    @AutoConfigureOrder(value=0x7FFFFFFF)
    public static class CasCoreTicketTransactionConfiguration {
        @ConditionalOnMissingBean(name={"ticketTransactionManager"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public PlatformTransactionManager ticketTransactionManager() {
            return new PseudoPlatformTransactionManager();
        }
    }

    @Configuration(value="CasCoreTicketExpirationPolicyConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreTicketExpirationPolicyConfiguration {
        @ConditionalOnMissingBean(name={"transientSessionTicketExpirationPolicy"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public ExpirationPolicyBuilder transientSessionTicketExpirationPolicy(CasConfigurationProperties casProperties) {
            return new TransientSessionTicketExpirationPolicyBuilder(casProperties);
        }

        @ConditionalOnMissingBean(name={"grantingTicketExpirationPolicy"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public ExpirationPolicyBuilder grantingTicketExpirationPolicy(CasConfigurationProperties casProperties) {
            return new TicketGrantingTicketExpirationPolicyBuilder(casProperties);
        }

        @ConditionalOnMissingBean(name={"proxyGrantingTicketExpirationPolicy"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public ExpirationPolicyBuilder proxyGrantingTicketExpirationPolicy(CasConfigurationProperties casProperties) {
            TicketGrantingTicketExpirationPolicyBuilder grantingTicketExpirationPolicy = new TicketGrantingTicketExpirationPolicyBuilder(casProperties);
            return new ProxyGrantingTicketExpirationPolicyBuilder((ExpirationPolicyBuilder)grantingTicketExpirationPolicy, casProperties);
        }

        @ConditionalOnMissingBean(name={"serviceTicketExpirationPolicy"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public ExpirationPolicyBuilder serviceTicketExpirationPolicy(CasConfigurationProperties casProperties) {
            return new ServiceTicketExpirationPolicyBuilder(casProperties);
        }

        @ConditionalOnMissingBean(name={"proxyTicketExpirationPolicy"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public ExpirationPolicyBuilder proxyTicketExpirationPolicy(CasConfigurationProperties casProperties) {
            return new ProxyTicketExpirationPolicyBuilder(casProperties);
        }
    }

    @Configuration(value="CasCoreTicketPlanConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreTicketPlanConfiguration {
        @ConditionalOnMissingBean(name={"defaultTicketFactory"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public TicketFactory defaultTicketFactory(List<TicketFactoryExecutionPlanConfigurer> configurers) {
            DefaultTicketFactory parentFactory = new DefaultTicketFactory();
            configurers.forEach(configurer -> {
                TicketFactory factory = configurer.configureTicketFactory();
                LOGGER.trace("Registering ticket factory via [{}]", (Object)factory.getName());
                parentFactory.addTicketFactory(factory.getTicketType(), factory);
            });
            return parentFactory;
        }
    }

    @Configuration(value="CasCoreProxyGrantingTicketFactoryConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreProxyGrantingTicketFactoryConfiguration {
        @ConditionalOnMissingBean(name={"defaultProxyGrantingTicketFactory"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public ProxyGrantingTicketFactory defaultProxyGrantingTicketFactory(@Qualifier(value="proxyGrantingTicketExpirationPolicy") ExpirationPolicyBuilder proxyGrantingTicketExpirationPolicy, @Qualifier(value="proxyGrantingTicketUniqueIdGenerator") UniqueTicketIdGenerator proxyGrantingTicketUniqueIdGenerator, @Qualifier(value="protocolTicketCipherExecutor") CipherExecutor protocolTicketCipherExecutor, @Qualifier(value="servicesManager") ServicesManager servicesManager) {
            return new DefaultProxyGrantingTicketFactory(proxyGrantingTicketUniqueIdGenerator, proxyGrantingTicketExpirationPolicy, protocolTicketCipherExecutor, servicesManager);
        }
    }

    @Configuration(value="CasCoreTransientSessionTicketFactoryConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreTransientSessionTicketFactoryConfiguration {
        @ConditionalOnMissingBean(name={"defaultTransientSessionTicketFactory"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public TransientSessionTicketFactory defaultTransientSessionTicketFactory(@Qualifier(value="transientSessionTicketExpirationPolicy") ExpirationPolicyBuilder transientSessionTicketExpirationPolicy) {
            return new DefaultTransientSessionTicketFactory(transientSessionTicketExpirationPolicy);
        }
    }

    @Configuration(value="CasCoreProxyTicketFactoryConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreProxyTicketFactoryConfiguration {
        @ConditionalOnMissingBean(name={"defaultProxyTicketFactory"})
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @Bean
        public ProxyTicketFactory defaultProxyTicketFactory(@Qualifier(value="serviceTicketSessionTrackingPolicy") ServiceTicketSessionTrackingPolicy serviceTicketSessionTrackingPolicy, @Qualifier(value="protocolTicketCipherExecutor") CipherExecutor protocolTicketCipherExecutor, @Qualifier(value="proxyTicketExpirationPolicy") ExpirationPolicyBuilder proxyTicketExpirationPolicy, @Qualifier(value="uniqueIdGeneratorsMap") Map<String, UniqueTicketIdGenerator> uniqueIdGeneratorsMap, @Qualifier(value="servicesManager") ServicesManager servicesManager) {
            return new DefaultProxyTicketFactory(proxyTicketExpirationPolicy, uniqueIdGeneratorsMap, protocolTicketCipherExecutor, serviceTicketSessionTrackingPolicy, servicesManager);
        }
    }

    @Configuration(value="CasCoreServiceTicketFactoryConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreServiceTicketFactoryConfiguration {
        @ConditionalOnMissingBean(name={"defaultServiceTicketFactory"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public ServiceTicketFactory defaultServiceTicketFactory(@Qualifier(value="serviceTicketSessionTrackingPolicy") ServiceTicketSessionTrackingPolicy serviceTicketSessionTrackingPolicy, @Qualifier(value="protocolTicketCipherExecutor") CipherExecutor protocolTicketCipherExecutor, @Qualifier(value="serviceTicketExpirationPolicy") ExpirationPolicyBuilder serviceTicketExpirationPolicy, @Qualifier(value="servicesManager") ServicesManager servicesManager, @Qualifier(value="uniqueIdGeneratorsMap") Map<String, UniqueTicketIdGenerator> uniqueIdGeneratorsMap) {
            return new DefaultServiceTicketFactory(serviceTicketExpirationPolicy, uniqueIdGeneratorsMap, serviceTicketSessionTrackingPolicy, protocolTicketCipherExecutor, servicesManager);
        }

        @ConditionalOnMissingBean(name={"defaultServiceTicketGeneratorAuthority"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public ServiceTicketGeneratorAuthority defaultServiceTicketGeneratorAuthority() {
            return ServiceTicketGeneratorAuthority.allow();
        }
    }

    @Configuration(value="CasCoreTicketGrantingTicketFactoryConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreTicketGrantingTicketFactoryConfiguration {
        @ConditionalOnMissingBean(name={"defaultTicketGrantingTicketFactory"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public TicketGrantingTicketFactory defaultTicketGrantingTicketFactory(@Qualifier(value="grantingTicketExpirationPolicy") ExpirationPolicyBuilder grantingTicketExpirationPolicy, @Qualifier(value="protocolTicketCipherExecutor") CipherExecutor protocolTicketCipherExecutor, @Qualifier(value="ticketGrantingTicketUniqueIdGenerator") UniqueTicketIdGenerator ticketGrantingTicketUniqueIdGenerator, @Qualifier(value="servicesManager") ServicesManager servicesManager) {
            return new DefaultTicketGrantingTicketFactory(ticketGrantingTicketUniqueIdGenerator, grantingTicketExpirationPolicy, protocolTicketCipherExecutor, servicesManager);
        }
    }

    @Configuration(value="CasCoreTicketGrantingTicketExecutionPlanConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreTicketGrantingTicketExecutionPlanConfiguration {
        @ConditionalOnMissingBean(name={"defaultTicketGrantingTicketFactoryConfigurer"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public TicketFactoryExecutionPlanConfigurer defaultTicketGrantingTicketFactoryConfigurer(@Qualifier(value="defaultTicketGrantingTicketFactory") TicketGrantingTicketFactory defaultTicketGrantingTicketFactory) {
            return () -> defaultTicketGrantingTicketFactory;
        }
    }

    @Configuration(value="CasCoreTransientSessionTicketExecutionPlanConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreTransientSessionTicketExecutionPlanConfiguration {
        @ConditionalOnMissingBean(name={"defaultTransientSessionTicketFactoryConfigurer"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public TicketFactoryExecutionPlanConfigurer defaultTransientSessionTicketFactoryConfigurer(@Qualifier(value="defaultTransientSessionTicketFactory") TransientSessionTicketFactory defaultTransientSessionTicketFactory) {
            return () -> defaultTransientSessionTicketFactory;
        }
    }

    @Configuration(value="CasCoreServiceTicketExecutionPlanConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreServiceTicketExecutionPlanConfiguration {
        @ConditionalOnMissingBean(name={"defaultServiceTicketFactoryConfigurer"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public TicketFactoryExecutionPlanConfigurer defaultServiceTicketFactoryConfigurer(@Qualifier(value="defaultServiceTicketFactory") ServiceTicketFactory defaultServiceTicketFactory) {
            return () -> defaultServiceTicketFactory;
        }
    }

    @Configuration(value="CasCoreProxyTicketExecutionPlanConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreProxyTicketExecutionPlanConfiguration {
        @ConditionalOnMissingBean(name={"defaultProxyTicketFactoryConfigurer"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public TicketFactoryExecutionPlanConfigurer defaultProxyTicketFactoryConfigurer(@Qualifier(value="defaultProxyTicketFactory") ProxyTicketFactory defaultProxyTicketFactory) {
            return () -> defaultProxyTicketFactory;
        }
    }

    @Configuration(value="CasCoreProxyGrantingTicketExecutionPlanConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreProxyGrantingTicketExecutionPlanConfiguration {
        @ConditionalOnMissingBean(name={"defaultProxyGrantingTicketFactoryConfigurer"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public TicketFactoryExecutionPlanConfigurer defaultProxyGrantingTicketFactoryConfigurer(@Qualifier(value="defaultProxyGrantingTicketFactory") ProxyGrantingTicketFactory defaultProxyGrantingTicketFactory) {
            return () -> defaultProxyGrantingTicketFactory;
        }
    }

    @Configuration(value="CasCoreTicketIdGeneratorConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreTicketIdGeneratorConfiguration {
        @ConditionalOnMissingBean(name={"proxyGrantingTicketUniqueIdGenerator"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public UniqueTicketIdGenerator proxyGrantingTicketUniqueIdGenerator(CasConfigurationProperties casProperties) {
            return new ProxyGrantingTicketIdGenerator(casProperties.getTicket().getTgt().getCore().getMaxLength(), casProperties.getHost().getName());
        }

        @ConditionalOnMissingBean(name={"ticketGrantingTicketUniqueIdGenerator"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public UniqueTicketIdGenerator ticketGrantingTicketUniqueIdGenerator(CasConfigurationProperties casProperties) {
            return new TicketGrantingTicketIdGenerator(casProperties.getTicket().getTgt().getCore().getMaxLength(), casProperties.getHost().getName());
        }

        @ConditionalOnMissingBean(name={"proxy20TicketUniqueIdGenerator"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public UniqueTicketIdGenerator proxy20TicketUniqueIdGenerator(CasConfigurationProperties casProperties) {
            return new ProxyTicketIdGenerator(casProperties.getTicket().getPgt().getMaxLength(), casProperties.getHost().getName());
        }
    }

    @Configuration(value="CasCoreTicketRegistryConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreTicketRegistryConfiguration {
        @ConditionalOnMissingBean(name={"ticketRegistry"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public TicketRegistry ticketRegistry(@Qualifier(value="logoutManager") ObjectProvider<LogoutManager> logoutManager, CasConfigurationProperties casProperties) {
            LOGGER.info("Runtime memory is used as the persistence storage for retrieving and managing tickets. Tickets that are issued during runtime will be LOST when the web server is restarted. This MAY impact SSO functionality.");
            InMemoryTicketRegistryProperties mem = casProperties.getTicket().getRegistry().getInMemory();
            CipherExecutor cipher = CoreTicketUtils.newTicketRegistryCipherExecutor((EncryptionRandomizedSigningJwtCryptographyProperties)mem.getCrypto(), (String)"in-memory");
            if (mem.isCache()) {
                return new CachingTicketRegistry(cipher, logoutManager);
            }
            ConcurrentHashMap storageMap = new ConcurrentHashMap(mem.getInitialCapacity(), mem.getLoadFactor(), mem.getConcurrency());
            return new DefaultTicketRegistry(storageMap, cipher);
        }

        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        @Bean
        @ConditionalOnMissingBean(name={"protocolTicketCipherExecutor"})
        public CipherExecutor protocolTicketCipherExecutor(CasConfigurationProperties casProperties) {
            EncryptionJwtSigningJwtCryptographyProperties crypto = casProperties.getTicket().getCrypto();
            if (crypto.isEnabled()) {
                return CipherExecutorUtils.newStringCipherExecutor((EncryptionJwtSigningJwtCryptographyProperties)crypto, ProtocolTicketCipherExecutor.class);
            }
            LOGGER.trace("Protocol tickets generated by CAS are not signed/encrypted.");
            return CipherExecutor.noOp();
        }

        @ConditionalOnMissingBean(name={"ticketCatalog"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public TicketCatalog ticketCatalog(CasConfigurationProperties casProperties, List<TicketCatalogConfigurer> configurers) {
            DefaultTicketCatalog plan = new DefaultTicketCatalog();
            configurers.forEach(c -> {
                LOGGER.trace("Configuring ticket metadata registration plan [{}]", (Object)c.getName());
                c.configureTicketCatalog((TicketCatalog)plan, casProperties);
            });
            return plan;
        }
    }

    @Configuration(value="CasCoreTicketsAuthenticationPlanConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreTicketsAuthenticationPlanConfiguration {
        @ConditionalOnMissingBean(name={"ticketAuthenticationPolicyExecutionPlanConfigurer"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public AuthenticationEventExecutionPlanConfigurer ticketAuthenticationPolicyExecutionPlanConfigurer(@Qualifier(value="ticketRegistry") TicketRegistry ticketRegistry, CasConfigurationProperties casProperties) {
            return plan -> {
                AuthenticationPolicyProperties policyProps = casProperties.getAuthn().getPolicy();
                if (policyProps.getUniquePrincipal().isEnabled()) {
                    LOGGER.trace("Activating authentication policy [{}]", (Object)UniquePrincipalAuthenticationPolicy.class.getSimpleName());
                    plan.registerAuthenticationPolicy((AuthenticationPolicy)new UniquePrincipalAuthenticationPolicy(ticketRegistry));
                }
            };
        }
    }

    @Configuration(value="CasCoreTicketsBaseConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreTicketsBaseConfiguration {
        @ConditionalOnMissingBean(name={"serviceTicketSessionTrackingPolicy"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public ServiceTicketSessionTrackingPolicy serviceTicketSessionTrackingPolicy(@Qualifier(value="ticketRegistry") TicketRegistry ticketRegistry, CasConfigurationProperties casProperties) {
            return new DefaultServiceTicketSessionTrackingPolicy(casProperties, ticketRegistry);
        }

        @ConditionalOnMissingBean(name={"defaultTicketRegistrySupport"})
        @Bean
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public TicketRegistrySupport defaultTicketRegistrySupport(@Qualifier(value="ticketRegistry") TicketRegistry ticketRegistry) {
            return new DefaultTicketRegistrySupport(ticketRegistry);
        }
    }
}

