/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.util.serialization.AbstractJacksonBackedStringSerializer
 */
package org.apereo.cas.ticket.serialization.serializers;

import org.apereo.cas.ticket.TransientSessionTicketImpl;
import org.apereo.cas.util.serialization.AbstractJacksonBackedStringSerializer;

public class TransientSessionTicketStringSerializer
extends AbstractJacksonBackedStringSerializer<TransientSessionTicketImpl> {
    private static final long serialVersionUID = 8959617299162115085L;

    public TransientSessionTicketStringSerializer() {
        super(MINIMAL_PRETTY_PRINTER);
    }

    public Class<TransientSessionTicketImpl> getTypeToSerialize() {
        return TransientSessionTicketImpl.class;
    }
}

