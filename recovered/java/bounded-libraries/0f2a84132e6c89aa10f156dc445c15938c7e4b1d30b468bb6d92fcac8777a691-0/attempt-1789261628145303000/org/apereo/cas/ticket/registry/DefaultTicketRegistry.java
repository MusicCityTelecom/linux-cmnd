/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.ticket.Ticket
 *  org.apereo.cas.util.crypto.CipherExecutor
 */
package org.apereo.cas.ticket.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Generated;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.registry.AbstractMapBasedTicketRegistry;
import org.apereo.cas.util.crypto.CipherExecutor;

public class DefaultTicketRegistry
extends AbstractMapBasedTicketRegistry {
    private final Map<String, Ticket> mapInstance;

    public DefaultTicketRegistry() {
        this(CipherExecutor.noOp());
    }

    public DefaultTicketRegistry(CipherExecutor cipherExecutor) {
        super(cipherExecutor);
        this.mapInstance = new ConcurrentHashMap<String, Ticket>();
    }

    public DefaultTicketRegistry(Map<String, Ticket> storageMap, CipherExecutor cipherExecutor) {
        super(cipherExecutor);
        this.mapInstance = storageMap;
    }

    @Override
    @Generated
    public Map<String, Ticket> getMapInstance() {
        return this.mapInstance;
    }
}

