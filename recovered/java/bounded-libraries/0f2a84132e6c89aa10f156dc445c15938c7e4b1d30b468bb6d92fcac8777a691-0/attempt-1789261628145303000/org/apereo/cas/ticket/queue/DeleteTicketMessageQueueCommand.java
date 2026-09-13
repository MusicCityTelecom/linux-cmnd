/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.ticket.registry.TicketRegistry
 *  org.apereo.cas.util.PublisherIdentifier
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.ticket.queue;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Generated;
import org.apereo.cas.ticket.queue.BaseMessageQueueCommand;
import org.apereo.cas.ticket.registry.TicketRegistry;
import org.apereo.cas.util.PublisherIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class DeleteTicketMessageQueueCommand
extends BaseMessageQueueCommand {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteTicketMessageQueueCommand.class);
    private static final long serialVersionUID = 8183330712274484245L;
    @JsonProperty
    private String ticketId;

    @JsonCreator
    public DeleteTicketMessageQueueCommand(@JsonProperty(value="id") PublisherIdentifier id, @JsonProperty(value="ticketId") String ticketId) {
        super(id);
        this.ticketId = ticketId;
    }

    @Override
    public void execute(TicketRegistry registry) throws Exception {
        LOGGER.debug("Executing queue command on ticket registry id [{}] to delete ticket [{}]", (Object)this.getId().getId(), (Object)this.ticketId);
        registry.deleteTicket(this.ticketId);
    }

    @Generated
    public String getTicketId() {
        return this.ticketId;
    }
}

