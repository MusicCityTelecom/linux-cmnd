/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.CentralAuthenticationService
 *  org.apereo.cas.authentication.AuthenticationResult
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.authentication.principal.SimpleWebApplicationServiceImpl
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.ticket.ServiceTicket
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.ResponseEntity
 */
package org.apereo.cas.rest.factory;

import lombok.Generated;
import org.apereo.cas.CentralAuthenticationService;
import org.apereo.cas.authentication.AuthenticationResult;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.authentication.principal.SimpleWebApplicationServiceImpl;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.rest.factory.ServiceTicketResourceEntityResponseFactory;
import org.apereo.cas.ticket.ServiceTicket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CasProtocolServiceTicketResourceEntityResponseFactory
implements ServiceTicketResourceEntityResponseFactory {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CasProtocolServiceTicketResourceEntityResponseFactory.class);
    protected final CentralAuthenticationService centralAuthenticationService;

    @Override
    public ResponseEntity<String> build(String ticketGrantingTicket, WebApplicationService webApplicationService, AuthenticationResult authenticationResult) {
        String serviceTicketId = this.grantServiceTicket(ticketGrantingTicket, webApplicationService, authenticationResult);
        return new ResponseEntity((Object)serviceTicketId, HttpStatus.OK);
    }

    protected String grantServiceTicket(String ticketGrantingTicket, WebApplicationService service, AuthenticationResult authenticationResult) {
        ServiceTicket ticket = this.centralAuthenticationService.grantServiceTicket(ticketGrantingTicket, (Service)service, authenticationResult);
        LOGGER.debug("Generated service ticket [{}]", (Object)ticket.getId());
        return ticket.getId();
    }

    @Override
    public boolean supports(WebApplicationService service, AuthenticationResult authenticationResult) {
        return service instanceof SimpleWebApplicationServiceImpl;
    }

    public int getOrder() {
        return Integer.MAX_VALUE;
    }

    @Generated
    public CasProtocolServiceTicketResourceEntityResponseFactory(CentralAuthenticationService centralAuthenticationService) {
        this.centralAuthenticationService = centralAuthenticationService;
    }
}

