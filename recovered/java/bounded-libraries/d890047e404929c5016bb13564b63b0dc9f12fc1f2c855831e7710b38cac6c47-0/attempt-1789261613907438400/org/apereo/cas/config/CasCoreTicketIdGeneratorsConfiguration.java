/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.ticket.UniqueTicketIdGenerator
 *  org.apereo.cas.ticket.UniqueTicketIdGeneratorConfigurer
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.ScopedProxyMode
 */
package org.apereo.cas.config;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.ticket.UniqueTicketIdGenerator;
import org.apereo.cas.ticket.UniqueTicketIdGeneratorConfigurer;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;

@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.TicketRegistry)
@AutoConfiguration
public class CasCoreTicketIdGeneratorsConfiguration {
    @Bean
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    public Map<String, UniqueTicketIdGenerator> uniqueIdGeneratorsMap(ObjectProvider<List<UniqueTicketIdGeneratorConfigurer>> configurers) {
        HashMap<String, UniqueTicketIdGenerator> map = new HashMap<String, UniqueTicketIdGenerator>();
        configurers.ifAvailable(cfgs -> cfgs.forEach(c -> {
            Collection pair = c.buildUniqueTicketIdGenerators();
            pair.forEach(p -> map.put((String)p.getKey(), (UniqueTicketIdGenerator)p.getValue()));
        }));
        return map;
    }
}

