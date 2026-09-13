/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.util.serialization.AbstractJacksonBackedStringSerializer
 */
package org.apereo.cas.ticket.serialization.serializers;

import org.apereo.cas.ticket.registry.DefaultEncodedTicket;
import org.apereo.cas.util.serialization.AbstractJacksonBackedStringSerializer;

public class EncodedTicketStringSerializer
extends AbstractJacksonBackedStringSerializer<DefaultEncodedTicket> {
    private static final long serialVersionUID = 8959835299162115085L;

    public EncodedTicketStringSerializer() {
        super(MINIMAL_PRETTY_PRINTER);
    }

    public Class<DefaultEncodedTicket> getTypeToSerialize() {
        return DefaultEncodedTicket.class;
    }
}

