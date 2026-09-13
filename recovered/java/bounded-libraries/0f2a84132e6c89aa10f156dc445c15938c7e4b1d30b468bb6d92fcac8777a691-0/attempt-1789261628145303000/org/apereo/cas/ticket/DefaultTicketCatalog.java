/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.ticket.Ticket
 *  org.apereo.cas.ticket.TicketCatalog
 *  org.apereo.cas.ticket.TicketDefinition
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 */
package org.apereo.cas.ticket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.TicketCatalog;
import org.apereo.cas.ticket.TicketDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

public class DefaultTicketCatalog
implements TicketCatalog {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTicketCatalog.class);
    private final Map<String, TicketDefinition> ticketMetadataMap = new HashMap<String, TicketDefinition>(0);

    public TicketDefinition find(String ticketId) {
        int index = ticketId.indexOf(45);
        String prefix = index == -1 ? ticketId : ticketId.substring(0, index);
        TicketDefinition definition = this.ticketMetadataMap.values().stream().filter(md -> prefix.equalsIgnoreCase(md.getPrefix())).findFirst().orElse(null);
        if (definition == null) {
            LOGGER.error("Ticket definition for [{}] cannot be found in the ticket catalog which only contains the following ticket types: [{}]", (Object)ticketId, this.ticketMetadataMap.keySet());
        }
        return definition;
    }

    public TicketDefinition find(Ticket ticket) {
        LOGGER.trace("Locating ticket definition for ticket [{}]", (Object)ticket);
        return this.find(ticket.getId());
    }

    public Collection<TicketDefinition> findTicketImplementations(Class<? extends Ticket> ticketClass) {
        List<TicketDefinition> list = this.ticketMetadataMap.values().stream().sorted().filter(t -> ticketClass.isAssignableFrom(t.getImplementationClass())).collect(Collectors.toList());
        AnnotationAwareOrderComparator.sort(list);
        LOGGER.trace("Located all registered and known sorted ticket definitions [{}] that match [{}]", list, ticketClass);
        return list;
    }

    public Optional<TicketDefinition> findTicketDefinition(Class<? extends Ticket> ticketClass) {
        return this.ticketMetadataMap.values().stream().sorted().filter(t -> ticketClass.equals(t.getApiClass())).findFirst();
    }

    public void register(TicketDefinition ticketDefinition) {
        LOGGER.trace("Registering/Updating ticket definition [{}]", (Object)ticketDefinition);
        this.ticketMetadataMap.put(ticketDefinition.getPrefix(), ticketDefinition);
    }

    public void update(TicketDefinition metadata) {
        this.register(metadata);
    }

    public boolean contains(String ticketId) {
        LOGGER.trace("Locating ticket definition for [{}]", (Object)ticketId);
        return this.ticketMetadataMap.containsKey(ticketId);
    }

    public Collection<TicketDefinition> findAll() {
        ArrayList<TicketDefinition> list = new ArrayList<TicketDefinition>(this.ticketMetadataMap.values());
        AnnotationAwareOrderComparator.sort(list);
        LOGGER.trace("Located all registered and known sorted ticket definitions [{}]", list);
        return list;
    }

    @Generated
    public DefaultTicketCatalog() {
    }
}

