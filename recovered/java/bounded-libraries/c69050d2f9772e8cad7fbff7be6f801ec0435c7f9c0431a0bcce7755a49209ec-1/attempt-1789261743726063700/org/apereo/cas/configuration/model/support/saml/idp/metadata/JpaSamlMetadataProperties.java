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
import org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-saml-idp-metadata-jpa")
@JsonFilter(value="JpaSamlMetadataProperties")
public class JpaSamlMetadataProperties
extends AbstractJpaProperties {
    private static final long serialVersionUID = 352435146313504995L;
    private boolean idpMetadataEnabled;
    @NestedConfigurationProperty
    private EncryptionJwtSigningJwtCryptographyProperties crypto = new EncryptionJwtSigningJwtCryptographyProperties();

    public JpaSamlMetadataProperties() {
        super.setUrl("jdbc:hsqldb:mem:cas-saml-metadata");
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
    public JpaSamlMetadataProperties setIdpMetadataEnabled(boolean idpMetadataEnabled) {
        this.idpMetadataEnabled = idpMetadataEnabled;
        return this;
    }

    @Generated
    public JpaSamlMetadataProperties setCrypto(EncryptionJwtSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }
}

