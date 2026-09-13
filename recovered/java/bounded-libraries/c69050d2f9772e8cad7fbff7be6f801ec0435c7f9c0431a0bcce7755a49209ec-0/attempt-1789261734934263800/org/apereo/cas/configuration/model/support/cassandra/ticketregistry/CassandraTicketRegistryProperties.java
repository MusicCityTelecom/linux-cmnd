/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.cassandra.ticketregistry;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionRandomizedSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.model.support.cassandra.authentication.BaseCassandraProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-cassandra-ticket-registry")
@JsonFilter(value="CassandraTicketRegistryProperties")
public class CassandraTicketRegistryProperties
extends BaseCassandraProperties {
    private static final long serialVersionUID = -2468250557119133004L;
    private boolean dropTablesOnStartup;
    @NestedConfigurationProperty
    private EncryptionRandomizedSigningJwtCryptographyProperties crypto = new EncryptionRandomizedSigningJwtCryptographyProperties();

    @Generated
    public boolean isDropTablesOnStartup() {
        return this.dropTablesOnStartup;
    }

    @Generated
    public EncryptionRandomizedSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public CassandraTicketRegistryProperties setDropTablesOnStartup(boolean dropTablesOnStartup) {
        this.dropTablesOnStartup = dropTablesOnStartup;
        return this;
    }

    @Generated
    public CassandraTicketRegistryProperties setCrypto(EncryptionRandomizedSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }
}

