/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.clearpass;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionJwtSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-authentication", automated=true)
@JsonFilter(value="ClearpassProperties")
public class ClearpassProperties
implements Serializable {
    private static final long serialVersionUID = 6047778458053531460L;
    @RequiredProperty
    private boolean cacheCredential;
    @NestedConfigurationProperty
    private EncryptionJwtSigningJwtCryptographyProperties crypto = new EncryptionJwtSigningJwtCryptographyProperties();

    public ClearpassProperties() {
        this.crypto.getEncryption().setKeySize(256);
        this.crypto.getSigning().setKeySize(512);
    }

    @Generated
    public boolean isCacheCredential() {
        return this.cacheCredential;
    }

    @Generated
    public EncryptionJwtSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public ClearpassProperties setCacheCredential(boolean cacheCredential) {
        this.cacheCredential = cacheCredential;
        return this;
    }

    @Generated
    public ClearpassProperties setCrypto(EncryptionJwtSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }
}

