/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.ticket.AuthenticationAwareTicket
 *  org.apereo.cas.ticket.Ticket
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.apereo.cas.ticket.registry.TicketRegistry
 *  org.apereo.cas.ticket.registry.TicketRegistrySupport
 *  org.apereo.cas.util.function.FunctionUtils
 */
package org.apereo.cas.ticket.registry;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.ticket.AuthenticationAwareTicket;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.registry.TicketRegistry;
import org.apereo.cas.ticket.registry.TicketRegistrySupport;
import org.apereo.cas.util.function.FunctionUtils;

public class DefaultTicketRegistrySupport
implements TicketRegistrySupport {
    private final TicketRegistry ticketRegistry;

    public Ticket getTicket(String ticketId) {
        if (StringUtils.isBlank((CharSequence)ticketId)) {
            return null;
        }
        return (Ticket)FunctionUtils.doAndHandle(() -> {
            Ticket state = this.ticketRegistry.getTicket(ticketId, Ticket.class);
            return state == null || state.isExpired() ? null : state;
        });
    }

    public TicketGrantingTicket getTicketGrantingTicket(String ticketGrantingTicketId) {
        if (StringUtils.isBlank((CharSequence)ticketGrantingTicketId)) {
            return null;
        }
        TicketGrantingTicket tgt = (TicketGrantingTicket)this.getTicket(ticketGrantingTicketId);
        return tgt == null || tgt.isExpired() ? null : tgt;
    }

    public Authentication getAuthenticationFrom(String ticketGrantingTicketId) {
        if (StringUtils.isBlank((CharSequence)ticketGrantingTicketId)) {
            return null;
        }
        TicketGrantingTicket tgt = this.getTicketGrantingTicket(ticketGrantingTicketId);
        return Optional.ofNullable(tgt).map(AuthenticationAwareTicket::getAuthentication).orElse(null);
    }

    public Principal getAuthenticatedPrincipalFrom(String ticketGrantingTicketId) {
        Authentication auth = this.getAuthenticationFrom(ticketGrantingTicketId);
        return Optional.ofNullable(auth).map(Authentication::getPrincipal).orElse(null);
    }

    public Map<String, List<Object>> getPrincipalAttributesFrom(String ticketGrantingTicketId) {
        Principal principal = this.getAuthenticatedPrincipalFrom(ticketGrantingTicketId);
        return Optional.ofNullable(principal).map(Principal::getAttributes).orElse(null);
    }

    public void updateAuthentication(String ticketGrantingTicketId, Authentication authentication) throws Exception {
        TicketGrantingTicket tgt;
        if (StringUtils.isNotBlank((CharSequence)ticketGrantingTicketId) && (tgt = (TicketGrantingTicket)this.getTicket(ticketGrantingTicketId)) != null && !tgt.isExpired()) {
            tgt.getAuthentication().update(authentication);
            this.ticketRegistry.updateTicket((Ticket)tgt);
        }
    }

    @Generated
    public DefaultTicketRegistrySupport(TicketRegistry ticketRegistry) {
        this.ticketRegistry = ticketRegistry;
    }

    @Generated
    public TicketRegistry getTicketRegistry() {
        return this.ticketRegistry;
    }
}

