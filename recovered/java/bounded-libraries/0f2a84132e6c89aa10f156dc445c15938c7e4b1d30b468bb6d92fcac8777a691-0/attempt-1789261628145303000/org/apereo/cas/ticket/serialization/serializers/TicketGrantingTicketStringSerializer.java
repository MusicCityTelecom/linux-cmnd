/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.util.serialization.AbstractJacksonBackedStringSerializer
 */
package org.apereo.cas.ticket.serialization.serializers;

import org.apereo.cas.ticket.TicketGrantingTicketImpl;
import org.apereo.cas.util.serialization.AbstractJacksonBackedStringSerializer;

public class TicketGrantingTicketStringSerializer
extends AbstractJacksonBackedStringSerializer<TicketGrantingTicketImpl> {
    private static final long serialVersionUID = 1527874389457723545L;

    public TicketGrantingTicketStringSerializer() {
        super(MINIMAL_PRETTY_PRINTER);
    }

    public Class<TicketGrantingTicketImpl> getTypeToSerialize() {
        return TicketGrantingTicketImpl.class;
    }
}

