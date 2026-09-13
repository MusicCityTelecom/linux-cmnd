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
import org.apereo.cas.configuration.model.core.ticket.ProxyGrantingTicketProperties;
import org.apereo.cas.configuration.model.core.ticket.ProxyTicketProperties;
import org.apereo.cas.configuration.model.core.ticket.ServiceTicketProperties;
import org.apereo.cas.configuration.model.core.ticket.TicketGrantingTicketProperties;
import org.apereo.cas.configuration.model.core.ticket.TransientSessionTicketProperties;
import org.apereo.cas.configuration.model.core.ticket.registry.TicketRegistryProperties;
import org.apereo.cas.configuration.model.core.util.EncryptionJwtSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-tickets", automated=true)
public class TicketProperties
implements Serializable {
    private static final long serialVersionUID = 5586947805593202037L;
    @NestedConfigurationProperty
    private TransientSessionTicketProperties tst = new TransientSessionTicketProperties();
    @NestedConfigurationProperty
    private ProxyGrantingTicketProperties pgt = new ProxyGrantingTicketProperties();
    @NestedConfigurationProperty
    private EncryptionJwtSigningJwtCryptographyProperties crypto = new EncryptionJwtSigningJwtCryptographyProperties();
    @NestedConfigurationProperty
    private ProxyTicketProperties pt = new ProxyTicketProperties();
    @NestedConfigurationProperty
    private TicketRegistryProperties registry = new TicketRegistryProperties();
    @NestedConfigurationProperty
    private ServiceTicketProperties st = new ServiceTicketProperties();
    @NestedConfigurationProperty
    private TicketGrantingTicketProperties tgt = new TicketGrantingTicketProperties();

    public TicketProperties() {
        this.crypto.setEnabled(false);
        this.crypto.getEncryption().setKeySize(256);
        this.crypto.getSigning().setKeySize(512);
    }

    @Generated
    public TransientSessionTicketProperties getTst() {
        return this.tst;
    }

    @Generated
    public ProxyGrantingTicketProperties getPgt() {
        return this.pgt;
    }

    @Generated
    public EncryptionJwtSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public ProxyTicketProperties getPt() {
        return this.pt;
    }

    @Generated
    public TicketRegistryProperties getRegistry() {
        return this.registry;
    }

    @Generated
    public ServiceTicketProperties getSt() {
        return this.st;
    }

    @Generated
    public TicketGrantingTicketProperties getTgt() {
        return this.tgt;
    }

    @Generated
    public TicketProperties setTst(TransientSessionTicketProperties tst) {
        this.tst = tst;
        return this;
    }

    @Generated
    public TicketProperties setPgt(ProxyGrantingTicketProperties pgt) {
        this.pgt = pgt;
        return this;
    }

    @Generated
    public TicketProperties setCrypto(EncryptionJwtSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }

    @Generated
    public TicketProperties setPt(ProxyTicketProperties pt) {
        this.pt = pt;
        return this;
    }

    @Generated
    public TicketProperties setRegistry(TicketRegistryProperties registry) {
        this.registry = registry;
        return this;
    }

    @Generated
    public TicketProperties setSt(ServiceTicketProperties st) {
        this.st = st;
        return this;
    }

    @Generated
    public TicketProperties setTgt(TicketGrantingTicketProperties tgt) {
        this.tgt = tgt;
        return this;
    }
}

