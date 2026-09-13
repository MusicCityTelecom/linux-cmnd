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
import org.apereo.cas.configuration.model.core.util.EncryptionRandomizedCryptoProperties;
import org.apereo.cas.configuration.model.core.util.SigningJwtCryptoProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-util", automated=true)
public class EncryptionRandomizedSigningJwtCryptographyProperties
implements Serializable {
    private static final long serialVersionUID = -6802876221525521736L;
    private boolean enabled = true;
    @NestedConfigurationProperty
    private EncryptionRandomizedCryptoProperties encryption = new EncryptionRandomizedCryptoProperties();
    @NestedConfigurationProperty
    private SigningJwtCryptoProperties signing = new SigningJwtCryptoProperties();
    private String alg = "AES";

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public EncryptionRandomizedCryptoProperties getEncryption() {
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
    public EncryptionRandomizedSigningJwtCryptographyProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public EncryptionRandomizedSigningJwtCryptographyProperties setEncryption(EncryptionRandomizedCryptoProperties encryption) {
        this.encryption = encryption;
        return this;
    }

    @Generated
    public EncryptionRandomizedSigningJwtCryptographyProperties setSigning(SigningJwtCryptoProperties signing) {
        this.signing = signing;
        return this;
    }

    @Generated
    public EncryptionRandomizedSigningJwtCryptographyProperties setAlg(String alg) {
        this.alg = alg;
        return this;
    }
}

