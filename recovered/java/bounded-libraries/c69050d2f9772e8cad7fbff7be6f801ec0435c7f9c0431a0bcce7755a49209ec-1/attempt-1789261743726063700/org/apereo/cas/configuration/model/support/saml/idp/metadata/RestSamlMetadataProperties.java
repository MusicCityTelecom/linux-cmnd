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
import org.apereo.cas.configuration.model.RestEndpointProperties;
import org.apereo.cas.configuration.model.core.util.EncryptionJwtSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-saml-idp-metadata-rest")
@JsonFilter(value="RestSamlMetadataProperties")
public class RestSamlMetadataProperties
extends RestEndpointProperties {
    private static final long serialVersionUID = -7734304585762871404L;
    private boolean idpMetadataEnabled;
    @NestedConfigurationProperty
    private EncryptionJwtSigningJwtCryptographyProperties crypto = new EncryptionJwtSigningJwtCryptographyProperties();

    public RestSamlMetadataProperties() {
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
    public RestSamlMetadataProperties setIdpMetadataEnabled(boolean idpMetadataEnabled) {
        this.idpMetadataEnabled = idpMetadataEnabled;
        return this;
    }

    @Generated
    public RestSamlMetadataProperties setCrypto(EncryptionJwtSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }
}

