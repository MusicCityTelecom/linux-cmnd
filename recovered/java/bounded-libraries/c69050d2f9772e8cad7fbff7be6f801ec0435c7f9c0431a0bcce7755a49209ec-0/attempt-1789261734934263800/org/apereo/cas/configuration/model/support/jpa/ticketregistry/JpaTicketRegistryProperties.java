/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  javax.persistence.LockModeType
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.jpa.ticketregistry;

import com.fasterxml.jackson.annotation.JsonFilter;
import javax.persistence.LockModeType;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionRandomizedSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-jpa-ticket-registry")
@JsonFilter(value="JpaTicketRegistryProperties")
public class JpaTicketRegistryProperties
extends AbstractJpaProperties {
    public static final String DEFAULT_LOCK_TIMEOUT = "PT1H";
    private static final long serialVersionUID = -8053839523783801072L;
    private LockModeType ticketLockType = LockModeType.NONE;
    @DurationCapable
    private String jpaLockingTimeout = "PT1H";
    @NestedConfigurationProperty
    private EncryptionRandomizedSigningJwtCryptographyProperties crypto = new EncryptionRandomizedSigningJwtCryptographyProperties();
    @RequiredProperty
    private boolean enabled = true;

    public JpaTicketRegistryProperties() {
        super.setUrl("jdbc:hsqldb:mem:cas-ticket-registry");
        this.crypto.setEnabled(false);
    }

    @Generated
    public LockModeType getTicketLockType() {
        return this.ticketLockType;
    }

    @Generated
    public String getJpaLockingTimeout() {
        return this.jpaLockingTimeout;
    }

    @Generated
    public EncryptionRandomizedSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public JpaTicketRegistryProperties setTicketLockType(LockModeType ticketLockType) {
        this.ticketLockType = ticketLockType;
        return this;
    }

    @Generated
    public JpaTicketRegistryProperties setJpaLockingTimeout(String jpaLockingTimeout) {
        this.jpaLockingTimeout = jpaLockingTimeout;
        return this;
    }

    @Generated
    public JpaTicketRegistryProperties setCrypto(EncryptionRandomizedSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }

    @Generated
    public JpaTicketRegistryProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}

