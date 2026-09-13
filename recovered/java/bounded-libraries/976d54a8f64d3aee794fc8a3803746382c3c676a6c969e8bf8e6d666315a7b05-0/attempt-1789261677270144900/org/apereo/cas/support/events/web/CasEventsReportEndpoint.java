/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.v3.oas.annotations.Operation
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.support.events.CasEventRepository
 *  org.apereo.cas.support.events.dao.CasEvent
 *  org.apereo.cas.web.BaseCasActuatorEndpoint
 *  org.springframework.boot.actuate.endpoint.annotation.Endpoint
 *  org.springframework.boot.actuate.endpoint.annotation.ReadOperation
 *  org.springframework.context.ApplicationContext
 */
package org.apereo.cas.support.events.web;

import io.swagger.v3.oas.annotations.Operation;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.support.events.CasEventRepository;
import org.apereo.cas.support.events.dao.CasEvent;
import org.apereo.cas.web.BaseCasActuatorEndpoint;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.ApplicationContext;

@Endpoint(id="events", enableByDefault=false)
public class CasEventsReportEndpoint
extends BaseCasActuatorEndpoint {
    private static final long LIMIT = 1000L;
    private final ApplicationContext applicationContext;

    public CasEventsReportEndpoint(CasConfigurationProperties casProperties, ApplicationContext applicationContext) {
        super(casProperties);
        this.applicationContext = applicationContext;
    }

    @ReadOperation
    @Operation(summary="Provide a report of CAS events in the event repository")
    public List<? extends CasEvent> events() {
        CasEventRepository eventRepository = (CasEventRepository)this.applicationContext.getBean("casEventRepository", CasEventRepository.class);
        return eventRepository.load().sorted(Comparator.comparingLong(CasEvent::getTimestamp).reversed()).limit(1000L).collect(Collectors.toList());
    }
}

