/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.logout;

import org.apereo.cas.ticket.TicketGrantingTicket;
import org.springframework.core.Ordered;

@FunctionalInterface
public interface LogoutPostProcessor
extends Ordered {
    default public int getOrder() {
        return Integer.MIN_VALUE;
    }

    public void handle(TicketGrantingTicket var1);

    default public String getName() {
        return this.getClass().getSimpleName();
    }
}

