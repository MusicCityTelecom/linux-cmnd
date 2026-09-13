/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceTicketGrantingTicketExpirationPolicy
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.ticket.ExpirationPolicy
 *  org.apereo.cas.ticket.ExpirationPolicyBuilder
 *  org.apereo.cas.ticket.Ticket
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.apereo.cas.ticket.TicketGrantingTicketFactory
 *  org.apereo.cas.ticket.UniqueTicketIdGenerator
 *  org.apereo.cas.util.crypto.CipherExecutor
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.ticket.factory;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceTicketGrantingTicketExpirationPolicy;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.ExpirationPolicyBuilder;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.TicketGrantingTicketFactory;
import org.apereo.cas.ticket.TicketGrantingTicketImpl;
import org.apereo.cas.ticket.UniqueTicketIdGenerator;
import org.apereo.cas.util.crypto.CipherExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultTicketGrantingTicketFactory
implements TicketGrantingTicketFactory {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTicketGrantingTicketFactory.class);
    protected final UniqueTicketIdGenerator ticketGrantingTicketUniqueTicketIdGenerator;
    protected final ExpirationPolicyBuilder<TicketGrantingTicket> ticketGrantingTicketExpirationPolicy;
    protected final CipherExecutor<Serializable, String> cipherExecutor;
    protected final ServicesManager servicesManager;

    public <T extends TicketGrantingTicket> T create(Authentication authentication, Service service, Class<T> clazz) {
        String tgtId = this.produceTicketIdentifier(authentication);
        return this.produceTicket(authentication, tgtId, service, clazz);
    }

    public Class<? extends Ticket> getTicketType() {
        return TicketGrantingTicket.class;
    }

    protected <T extends TicketGrantingTicket> T produceTicket(Authentication authentication, String tgtId, Service service, Class<T> clazz) {
        ExpirationPolicy expirationPolicy = this.getTicketGrantingTicketExpirationPolicy(service);
        TicketGrantingTicketImpl result = new TicketGrantingTicketImpl(tgtId, authentication, expirationPolicy);
        if (!clazz.isAssignableFrom(result.getClass())) {
            throw new ClassCastException("Result [" + result + " is of type " + result.getClass() + " when we were expecting " + clazz);
        }
        return (T)result;
    }

    protected ExpirationPolicy getTicketGrantingTicketExpirationPolicy(Service service) {
        RegisteredServiceTicketGrantingTicketExpirationPolicy policy;
        RegisteredService registeredService = this.servicesManager.findServiceBy(service);
        if (registeredService != null && (policy = registeredService.getTicketGrantingTicketExpirationPolicy()) != null) {
            return policy.toExpirationPolicy().orElseGet(() -> this.ticketGrantingTicketExpirationPolicy.buildTicketExpirationPolicy());
        }
        return this.ticketGrantingTicketExpirationPolicy.buildTicketExpirationPolicy();
    }

    protected String produceTicketIdentifier(Authentication authentication) {
        String tgtId = this.ticketGrantingTicketUniqueTicketIdGenerator.getNewTicketId("TGT");
        if (this.cipherExecutor != null && this.cipherExecutor.isEnabled()) {
            LOGGER.trace("Attempting to encode ticket-granting ticket [{}]", (Object)tgtId);
            tgtId = (String)this.cipherExecutor.encode((Object)tgtId);
            LOGGER.trace("Encoded ticket-granting ticket id [{}]", (Object)tgtId);
        }
        return tgtId;
    }

    @Generated
    public DefaultTicketGrantingTicketFactory(UniqueTicketIdGenerator ticketGrantingTicketUniqueTicketIdGenerator, ExpirationPolicyBuilder<TicketGrantingTicket> ticketGrantingTicketExpirationPolicy, CipherExecutor<Serializable, String> cipherExecutor, ServicesManager servicesManager) {
        this.ticketGrantingTicketUniqueTicketIdGenerator = ticketGrantingTicketUniqueTicketIdGenerator;
        this.ticketGrantingTicketExpirationPolicy = ticketGrantingTicketExpirationPolicy;
        this.cipherExecutor = cipherExecutor;
        this.servicesManager = servicesManager;
    }
}

