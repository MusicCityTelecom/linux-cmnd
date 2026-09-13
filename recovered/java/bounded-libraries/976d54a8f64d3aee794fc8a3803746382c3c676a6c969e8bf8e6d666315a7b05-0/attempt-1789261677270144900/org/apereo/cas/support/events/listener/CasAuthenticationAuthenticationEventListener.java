/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.adaptive.geo.GeoLocationRequest
 *  org.apereo.cas.support.events.AbstractCasEvent
 *  org.apereo.cas.support.events.CasEventRepository
 *  org.apereo.cas.support.events.authentication.CasAuthenticationPolicyFailureEvent
 *  org.apereo.cas.support.events.authentication.CasAuthenticationTransactionFailureEvent
 *  org.apereo.cas.support.events.authentication.adaptive.CasRiskyAuthenticationDetectedEvent
 *  org.apereo.cas.support.events.dao.CasEvent
 *  org.apereo.cas.support.events.ticket.CasTicketGrantingTicketCreatedEvent
 *  org.apereo.cas.support.events.ticket.CasTicketGrantingTicketDestroyedEvent
 *  org.apereo.cas.util.DateTimeUtils
 *  org.apereo.cas.util.HttpRequestUtils
 *  org.apereo.cas.util.text.MessageSanitizer
 *  org.apereo.inspektr.common.web.ClientInfo
 *  org.apereo.inspektr.common.web.ClientInfoHolder
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.support.events.listener;

import java.time.Instant;
import java.time.ZonedDateTime;
import lombok.Generated;
import org.apereo.cas.authentication.adaptive.geo.GeoLocationRequest;
import org.apereo.cas.support.events.AbstractCasEvent;
import org.apereo.cas.support.events.CasEventRepository;
import org.apereo.cas.support.events.authentication.CasAuthenticationPolicyFailureEvent;
import org.apereo.cas.support.events.authentication.CasAuthenticationTransactionFailureEvent;
import org.apereo.cas.support.events.authentication.adaptive.CasRiskyAuthenticationDetectedEvent;
import org.apereo.cas.support.events.dao.CasEvent;
import org.apereo.cas.support.events.listener.CasAuthenticationEventListener;
import org.apereo.cas.support.events.ticket.CasTicketGrantingTicketCreatedEvent;
import org.apereo.cas.support.events.ticket.CasTicketGrantingTicketDestroyedEvent;
import org.apereo.cas.util.DateTimeUtils;
import org.apereo.cas.util.HttpRequestUtils;
import org.apereo.cas.util.text.MessageSanitizer;
import org.apereo.inspektr.common.web.ClientInfo;
import org.apereo.inspektr.common.web.ClientInfoHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CasAuthenticationAuthenticationEventListener
implements CasAuthenticationEventListener {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CasAuthenticationAuthenticationEventListener.class);
    private final CasEventRepository casEventRepository;
    private final MessageSanitizer messageSanitizer;

    private static CasEvent prepareCasEvent(AbstractCasEvent event) {
        CasEvent dto = new CasEvent();
        dto.setType(event.getClass().getCanonicalName());
        dto.putTimestamp(Long.valueOf(event.getTimestamp()));
        ZonedDateTime dt = DateTimeUtils.zonedDateTimeOf((Instant)Instant.ofEpochMilli(event.getTimestamp()));
        dto.setCreationTime(dt.toString());
        ClientInfo clientInfo = ClientInfoHolder.getClientInfo();
        if (clientInfo != null) {
            dto.putClientIpAddress(clientInfo.getClientIpAddress());
            dto.putServerIpAddress(clientInfo.getServerIpAddress());
            dto.putAgent(clientInfo.getUserAgent());
            GeoLocationRequest location = HttpRequestUtils.getHttpServletRequestGeoLocation((String)clientInfo.getGeoLocation());
            dto.putGeoLocation(location);
        } else {
            LOGGER.trace("No client information is available. The final event cannot track client location, user agent or IP addresses");
        }
        return dto;
    }

    @Override
    public void handleCasTicketGrantingTicketCreatedEvent(CasTicketGrantingTicketCreatedEvent event) throws Exception {
        CasEvent dto = CasAuthenticationAuthenticationEventListener.prepareCasEvent((AbstractCasEvent)event);
        dto.setCreationTime(event.getTicketGrantingTicket().getCreationTime().toString());
        dto.putEventId(this.messageSanitizer.sanitize(event.getTicketGrantingTicket().getId()));
        dto.setPrincipalId(event.getTicketGrantingTicket().getAuthentication().getPrincipal().getId());
        this.casEventRepository.save(dto);
    }

    @Override
    public void handleCasTicketGrantingTicketDeletedEvent(CasTicketGrantingTicketDestroyedEvent event) throws Exception {
        CasEvent dto = CasAuthenticationAuthenticationEventListener.prepareCasEvent((AbstractCasEvent)event);
        dto.setCreationTime(event.getTicketGrantingTicket().getCreationTime().toString());
        dto.putEventId(this.messageSanitizer.sanitize(event.getTicketGrantingTicket().getId()));
        dto.setPrincipalId(event.getTicketGrantingTicket().getAuthentication().getPrincipal().getId());
        this.casEventRepository.save(dto);
    }

    @Override
    public void handleCasAuthenticationTransactionFailureEvent(CasAuthenticationTransactionFailureEvent event) throws Exception {
        CasEvent dto = CasAuthenticationAuthenticationEventListener.prepareCasEvent((AbstractCasEvent)event);
        dto.setPrincipalId(event.getCredential().getId());
        dto.putEventId(CasAuthenticationPolicyFailureEvent.class.getSimpleName());
        this.casEventRepository.save(dto);
    }

    @Override
    public void handleCasAuthenticationPolicyFailureEvent(CasAuthenticationPolicyFailureEvent event) throws Exception {
        CasEvent dto = CasAuthenticationAuthenticationEventListener.prepareCasEvent((AbstractCasEvent)event);
        dto.setPrincipalId(event.getAuthentication().getPrincipal().getId());
        dto.putEventId(CasAuthenticationPolicyFailureEvent.class.getSimpleName());
        this.casEventRepository.save(dto);
    }

    @Override
    public void handleCasRiskyAuthenticationDetectedEvent(CasRiskyAuthenticationDetectedEvent event) throws Exception {
        CasEvent dto = CasAuthenticationAuthenticationEventListener.prepareCasEvent((AbstractCasEvent)event);
        dto.putEventId(event.getService().getName());
        dto.setPrincipalId(event.getAuthentication().getPrincipal().getId());
        this.casEventRepository.save(dto);
    }

    @Generated
    public CasAuthenticationAuthenticationEventListener(CasEventRepository casEventRepository, MessageSanitizer messageSanitizer) {
        this.casEventRepository = casEventRepository;
        this.messageSanitizer = messageSanitizer;
    }

    @Generated
    public CasEventRepository getCasEventRepository() {
        return this.casEventRepository;
    }

    @Generated
    public MessageSanitizer getMessageSanitizer() {
        return this.messageSanitizer;
    }
}

