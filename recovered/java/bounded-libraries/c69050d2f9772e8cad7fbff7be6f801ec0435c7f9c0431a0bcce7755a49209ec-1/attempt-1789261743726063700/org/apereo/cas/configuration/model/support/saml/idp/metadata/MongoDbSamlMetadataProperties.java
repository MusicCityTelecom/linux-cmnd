/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.saml.idp.metadata;

import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionJwtSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.model.support.mongo.SingleCollectionMongoDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-saml-idp-metadata-mongo")
public class MongoDbSamlMetadataProperties
extends SingleCollectionMongoDbProperties {
    private static final long serialVersionUID = -227092724742371662L;
    private String idpMetadataCollection;
    @NestedConfigurationProperty
    private EncryptionJwtSigningJwtCryptographyProperties crypto = new EncryptionJwtSigningJwtCryptographyProperties();

    public MongoDbSamlMetadataProperties() {
        this.setCollection("cas-saml-metadata");
        this.crypto.getEncryption().setKeySize(256);
        this.crypto.getSigning().setKeySize(512);
    }

    @Generated
    public String getIdpMetadataCollection() {
        return this.idpMetadataCollection;
    }

    @Generated
    public EncryptionJwtSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public MongoDbSamlMetadataProperties setIdpMetadataCollection(String idpMetadataCollection) {
        this.idpMetadataCollection = idpMetadataCollection;
        return this;
    }

    @Generated
    public MongoDbSamlMetadataProperties setCrypto(EncryptionJwtSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }
}

