/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.saml.idp.metadata;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionJwtSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.model.support.couchdb.BaseCouchDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-saml-idp-metadata-couchdb")
@JsonFilter(value="CouchDbSamlMetadataProperties")
public class CouchDbSamlMetadataProperties
extends BaseCouchDbProperties {
    private static final long serialVersionUID = 1673956475847790139L;
    private boolean idpMetadataEnabled;
    @NestedConfigurationProperty
    private EncryptionJwtSigningJwtCryptographyProperties crypto = new EncryptionJwtSigningJwtCryptographyProperties();

    public CouchDbSamlMetadataProperties() {
        this.setDbName("saml_metadata");
        this.crypto.getEncryption().setKeySize(256);
        this.crypto.getSigning().setKeySize(512);
    }

    @Generated
    public boolean isIdpMetadataEnabled() {
        return this.idpMetadataEnabled;
    }

    @Generated
    public EncryptionJwtSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public CouchDbSamlMetadataProperties setIdpMetadataEnabled(boolean idpMetadataEnabled) {
        this.idpMetadataEnabled = idpMetadataEnabled;
        return this;
    }

    @Generated
    public CouchDbSamlMetadataProperties setCrypto(EncryptionJwtSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }
}

