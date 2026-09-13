/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.ticket.Ticket
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
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.queue.BaseMessageQueueCommand;
import org.apereo.cas.ticket.registry.TicketRegistry;
import org.apereo.cas.util.PublisherIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class AddTicketMessageQueueCommand
extends BaseMessageQueueCommand {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AddTicketMessageQueueCommand.class);
    private static final long serialVersionUID = -7698722632898271240L;
    @JsonProperty
    private String ticket;
    @JsonProperty
    private String ticketType;

    @JsonCreator
    public AddTicketMessageQueueCommand(@JsonProperty(value="id") PublisherIdentifier id, @JsonProperty(value="ticket") String ticket, @JsonProperty(value="ticketType") String ticketType) {
        super(id);
        this.ticket = ticket;
        this.ticketType = ticketType;
    }

    @Override
    public void execute(TicketRegistry registry) throws Exception {
        LOGGER.debug("Executing queue command on ticket registry id [{}] to add ticket [{}]", (Object)this.getId().getId(), (Object)this.ticket);
        Ticket result = AddTicketMessageQueueCommand.deserializeTicket(this.ticket, this.ticketType);
        registry.addTicket(result);
    }

    @Generated
    public String getTicket() {
        return this.ticket;
    }

    @Generated
    public String getTicketType() {
        return this.ticketType;
    }
}

