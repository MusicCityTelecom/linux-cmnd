/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.mongo.ticketregistry;

import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionRandomizedSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.model.support.mongo.BaseMongoDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-mongo-ticket-registry")
public class MongoDbTicketRegistryProperties
extends BaseMongoDbProperties {
    private static final long serialVersionUID = 8243690796900311918L;
    private boolean dropCollection;
    private boolean updateIndexes = true;
    private boolean dropIndexes;
    @NestedConfigurationProperty
    private EncryptionRandomizedSigningJwtCryptographyProperties crypto = new EncryptionRandomizedSigningJwtCryptographyProperties();

    public MongoDbTicketRegistryProperties() {
        this.crypto.setEnabled(false);
    }

    @Generated
    public boolean isDropCollection() {
        return this.dropCollection;
    }

    @Generated
    public boolean isUpdateIndexes() {
        return this.updateIndexes;
    }

    @Generated
    public boolean isDropIndexes() {
        return this.dropIndexes;
    }

    @Generated
    public EncryptionRandomizedSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public MongoDbTicketRegistryProperties setDropCollection(boolean dropCollection) {
        this.dropCollection = dropCollection;
        return this;
    }

    @Generated
    public MongoDbTicketRegistryProperties setUpdateIndexes(boolean updateIndexes) {
        this.updateIndexes = updateIndexes;
        return this;
    }

    @Generated
    public MongoDbTicketRegistryProperties setDropIndexes(boolean dropIndexes) {
        this.dropIndexes = dropIndexes;
        return this;
    }

    @Generated
    public MongoDbTicketRegistryProperties setCrypto(EncryptionRandomizedSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }
}

