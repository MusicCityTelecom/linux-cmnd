/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.util;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-util", automated=true)
public class SigningJwtCryptoProperties
implements Serializable {
    private static final long serialVersionUID = -552544781333015532L;
    @RequiredProperty
    private String key = "";
    private int keySize = 512;

    @Generated
    public String getKey() {
        return this.key;
    }

    @Generated
    public int getKeySize() {
        return this.keySize;
    }

    @Generated
    public SigningJwtCryptoProperties setKey(String key) {
        this.key = key;
        return this;
    }

    @Generated
    public SigningJwtCryptoProperties setKeySize(int keySize) {
        this.keySize = keySize;
        return this;
    }
}

