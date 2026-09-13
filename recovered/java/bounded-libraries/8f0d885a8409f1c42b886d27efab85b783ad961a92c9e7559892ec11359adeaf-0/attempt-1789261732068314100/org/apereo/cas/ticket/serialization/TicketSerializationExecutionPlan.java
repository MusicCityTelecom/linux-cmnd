/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.util.serialization.StringSerializer
 */
package org.apereo.cas.ticket.serialization;

import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.util.serialization.StringSerializer;

public interface TicketSerializationExecutionPlan {
    public void registerTicketSerializer(StringSerializer<? extends Ticket> var1);

    public void registerTicketSerializer(String var1, StringSerializer<? extends Ticket> var2);

    public StringSerializer<Ticket> getTicketSerializer(Class<? extends Ticket> var1);

    public StringSerializer<Ticket> getTicketSerializer(Ticket var1);

    public StringSerializer<Ticket> getTicketSerializer(String var1);
}

