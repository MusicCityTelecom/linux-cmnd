/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.ticket.registry.TicketRegistry
 *  org.apereo.cas.util.PublisherIdentifier
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.ticket.queue;

import lombok.Generated;
import org.apereo.cas.ticket.queue.BaseMessageQueueCommand;
import org.apereo.cas.ticket.registry.TicketRegistry;
import org.apereo.cas.util.PublisherIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseTicketRegistryQueueReceiver {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTicketRegistryQueueReceiver.class);
    private final TicketRegistry ticketRegistry;
    private final PublisherIdentifier ticketRegistryId;

    public void receive(BaseMessageQueueCommand command) throws Exception {
        if (!command.getId().equals((Object)this.getTicketRegistryId())) {
            LOGGER.debug("Received message from ticket registry id [{}]. Executing command [{}]", (Object)command.getId(), (Object)command.getClass().getSimpleName());
            command.execute(this.getTicketRegistry());
        } else {
            LOGGER.trace("Ignoring inbound command on ticket registry with id [{}]", (Object)this.getTicketRegistryId());
        }
    }

    @Generated
    public BaseTicketRegistryQueueReceiver(TicketRegistry ticketRegistry, PublisherIdentifier ticketRegistryId) {
        this.ticketRegistry = ticketRegistry;
        this.ticketRegistryId = ticketRegistryId;
    }

    @Generated
    public TicketRegistry getTicketRegistry() {
        return this.ticketRegistry;
    }

    @Generated
    public PublisherIdentifier getTicketRegistryId() {
        return this.ticketRegistryId;
    }
}

