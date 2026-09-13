/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.principal.Service
 */
package org.apereo.cas.ticket;

import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.ticket.TicketFactory;
import org.apereo.cas.ticket.TicketGrantingTicket;

public interface TicketGrantingTicketFactory
extends TicketFactory {
    public <T extends TicketGrantingTicket> T create(Authentication var1, Service var2, Class<T> var3);
}

