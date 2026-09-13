/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.mfa.gauth;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionRandomizedCryptoProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-gauth")
@JsonFilter(value="GoogleAuthenticatorMultifactorScratchCodeProperties")
public class GoogleAuthenticatorMultifactorScratchCodeProperties
implements Serializable {
    private static final long serialVersionUID = 8740203143088539401L;
    @NestedConfigurationProperty
    private EncryptionRandomizedCryptoProperties encryption = new EncryptionRandomizedCryptoProperties();

    @Generated
    public EncryptionRandomizedCryptoProperties getEncryption() {
        return this.encryption;
    }

    @Generated
    public GoogleAuthenticatorMultifactorScratchCodeProperties setEncryption(EncryptionRandomizedCryptoProperties encryption) {
        this.encryption = encryption;
        return this;
    }
}

