/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.ticket.Ticket
 *  org.apereo.cas.ticket.registry.TicketRegistry
 *  org.apereo.cas.util.PublisherIdentifier
 *  org.apereo.cas.util.spring.ApplicationContextProvider
 */
package org.apereo.cas.ticket.queue;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.registry.TicketRegistry;
import org.apereo.cas.ticket.serialization.TicketSerializationManager;
import org.apereo.cas.util.PublisherIdentifier;
import org.apereo.cas.util.spring.ApplicationContextProvider;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public abstract class BaseMessageQueueCommand
implements Serializable {
    private static final long serialVersionUID = 7050449807845156228L;
    private final PublisherIdentifier id;

    public void execute(TicketRegistry registry) throws Exception {
    }

    protected static Ticket deserializeTicket(String ticket, String ticketType) {
        TicketSerializationManager manager = (TicketSerializationManager)ApplicationContextProvider.getApplicationContext().getBean(TicketSerializationManager.class);
        return manager.deserializeTicket(ticket, ticketType);
    }

    @Generated
    public String toString() {
        return "BaseMessageQueueCommand(id=" + this.id + ")";
    }

    @Generated
    public PublisherIdentifier getId() {
        return this.id;
    }

    @Generated
    protected BaseMessageQueueCommand(PublisherIdentifier id) {
        this.id = id;
    }
}

