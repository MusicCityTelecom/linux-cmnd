/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.ticket.Ticket
 *  org.apereo.cas.util.crypto.CipherExecutor
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.ticket.registry;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.registry.AbstractTicketRegistry;
import org.apereo.cas.util.crypto.CipherExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractMapBasedTicketRegistry
extends AbstractTicketRegistry {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMapBasedTicketRegistry.class);

    protected AbstractMapBasedTicketRegistry(CipherExecutor cipherExecutor) {
        this.setCipherExecutor(cipherExecutor);
    }

    @Override
    public void addTicketInternal(Ticket ticket) throws Exception {
        Ticket encTicket = this.encodeTicket(ticket);
        LOGGER.debug("Putting ticket [{}] in registry.", (Object)ticket.getId());
        this.getMapInstance().put(encTicket.getId(), encTicket);
    }

    public Ticket getTicket(String ticketId, Predicate<Ticket> predicate) {
        String encTicketId = this.encodeTicketId(ticketId);
        if (StringUtils.isBlank((CharSequence)ticketId)) {
            return null;
        }
        Ticket found = this.getMapInstance().get(encTicketId);
        if (found == null) {
            LOGGER.debug("Ticket [{}] could not be found", (Object)encTicketId);
            return null;
        }
        Ticket result = this.decodeTicket(found);
        if (!predicate.test(result)) {
            LOGGER.debug("Cannot successfully fetch ticket [{}]", (Object)ticketId);
            return null;
        }
        return result;
    }

    @Override
    public long deleteSingleTicket(String ticketId) {
        String encTicketId = this.encodeTicketId(ticketId);
        return !StringUtils.isBlank((CharSequence)encTicketId) && this.getMapInstance().remove(encTicketId) != null ? 1L : 0L;
    }

    public long deleteAll() {
        int size = this.getMapInstance().size();
        this.getMapInstance().clear();
        return size;
    }

    public Collection<? extends Ticket> getTickets() {
        return this.decodeTickets(this.getMapInstance().values());
    }

    public Ticket updateTicket(Ticket ticket) throws Exception {
        LOGGER.trace("Updating ticket [{}] in registry...", (Object)ticket.getId());
        this.addTicket(ticket);
        return ticket;
    }

    public abstract Map<String, Ticket> getMapInstance();

    @Generated
    protected AbstractMapBasedTicketRegistry() {
    }
}

