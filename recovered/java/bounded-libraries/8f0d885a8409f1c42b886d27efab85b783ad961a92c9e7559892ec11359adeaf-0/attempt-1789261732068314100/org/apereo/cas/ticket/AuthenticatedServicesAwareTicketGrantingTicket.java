/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.principal.Service
 */
package org.apereo.cas.ticket;

import java.util.Map;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.ticket.TicketGrantingTicket;

public interface AuthenticatedServicesAwareTicketGrantingTicket
extends TicketGrantingTicket {
    public Map<String, Service> getServices();
}

