/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.apache.commons.text.StringEscapeUtils
 *  org.apereo.cas.CentralAuthenticationService
 *  org.apereo.cas.authentication.AuthenticationException
 *  org.apereo.cas.authentication.AuthenticationResult
 *  org.apereo.cas.logout.slo.SingleLogoutRequestContext
 *  org.apereo.cas.logout.slo.SingleLogoutRequestExecutor
 *  org.apereo.cas.rest.BadRestRequestException
 *  org.apereo.cas.rest.authentication.RestAuthenticationService
 *  org.apereo.cas.rest.factory.TicketGrantingTicketResourceEntityResponseFactory
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.apereo.cas.util.LoggingUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.ResponseEntity
 *  org.springframework.util.MultiValueMap
 *  org.springframework.web.bind.annotation.DeleteMapping
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package org.apereo.cas.support.rest.resources;

import java.util.List;
import java.util.Optional;
import javax.security.auth.login.FailedLoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apache.commons.text.StringEscapeUtils;
import org.apereo.cas.CentralAuthenticationService;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.AuthenticationResult;
import org.apereo.cas.logout.slo.SingleLogoutRequestContext;
import org.apereo.cas.logout.slo.SingleLogoutRequestExecutor;
import org.apereo.cas.rest.BadRestRequestException;
import org.apereo.cas.rest.authentication.RestAuthenticationService;
import org.apereo.cas.rest.factory.TicketGrantingTicketResourceEntityResponseFactory;
import org.apereo.cas.support.rest.resources.RestResourceUtils;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.util.LoggingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="ticketGrantingTicketResource")
public class TicketGrantingTicketResource {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketGrantingTicketResource.class);
    private final RestAuthenticationService authenticationService;
    private final CentralAuthenticationService centralAuthenticationService;
    private final TicketGrantingTicketResourceEntityResponseFactory ticketGrantingTicketResourceEntityResponseFactory;
    private final ApplicationContext applicationContext;
    private final SingleLogoutRequestExecutor singleLogoutRequestExecutor;

    @GetMapping(value={"/v1/tickets"})
    public ResponseEntity<String> rejectGetResponse() {
        return new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PostMapping(value={"/v1/tickets"}, consumes={"application/x-www-form-urlencoded", "application/json", "text/html", "text/plain"}, produces={"application/x-www-form-urlencoded", "application/json", "text/html", "text/plain"})
    public ResponseEntity<String> createTicketGrantingTicket(@RequestBody(required=false) MultiValueMap<String, String> requestBody, HttpServletRequest request, HttpServletResponse response) {
        try {
            TicketGrantingTicket tgtId = this.createTicketGrantingTicketForRequest(requestBody, request, response);
            return this.createResponseEntityForTicket(request, tgtId);
        }
        catch (AuthenticationException e) {
            return RestResourceUtils.createResponseEntityForAuthnFailure(e, request, this.applicationContext);
        }
        catch (BadRestRequestException e) {
            LoggingUtils.error((Logger)LOGGER, (Throwable)e);
            return new ResponseEntity((Object)StringEscapeUtils.escapeHtml4((String)e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            LoggingUtils.error((Logger)LOGGER, (Throwable)e);
            return new ResponseEntity((Object)StringEscapeUtils.escapeHtml4((String)e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value={"/v1/tickets/{tgtId:.+}"}, produces={"application/json"})
    public ResponseEntity<List<SingleLogoutRequestContext>> deleteTicketGrantingTicket(@PathVariable(value="tgtId") String tgtId, HttpServletRequest request, HttpServletResponse response) {
        List requests = this.singleLogoutRequestExecutor.execute(tgtId, request, response);
        return new ResponseEntity((Object)requests, HttpStatus.OK);
    }

    protected ResponseEntity<String> createResponseEntityForTicket(HttpServletRequest request, TicketGrantingTicket tgtId) throws Exception {
        return this.ticketGrantingTicketResourceEntityResponseFactory.build(tgtId, request);
    }

    protected TicketGrantingTicket createTicketGrantingTicketForRequest(MultiValueMap<String, String> requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Optional authenticationResult = this.authenticationService.authenticate(requestBody, request, response);
        AuthenticationResult result = (AuthenticationResult)authenticationResult.orElseThrow(FailedLoginException::new);
        return this.centralAuthenticationService.createTicketGrantingTicket(result);
    }

    @Generated
    public TicketGrantingTicketResource(RestAuthenticationService authenticationService, CentralAuthenticationService centralAuthenticationService, TicketGrantingTicketResourceEntityResponseFactory ticketGrantingTicketResourceEntityResponseFactory, ApplicationContext applicationContext, SingleLogoutRequestExecutor singleLogoutRequestExecutor) {
        this.authenticationService = authenticationService;
        this.centralAuthenticationService = centralAuthenticationService;
        this.ticketGrantingTicketResourceEntityResponseFactory = ticketGrantingTicketResourceEntityResponseFactory;
        this.applicationContext = applicationContext;
        this.singleLogoutRequestExecutor = singleLogoutRequestExecutor;
    }
}

