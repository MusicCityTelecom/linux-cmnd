/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.logout.LogoutManager
 *  org.apereo.cas.ticket.registry.DefaultTicketRegistryCleaner
 *  org.apereo.cas.ticket.registry.NoOpTicketRegistryCleaner
 *  org.apereo.cas.ticket.registry.TicketRegistry
 *  org.apereo.cas.ticket.registry.TicketRegistryCleaner
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.apereo.cas.util.lock.LockRepository
 *  org.apereo.cas.util.spring.beans.BeanCondition
 *  org.apereo.cas.util.spring.beans.BeanSupplier
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.apereo.cas.util.spring.boot.ConditionalOnMatchingHostname
 *  org.apereo.inspektr.common.Cleanable
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.ScopedProxyMode
 *  org.springframework.core.env.PropertyResolver
 *  org.springframework.scheduling.annotation.EnableAsync
 *  org.springframework.scheduling.annotation.EnableScheduling
 *  org.springframework.scheduling.annotation.Scheduled
 *  org.springframework.transaction.annotation.EnableTransactionManagement
 */
package org.apereo.cas.config;

import lombok.Generated;
import org.apereo.cas.config.CasCoreTicketsConfiguration;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.logout.LogoutManager;
import org.apereo.cas.ticket.registry.DefaultTicketRegistryCleaner;
import org.apereo.cas.ticket.registry.NoOpTicketRegistryCleaner;
import org.apereo.cas.ticket.registry.TicketRegistry;
import org.apereo.cas.ticket.registry.TicketRegistryCleaner;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.lock.LockRepository;
import org.apereo.cas.util.spring.beans.BeanCondition;
import org.apereo.cas.util.spring.beans.BeanSupplier;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.apereo.cas.util.spring.boot.ConditionalOnMatchingHostname;
import org.apereo.inspektr.common.Cleanable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.PropertyResolver;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@EnableScheduling
@EnableAsync(proxyTargetClass=false)
@EnableTransactionManagement(proxyTargetClass=false)
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.TicketRegistry)
@AutoConfiguration(after={CasCoreTicketsConfiguration.class})
public class CasCoreTicketsSchedulingConfiguration {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CasCoreTicketsSchedulingConfiguration.class);

    @ConditionalOnMissingBean(name={"ticketRegistryCleaner"})
    @Bean
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    public TicketRegistryCleaner ticketRegistryCleaner(CasConfigurationProperties casProperties, @Qualifier(value="casTicketRegistryLockRepository") LockRepository lockRepository, @Qualifier(value="logoutManager") LogoutManager logoutManager, @Qualifier(value="ticketRegistry") TicketRegistry ticketRegistry) {
        boolean isCleanerEnabled = casProperties.getTicket().getRegistry().getCleaner().getSchedule().isEnabled();
        if (isCleanerEnabled) {
            LOGGER.debug("Ticket registry cleaner is enabled.");
            return new DefaultTicketRegistryCleaner(lockRepository, logoutManager, ticketRegistry);
        }
        LOGGER.debug("Ticket registry cleaner is not enabled. Expired tickets are not forcefully cleaned by CAS. It is up to the ticket registry itself to clean up tickets based on its own expiration and eviction policies.");
        return NoOpTicketRegistryCleaner.getInstance();
    }

    @ConditionalOnMissingBean(name={"ticketRegistryCleanerScheduler"})
    @ConditionalOnMatchingHostname(name="cas.ticket.registry.cleaner.schedule.enabled-on-host")
    @Bean
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    public Cleanable ticketRegistryCleanerScheduler(ConfigurableApplicationContext applicationContext, @Qualifier(value="ticketRegistryCleaner") TicketRegistryCleaner ticketRegistryCleaner) throws Exception {
        return (Cleanable)BeanSupplier.of(Cleanable.class).when(BeanCondition.on((String)"cas.ticket.registry.cleaner.schedule.enabled").isTrue().evenIfMissing().given((PropertyResolver)applicationContext.getEnvironment())).supply(() -> new TicketRegistryCleanerScheduler(ticketRegistryCleaner)).otherwiseProxy().get();
    }

    public static class TicketRegistryCleanerScheduler
    implements Cleanable {
        private final TicketRegistryCleaner ticketRegistryCleaner;

        @Scheduled(initialDelayString="${cas.ticket.registry.cleaner.schedule.start-delay:PT30S}", fixedDelayString="${cas.ticket.registry.cleaner.schedule.repeat-interval:PT120S}")
        public void clean() {
            FunctionUtils.doAndHandle(unused -> this.ticketRegistryCleaner.clean());
        }

        @Generated
        public TicketRegistryCleanerScheduler(TicketRegistryCleaner ticketRegistryCleaner) {
            this.ticketRegistryCleaner = ticketRegistryCleaner;
        }
    }
}

