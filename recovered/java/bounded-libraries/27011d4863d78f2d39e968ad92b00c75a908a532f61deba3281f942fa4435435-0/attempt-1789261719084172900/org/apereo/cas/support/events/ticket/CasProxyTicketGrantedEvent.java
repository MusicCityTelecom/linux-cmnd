/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.ticket.proxy.ProxyGrantingTicket
 *  org.apereo.cas.ticket.proxy.ProxyTicket
 */
package org.apereo.cas.support.events.ticket;

import lombok.Generated;
import org.apereo.cas.support.events.AbstractCasEvent;
import org.apereo.cas.ticket.proxy.ProxyGrantingTicket;
import org.apereo.cas.ticket.proxy.ProxyTicket;

public class CasProxyTicketGrantedEvent
extends AbstractCasEvent {
    private static final long serialVersionUID = 128616377249711105L;
    private final ProxyGrantingTicket proxyGrantingTicket;
    private final ProxyTicket proxyTicket;

    public CasProxyTicketGrantedEvent(Object source, ProxyGrantingTicket proxyGrantingTicket, ProxyTicket proxyTicket) {
        super(source);
        this.proxyGrantingTicket = proxyGrantingTicket;
        this.proxyTicket = proxyTicket;
    }

    @Override
    @Generated
    public String toString() {
        return "CasProxyTicketGrantedEvent(super=" + super.toString() + ", proxyGrantingTicket=" + this.proxyGrantingTicket + ", proxyTicket=" + this.proxyTicket + ")";
    }

    @Generated
    public ProxyGrantingTicket getProxyGrantingTicket() {
        return this.proxyGrantingTicket;
    }

    @Generated
    public ProxyTicket getProxyTicket() {
        return this.proxyTicket;
    }
}

