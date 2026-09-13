/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.Credential
 */
package org.apereo.cas.ticket.proxy;

import org.apereo.cas.authentication.Credential;
import org.apereo.cas.ticket.TicketGrantingTicket;

@FunctionalInterface
public interface ProxyHandler {
    public String handle(Credential var1, TicketGrantingTicket var2);

    default public boolean canHandle(Credential credential) {
        return true;
    }
}

