/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.apereo.cas.ticket.proxy.ProxyHandler
 */
package org.apereo.cas.ticket.proxy.support;

import org.apereo.cas.authentication.Credential;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.proxy.ProxyHandler;

public class Cas10ProxyHandler
implements ProxyHandler {
    public String handle(Credential credential, TicketGrantingTicket proxyGrantingTicketId) {
        return null;
    }

    public boolean canHandle(Credential credential) {
        return false;
    }
}

