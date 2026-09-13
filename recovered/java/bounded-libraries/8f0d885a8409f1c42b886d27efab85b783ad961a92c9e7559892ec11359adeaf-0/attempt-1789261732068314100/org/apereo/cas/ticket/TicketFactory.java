/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CanIgnoreReturnValue
 */
package org.apereo.cas.ticket;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.apereo.cas.ticket.Ticket;

@FunctionalInterface
public interface TicketFactory {
    public static final String BEAN_NAME = "defaultTicketFactory";

    default public String getName() {
        return this.getClass().getSimpleName();
    }

    @CanIgnoreReturnValue
    default public TicketFactory get(Class<? extends Ticket> clazz) {
        return this;
    }

    public Class<? extends Ticket> getTicketType();
}

