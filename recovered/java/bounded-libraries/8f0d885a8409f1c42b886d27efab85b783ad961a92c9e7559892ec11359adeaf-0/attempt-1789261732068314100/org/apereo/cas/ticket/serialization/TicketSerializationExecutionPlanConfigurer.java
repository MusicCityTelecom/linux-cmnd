/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.ticket.serialization;

import org.apereo.cas.ticket.serialization.TicketSerializationExecutionPlan;

@FunctionalInterface
public interface TicketSerializationExecutionPlanConfigurer {
    public void configureTicketSerialization(TicketSerializationExecutionPlan var1);
}

