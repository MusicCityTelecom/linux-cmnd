/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.ticket.TicketGrantingTicket
 */
package org.apereo.cas.support.events.ticket;

import lombok.Generated;
import org.apereo.cas.support.events.AbstractCasEvent;
import org.apereo.cas.ticket.TicketGrantingTicket;

public class CasProxyGrantingTicketCreatedEvent
extends AbstractCasEvent {
    private static final long serialVersionUID = -1862937393590213844L;
    private final TicketGrantingTicket ticketGrantingTicket;

    public CasProxyGrantingTicketCreatedEvent(Object source, TicketGrantingTicket ticketGrantingTicket) {
        super(source);
        this.ticketGrantingTicket = ticketGrantingTicket;
    }

    @Override
    @Generated
    public String toString() {
        return "CasProxyGrantingTicketCreatedEvent(super=" + super.toString() + ", ticketGrantingTicket=" + this.ticketGrantingTicket + ")";
    }

    @Generated
    public TicketGrantingTicket getTicketGrantingTicket() {
        return this.ticketGrantingTicket;
    }
}

