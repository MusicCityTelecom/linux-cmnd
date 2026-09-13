/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 *  org.apereo.cas.authentication.principal.SimpleWebApplicationServiceImpl
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.ticket.UniqueTicketIdGenerator
 *  org.apereo.cas.ticket.UniqueTicketIdGeneratorConfigurer
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.ServiceTicketIdGenerator
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.ScopedProxyMode
 */
package org.apereo.cas.config;

import org.apache.commons.lang3.tuple.Pair;
import org.apereo.cas.authentication.principal.SimpleWebApplicationServiceImpl;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.ticket.UniqueTicketIdGenerator;
import org.apereo.cas.ticket.UniqueTicketIdGeneratorConfigurer;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.ServiceTicketIdGenerator;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;

@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.TicketRegistry)
@AutoConfiguration
public class CasDefaultServiceTicketIdGeneratorsConfiguration {
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    @Bean
    public UniqueTicketIdGenerator serviceTicketUniqueIdGenerator(CasConfigurationProperties casProperties) {
        return new ServiceTicketIdGenerator(casProperties.getTicket().getSt().getMaxLength(), casProperties.getHost().getName());
    }

    @Bean
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    public UniqueTicketIdGeneratorConfigurer casDefaultServiceTicketUniqueTicketIdGeneratorConfigurer(@Qualifier(value="serviceTicketUniqueIdGenerator") UniqueTicketIdGenerator serviceTicketUniqueIdGenerator) {
        return () -> CollectionUtils.wrap((Object)Pair.of((Object)SimpleWebApplicationServiceImpl.class.getName(), (Object)serviceTicketUniqueIdGenerator));
    }
}

