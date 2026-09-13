/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.util.serialization.AbstractJacksonBackedStringSerializer
 */
package org.apereo.cas.ticket.serialization.serializers;

import org.apereo.cas.ticket.ServiceTicketImpl;
import org.apereo.cas.util.serialization.AbstractJacksonBackedStringSerializer;

public class ServiceTicketStringSerializer
extends AbstractJacksonBackedStringSerializer<ServiceTicketImpl> {
    private static final long serialVersionUID = 8959617299162115085L;

    public ServiceTicketStringSerializer() {
        super(MINIMAL_PRETTY_PRINTER);
    }

    public Class<ServiceTicketImpl> getTypeToSerialize() {
        return ServiceTicketImpl.class;
    }
}

