/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CanIgnoreReturnValue
 *  lombok.Generated
 *  lombok.NonNull
 *  org.apereo.cas.ticket.Ticket
 *  org.apereo.cas.ticket.TicketFactory
 */
package org.apereo.cas.ticket.factory;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import lombok.NonNull;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.TicketFactory;

public class DefaultTicketFactory
implements TicketFactory {
    private final Map<String, Object> factoryMap = new HashMap<String, Object>(0);

    public TicketFactory get(Class<? extends Ticket> clazz) {
        return (TicketFactory)this.factoryMap.get(clazz.getCanonicalName());
    }

    @CanIgnoreReturnValue
    public DefaultTicketFactory addTicketFactory(@NonNull Class<? extends Ticket> ticketClass, @NonNull TicketFactory factory) {
        if (ticketClass == null) {
            throw new NullPointerException("ticketClass is marked non-null but is null");
        }
        if (factory == null) {
            throw new NullPointerException("factory is marked non-null but is null");
        }
        this.factoryMap.put(ticketClass.getCanonicalName(), factory);
        return this;
    }

    public Class<? extends Ticket> getTicketType() {
        return Ticket.class;
    }

    @Generated
    public DefaultTicketFactory() {
    }
}

