/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.ticket;

import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.TicketDefinitionProperties;
import org.springframework.core.Ordered;

public interface TicketDefinition
extends Ordered,
Comparable<TicketDefinition> {
    public String getPrefix();

    public Class<? extends Ticket> getImplementationClass();

    public Class<? extends Ticket> getApiClass();

    public TicketDefinitionProperties getProperties();

    default public int getOrder() {
        return Integer.MAX_VALUE;
    }
}

