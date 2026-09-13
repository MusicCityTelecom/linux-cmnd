/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  lombok.Generated
 *  org.apache.commons.lang3.BooleanUtils
 *  org.apache.commons.text.StringEscapeUtils
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationCredentialsThreadLocalBinder
 *  org.apereo.cas.authentication.AuthenticationException
 *  org.apereo.cas.authentication.AuthenticationResult
 *  org.apereo.cas.authentication.AuthenticationResultBuilder
 *  org.apereo.cas.authentication.AuthenticationSystemSupport
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.rest.BadRestRequestException
 *  org.apereo.cas.rest.factory.RestHttpRequestCredentialFactory
 *  org.apereo.cas.rest.factory.ServiceTicketResourceEntityResponseFactory
 *  org.apereo.cas.ticket.InvalidTicketException
 *  org.apereo.cas.ticket.registry.TicketRegistrySupport
 *  org.apereo.cas.util.LoggingUtils
 *  org.apereo.cas.web.support.ArgumentExtractor
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.ResponseEntity
 *  org.springframework.util.MultiValueMap
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package org.apereo.cas.support.rest.resources;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.Generated;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationCredentialsThreadLocalBinder;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.AuthenticationResult;
import org.apereo.cas.authentication.AuthenticationResultBuilder;
import org.apereo.cas.authentication.AuthenticationSystemSupport;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.rest.BadRestRequestException;
import org.apereo.cas.rest.factory.RestHttpRequestCredentialFactory;
import org.apereo.cas.rest.factory.ServiceTicketResourceEntityResponseFactory;
import org.apereo.cas.support.rest.resources.RestResourceUtils;
import org.apereo.cas.ticket.InvalidTicketException;
import org.apereo.cas.ticket.registry.TicketRegistrySupport;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.web.support.ArgumentExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="serviceTicketResourceRestController")
public class ServiceTicketResource {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceTicketResource.class);
    private final AuthenticationSystemSupport authenticationSystemSupport;
    private final TicketRegistrySupport ticketRegistrySupport;
    private final ArgumentExtractor argumentExtractor;
    private final ServiceTicketResourceEntityResponseFactory serviceTicketResourceEntityResponseFactory;
    private final RestHttpRequestCredentialFactory credentialFactory;
    private final ApplicationContext applicationContext;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PostMapping(value={"/v1/tickets/{tgtId:.+}"}, consumes={"application/x-www-form-urlencoded", "application/json", "text/html", "text/plain"}, produces={"application/x-www-form-urlencoded", "application/json", "text/html", "text/plain"})
    public ResponseEntity<String> createServiceTicket(HttpServletRequest httpServletRequest, @RequestBody(required=false) MultiValueMap<String, String> requestBody, @PathVariable(value="tgtId") String tgtId) {
        ResponseEntity responseEntity;
        try {
            Authentication authn = this.ticketRegistrySupport.getAuthenticationFrom(StringEscapeUtils.escapeHtml4((String)tgtId));
            if (authn == null) {
                throw new InvalidTicketException(tgtId);
            }
            AuthenticationCredentialsThreadLocalBinder.bindCurrent((Authentication)authn);
            WebApplicationService service = Objects.requireNonNull(this.argumentExtractor.extractService(httpServletRequest), "Target service/application is unspecified or unrecognized in the request");
            if (BooleanUtils.toBoolean((String)httpServletRequest.getParameter("renew"))) {
                List credential = this.credentialFactory.fromRequest(httpServletRequest, requestBody);
                if (credential == null || credential.isEmpty()) {
                    throw new BadRestRequestException("No credentials are provided or extracted to authenticate the REST request");
                }
                AuthenticationResult authenticationResult = this.authenticationSystemSupport.finalizeAuthenticationTransaction((Service)service, (Collection)credential);
                ResponseEntity responseEntity2 = this.serviceTicketResourceEntityResponseFactory.build(tgtId, service, authenticationResult);
                return responseEntity2;
            }
            AuthenticationResultBuilder builder = this.authenticationSystemSupport.getAuthenticationResultBuilderFactory().newBuilder();
            AuthenticationResult authenticationResult = builder.collect(authn).build(this.authenticationSystemSupport.getPrincipalElectionStrategy(), (Service)service);
            ResponseEntity responseEntity3 = this.serviceTicketResourceEntityResponseFactory.build(tgtId, service, authenticationResult);
            return responseEntity3;
        }
        catch (InvalidTicketException e) {
            responseEntity = new ResponseEntity((Object)(StringEscapeUtils.escapeHtml4((String)tgtId) + " could not be found or is considered invalid"), HttpStatus.NOT_FOUND);
            return responseEntity;
        }
        catch (AuthenticationException e) {
            responseEntity = RestResourceUtils.createResponseEntityForAuthnFailure(e, httpServletRequest, this.applicationContext);
            return responseEntity;
        }
        catch (BadRestRequestException e) {
            LoggingUtils.error((Logger)LOGGER, (Throwable)e);
            responseEntity = new ResponseEntity((Object)StringEscapeUtils.escapeHtml4((String)e.getMessage()), HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
        catch (Exception e) {
            LoggingUtils.error((Logger)LOGGER, (Throwable)e);
            responseEntity = new ResponseEntity((Object)StringEscapeUtils.escapeHtml4((String)e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            return responseEntity;
        }
        finally {
            AuthenticationCredentialsThreadLocalBinder.clear();
        }
    }

    @Generated
    public ServiceTicketResource(AuthenticationSystemSupport authenticationSystemSupport, TicketRegistrySupport ticketRegistrySupport, ArgumentExtractor argumentExtractor, ServiceTicketResourceEntityResponseFactory serviceTicketResourceEntityResponseFactory, RestHttpRequestCredentialFactory credentialFactory, ApplicationContext applicationContext) {
        this.authenticationSystemSupport = authenticationSystemSupport;
        this.ticketRegistrySupport = ticketRegistrySupport;
        this.argumentExtractor = argumentExtractor;
        this.serviceTicketResourceEntityResponseFactory = serviceTicketResourceEntityResponseFactory;
        this.credentialFactory = credentialFactory;
        this.applicationContext = applicationContext;
    }
}

