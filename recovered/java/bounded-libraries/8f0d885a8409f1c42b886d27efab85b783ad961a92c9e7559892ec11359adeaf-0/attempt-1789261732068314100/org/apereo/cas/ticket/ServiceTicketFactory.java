/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.principal.Service
 */
package org.apereo.cas.ticket;

import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.ticket.ExpirationPolicyBuilder;
import org.apereo.cas.ticket.ServiceTicket;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.TicketFactory;
import org.apereo.cas.ticket.TicketGrantingTicket;

public interface ServiceTicketFactory
extends TicketFactory {
    public ExpirationPolicyBuilder<ServiceTicket> getTicketExpirationPolicy();

    public <T extends Ticket> T create(TicketGrantingTicket var1, Service var2, boolean var3, Class<T> var4);
}

