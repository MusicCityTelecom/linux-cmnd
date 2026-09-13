/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.ticket.AuthenticatedServicesAwareTicketGrantingTicket
 *  org.apereo.cas.ticket.ServiceTicket
 *  org.apereo.cas.ticket.ServiceTicketSessionTrackingPolicy
 *  org.apereo.cas.ticket.registry.TicketRegistry
 *  org.jooq.lambda.Unchecked
 */
package org.apereo.cas.ticket;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.ticket.AuthenticatedServicesAwareTicketGrantingTicket;
import org.apereo.cas.ticket.ServiceTicket;
import org.apereo.cas.ticket.ServiceTicketSessionTrackingPolicy;
import org.apereo.cas.ticket.registry.TicketRegistry;
import org.jooq.lambda.Unchecked;

public class DefaultServiceTicketSessionTrackingPolicy
implements ServiceTicketSessionTrackingPolicy {
    private final CasConfigurationProperties casProperties;
    private final TicketRegistry ticketRegistry;

    public synchronized void track(AuthenticatedServicesAwareTicketGrantingTicket ownerTicket, ServiceTicket serviceTicket) {
        ownerTicket.update();
        serviceTicket.getService().setPrincipal(ownerTicket.getRoot().getAuthentication().getPrincipal().getId());
        boolean onlyTrackMostRecentSession = this.casProperties.getTicket().getTgt().getCore().isOnlyTrackMostRecentSession();
        if (onlyTrackMostRecentSession) {
            String path = DefaultServiceTicketSessionTrackingPolicy.normalizePath(serviceTicket.getService());
            List toRemove = ownerTicket.getServices().entrySet().stream().filter(entry -> {
                String normalizedExistingPath = DefaultServiceTicketSessionTrackingPolicy.normalizePath((Service)entry.getValue());
                return path.equals(normalizedExistingPath);
            }).collect(Collectors.toList());
            toRemove.forEach(Unchecked.consumer(entry -> {
                ownerTicket.getServices().remove(entry.getKey());
                this.ticketRegistry.deleteTicket((String)entry.getKey());
            }));
        }
        ownerTicket.getServices().put(serviceTicket.getId(), serviceTicket.getService());
    }

    private static String normalizePath(Service service) {
        String path = service.getId();
        path = StringUtils.substringBefore((String)path, (String)"?");
        path = StringUtils.substringBefore((String)path, (String)";");
        path = StringUtils.substringBefore((String)path, (String)"#");
        return path;
    }

    @Generated
    public DefaultServiceTicketSessionTrackingPolicy(CasConfigurationProperties casProperties, TicketRegistry ticketRegistry) {
        this.casProperties = casProperties;
        this.ticketRegistry = ticketRegistry;
    }
}

