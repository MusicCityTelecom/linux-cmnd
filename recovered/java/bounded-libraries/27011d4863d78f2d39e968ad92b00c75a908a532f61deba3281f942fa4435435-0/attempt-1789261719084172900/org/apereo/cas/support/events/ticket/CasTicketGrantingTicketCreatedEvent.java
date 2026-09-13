/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.ticket.TicketGrantingTicket
 */
package org.apereo.cas.support.events.ticket;

import lombok.Generated;
import org.apereo.cas.support.events.ticket.AbstractCasTicketGrantingTicketEvent;
import org.apereo.cas.ticket.TicketGrantingTicket;

public class CasTicketGrantingTicketCreatedEvent
extends AbstractCasTicketGrantingTicketEvent {
    private static final long serialVersionUID = -1862937393590213844L;

    public CasTicketGrantingTicketCreatedEvent(Object source, TicketGrantingTicket ticketGrantingTicket) {
        super(source, ticketGrantingTicket);
    }

    @Override
    @Generated
    public String toString() {
        return "CasTicketGrantingTicketCreatedEvent(super=" + super.toString() + ")";
    }
}

