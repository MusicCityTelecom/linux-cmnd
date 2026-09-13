/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.apache.commons.io.FileUtils
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 *  org.springframework.core.io.FileSystemResource
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.configuration.model.support.saml.idp.metadata;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.File;
import lombok.Generated;
import org.apache.commons.io.FileUtils;
import org.apereo.cas.configuration.model.core.util.EncryptionJwtSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.model.support.git.services.BaseGitProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@RequiresModule(name="cas-server-support-saml-idp-metadata-git")
@JsonFilter(value="GitSamlMetadataProperties")
public class GitSamlMetadataProperties
extends BaseGitProperties {
    private static final long serialVersionUID = 4194689836396653458L;
    private boolean idpMetadataEnabled;
    @NestedConfigurationProperty
    private EncryptionJwtSigningJwtCryptographyProperties crypto = new EncryptionJwtSigningJwtCryptographyProperties();

    public GitSamlMetadataProperties() {
        this.getCloneDirectory().setLocation((Resource)new FileSystemResource(new File(FileUtils.getTempDirectory(), "cas-saml-metadata")));
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
    public GitSamlMetadataProperties setIdpMetadataEnabled(boolean idpMetadataEnabled) {
        this.idpMetadataEnabled = idpMetadataEnabled;
        return this;
    }

    @Generated
    public GitSamlMetadataProperties setCrypto(EncryptionJwtSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }
}

