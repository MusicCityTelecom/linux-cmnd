/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.apache.commons.text.StringEscapeUtils
 *  org.apereo.cas.authentication.AuthenticationException
 *  org.apereo.cas.authentication.AuthenticationResult
 *  org.apereo.cas.rest.BadRestRequestException
 *  org.apereo.cas.rest.authentication.RestAuthenticationService
 *  org.apereo.cas.rest.factory.UserAuthenticationResourceEntityResponseFactory
 *  org.apereo.cas.util.LoggingUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.ResponseEntity
 *  org.springframework.util.MultiValueMap
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package org.apereo.cas.support.rest.resources;

import java.util.Optional;
import javax.security.auth.login.FailedLoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apache.commons.text.StringEscapeUtils;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.AuthenticationResult;
import org.apereo.cas.rest.BadRestRequestException;
import org.apereo.cas.rest.authentication.RestAuthenticationService;
import org.apereo.cas.rest.factory.UserAuthenticationResourceEntityResponseFactory;
import org.apereo.cas.support.rest.resources.RestResourceUtils;
import org.apereo.cas.util.LoggingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="userAuthenticationResource")
public class UserAuthenticationResource {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthenticationResource.class);
    private final RestAuthenticationService authenticationService;
    private final UserAuthenticationResourceEntityResponseFactory userAuthenticationResourceEntityResponseFactory;
    private final ApplicationContext applicationContext;

    @PostMapping(value={"/v1/users"}, consumes={"application/x-www-form-urlencoded", "application/json"}, produces={"application/x-www-form-urlencoded", "application/json"})
    public ResponseEntity<String> authenticateRequest(@RequestBody MultiValueMap<String, String> requestBody, HttpServletRequest request, HttpServletResponse response) {
        try {
            Optional authenticationResult = this.authenticationService.authenticate(requestBody, request, response);
            AuthenticationResult result = (AuthenticationResult)authenticationResult.orElseThrow(FailedLoginException::new);
            return this.userAuthenticationResourceEntityResponseFactory.build(result, request);
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

    @Generated
    public UserAuthenticationResource(RestAuthenticationService authenticationService, UserAuthenticationResourceEntityResponseFactory userAuthenticationResourceEntityResponseFactory, ApplicationContext applicationContext) {
        this.authenticationService = authenticationService;
        this.userAuthenticationResourceEntityResponseFactory = userAuthenticationResourceEntityResponseFactory;
        this.applicationContext = applicationContext;
    }
}

