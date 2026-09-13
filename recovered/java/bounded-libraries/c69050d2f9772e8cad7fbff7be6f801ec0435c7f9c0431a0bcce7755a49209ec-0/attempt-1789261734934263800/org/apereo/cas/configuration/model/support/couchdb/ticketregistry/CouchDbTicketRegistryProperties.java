/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.couchdb.ticketregistry;

import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionRandomizedSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.model.support.couchdb.BaseCouchDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-couchdb-ticket-registry")
public class CouchDbTicketRegistryProperties
extends BaseCouchDbProperties {
    private static final long serialVersionUID = 6895485069081125319L;
    @NestedConfigurationProperty
    private EncryptionRandomizedSigningJwtCryptographyProperties crypto = new EncryptionRandomizedSigningJwtCryptographyProperties();

    public CouchDbTicketRegistryProperties() {
        this.crypto.setEnabled(false);
        this.setDbName("ticket_registry");
    }

    @Generated
    public EncryptionRandomizedSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public CouchDbTicketRegistryProperties setCrypto(EncryptionRandomizedSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }
}

