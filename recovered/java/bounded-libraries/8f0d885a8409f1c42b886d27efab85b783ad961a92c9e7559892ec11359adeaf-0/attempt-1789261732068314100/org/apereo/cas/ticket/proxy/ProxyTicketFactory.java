/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.principal.Service
 */
package org.apereo.cas.ticket.proxy;

import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.TicketFactory;
import org.apereo.cas.ticket.proxy.ProxyGrantingTicket;

public interface ProxyTicketFactory
extends TicketFactory {
    public <T extends Ticket> T create(ProxyGrantingTicket var1, Service var2, Class<T> var3);
}

