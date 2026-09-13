/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.ticket;

import java.util.Collection;
import java.util.Optional;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.TicketDefinition;

public interface TicketCatalog {
    public static final String BEAN_NAME = "ticketCatalog";

    public void register(TicketDefinition var1);

    public void update(TicketDefinition var1);

    public boolean contains(String var1);

    public TicketDefinition find(String var1);

    public TicketDefinition find(Ticket var1);

    public Collection<TicketDefinition> findTicketImplementations(Class<? extends Ticket> var1);

    public Optional<TicketDefinition> findTicketDefinition(Class<? extends Ticket> var1);

    public Collection<TicketDefinition> findAll();
}

