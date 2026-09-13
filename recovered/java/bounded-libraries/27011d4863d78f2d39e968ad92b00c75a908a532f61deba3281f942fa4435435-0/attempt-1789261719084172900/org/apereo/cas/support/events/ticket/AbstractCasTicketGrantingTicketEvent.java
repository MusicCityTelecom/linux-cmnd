/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.ticket.TicketGrantingTicket
 */
package org.apereo.cas.support.events.ticket;

import java.time.ZonedDateTime;
import lombok.Generated;
import org.apereo.cas.support.events.AbstractCasEvent;
import org.apereo.cas.ticket.TicketGrantingTicket;

public abstract class AbstractCasTicketGrantingTicketEvent
extends AbstractCasEvent {
    public static final long serialVersionUID = 5815205609847140811L;
    private final TicketGrantingTicket ticketGrantingTicket;

    protected AbstractCasTicketGrantingTicketEvent(Object source, TicketGrantingTicket ticketGrantingTicket) {
        super(source);
        this.ticketGrantingTicket = ticketGrantingTicket;
    }

    public ZonedDateTime getCreationTime() {
        return this.ticketGrantingTicket.getCreationTime();
    }

    public String getId() {
        return this.ticketGrantingTicket.getId();
    }

    public Long getTimeToLive() {
        return this.ticketGrantingTicket.getExpirationPolicy().getTimeToLive();
    }

    public Long getTimeToIdle() {
        return this.ticketGrantingTicket.getExpirationPolicy().getTimeToIdle();
    }

    public String getPrincipalId() {
        return this.ticketGrantingTicket.getAuthentication().getPrincipal().getId();
    }

    @Override
    @Generated
    public String toString() {
        return "AbstractCasTicketGrantingTicketEvent(super=" + super.toString() + ", ticketGrantingTicket=" + this.ticketGrantingTicket + ")";
    }

    @Generated
    public TicketGrantingTicket getTicketGrantingTicket() {
        return this.ticketGrantingTicket;
    }
}

