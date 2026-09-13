/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.util.serialization.AbstractJacksonBackedStringSerializer
 */
package org.apereo.cas.ticket.serialization.serializers;

import org.apereo.cas.ticket.ProxyTicketImpl;
import org.apereo.cas.util.serialization.AbstractJacksonBackedStringSerializer;

public class ProxyTicketStringSerializer
extends AbstractJacksonBackedStringSerializer<ProxyTicketImpl> {
    private static final long serialVersionUID = -6343596853082798477L;

    public ProxyTicketStringSerializer() {
        super(MINIMAL_PRETTY_PRINTER);
    }

    public Class<ProxyTicketImpl> getTypeToSerialize() {
        return ProxyTicketImpl.class;
    }
}

