/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.ticket.registry.TicketRegistryCleaner
 */
package org.apereo.cas.ticket.registry;

import lombok.Generated;
import org.apereo.cas.ticket.registry.TicketRegistryCleaner;

public class NoOpTicketRegistryCleaner
implements TicketRegistryCleaner {
    private static TicketRegistryCleaner INSTANCE;

    public static TicketRegistryCleaner getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NoOpTicketRegistryCleaner();
        }
        return INSTANCE;
    }

    @Generated
    public NoOpTicketRegistryCleaner() {
    }
}

