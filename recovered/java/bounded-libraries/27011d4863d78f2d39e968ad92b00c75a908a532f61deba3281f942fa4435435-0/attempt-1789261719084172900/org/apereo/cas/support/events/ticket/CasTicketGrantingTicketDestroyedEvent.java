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

public class CasTicketGrantingTicketDestroyedEvent
extends AbstractCasTicketGrantingTicketEvent {
    private static final long serialVersionUID = 584961303690286494L;

    public CasTicketGrantingTicketDestroyedEvent(Object source, TicketGrantingTicket ticket) {
        super(source, ticket);
    }

    @Override
    @Generated
    public String toString() {
        return "CasTicketGrantingTicketDestroyedEvent(super=" + super.toString() + ")";
    }
}

