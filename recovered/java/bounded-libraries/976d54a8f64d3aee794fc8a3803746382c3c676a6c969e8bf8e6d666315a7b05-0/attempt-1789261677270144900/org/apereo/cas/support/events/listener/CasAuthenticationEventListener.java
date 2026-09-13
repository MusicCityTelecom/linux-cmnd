/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.support.events.authentication.CasAuthenticationPolicyFailureEvent
 *  org.apereo.cas.support.events.authentication.CasAuthenticationTransactionFailureEvent
 *  org.apereo.cas.support.events.authentication.adaptive.CasRiskyAuthenticationDetectedEvent
 *  org.apereo.cas.support.events.ticket.CasTicketGrantingTicketCreatedEvent
 *  org.apereo.cas.support.events.ticket.CasTicketGrantingTicketDestroyedEvent
 *  org.apereo.cas.util.spring.CasEventListener
 *  org.springframework.context.event.EventListener
 *  org.springframework.scheduling.annotation.Async
 */
package org.apereo.cas.support.events.listener;

import org.apereo.cas.support.events.authentication.CasAuthenticationPolicyFailureEvent;
import org.apereo.cas.support.events.authentication.CasAuthenticationTransactionFailureEvent;
import org.apereo.cas.support.events.authentication.adaptive.CasRiskyAuthenticationDetectedEvent;
import org.apereo.cas.support.events.ticket.CasTicketGrantingTicketCreatedEvent;
import org.apereo.cas.support.events.ticket.CasTicketGrantingTicketDestroyedEvent;
import org.apereo.cas.util.spring.CasEventListener;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

public interface CasAuthenticationEventListener
extends CasEventListener {
    @EventListener
    @Async
    public void handleCasTicketGrantingTicketCreatedEvent(CasTicketGrantingTicketCreatedEvent var1) throws Exception;

    @EventListener
    @Async
    public void handleCasTicketGrantingTicketDeletedEvent(CasTicketGrantingTicketDestroyedEvent var1) throws Exception;

    @EventListener
    @Async
    public void handleCasAuthenticationTransactionFailureEvent(CasAuthenticationTransactionFailureEvent var1) throws Exception;

    @EventListener
    @Async
    public void handleCasAuthenticationPolicyFailureEvent(CasAuthenticationPolicyFailureEvent var1) throws Exception;

    @EventListener
    @Async
    public void handleCasRiskyAuthenticationDetectedEvent(CasRiskyAuthenticationDetectedEvent var1) throws Exception;
}

