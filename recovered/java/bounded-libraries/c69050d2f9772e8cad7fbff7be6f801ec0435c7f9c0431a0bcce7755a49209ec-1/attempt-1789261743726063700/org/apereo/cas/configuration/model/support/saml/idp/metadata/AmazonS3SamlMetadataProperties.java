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
import org.apereo.cas.configuration.model.support.aws.BaseAmazonWebServicesProperties;
import org.apereo.cas.configuration.support.ExpressionLanguageCapable;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-saml-idp-metadata-aws-s3")
@JsonFilter(value="AmazonS3SamlMetadataProperties")
public class AmazonS3SamlMetadataProperties
extends BaseAmazonWebServicesProperties {
    private static final long serialVersionUID = 352435146313504995L;
    @ExpressionLanguageCapable
    private String idpMetadataBucketName;
    @ExpressionLanguageCapable
    private String bucketName;
    @NestedConfigurationProperty
    private EncryptionJwtSigningJwtCryptographyProperties crypto = new EncryptionJwtSigningJwtCryptographyProperties();

    public AmazonS3SamlMetadataProperties() {
        this.crypto.getEncryption().setKeySize(256);
        this.crypto.getSigning().setKeySize(512);
    }

    @Generated
    public String getIdpMetadataBucketName() {
        return this.idpMetadataBucketName;
    }

    @Generated
    public String getBucketName() {
        return this.bucketName;
    }

    @Generated
    public EncryptionJwtSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public AmazonS3SamlMetadataProperties setIdpMetadataBucketName(String idpMetadataBucketName) {
        this.idpMetadataBucketName = idpMetadataBucketName;
        return this;
    }

    @Generated
    public AmazonS3SamlMetadataProperties setBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

    @Generated
    public AmazonS3SamlMetadataProperties setCrypto(EncryptionJwtSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }
}

