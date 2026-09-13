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
public class DeleteTicketsMessageQueueCommand
extends BaseMessageQueueCommand {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteTicketsMessageQueueCommand.class);
    private static final long serialVersionUID = 8907022828993467474L;

    @JsonCreator
    public DeleteTicketsMessageQueueCommand(@JsonProperty(value="id") PublisherIdentifier id) {
        super(id);
    }

    @Override
    public void execute(TicketRegistry registry) {
        LOGGER.debug("Executing queue command on ticket registry id [{}] to delete tickets", (Object)this.getId().getId());
        registry.deleteAll();
    }
}

