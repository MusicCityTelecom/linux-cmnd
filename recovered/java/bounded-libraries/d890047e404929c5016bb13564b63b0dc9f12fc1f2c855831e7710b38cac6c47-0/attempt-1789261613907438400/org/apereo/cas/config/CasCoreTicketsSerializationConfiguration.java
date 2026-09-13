/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.ticket.serialization.DefaultTicketSerializationExecutionPlan
 *  org.apereo.cas.ticket.serialization.DefaultTicketStringSerializationManager
 *  org.apereo.cas.ticket.serialization.TicketSerializationExecutionPlan
 *  org.apereo.cas.ticket.serialization.TicketSerializationExecutionPlanConfigurer
 *  org.apereo.cas.ticket.serialization.TicketSerializationManager
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
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 */
package org.apereo.cas.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.ticket.serialization.DefaultTicketSerializationExecutionPlan;
import org.apereo.cas.ticket.serialization.DefaultTicketStringSerializationManager;
import org.apereo.cas.ticket.serialization.TicketSerializationExecutionPlan;
import org.apereo.cas.ticket.serialization.TicketSerializationExecutionPlanConfigurer;
import org.apereo.cas.ticket.serialization.TicketSerializationManager;
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
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.TicketRegistry)
@AutoConfiguration
public class CasCoreTicketsSerializationConfiguration {

    @Configuration(value="CasCoreTicketsSerializationManagementConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreTicketsSerializationManagementConfiguration {
        @Bean
        @ConditionalOnMissingBean(name={"ticketSerializationManager"})
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public TicketSerializationManager ticketSerializationManager(@Qualifier(value="ticketSerializationExecutionPlan") TicketSerializationExecutionPlan ticketSerializationExecutionPlan) {
            return new DefaultTicketStringSerializationManager(ticketSerializationExecutionPlan);
        }
    }

    @Configuration(value="CasCoreTicketsSerializationPlanConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreTicketsSerializationPlanConfiguration {
        @Bean
        @ConditionalOnMissingBean(name={"ticketSerializationExecutionPlan"})
        @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
        public TicketSerializationExecutionPlan ticketSerializationExecutionPlan(ObjectProvider<List<TicketSerializationExecutionPlanConfigurer>> providerList) {
            List providers = Optional.ofNullable((List)providerList.getIfAvailable()).orElseGet(ArrayList::new);
            AnnotationAwareOrderComparator.sort((List)providers);
            DefaultTicketSerializationExecutionPlan plan = new DefaultTicketSerializationExecutionPlan();
            providers.forEach(provider -> provider.configureTicketSerialization((TicketSerializationExecutionPlan)plan));
            return plan;
        }
    }
}

