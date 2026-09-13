/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.ticket.ServiceTicket
 *  org.apereo.cas.ticket.TicketGrantingTicket
 */
package org.apereo.cas.support.events.ticket;

import lombok.Generated;
import org.apereo.cas.support.events.AbstractCasEvent;
import org.apereo.cas.ticket.ServiceTicket;
import org.apereo.cas.ticket.TicketGrantingTicket;

public class CasServiceTicketGrantedEvent
extends AbstractCasEvent {
    private static final long serialVersionUID = 128616377249711105L;
    private final TicketGrantingTicket ticketGrantingTicket;
    private final ServiceTicket serviceTicket;

    public CasServiceTicketGrantedEvent(Object source, TicketGrantingTicket ticketGrantingTicket, ServiceTicket serviceTicket) {
        super(source);
        this.ticketGrantingTicket = ticketGrantingTicket;
        this.serviceTicket = serviceTicket;
    }

    @Override
    @Generated
    public String toString() {
        return "CasServiceTicketGrantedEvent(super=" + super.toString() + ", ticketGrantingTicket=" + this.ticketGrantingTicket + ", serviceTicket=" + this.serviceTicket + ")";
    }

    @Generated
    public TicketGrantingTicket getTicketGrantingTicket() {
        return this.ticketGrantingTicket;
    }

    @Generated
    public ServiceTicket getServiceTicket() {
        return this.serviceTicket;
    }
}

