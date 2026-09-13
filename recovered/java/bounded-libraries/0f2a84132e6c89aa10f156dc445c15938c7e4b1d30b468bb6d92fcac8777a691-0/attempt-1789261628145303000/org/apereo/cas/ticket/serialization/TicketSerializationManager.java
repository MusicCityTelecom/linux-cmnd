/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.ticket.Ticket
 */
package org.apereo.cas.ticket.serialization;

import org.apereo.cas.ticket.Ticket;

public interface TicketSerializationManager {
    public static final String BEAN_NAME = "ticketSerializationManager";

    public String serializeTicket(Ticket var1);

    public Ticket deserializeTicket(String var1, String var2);

    public <T extends Ticket> T deserializeTicket(String var1, Class<T> var2);
}

