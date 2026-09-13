/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.principal.Principal
 */
package org.apereo.cas.ticket.registry;

import java.util.List;
import java.util.Map;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.registry.TicketRegistry;

public interface TicketRegistrySupport {
    public static final String BEAN_NAME = "defaultTicketRegistrySupport";

    public Authentication getAuthenticationFrom(String var1);

    public TicketGrantingTicket getTicketGrantingTicket(String var1);

    public Ticket getTicket(String var1);

    public Principal getAuthenticatedPrincipalFrom(String var1);

    public Map<String, List<Object>> getPrincipalAttributesFrom(String var1);

    public void updateAuthentication(String var1, Authentication var2) throws Exception;

    public TicketRegistry getTicketRegistry();
}

