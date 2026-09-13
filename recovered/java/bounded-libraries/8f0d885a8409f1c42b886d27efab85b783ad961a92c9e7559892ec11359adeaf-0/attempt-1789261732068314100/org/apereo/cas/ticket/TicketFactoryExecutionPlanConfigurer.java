/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.ticket;

import org.apereo.cas.ticket.TicketFactory;

@FunctionalInterface
public interface TicketFactoryExecutionPlanConfigurer {
    public TicketFactory configureTicketFactory();
}

