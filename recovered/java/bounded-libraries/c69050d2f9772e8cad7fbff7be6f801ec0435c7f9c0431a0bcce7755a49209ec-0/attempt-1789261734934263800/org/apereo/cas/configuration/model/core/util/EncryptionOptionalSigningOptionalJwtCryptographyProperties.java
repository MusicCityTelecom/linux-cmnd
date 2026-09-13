/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.util;

import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionJwtSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-util", automated=true)
public class EncryptionOptionalSigningOptionalJwtCryptographyProperties
extends EncryptionJwtSigningJwtCryptographyProperties {
    private static final long serialVersionUID = 7185404480671258520L;
    private boolean encryptionEnabled = true;
    private boolean signingEnabled = true;

    @Generated
    public boolean isEncryptionEnabled() {
        return this.encryptionEnabled;
    }

    @Generated
    public boolean isSigningEnabled() {
        return this.signingEnabled;
    }

    @Generated
    public EncryptionOptionalSigningOptionalJwtCryptographyProperties setEncryptionEnabled(boolean encryptionEnabled) {
        this.encryptionEnabled = encryptionEnabled;
        return this;
    }

    @Generated
    public EncryptionOptionalSigningOptionalJwtCryptographyProperties setSigningEnabled(boolean signingEnabled) {
        this.signingEnabled = signingEnabled;
        return this;
    }
}

