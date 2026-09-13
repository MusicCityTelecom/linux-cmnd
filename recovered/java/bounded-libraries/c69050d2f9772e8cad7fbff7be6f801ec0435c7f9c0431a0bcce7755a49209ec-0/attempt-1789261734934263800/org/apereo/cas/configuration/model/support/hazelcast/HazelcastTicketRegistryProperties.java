/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.hazelcast;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionRandomizedSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.model.support.hazelcast.BaseHazelcastProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-hazelcast-ticket-registry")
@JsonFilter(value="HazelcastTicketRegistryProperties")
public class HazelcastTicketRegistryProperties
extends BaseHazelcastProperties {
    private static final long serialVersionUID = -1095208036374406772L;
    private long pageSize = 500L;
    @NestedConfigurationProperty
    private EncryptionRandomizedSigningJwtCryptographyProperties crypto = new EncryptionRandomizedSigningJwtCryptographyProperties();

    public HazelcastTicketRegistryProperties() {
        this.crypto.setEnabled(false);
    }

    @Generated
    public long getPageSize() {
        return this.pageSize;
    }

    @Generated
    public EncryptionRandomizedSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public HazelcastTicketRegistryProperties setPageSize(long pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @Generated
    public HazelcastTicketRegistryProperties setCrypto(EncryptionRandomizedSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }
}

