/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.support.events.authentication.CasAuthenticationPrincipalResolvedEvent
 *  org.apereo.cas.support.events.authentication.CasAuthenticationTransactionFailureEvent
 *  org.apereo.cas.support.events.ticket.CasProxyTicketGrantedEvent
 *  org.apereo.cas.support.events.ticket.CasServiceTicketGrantedEvent
 *  org.apereo.cas.support.events.ticket.CasServiceTicketValidatedEvent
 *  org.apereo.cas.support.events.ticket.CasTicketGrantingTicketCreatedEvent
 *  org.apereo.cas.support.events.ticket.CasTicketGrantingTicketDestroyedEvent
 *  org.apereo.cas.util.spring.CasEventListener
 *  org.springframework.context.event.EventListener
 *  org.springframework.scheduling.annotation.Async
 */
package org.apereo.cas.support.events.listener;

import org.apereo.cas.support.events.authentication.CasAuthenticationPrincipalResolvedEvent;
import org.apereo.cas.support.events.authentication.CasAuthenticationTransactionFailureEvent;
import org.apereo.cas.support.events.ticket.CasProxyTicketGrantedEvent;
import org.apereo.cas.support.events.ticket.CasServiceTicketGrantedEvent;
import org.apereo.cas.support.events.ticket.CasServiceTicketValidatedEvent;
import org.apereo.cas.support.events.ticket.CasTicketGrantingTicketCreatedEvent;
import org.apereo.cas.support.events.ticket.CasTicketGrantingTicketDestroyedEvent;
import org.apereo.cas.util.spring.CasEventListener;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

public interface LoggingCasEventListener
extends CasEventListener {
    @EventListener
    @Async
    public void logTicketGrantingTicketCreatedEvent(CasTicketGrantingTicketCreatedEvent var1);

    @EventListener
    @Async
    public void logAuthenticationTransactionFailureEvent(CasAuthenticationTransactionFailureEvent var1);

    @EventListener
    @Async
    public void logAuthenticationPrincipalResolvedEvent(CasAuthenticationPrincipalResolvedEvent var1);

    @EventListener
    @Async
    public void logTicketGrantingTicketDestroyedEvent(CasTicketGrantingTicketDestroyedEvent var1);

    @EventListener
    @Async
    public void logProxyTicketGrantedEvent(CasProxyTicketGrantedEvent var1);

    @EventListener
    @Async
    public void logServiceTicketGrantedEvent(CasServiceTicketGrantedEvent var1);

    @EventListener
    @Async
    public void logServiceTicketValidatedEvent(CasServiceTicketValidatedEvent var1);
}

