/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.ticket.registry;

import org.apereo.cas.ticket.Ticket;

public interface TicketRegistryCleaner {
    default public int clean() {
        return 0;
    }

    default public int cleanTicket(Ticket ticket) {
        return 0;
    }
}

