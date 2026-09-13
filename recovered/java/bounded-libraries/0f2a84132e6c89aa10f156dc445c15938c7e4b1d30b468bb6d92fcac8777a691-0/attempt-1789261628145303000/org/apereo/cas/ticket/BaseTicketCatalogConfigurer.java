/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.ticket.Ticket
 *  org.apereo.cas.ticket.TicketCatalog
 *  org.apereo.cas.ticket.TicketCatalogConfigurer
 *  org.apereo.cas.ticket.TicketDefinition
 */
package org.apereo.cas.ticket;

import org.apereo.cas.ticket.DefaultTicketDefinition;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.TicketCatalog;
import org.apereo.cas.ticket.TicketCatalogConfigurer;
import org.apereo.cas.ticket.TicketDefinition;

public abstract class BaseTicketCatalogConfigurer
implements TicketCatalogConfigurer {
    protected TicketDefinition buildTicketDefinition(TicketCatalog plan, String prefix, Class<? extends Ticket> api, Class<? extends Ticket> impl, int order) {
        if (plan.contains(prefix)) {
            return plan.find(prefix);
        }
        return new DefaultTicketDefinition(impl, api, prefix, order);
    }

    protected TicketDefinition buildTicketDefinition(TicketCatalog plan, String prefix, Class impl, Class api) {
        if (plan.contains(prefix)) {
            return plan.find(prefix);
        }
        return new DefaultTicketDefinition(impl, api, prefix, Integer.MAX_VALUE);
    }

    protected void registerTicketDefinition(TicketCatalog plan, TicketDefinition metadata) {
        plan.register(metadata);
    }
}

