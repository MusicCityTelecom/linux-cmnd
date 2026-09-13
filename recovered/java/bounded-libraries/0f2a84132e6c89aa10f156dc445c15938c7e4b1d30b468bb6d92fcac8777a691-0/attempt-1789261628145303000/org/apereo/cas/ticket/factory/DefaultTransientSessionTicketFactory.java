/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.ticket.ExpirationPolicy
 *  org.apereo.cas.ticket.ExpirationPolicyBuilder
 *  org.apereo.cas.ticket.Ticket
 *  org.apereo.cas.ticket.TransientSessionTicket
 *  org.apereo.cas.ticket.TransientSessionTicketFactory
 *  org.apereo.cas.ticket.UniqueTicketIdGenerator
 */
package org.apereo.cas.ticket.factory;

import java.io.Serializable;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.ExpirationPolicyBuilder;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.TransientSessionTicket;
import org.apereo.cas.ticket.TransientSessionTicketFactory;
import org.apereo.cas.ticket.TransientSessionTicketImpl;
import org.apereo.cas.ticket.UniqueTicketIdGenerator;
import org.apereo.cas.util.DefaultUniqueTicketIdGenerator;

public class DefaultTransientSessionTicketFactory
implements TransientSessionTicketFactory<TransientSessionTicket> {
    private final ExpirationPolicyBuilder<TransientSessionTicket> expirationPolicyBuilder;
    private final UniqueTicketIdGenerator ticketIdGenerator = new DefaultUniqueTicketIdGenerator();

    public TransientSessionTicket create(Service service, Map<String, Serializable> properties) {
        String id = this.ticketIdGenerator.getNewTicketId("TST");
        ExpirationPolicy expirationPolicy = TransientSessionTicketFactory.buildExpirationPolicy(this.expirationPolicyBuilder, properties);
        return new TransientSessionTicketImpl(id, expirationPolicy, service, properties);
    }

    public TransientSessionTicket create(String id, Service service, Map<String, Serializable> properties) {
        ExpirationPolicy expirationPolicy = TransientSessionTicketFactory.buildExpirationPolicy(this.expirationPolicyBuilder, properties);
        return new TransientSessionTicketImpl(TransientSessionTicketFactory.normalizeTicketId((String)id), expirationPolicy, service, properties);
    }

    public Class<? extends Ticket> getTicketType() {
        return TransientSessionTicket.class;
    }

    @Generated
    public DefaultTransientSessionTicketFactory(ExpirationPolicyBuilder<TransientSessionTicket> expirationPolicyBuilder) {
        this.expirationPolicyBuilder = expirationPolicyBuilder;
    }
}

