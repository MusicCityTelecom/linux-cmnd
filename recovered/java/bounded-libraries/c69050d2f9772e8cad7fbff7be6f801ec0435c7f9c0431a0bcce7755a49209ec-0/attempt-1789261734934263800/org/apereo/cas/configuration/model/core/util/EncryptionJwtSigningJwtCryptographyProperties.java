/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.util;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionJwtCryptoProperties;
import org.apereo.cas.configuration.model.core.util.SigningJwtCryptoProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-util", automated=true)
public class EncryptionJwtSigningJwtCryptographyProperties
implements Serializable {
    private static final long serialVersionUID = -3015641631298039059L;
    private boolean enabled = true;
    @NestedConfigurationProperty
    private EncryptionJwtCryptoProperties encryption = new EncryptionJwtCryptoProperties();
    @NestedConfigurationProperty
    private SigningJwtCryptoProperties signing = new SigningJwtCryptoProperties();
    private String alg = "A128CBC-HS256";
    private String strategyType = "ENCRYPT_AND_SIGN";

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public EncryptionJwtCryptoProperties getEncryption() {
        return this.encryption;
    }

    @Generated
    public SigningJwtCryptoProperties getSigning() {
        return this.signing;
    }

    @Generated
    public String getAlg() {
        return this.alg;
    }

    @Generated
    public String getStrategyType() {
        return this.strategyType;
    }

    @Generated
    public EncryptionJwtSigningJwtCryptographyProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public EncryptionJwtSigningJwtCryptographyProperties setEncryption(EncryptionJwtCryptoProperties encryption) {
        this.encryption = encryption;
        return this;
    }

    @Generated
    public EncryptionJwtSigningJwtCryptographyProperties setSigning(SigningJwtCryptoProperties signing) {
        this.signing = signing;
        return this;
    }

    @Generated
    public EncryptionJwtSigningJwtCryptographyProperties setAlg(String alg) {
        this.alg = alg;
        return this;
    }

    @Generated
    public EncryptionJwtSigningJwtCryptographyProperties setStrategyType(String strategyType) {
        this.strategyType = strategyType;
        return this;
    }
}

