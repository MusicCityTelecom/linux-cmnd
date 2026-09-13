/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.ticket.BaseTicketCatalogConfigurer
 *  org.apereo.cas.ticket.ProxyGrantingTicketImpl
 *  org.apereo.cas.ticket.ProxyTicketImpl
 *  org.apereo.cas.ticket.ServiceTicket
 *  org.apereo.cas.ticket.ServiceTicketImpl
 *  org.apereo.cas.ticket.TicketCatalog
 *  org.apereo.cas.ticket.TicketDefinition
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.apereo.cas.ticket.TicketGrantingTicketImpl
 *  org.apereo.cas.ticket.TransientSessionTicket
 *  org.apereo.cas.ticket.TransientSessionTicketImpl
 *  org.apereo.cas.ticket.proxy.ProxyGrantingTicket
 *  org.apereo.cas.ticket.proxy.ProxyTicket
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 */
package org.apereo.cas.config;

import lombok.Generated;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.ticket.BaseTicketCatalogConfigurer;
import org.apereo.cas.ticket.ProxyGrantingTicketImpl;
import org.apereo.cas.ticket.ProxyTicketImpl;
import org.apereo.cas.ticket.ServiceTicket;
import org.apereo.cas.ticket.ServiceTicketImpl;
import org.apereo.cas.ticket.TicketCatalog;
import org.apereo.cas.ticket.TicketDefinition;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.TicketGrantingTicketImpl;
import org.apereo.cas.ticket.TransientSessionTicket;
import org.apereo.cas.ticket.TransientSessionTicketImpl;
import org.apereo.cas.ticket.proxy.ProxyGrantingTicket;
import org.apereo.cas.ticket.proxy.ProxyTicket;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.TicketRegistry)
@AutoConfiguration
public class CasCoreTicketCatalogConfiguration
extends BaseTicketCatalogConfigurer {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CasCoreTicketCatalogConfiguration.class);

    public final void configureTicketCatalog(TicketCatalog plan, CasConfigurationProperties casProperties) {
        LOGGER.trace("Registering core CAS protocol ticket definitions...");
        this.buildAndRegisterProxyTicketDefinition(plan, this.buildTicketDefinition(plan, "PT", ProxyTicket.class, ProxyTicketImpl.class, Integer.MIN_VALUE));
        this.buildAndRegisterServiceTicketDefinition(plan, this.buildTicketDefinition(plan, "ST", ServiceTicket.class, ServiceTicketImpl.class, Integer.MIN_VALUE));
        this.buildAndRegisterProxyGrantingTicketDefinition(plan, this.buildTicketDefinition(plan, "PGT", ProxyGrantingTicket.class, ProxyGrantingTicketImpl.class, Integer.MAX_VALUE));
        this.buildAndRegisterTicketGrantingTicketDefinition(plan, this.buildTicketDefinition(plan, "TGT", TicketGrantingTicket.class, TicketGrantingTicketImpl.class, Integer.MAX_VALUE));
        this.buildAndRegisterTransientSessionTicketDefinition(plan, this.buildTicketDefinition(plan, "TST", TransientSessionTicket.class, TransientSessionTicketImpl.class, Integer.MAX_VALUE));
    }

    protected void buildAndRegisterProxyGrantingTicketDefinition(TicketCatalog plan, TicketDefinition metadata) {
        this.registerTicketDefinition(plan, metadata);
    }

    protected void buildAndRegisterProxyTicketDefinition(TicketCatalog plan, TicketDefinition metadata) {
        this.registerTicketDefinition(plan, metadata);
    }

    protected void buildAndRegisterServiceTicketDefinition(TicketCatalog plan, TicketDefinition metadata) {
        this.registerTicketDefinition(plan, metadata);
    }

    protected void buildAndRegisterTicketGrantingTicketDefinition(TicketCatalog plan, TicketDefinition metadata) {
        this.registerTicketDefinition(plan, metadata);
    }

    protected void buildAndRegisterTransientSessionTicketDefinition(TicketCatalog plan, TicketDefinition metadata) {
        metadata.getProperties().setExcludeFromCascade(true);
        this.registerTicketDefinition(plan, metadata);
    }
}

