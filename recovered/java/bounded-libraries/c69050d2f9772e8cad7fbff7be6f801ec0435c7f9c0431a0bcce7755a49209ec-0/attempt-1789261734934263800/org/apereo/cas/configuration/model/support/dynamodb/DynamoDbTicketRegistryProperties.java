/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.dynamodb;

import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionRandomizedSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.model.support.dynamodb.AbstractDynamoDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-dynamodb-ticket-registry")
public class DynamoDbTicketRegistryProperties
extends AbstractDynamoDbProperties {
    private static final long serialVersionUID = 699497009058965681L;
    private String serviceTicketsTableName = "serviceTicketsTable";
    private String proxyTicketsTableName = "proxyTicketsTable";
    private String ticketGrantingTicketsTableName = "ticketGrantingTicketsTable";
    private String proxyGrantingTicketsTableName = "proxyGrantingTicketsTable";
    private String transientSessionTicketsTableName = "transientSessionTicketsTable";
    @NestedConfigurationProperty
    private EncryptionRandomizedSigningJwtCryptographyProperties crypto = new EncryptionRandomizedSigningJwtCryptographyProperties();

    public DynamoDbTicketRegistryProperties() {
        this.crypto.setEnabled(false);
    }

    @Generated
    public String getServiceTicketsTableName() {
        return this.serviceTicketsTableName;
    }

    @Generated
    public String getProxyTicketsTableName() {
        return this.proxyTicketsTableName;
    }

    @Generated
    public String getTicketGrantingTicketsTableName() {
        return this.ticketGrantingTicketsTableName;
    }

    @Generated
    public String getProxyGrantingTicketsTableName() {
        return this.proxyGrantingTicketsTableName;
    }

    @Generated
    public String getTransientSessionTicketsTableName() {
        return this.transientSessionTicketsTableName;
    }

    @Generated
    public EncryptionRandomizedSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public DynamoDbTicketRegistryProperties setServiceTicketsTableName(String serviceTicketsTableName) {
        this.serviceTicketsTableName = serviceTicketsTableName;
        return this;
    }

    @Generated
    public DynamoDbTicketRegistryProperties setProxyTicketsTableName(String proxyTicketsTableName) {
        this.proxyTicketsTableName = proxyTicketsTableName;
        return this;
    }

    @Generated
    public DynamoDbTicketRegistryProperties setTicketGrantingTicketsTableName(String ticketGrantingTicketsTableName) {
        this.ticketGrantingTicketsTableName = ticketGrantingTicketsTableName;
        return this;
    }

    @Generated
    public DynamoDbTicketRegistryProperties setProxyGrantingTicketsTableName(String proxyGrantingTicketsTableName) {
        this.proxyGrantingTicketsTableName = proxyGrantingTicketsTableName;
        return this;
    }

    @Generated
    public DynamoDbTicketRegistryProperties setTransientSessionTicketsTableName(String transientSessionTicketsTableName) {
        this.transientSessionTicketsTableName = transientSessionTicketsTableName;
        return this;
    }

    @Generated
    public DynamoDbTicketRegistryProperties setCrypto(EncryptionRandomizedSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }
}

