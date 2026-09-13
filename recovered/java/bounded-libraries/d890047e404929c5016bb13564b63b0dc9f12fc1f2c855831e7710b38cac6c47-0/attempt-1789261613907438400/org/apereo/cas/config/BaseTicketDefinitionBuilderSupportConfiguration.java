/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.ticket.TicketCatalog
 *  org.apereo.cas.ticket.TicketDefinition
 *  org.springframework.context.ConfigurableApplicationContext
 */
package org.apereo.cas.config;

import lombok.Generated;
import org.apereo.cas.config.CasCoreTicketCatalogConfiguration;
import org.apereo.cas.config.CasTicketCatalogConfigurationValuesProvider;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.ticket.TicketCatalog;
import org.apereo.cas.ticket.TicketDefinition;
import org.springframework.context.ConfigurableApplicationContext;

public abstract class BaseTicketDefinitionBuilderSupportConfiguration
extends CasCoreTicketCatalogConfiguration {
    private final CasConfigurationProperties casProperties;
    private final CasTicketCatalogConfigurationValuesProvider configurationValuesProvider;
    private final ConfigurableApplicationContext applicationContext;

    @Override
    protected void buildAndRegisterServiceTicketDefinition(TicketCatalog plan, TicketDefinition metadata) {
        metadata.getProperties().setStorageName(this.configurationValuesProvider.getServiceTicketStorageName().apply(this.casProperties));
        metadata.getProperties().setStorageTimeout(this.configurationValuesProvider.getServiceTicketStorageTimeout().apply(this.applicationContext).longValue());
        super.buildAndRegisterServiceTicketDefinition(plan, metadata);
    }

    @Override
    protected void buildAndRegisterProxyTicketDefinition(TicketCatalog plan, TicketDefinition metadata) {
        metadata.getProperties().setStorageName(this.configurationValuesProvider.getProxyTicketStorageName().apply(this.casProperties));
        metadata.getProperties().setStorageTimeout(this.configurationValuesProvider.getProxyTicketStorageTimeout().apply(this.applicationContext).longValue());
        super.buildAndRegisterProxyTicketDefinition(plan, metadata);
    }

    @Override
    protected void buildAndRegisterTicketGrantingTicketDefinition(TicketCatalog plan, TicketDefinition metadata) {
        metadata.getProperties().setStorageName(this.configurationValuesProvider.getTicketGrantingTicketStorageName().apply(this.casProperties));
        metadata.getProperties().setStorageTimeout(this.configurationValuesProvider.getTicketGrantingTicketStorageTimeout().apply(this.applicationContext).longValue());
        super.buildAndRegisterTicketGrantingTicketDefinition(plan, metadata);
    }

    @Override
    protected void buildAndRegisterProxyGrantingTicketDefinition(TicketCatalog plan, TicketDefinition metadata) {
        metadata.getProperties().setStorageName(this.configurationValuesProvider.getProxyGrantingTicketStorageName().apply(this.casProperties));
        metadata.getProperties().setStorageTimeout(this.configurationValuesProvider.getProxyGrantingTicketStorageTimeout().apply(this.applicationContext).longValue());
        super.buildAndRegisterProxyGrantingTicketDefinition(plan, metadata);
    }

    @Override
    protected void buildAndRegisterTransientSessionTicketDefinition(TicketCatalog plan, TicketDefinition metadata) {
        metadata.getProperties().setStorageName(this.configurationValuesProvider.getTransientSessionStorageName().apply(this.casProperties));
        metadata.getProperties().setStorageTimeout(this.configurationValuesProvider.getTransientSessionStorageTimeout().apply(this.applicationContext).longValue());
        super.buildAndRegisterTransientSessionTicketDefinition(plan, metadata);
    }

    @Generated
    protected BaseTicketDefinitionBuilderSupportConfiguration(CasConfigurationProperties casProperties, CasTicketCatalogConfigurationValuesProvider configurationValuesProvider, ConfigurableApplicationContext applicationContext) {
        this.casProperties = casProperties;
        this.configurationValuesProvider = configurationValuesProvider;
        this.applicationContext = applicationContext;
    }
}

