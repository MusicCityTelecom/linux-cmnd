/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.memcached;

import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionRandomizedSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.model.support.memcached.BaseMemcachedProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-memcached-ticket-registry")
public class MemcachedTicketRegistryProperties
extends BaseMemcachedProperties {
    private static final long serialVersionUID = 509520518053691786L;
    @NestedConfigurationProperty
    private EncryptionRandomizedSigningJwtCryptographyProperties crypto = new EncryptionRandomizedSigningJwtCryptographyProperties();

    public MemcachedTicketRegistryProperties() {
        this.crypto.setEnabled(false);
    }

    @Generated
    public EncryptionRandomizedSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public MemcachedTicketRegistryProperties setCrypto(EncryptionRandomizedSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }
}

