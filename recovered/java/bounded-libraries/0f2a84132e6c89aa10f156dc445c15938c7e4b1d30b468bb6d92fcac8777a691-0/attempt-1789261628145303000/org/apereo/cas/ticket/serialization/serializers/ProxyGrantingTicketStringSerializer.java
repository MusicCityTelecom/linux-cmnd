/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.util.serialization.AbstractJacksonBackedStringSerializer
 */
package org.apereo.cas.ticket.serialization.serializers;

import org.apereo.cas.ticket.ProxyGrantingTicketImpl;
import org.apereo.cas.util.serialization.AbstractJacksonBackedStringSerializer;

public class ProxyGrantingTicketStringSerializer
extends AbstractJacksonBackedStringSerializer<ProxyGrantingTicketImpl> {
    private static final long serialVersionUID = 7089208351327601379L;

    public ProxyGrantingTicketStringSerializer() {
        super(MINIMAL_PRETTY_PRINTER);
    }

    public Class<ProxyGrantingTicketImpl> getTypeToSerialize() {
        return ProxyGrantingTicketImpl.class;
    }
}

