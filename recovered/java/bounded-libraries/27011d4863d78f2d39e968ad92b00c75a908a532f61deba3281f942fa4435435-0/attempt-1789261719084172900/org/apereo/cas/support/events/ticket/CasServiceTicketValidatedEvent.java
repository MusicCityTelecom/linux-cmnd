/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.ticket.ServiceTicket
 *  org.apereo.cas.validation.Assertion
 */
package org.apereo.cas.support.events.ticket;

import lombok.Generated;
import org.apereo.cas.support.events.AbstractCasEvent;
import org.apereo.cas.ticket.ServiceTicket;
import org.apereo.cas.validation.Assertion;

public class CasServiceTicketValidatedEvent
extends AbstractCasEvent {
    private static final long serialVersionUID = -1218257740549089556L;
    private final transient Assertion assertion;
    private final ServiceTicket serviceTicket;

    public CasServiceTicketValidatedEvent(Object source, ServiceTicket serviceTicket, Assertion assertion) {
        super(source);
        this.assertion = assertion;
        this.serviceTicket = serviceTicket;
    }

    @Override
    @Generated
    public String toString() {
        return "CasServiceTicketValidatedEvent(super=" + super.toString() + ", assertion=" + this.assertion + ", serviceTicket=" + this.serviceTicket + ")";
    }

    @Generated
    public Assertion getAssertion() {
        return this.assertion;
    }

    @Generated
    public ServiceTicket getServiceTicket() {
        return this.serviceTicket;
    }
}

