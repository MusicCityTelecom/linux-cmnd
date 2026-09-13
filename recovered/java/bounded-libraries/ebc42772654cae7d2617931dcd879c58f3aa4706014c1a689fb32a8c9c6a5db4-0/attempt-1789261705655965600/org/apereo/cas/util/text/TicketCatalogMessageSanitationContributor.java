/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.ticket.TicketCatalog
 *  org.apereo.cas.ticket.TicketDefinition
 *  org.springframework.beans.factory.ObjectProvider
 */
package org.apereo.cas.util.text;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apereo.cas.ticket.TicketCatalog;
import org.apereo.cas.ticket.TicketDefinition;
import org.apereo.cas.util.text.MessageSanitationContributor;
import org.springframework.beans.factory.ObjectProvider;

public class TicketCatalogMessageSanitationContributor
implements MessageSanitationContributor {
    private final ObjectProvider<TicketCatalog> ticketCatalog;

    @Override
    public List<String> getTicketIdentifierPrefixes() {
        return this.ticketCatalog.stream().map(TicketCatalog::findAll).flatMap(Collection::stream).map(TicketDefinition::getPrefix).collect(Collectors.toList());
    }

    @Generated
    public TicketCatalogMessageSanitationContributor(ObjectProvider<TicketCatalog> ticketCatalog) {
        this.ticketCatalog = ticketCatalog;
    }
}

