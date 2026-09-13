/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationCredentialsThreadLocalBinder
 *  org.apereo.cas.logout.LogoutManager
 *  org.apereo.cas.logout.SingleLogoutExecutionRequest
 *  org.apereo.cas.logout.slo.SingleLogoutRequestContext
 *  org.apereo.cas.logout.slo.SingleLogoutRequestExecutor
 *  org.apereo.cas.support.events.ticket.CasTicketGrantingTicketDestroyedEvent
 *  org.apereo.cas.ticket.Ticket
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.apereo.cas.ticket.registry.TicketRegistry
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationEvent
 */
package org.apereo.cas.logout.slo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationCredentialsThreadLocalBinder;
import org.apereo.cas.logout.LogoutManager;
import org.apereo.cas.logout.SingleLogoutExecutionRequest;
import org.apereo.cas.logout.slo.SingleLogoutRequestContext;
import org.apereo.cas.logout.slo.SingleLogoutRequestExecutor;
import org.apereo.cas.support.events.ticket.CasTicketGrantingTicketDestroyedEvent;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.registry.TicketRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

public class DefaultSingleLogoutRequestExecutor
implements SingleLogoutRequestExecutor {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSingleLogoutRequestExecutor.class);
    private final TicketRegistry ticketRegistry;
    private final LogoutManager logoutManager;
    private final ApplicationContext applicationContext;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<SingleLogoutRequestContext> execute(String ticketId, HttpServletRequest request, HttpServletResponse response) {
        Authentication ca = AuthenticationCredentialsThreadLocalBinder.getCurrentAuthentication();
        try {
            Ticket ticket = this.ticketRegistry.getTicket(ticketId, Ticket.class);
            LOGGER.debug("Ticket [{}] found. Processing logout requests and then deleting the ticket...", (Object)ticket.getId());
            ArrayList<SingleLogoutRequestContext> logoutRequests = new ArrayList<SingleLogoutRequestContext>();
            if (ticket instanceof TicketGrantingTicket) {
                TicketGrantingTicket tgt = (TicketGrantingTicket)ticket;
                AuthenticationCredentialsThreadLocalBinder.bindCurrent((Authentication)tgt.getAuthentication());
                logoutRequests.addAll(this.logoutManager.performLogout(SingleLogoutExecutionRequest.builder().ticketGrantingTicket(tgt).httpServletRequest(Optional.of(request)).httpServletResponse(Optional.of(response)).build()));
                this.applicationContext.publishEvent((ApplicationEvent)new CasTicketGrantingTicketDestroyedEvent((Object)this, tgt));
            }
            LOGGER.trace("Removing ticket [{}] from registry...", (Object)ticketId);
            this.ticketRegistry.deleteTicket(ticketId);
            ArrayList<SingleLogoutRequestContext> arrayList = logoutRequests;
            return arrayList;
        }
        catch (Exception e) {
            String msg = String.format("Ticket-granting ticket [%s] cannot be found in the ticket registry.", ticketId);
            LOGGER.debug(msg, (Throwable)e);
        }
        finally {
            AuthenticationCredentialsThreadLocalBinder.bindCurrent((Authentication)ca);
        }
        return new ArrayList<SingleLogoutRequestContext>(0);
    }

    @Generated
    public DefaultSingleLogoutRequestExecutor(TicketRegistry ticketRegistry, LogoutManager logoutManager, ApplicationContext applicationContext) {
        this.ticketRegistry = ticketRegistry;
        this.logoutManager = logoutManager;
        this.applicationContext = applicationContext;
    }
}

