/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.ticket.registry;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionRandomizedSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-tickets", automated=true)
@JsonFilter(value="InMemoryTicketRegistryProperties")
public class InMemoryTicketRegistryProperties
implements Serializable {
    private static final long serialVersionUID = -2600525447128979994L;
    private boolean cache;
    private int initialCapacity = 1000;
    private int loadFactor = 1;
    private int concurrency = 20;
    @NestedConfigurationProperty
    private EncryptionRandomizedSigningJwtCryptographyProperties crypto = new EncryptionRandomizedSigningJwtCryptographyProperties();

    public InMemoryTicketRegistryProperties() {
        this.crypto.setEnabled(false);
    }

    @Generated
    public boolean isCache() {
        return this.cache;
    }

    @Generated
    public int getInitialCapacity() {
        return this.initialCapacity;
    }

    @Generated
    public int getLoadFactor() {
        return this.loadFactor;
    }

    @Generated
    public int getConcurrency() {
        return this.concurrency;
    }

    @Generated
    public EncryptionRandomizedSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public void setCache(boolean cache) {
        this.cache = cache;
    }

    @Generated
    public void setInitialCapacity(int initialCapacity) {
        this.initialCapacity = initialCapacity;
    }

    @Generated
    public void setLoadFactor(int loadFactor) {
        this.loadFactor = loadFactor;
    }

    @Generated
    public void setConcurrency(int concurrency) {
        this.concurrency = concurrency;
    }

    @Generated
    public void setCrypto(EncryptionRandomizedSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
    }
}

